package com.mx.antorcha.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mx.antorcha.Adaptadores.AdaptadorListaDeportes;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Modelos.Deporte;
import com.mx.antorcha.Modelos.Disciplina;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.DisciplinasDeportesSharedPreferences;

import java.util.ArrayList;

/**
 *
 */
public class FragmentPerfilDeportes extends Fragment {

    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil_deportes, container, false);

        /******************* VARIABLES TEMPORALES PARA PRUEBAS ***********************
        ArrayList<Deporte> deportes = new ArrayList<>();
        deportes.add(new Deporte());
        /******************************************/

        ConexionBaseDatosObtener conexionBaseDatosObtener = new ConexionBaseDatosObtener(activity);

        DisciplinasDeportesSharedPreferences disciplinasDeportesSharedPreferences = new DisciplinasDeportesSharedPreferences(activity);
        ArrayList<Disciplina> deportes = conexionBaseDatosObtener.obtenerDisciplinas();
        ArrayList<String> disciplinasDeportes = disciplinasDeportesSharedPreferences.getDisciplinas();

        ArrayList<Disciplina> deportesAuxiliar = new ArrayList<>();

        for (Disciplina deporte : deportes) {

            if (disciplinasDeportes.contains(deporte.getId() + "")) {
                deportesAuxiliar.add(deporte);
            }
        }

        ListView listView  = (ListView) rootView.findViewById(R.id.perfil_deportes_lista_deportes);
        AdaptadorListaDeportes adaptadorListaDeportes = new AdaptadorListaDeportes(activity, R.id.perfil_deportes_lista_deportes, deportesAuxiliar);
        listView.setAdapter(adaptadorListaDeportes);
        return rootView;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


}
