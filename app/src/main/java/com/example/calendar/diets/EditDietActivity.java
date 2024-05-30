package com.example.calendar.diets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.BDWorker;
import com.example.calendar.R;

public class EditDietActivity extends AppCompatActivity {
    // Объявление переменных для работы с элементами интерфейса и базой данных
    private EditText foodEditText;
    private EditText caloriesEditText;
    private EditText fatsEditText;
    private EditText proteinsEditText;
    private EditText carbsEditText;
    private BDWorker bdWorker;
    private String selectedDate;
    private String originalFood;

    // Метод, вызываемый при создании активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diet);

        // Инициализация элементов интерфейса и объекта для работы с базой данных
        foodEditText = findViewById(R.id.editTextFood);
        caloriesEditText = findViewById(R.id.editTextCalories);
        fatsEditText = findViewById(R.id.editTextFats);
        proteinsEditText = findViewById(R.id.editTextProteins);
        carbsEditText = findViewById(R.id.editTextCarbs);
        bdWorker = new BDWorker(this);

        // Получение данных о редактируемой диете из предыдущего экрана
        selectedDate = getIntent().getStringExtra("selectedDate");
        originalFood = getIntent().getStringExtra("food");
        int calories = getIntent().getIntExtra("calories", 0);
        float fats = getIntent().getFloatExtra("fats", 0.0f);
        float proteins = getIntent().getFloatExtra("proteins", 0.0f);
        float carbs = getIntent().getFloatExtra("carbs", 0.0f);

        // Установка полученных данных в соответствующие поля ввода
        foodEditText.setText(originalFood);
        caloriesEditText.setText(String.valueOf(calories));
        fatsEditText.setText(String.valueOf(fats));
        proteinsEditText.setText(String.valueOf(proteins));
        carbsEditText.setText(String.valueOf(carbs));

        // Настройка кнопки для сохранения изменений
        Button buttonSaveDiet = findViewById(R.id.buttonSaveDiet);
        buttonSaveDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDiet(); // Вызов метода для обновления данных о диете
            }
        });
    }

    // Метод для обновления данных о диете в базе данных
    private void updateDiet() {
        String food = foodEditText.getText().toString();
        int calories = Integer.parseInt(caloriesEditText.getText().toString());
        float fats = Float.parseFloat(fatsEditText.getText().toString());
        float proteins = Float.parseFloat(proteinsEditText.getText().toString());
        float carbs = Float.parseFloat(carbsEditText.getText().toString());

        // Обновление данных о диете в базе данных
        bdWorker.updateDiet(originalFood, selectedDate, calories, fats, proteins, carbs);
        Toast.makeText(this, "Рацион обновлен", Toast.LENGTH_SHORT).show();
        finish(); // Завершение текущей активности
    }
}
