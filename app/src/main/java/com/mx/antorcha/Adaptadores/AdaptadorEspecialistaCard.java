package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.antorcha.Modelos.Especialista;
import com.mx.antorcha.R;

import java.util.ArrayList;

/**
 * Created by Ruben on 05/03/2016
 */
public class AdaptadorEspecialistaCard extends RecyclerView.Adapter<AdaptadorEspecialistaCard.ViewHolder> {
    private ArrayList<Especialista> especialistas;
    static Activity activity;

    public AdaptadorEspecialistaCard (Activity activity, ArrayList<Especialista> especialistas) {
        this.activity = activity;
        this.especialistas = especialistas;
    }

    @Override
    public AdaptadorEspecialistaCard.ViewHolder onCreateViewHolder (ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_especialidades, viewGroup, false);

        AdaptadorEspecialistaCard.ViewHolder viewHolder = new AdaptadorEspecialistaCard.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder (AdaptadorEspecialistaCard.ViewHolder viewHolder, int position) {
        Especialista especialista = especialistas.get(position);

        viewHolder.setTextViewNombre(especialista.getNombre());
        viewHolder.setTextViewTitulo(especialista.getTitulo());
        viewHolder.setTextViewEspecialidad(especialista.getEspecialidad());
        viewHolder.setTextViewDescripcion(especialista.getDescripcion());
        viewHolder.setTextViewEscribir(especialista.getCorreo());
        viewHolder.setTextViewLlamar(especialista.getTelefono());
        viewHolder.setImageViewPrincipal(especialista.getSexo());
    }

    @Override
    public int getItemCount () {
        return especialistas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNombre;
        private TextView textViewTitulo;
        private TextView textViewEspecialidad;
        private TextView textViewDescripcion;
        private TextView textViewEscribir;
        private TextView textViewLlamar;
        private ImageView imageViewPrincipal;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewPrincipal = (ImageView) itemView.findViewById(R.id.item_especialidades_imagen_perfil);
            textViewNombre = (TextView) itemView.findViewById(R.id.item_especialidades_nombre);
            textViewTitulo = (TextView) itemView.findViewById(R.id.item_especialidades_titulo);
            textViewEspecialidad = (TextView) itemView.findViewById(R.id.item_especialidad_especialidad);
            textViewDescripcion = (TextView) itemView.findViewById(R.id.item_especialidades_descripcion);
            textViewEscribir = (TextView) itemView.findViewById(R.id.item_especialidades_escribir);
            textViewLlamar = (TextView) itemView.findViewById(R.id.item_especialidades_llamar);

        }

        public void setTextViewNombre(String nombre) {
            textViewNombre.setText(nombre);
        }

        public void setTextViewTitulo(String titulo) {
            textViewTitulo.setText(titulo);
        }

        public void setTextViewEspecialidad(String especialidad) {
            //Temporal
            switch (especialidad) {
                case "1" : {
                    textViewEspecialidad.setText("Nutriología");
                    break;
                }
                case "2" : {
                    textViewEspecialidad.setText("Fisioterapeuta");
                    break;
                }
                case "3" : {
                    textViewEspecialidad.setText("Entrenador");
                    break;
                }
            }
        }

        public void setTextViewDescripcion(String descripcion) {
            textViewDescripcion.setText(descripcion);
        }

        public void setTextViewEscribir(final String correo) {

            textViewEscribir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {correo});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Antorcha - ");
                    intent.setType("message/rfc822");
                    activity.startActivity(Intent.createChooser(intent, null));
                }
            });
        }

        public void setTextViewLlamar(final String numero) {

            textViewLlamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numero));
                    activity.startActivity(intent);

                }
            });
        }

        public void setImageViewPrincipal(String sexo) {

            if (sexo.toUpperCase().equals("FEMENINO")) {
                imageViewPrincipal.setImageResource(R.drawable.default_perfil_mujer);
            }
        }
    }
}
