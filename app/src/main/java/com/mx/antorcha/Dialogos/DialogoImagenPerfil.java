package com.mx.antorcha.Dialogos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.mx.antorcha.R;

/**
 * Created by Ruben on 22/12/2015.
 */
public class DialogoImagenPerfil extends DialogFragment {

    private Activity activity;
    public static final int INTENT_CAMARA = 5;
    public static final int INTENT_GALERIA = 10;
    public DialogoImagenPerfil () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogo_imagen_perfil, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        //Cuando se da click en la opción para tomar foto
        TextView textViewCamara = (TextView) view.findViewById(R.id.dialogo_imagen_perfil_camara);
        textViewCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                activity.startActivityForResult(intent, INTENT_CAMARA);

            }
        });

        //Cuando se da click en la opción para seleccionar foto
        TextView textViewGaleria = (TextView) view.findViewById(R.id.dialogo_imagen_perfil_galeria);
        textViewGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activity.startActivityForResult(i, INTENT_GALERIA);
                getDialog().dismiss();
            }
        });
        return view;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
