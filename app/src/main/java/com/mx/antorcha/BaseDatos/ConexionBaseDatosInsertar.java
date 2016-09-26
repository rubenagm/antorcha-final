package com.mx.antorcha.BaseDatos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
public class ConexionBaseDatosInsertar extends SQLiteOpenHelper {
    private Context activity;

    public ConexionBaseDatosInsertar(Context activity) {
        super(activity, "Antorcha", null, 1);

        this.activity = activity;
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
        //NADA HASTA AHORITA
    }

    //Se insertan datos en la meta
    public long insertarMeta(Meta meta){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues   = new ContentValues();

        contentValues.put("Nombre", meta.getNombre());
        contentValues.put("Inicio", meta.getInicio());
        contentValues.put("Fin", meta.getFin());
        contentValues.put("FechaInicio", meta.getFechaInicio());
        contentValues.put("FechaFin", meta.getFechaFin());
        contentValues.put("TipoMedida", meta.getTipoMedida());
        contentValues.put("IdServidor", meta.getIdServidor());
        contentValues.put("Estado", 1);

        long id = sqLiteDatabase.insert("Meta", null, contentValues);

        Log.i(Querys.TAG_INSERTAR, "Se ha insertado una meta");

        return id;
    }

    //Se insertan datos en la meta
    public long insertarMetaProgreso(MetaProgreso metaProgreso){

        ArrayList<MetaProgreso> metaProgresos =
                new ConexionBaseDatosObtener(activity).obtenerMetaProgreso(metaProgreso.getIdMeta());

        long id = -1;
        ArrayList<Integer> enteros = new ArrayList<>();

        for (MetaProgreso metaProgreso1 : metaProgresos) {
            enteros.add(metaProgreso1.getIdServidor());
        }

        if (metaProgreso.getIdServidor() == 0 || (!enteros.contains(metaProgreso.getIdServidor()))) {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues   = new ContentValues();

            contentValues.put("IdMeta", metaProgreso.getIdMeta());
            contentValues.put("Progreso",metaProgreso.getProgreso());
            contentValues.put("Fecha",metaProgreso.getFecha());
            contentValues.put("IdServidor", metaProgreso.getIdServidor());

            id = sqLiteDatabase.insert("MetaProgreso", null, contentValues);

            Log.i(Querys.TAG_INSERTAR, "Se ha insertado una meta progreso");


        }

        return id;

    }

    //Se insertan las medallas
    public void insertarMedalla (Medalla medalla) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues   = new ContentValues();

        contentValues.put("Id", medalla.getId());
        contentValues.put("Nombre",medalla.getNombre());
        contentValues.put("Tipo", medalla.getTipo());
        contentValues.put("Descripcion", medalla.getDescripcion());
        contentValues.put("ComoSeLogra", medalla.getComoSeLogra());
        contentValues.put("Imagen", medalla.getImagen());

        sqLiteDatabase.insert("Medalla", null, contentValues);

        Log.i(Querys.TAG_INSERTAR, "Se ha insertado una medalla");
    }

    //Se inserta una disciplina
    public void insertarDisciplina (Disciplina disciplina) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues   = new ContentValues();

        contentValues.put("Id", disciplina.getId());
        contentValues.put("Nombre",disciplina.getNombre());
        contentValues.put("Descripcion", disciplina.getDescripcion());

        sqLiteDatabase.insert("Disciplina", null, contentValues);

        Log.i(Querys.TAG_INSERTAR, "Se ha insertado una disciplina");
    }

    //Se inserta un deporte
    public void insertarDeporte (Deporte deporte) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues   = new ContentValues();

        contentValues.put("Id", deporte.getId());
        contentValues.put("Nombre",deporte.getNombre());
        contentValues.put("Disciplina", deporte.getDisciplina());
        contentValues.put("Imagen", deporte.getImagen());

        sqLiteDatabase.insert("Deporte", null, contentValues);

        Log.i(Querys.TAG_INSERTAR, "Se ha insertado un Deporte");
    }

    public void insertarEvento (Evento evento) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues   = new ContentValues();

        contentValues.put("Id", evento.getId());
        contentValues.put("Nombre", evento.getNombre());
        contentValues.put("Descripcion", evento.getDescripcion());
        contentValues.put("Domicilio", evento.getDomicilio());
        contentValues.put("Colonia", evento.getColonia());
        contentValues.put("Ciudad", evento.getCiudad());
        contentValues.put("CodigoPostal", evento.getCodigoPostal());
        contentValues.put("Estado", evento.getEstado());
        contentValues.put("Municipio", evento.getMunicipio());
        contentValues.put("Telefono", evento.getTelefono());
        contentValues.put("FechaInicio", evento.getFechaInicio());
        contentValues.put("FechaFin", evento.getFechaFin());
        contentValues.put("Latitud", evento.getLatitud());
        contentValues.put("Longitud", evento.getLongitud());

        sqLiteDatabase.insert("Evento", null, contentValues);

        Log.i(Querys.TAG_INSERTAR, "Se ha insertado un Evento");
    }


    public void insertarEspacioDeportivo (EspacioDeportivo espacioDeportivo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues   = new ContentValues();

        contentValues.put("Id", espacioDeportivo.getId());
        contentValues.put("Nombre", espacioDeportivo.getNombre());
        contentValues.put("Descripcion", espacioDeportivo.getDescripcion());
        contentValues.put("Domicilio", espacioDeportivo.getDomicilio());
        contentValues.put("Colonia", espacioDeportivo.getColonia());
        contentValues.put("Ciudad", espacioDeportivo.getCiudad());
        contentValues.put("CodigoPostal", espacioDeportivo.getCodigoPostal());
        contentValues.put("Estado", espacioDeportivo.getEstado());
        contentValues.put("Municipio", espacioDeportivo.getMunicipio());
        contentValues.put("Telefono", espacioDeportivo.getTelefono());
        contentValues.put("Latitud", espacioDeportivo.getLatitud());
        contentValues.put("Longitud", espacioDeportivo.getLongitud());
        contentValues.put("Valoracion", espacioDeportivo.getValoracion());
        contentValues.put("Horario", espacioDeportivo.getHorario());

        sqLiteDatabase.insert("EspacioDeportivo", null, contentValues);

        Log.i(Querys.TAG_INSERTAR, "Se ha insertado un Espacio deportivo");
    }

    public void insertarPendiente (Pendiente pendiente) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues   = new ContentValues();

        contentValues.put("Tipo", pendiente.getTipo());
        contentValues.put("Datos", pendiente.getDatos());

        sqLiteDatabase.insert("Pendiente", null, contentValues);

        Log.i(Querys.TAG_INSERTAR, "Se ha insertado un Pendiente");
    }

    /*
    Función que inserta un pendiente de sincronizacion

     */

}
