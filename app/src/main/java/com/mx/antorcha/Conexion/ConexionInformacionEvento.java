package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.Modelos.Evento;

import org.json.JSONException;
import org.json.JSONObject;

import static com.mx.antorcha.Conexion.InfoConexion.URL_INFORMACION_EVENTO;
import java.util.HashMap;
import java.util.Map;


/**
 *
 */
public class ConexionInformacionEvento {

    private Activity activity;
    private String idEvento;

    public ConexionInformacionEvento(Activity activity, String idEvento) {
        this.activity = activity;
        this.idEvento = idEvento;
    }

    public void obtenerEvento (){

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_INFORMACION_EVENTO + idEvento,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("InformacionEvento", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Evento evento = new Evento (
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("nombre"),
                                    jsonObject.getString("descripcion"),
                                    jsonObject.getString("domicilio"),
                                    jsonObject.getString("colonia"),
                                    jsonObject.getString("codigoPostal"),
                                    jsonObject.getString("municipio"),
                                    jsonObject.getString("ciudad"),
                                    jsonObject.getString("estado"),
                                    jsonObject.getString("telefono"),
                                    jsonObject.getString("fechaIncio"),
                                    jsonObject.getString("fechaFin"),
                                    jsonObject.getDouble("latitud"),
                                    jsonObject.getDouble("longitud"));


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
                //No hay parametros
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(postRequest);

    }
}
