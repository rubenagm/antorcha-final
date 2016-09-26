package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.Adaptadores.AdaptadorListaMedallas;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Modelos.Medalla;
import com.mx.antorcha.SharedPreferences.MedallasSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ConexionMedallas  {

    private Context activity;
    private RecyclerView recyclerView;
    private TextView textView;

    public ConexionMedallas(Context activity) {

        this.activity = activity;
    }

    public void obtenerMedallas() {

        final ConexionBaseDatosInsertar conexionBaseDatosInsertar = new ConexionBaseDatosInsertar(activity);

        StringRequest postRequest = new StringRequest(Request.Method.GET, InfoConexion.URL_MEDALLA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Peticion", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int x = 0; x < jsonArray.length(); x++) {

                                //Se obtienen los datos y se mandan a la medalla
                                Medalla medalla = new Medalla(
                                        Integer.parseInt(jsonArray.getJSONObject(x).getString("idMedalla")),
                                        jsonArray.getJSONObject(x).getString("nombreMedalla"),
                                        Integer.parseInt(jsonArray.getJSONObject(x).getString("tipoMedalla")),
                                        jsonArray.getJSONObject(x).getString("descripcionMedalla"),
                                        jsonArray.getJSONObject(x).getString("comoObtener"),
                                        jsonArray.getJSONObject(x).getString("imagenMedalla")
                                );

                                conexionBaseDatosInsertar.insertarMedalla(medalla);

                                if (recyclerView != null) {

                                    ArrayList<Medalla> medallas =
                                            new ConexionBaseDatosObtener(activity).obtenerMedallas();
                                    AdaptadorListaMedallas adaptadorListaMedallas =
                                            new AdaptadorListaMedallas(activity, medallas);
                                    recyclerView.setAdapter(adaptadorListaMedallas);

                                    textView.setText(
                                            new MedallasSharedPreferences(activity).totalObtenidas()
                                                    + "/" + medallas.size());
                                }

                            }

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
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
