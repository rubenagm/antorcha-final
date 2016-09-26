package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.graphics.Matrix;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Modelos.Deporte;
import com.mx.antorcha.Modelos.Disciplina;
import com.mx.antorcha.R;

import java.util.ArrayList;

/**
 *
 */
public class AdaptadorListaNuevosDeportes extends ArrayAdapter<Disciplina>{

    private Activity activity;
    private ArrayList<String> stringDeportes;
    private ArrayList<String> stringDisciplinas;

    public AdaptadorListaNuevosDeportes(Activity activity, ArrayList<Disciplina> disciplinas) {
        super(activity, R.layout.item_seleccionar_disciplina, disciplinas);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Disciplina disciplina = getItem(position);
        LayoutInflater vi = LayoutInflater.from(getContext());
        convertView = vi.inflate(R.layout.item_seleccionar_disciplina, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_seleccionar_disciplina_imagen);
        AdaptadorSVG.mostrarImagen(imageView, activity, R.raw.icono_flecha_izquierda);
        TextView textViewDisciplina = (TextView) convertView.findViewById(R.id.item_seleccionar_disciplina_texto);
        textViewDisciplina.setText(disciplina.getNombre());
        /****** VARIABLES DE PRUEBA *******

        ArrayList<Deporte> deportes = new ArrayList<>();
        deportes.add(new Deporte());
        deportes.add(new Deporte());
        /**********************************/

        ConexionBaseDatosObtener conexionBaseDatosObtener = new ConexionBaseDatosObtener(activity);
        ArrayList<Deporte> deportes = conexionBaseDatosObtener.obtenerDeportes(disciplina.getId());

        final LinearLayout linearLayoutSeleccionarDisciplina = (LinearLayout) convertView.findViewById(R.id.seleccionar_disciplina_layout);

        for (final Deporte deporte : deportes) {
            View view = vi.inflate(R.layout.item_seleccionar_deporte, null);
            linearLayoutSeleccionarDisciplina.addView(view);

            LinearLayout linearLayout = (LinearLayout) view;
            final CheckBox checkBoxDeportes = (CheckBox) linearLayout.getChildAt(0);
            TextView textView = (TextView) linearLayout.getChildAt(1);
            textView.setText(deporte.getNombre());

            if (stringDeportes.contains(deporte.getId() + "")) {
                checkBoxDeportes.setChecked(true);
            }

            //Se pone el click de los checbox
            checkBoxDeportes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkBoxDeportes.isChecked()) {
                        //checkBoxDeportes.setChecked(false);
                        stringDeportes.add(deporte.getId() + "");
                    } else {
                        //checkBoxDeportes.setChecked(true);
                        stringDeportes.remove(deporte.getId() + "");
                    }
                }
            });

        }

        //Se oculta
        linearLayoutSeleccionarDisciplina.setVisibility(View.GONE);

        //Se ca´tura el clic de la disciplina
        final RelativeLayout relativeLayoutClic = (RelativeLayout) convertView.findViewById(R.id.item_disciplina_clic);

        relativeLayoutClic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (linearLayoutSeleccionarDisciplina.getVisibility() == View.GONE) {
                    linearLayoutSeleccionarDisciplina.setVisibility(View.VISIBLE);
                } else {
                    linearLayoutSeleccionarDisciplina.setVisibility(View.GONE);
                }
            }
        });

        //el click en el checkbox de las disciplinas
        final CheckBox checkBoxDisciplina = (CheckBox) convertView.findViewById(R.id.item_seleccionar_disciplina_check);
        checkBoxDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxDisciplina.isChecked()) {
                    //checkBoxDisciplina.setChecked(false);
                    stringDisciplinas.add(disciplina.getId() + "");
                } else {
                    //checkBoxDisciplina.setChecked(true);
                    stringDisciplinas.remove(disciplina.getId() + "");
                }
            }
        });

        if (stringDisciplinas.contains(disciplina.getId() + "")) {
            checkBoxDisciplina.setChecked(true);
        }

        return convertView;
    }


    public void setStringDeportes(ArrayList<String> stringDeportes) {
        this.stringDeportes = stringDeportes;
    }

    public void setStringDisciplinas(ArrayList<String> stringDisciplinas) {
        this.stringDisciplinas = stringDisciplinas;
    }
}
