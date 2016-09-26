package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Compartir.Compartir;
import com.mx.antorcha.Conexion.ConexionMetaProgreso;
import com.mx.antorcha.Dialogos.DialogoMeta;
import com.mx.antorcha.Modelos.Meta;
import com.mx.antorcha.Modelos.MetaProgreso;
import com.mx.antorcha.R;

import java.util.ArrayList;

/**
 *
 */
public class AdaptadorListaMetas extends ArrayAdapter<Meta> {
    private Context activity;

    FragmentManager fragmentManager;
    private ListView listView;
    View viewCompartir;
    LinearLayout linearLayoutClic;

    public AdaptadorListaMetas(Context activity, ArrayList<Meta> metas, FragmentManager fragmentManager) {
        super(activity, R.layout.item_lista_meta, metas);
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Meta meta = getItem(position);

        LayoutInflater vi = LayoutInflater.from(getContext());
        convertView = vi.inflate(R.layout.item_lista_meta, null);
        viewCompartir = convertView;

        ImageView imageViewMasOpciones = (ImageView) convertView.findViewById(R.id.item_metas_mas_opciones);
        ImageView imageViewCompartir   = (ImageView) convertView.findViewById(R.id.item_metas_compartir);


        AdaptadorSVG.mostrarImagen(imageViewMasOpciones, activity, R.raw.icono_mas_opciones);
        AdaptadorSVG.mostrarImagen(imageViewCompartir, activity, R.raw.icono_compartir);

        //nombre de la meta
        TextView textViewNombre = (TextView) convertView.findViewById(R.id.item_meta_nombre_meta);
        textViewNombre.setText(meta.getNombre());

        TextView textViewFin = (TextView) convertView.findViewById(R.id.item_meta_fin);
        textViewFin.setText(meta.getFin() + obtenerMedida(meta.getTipoMedida()));

        //se coloca la linea
        ImageView imageViewLinea = (ImageView) convertView.findViewById(R.id.item_lista_medalla_linea);
        AdaptadorSVG.mostrarImagen(imageViewLinea, activity, R.raw.linea_meta);

        //Onclick de compartir
        RelativeLayout relativeLayoutClicCompartir = (RelativeLayout) convertView.findViewById(R.id.item_metas_clic_compartir);

        relativeLayoutClicCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Compartir compartir = new Compartir(activity);
                compartir.agregarTexto("Nueva meta!");
                compartir.agregarView(linearLayoutClic);
                compartir.compartir();
            }
        });

        //Se cargan los progresos de las metas
        ConexionBaseDatosObtener conexionBaseDatosObtener = new ConexionBaseDatosObtener(activity);
        ArrayList<MetaProgreso> metaProgresos = conexionBaseDatosObtener.obtenerMetaProgreso(meta.getId());
        MetaProgreso metaProgreso = null;

        //Se crea un meta progreso para guardar el último
        if (metaProgresos.size() != 0) {
            metaProgreso = metaProgresos.get(metaProgresos.size() - 1);
        }

        //Cantidad de la meta
        TextView textViewInicio = (TextView) convertView.findViewById(R.id.item_meta_inicio);

        if (metaProgreso != null) {
            textViewInicio.setText(metaProgreso.getProgreso() + obtenerMedida(meta.getTipoMedida()));
        } else {
            textViewInicio.setText(meta.getInicio() + obtenerMedida(meta.getTipoMedida()));
        }

        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.item_meta_progress);

        int porcentaje = obtenerPorcentaje(meta, metaProgreso);

        //Se muestra en texto el porcentaje de la meta
        TextView textViewPorcentaje = (TextView) convertView.findViewById(R.id.item_meta_porcentaje);
        textViewPorcentaje.setText(porcentaje + "%");

        progressBar.setProgress(porcentaje);

        if (porcentaje >= 100) {
            LinearLayout linearLayoutMetaEnProgreso = (LinearLayout) convertView.findViewById(R.id.item_meta_meta_en_progreso);
            linearLayoutMetaEnProgreso.setVisibility(View.GONE);

            LinearLayout linearLayoutMetaTerminada = (LinearLayout) convertView.findViewById(R.id.item_meta_meta_terminada);
            linearLayoutMetaTerminada.setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.item_meta_nombre_meta_terminada)).setText(meta.getNombre());
            ((TextView) convertView.findViewById(R.id.item_meta_medida_terminada)).setText(metaProgreso.getProgreso() + obtenerMedida(meta.getTipoMedida()));

        }

        //Onlclick del item de la lista
        linearLayoutClic = (LinearLayout) convertView.findViewById(R.id.item_meta_clic_item);
        linearLayoutClic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoMeta dialogoMeta = new DialogoMeta();
                dialogoMeta.setFragmentManager(fragmentManager);
                dialogoMeta.setIdMeta(meta.getId());
                dialogoMeta.setActivity(activity);
                dialogoMeta.setListView(listView);
                dialogoMeta.show(fragmentManager, "dialogo_meta");
            }
        });

        return convertView;
    }

    static public int obtenerPorcentaje (Meta meta, MetaProgreso metaProgreso) {
        //se calcula el porcentaje
        double porcentaje = 0;

        if (meta.getFin() > meta.getInicio()) {
            porcentaje = 100 / (meta.getFin() - meta.getInicio()) ;
        } else {
            porcentaje = 100 / (meta.getInicio() - meta.getFin());
        }


        //Se calcula el último porcentaje
        if (metaProgreso != null) {

            if (meta.getInicio() < meta.getFin()) {
                porcentaje = (metaProgreso.getProgreso() - meta.getInicio()) * porcentaje;
            } else {
                porcentaje = (meta.getInicio() - metaProgreso.getProgreso()) * porcentaje;
            }
        } else {
            porcentaje = 0;
        }

        //si es mayor a 100 se pone 100
        if (porcentaje > 100) {
            porcentaje = 100;
        }

        if (porcentaje < 0) {
            porcentaje = 0;
        }

        return (int) porcentaje;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public String obtenerMedida (String medida) {
        switch (medida) {
            case "Segundos" : {
                return "SEG";
            }
            case "Minutos" : {
                return "MIN";
            }
            case "Horas" : {
                return "HR";
            }
            case "Gramos" : {
                return "GR";
            }
            case "Kilogramos" : {
                return "KG";
            }
            case "Centimetros" : {
                return "CM";
            }
            case "Metros" : {
                return "MT";
            }
            case "Kilometros" : {
                return "KM";
            }
            case "Porcentaje" : {
                return "%";
            }
            case "Repeticiones" : {
                return "REP";
            }
            case "Series" : {
                return "SER";
            }
            case "Vueltas" : {
                return "VLTS";
            }
            case "Puntos" : {
                return "PTS";
            }
            case "Anotaciones" : {
                return "ATS";
            }
            case "Juegos" : {
                return "J";
            }
            default : {
                return "N/A";
            }
        }
    }


}
