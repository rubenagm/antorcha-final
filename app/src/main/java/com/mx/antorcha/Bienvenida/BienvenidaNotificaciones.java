package com.mx.antorcha.Bienvenida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mx.antorcha.Activities.BuscarActividad;
import com.mx.antorcha.R;

public class BienvenidaNotificaciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida_notificaciones);

        findViewById(R.id.bienvenida_notificaciones_siguiente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BienvenidaNotificaciones.this, BuscarActividad.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
