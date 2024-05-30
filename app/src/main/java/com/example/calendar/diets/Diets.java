package com.example.calendar.diets;

// Класс Diets представляет информацию о пищевых продуктах и их пищевой ценности
public class Diets {
    private String food;      // Название пищевого продукта
    private int calories;     // Количество калорий в единице продукта
    private float fats;       // Количество жиров в единице продукта
    private float proteins;   // Количество белков в единице продукта
    private float carbs;      // Количество углеводов в единице продукта

    // Конструктор класса, инициализирующий все поля
    public Diets(String food, int calories, float fats, float proteins, float carbs){
        this.food = food;
        this.calories = calories;
        this.fats = fats;
        this.proteins = proteins;
        this.carbs = carbs;
    }

    // Возвращает название пищевого продукта
    public String getFood() {
        return food;
    }

    // Возвращает количество калорий в продукте
    public int getCalories() {
        return calories;
    }

    // Возвращает количество жиров в продукте
    public float getFats() {
        return fats;
    }

    // Возвращает количество белков в продукте
    public float getProteins() {
        return proteins;
    }

    // Возвращает количество углеводов в продукте
    public float getCarbs() {
        return carbs;
    }
}
