package com.mx.antorcha.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mx.antorcha.R;

import java.util.ArrayList;

/**
 * Created by Ruben on 23/12/2015.
 */
public class AdaptadorSpinner extends ArrayAdapter<String> {

    public AdaptadorSpinner(Context context, ArrayList<String> strings) {
        super(context,R.layout.item_spinner ,R.id.spinner_texto, strings);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        convertView = vi.inflate(R.layout.item_spinner, null);

        TextView textViewTexto = (TextView) convertView.findViewById(R.id.spinner_texto);
        textViewTexto.setText(getItem(position));

        return convertView;

    }
}
