package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mx.antorcha.Activities.BuscarActividad;
import com.mx.antorcha.Activities.Compartir;
import com.mx.antorcha.Conexion.DescargarImagen;
import com.mx.antorcha.Conexion.InfoConexion;
import com.mx.antorcha.Dialogos.DialogoValoracion;
import com.mx.antorcha.Modelos.EspacioDeportivo;
import com.mx.antorcha.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 *
 */
public class AdaptadorEspacioCard extends RecyclerView.Adapter<AdaptadorEspacioCard.ViewHolder> {
    ArrayList<EspacioDeportivo> espacioDeportivos;
    static Activity activity;
    static public FragmentManager fragmentManager;

    public AdaptadorEspacioCard (Activity activity, ArrayList<EspacioDeportivo> espacioDeportivos) {
        this.espacioDeportivos = espacioDeportivos;
        this.activity = activity;
    }

    @Override
    public AdaptadorEspacioCard.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_actividades_card_espacio, parent, false);

        AdaptadorEspacioCard.ViewHolder viewHolder = new AdaptadorEspacioCard.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdaptadorEspacioCard.ViewHolder holder, int position) {
        holder.setEspacioDeportivo(espacioDeportivos.get(position));
        holder.setTextViewNombre(espacioDeportivos.get(position).getNombre());

        //Se calcula la descripcion por si es muy larga
        String descripcion;

        if (espacioDeportivos.get(position).getDescripcion().length() > 107) {
            descripcion = espacioDeportivos.get(position).getDescripcion().substring(0, 110) + "...";
        } else {
            descripcion = espacioDeportivos.get(position).getDescripcion();
        }
        holder.setTextViewCalificar(espacioDeportivos.get(position).getId());
        holder.setLinearLayout(espacioDeportivos.get(position).getId());
        holder.setImageView(espacioDeportivos.get(position).getId());
        holder.setTextViewDescripcion(descripcion);
        holder.onClickInvitar(espacioDeportivos.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return espacioDeportivos.size();
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private EspacioDeportivo espacioDeportivo;
        private TextView textViewNombre;
        private TextView textViewCalificar;
        private TextView textViewDescripcion;
        private TextView textViewInvitar;
        private LinearLayout linearLayout;
        private ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            //Del itemView se jalan los elementos del xml
            //ImageView imageViewCompartir = (ImageView) itemView.findViewById(R.id.item_card_espacio_compartir);
            //AdaptadorSVG.mostrarImagen(imageViewCompartir, activity, R.raw.icono_mas_opciones);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_espacio_mostrar_espacio);
            textViewInvitar = (TextView) itemView.findViewById(R.id.item_espacio_invitar);
            textViewCalificar = (TextView) itemView.findViewById(R.id.item_espacio_calificar);
            textViewNombre = (TextView) itemView.findViewById(R.id.item_card_actividades_espacio_nombre);
            textViewDescripcion = (TextView) itemView.findViewById(R.id.item_card_actividades_espacio_descripcion);
            imageView = (ImageView) itemView.findViewById(R.id.item_actividades_card_espacio_imagen);
        }

        public void setLinearLayout (final int idEspacio) {
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, BuscarActividad.class);
                    intent.putExtra("idEspacio", idEspacio);
                    activity.startActivity(intent);
                }
            });
        }

        public void setEspacioDeportivo (EspacioDeportivo espacioDeportivo) {
            this.espacioDeportivo = espacioDeportivo;
        }

        public void onClickInvitar(final int idEspacio){
            textViewInvitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, Compartir.class);
                    intent.putExtra("tipo", "espacio");
                    intent.putExtra("id", idEspacio);
                    activity.startActivity(intent);
                }
            });
        }

        public void setTextViewNombre(String nombre) {
            this.textViewNombre.setText(nombre);
        }

        public void setTextViewDescripcion(String descripcion) {
            this.textViewDescripcion.setText(descripcion);
        }

        public void setImageView(int idEspacio) {
            DescargarImagen.imagenGuardada(activity, "espacio_" + idEspacio + ".png", this.imageView, InfoConexion.URL_DESCARGAR_IMAGEN_ESPACIO + idEspacio + "_1_.png");
        }

        public void setTextViewCalificar(int idEspacio) {
            this.textViewCalificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogoValoracion dialogoValoracion = new DialogoValoracion();
                    dialogoValoracion.setActivity(activity);
                    dialogoValoracion.setIdEspacio(espacioDeportivo.getId());
                    dialogoValoracion.show(fragmentManager, "dialogo_valoracion");
                }
            });
        }
    }
}
