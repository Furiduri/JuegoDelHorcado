package com.example.GameHangman;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.GameHangman.Utilidades.palabras_sql;

public class ConexionSQLite extends SQLiteOpenHelper {


    public ConexionSQLite(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(palabras_sql.UP_CREAR_TABLA_Palabras);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(palabras_sql.UP_DROP_TABLE);
        onCreate(db);
    }
}
