package com.example.nuevotfg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "ProCookDB.db", null, 1);
    }

    @Override
    public void onConfigure(SQLiteDatabase DB) {
        DB.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(DB);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Ingredientes (idIngredient INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(10)," +
                " usuarios_idUser INTEGER, FOREIGN KEY (usuarios_idUser) REFERENCES Usuarios (idUser), UNIQUE(name));");
        DB.execSQL("CREATE TABLE Usuarios (idUser INTEGER PRIMARY KEY AUTOINCREMENT, mail varchar(30), password varchar(30));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS Ingredientes");
        DB.execSQL("DROP TABLE IF EXISTS Usuarios");
    }

    public boolean insertIngredient(String name, String idString) {
        long result = -1;
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int idUser = Integer.parseInt(idString);

        Cursor cursor = DB.rawQuery("SELECT * FROM Ingredientes where usuarios_idUser = ? and name = ?",new String[]{idString, name});
        if (!cursor.moveToFirst()){
            contentValues.put("name", name);
            contentValues.put("usuarios_idUser", idUser);
            result = DB.insert("Ingredientes", null, contentValues);
        }
        return result != -1;
    }

    public void insertUser(String mail, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mail", mail);
        contentValues.put("password", password);
        DB.insert("Usuarios", null, contentValues);

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

    public Cursor viewIngredient(String idUser) {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT * FROM Ingredientes where usuarios_idUser = ?",new String[]{idUser});

    }

    public Cursor checkUserAndPass(String user, String pass) throws SQLException {
        Cursor mcursor = null;

        mcursor = this.getReadableDatabase().rawQuery("SELECT idUser FROM Usuarios where mail = ? and password = ?", new String[]{user, pass});


        return mcursor;
    }

    //    public boolean updateIngredient(String name) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//
//        Cursor cursor = DB.rawQuery("SELECT * FROM Ingredientes where name = ?", new String[]{name});
//        if (cursor.getCount() > 0) {
//            long result = DB.update("Ingredientes", contentValues, "name = ?", new String[]{name});
//            return result != -1;
//        } else{
//            return false;
//        }
//    }
}
