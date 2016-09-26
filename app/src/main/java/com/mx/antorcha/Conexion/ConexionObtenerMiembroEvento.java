package com.mx.antorcha.Conexion;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.Modelos.EspacioDeportivo;
import com.mx.antorcha.Modelos.Evento;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_OBTENER_MIEMBRO_ESPACIO;
import static com.mx.antorcha.Conexion.InfoConexion.URL_OBTENER_MIEMBRO_EVENTO;

/**
 * Created by root on 29/03/16.
 */
public class ConexionObtenerMiembroEvento {

    private Context context;

    public ConexionObtenerMiembroEvento(Context context) {
        this.context = context;
    }

    public void obtenerEventos () {
        StringRequest postRequest = new StringRequest(Request.Method.GET, URL_OBTENER_MIEMBRO_EVENTO
                + new MiembroSharedPreferences(context).getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Si todo sale correcto, después del registro se hace...
                        Log.i("Peticion registro", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject jsonObject = jsonArray.getJSONArray(x).getJSONObject(0);

                                Evento evento = new Evento (
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("nombre"),
                                        jsonObject.getString("descripcion"),
                                        jsonObject.getString("direccion"),
                                        jsonObject.getString("colonia"),
                                        jsonObject.getString("codigoPostal"),
                                        jsonObject.getString("municipio"),
                                        jsonObject.getString("ciudad"),
                                        jsonObject.getString("estado"),
                                        jsonObject.getString("telefono"),
                                        jsonObject.getString("fechaInicio"),
                                        "",
                                        jsonObject.getDouble("latitud"),
                                        jsonObject.getDouble("longitud"));

                                new ConexionBaseDatosInsertar(context).insertarEvento(evento);

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

                return params;
            }
        };

        Volley.newRequestQueue(context).add(postRequest);

    }
}
