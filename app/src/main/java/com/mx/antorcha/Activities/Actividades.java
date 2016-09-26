package com.mx.antorcha.Activities;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorActividadesTabs;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Conexion.ConexionObtenerMedallas;
import com.mx.antorcha.LibreriaTabsSliding.SlidingTabLayout;
import com.mx.antorcha.MenuDrawer.AdapterDrawer;
import com.mx.antorcha.Modelos.EspacioDeportivo;
import com.mx.antorcha.Modelos.Evento;
import com.mx.antorcha.R;

import java.util.ArrayList;
//
public class Actividades extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.actividades_toolbar);
        setSupportActionBar(toolbar);

        //se carga el drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView) findViewById(R.id.lista_drawer);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        listView.setAdapter(new AdapterDrawer(this, R.layout.drawer, arrayList, "Actividades"));

        //Se muestra el boton del drawer
        ImageView imageViewDrawer = (ImageView) findViewById(R.id.actividades_barra_drawer);
        AdaptadorSVG.mostrarImagen(imageViewDrawer, this, R.raw.icono_menu_drawer);

        imageViewDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        AdaptadorActividadesTabs adaptadorActividadesTabs = new AdaptadorActividadesTabs(getSupportFragmentManager(), this);
        adaptadorActividadesTabs.setFragmentManager(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.actividades_pager);
        viewPager.setAdapter(adaptadorActividadesTabs);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.actividades_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(Color.WHITE);
        slidingTabLayout.setViewPager(viewPager);

        ConexionObtenerMedallas conexionObtenerMedallas = new ConexionObtenerMedallas(Actividades.this);
        conexionObtenerMedallas.obtenerMedallas();
    }
}
