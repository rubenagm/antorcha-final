package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Modelos.Meta;
import com.mx.antorcha.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Ruben on 09/03/2016
 */
public class AdaptadorListaAyudanosMejorar extends ArrayAdapter<String>{

    private ArrayList<String> strings;
    private Activity activity;

    public AdaptadorListaAyudanosMejorar(Activity activity, ArrayList<String> strings) {
        super(activity, R.layout.item_ayudanos_mejorar, strings);
        this.strings = strings;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String pregunta = getItem(position);

        LayoutInflater vi = LayoutInflater.from(getContext());
        convertView = vi.inflate(R.layout.item_ayudanos_mejorar, null);

        TextView textView = (TextView) convertView.findViewById(R.id.ayudanos_mejorar_pregunta);
        ImageView imageViewFlecha = (ImageView) convertView.findViewById(R.id.ayudanos_mejorar_flecha);

        textView.setText(pregunta);

        if (position == 0 || position == 5 || position == 10) {
            textView.setPadding(0, 25, 0, 0);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setAlpha(0.87f);
        } else {
            AdaptadorSVG.mostrarImagen(imageViewFlecha, activity, R.raw.icono_flecha_derecha);
            RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.ayudanos_mejorar_click);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"ruben.agm02@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, pregunta);
                    intent.setType("message/rfc822");
                    activity.startActivity(Intent.createChooser(intent, null));
                }
            });
        }
//        textView.setPadding(0, 20, 0, 0);

        return convertView;

    }
}
