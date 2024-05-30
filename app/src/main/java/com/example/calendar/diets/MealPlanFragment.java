package com.example.calendar.diets;

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

public class MealPlanFragment extends Fragment implements DateDependentFragment {

    // Объявление переменных для работы с базой данных и элементами интерфейса
    private BDWorker bdWorker;
    private EditText editTextDiet;
    private String selectedDate;

    // Метод, вызываемый при создании представления фрагмента
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);

        // Инициализация объекта для работы с базой данных и получение ссылок на элементы интерфейса
        bdWorker = new BDWorker(getContext());
        editTextDiet = view.findViewById(R.id.editTextDiet);
        editTextDiet.setKeyListener(null);  // Запретить редактирование

        // Настройка кнопок для добавления, удаления и редактирования диеты
        Button buttonDietAdd = view.findViewById(R.id.buttonDietAdd);
        buttonDietAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDietActivity.class);
                intent.putExtra("selectedDate", selectedDate);
                startActivity(intent);
            }
        });

        Button buttonDietDelete = view.findViewById(R.id.buttonDietDelete);
        buttonDietDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        Button buttonDietEdit = view.findViewById(R.id.buttonDietEdit);
        buttonDietEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        return view;
    }

    // Метод, вызываемый при возобновлении фрагмента
    @Override
    public void onResume() {
        super.onResume();
        if (selectedDate != null) {
            displayDiets(); // При возобновлении фрагмента обновить отображаемые данные
        }
    }

    // Метод для установки выбранной даты
    @Override
    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
        if (isResumed()) {
            displayDiets(); // При выборе новой даты обновить отображаемые данные
        }
    }

    // Метод для отображения данных о диете
    private void displayDiets() {
        LocalDate date = LocalDate.parse(selectedDate);
        List<Diets> diets = bdWorker.getDietsForDate(date);
        StringBuilder dietInfo = new StringBuilder();
        for (Diets diet : diets) {
            dietInfo.append(diet.getFood())
                    .append(" - ")
                    .append(diet.getCalories())
                    .append(" ккал, ")
                    .append(diet.getFats())
                    .append(" жиров, ")
                    .append(diet.getProteins())
                    .append(" белков, ")
                    .append(diet.getCarbs())
                    .append(" углеводов\n");
        }
        editTextDiet.setText(dietInfo.toString());
    }

    // Метод для отображения диалогового окна для удаления рациона
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Удалить рацион");

        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog, (ViewGroup) getView(), false);
        final EditText input = viewInflated.findViewById(R.id.editTextDeleteWorkout);
        input.setHint("Введите название еды");
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String foodToDelete = input.getText().toString();
                if (!foodToDelete.isEmpty()) {
                    LocalDate date = LocalDate.parse(selectedDate);
                    boolean success = bdWorker.deleteDiet(foodToDelete, date);
                    if (success) {
                        Toast.makeText(getContext(), "Диета удалена", Toast.LENGTH_SHORT).show();
                        displayDiets();  // Обновить отображаемую информацию
                    } else {
                        Toast.makeText(getContext(), "Не удалось найти диету", Toast.LENGTH_SHORT).show();
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

    // Метод для отображения диалогового окна для редактирования рациона
    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Изменить рацион");

        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog, (ViewGroup) getView(), false);
        final EditText input = viewInflated.findViewById(R.id.editTextDeleteWorkout);
        input.setHint("Введите название еды");
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String foodToEdit = input.getText().toString();
                if (!foodToEdit.isEmpty()) {
                    LocalDate date = LocalDate.parse(selectedDate);
                    Diets diet = bdWorker.getDietForDate(foodToEdit, date);
                    if (diet != null) {
                        Intent intent = new Intent(getActivity(), EditDietActivity.class);
                        intent.putExtra("selectedDate", selectedDate);
                        intent.putExtra("food", diet.getFood());
                        intent.putExtra("calories", diet.getCalories());
                        intent.putExtra("fats", diet.getFats());
                        intent.putExtra("proteins", diet.getProteins());
                        intent.putExtra("carbs", diet.getCarbs());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Диета не найдена", Toast.LENGTH_SHORT).show();
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
