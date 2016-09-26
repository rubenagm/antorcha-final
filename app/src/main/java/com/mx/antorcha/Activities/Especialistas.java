package com.mx.antorcha.Activities;

import android.content.pm.ActivityInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorSpinner;
import com.mx.antorcha.Conexion.ConexionEspecialistas;
import com.mx.antorcha.MenuDrawer.AdapterDrawer;
import com.mx.antorcha.R;

import java.util.ArrayList;

public class Especialistas extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private ConexionEspecialistas conexionEspecialistas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialistas);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.especialistas_toolbar);
        setSupportActionBar(toolbar);

        //Se agrega el drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView) findViewById(R.id.lista_drawer);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        listView.setAdapter(new AdapterDrawer(this, R.layout.drawer, arrayList, "Especialistas"));

        //Se carga el drawer
        ImageView imageViewDrawer = (ImageView) findViewById(R.id.especialistas_barra_drawer);
        AdaptadorSVG.mostrarImagen(imageViewDrawer, this, R.raw.icono_menu_drawer);

        imageViewDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        //Se carga la lista de especialistas
        //se carga la parte del recycler view
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(Especialistas.this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_especialistas);
        recyclerView.setLayoutManager(layoutManager);

        //Se cargan los spinner
        Spinner spinnerMunicipio = (Spinner) findViewById(R.id.especialistas_spinner_municipio);
        final Spinner spinnerEspecialidad = (Spinner) findViewById(R.id.especialistas_spinner_especialidad);

        ArrayList<String> especialidades = new ArrayList<>();
        ArrayList<String> municipios = new ArrayList<>();

        especialidades.add("Nutriologos");
        especialidades.add("Fisioterapeutas");
        especialidades.add("Entrenadores");

        municipios.add("Guadalajara");
        municipios.add("Zapopan");
        municipios.add("Tlaquepaque");
        municipios.add("Tlajomulco");
        municipios.add("Tonala");

        AdaptadorSpinner adaptadorSpinnerEspecialidades = new AdaptadorSpinner(this, especialidades);
        AdaptadorSpinner adaptadorSpinnerMunicipios = new AdaptadorSpinner(this, municipios);

        spinnerEspecialidad.setAdapter(adaptadorSpinnerEspecialidades);
        spinnerMunicipio.setAdapter(adaptadorSpinnerMunicipios);

        //Se realiza la conexión con el web service
        conexionEspecialistas = new ConexionEspecialistas(this, recyclerView, spinnerMunicipio, spinnerEspecialidad);
        conexionEspecialistas.obtenerEspecialistas();

        //Se coloca el filtro de buscar especialistas
        spinnerEspecialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                conexionEspecialistas.obtenerEspecialistas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                conexionEspecialistas.obtenerEspecialistas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

