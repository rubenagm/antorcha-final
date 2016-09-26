package com.mx.antorcha.Conexion;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.SharedPreferences.DisciplinasDeportesSharedPreferences;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_OBTENER_DEPORTES;
import static com.mx.antorcha.Conexion.InfoConexion.URL_OBTENER_DISCIPLINAS;

/**
 * Created by root on 8/04/16.
 */
public class ConexionObtenerDeporte {

    private Context context;

    public ConexionObtenerDeporte(Context context) {
        this.context = context;
    }

    public void obtenerDeportes(){
        StringRequest postRequest = new StringRequest(Request.Method.GET, URL_OBTENER_DEPORTES + new MiembroSharedPreferences(context).getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Si todo sale correcto, después del registro se hace...
                        Log.i("Peticion registro", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayList<String> strings = new ArrayList<>();

                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(x);
                                String disciplina = jsonObject.getString("idDeporte");

                                strings.add(disciplina);
                            }

                            new DisciplinasDeportesSharedPreferences(context).setDeportes(strings);

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

                return params;
            }
        };

        Volley.newRequestQueue(context).add(postRequest);
    }
}
