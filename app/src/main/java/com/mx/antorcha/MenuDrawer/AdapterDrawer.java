package com.mx.antorcha.MenuDrawer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.mx.antorcha.Activities.Actividades;
import com.mx.antorcha.Activities.AyudanosMejorar;
import com.mx.antorcha.Activities.BuscarActividad;
import com.mx.antorcha.Activities.Especialistas;
import com.mx.antorcha.Activities.Inicio;
import com.mx.antorcha.Activities.Medallas;
import com.mx.antorcha.Activities.Metas;
import com.mx.antorcha.Activities.Perfil;
import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Conexion.DescargarImagen;
import com.mx.antorcha.Fragment.FragmentPerfilPerfil;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import java.util.ArrayList;

/**
 *
 */
public class AdapterDrawer extends ArrayAdapter<String> {
    private int resource;
    private ImageView imageViewBuscarActividad;
    private ImageView imageViewActividades;
    private ImageView imageViewPerfil;
    private ImageView imageViewMedallas;
    private ImageView imageViewMetas;
    private ImageView imageViewMas;
    private ImageView imageViewAyudanosMejorar;
    private ImageView imageViewEspecialistas;

    private LinearLayout linearLayoutBuscarActividad;
    private LinearLayout linearLayoutActividades;
    private LinearLayout linearLayoutPerfil;
    private LinearLayout linearLayoutMedallas;
    private LinearLayout linearLayoutMetas;
    private LinearLayout linearLayoutAyudanosMejorar;
    private LinearLayout linearLayoutEspecialistas;

    private Activity activity;
    private String actividad;

    MiembroSharedPreferences miembroSharedPreferences;

