package com.example.nuevotfg.DB;

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
        DB.execSQL("CREATE TABLE Ingredientes (" +
                "idIngredient INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name varchar(10)," +
                " intolerante INTEGER, " +
                " usuarios_idUser INTEGER," +
                " FOREIGN KEY (usuarios_idUser) REFERENCES Usuarios (idUser));");
        DB.execSQL("CREATE TABLE Usuarios (idUser INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mail varchar(30)," +
                "password varchar(30)," +
                "name varchar(30)," +
                "lastname varchar(30)," +
                "phone varchar(30));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS Ingredientes");
        DB.execSQL("DROP TABLE IF EXISTS Usuarios");
    }

    public boolean insertIngredient(String name, String idString, int check) {
        long result = -1;
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int idUser = Integer.parseInt(idString);
        String checkString = Integer.toString(check);

        Cursor cursor = DB.rawQuery("SELECT * FROM Ingredientes where usuarios_idUser = ? and name = ? and intolerante = ?",new String[]{idString, name, checkString});
        if (!cursor.moveToFirst()){
            contentValues.put("name", name);
            contentValues.put("intolerante", check);
            contentValues.put("usuarios_idUser", idUser);

            result = DB.insert("Ingredientes", null, contentValues);
        }
        return result != -1;
    }

    public void insertUser(String mail, String password, String name, String lastname, String phone) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mail", mail);
        contentValues.put("password", password);
        contentValues.put("name", name);
        contentValues.put("lastname", lastname);
        contentValues.put("phone", phone);
        DB.insert("Usuarios", null, contentValues);

    }

    public boolean deleteIngredient(String name, int check) {
        String checkString = Integer.toString(check);
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Ingredientes where name = ? and intolerante = ?", new String[]{name, checkString});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Ingredientes", "name = ? and intolerante = ?", new String[]{name, checkString});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else{
            return false;
        }
    }

    public Cursor viewIngredient(String idUser, int check) {
        SQLiteDatabase DB = this.getWritableDatabase();
        String checkString = Integer.toString(check);
        return DB.rawQuery("SELECT * FROM Ingredientes where usuarios_idUser = ? and intolerante = ?",new String[]{idUser, checkString});

    }

    public Cursor checkUserAndPass(String user, String pass) throws SQLException {return this.getReadableDatabase().rawQuery("SELECT idUser FROM Usuarios where mail = ? and password = ?", new String[]{user, pass});}
}
