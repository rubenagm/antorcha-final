package com.mx.antorcha.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Bienvenida.SeleccionaDeportes;
import com.mx.antorcha.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SeleccionaUbicacion extends AppCompatActivity {
    private GoogleMap googleMap;
    private MapView mapView;
    private Activity activity;
    private String datos[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_ubicacion);

        datos = getIntent().getStringArrayExtra("datos");

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.selecciona_ubicacion_toolbar);
        setSupportActionBar(toolbar);

        ImageView imageViewRegresar = (ImageView) findViewById(R.id.seleccionar_ubicacion_regresar);
        AdaptadorSVG.mostrarImagen(imageViewRegresar, this, R.raw.icono_regresar);
        activity = this;
        imageViewRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Se carga el mapa
        //Inicializar el mapa
        mapView = (MapView) findViewById(R.id.seleccionar_ubicacion_mapa);
        mapView.onCreate(savedInstanceState);
        googleMap = mapView.getMap();

        //Si hay un error al momento de cargar
        if (googleMap != null) {

            try {
                googleMap.setMyLocationEnabled(true);
            } catch (Exception e) {

            }
            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(new LatLng(20.608557015413055,
                            -103.40919971466064));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(13f);

            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);

            ImageView imageViewVerificar = (ImageView) findViewById(R.id.seleccionar_ubicacion_verificar);
            imageViewVerificar.setVisibility(View.VISIBLE);
            ImageView imageViewMarker = (ImageView) findViewById(R.id.selecciona_ubicacion_marker);
            AdaptadorSVG.mostrarImagen(imageViewMarker, this, R.raw.icono_marker_naranja);

            imageViewVerificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(SeleccionaUbicacion.this).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Se cargan los datos del formulario

                            //Cuando se responde sí se agrega al calendario
                            Intent intent = new Intent(activity, SeleccionaDeportesEvento.class);
                            intent.putExtra("datos", datos);
                            startActivity(intent);
                        }
                    }).setNegativeButton("No", null)
                            .setIcon(R.drawable.logo_antorcha)
                            .setMessage("¿Seguro que toda la información es correcta?")
                            .setTitle("Guardar evento")
                            .show();
                }
            });

            //Para cuando se mueve el marker
            googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    Log.i("Google", cameraPosition.target.toString() +  "///" + cameraPosition.zoom);
                    datos[7] = String.valueOf(cameraPosition.target.latitude);
                    datos[8] = String.valueOf(cameraPosition.target.longitude);
                }
            });
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
    }
}
