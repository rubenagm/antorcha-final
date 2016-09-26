package com.mx.antorcha.GCM;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;
import com.mx.antorcha.Notificaciones.Notificacion;

/**
 *
 */
public class ServicioGCM extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {

        com.mx.antorcha.Modelos.Notificacion notificacion = new com.mx.antorcha.Modelos.Notificacion();

        Log.i("GCM-Google JSON", data.toString());
        notificacion.setTexto(data.getString("mensaje"));
        notificacion.setTitulo(data.getString("titulo"));
        notificacion.setActivity(data.getString("activity"));
        notificacion.setAccion(data.getString("accion"));
        notificacion.setId(data.getString("identificador"));
        notificacion.setIcono(data.getString("icono"));
        //deja Log.i("GCM-Google", data.toString());
        Notificacion.normal(this, notificacion);

    }
}
