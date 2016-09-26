package com.mx.antorcha.BaseDatos;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mx.antorcha.Modelos.Deporte;
import com.mx.antorcha.Modelos.Disciplina;
import com.mx.antorcha.Modelos.EspacioDeportivo;
import com.mx.antorcha.Modelos.Evento;
import com.mx.antorcha.Modelos.Medalla;
import com.mx.antorcha.Modelos.Meta;
import com.mx.antorcha.Modelos.MetaProgreso;
import com.mx.antorcha.Modelos.Pendiente;

import java.util.ArrayList;

/**
 *
 */
public class ConexionBaseDatosObtener extends SQLiteOpenHelper {

    public ConexionBaseDatosObtener(Context activity) {
        super(activity, "Antorcha", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Querys.CREAR_TABLA_METAS);
        db.execSQL(Querys.CREAR_TABLA_META_PROGRESO);
        db.execSQL(Querys.CREAR_TABLA_MEDALLAS);
        db.execSQL(Querys.CREAR_TABLA_DEPORTES);
        db.execSQL(Querys.CREAR_TABLA_DISCIPLINAS);
        db.execSQL(Querys.CREAR_TABLA_ESPACIOS);
        db.execSQL(Querys.CREAR_TABLA_EVENTO);
        db.execSQL(Querys.CREAR_TABLA_PENDIENTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Meta> obtenerMetas () {
        ArrayList<Meta> metas = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_METAS, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            metas.add(new Meta(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getDouble(cursor.getColumnIndex("Inicio")),
                    cursor.getDouble(cursor.getColumnIndex("Fin")),
                    cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getString(cursor.getColumnIndex("FechaFin")),
                    cursor.getString(cursor.getColumnIndex("FechaInicio")),
                    cursor.getString(cursor.getColumnIndex("TipoMedida")),
                    cursor.getInt(cursor.getColumnIndex(("IdServidor"))),
                    1
            ));

            cursor.moveToNext();
        }
        cursor.close();

        return metas;
    }

    public Meta obtenerMetas (int id) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_UNA_META + id, null);
        cursor.moveToFirst();

        Meta meta = new Meta(
                cursor.getInt(cursor.getColumnIndex("Id")),
                cursor.getDouble(cursor.getColumnIndex("Inicio")),
                cursor.getDouble(cursor.getColumnIndex("Fin")),
                cursor.getString(cursor.getColumnIndex("Nombre")),
                cursor.getString(cursor.getColumnIndex("FechaFin")),
                cursor.getString(cursor.getColumnIndex("FechaInicio")),
                cursor.getString(cursor.getColumnIndex("TipoMedida")),
                cursor.getInt(cursor.getColumnIndex(("IdServidor"))),
                1
        );

        cursor.close();

