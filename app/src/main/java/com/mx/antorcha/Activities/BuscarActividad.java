package com.mx.antorcha.Activities;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorBuscarActividadTabs;
import com.mx.antorcha.Conexion.ConexionActualizarPerfil;
import com.mx.antorcha.GCM.ServicioRegistro;
import com.mx.antorcha.LibreriaTabsSliding.SlidingTabLayout;
import com.mx.antorcha.MenuDrawer.AdapterDrawer;
import com.mx.antorcha.R;
import com.mx.antorcha.Servicios.ServicioSincronizacion;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class BuscarActividad extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_actividad);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = new Intent(this, ServicioSincronizacion.class);
        startService(intent);

        //Se inicializa el shared preferences
        MiembroSharedPreferences miembroSharedPreferences = new MiembroSharedPreferences(this);

        //Se comprueba si está actualizado el perfil en la nube
        if (miembroSharedPreferences.getActualizar() == 1 || (miembroSharedPreferences.getRegistrado() == 0)) {
            startService(new Intent(this, ServicioRegistro.class));
            ConexionActualizarPerfil.actualizar(miembroSharedPreferences.getNombre(),
                    miembroSharedPreferences.getFechaNacimiento(),
                    miembroSharedPreferences.getDescripcion(),
                    miembroSharedPreferences.getIntereses(),
                    this, miembroSharedPreferences.getGCM());
        } else if (miembroSharedPreferences.getRegistrado() == 1) {
            ConexionActualizarPerfil.actualizar(miembroSharedPreferences.getNombre(),
                    miembroSharedPreferences.getFechaNacimiento(),
                    miembroSharedPreferences.getDescripcion(),
                    miembroSharedPreferences.getIntereses(),
                    this, miembroSharedPreferences.getGCM());
        }
        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.buscar_actividad_toolbar);
        setSupportActionBar(toolbar);

        //se carga el drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView) findViewById(R.id.lista_drawer);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        listView.setAdapter(new AdapterDrawer(this, R.layout.drawer, arrayList, "BuscarActividad"));

        //Se muestra el boton del drawer
        ImageView imageViewDrawer = (ImageView) findViewById(R.id.buscar_actividad_barra_drawer);
        AdaptadorSVG.mostrarImagen(imageViewDrawer, this, R.raw.icono_menu_drawer);

        imageViewDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        //se crea el adaptador para las tabs de la activity
        AdaptadorBuscarActividadTabs adaptadorBuscarActividadTabs = new AdaptadorBuscarActividadTabs(
                getSupportFragmentManager(), this, getIntent().getExtras());
        adaptadorBuscarActividadTabs.setFragmentManager(getSupportFragmentManager());

        //las páginas de las tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.buscar_actividad_pager);
        viewPager.setAdapter(adaptadorBuscarActividadTabs);
        //viewPager.setCurrentItem(1);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("idEvento")) {
            viewPager.setCurrentItem(1);
        }

        //Sliding
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.buscar_actividad_tabs);
        slidingTabLayout.setDistributeEvenly(true);

        slidingTabLayout.setSelectedIndicatorColors(Color.WHITE);
        slidingTabLayout.setViewPager(viewPager);
    }
}
