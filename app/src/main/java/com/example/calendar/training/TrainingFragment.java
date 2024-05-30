package com.example.calendar.training;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calendar.BDWorker;
import com.example.calendar.DateDependentFragment;
import com.example.calendar.R;

import java.time.LocalDate;
import java.util.List;

public class TrainingFragment extends Fragment implements DateDependentFragment {

    private BDWorker bdWorker;
    private EditText editTextTraining;
    private String selectedDate;

    // Метод для создания и инициализации фрагмента
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training, container, false);

        // Инициализация объекта для работы с базой данных
        bdWorker = new BDWorker(getContext());
        // Получение ссылки на EditText для отображения информации о тренировке
        editTextTraining = view.findViewById(R.id.editTextTraining);
        editTextTraining.setKeyListener(null);  // Запретить редактирование

        // Настройка кнопки добавления тренировки
        Button buttonTrainingAdd = view.findViewById(R.id.buttonTrainingAdd);
        buttonTrainingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTrainingActivity.class);
                intent.putExtra("selectedDate", selectedDate);
                startActivity(intent);
            }
        });

        // Настройка кнопки удаления тренировки
        Button buttonTrainingDelete = view.findViewById(R.id.buttonTrainingDelete);
        buttonTrainingDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        // Настройка кнопки редактирования тренировки
        Button buttonTrainingEdit = view.findViewById(R.id.buttonTrainingEdit);
        buttonTrainingEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        return view;
    }

    // Метод, вызываемый при возобновлении работы фрагмента
    @Override
    public void onResume() {
        super.onResume();
        if (selectedDate != null) {
            displayTrainings();
        }
    }

    // Установка выбранной даты
    @Override
    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
        if (isResumed()) {
            displayTrainings();
        }
    }

    // Метод для отображения тренировок на выбранную дату
    private void displayTrainings() {
        LocalDate date = LocalDate.parse(selectedDate);
        List<Training> trainings = bdWorker.getTrainingsForDate(date);
        StringBuilder trainingInfo = new StringBuilder();
        for (Training training : trainings) {
            trainingInfo.append(training.getWorkout())
                    .append(" - ")
                    .append(training.getNumberApproaches())
                    .append(" подходов по ")
                    .append(training.getNumberTimes())
                    .append(" раз")
                    .append(" (")
                    .append(training.getNotes())
                    .append(")\n");
        }
        editTextTraining.setText(trainingInfo.toString());
    }

    // Метод для отображения диалога подтверждения удаления тренировки
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Удалить тренировку");

        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog, (ViewGroup) getView(), false);
        final EditText input = viewInflated.findViewById(R.id.editTextDeleteWorkout);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String workoutToDelete = input.getText().toString();
                if (!workoutToDelete.isEmpty()) {
                    LocalDate date = LocalDate.parse(selectedDate);
                    boolean success = bdWorker.deleteTraining(workoutToDelete, date);
                    if (success) {
                        Toast.makeText(getContext(), "Тренировка удалена", Toast.LENGTH_SHORT).show();
                        displayTrainings();  // Обновить отображаемую информацию
                    } else {
                        Toast.makeText(getContext(), "Не удалось найти тренировку", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    // Метод для отображения диалога редактирования тренировки
    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Изменить тренировку");

        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog, (ViewGroup) getView(), false);
        final EditText input = viewInflated.findViewById(R.id.editTextDeleteWorkout);
        input.setHint("Введите название тренировки");
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String workoutToEdit = input.getText().toString();
                if (!workoutToEdit.isEmpty()) {
                    LocalDate date = LocalDate.parse(selectedDate);
                    Training training = bdWorker.getTrainingForDate(workoutToEdit, date);
                    if (training != null) {
                        Intent intent = new Intent(getActivity(), EditTrainingActivity.class);
                        intent.putExtra("selectedDate", selectedDate);
                        intent.putExtra("workout", training.getWorkout());
                        intent.putExtra("numberTimes", training.getNumberTimes());
                        intent.putExtra("numberApproaches", training.getNumberApproaches());
                        intent.putExtra("notes", training.getNotes());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Тренировка не найдена", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
