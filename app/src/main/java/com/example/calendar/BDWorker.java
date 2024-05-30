package com.example.calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.calendar.diets.Diets;
import com.example.calendar.training.Training;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BDWorker {
    private SQLiteDatabase database;

    // Конструктор, принимающий контекст и создающий объект базы данных
    public BDWorker(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Метод для вставки новой записи о тренировке
    public void insertTraining(String workout, String date, int numberTimes, int numberApproaches, String notes) {
        String query = "INSERT INTO training_plans (workout, date, NumberTimes, NumberApproaches, notes) VALUES (?, ?, ?, ?, ?)";
        database.execSQL(query, new Object[]{workout, date, numberTimes, numberApproaches, notes});
    }

    // Метод для получения списка тренировок на определенную дату
    public List<Training> getTrainingsForDate(LocalDate date) {
        String query = "SELECT * FROM training_plans WHERE date = ?";
        Cursor cursor = database.rawQuery(query, new String[]{date.toString()});
        List<Training> trainings = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String workout = cursor.getString(cursor.getColumnIndexOrThrow("workout"));
                int numberTimes = cursor.getInt(cursor.getColumnIndexOrThrow("NumberTimes"));
                int numberApproaches = cursor.getInt(cursor.getColumnIndexOrThrow("NumberApproaches"));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
                trainings.add(new Training(workout, numberTimes, numberApproaches, notes));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return trainings;
    }

    // Метод для удаления записи о тренировке по названию и дате
    public boolean deleteTraining(String workout, LocalDate date) {
        String query = "DELETE FROM training_plans WHERE workout = ? AND date = ?";
        database.execSQL(query, new String[]{workout, date.toString()});
        return true;
    }

    // Метод для получения информации о конкретной тренировке по названию и дате
    public Training getTrainingForDate(String workout, LocalDate date) {
        String query = "SELECT * FROM training_plans WHERE workout = ? AND date = ?";
        Cursor cursor = database.rawQuery(query, new String[]{workout, date.toString()});
        if (cursor.moveToFirst()) {
            int numberTimes = cursor.getInt(cursor.getColumnIndexOrThrow("NumberTimes"));
            int numberApproaches = cursor.getInt(cursor.getColumnIndexOrThrow("NumberApproaches"));
            String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
            cursor.close();
            return new Training(workout, numberTimes, numberApproaches, notes);
        }
        cursor.close();
        return null;
    }

    // Метод для обновления записи о тренировке
    public void updateTraining(String workout, String date, int numberTimes, int numberApproaches, String notes) {
        String query = "UPDATE training_plans SET NumberTimes = ?, NumberApproaches = ?, notes = ? WHERE workout = ? AND date = ?";
        database.execSQL(query, new Object[]{numberTimes, numberApproaches, notes, workout, date});
    }

    // Метод для получения списка планов питания на определенную дату
    public List<Diets> getDietsForDate(LocalDate date) {
        String query = "SELECT * FROM diet_plans WHERE date = ?";
        Cursor cursor = database.rawQuery(query, new String[]{date.toString()});
        List<Diets> diets = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String food = cursor.getString(cursor.getColumnIndexOrThrow("food"));
                int calories = cursor.getInt(cursor.getColumnIndexOrThrow("calories"));
                float fats = cursor.getFloat(cursor.getColumnIndexOrThrow("fats"));
                float proteins = cursor.getFloat(cursor.getColumnIndexOrThrow("proteins"));
                float carbs = cursor.getFloat(cursor.getColumnIndexOrThrow("carbs"));
                diets.add(new Diets(food, calories, fats, proteins, carbs));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return diets;
    }

    // Метод для вставки новой записи о плане питания
    public void insertDiet(String food, String date, int calories, float fats, float proteins, float carbs) {
        String query = "INSERT INTO diet_plans (food, date, calories, fats, proteins, carbs) VALUES (?, ?, ?, ?, ?, ?)";
        database.execSQL(query, new Object[]{food, date, calories, fats, proteins, carbs});
    }

    // Метод для удаления записи о плане питания по названию еды и дате
    public boolean deleteDiet(String food, LocalDate date) {
        String query = "DELETE FROM diet_plans WHERE food = ? AND date = ?";
        database.execSQL(query, new String[]{food, date.toString()});
        return true;
    }

    // Метод для получения информации о конкретном плане питания по названию еды и дате
    public Diets getDietForDate(String food, LocalDate date) {
        String query = "SELECT * FROM diet_plans WHERE food = ? AND date = ?";
        Cursor cursor = database.rawQuery(query, new String[]{food, date.toString()});
        if (cursor.moveToFirst()) {
            int calories = cursor.getInt(cursor.getColumnIndexOrThrow("calories"));
            float fats = cursor.getFloat(cursor.getColumnIndexOrThrow("fats"));
            float proteins = cursor.getFloat(cursor.getColumnIndexOrThrow("proteins"));
            float carbs = cursor.getFloat(cursor.getColumnIndexOrThrow("carbs"));
            cursor.close();
            return new Diets(food, calories, fats, proteins, carbs);
        }
        cursor.close();
        return null;
    }

    // Метод для обновления записи о плане питания
    public void updateDiet(String food, String date, int calories, float fats, float proteins, float carbs) {
        String query = "UPDATE diet_plans SET calories = ?, fats = ?, proteins = ?, carbs = ? WHERE food = ? AND date = ?";
        database.execSQL(query, new Object[]{calories, fats, proteins, carbs, food, date});
    }
}