        return meta;
    }

    public ArrayList<Meta> obtenerMetasEliminar () {
        ArrayList<Meta> metas = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_METAS_ELIMINAR, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            metas.add(new Meta(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getDouble(cursor.getColumnIndex("Inicio")),
                    cursor.getDouble(cursor.getColumnIndex("Fin")),
                    cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getString(cursor.getColumnIndex("FechaFin")),
                    cursor.getString(cursor.getColumnIndex("FechaInicio")),
                    cursor.getString(cursor.getColumnIndex("TipoMedida")),
                    cursor.getInt(cursor.getColumnIndex(("IdServidor"))),
                    1
            ));

            cursor.moveToNext();
        }
        cursor.close();

        return metas;
    }

    public ArrayList<MetaProgreso> obtenerMetaProgreso (int idMeta) {
        ArrayList<MetaProgreso> metaProgresos = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_METAS_PROGRESO + idMeta, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            metaProgresos.add(new MetaProgreso(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getInt(cursor.getColumnIndex("IdMeta")),
                    cursor.getDouble(cursor.getColumnIndex("Progreso")),
                    cursor.getString(cursor.getColumnIndex("Fecha")),
                    cursor.getInt(cursor.getColumnIndex("IdServidor"))));

            cursor.moveToNext();
        }
        cursor.close();

        return metaProgresos;
    }

    public ArrayList<Medalla> obtenerMedallas () {
        ArrayList<Medalla> medallas = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_MEDALLAS, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            medallas.add(new Medalla(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getInt(cursor.getColumnIndex("Tipo")),
                    cursor.getString(cursor.getColumnIndex("Descripcion")),
                    cursor.getString(cursor.getColumnIndex("ComoSeLogra")),
                    cursor.getString(cursor.getColumnIndex("Imagen"))
            ));

            cursor.moveToNext();
        }
        cursor.close();

        return medallas;
    }

    public Medalla obtenerMedallas (int id) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_UNA_MEDALLA + id, null);
        cursor.moveToFirst();

        Medalla medalla =  new Medalla(
                cursor.getInt(cursor.getColumnIndex("Id")),
                cursor.getString(cursor.getColumnIndex("Nombre")),
                cursor.getInt(cursor.getColumnIndex("Tipo")),
                cursor.getString(cursor.getColumnIndex("Descripcion")),
                cursor.getString(cursor.getColumnIndex("ComoSeLogra")),
                cursor.getString(cursor.getColumnIndex("Imagen"))
        );
        cursor.close();

        return medalla;
    }


    //Se obtienen todas las disciplinas
    public ArrayList<Disciplina> obtenerDisciplinas () {
        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_DISCIPLINAS, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            disciplinas.add(new Disciplina(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getString(cursor.getColumnIndex("Descripcion")),
                    ""
            ));

            cursor.moveToNext();
        }
        cursor.close();

        return disciplinas;
    }

    //Se obtienen todos los deportes
    public ArrayList<Deporte> obtenerDeportes () {
        ArrayList<Deporte> deportes = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_DEPORTES, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            deportes.add(new Deporte(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getInt(cursor.getColumnIndex("Disciplina")),
                    cursor.getString(cursor.getColumnIndex("Imagen"))
            ));

            cursor.moveToNext();
        }
        cursor.close();

        return deportes;
    }

    //Se obtienen los deportes por disciplina
    public ArrayList<Deporte> obtenerDeportes (int disciplina) {
        ArrayList<Deporte> deportes = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_DEPORTE_POR_DISCPLINA + disciplina, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            deportes.add(new Deporte(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getInt(cursor.getColumnIndex("Disciplina")),
                    cursor.getString(cursor.getColumnIndex("Imagen"))
            ));

            cursor.moveToNext();
        }
        cursor.close();

        return deportes;
    }

    public ArrayList<Evento> obtenerEventos(){
        ArrayList<Evento> eventos = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_EVENTOS, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            eventos.add(new Evento(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getString(cursor.getColumnIndex("Descripcion")),
                    cursor.getString(cursor.getColumnIndex("Domicilio")),
                    cursor.getString(cursor.getColumnIndex("Colonia")),
                    cursor.getString(cursor.getColumnIndex("CodigoPostal")),
                    cursor.getString(cursor.getColumnIndex("Municipio")),
                    cursor.getString(cursor.getColumnIndex("Ciudad")),
                    cursor.getString(cursor.getColumnIndex("Estado")),
                    cursor.getString(cursor.getColumnIndex("Telefono")),
                    cursor.getString(cursor.getColumnIndex("FechaInicio")),
                    cursor.getString(cursor.getColumnIndex("FechaFin")),
                    cursor.getDouble(cursor.getColumnIndex("Latitud")),
                    cursor.getDouble(cursor.getColumnIndex("Longitud"))
                    ));

            cursor.moveToNext();
        }
        cursor.close();

        return eventos;
    }

    public Evento obtenerUnEvento (int idEvento) {
        Evento evento;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_UN_EVENTO + idEvento, null);
        cursor.moveToFirst();

        evento = new Evento(
                cursor.getInt(cursor.getColumnIndex("Id")),
                cursor.getString(cursor.getColumnIndex("Nombre")),
                cursor.getString(cursor.getColumnIndex("Descripcion")),
                cursor.getString(cursor.getColumnIndex("Domicilio")),
                cursor.getString(cursor.getColumnIndex("Colonia")),
                cursor.getString(cursor.getColumnIndex("CodigoPostal")),
                cursor.getString(cursor.getColumnIndex("Municipio")),
                cursor.getString(cursor.getColumnIndex("Ciudad")),
                cursor.getString(cursor.getColumnIndex("Estado")),
                cursor.getString(cursor.getColumnIndex("Telefono")),
                cursor.getString(cursor.getColumnIndex("FechaInicio")),
                cursor.getString(cursor.getColumnIndex("FechaFin")),
                cursor.getDouble(cursor.getColumnIndex("Latitud")),
                cursor.getDouble(cursor.getColumnIndex("Longitud"))
        );

        cursor.moveToNext();

        cursor.close();

        return evento;
    }

    public ArrayList<EspacioDeportivo> obtenerEspacios () {
        ArrayList<EspacioDeportivo> espacioDeportivos = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_ESPACIOS_DEPORTIVOS, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            espacioDeportivos.add(new EspacioDeportivo(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getString(cursor.getColumnIndex("Descripcion")),
                    cursor.getString(cursor.getColumnIndex("Domicilio")),
                    cursor.getString(cursor.getColumnIndex("Colonia")),
                    cursor.getString(cursor.getColumnIndex("CodigoPostal")),
                    cursor.getString(cursor.getColumnIndex("Municipio")),
                    cursor.getString(cursor.getColumnIndex("Ciudad")),
                    cursor.getString(cursor.getColumnIndex("Estado")),
                    cursor.getString(cursor.getColumnIndex("Telefono")),
                    cursor.getDouble(cursor.getColumnIndex("Latitud")),
                    cursor.getDouble(cursor.getColumnIndex("Longitud")),
                    cursor.getString(cursor.getColumnIndex("Valoracion")),
                    cursor.getString(cursor.getColumnIndex("Horario"))));

            cursor.moveToNext();
        }
        cursor.close();

        return espacioDeportivos;
    }

    public EspacioDeportivo obtenerUnEspacio (int idEspacio) {
        EspacioDeportivo espacioDeportivo;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_UN_ESPACIO + idEspacio, null);
        cursor.moveToFirst();

        espacioDeportivo = new EspacioDeportivo(
                cursor.getInt(cursor.getColumnIndex("Id")),
                cursor.getString(cursor.getColumnIndex("Nombre")),
                cursor.getString(cursor.getColumnIndex("Descripcion")),
                cursor.getString(cursor.getColumnIndex("Domicilio")),
                cursor.getString(cursor.getColumnIndex("Colonia")),
                cursor.getString(cursor.getColumnIndex("CodigoPostal")),
                cursor.getString(cursor.getColumnIndex("Municipio")),
                cursor.getString(cursor.getColumnIndex("Ciudad")),
                cursor.getString(cursor.getColumnIndex("Estado")),
                cursor.getString(cursor.getColumnIndex("Telefono")),
                cursor.getDouble(cursor.getColumnIndex("Latitud")),
                cursor.getDouble(cursor.getColumnIndex("Longitud")),
                cursor.getString(cursor.getColumnIndex("Valoracion")),
                cursor.getString(cursor.getColumnIndex("Horario")));
        cursor.close();

        return espacioDeportivo;
    }

    public ArrayList<Pendiente> obtenerPendientes () {
        ArrayList<Pendiente> pendientes = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Querys.OBTENER_PENDIENTES, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            pendientes.add( new Pendiente(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getString(cursor.getColumnIndex("Tipo")),
                    cursor.getString(cursor.getColumnIndex("Datos"))));
            cursor.moveToNext();
        }

        return pendientes;
    }

}
