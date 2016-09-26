package com.mx.antorcha.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mx.antorcha.Bienvenida.Bienvenida;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        EsperaSplashScreen esperaSplashScreen = new EsperaSplashScreen(this);
        esperaSplashScreen.execute();
    }

    class EsperaSplashScreen extends AsyncTask<Void, Void, Void> {

        private Activity activity;

        public EsperaSplashScreen (Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v){

            //Se comprueba si ya se ha iniciado sesión
            MiembroSharedPreferences miembroSharedPreferences = new MiembroSharedPreferences(activity);

            if (miembroSharedPreferences.getId() != 0) {
                Intent intent = new Intent(activity, BuscarActividad.class);
                activity.startActivity(intent);
                activity.finish();
            } else {
                Intent intent = new Intent(activity, Inicio.class);
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }

}