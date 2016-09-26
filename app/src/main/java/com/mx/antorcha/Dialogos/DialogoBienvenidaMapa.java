package com.mx.antorcha.Dialogos;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.mx.antorcha.R;

/**
 * Created by root on 10/04/16.
 */
public class DialogoBienvenidaMapa extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogo_bienvenida, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        view.findViewById(R.id.dialogo_bienvenida_aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
