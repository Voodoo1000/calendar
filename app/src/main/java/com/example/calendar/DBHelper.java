package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // Имя базы данных и версия
    private static final String DATABASE_NAME = "BD.db";
    private static final int DATABASE_VERSION = 1;

    // Конструктор, принимающий контекст приложения
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание таблицы для хранения планов тренировок
        db.execSQL("CREATE TABLE training_plans (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "workout TEXT, " +
                "date TEXT, " +
                "NumberTimes INTEGER, " +
                "NumberApproaches INTEGER, " +
                "notes TEXT)");

        // Создание таблицы для хранения планов питания
        db.execSQL("CREATE TABLE diet_plans (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "food TEXT, " +
                "date TEXT, " +
                "calories INTEGER, " +
                "fats REAL, " +
                "proteins REAL, " +
                "carbs REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаление старых таблиц при обновлении базы данных
        db.execSQL("DROP TABLE IF EXISTS training_plans");
        db.execSQL("DROP TABLE IF EXISTS diet_plans");
        // Создание новых таблиц
        onCreate(db);
    }
}
