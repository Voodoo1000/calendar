package com.example.calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.calendar.diets.MealPlanFragment;
import com.example.calendar.training.TrainingFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    // Конструктор адаптера, принимающий FragmentActivity как параметр
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    // Метод для создания фрагмента для заданной позиции
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Возвращаем фрагмент в зависимости от позиции
        switch (position) {
            case 0:
                // Если позиция 0, возвращаем фрагмент для тренировок
                return new TrainingFragment();
            case 1:
                // Если позиция 1, возвращаем фрагмент для плана питания
                return new MealPlanFragment();
            default:
                // По умолчанию возвращаем фрагмент для тренировок (не должно использоваться)
                return new TrainingFragment();
        }
    }

    // Метод, возвращающий количество фрагментов
    @Override
    public int getItemCount() {
        // У нас два фрагмента: для тренировок и для плана питания
        return 2;
    }
}
