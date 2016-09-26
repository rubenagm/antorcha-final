package com.mx.antorcha.Servicios;

import android.app.IntentService;
import android.content.Intent;

import com.mx.antorcha.BaseDatos.CargarDeportesDisciplinas;
import com.mx.antorcha.Conexion.ConexionDescargarMetas;
import com.mx.antorcha.Conexion.ConexionMedallas;
import com.mx.antorcha.Conexion.ConexionMetas;
import com.mx.antorcha.Conexion.ConexionObtenerDeporte;
import com.mx.antorcha.Conexion.ConexionObtenerDisciplina;
import com.mx.antorcha.Conexion.ConexionObtenerMedallas;
import com.mx.antorcha.Conexion.ConexionObtenerMiembroEspacio;
import com.mx.antorcha.Conexion.ConexionObtenerMiembroEvento;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

/**
 * Created by Rubén on 25/03/16.
 */
public class ServicioDescarga extends IntentService {

    public ServicioDescarga() {
        super("ServicioDescarga");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //Se obtienen las medallas
        try {
            synchronized ("ServicioDescarga") {
                ConexionObtenerMedallas conexionObtenerMedallas = new ConexionObtenerMedallas(this);
                conexionObtenerMedallas.obtenerMedallas();

                //Se obtienen las medallas
                ConexionMedallas conexionMedallas = new ConexionMedallas(this);
                conexionMedallas.obtenerMedallas();

                //Se descargan las metas y dentro se descarga su progreso
                ConexionDescargarMetas conexionDescargarMetas = new ConexionDescargarMetas(this);
                conexionDescargarMetas.obtenerMetas();

                //Se descargan los espacios para mis actividades
                ConexionObtenerMiembroEspacio conexionObtenerMiembroEspacio = new ConexionObtenerMiembroEspacio(this);
                conexionObtenerMiembroEspacio.obtenerMiembroEspacio();

                //Se descargan los eventos para mis actividades
                ConexionObtenerMiembroEvento conexionObtenerMiembroEvento = new ConexionObtenerMiembroEvento(this);
                conexionObtenerMiembroEvento.obtenerEventos();

                //Se descargan las disciplinas del miembro
                ConexionObtenerDisciplina conexionObtenerDisciplina = new ConexionObtenerDisciplina(this);
                conexionObtenerDisciplina.obtenerDisciplinas();

                //Se descargan los deportes del miembro
                ConexionObtenerDeporte conexionObtenerDeporte = new ConexionObtenerDeporte(this);
                conexionObtenerDeporte.obtenerDeportes();

                CargarDeportesDisciplinas.cargarDisciplinas(this);
                CargarDeportesDisciplinas.cargarDeportes(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
