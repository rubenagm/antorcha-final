package com.mx.antorcha.Dialogos;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.mx.antorcha.Adaptadores.AdaptadorListaMetas;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosActualizar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Conexion.ConexionEliminarMeta;
import com.mx.antorcha.Modelos.Meta;
import com.mx.antorcha.R;

import java.util.ArrayList;

/**
 *
 */
public class DialogoMeta extends DialogFragment {

    private int idMeta;
    private Context activity;
    private ListView listView;

    public DialogoMeta () {

    }

    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogo_actualizar_eliminar_meta, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        //On click de los textos
        TextView textViewActualizarProgreso = (TextView) view.findViewById(R.id.dialogo_meta_actualizar_progreso);
        textViewActualizarProgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoInsertarProgreso dialogoInsertarProgreso = new DialogoInsertarProgreso();
                dialogoInsertarProgreso.setIdMeta(idMeta);
                dialogoInsertarProgreso.setActivity(activity);
                dialogoInsertarProgreso.setListView(listView);
                dialogoInsertarProgreso.setFragmentManager(fragmentManager);
                dialogoInsertarProgreso.show(fragmentManager, "dialogo_insertar_progreso");

                getDialog().dismiss();
            }
        });

        //Al dar click en eliminar
        TextView textViewEliminar = (TextView) view.findViewById(R.id.dialogo_meta_eliminar);

        textViewEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cuando se responde sí se borra la meta (Lógicamente)
                        Meta meta = new ConexionBaseDatosObtener(activity).obtenerMetas(idMeta);
                        ConexionBaseDatosActualizar conexionBaseDatosActualizar =
                                new ConexionBaseDatosActualizar(activity);
                        conexionBaseDatosActualizar.borrarMeta(idMeta);
                        ConexionEliminarMeta.eliminarMeta(meta, activity, 0);

                        if (listView != null) {
                            //Se actualiza la lista
                            ConexionBaseDatosObtener conexionBaseDatosObtener = new ConexionBaseDatosObtener(activity);
                            ArrayList<Meta> metas = conexionBaseDatosObtener.obtenerMetas();

                            AdaptadorListaMetas adaptadorListaMetas = new AdaptadorListaMetas(activity, metas, fragmentManager);

                            listView.setAdapter(adaptadorListaMetas);
                        }

                        getDialog().dismiss();

                    }
                }).setNegativeButton("No", null)
                        .setIcon(R.drawable.logo_antorcha)
                        .setMessage("¿Seguro que desea eliminar la meta?")
                        .setTitle("Eliminar meta")
                        .show();
            }
        });

        return view;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
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


}
