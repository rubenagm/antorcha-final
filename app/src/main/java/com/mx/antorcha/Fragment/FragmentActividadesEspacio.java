package com.mx.antorcha.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mx.antorcha.Adaptadores.AdaptadorEspacioCard;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Modelos.EspacioDeportivo;
import com.mx.antorcha.R;

import java.util.ArrayList;

/**
 *
 */
public class FragmentActividadesEspacio extends Fragment{

    private Activity activity;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_actividades_espacio, container, false);

        /*************** VARIABLES TEMPORALES PARA PRUEBAS ***************/
        ArrayList<EspacioDeportivo> espacioDeportivos = new ConexionBaseDatosObtener(activity).obtenerEspacios();
        /******************************/

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_actividades_espacio);
        recyclerView.setLayoutManager(layoutManager);
        AdaptadorEspacioCard adaptadorEspacioCard = new AdaptadorEspacioCard(activity, espacioDeportivos);
        adaptadorEspacioCard.setFragmentManager(fragmentManager);
        recyclerView.setAdapter(adaptadorEspacioCard);
        return rootView;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
