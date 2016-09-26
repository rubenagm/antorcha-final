package com.mx.antorcha.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.R;

public class NuevaNotificacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_notificacion);

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.nueva_notificacion_toolbar);
        setSupportActionBar(toolbar);

        //el click en guardar
        ImageView imageViewGuardar = (ImageView) findViewById(R.id.nueva_notificacion_aceptar);
        AdaptadorSVG.mostrarImagen(imageViewGuardar, this, R.raw.icono_guardar_palomita);

        imageViewGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NuevaNotificacion.this, BuscarActividad.class);
                startActivity(intent);
            }
        });

        //se cargan los datos
        TextView textViewTitulo = (TextView) findViewById(R.id.nueva_notificacion_titulo);
        textViewTitulo.setText(getIntent().getExtras().getString("titulo"));

        TextView textViewContenido = (TextView) findViewById(R.id.nueva_notificacion_contenido);
        textViewContenido.setText(getIntent().getExtras().getString("contenido"));


    }
}
