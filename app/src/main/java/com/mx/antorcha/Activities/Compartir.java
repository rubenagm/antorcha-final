package com.mx.antorcha.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

public class Compartir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir);

        ImageView imageViewInvitar = (ImageView) findViewById(R.id.compartir_boton_compartir);
        AdaptadorSVG.mostrarImagen(imageViewInvitar, this, R.raw.boton_compartir_codigo);

        //se carga el boton para atrás
        ImageView imageViewAtras = (ImageView) findViewById(R.id.compartir_atras);
        AdaptadorSVG.mostrarImagen(imageViewAtras, this, R.raw.icono_regresar);

        int id = getIntent().getExtras().getInt("id");
        int tipo;
        int miembro = new MiembroSharedPreferences(this).getId();

        //Se forma el código de invitación
        if (getIntent().getExtras().getString("tipo").equals("espacio")) {
            tipo = 1;
        } else {
            tipo = 2;
        }

        final String codigo = generarCodigo(id, miembro, tipo);

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView textView = (TextView) findViewById(R.id.compartir_codigo_invitacion);
        textView.setText(codigo);

        imageViewInvitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout relativeLayoutCompartir = (RelativeLayout) findViewById(R.id.compartir_layout_compartir);
                com.mx.antorcha.Compartir.Compartir compartir = new com.mx.antorcha.Compartir.Compartir(Compartir.this);
                compartir.agregarView(relativeLayoutCompartir);
                compartir.agregarTexto("antorcha.com.mx/" + codigo);
                compartir.compartir();
            }
        });
    }

    public String generarCodigo (int id, int miembro, int tipo) {
        String codigo = "";
        codigo = Integer.toHexString(id);

        while (codigo.length() < 4) {
            codigo = "0" + codigo;
        }
        //Hasta este punto codigo mide 4

        codigo = codigo + tipo;

        //Hasta este punto codigo mide 5

        codigo = codigo + Integer.toHexString(miembro);

        return codigo.toUpperCase();
    }
}
