package com.mx.antorcha.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.mx.antorcha.Activities.Compartir;
import com.mx.antorcha.Activities.Inicio;
import com.mx.antorcha.Activities.NuevoEvento;
import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorListaEventos;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Conexion.ConexionBuscarEvento;
import com.mx.antorcha.Conexion.ConexionMiembroEvento;
import com.mx.antorcha.Conexion.DescargarImagen;
import com.mx.antorcha.Conexion.InfoConexion;
import com.mx.antorcha.Dialogos.DialogoMostrarFiltroEspacio;
import com.mx.antorcha.Modelos.Evento;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class FragmentBuscarEventos extends Fragment  { //implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener

    private Activity activity;
    GoogleMap mMap;
    MapView mapView;
    private Bundle bundle;
    public ArrayList<Evento> eventos;
    private ListView listView;
    /*LinearLayout linearLayoutCentral;
    private FragmentManager fragmentManager;
    SlidingUpPanelLayout slidingUpPanelLayout;
    LinearLayout linearLayoutSliding;*/

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_buscar_evento, container, false);
        listView = (ListView) rootView.findViewById(R.id.fragment_buscar_evento_lista);

        RelativeLayout relativeLayoutAgregar = (RelativeLayout) rootView.findViewById(R.id.fragment_buscar_evento_agregar);
        relativeLayoutAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NuevoEvento.class);
                activity.startActivity(intent);
            }
        });

        if (bundle != null && bundle.containsKey("idEvento")) {
            Toast.makeText(activity,
                    "Felicidades! se ha dado de alta tu evento!",
                    Toast.LENGTH_LONG).show();
        }
        /* PARA LOS MAPAS ----------------

        eventos = new ArrayList<>();
        //
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float dp = 100f;
        float fpixels = metrics.density * dp;
        final int pixels = (int) (fpixels + 0.5f);

        slidingUpPanelLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.buscar_evento_sliding_layout);
        slidingUpPanelLayout.setPanelHeight(0);

        //Se cargan las imagenes de los icono de contacto
        ImageView imageViewCompartir = (ImageView) rootView.findViewById(R.id.sliding_buscar_actividades_evento_compartir);
        ImageView imageViewContacto = (ImageView) rootView.findViewById(R.id.sliding_buscar_actividades_evento_contacto);
        ImageView imageViewMarkerCentral = (ImageView) rootView.findViewById(R.id.buscar_evento_marker_central);
        ImageView imageViewBotonAsistoEvento = (ImageView) rootView.findViewById(R.id.sliding_buscar_evento_asisto_evento);

        //Se carga el globo de busqueda
        ImageView imageViewGloboBusqueda = (ImageView) rootView.findViewById(R.id.buscar_evento_globo_busqueda);
        AdaptadorSVG.mostrarImagen(imageViewGloboBusqueda, activity, R.raw.globo_busqueda);

        AdaptadorSVG.mostrarImagen(imageViewCompartir, activity, R.raw.icono_compartir);
        AdaptadorSVG.mostrarImagen(imageViewContacto, activity, R.raw.icono_llamada);
        AdaptadorSVG.mostrarImagen(imageViewMarkerCentral, activity, R.raw.icono_marker_naranja);
        AdaptadorSVG.mostrarImagen(imageViewBotonAsistoEvento, activity, R.raw.boton_agregar_calendario);

        //El on click de layout
        linearLayoutCentral = (LinearLayout) rootView.findViewById(R.id.buscar_evento_layout_click_filtro);
        linearLayoutCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoMostrarFiltroEspacio dialogoMostrarFiltroEspacio = new DialogoMostrarFiltroEspacio();
                dialogoMostrarFiltroEspacio.setContext(activity);
                dialogoMostrarFiltroEspacio.show(fragmentManager, "filtro_espacio");
            }
        });

        //se inicializa el Linear del sliding
        linearLayoutSliding = (LinearLayout) rootView.findViewById(R.id.dragView);

        if (bundle != null && bundle.containsKey("idEvento")) {

            //Se comprueba si se tiene en la base de datos
            Evento evento = new ConexionBaseDatosObtener(activity).obtenerUnEvento(bundle.getInt("idEvento"));

            if (evento != null) {
                mostrarEvento(evento, rootView);
                slidingUpPanelLayout.setPanelHeight(pixels);
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            } else {
                //De momento nada
            }
        }

        //Inicializar el mapa
        mapView = (MapView) rootView.findViewById(R.id.map_fragment_evento);
        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(activity);
        mMap = mapView.getMap();

        if (mMap != null) {

            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(new LatLng(20.684899874296615,
                            -103.34658857434988));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(11f);

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11f));
            try {
                mMap.setMyLocationEnabled(true);
            } catch (Exception e) {

            }

            //Onclick del mapa
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    slidingUpPanelLayout.setPanelHeight(0);
                    linearLayoutCentral.setVisibility(View.VISIBLE);
                }
            });

            mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(activity, linearLayoutSliding));

            //Onclick del Marker
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Evento evento = obtenerEvento(marker.getTitle());

                    if (evento != null) {
                        mostrarEvento(evento, rootView);
                    }

                    slidingUpPanelLayout.setPanelHeight(pixels);
                    linearLayoutCentral.setVisibility(View.INVISIBLE);
                    return false;
                }
            });

            //Se buscan los eventos
            ConexionBuscarEvento conexionBuscarEvento = new ConexionBuscarEvento(activity, mMap);
            conexionBuscarEvento.setEventos(eventos);
            conexionBuscarEvento.execute();
        }
        */
        return rootView;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    //Onclick del Marcador
    /*@Override
    public boolean onMarkerClick(Marker marker){
        marker.showInfoWindow();
        return true;
    }
    @Override
    public void onMarkerDrag(Marker marker){

    }
    @Override
    public void onMarkerDragEnd(Marker marker){
        //Actualiza la informacion del Marcador
        marker.showInfoWindow();
    }
    @Override
    public void onMarkerDragStart(Marker marker){

    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
    */
    @Override
    public void onResume(){
        super.onResume();

        ConexionBuscarEvento conexionBuscarEvento = new ConexionBuscarEvento(activity, listView);
        conexionBuscarEvento.execute();

        if (mapView != null) {
            mapView.onResume();
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    /*private class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View v;
        LinearLayout linearLayout;
        public MyInfoWindowAdapter(Activity activity, LinearLayout linearLayout) {
            v = activity.getLayoutInflater().inflate(R.layout.etiqueta_marker,
                    null);
            this.linearLayout = linearLayout;

        }

        @Override
        public View getInfoContents(Marker marker) {
            // set some bitmap to the imageview
            linearLayout.setVisibility(View.VISIBLE);

            TextView textViewNombre = (TextView) v.findViewById(R.id.etiqueta_marquer_nombre);
            textViewNombre.setText(marker.getTitle());
            return v;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }
    public Evento obtenerEvento (String titulo) {

        for (int x = 0; x < eventos.size(); x++) {

            if (titulo.equals(eventos.get(x).getNombre())) {

                return eventos.get(x);
            }
        }

        return null;
    }

    public void mostrarEvento(final Evento evento, View view){
        TextView textViewNombre = (TextView) view.findViewById(R.id.sliding_buscar_evento_nombre_principal);
        TextView textViewFecha = (TextView) view.findViewById(R.id.sliding_buscar_evento_fecha_evento);
        TextView textViewCantidadPersonas = (TextView) view.findViewById(R.id.sliding_buscar_evento_cantidad_personas);
        TextView textViewLugar = (TextView) view.findViewById(R.id.sliding_buscar_evento_lugar);
        TextView textViewDescripcion = (TextView) view.findViewById(R.id.sliding_buscar_evento_descripcion);
        ImageView imageViewAsistirEvento = (ImageView) view.findViewById(R.id.sliding_buscar_evento_asisto_evento);

        //Se muestra la información;
        textViewNombre.setText(evento.getNombre());
        textViewFecha.setText(evento.getFechaInicio());
        textViewCantidadPersonas.setText(10 + "");
        textViewLugar.setText(evento.getDomicilio());
        textViewDescripcion.setText(evento.getDescripcion());

        ImageView imageViewCompartir = (ImageView) view.findViewById(R.id.sliding_buscar_actividades_evento_compartir);
        ImageView imageViewContacto = (ImageView) view.findViewById(R.id.sliding_buscar_actividades_evento_contacto);

        //Imagen principal
        ImageView imageViewImagenPrincipal = (ImageView) view.findViewById(R.id.sliding_buscar_espacio_imagen_principal);
        imageViewImagenPrincipal.setImageResource(R.drawable.default_evento);
        DescargarImagen.imagenGuardada(activity, "evento_" + evento.getId() + ".png", imageViewImagenPrincipal, InfoConexion.URL_DESCARGAR_IMAGEN_EVENTO + evento.getId() + "_1_.png");

        //Imagen secundaria
        ImageView imageViewSecundaria = (ImageView) view.findViewById(R.id.sliding_imagen_evento);
        imageViewSecundaria.setImageResource(R.drawable.default_evento);
        DescargarImagen.imagenGuardada(activity, "evento_" + evento.getId() + ".png", imageViewSecundaria, InfoConexion.URL_DESCARGAR_IMAGEN_EVENTO + evento.getId() + "_2_.png");
        //Se pone el click de la llamada
        imageViewContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + evento.getTelefono()));
                activity.startActivity(intent);
            }
        });

        imageViewCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Compartir.class);
                intent.putExtra("id", evento.getId());
                intent.putExtra("tipo", "evento");

                activity.startActivity(intent);
            }
        });

        //El click de l botón de agregar evento
        imageViewAsistirEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConexionBaseDatosInsertar(activity).insertarEvento(evento);
                ConexionMiembroEvento conexionMiembroEvento
                        = new ConexionMiembroEvento(activity,
                        new MiembroSharedPreferences(activity).getId(),
                        evento.getId(),
                        0);
                conexionMiembroEvento.insertarEvento();

                new AlertDialog.Builder(activity).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
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
                        activity.startActivity(intent);
                    }
                }).setNegativeButton("No", null)
                        .setIcon(R.drawable.logo_antorcha)
                        .setMessage("¿Quieres agregar el evento a tu calendario?")
                        .setTitle("Asistir a evento")
                        .show();
                //Toast.makeText(activity, evento.getNombre(), Toast.LENGTH_LONG).show();
            }
        });

    }*/
}
