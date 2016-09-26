package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Modelos.Deporte;
import com.mx.antorcha.Modelos.Disciplina;
import com.mx.antorcha.R;

import java.util.ArrayList;

/**
 *
 */
public class AdaptadorListaDeportes extends ArrayAdapter<Disciplina> {

    private Activity activity;

    public AdaptadorListaDeportes(Activity activity, int resource, ArrayList<Disciplina> disciplinas) {
        super(activity, resource, disciplinas);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        convertView = vi.inflate(R.layout.item_perfil_deporte, null);

        Disciplina disciplina = getItem(position);

        ImageView imageViewMasOpciones = (ImageView) convertView.findViewById(R.id.deportes_lista_mas_opciones);
        AdaptadorSVG.mostrarImagen(imageViewMasOpciones, activity, R.raw.icono_compartir);

        ImageView imageViewFondo = (ImageView) convertView.findViewById(R.id.item_perfil_deporte_fondo);
        imageViewFondo.setImageResource(retornaImagen(disciplina.getNombre()));

        TextView textViewPrincipal = (TextView) convertView.findViewById(R.id.item_deporte_texto_principal);
        textViewPrincipal.setText(disciplina.getNombre());

        TextView textViewSecundario = (TextView) convertView.findViewById(R.id.item_deporte_texto_secundario);
        textViewSecundario.setText("");

        return convertView;
    }

    public int retornaImagen (String disciplina) {

        switch (disciplina) {
            case "Acuáticos" : {
                return R.drawable.acuaticos;
            }
            case "Aérea" : {
                return R.drawable.aerea;
            }
            case "Agarre" : {
                return R.drawable.agarre;
            }
            case "Animales" : {
                return R.drawable.animales;
            }
            case "Atletismo" : {
                return R.drawable.atletismo;
            }
            case "Motor" : {
                return R.drawable.motor;
            }
            case "Ciclismo" : {
                return R.drawable.ciclismo;
            }
            case "Combate" : {
                return R.drawable.combate;
            }
            case "Equipo" : {
                return R.drawable.equipo;
            }
            case "Fuerza" : {
                return R.drawable.fuerza;
            }
            case "Fuerza mayor" : {
                return R.drawable.fuerza_mayor;
            }
            case "Misceláneas" : {
                return R.drawable.miscelanea;
            }
            case "Múltiples" : {
                return R.drawable.multiples;
            }
            case "Nieve" : {
                return R.drawable.nieve;
            }
            case "Patinaje" : {
                return R.drawable.patinaje;
            }
            case "Pista" : {
                return R.drawable.pista;
            }
            case "Resistencia" : {
                return R.drawable.resistencia;
            }
            case "Tabla" : {
                return R.drawable.tabla;
            }
            case "Tiro al blanco" : {
                return R.drawable.tiro;
            }
            case "Trineo" : {
                return R.drawable.trineo;
            }
            case "Desplazamiento" : {
                return R.drawable.desplazamiento;
            }
            case "Deportes alternativos" : {
                return R.drawable.alternativos;
            }
            case "Aerobicos" : {
                return R.drawable.aerobicos;
            }
            case "Danza" : {
                return R.drawable.danza;
            }
            case "Gimnasia" : {
                return R.drawable.gimnasia;
            }
            case "Unidades deportivas" : {
                return R.drawable.unidad_deportiva;
            }
            default:

                return R.drawable.default_espacio;
        }
    }
}
