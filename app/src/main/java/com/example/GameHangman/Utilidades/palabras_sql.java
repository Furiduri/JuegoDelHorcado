package com.example.GameHangman.Utilidades;

public class palabras_sql {
    //Constantes
    static public final String TABLE_Words = "Palabras";
    static public final String CAMPO_ID="id";
    static public final String CAMPO_Palabrs="Palabra";
    static public final String CAMPO_Level="Nivel";

    // Comandos
    static final public String UP_CREAR_TABLA_Palabras="CREATE TABLE "+TABLE_Words+" ("
            +CAMPO_ID+" INTEGER, "
            +CAMPO_Palabrs+" TEXT, "
            +CAMPO_Level+" INTEGER)";

    static public final String UP_DROP_TABLE="DROP TABLE IF EXISTS "+ palabras_sql.TABLE_Words;
}
