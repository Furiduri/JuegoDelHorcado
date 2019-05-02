package com.example.GameHangman.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.GameHangman.ConexionSQLite;
import com.example.GameHangman.Entidades.ObjPalabra;
import com.example.GameHangman.Entidades.ObjPuntuacion;

import java.util.ArrayList;

public class Utilidades {
    //Constantes
    static public final String DB_Game = "db_Game";
    //Tabla Palabras
    static public final String TABLE_Words = "T_Palabras";
    static public final String CAMPO_PK_Palabra ="PK_Palabra";
    static public final String CAMPO_Palabra="Palabra";
    static public final String CAMPO_Level="Nivel";
    //Tabla Puntuaciones
    static public final String TABLE_Score = "T_Puntuaciones";
    static public final String CAMPO_PK_Puntuacion = "PK_Puntucion";
    static public final String CAMPO_Name ="Name";
    static public final String CAMPO_Life = "Life";
    static public final String CAMPO_Time = "Time";
    static public final String CAMPO_Punatucion = "Puntuacion";

    // Comandos
    //Crea la tabla palabras
    static final public String UP_CREAR_TABLA_Palabras="CREATE TABLE "+TABLE_Words+" ("
            + CAMPO_PK_Palabra +" INTEGER PRIMARY KEY, "
            + CAMPO_Palabra +" TEXT, "
            +CAMPO_Level+" INTEGER)";
    //Crear Tabla puntuaciones
    static  final public String UP_CREAR_TABLA_Puntuaciones = "CREATE TABLE "+TABLE_Score+" ("
            +CAMPO_PK_Puntuacion+" INTEGER PRIMARY KEY, "
            +CAMPO_Name+" TEXT, "
            + CAMPO_Palabra + " TEXT, "
            +CAMPO_Level+" INTEGER, "
            +CAMPO_Life+" INTEGER, "
            +CAMPO_Time+" INTEGER, "
            + CAMPO_Punatucion +" INTEGER)";


    //Vacia la tabla en caso de que existan datos
    static public final String UP_DROP_TABLE_Palabras="DROP TABLE IF EXISTS "+ Utilidades.TABLE_Words;
    static public final String UP_DROP_TABLE_Puntuaciones="DROP TABLE IF EXISTS "+ Utilidades.TABLE_Score;

    //Funciones

    //Agregar Nuevas palabras
    public static String ADDPalabra(String Palabra, String Level, Context context){
        String ResID = "0";
        //Creamos nuestra conexion
        ConexionSQLite conn = new ConexionSQLite(context, null);
        SQLiteDatabase db=conn.getWritableDatabase();
        //Parametros
        ContentValues Values = new ContentValues();
        Values.put(CAMPO_Palabra,Palabra.toUpperCase());
        Values.put(CAMPO_Level,Level);
        //Metodo insert para insertar datos en la tabla Words
        try{
            //Busca primero que no exista la palabra
            Cursor cursor = db.rawQuery(" SELECT "+CAMPO_PK_Palabra+","+CAMPO_Level+
                    " FROM "+TABLE_Words+" WHERE "+CAMPO_Palabra+" LIKE '"+Palabra+"' "
                    , null);
            //Si existe retorna el ID y el nivel de la palabra
            cursor.moveToFirst();
            ResID = cursor.getString(0) + " (YA EXISTE!)";
            Level = cursor.getString(1);
            cursor.close();
        }catch (Exception e){
            //si no existe la agrega
            Long idResultante=db.insert(Utilidades.TABLE_Words,Utilidades.CAMPO_PK_Palabra,Values);
            db.close();
            //Retorno del ID y la comprobacion de los datos ingresados
            ResID = idResultante.toString();
        }
        return ResID + ", Palabra: "+Palabra+", Dificultad: "+Level;
    }

