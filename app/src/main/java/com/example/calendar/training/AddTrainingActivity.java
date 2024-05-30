package com.example.calendar.training;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.BDWorker;
import com.example.calendar.R;

public class AddTrainingActivity extends AppCompatActivity {

    // Объявление переменных для элементов интерфейса и базы данных
    private EditText workoutEditText;
    private EditText numberTimesEditText;
    private EditText numberApproachesEditText;
    private EditText notesEditText;
    private BDWorker bdWorker;
    private String selectedDate;

    // Метод, вызываемый при создании активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        // Инициализация BDWorker для взаимодействия с базой данных
        bdWorker = new BDWorker(this);

        // Получаем переданную дату из Intent
        selectedDate = getIntent().getStringExtra("selectedDate");

        // Получение ссылок на элементы интерфейса
        workoutEditText = findViewById(R.id.workout);
        numberTimesEditText = findViewById(R.id.numberTimes);
        numberApproachesEditText = findViewById(R.id.numberApproaches);
        notesEditText = findViewById(R.id.notes);

        // Настройка кнопки сохранения тренировки
        Button saveButton = findViewById(R.id.buttonSaveTraining);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получение значений из полей ввода
                String workout = workoutEditText.getText().toString();
                String numberTimesStr = numberTimesEditText.getText().toString();
                String numberApproachesStr = numberApproachesEditText.getText().toString();
                String notes = notesEditText.getText().toString();

                // Проверка, чтобы все поля были заполнены
                if (workout.isEmpty() || numberTimesStr.isEmpty() || numberApproachesStr.isEmpty()) {
                    Toast.makeText(AddTrainingActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Преобразование строковых значений в целые числа
                int numberTimes = Integer.parseInt(numberTimesStr);
                int numberApproaches = Integer.parseInt(numberApproachesStr);

                // Добавление тренировки в базу данных
                bdWorker.insertTraining(workout, selectedDate, numberTimes, numberApproaches, notes);

                // Отображение уведомления об успешном добавлении и закрытие активности
                Toast.makeText(AddTrainingActivity.this, "Тренировка добавлена", Toast.LENGTH_SHORT).show();
                finish();  // Закрыть активность после сохранения
            }
        });
    }
}
