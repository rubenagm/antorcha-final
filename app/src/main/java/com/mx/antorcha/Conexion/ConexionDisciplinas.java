package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.Adaptadores.AdaptadorListaNuevosDeportes;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Modelos.Disciplina;
import com.mx.antorcha.Modelos.Medalla;
import com.mx.antorcha.SharedPreferences.DisciplinasDeportesSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_DISCIPLINA;
import static com.mx.antorcha.Conexion.InfoConexion.URL_MEDALLA;


/**
 *
 */
public class ConexionDisciplinas extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private ListView listView;
    private ArrayList<String> stringDisciplinas;
    private ArrayList<String> stringDeportes;

    public ConexionDisciplinas(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        final ConexionBaseDatosInsertar conexionBaseDatosInsertar = new ConexionBaseDatosInsertar(activity);

        StringRequest postRequest = new StringRequest(Request.Method.GET, URL_DISCIPLINA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Peticion", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int x = 0; x < jsonArray.length(); x++) {

                                //Se obtienen los datos y se mandan a la medalla
                                Disciplina disciplina = new Disciplina(
                                        Integer.parseInt(jsonArray.getJSONObject(x).getString("id")),
                                        jsonArray.getJSONObject(x).getString("nombre"),
                                        jsonArray.getJSONObject(x).getString("descripcion"),
                                        ""
                                );

                                conexionBaseDatosInsertar.insertarDisciplina(disciplina);
                            }

                            ArrayList<Disciplina> disciplinas =
                                    new ConexionBaseDatosObtener(activity).obtenerDisciplinas();

                            AdaptadorListaNuevosDeportes adaptadorListaNuevosDeportes
                                    = new AdaptadorListaNuevosDeportes(activity, disciplinas);

                            stringDisciplinas
                                    = new DisciplinasDeportesSharedPreferences(activity).getDisciplinas();

                            stringDeportes
                                    = new DisciplinasDeportesSharedPreferences(activity).getDeportes();

                            adaptadorListaNuevosDeportes.setStringDeportes(stringDeportes);
                            adaptadorListaNuevosDeportes.setStringDisciplinas(stringDisciplinas);

                            listView.setAdapter(adaptadorListaNuevosDeportes);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:

                return params;
            }
        };
        Volley.newRequestQueue(activity).add(postRequest);


        return null;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setStringDisciplinas(ArrayList<String> stringDisciplinas) {
        this.stringDisciplinas = stringDisciplinas;
    }

    public void setStringDeportes(ArrayList<String> stringDeportes) {
        this.stringDeportes = stringDeportes;
    }
}
