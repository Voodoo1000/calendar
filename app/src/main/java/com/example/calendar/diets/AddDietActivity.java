package com.example.calendar.diets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.BDWorker;
import com.example.calendar.R;

public class AddDietActivity extends AppCompatActivity {

    // Объявление переменных для работы с элементами интерфейса и базой данных
    private EditText foodEditText;
    private EditText caloriesEditText;
    private EditText fatsEditText;
    private EditText proteinsEditText;
    private EditText carbsEditText;
    private BDWorker bdWorker;
    private String selectedDate;

    // Метод, вызываемый при создании активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);

        // Инициализация объекта для работы с базой данных
        bdWorker = new BDWorker(this);

        // Получение переданной даты из предыдущего экрана
        selectedDate = getIntent().getStringExtra("selectedDate");

        // Привязка переменных к элементам интерфейса
        foodEditText = findViewById(R.id.food);
        caloriesEditText = findViewById(R.id.calories);
        fatsEditText = findViewById(R.id.fats);
        proteinsEditText = findViewById(R.id.proteins);
        carbsEditText = findViewById(R.id.carbs);

        // Настройка кнопки для сохранения рациона питания
        Button saveButton = findViewById(R.id.buttonSaveDiet);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получение данных о рационе питания из полей ввода
                String food = foodEditText.getText().toString();
                String caloriesStr = caloriesEditText.getText().toString();
                String fatsStr = fatsEditText.getText().toString();
                String proteinsStr = proteinsEditText.getText().toString();
                String carbsStr = carbsEditText.getText().toString();

                // Проверка, чтобы поля не были пустыми
                if (food.isEmpty() || caloriesStr.isEmpty() || fatsStr.isEmpty() || proteinsStr.isEmpty() || carbsStr.isEmpty()) {
                    Toast.makeText(AddDietActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Преобразование данных в соответствующие типы
                int calories = Integer.parseInt(caloriesStr);
                float fats = Float.parseFloat(fatsStr);
                float proteins = Float.parseFloat(proteinsStr);
                float carbs = Float.parseFloat(carbsStr);

                // Добавление рациона питания в базу данных
                bdWorker.insertDiet(food, selectedDate, calories, fats, proteins, carbs);

                // Отображение сообщения об успешном сохранении и закрытие активности
                Toast.makeText(AddDietActivity.this, "Рацион питания добавлен", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