    //Obtener Palabras
    public static ArrayList<ObjPalabra> GET_Palabras(Context context){
        ArrayList<ObjPalabra> ListaPalabras = new ArrayList<ObjPalabra>();
        //Creamos nuestra conexion
        ConexionSQLite conn = new ConexionSQLite(context, null);
        SQLiteDatabase db=conn.getWritableDatabase();
        try {
            //Obtenemos los datos
            Cursor cursor = db.rawQuery(" SELECT "+
                            CAMPO_PK_Palabra+","+CAMPO_Palabra+","+CAMPO_Level+
                            " FROM "+TABLE_Words
                    , null);
            //Si existen datos los aguardamos en un Array List
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                ListaPalabras.add(new ObjPalabra(
                        Integer.valueOf(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.valueOf(cursor.getString(2))));
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e){
            //Si no hay datos o ocurre un error se vacia el array, para evitar enviar datos corruptos
            ListaPalabras.clear();
        }
        return ListaPalabras;
    }

    //Obtener Puntuaciones
    public static ArrayList<ObjPuntuacion> GET_Puntuaciones(Context context){
        ArrayList<ObjPuntuacion> ListaPuntuaciones = new ArrayList<ObjPuntuacion>();
        //Creamos nuestra conexion
        ConexionSQLite conn = new ConexionSQLite(context, null);
        SQLiteDatabase db=conn.getWritableDatabase();
        try {
            //Realizamos la consulta
            Cursor cursor = db.rawQuery(" SELECT "+
                            CAMPO_PK_Puntuacion+","+CAMPO_Name+","+CAMPO_Palabra+","+CAMPO_Level+","+CAMPO_Life+","+CAMPO_Time+","+CAMPO_Punatucion+
                            " FROM "+TABLE_Score
                    , null);
            //Si existen datos los aguardamos en un Array List
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                ListaPuntuaciones.add(new ObjPuntuacion(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e){
            //Si no hay datos o ocurre un error se vacia el array, para evitar enviar datos corruptos
            ListaPuntuaciones.clear();
        }
        return ListaPuntuaciones;
    }

    //Save Score
    public static String AddNewScore(String UserName, String Score, String Life, String Level, String Palabra, String Time, Context context){
        String ResID = "";
        String MSGRes = "ERROR";
        //Creamos nuestra conexion
        ConexionSQLite conn = new ConexionSQLite(context, null);
        SQLiteDatabase db=conn.getWritableDatabase();

        //Metodo insert para insertar datos en la tabla Words
        try{
            //Busca primero que no exista la palabra
            Cursor cursor = db.rawQuery(" SELECT "+CAMPO_PK_Puntuacion+","+CAMPO_Punatucion+
                            " FROM "+TABLE_Score+" WHERE "+CAMPO_Palabra+" LIKE '"+Palabra+"' AND "+CAMPO_Name+" LIKE '"+UserName+"'"
                    , null);
            //Si existe retorna el ID
            cursor.moveToFirst();
            ResID = cursor.getString(0);
            //Actualiza la puntuacion
            //Parametros
            if(Integer.valueOf(Score) > Integer.valueOf(cursor.getString(1))) {
                ContentValues Values = new ContentValues();
                Values.put(CAMPO_Punatucion, Score);
                db.update(TABLE_Score, Values, CAMPO_PK_Puntuacion + "=" + ResID, null);
                MSGRes = "Se actualizo tu puntuacion \n De" + cursor.getString(1) + " a " + Score;
            }else{
                MSGRes = "Te fue mejor en la pasada \n" + cursor.getString(1) + "\n Obtiviste: " + Score;
            }
            cursor.close();
        }catch (Exception e){
            //si no existe la agrega
            //Parametros
            ContentValues Values = new ContentValues();
            Values.put(CAMPO_Name,UserName);
            Values.put(CAMPO_Punatucion,Score);
            Values.put(CAMPO_Life,Life);
            Values.put(CAMPO_Palabra,Palabra.toUpperCase());
            Values.put(CAMPO_Level,Level);
            Values.put(CAMPO_Time,Time);
            Long idResultante=db.insert(Utilidades.TABLE_Score,Utilidades.CAMPO_PK_Puntuacion,Values);
            db.close();
            //Retorno del ID y la comprobacion de los datos ingresados
            MSGRes = "Guardado: ID = "+ idResultante.toString();
        }
        return MSGRes;
    }
}
