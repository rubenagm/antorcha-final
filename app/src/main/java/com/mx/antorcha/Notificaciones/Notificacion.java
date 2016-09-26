package com.mx.antorcha.Notificaciones;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.mx.antorcha.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class Notificacion {

    static public void simple (Activity activity, com.mx.antorcha.Modelos.Notificacion notificacion) {

        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity);
        builder.setContentTitle(notificacion.getTitulo());
        builder.setContentText(notificacion.getTexto());
        builder.setContentIntent(pendingIntent);
        builder.setVibrate(new long[] {500, 500, 500, 500, 500});

        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(1, builder.build());
    }

    static public void normal (Context context, com.mx.antorcha.Modelos.Notificacion notificacion) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(notificacion.getTitulo());
        builder.setContentText(notificacion.getTexto());
        builder.setContentIntent(ObtenerNotificacion.obtenerPending(notificacion, context));
        builder.setSmallIcon(ObtenerNotificacion.obtenerIcono(notificacion));
        builder.setVibrate(new long[] {500, 500, 500, 500, 500});

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, builder.build());
    }

    static public com.mx.antorcha.Modelos.Notificacion obtenerObjeto (String json) {
        com.mx.antorcha.Modelos.Notificacion notificacion = new com.mx.antorcha.Modelos.Notificacion();
        try {
            JSONObject jsonObject = new JSONObject(json);
            notificacion.setTexto(jsonObject.getString("mensaje"));
            notificacion.setTitulo(jsonObject.getString("titulo"));
            notificacion.setAccion(jsonObject.getString("accion"));
            notificacion.setIcono(jsonObject.getString("icono"));
            notificacion.setId(jsonObject.getString("identificador"));
            notificacion.setActivity(jsonObject.getString("activity"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return notificacion;
    }
}
