package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.Adaptadores.AdaptadorListaMedallas;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Modelos.Medalla;
import com.mx.antorcha.SharedPreferences.MedallasSharedPreferences;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ConexionActualizarPerfil {

    private Activity activity;

    public ConexionActualizarPerfil(Activity activity) {

        this.activity = activity;
    }

    static public void actualizar (final String nombre, final String fechaNacimiento, final String descripcion, final String intereses, final Context activity, final String gcm) {
        StringRequest postRequest = new StringRequest(Request.Method.PUT, InfoConexion.URL_ACTUALIZAR_MIEMBRO
                + (new MiembroSharedPreferences(activity).getId()),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Peticion", response);

                        try {
                            if ((new JSONObject(response).getString("actualizado")).equals("true")) {
                                MiembroSharedPreferences miembroSharedPreferences = new MiembroSharedPreferences(activity);
                                miembroSharedPreferences.setActualizar(0);

                                if (miembroSharedPreferences.getGCM().length() > 1) {
                                    miembroSharedPreferences.setRegistrado(2);
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
                params.put("nombre", nombre);
                params.put("fechaNacimiento", fechaNacimiento);
                params.put("queBusco", intereses);
                params.put("descripcion", descripcion);
                params.put("gcm", gcm);
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(postRequest);
    }


}
