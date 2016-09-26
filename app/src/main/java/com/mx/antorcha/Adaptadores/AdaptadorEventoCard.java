package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mx.antorcha.Activities.BuscarActividad;
import com.mx.antorcha.Activities.Compartir;
import com.mx.antorcha.Activities.DetalleEvento;
import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Modelos.Evento;
import com.mx.antorcha.R;

import java.util.ArrayList;

/**
 *
 */
public class AdaptadorEventoCard extends RecyclerView.Adapter<AdaptadorEventoCard.ViewHolder> {
    ArrayList<Evento> eventos;
    static public Activity activity;

    public AdaptadorEventoCard (Activity activity, ArrayList<Evento> eventos) {
        this.eventos = eventos;
        this.activity = activity;
    }

    @Override
    public AdaptadorEventoCard.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_actividades_eventos_card, parent, false);

        return new AdaptadorEventoCard.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorEventoCard.ViewHolder holder, int position) {
        holder.setEvento(eventos.get(position));
        holder.setTextViewNombre(eventos.get(position).getNombre());
        holder.setTextViewFecha(eventos.get(position).getFechaInicio());
        holder.onClickInvitar(eventos.get(position).getId());
        holder.setLinearLayoutClick(eventos.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Evento evento;
        private TextView textViewNombre;
        private TextView textViewFecha;
        private TextView textViewInvitar;
        private LinearLayout linearLayoutClick;

        public ViewHolder(View itemView) {
            super(itemView);
            //Del itemView se jalan los elementos del xml
            /*ImageView imageViewCompartir = (ImageView) itemView.findViewById(R.id.item_card_evento_compartir);
            AdaptadorSVG.mostrarImagen(imageViewCompartir, activity, R.raw.icono_mas_opciones);*/
            linearLayoutClick = (LinearLayout) itemView.findViewById(R.id.card_evento_click_evento);
            textViewInvitar = (TextView) itemView.findViewById(R.id.item_evento_invitar);
            textViewNombre = (TextView) itemView.findViewById(R.id.item_card_actividades_evento_nombre);
            textViewFecha = (TextView) itemView.findViewById(R.id.item_card_actividades_evento_fecha);

        }

        public void setEvento (Evento evento) {
            this.evento = evento;
        }

        public void setTextViewNombre (String nombre) {
            this.textViewNombre.setText(nombre);
        }

        public void setTextViewFecha (String fecha) {
            this.textViewFecha.setText(fecha);
        }

        public void onClickInvitar (final int idEvento) {
            textViewInvitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, DetalleEvento.class);

                    String[] parametros = new String[20];
                    parametros[0] = evento.getId() + "";
                    parametros[1] = evento.getNombre();
                    parametros[2] = evento.getDescripcion();
                    parametros[3] = evento.getDomicilio();
                    parametros[4] = evento.getColonia();
                    parametros[5] = evento.getCodigoPostal();
                    parametros[6] = evento.getMunicipio();
                    parametros[7] = evento.getCiudad();
                    parametros[8] = evento.getEstado();
                    parametros[9] = evento.getTelefono();
                    parametros[10] = evento.getFechaInicio();
                    parametros[11] = evento.getFechaFin();
                    parametros[12] = evento.getLatitud() + "";
                    parametros[13] = evento.getLongitud() + "";

                    intent.putExtra("Evento", parametros);

                    activity.startActivity(intent);
                }
            });
        }

        public void setLinearLayoutClick(final int idEvento) {
            this.linearLayoutClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, BuscarActividad.class);
                    intent.putExtra("idEvento", idEvento);

                    activity.startActivity(intent);
                }
            });
        }
    }
}
