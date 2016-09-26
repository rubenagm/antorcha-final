package com.mx.antorcha.Activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorListaAyudanosMejorar;
import com.mx.antorcha.MenuDrawer.AdapterDrawer;
import com.mx.antorcha.R;

import java.util.ArrayList;

public class AyudanosMejorar extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayudanos_mejorar);

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.ayudanos_mejorar_toolbar);
        setSupportActionBar(toolbar);

        //Se agrega el drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView) findViewById(R.id.lista_drawer);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        listView.setAdapter(new AdapterDrawer(this, R.layout.drawer, arrayList, "AyudanosMejorar"));

        //Se carga el drawer
        ImageView imageViewDrawer = (ImageView) findViewById(R.id.ayudanos_mejorar_barra_drawer);
        AdaptadorSVG.mostrarImagen(imageViewDrawer, this, R.raw.icono_menu_drawer);

        imageViewDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        ListView listView = (ListView) findViewById(R.id.ayudanos_mejorar_lista);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Problemas con la aplicación");
        strings.add("Cierre inesperado");
        strings.add("Error de funcionamiento");
        strings.add("Error con mis datos");
        strings.add("La aplicación dejó de funcionar");
        strings.add("Espacios deportivos");
        strings.add("Dar de alta un espacio deportivo");
        strings.add("Corregir datos de un espacio deportivo");
        strings.add("No encuentro un espacio deportivo");
        strings.add("No encuentro mi deporte favorito");
        strings.add("Sugerencias");
        strings.add("Quiero dar una nueva idea a Antorcha");
        strings.add("Quiero felicitar al equipo");
        strings.add("Quiero sugerir algunas mejoras");

        AdaptadorListaAyudanosMejorar adaptadorListaAyudanosMejorar = new AdaptadorListaAyudanosMejorar(this, strings);
        listView.setAdapter(adaptadorListaAyudanosMejorar);

    }
}
