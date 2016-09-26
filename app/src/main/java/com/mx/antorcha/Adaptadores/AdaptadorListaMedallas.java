package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.antorcha.Modelos.Medalla;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MedallasSharedPreferences;

import java.util.ArrayList;

/**
 *
 */
public class AdaptadorListaMedallas extends RecyclerView.Adapter<AdaptadorListaMedallas.ViewHolder> {

    private ArrayList<Medalla> medallas;
    static public Context activity;
    static TextView textViewNombre;
    static TextView textViewTipo;
    static TextView textViewDescripcion;
    static ImageView imageViewMedallaPrincipal;
    static MedallasSharedPreferences medallasSharedPreferences;

    public AdaptadorListaMedallas (Context activity, ArrayList<Medalla> medallas) {
        this.medallas = medallas;
        this.activity = activity;
        medallasSharedPreferences = new MedallasSharedPreferences(activity);
    }

    @Override
    public AdaptadorListaMedallas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_medalla, parent, false);

        AdaptadorListaMedallas.ViewHolder viewHolder = new AdaptadorListaMedallas.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdaptadorListaMedallas.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        holder.setMedalla(medallas.get(position));

    }

    @Override
    public int getItemCount() {
        return medallas.size();
    }

    public void setTextViewNombre(TextView textViewNombre) {
        this.textViewNombre = textViewNombre;
    }

    public void setTextViewTipo(TextView textViewTipo) {
        this.textViewTipo = textViewTipo;
    }

    public void setTextViewDescripcion(TextView textViewDescripcion) {
        this.textViewDescripcion = textViewDescripcion;
    }

    public void setImageViewMedallaPrincipal(ImageView imageViewMedallaPrincipal) {
        this.imageViewMedallaPrincipal = imageViewMedallaPrincipal;
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        private Medalla medalla;
        private ImageView imageViewMedalla;

        public ViewHolder(View itemView) {
            super(itemView);

            //Del itemView se jalan los elementos del xml
            imageViewMedalla = (ImageView) itemView.findViewById(R.id.item_medallas_medalla);

            //on clic de la medalla
            imageViewMedalla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textViewNombre != null) {

                        if (medallasSharedPreferences.medallaObtenida(medalla.getId())) {
                            textViewNombre.setText(medalla.getNombre());
                            textViewDescripcion.setText(medalla.getDescripcion());
                            mostrarMedalla(imageViewMedallaPrincipal, medalla.getTipo(), activity, textViewTipo);
                        } else {
                            textViewNombre.setText(medalla.getNombre());
                            textViewDescripcion.setText(medalla.getComoSeLogra());
                            textViewTipo.setText("");
                            imageViewMedallaPrincipal.setImageDrawable(activity.getResources().getDrawable(R.drawable.oculta));
                        }
                    }
                }
            });

        }

        public void setMedalla (Medalla medalla) {
            this.medalla = medalla;

            if (medallasSharedPreferences.medallaObtenida(this.medalla.getId())) {
                mostrarMedallaPequeña(imageViewMedalla, medalla.getTipo(), activity);
                imageViewMedalla.setAlpha(1.0f);
            }
        }
    }

    static public void mostrarMedallaPequeña(ImageView imageView, int tipo, Context activity){

        switch (tipo) {
            case 1 : {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.prometeo_s));
                break;
            }
            case 2 : {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.antorcha_s));
                break;
            }
            case 3 : {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.fuego_s));
                break;
            }
            case 4 : {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.flama_s));
                break;
            }
            case 5 : {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.chispa_s));
                break;
            }

        }
    }

    static public void mostrarMedalla(ImageView imageView, int tipo, Context activity, TextView textView){

        switch (tipo) {
            case 1 : {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.prometeo_s));
                textView.setText("Prometeo");
                break;
            }
            case 2 : {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.antorcha_s));
                textView.setText("Antorcha");
                break;
            }
            case 3 : {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.fuego_s));
                textView.setText("Fuego");
                break;
            }
            case 4 : {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.flama_s));
                textView.setText("Flama");
                break;
            }
            case 5 : {
                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.chispa_s));
                textView.setText("Chispa");
                break;
            }

        }
    }
}
