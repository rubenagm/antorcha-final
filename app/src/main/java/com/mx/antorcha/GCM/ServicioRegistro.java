package com.mx.antorcha.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

/**

 */
public class ServicioRegistro extends IntentService{

    public ServicioRegistro() {
        super("registro");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            synchronized ("registro") {
                // Initially a network call, to retrieve the token, subsequent calls are local.
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken("906946575566", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.i("GCM", "GCM Registration Token: " + token);

                if (token.length() > 1) {
                    MiembroSharedPreferences miembroSharedPreferences = new MiembroSharedPreferences(this);
                    miembroSharedPreferences.setRegistrado(1);
                    miembroSharedPreferences.setGCM(token);
                }
            }
        } catch (Exception e) {
            Log.d("Error", "Failed to complete token refresh", e);

        }
    }
}
