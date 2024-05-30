package com.example.calendar.training;

// Класс, представляющий тренировочный план
public class Training {
    private String workout; // Название упражнения
    private int numberTimes; // Количество повторений упражнения за один подход
    private int numberApproaches; // Общее количество подходов к упражнению
    private String notes; // Дополнительные заметки о тренировке

    // Конструктор для создания нового объекта тренировочного плана
    public Training(String workout, int numberTimes, int numberApproaches, String notes) {
        this.workout = workout; // Инициализация названия упражнения
        this.numberTimes = numberTimes; // Инициализация количества повторений
        this.numberApproaches = numberApproaches; // Инициализация количества подходов
        this.notes = notes; // Инициализация заметок
    }

    // Возвращает название упражнения
    public String getWorkout() {
        return workout;
    }

    // Возвращает количество повторений упражнения за один подход
    public int getNumberTimes() {
        return numberTimes;
    }

    // Возвращает общее количество подходов к упражнению
    public int getNumberApproaches() {
        return numberApproaches;
    }

    // Возвращает дополнительные заметки о тренировке
    public String getNotes() {
        return notes;
    }
}
