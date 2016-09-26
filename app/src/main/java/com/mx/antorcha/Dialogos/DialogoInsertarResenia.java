package com.mx.antorcha.Dialogos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.mx.antorcha.Conexion.ConexionResenia;
import com.mx.antorcha.R;

/**
 *
 */
public class DialogoInsertarResenia extends DialogFragment {

    private int tipo;
    private int id;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogo_insertar_resenia, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        TextView textViewCancelar = (TextView) view.findViewById(R.id.dialogo_insertar_resenia_cancelar);
        TextView textViewEnviar = (TextView) view.findViewById(R.id.dialogo_insertar_resenia_enviar);
        final EditText editTextResenia = (EditText) view.findViewById(R.id.dialogo_insertar_resenia_texto);
        final EditText editTextTitulo = (EditText) view.findViewById(R.id.dialogo_insertar_resenia_titulo) ;

        //El click en cancelar
        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //El click en enviar
        textViewEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog();

                ConexionResenia conexionResenia = new ConexionResenia(activity,
                        editTextResenia.getText().toString(), id + "", tipo + "", editTextTitulo.getText().toString());
                conexionResenia.setDialogoInsertarResenia(getDialog());
                conexionResenia.execute();
            }
        });

        return view;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
