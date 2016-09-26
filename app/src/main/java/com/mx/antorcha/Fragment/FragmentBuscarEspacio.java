package com.mx.antorcha.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Conexion.ConexionBuscarEspacio;
import com.mx.antorcha.Conexion.ConexionInformacionEspacio;
import com.mx.antorcha.Conexion.ConexionInsertarMiembroEspacio;
import com.mx.antorcha.Conexion.DescargarImagen;
import com.mx.antorcha.Conexion.InfoConexion;
import com.mx.antorcha.Dialogos.DialogoBienvenidaMapa;
import com.mx.antorcha.Dialogos.DialogoInsertarResenia;
import com.mx.antorcha.Dialogos.DialogoMostrarFiltroEspacio;
import com.mx.antorcha.Modelos.EspacioDeportivo;
import com.mx.antorcha.Modelos.Resenia;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 *
 */
public class FragmentBuscarEspacio extends Fragment implements GoogleMap.OnMarkerDragListener {
    private int indice = 0;
    private Activity activity;
    GoogleMap mMap;
    private View view;
    private Bundle bundle;
    private ImageView imageViewAsignarmeEspacio;
    MapView mapView;
    public LinearLayout linearLayoutSliding;
    LinearLayout linearLayoutCentral;
    private FragmentManager fragmentManager;
    private ConexionBuscarEspacio conexionBuscarEspacio;
    public ArrayList<EspacioDeportivo> espacioDeportivos;
    public ArrayList<Resenia> resenias;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_buscar_espacio, container, false);
        view = rootView;
        espacioDeportivos = new ArrayList<>();
        resenias = new ArrayList<>();

        //
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float dp = 100f;
        float fpixels = metrics.density * dp;
        final int pixels = (int) (fpixels + 0.5f);

        //se inicializa el Linear del sliding
        linearLayoutSliding = (LinearLayout) rootView.findViewById(R.id.dragView);

        //Se cargan las flechas para reseña y para imagenes
        ImageView imageViewFlechaIzquierdaResenia = (ImageView) rootView.findViewById(R.id.sliding_buscar_actividad_espacio_flecha_izquierda);
        ImageView imageViewFlechaDerechaResenia = (ImageView) rootView.findViewById(R.id.sliding_buscar_actividad_espacio_flecha_derecha);
        ImageView imageViewFlechaIzquierdaImagen = (ImageView) rootView.findViewById(R.id.sliding_buscar_espacio_imagen_flecha_izquierda);
        ImageView imageViewFlechaDerechaImagen = (ImageView) rootView.findViewById(R.id.sliding_buscar_espacio_imagen_flecha_derecha);

        AdaptadorSVG.mostrarImagen(imageViewFlechaIzquierdaResenia, activity, R.raw.icono_flecha_izquierda);
        AdaptadorSVG.mostrarImagen(imageViewFlechaDerechaResenia, activity, R.raw.icono_flecha_derecha);
        AdaptadorSVG.mostrarImagen(imageViewFlechaIzquierdaImagen, activity, R.raw.icono_flecha_izquierda);
        AdaptadorSVG.mostrarImagen(imageViewFlechaDerechaImagen, activity, R.raw.icono_flecha_derecha);

        //Se cargan las imagenes de los icono de contacto
        ImageView imageViewCompartir = (ImageView) rootView.findViewById(R.id.sliding_buscar_actividades_espacio_compartir);
        ImageView imageViewContacto = (ImageView) rootView.findViewById(R.id.sliding_buscar_actividades_espacio_contacto);
        ImageView imageViewLapiz = (ImageView) rootView.findViewById(R.id.sliding_buscar_actividades_espacio_lapiz);
        ImageView imageViewMarkerCentral = (ImageView) rootView.findViewById(R.id.buscar_espacio_marker_central);

        final SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.buscar_espacio_sliding_layout);
        slidingUpPanelLayout.setPanelHeight(0);

        //Se carga el globo de busqueda
        ImageView imageViewGloboBusqueda = (ImageView) rootView.findViewById(R.id.buscar_espacio_globo_busqueda);
        AdaptadorSVG.mostrarImagen(imageViewGloboBusqueda, activity, R.raw.globo_busqueda);

        //Inicializar el mapa
        mapView = (MapView) rootView.findViewById(R.id.map_fragment_espacio);
        mapView.onCreate(savedInstanceState);

        MapsInitializer.initialize(getContext());

        //Se cargan las imagenes en vectores
        AdaptadorSVG.mostrarImagen(imageViewCompartir, activity, R.raw.icono_compartir);
        AdaptadorSVG.mostrarImagen(imageViewContacto, activity, R.raw.icono_llamada);
        AdaptadorSVG.mostrarImagen(imageViewLapiz, activity, R.raw.icono_lapiz);
        AdaptadorSVG.mostrarImagen(imageViewMarkerCentral, activity, R.raw.icono_marker_naranja);
        mMap = mapView.getMap();

        if (mMap != null) {

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(2));

            try {
                mMap.setMyLocationEnabled(true);
            } catch (Exception e) {

            }
            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(new LatLng(20.608557015413055,
                            -103.40919971466064));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(13f);

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);

            //Onclick del Marker
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    slidingUpPanelLayout.setPanelHeight(pixels);
                    linearLayoutCentral.setVisibility(View.INVISIBLE);

                    //Cuando se da click se busca el espacio
                    EspacioDeportivo espacioDeportivo = obtenerEspacio(marker.getTitle());

                    if (espacioDeportivo != null) {
                        mostrarInformacionEspacio(espacioDeportivo, rootView);
                    }

                    return false;
                }
            });

            //Onclick del mapa
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    slidingUpPanelLayout.setPanelHeight(0);
                    linearLayoutCentral.setVisibility(View.VISIBLE);
                }
            });

            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    Log.i("Google", cameraPosition.target.toString() +  "///" + cameraPosition.zoom);
                }
            });

            mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(activity, linearLayoutSliding));

            //El on click de layout
            linearLayoutCentral = (LinearLayout) rootView.findViewById(R.id.buscar_espacio_layout_click_filtro);
            linearLayoutCentral.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogoMostrarFiltroEspacio dialogoMostrarFiltroEspacio = new DialogoMostrarFiltroEspacio();
                    dialogoMostrarFiltroEspacio.setContext(activity);
                    dialogoMostrarFiltroEspacio.show(fragmentManager, "filtro_espacio");
                }
            });
            ConexionBuscarEspacio conexionBuscarEspacio = new ConexionBuscarEspacio(activity, mMap);
            conexionBuscarEspacio.setEspacioDeportivos(espacioDeportivos);
            conexionBuscarEspacio.execute();

            //Se carga la imagen del boton de asignarme espacio
            imageViewAsignarmeEspacio = (ImageView) rootView.findViewById(R.id.buscar_espacio_boton_asignarme_espacio);
            AdaptadorSVG.mostrarImagen(imageViewAsignarmeEspacio, activity, R.raw.boton_asisto_centro_deportivo);

        }

        if (bundle != null && bundle.containsKey("idEspacio")) {
            mostrarEspacio(bundle.getInt("idEspacio"), rootView);
            slidingUpPanelLayout.setPanelHeight(pixels);
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }

        final TextView textViewResenia = (TextView) view.findViewById(R.id.sliding_buscar_espacio_resenia);
        final TextView textViewTituloResenia = (TextView) view.findViewById(R.id.sliding_buscar_espacio_titulo_resenia);
        final TextView textViewNombreResenia = (TextView) view.findViewById(R.id.sliding_buscar_espacio_nombre_resenia);

        //Se colocan los click de las flechas para las reseñas
        imageViewFlechaIzquierdaResenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (resenias.size() > 0) {
                    textViewResenia.setText(resenias.get(indice).getResenia());
                    textViewTituloResenia.setText(resenias.get(indice).getTitulo());
                    textViewNombreResenia.setText(resenias.get(indice).getNombre());

                    if (indice == 0) {
                        indice = resenias.size() - 1;
                    } else {
                        indice--;
                    }
                } else {
                    Toast.makeText(activity, "No hay reseñas!, se el primero!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewFlechaDerechaResenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (resenias.size() > 0) {
                    textViewResenia.setText(resenias.get(indice).getResenia());
                    textViewTituloResenia.setText(resenias.get(indice).getTitulo());
                    textViewNombreResenia.setText(resenias.get(indice).getNombre());

                    if (indice == resenias.size() - 1) {
                        indice = 0;
                    } else {
                        indice++;
                    }
                } else {
                    Toast.makeText(activity, "No hay reseñas!, se el primero!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (new MiembroSharedPreferences(activity).getPrimeraVez()) {
            //El dialogo de bienvenida
            DialogoBienvenidaMapa dialogoBienvenidaMapa = new DialogoBienvenidaMapa();
            dialogoBienvenidaMapa.show(fragmentManager, "dialogo_bienvenida");
            new MiembroSharedPreferences(activity).setPrimeraVez();
        }

        return rootView;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }



    @Override
    public void onMarkerDrag(Marker marker){

    }
    @Override
    public void onMarkerDragEnd(Marker marker){
        //Actualiza la informacion del Marcador
    }
    @Override
    public void onMarkerDragStart(Marker marker){

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

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

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

            TextView textView = (TextView) v.findViewById(R.id.etiqueta_marquer_nombre);
            textView.setText(marker.getTitle());
            return v;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }
    }

    public EspacioDeportivo obtenerEspacio (String titulo) {

        for (EspacioDeportivo espacioDeportivo : espacioDeportivos) {

            if (espacioDeportivo.getNombre().equals(titulo)) {

                return espacioDeportivo;
            }
        }

        return null;
    }

    public void mostrarInformacionEspacio(final EspacioDeportivo espacioDeportivo, View view){
        TextView textViewNombre = (TextView) view.findViewById(R.id.sliding_buscar_actividades_espacio_nombre);
        TextView textViewDescripciom = (TextView) view.findViewById(R.id.sliding_buscar_actividades_espacio_descripcion);
        ImageView imageViewInsertarResenia = (ImageView) view.findViewById(R.id.sliding_buscar_actividades_espacio_lapiz);
        TextView textViewValoracion = (TextView) view.findViewById(R.id.sliding_buscar_espacio_valoracion);
        TextView textViewHorario = (TextView) view.findViewById(R.id.sliding_buscar_espacio_horario);
        ImageView imageViewImagenPrincipal = (ImageView) view.findViewById(R.id.sliding_buscar_espacio_imagen_principal);

        imageViewImagenPrincipal.setImageResource(R.drawable.default_espacio);
        DescargarImagen.imagenGuardada(activity, "espacio_" + espacioDeportivo.getId() + ".png", imageViewImagenPrincipal, InfoConexion.URL_DESCARGAR_IMAGEN_ESPACIO + espacioDeportivo.getId() + "_1_.png");
        String descripcion = "";

        if (espacioDeportivo.getDescripcion().length() > 40) {
            descripcion = espacioDeportivo.getDescripcion().substring(0, 37) + "...";
        } else {
            descripcion = espacioDeportivo.getDescripcion();
        }

        textViewValoracion.setText((espacioDeportivo.getValoracion() + "").substring(0, 3));
        textViewDescripciom.setText(descripcion);
        textViewNombre.setText(espacioDeportivo.getNombre());
        textViewHorario.setText(espacioDeportivo.getHorario());

        //Se coloca el click para guardar una reseña
        imageViewInsertarResenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoInsertarResenia dialogoInsertarResenia = new DialogoInsertarResenia();
                dialogoInsertarResenia.setActivity(activity);
                dialogoInsertarResenia.setId(espacioDeportivo.getId());
                dialogoInsertarResenia.setTipo(1);
                dialogoInsertarResenia.show(fragmentManager, "dialogo_insertar_resenia");
            }
        });

        //Se manda el view para cargar todos los datos desde el hilo
        imageViewAsignarmeEspacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConexionBaseDatosInsertar(activity).insertarEspacioDeportivo(espacioDeportivo);
                ConexionInsertarMiembroEspacio conexionInsertarMiembroEspacio
                        = new ConexionInsertarMiembroEspacio(activity,
                        new MiembroSharedPreferences(activity).getId(),
                        espacioDeportivo.getId(),
                        0,
                        0);
                conexionInsertarMiembroEspacio.insertarMiembroEspacio();
                Toast.makeText(activity, "Se ha asignado este espacio a tus actividades", Toast.LENGTH_LONG).show();
            }
        });

        ConexionInformacionEspacio conexionInformacionEspacio = new ConexionInformacionEspacio(activity, espacioDeportivo.getId() + "");
        conexionInformacionEspacio.setView(view);
        conexionInformacionEspacio.setResenias(resenias);
        conexionInformacionEspacio.obtenerEspacio();

        indice = 0;
    }

    public void mostrarEspacio (int idEspacio, View view) {

        ConexionBaseDatosObtener conexionBaseDatosObtener = new ConexionBaseDatosObtener(activity);

        EspacioDeportivo espacioDeportivo = conexionBaseDatosObtener.obtenerUnEspacio(idEspacio);

        if (espacioDeportivo != null) {
            mostrarInformacionEspacio(espacioDeportivo, view);

        }
    }
}
