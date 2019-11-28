package com.example.GameHangman;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.GameHangman.Entidades.ObjPalabra;
import com.example.GameHangman.Utilidades.Utilidades;

import java.util.ArrayList;

public class ConexionSQLite extends SQLiteOpenHelper {


    public ConexionSQLite(Context context,SQLiteDatabase.CursorFactory factory) {
        super(context, Utilidades.DB_Game, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se crean las tablas a utilizar en la base de datos
        db.execSQL(Utilidades.UP_CREAR_TABLA_Palabras);
        db.execSQL(Utilidades.UP_CREAR_TABLA_Puntuaciones);
        //Datos por defecto de palabras
        ArrayList<ObjPalabra> lista = new ArrayList<ObjPalabra>();
        lista.add(new ObjPalabra(1,"Hola Mundo",2));
        lista.add(new ObjPalabra(2,"La vida es bella",3));
        lista.add(new ObjPalabra(3,"Sopa",1));
        lista.add(new ObjPalabra(4,"Manzana",1));
        for (ObjPalabra palabra:lista
             ) {
            ContentValues Values = new ContentValues();
            Values.put(Utilidades.CAMPO_Palabra,palabra.getPalabra().toUpperCase());
            Values.put(Utilidades.CAMPO_Level,palabra.getNivel());
            db.insert(Utilidades.TABLE_Words,Utilidades.CAMPO_PK_Palabra,Values);
        }
        lista.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se vacian los datos de las tablas al atulizar la version de la base de datos
        db.execSQL(Utilidades.UP_DROP_TABLE_Palabras);
        db.execSQL(Utilidades.UP_DROP_TABLE_Puntuaciones);

        onCreate(db);
    }
}
