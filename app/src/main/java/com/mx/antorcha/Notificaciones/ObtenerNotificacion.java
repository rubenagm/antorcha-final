package com.mx.antorcha.Notificaciones;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mx.antorcha.Activities.BuscarActividad;
import com.mx.antorcha.Activities.Medallas;
import com.mx.antorcha.Activities.NuevaNotificacion;
import com.mx.antorcha.Modelos.Notificacion;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MedallasSharedPreferences;

/**
 *
 */
public class ObtenerNotificacion {

    static public PendingIntent obtenerPending (com.mx.antorcha.Modelos.Notificacion notificacion, Context context) {
        switch (notificacion.getAccion()) {
            case "espacio" : {
                Intent intent = new Intent(context, BuscarActividad.class);
                intent.putExtra("id", notificacion.getId());
                intent.putExtra("tipo", "espacio");

                return PendingIntent.getActivity(context, 0, intent, 0);
            }
            case "evento" : {
                Intent intent = new Intent(context, BuscarActividad.class);
                intent.putExtra("id", notificacion.getId());
                intent.putExtra("tipo", "evento");

                return PendingIntent.getActivity(context, 0, intent, 0);
            }
            case "medalla" : {
                MedallasSharedPreferences medallasSharedPreferences
                        = new MedallasSharedPreferences(context);

                if (medallasSharedPreferences.obtenerMedallas().contains(notificacion.getId())) {
                    //Si ya se contiene no se puede volver a insertar la medalla
                } else {
                    medallasSharedPreferences.agregarMedalla(Integer.parseInt(notificacion.getId()));
                }
                Intent intent = new Intent(context, Medallas.class);
                intent.putExtra("id", notificacion.getId());

                return PendingIntent.getActivity(context, 0, intent, 0);
            }
            case "notificacion" : {
                Intent intent = new Intent(context, NuevaNotificacion.class);
                intent.putExtra("titulo", notificacion.getTitulo());
                intent.putExtra("contenido", notificacion.getTexto());

                return PendingIntent.getActivity(context, 0, intent, 0);
            }
            default : {
                return null;
            }
        }
    }

    static public int obtenerIcono(Notificacion notificacion){

        switch (notificacion.getIcono()) {
            case "medalla" : {
                return R.drawable.notificacion_medalla;
            }
            case "espacio" : {
                return R.drawable.notificacion_espacio_deportivo;
            }
            case "evento" : {
                return R.drawable.notificacion_evento;
            }
            default : {
                return R.drawable.notificacion_evento;
            }
        }
    }
}
