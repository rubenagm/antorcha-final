package com.mx.antorcha.Dialogos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mx.antorcha.Adaptadores.AdaptadorListaMetas;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Conexion.ConexionMetaProgreso;
import com.mx.antorcha.Modelos.MetaProgreso;
import com.mx.antorcha.R;

/**
 * C
 */
public class DialogoInsertarProgreso extends DialogFragment {

    private int idMeta;
    private Context activity;
    private ListView listView;
    private FragmentManager fragmentManager;
    private EditText editTextNuevoProgreso;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogo_insertar_progreso, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        editTextNuevoProgreso = (EditText) view.findViewById(R.id.dialogo_nuevo_progreso);

        Button button = (Button) view.findViewById(R.id.dialogo_guardar_nuevo_progreso);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MetaProgreso metaProgreso = new MetaProgreso(
                        0, idMeta,
                        Double.parseDouble(editTextNuevoProgreso.getText().toString()),
                        "2015-11-10", 0);

                ConexionBaseDatosInsertar conexionBaseDatosInsertar = new ConexionBaseDatosInsertar(activity);
                long id = conexionBaseDatosInsertar.insertarMetaProgreso(metaProgreso);
                metaProgreso.setId((int) id);

                //Se intenta insertar en el servidor
                ConexionMetaProgreso conexionMetaProgreso = new ConexionMetaProgreso(activity, metaProgreso, 0);
                conexionMetaProgreso.insertarProgreso();

                if (listView != null) {
                    ConexionBaseDatosObtener conexionBaseDatosObtener = new ConexionBaseDatosObtener(activity);
                    AdaptadorListaMetas adaptadorListaMetas = new AdaptadorListaMetas(activity, conexionBaseDatosObtener.obtenerMetas(), fragmentManager );

                    listView.setAdapter(adaptadorListaMetas);
                }

                getDialog().dismiss();
            }
        });
        return view;
    }


    public void setIdMeta(int idMeta) {
        this.idMeta = idMeta;
    }

    public void setActivity(Context activity) {
        this.activity = activity;
    }


    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
