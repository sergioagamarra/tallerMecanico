package com.example.tallermecanicoantonio;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    static final int DNI = 37730377;
    static final String USUARIO = "sergioagamarra";
    static final String CONTRASENIA = "emiliano7";

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cliente(dni integer primary key, nombre text, email text, telefono text)");
        db.execSQL("CREATE TABLE servicio(id integer primary key autoincrement, dni_cliente INT, descripcion TEXT, dominio TEXT, modelo TEXT, importe INTEGER, fecha TEXT)");
        db.execSQL("create table admin(dni_adm integer primary key, usuario text, contrasenia text)");
        db.execSQL("insert into admin(dni_adm, usuario, contrasenia) " +
                "values ('"+ DNI +"', '"+ USUARIO + "', '" + CONTRASENIA + "')");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table cliente(dni integer primary key, nombre text, email text, telefono text)");
        db.execSQL("CREATE TABLE servicio(id integer primary key autoincrement, dni_cliente INT, descripcion TEXT, dominio TEXT, modelo TEXT, importe INTEGER, fecha TEXT)");
        db.execSQL("create table admin(dni_adm integer primary key, usuario text, contrasenia text)");
        db.execSQL("insert into admin(dni_adm, usuario, contrasenia) " +
                "values ('"+ DNI +"', '"+ USUARIO + "', '" + CONTRASENIA + "')");
    }
}

