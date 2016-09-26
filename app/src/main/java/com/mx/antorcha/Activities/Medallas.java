package com.mx.antorcha.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorListaMedallas;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Compartir.*;
import com.mx.antorcha.Compartir.Compartir;
import com.mx.antorcha.Conexion.ConexionActualizarPerfil;
import com.mx.antorcha.Conexion.ConexionMedallas;
import com.mx.antorcha.Conexion.ConexionObtenerMedallas;
import com.mx.antorcha.Conexion.DescargarImagen;
import com.mx.antorcha.GCM.ServicioRegistro;
import com.mx.antorcha.MenuDrawer.AdapterDrawer;
import com.mx.antorcha.Modelos.Medalla;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MedallasSharedPreferences;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import java.util.ArrayList;

public class Medallas extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medallas);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Se inicializa el shared preferences
        MiembroSharedPreferences miembroSharedPreferences = new MiembroSharedPreferences(this);

        //Se comprueba si está actualizado el perfil en la nube
        if (miembroSharedPreferences.getActualizar() == 1 || miembroSharedPreferences.getRegistrado() == 1) {

            ConexionActualizarPerfil.actualizar(miembroSharedPreferences.getNombre(),
                    miembroSharedPreferences.getFechaNacimiento(),
                    miembroSharedPreferences.getDescripcion(),
                    miembroSharedPreferences.getIntereses(),
                    this, miembroSharedPreferences.getGCM());
        }

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.medallas_toolbar);
        setSupportActionBar(toolbar);

        //se carga el drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView) findViewById(R.id.lista_drawer);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        listView.setAdapter(new AdapterDrawer(this, R.layout.drawer, arrayList, "Medallas"));

        //Se muestra el boton del drawer
        ImageView imageViewDrawer = (ImageView) findViewById(R.id.medallas_barra_drawer);
        AdaptadorSVG.mostrarImagen(imageViewDrawer, this, R.raw.icono_menu_drawer);

        imageViewDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        /********** Agregar medallas para pruebas ***********/

        MedallasSharedPreferences medallasSharedPreferences = new MedallasSharedPreferences(this);
        //medallasSharedPreferences.agregarMedalla(3);
        /****************************************************/

        //Se obtienen los textview de las descripciones
        TextView textViewNombre = (TextView) findViewById(R.id.medallas_nombre_medalla);
        TextView textViewDescripcion = (TextView) findViewById(R.id.medallas_descripcion_medalla);
        TextView textViewTipo = (TextView) findViewById(R.id.medallas_tipo_medalla);

        //Se colocan las imagenes de las medallas
        ImageView imageViewFlechaIzquierda = (ImageView) findViewById(R.id.medallas_flecha_izquierda);
        ImageView imageViewFlechaDerecha = (ImageView) findViewById(R.id.medallas_flecha_derecha);
        ImageView imageViewMedallaPrincipal = (ImageView) findViewById(R.id.medallas_medalla_principal);

        //en caso de que se reciba la medalla se pone por defecto

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("id")) {
                Medalla medalla = new ConexionBaseDatosObtener(this).obtenerMedallas(Integer.parseInt(getIntent().getExtras().getString("id")));
                textViewNombre.setText(medalla.getNombre());
                textViewDescripcion.setText(medalla.getDescripcion());
                AdaptadorListaMedallas.mostrarMedalla(imageViewMedallaPrincipal, medalla.getTipo(), this , textViewTipo);
            }
        }

        ConexionBaseDatosObtener conexionBaseDatosObtener = new ConexionBaseDatosObtener(this);
        ArrayList<Medalla> medallas = conexionBaseDatosObtener.obtenerMedallas();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        //Se crea el recycler view para las medallas
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.medallas_lista_medallas);
        recyclerView.setLayoutManager(layoutManager);
        AdaptadorListaMedallas adaptadorListaMedallas = new AdaptadorListaMedallas(this, medallas);
        adaptadorListaMedallas.setTextViewNombre(textViewNombre);
        adaptadorListaMedallas.setTextViewDescripcion(textViewDescripcion);
        adaptadorListaMedallas.setTextViewTipo(textViewTipo);
        adaptadorListaMedallas.setImageViewMedallaPrincipal(imageViewMedallaPrincipal);
        recyclerView.setAdapter(adaptadorListaMedallas);

        AdaptadorSVG.mostrarImagen(imageViewFlechaDerecha, this, R.raw.icono_flecha_derecha);
        AdaptadorSVG.mostrarImagen(imageViewFlechaIzquierda, this, R.raw.icono_flecha_izquierda);

        //se coloca el boton de compartir
        ImageView imageViewCompartir = (ImageView) findViewById(R.id.medallas_compartir);
        AdaptadorSVG.mostrarImagen(imageViewCompartir, this, R.raw.icono_compartir);

        imageViewCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.mx.antorcha.Compartir.Compartir compartir = new Compartir(Medallas.this);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.medallas_layout_compartir);
                compartir.agregarView(linearLayout);
                compartir.agregarTexto("antorcha.com.");
                compartir.compartir();
            }
        });

        TextView textViewCantidadMedallas = (TextView) findViewById(R.id.medallas_cantidad_medallas);
        textViewCantidadMedallas.setText(medallasSharedPreferences.totalObtenidas() + "/" + medallas.size());

        //Se obtienen las medallas
        ConexionMedallas conexionMedallas = new ConexionMedallas(this);
        conexionMedallas.setRecyclerView(recyclerView);
        conexionMedallas.setTextView(textViewCantidadMedallas);
        conexionMedallas.obtenerMedallas();

        //Se cargan las medallas obtenidas
        TextView textViewPrometeo = (TextView) findViewById(R.id.medallas_cantidad_prometeo);
        TextView textViewAntorcha = (TextView) findViewById(R.id.medallas_cantidad_antorcha);
        TextView textViewFuego = (TextView) findViewById(R.id.medallas_cantidad_fuego);
        TextView textViewFlama = (TextView) findViewById(R.id.medallas_cantidad_flama);
        TextView textViewChispa = (TextView) findViewById(R.id.medallas_cantidad_chispa);

        for (Medalla medalla : medallas) {

            switch (medalla.getTipo()) {
                case 1 : {

                    if (medallasSharedPreferences.medallaObtenida(medalla.getId())) {
                        textViewPrometeo.setText((Integer.parseInt(textViewPrometeo.getText().toString()) + 1) + "");
                    }
                    break;
                }
                case 2 : {

                    if (medallasSharedPreferences.medallaObtenida(medalla.getId())) {
                        textViewAntorcha.setText((Integer.parseInt(textViewAntorcha.getText().toString()) + 1) + "");
                    }
                    break;
                }
                case 3 : {

                    if (medallasSharedPreferences.medallaObtenida(medalla.getId())) {
                        textViewFuego.setText((Integer.parseInt(textViewFuego.getText().toString()) + 1)+ "");
                    }
                    break;
                }
                case 4 : {

                    if (medallasSharedPreferences.medallaObtenida(medalla.getId())) {
                        textViewFlama.setText((Integer.parseInt(textViewFlama.getText().toString()) + 1) + "");
                    }
                    break;
                }
                case 5 : {

                    if (medallasSharedPreferences.medallaObtenida(medalla.getId())) {
                        textViewChispa.setText((Integer.parseInt(textViewChispa.getText().toString()) + 1) + "");
                    }
                    break;
                }
            }
        }

        ConexionObtenerMedallas conexionObtenerMedallas = new ConexionObtenerMedallas(Medallas.this);
        conexionObtenerMedallas.obtenerMedallas();
    }



}
