package com.mx.antorcha.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mx.antorcha.Adaptadores.AdaptadorEspacioCard;
import com.mx.antorcha.Adaptadores.AdaptadorEventoCard;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Modelos.Evento;
import com.mx.antorcha.R;

import java.util.ArrayList;

/**
 * Created by Ruben on 19/12/2015.
 */
public class FragmentActividadesEvento extends Fragment{

    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_actividades_evento, container, false);

        /******************************/
        ArrayList<Evento> eventos = new ConexionBaseDatosObtener(activity).obtenerEventos();
        /******************************/

        //se carga la parte del recycler view
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_actividades_evento);
        recyclerView.setLayoutManager(layoutManager);

        //Se agrega al adaptador
        AdaptadorEventoCard adaptadorEventoCard = new AdaptadorEventoCard(activity, eventos);
        recyclerView.setAdapter(adaptadorEventoCard);

        return rootView;
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
