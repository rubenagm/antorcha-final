package com.mx.antorcha.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mx.antorcha.Activities.ActualizarPerfil;
import com.mx.antorcha.Activities.Perfil;
import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorListaMedallas;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Dialogos.DialogoActualizarPerfil;
import com.mx.antorcha.Dialogos.DialogoImagenPerfil;
import com.mx.antorcha.Modelos.Medalla;
import com.mx.antorcha.OtrasFunciones.CalculoFechas;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MedallasSharedPreferences;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 */
public class FragmentPerfilPerfil extends Fragment {

    private Activity activity;
    private FragmentManager fragmentManager;
    private Perfil perfil;
    private MiembroSharedPreferences miembroSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil_perfil, container, false);

        //Se iniciaiza el shared prefecrences
        miembroSharedPreferences = new MiembroSharedPreferences(activity);

        /******** VARIABLES TEMPORALES PARA PRUEBAS *******/
        ArrayList<Medalla> medallas = new ConexionBaseDatosObtener(activity).obtenerMedallas();
        MedallasSharedPreferences medallasSharedPreferences = new MedallasSharedPreferences(activity);
        for (int x = medallas.size() - 1; x >= 0 ; x--) {

            if (medallasSharedPreferences.medallaObtenida(medallas.get(x).getId())) {

            } else {
                medallas.remove(x);
            }
        }
        /*************************************************/

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);

        //Se crea el recycler view para las medallas
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.perfil_lista_medallas);
        recyclerView.setLayoutManager(layoutManager);
        AdaptadorListaMedallas adaptadorListaMedallas = new AdaptadorListaMedallas(activity, medallas);

        recyclerView.setAdapter(adaptadorListaMedallas);

        //Se colocan las imagenes de las medallas
        ImageView imageViewFlechaIzquierda = (ImageView) rootView.findViewById(R.id.perfil_flecha_izquierda);
        ImageView imageViewFlechaDerecha = (ImageView) rootView.findViewById(R.id.perfil_flecha_derecha);
        ImageView imageViewActualizarPerfil = (ImageView) rootView.findViewById(R.id.perfil_actualizar_perfil);

        AdaptadorSVG.mostrarImagen(imageViewFlechaIzquierda, activity, R.raw.icono_flecha_izquierda);
        AdaptadorSVG.mostrarImagen(imageViewFlechaDerecha, activity, R.raw.icono_flecha_derecha);
        AdaptadorSVG.mostrarImagen(imageViewActualizarPerfil, activity, R.raw.icono_mas_opciones_blanco);

        //Se carga la imagen para el degradado de la foto, el fondo oscuro
        //ImageView imageViewDifuminado = (ImageView) rootView.findViewById(R.id.perfil_imagen_degradado);
        //AdaptadorSVG.mostrarImagen(imageViewDifuminado, activity, R.raw.degradado_inferior);

        //Se carga la imagen para el degradado superior
        //ImageView imageViewDegradadoSuperior = (ImageView) rootView.findViewById(R.id.perfil_imagen_degradado_superior);
        //AdaptadorSVG.mostrarImagen(imageViewDegradadoSuperior, activity, R.raw.degradado_superior);

        //el click en la foto de perfil
        ImageView imageViewPerfil = (ImageView) rootView.findViewById(R.id.perfil_imagen_miembro);
        imageViewPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoImagenPerfil dialogoImagenPerfil = new DialogoImagenPerfil();
                dialogoImagenPerfil.setActivity(activity);
                dialogoImagenPerfil.show(fragmentManager, "dialogo_imagen_perfil");
            }
        });

        //El click para actualizar el perfil
        RelativeLayout relativeLayoutActualizarPerfil
                = (RelativeLayout) rootView.findViewById(R.id.perfil_click_actualizar_perfil);
        relativeLayoutActualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActualizarPerfil.class);
                activity.startActivity(intent);
            }
        });

        perfil.setImageViewPerfil(imageViewPerfil);

        Bitmap bitmapImagenPerfil = obtenerImagen(miembroSharedPreferences.getId());

        if (bitmapImagenPerfil != null) {
            imageViewPerfil.setImageBitmap(bitmapImagenPerfil);
        }

        //se carga la información del usuario en el perfil
        MiembroSharedPreferences miembroSharedPreferences = new MiembroSharedPreferences(activity);

        //se inicializan los datos
        TextView textViewNombre = (TextView) rootView.findViewById(R.id.perfil_nombre_miembro);
        textViewNombre.setText(miembroSharedPreferences.getNombre());

        //se coloca la edad
        TextView textViewEdad = (TextView) rootView.findViewById(R.id.perfil_edad_miembro);
        textViewEdad.setText(CalculoFechas.calcularEdad(miembroSharedPreferences.getFechaNacimiento()) + " años");

        //Se carga la descripción y los intereses
        TextView textViewDescripcion = (TextView) rootView.findViewById(R.id.perfil_perfil_descripcion);
        TextView textViewIntereses = (TextView) rootView.findViewById(R.id.perfil_perfil_intereses);

        if (miembroSharedPreferences.getDescripcion() != "" && miembroSharedPreferences.getDescripcion() != "null") {
            textViewDescripcion.setText(miembroSharedPreferences.getDescripcion());
        }

        if (miembroSharedPreferences.getIntereses() != "" && miembroSharedPreferences.getDescripcion() != "null") {
            textViewIntereses.setText(miembroSharedPreferences.getIntereses());
        }

        //Se cargan las medallas obtenidas
        TextView textViewPrometeo = (TextView) rootView.findViewById(R.id.medallas_cantidad_prometeo);
        TextView textViewAntorcha = (TextView) rootView.findViewById(R.id.medallas_cantidad_antorcha);
        TextView textViewFuego = (TextView) rootView.findViewById(R.id.medallas_cantidad_fuego);
        TextView textViewFlama = (TextView) rootView.findViewById(R.id.medallas_cantidad_flama);
        TextView textViewChispa = (TextView) rootView.findViewById(R.id.medallas_cantidad_chispa);

        for (Medalla medalla : medallas) {

            switch (medalla.getTipo()) {
                case 1 : {

                    if (medallasSharedPreferences.medallaObtenida(medalla.getId())) {
//                        textViewPrometeo.setText((Integer.parseInt(textViewPrometeo.getText().toString()) + 1) + "");
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
        return rootView;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    static public Bitmap obtenerImagen (int id) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/antorcha/" + id + ".jpg");
        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(myDir));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
