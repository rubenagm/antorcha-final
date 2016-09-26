package com.mx.antorcha.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.Conexion.ConexionMiembroEvento;
import com.mx.antorcha.Conexion.DescargarImagen;
import com.mx.antorcha.Conexion.InfoConexion;
import com.mx.antorcha.Modelos.Evento;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetalleEvento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento);

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.detalle_evento_toolbar);
        setSupportActionBar(toolbar);

        //Se carga la imagen para el regresar
        ImageView imageViewRegresar = (ImageView) findViewById(R.id.detalle_evento_regresar);
        AdaptadorSVG.mostrarImagen(imageViewRegresar, this, R.raw.icono_regresar);

        imageViewRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Se obtienen los datos del evento en un String array
        String[] eventoString = getIntent().getExtras().getStringArray("Evento");

        final Evento evento = new Evento (
                Integer.parseInt(eventoString[0]),
                eventoString[1],
                eventoString[2],
                eventoString[3],
                eventoString[4],
                eventoString[5],
                eventoString[6],
                eventoString[7],
                eventoString[8],
                eventoString[9],
                eventoString[10],
                "",
                Double.parseDouble(eventoString[12]),
                Double.parseDouble(eventoString[13]));
        //Se cargan las imagenes de los icono de contacto
        ImageView imageViewCompartir = (ImageView) findViewById(R.id.sliding_buscar_actividades_evento_compartir);
        ImageView imageViewContacto = (ImageView) findViewById(R.id.sliding_buscar_actividades_evento_contacto);
        ImageView imageViewBotonAsistoEvento = (ImageView) findViewById(R.id.sliding_buscar_evento_asisto_evento);

        AdaptadorSVG.mostrarImagen(imageViewCompartir, this, R.raw.icono_compartir);
        AdaptadorSVG.mostrarImagen(imageViewContacto, this, R.raw.icono_llamada);
        AdaptadorSVG.mostrarImagen(imageViewBotonAsistoEvento, this, R.raw.boton_agregar_calendario);

        TextView textViewNombre = (TextView) findViewById(R.id.sliding_buscar_evento_nombre_principal);
        TextView textViewFecha = (TextView) findViewById(R.id.sliding_buscar_evento_fecha_evento);
        TextView textViewCantidadPersonas = (TextView) findViewById(R.id.sliding_buscar_evento_cantidad_personas);
        TextView textViewLugar = (TextView) findViewById(R.id.sliding_buscar_evento_lugar);
        TextView textViewDescripcion = (TextView) findViewById(R.id.sliding_buscar_evento_descripcion);

        //Se muestra la información;
        textViewNombre.setText(evento.getNombre());
        textViewFecha.setText(evento.getFechaInicio());
        textViewCantidadPersonas.setText(10 + "");
        textViewLugar.setText(evento.getDomicilio());
        textViewDescripcion.setText(evento.getDescripcion());

        //Imagen principal
        ImageView imageViewImagenPrincipal = (ImageView) findViewById(R.id.sliding_buscar_espacio_imagen_principal);
        imageViewImagenPrincipal.setImageResource(R.drawable.default_evento);
        DescargarImagen.imagenGuardada(this, "evento_" + evento.getId() + ".png", imageViewImagenPrincipal, InfoConexion.URL_DESCARGAR_IMAGEN_EVENTO + evento.getId() + "_1_.png");

        //Imagen secundaria
        ImageView imageViewSecundaria = (ImageView) findViewById(R.id.sliding_imagen_evento);
        imageViewSecundaria.setImageResource(R.drawable.default_evento);
        DescargarImagen.imagenGuardada(this, "evento_" + evento.getId() + ".png", imageViewSecundaria, InfoConexion.URL_DESCARGAR_IMAGEN_EVENTO + evento.getId() + "_2_.png");
        //Se pone el click de la llamada
        imageViewContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + evento.getTelefono()));
                startActivity(intent);
            }
        });

        imageViewCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleEvento.this, Compartir.class);
                intent.putExtra("id", evento.getId());
                intent.putExtra("tipo", "evento");

                startActivity(intent);
            }
        });

        //El click de l botón de agregar evento
        imageViewBotonAsistoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConexionBaseDatosInsertar(DetalleEvento.this).insertarEvento(evento);
                ConexionMiembroEvento conexionMiembroEvento
                        = new ConexionMiembroEvento(DetalleEvento.this,
                        new MiembroSharedPreferences(DetalleEvento.this).getId(),
                        evento.getId(),
                        0);
                conexionMiembroEvento.insertarEvento();

                new AlertDialog.Builder(DetalleEvento.this).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cuando se responde sí se agrega al calendario
                        long startTime = 1;
                        long endTime = 1;

                        String startDate = evento.getFechaInicio();
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                            startTime=date.getTime();
                        }
                        catch(Exception e){ }

                        Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        //intent.putExtra("beginTime",startTime);
                        intent.putExtra("allDay", true);
                        intent.putExtra("rrule", "FREQ=YEARLY");
                        //intent.putExtra("endTime", endTime);
                        intent.putExtra("title", evento.getNombre());
                        startActivity(intent);
                    }
                }).setNegativeButton("No", null)
                        .setIcon(R.drawable.logo_antorcha)
                        .setMessage("¿Quieres agregar el evento a tu calendario?")
                        .setTitle("Asistir a evento")
                        .show();
                //Toast.makeText(activity, evento.getNombre(), Toast.LENGTH_LONG).show();
            }
        });


    }
}
