package com.example.calendar.training;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.BDWorker;
import com.example.calendar.R;

public class EditTrainingActivity extends AppCompatActivity {

    // Объявление переменных для элементов интерфейса и базы данных
    private EditText editTextWorkout;
    private EditText editTextNumberTimes;
    private EditText editTextNumberApproaches;
    private EditText editTextNotes;
    private BDWorker bdWorker;
    private String selectedDate;
    private String originalWorkout;

    // Метод, вызываемый при создании активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_training);

        // Получение ссылок на элементы интерфейса
        editTextWorkout = findViewById(R.id.editTextWorkout);
        editTextNumberTimes = findViewById(R.id.editTextNumberTimes);
        editTextNumberApproaches = findViewById(R.id.editTextNumberApproaches);
        editTextNotes = findViewById(R.id.editTextNotes);

        // Инициализация объекта для работы с базой данных
        bdWorker = new BDWorker(this);

        // Получение данных из Intent
        selectedDate = getIntent().getStringExtra("selectedDate");
        originalWorkout = getIntent().getStringExtra("workout");
        int numberTimes = getIntent().getIntExtra("numberTimes", 0);
        int numberApproaches = getIntent().getIntExtra("numberApproaches", 0);
        String notes = getIntent().getStringExtra("notes");

        // Установка значений в элементы интерфейса
        editTextWorkout.setText(originalWorkout);
        editTextNumberTimes.setText(String.valueOf(numberTimes));
        editTextNumberApproaches.setText(String.valueOf(numberApproaches));
        editTextNotes.setText(notes);

        // Настройка кнопки сохранения изменений
        Button buttonSaveTraining = findViewById(R.id.buttonSaveTraining);
        buttonSaveTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTraining();  // Обновление тренировки при нажатии на кнопку
            }
        });
    }

    // Метод для обновления тренировки в базе данных
    private void updateTraining() {
        // Получение значений из элементов интерфейса
        int numberTimes = Integer.parseInt(editTextNumberTimes.getText().toString());
        int numberApproaches = Integer.parseInt(editTextNumberApproaches.getText().toString());
        String notes = editTextNotes.getText().toString();

        // Обновление тренировки в базе данных
        bdWorker.updateTraining(originalWorkout, selectedDate, numberTimes, numberApproaches, notes);
        Toast.makeText(this, "Тренировка обновлена", Toast.LENGTH_SHORT).show();
        finish();  // Закрытие активности после обновления
    }
}
