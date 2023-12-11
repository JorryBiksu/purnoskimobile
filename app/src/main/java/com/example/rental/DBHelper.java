package com.example.rental;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "carRental.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "cars";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_IMAGE_URL = "image_url";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BRAND + " TEXT, " +
                    COLUMN_MODEL + " TEXT, " +
                    COLUMN_YEAR + " TEXT, " +
                    COLUMN_IMAGE_URL + " TEXT);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Metode untuk menambahkan mobil ke database
    public void addCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRAND, car.getBrand());
        values.put(COLUMN_MODEL, car.getModel());
        values.put(COLUMN_YEAR, String.valueOf(car.getYear()));  // Ubah ke String
        values.put(COLUMN_IMAGE_URL, car.getImageUrl());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    // Tambahkan di DBHelper.java
    public void deleteCar(int carId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(carId)});
        db.close();
    }

    // Metode untuk mendapatkan semua mobil dari database
    @SuppressLint("Range")
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Car car = new Car();
                car.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                car.setBrand(cursor.getString(cursor.getColumnIndex(COLUMN_BRAND)));
                car.setModel(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL)));

                // Ubah dari String ke int
                try {
                    int year = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_YEAR)));
                    car.setYear(year);
                } catch (NumberFormatException e) {
                    // Handle jika parsing gagal
                    e.printStackTrace();
                }

                car.setImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)));

                cars.add(car);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cars;
    }
}

