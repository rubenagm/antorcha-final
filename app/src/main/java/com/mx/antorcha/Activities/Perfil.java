package com.mx.antorcha.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.mx.antorcha.Adaptadores.AdaptadorPerfilTabs;
import com.mx.antorcha.Conexion.ConexionActualizarPerfil;
import com.mx.antorcha.Conexion.SubirImagen;
import com.mx.antorcha.Dialogos.DialogoImagenPerfil;
import com.mx.antorcha.LibreriaTabsSliding.SlidingTabLayout;
import com.mx.antorcha.MenuDrawer.AdapterDrawer;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Perfil extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private ImageView imageViewPerfil;
    private MiembroSharedPreferences miembroSharedPreferences;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private AdaptadorPerfilTabs adaptadorPerfilTabs;

    @Override
    public void onResume () {
        super.onResume();

        if (viewPager != null && adaptadorPerfilTabs != null) {
            viewPager.setAdapter(adaptadorPerfilTabs);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Se inicializa el shared preferences
        miembroSharedPreferences = new MiembroSharedPreferences(this);

        //Se comprueba si está actualizado el perfil en la nube
        if (miembroSharedPreferences.getActualizar() == 1 || miembroSharedPreferences.getRegistrado() == 1) {
            ConexionActualizarPerfil.actualizar(miembroSharedPreferences.getNombre(),
                    miembroSharedPreferences.getFechaNacimiento(),
                    miembroSharedPreferences.getDescripcion(),
                    miembroSharedPreferences.getIntereses(),
                    this, miembroSharedPreferences.getGCM());
        }

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.perfil_toolbar);
        setSupportActionBar(toolbar);

        //Se carga el boton de agregar deporte a la barra
        final ImageView imageViewAgregar = (ImageView) findViewById(R.id.perfil_barra_agregar_deporte);
        AdaptadorSVG.mostrarImagen(imageViewAgregar, this, R.raw.icono_agregar);
        imageViewAgregar.setVisibility(View.INVISIBLE);

        //se carga el drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView) findViewById(R.id.lista_drawer);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        listView.setAdapter(new AdapterDrawer(this, R.layout.drawer, arrayList, "Perfil"));
        SubirImagen.subirImagen(this, miembroSharedPreferences.getId());
        //Se muestra el boton del drawer
        ImageView imageViewDrawer = (ImageView) findViewById(R.id.perfil_barra_drawer);
        AdaptadorSVG.mostrarImagen(imageViewDrawer, this, R.raw.icono_menu_drawer);

        imageViewDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        adaptadorPerfilTabs = new AdaptadorPerfilTabs(getSupportFragmentManager(), this, imageViewAgregar);
        adaptadorPerfilTabs.setPerfil(this);

        //se carga el fragment para los dialog fragment
        adaptadorPerfilTabs.setFragmentManager(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.perfil_pager);
        viewPager.setAdapter(adaptadorPerfilTabs);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.perfil_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(Color.WHITE);
        slidingTabLayout.setViewPager(viewPager);

        //Cambiar los colores de los tabs
        slidingTabLayout.setSelectedIndicatorColors(Color.WHITE);

        //el onclick cuando se quieren agregar deportes
        imageViewAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, NuevoDeporteFavorito.class);
                startActivity(intent);
            }
        });

        //Se oculta el imageview
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    imageViewAgregar.setVisibility(View.INVISIBLE);
                } else {
                    imageViewAgregar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == DialogoImagenPerfil.INTENT_CAMARA) {
                Bitmap imagenPerfil = (Bitmap) data.getExtras().get("data");
                imageViewPerfil.setImageBitmap(imagenPerfil);
                guardarImagen(imagenPerfil, miembroSharedPreferences.getId());
            } else if (requestCode == DialogoImagenPerfil.INTENT_GALERIA){
                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageViewPerfil.setImageBitmap(bitmap);
                    guardarImagen(bitmap, miembroSharedPreferences.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static public void guardarImagen(Bitmap finalBitmap, int id) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/antorcha");
        myDir.mkdirs();
        String fname =  id + ".jpg";
        File file = new File (myDir, fname);

        if (file.exists ()) {
            file.delete ();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setImageViewPerfil(ImageView imageViewPerfil) {
        this.imageViewPerfil = imageViewPerfil;
    }
}
