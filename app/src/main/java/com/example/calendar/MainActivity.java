package com.example.calendar;

import android.os.Bundle;
import android.widget.CalendarView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Объявляем переменные для CalendarView, ViewPager2 и адаптера ViewPagerAdapter
    private CalendarView calendarView;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализируем CalendarView и ViewPager2, используя findViewById
        calendarView = findViewById(R.id.calendarView);
        viewPager = findViewById(R.id.viewPager);

        // Создаем экземпляр ViewPagerAdapter и устанавливаем его в ViewPager
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        // Получаем текущую дату
        selectedDate = getCurrentDate();

        // Устанавливаем слушатель для выбора даты на CalendarView
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Форматируем выбранную дату в строку "ГГГГ-ММ-ДД"
                selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                // Обновляем фрагменты, передавая им выбранную дату
                updateFragmentsWithSelectedDate();
            }
        });

        // Обновляем фрагменты при начальной загрузке, передавая текущую дату
        updateFragmentsWithSelectedDate();
    }

    // Метод для обновления фрагментов с выбранной датой
    private void updateFragmentsWithSelectedDate() {
        // Проходим через все фрагменты, добавленные в FragmentManager
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            // Если фрагмент поддерживает интерфейс DateDependentFragment, обновляем его дату
            if (fragment instanceof DateDependentFragment) {
                ((DateDependentFragment) fragment).setSelectedDate(selectedDate);
            }
        }
    }

    // Метод для получения текущей даты в формате "ГГГГ-ММ-ДД"
    private String getCurrentDate() {
        // Создаем экземпляр Calendar и получаем текущий год, месяц и день
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // Возвращаем дату в формате строки
        return String.format("%04d-%02d-%02d", year, month + 1, day);
    }
}
