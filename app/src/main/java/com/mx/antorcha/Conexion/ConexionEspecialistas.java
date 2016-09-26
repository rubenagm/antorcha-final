package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.Adaptadores.AdaptadorEspecialistaCard;
import com.mx.antorcha.Modelos.Especialista;
import com.mx.antorcha.SharedPreferences.MedallasSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_OBTENER_ESPECIALIDADES;

/**
 * Created by Ruben on 06/03/2016
 */
public class ConexionEspecialistas {

    private Activity activity;
    private RecyclerView recyclerView;
    private Spinner spinnerCiudad;
    private Spinner spinnerTipo;

    public ConexionEspecialistas(Activity activity, RecyclerView recyclerView, Spinner spinnerCiudad, Spinner spinnerTipo) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.spinnerCiudad = spinnerCiudad;
        this.spinnerTipo = spinnerTipo;
    }

    public void obtenerEspecialistas () {
        String tipo = "";

        switch (spinnerTipo.getSelectedItem().toString()) {
            case "Nutriologos" : {
                tipo = "1";
                break;
            }
            case "Fisioterapeutas" : {
                tipo = "2";
                break;
            }
            case "Entrenadores" : {
                tipo = "3";
                break;
            }
        }

        StringRequest postRequest = new StringRequest(Request.Method.GET, URL_OBTENER_ESPECIALIDADES
                + tipo + "/"
                + spinnerCiudad.getSelectedItem().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("ObtenerEspecialidades", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayList<Especialista> especialistas = new ArrayList<>();

                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(x);
                                especialistas.add(new Especialista(jsonObject.getString("nombre"),
                                        jsonObject.getString("titulo"),
                                        jsonObject.getString("especialidad"),
                                        jsonObject.getString("descripcion"),
                                        jsonObject.getString("telefono"),
                                        jsonObject.getString("correo"),
                                        jsonObject.getString("sexo")));
                            }

                            AdaptadorEspecialistaCard adaptadorEspecialistaCard = new AdaptadorEspecialistaCard(activity, especialistas);
                            recyclerView.setAdapter(adaptadorEspecialistaCard);
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
}
