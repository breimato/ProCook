package com.example.nuevotfg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "ProCookDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Ingredientes (idIngredient INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS Ingredientes");
    }

    public boolean insertIngredient(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);

        long result = DB.insert("Ingredientes", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateIngredient(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);

        Cursor cursor = DB.rawQuery("SELECT * FROM Ingredientes where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("Ingredientes", contentValues, "name = ?", new String[]{name});
            return result != -1;
        } else{
            return false;
        }
    }

    public boolean deleteIngredient(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Ingredientes where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Ingredientes", "name = ?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else{
            return false;
        }
    }

    public Cursor viewIngredient() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT * FROM Ingredientes", null);

    }
}
