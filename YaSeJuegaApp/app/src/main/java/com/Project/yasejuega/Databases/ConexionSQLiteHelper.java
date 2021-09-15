package com.Project.yasejuega.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidades.CREAR_TABLA_LISTA_TORNEOS);
        db.execSQL(Utilidades.CREAR_TABLA_LISTA_RESERVA);
        db.execSQL(Utilidades.CREAR_TABLA_LISTA_BUSQUEDA);
        db.execSQL(Utilidades.CREAR_TABLA_MAPA);
        db.execSQL(Utilidades.CREAR_TABLA_CAMPO_HORA);
        db.execSQL(Utilidades.CREAR_TABLA_CAMPO_TIPO);
        db.execSQL(Utilidades.CREAR_TABLA_CAMPO_ZONA);
        db.execSQL(Utilidades.CREAR_TABLA_LISTA_DISPONIBILIDAD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {

        db.execSQL(Utilidades.ACTUALIZAR_TABLA_LISTA_TORNEOS);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_LISTA_RESERVA);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_LISTA_BUSQUEDA);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_MAPA);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_CAMPO_HORA);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_CAMPO_TIPO);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_CAMPO_ZONA);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_LISTA_DISPONIBILIDAD);

        onCreate(db);
    }
}