    public AdapterDrawer(Activity activity, int resource, ArrayList<String> palabra, String actividad) {
        super(activity, resource, palabra);
        this.activity = activity;
        this.resource = resource;
        this.actividad = actividad;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        convertView = vi.inflate(resource, null);

        //Se carga el sharedPreferences del miembro
        miembroSharedPreferences = new MiembroSharedPreferences(activity);

        //Se inicializan las imagenes para poder mostrar el icono en svg
        imageViewBuscarActividad = (ImageView)
                convertView.findViewById(R.id.drawer_icono_buscar_actividad);
        imageViewActividades = (ImageView) convertView.findViewById(R.id.drawer_icono_actividades);
        imageViewPerfil = (ImageView) convertView.findViewById(R.id.drawer_icono_perfil);
        imageViewMedallas = (ImageView) convertView.findViewById(R.id.drawer_icono_medallas);
        imageViewMetas = (ImageView) convertView.findViewById(R.id.drawer_icono_metas);
        imageViewMas = (ImageView) convertView.findViewById(R.id.drawer_mas);
        imageViewAyudanosMejorar = (ImageView) convertView.findViewById(R.id.drawer_icono_ayudano_mejorar);
        imageViewEspecialistas = (ImageView) convertView.findViewById(R.id.drawer_icono_especialistas);

        //se muestran los iconos
        AdaptadorSVG.mostrarImagen(imageViewBuscarActividad, activity, R.raw.icono_buscar_actividad);
        AdaptadorSVG.mostrarImagen(imageViewActividades, activity, R.raw.icono_actividades);
        AdaptadorSVG.mostrarImagen(imageViewPerfil, activity, R.raw.icono_perfil);
        AdaptadorSVG.mostrarImagen(imageViewMedallas, activity, R.raw.icono_medallas);
        AdaptadorSVG.mostrarImagen(imageViewMetas, activity, R.raw.icono_metas);
        AdaptadorSVG.mostrarImagen(imageViewEspecialistas, activity, R.raw.icono_especialistas);
        AdaptadorSVG.mostrarImagen(imageViewAyudanosMejorar, activity, R.raw.icono_ayudanos_mejorar);

        //Se cargan los textview para pintarlos
        TextView textViewBuscarActividad = (TextView) convertView.findViewById(R.id.drawer_texto_buscar_actividad);
        TextView textViewActividades = (TextView) convertView.findViewById(R.id.drawer_texto_actividades);
        TextView textViewPerfil = (TextView) convertView.findViewById(R.id.drawer_texto_perfil);
        TextView textViewMedallas = (TextView) convertView.findViewById(R.id.drawer_texto_medallas);
        TextView textViewMetas = (TextView) convertView.findViewById(R.id.drawer_texto_metas);
        TextView textViewAyudanosMejorar = (TextView) convertView.findViewById(R.id.drawer_texto_ayudanos_mejorar);
        TextView textViewEspecialistas = (TextView) convertView.findViewById(R.id.drawer_texto_especialistas);

        //para pintar el icono seleccionado y el textview
        switch (actividad) {
            case "Perfil" :
                imageViewPerfil.setAlpha(1f);
                textViewPerfil.setTextColor(Color.parseColor("#FF9522"));
                AdaptadorSVG.mostrarImagen(imageViewPerfil, activity, R.raw.icono_perfil_seleccionado);
                break;
            case "Metas" :
                imageViewMetas.setAlpha(1f);
                textViewMetas.setTextColor(Color.parseColor("#FF9522"));
                AdaptadorSVG.mostrarImagen(imageViewMetas, activity, R.raw.icono_metas_seleccionado);
                break;
            case "Medallas" :
                imageViewMedallas.setAlpha(1f);
                textViewMedallas.setTextColor(Color.parseColor("#FF9522"));
                AdaptadorSVG.mostrarImagen(imageViewMedallas, activity, R.raw.icono_medallas_seleccionado);
                break;
            case "Actividades" :
                imageViewActividades.setAlpha(1f);
                textViewActividades.setTextColor(Color.parseColor("#FF9522"));
                AdaptadorSVG.mostrarImagen(imageViewActividades, activity, R.raw.icono_actividades_seleccionado);
                break;
            case "BuscarActividad" :
                imageViewBuscarActividad.setAlpha(1f);
                textViewBuscarActividad.setTextColor(Color.parseColor("#FF9522"));
                AdaptadorSVG.mostrarImagen(imageViewBuscarActividad, activity, R.raw.icono_buscar_actividad_seleccionado);
                break;
            case "AyudanosMejorar" :
                imageViewAyudanosMejorar.setAlpha(1f);
                AdaptadorSVG.mostrarImagen(imageViewAyudanosMejorar, activity, R.raw.icono_ayudanos_mejorar_seleccionado);
                textViewAyudanosMejorar.setTextColor(Color.parseColor("#FF9522"));
                break;
            case "Especialistas" :
                imageViewEspecialistas.setAlpha(1f);
                textViewEspecialistas.setTextColor(Color.parseColor("#FF9522"));
                AdaptadorSVG.mostrarImagen(imageViewEspecialistas, activity, R.raw.icono_especialistas_seleccionado);
                break;
        }


        //se inicializan los linear layout
        linearLayoutBuscarActividad = (LinearLayout) convertView.findViewById(R.id.drawer_layout_buscar_actividad);
        linearLayoutActividades = (LinearLayout) convertView.findViewById(R.id.drawer_layout_actividades);
        linearLayoutPerfil = (LinearLayout) convertView.findViewById(R.id.drawer_layout_perfil);
        linearLayoutMedallas = (LinearLayout) convertView.findViewById(R.id.drawer_layout_medallas);
        linearLayoutMetas = (LinearLayout) convertView.findViewById(R.id.drawer_layout_metas);
        linearLayoutAyudanosMejorar = (LinearLayout) convertView.findViewById(R.id.drawer_layout_ayudanos_mejorar);
        linearLayoutEspecialistas = (LinearLayout) convertView.findViewById(R.id.drawer_layout_especialistas);

        //Se agregan los clics del menú
        linearLayoutPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Perfil.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        linearLayoutMedallas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Medallas.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        linearLayoutBuscarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BuscarActividad.class);
                activity.startActivity(intent);
                activity.finish();

            }
        });

        linearLayoutMetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Metas.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        linearLayoutActividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Actividades.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        linearLayoutEspecialistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Especialistas.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        linearLayoutAyudanosMejorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AyudanosMejorar.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        //se obtiene la imagen guardada en el celular
        Bitmap bitmapImagenPerfil = FragmentPerfilPerfil.obtenerImagen(miembroSharedPreferences.getId());

        //se carga la imagen de perfil
        ImageView imageViewImagenPerfil = (ImageView)  convertView.findViewById(R.id.drawer_imagen_perfil);

        if (bitmapImagenPerfil != null) {
            imageViewImagenPerfil.setImageBitmap(bitmapImagenPerfil);
        } else {
            DescargarImagen.guardarImagen(activity, "http://asistencias.esy.es/imagenes/"
                    + miembroSharedPreferences.getId()
                    + ".jpg", miembroSharedPreferences.getId() + ".jpg");
        }

        //Se carga la información de usuario
        TextView textViewNombre = (TextView) convertView.findViewById(R.id.drawer_nombre_miembro);
        textViewNombre.setText(miembroSharedPreferences.getNombre());

        //Se carga el layout de sesión
        final RelativeLayout relativeLayoutCerrarSesion = (RelativeLayout) convertView.findViewById(R.id.drawer_layout_cerrar_sesion);

        //se carga el layout para cuando se de clic se pueda cerrar sesión
        RelativeLayout relativeLayoutOpcion = (RelativeLayout) convertView.findViewById(R.id.drawer_opcion_sesion);
        relativeLayoutOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (relativeLayoutCerrarSesion.getVisibility() == View.VISIBLE) {
                    relativeLayoutCerrarSesion.setVisibility(View.GONE);
                } else {
                    relativeLayoutCerrarSesion.setVisibility(View.VISIBLE);
                }
            }
        });

        relativeLayoutCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cuando se responde sí se cierra sesión

                        FacebookSdk.sdkInitialize(activity);
                        //Se borra la base de datos
                        activity.deleteDatabase("Antorcha");



                        //Se borra la imagen de perfil
                        DescargarImagen.borrarImagen(miembroSharedPreferences.getId());

                        miembroSharedPreferences.borrarTodo();
                        Intent intent = new Intent(activity, Inicio.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }).setNegativeButton("Cancelar", null)
                .setIcon(R.drawable.logo_antorcha)
                .setMessage("Estás a punto de cerrar sesión, presiona aceptar para continuar")
                .setTitle("Cerrar sesión")
                .show();


            }
        });

        return convertView;
    }
}
