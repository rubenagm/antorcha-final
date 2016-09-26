package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosEliminar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.Modelos.Meta;
import com.mx.antorcha.Modelos.Pendiente;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ConexionEliminarMeta {

    static public void eliminarMeta (final Meta meta, final Context activity, final int tipo) {

        StringRequest postRequest = new StringRequest(Request.Method.DELETE, InfoConexion.URL_ELIMINAR_META + meta.getIdServidor(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Peticion", response);

                        try {
                            if ((new JSONObject(response).getString("eliminado")).equals("true")) {
                                ConexionBaseDatosEliminar conexionBaseDatosEliminar
                                        = new ConexionBaseDatosEliminar(activity);
                                conexionBaseDatosEliminar.eliminarMeta(meta.getId());

                                new ConexionBaseDatosEliminar(activity).eliminarPendiente(tipo);
                            } else {

                                if (tipo == 0) {
                                    String datos =
                                            meta.getId() + "";

                                    Pendiente pendiente = new Pendiente(0, Pendiente.ELIMINAR_META, datos);

                                    new ConexionBaseDatosInsertar(activity).insertarPendiente(pendiente);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            if (tipo == 0) {
                                String datos =
                                        meta.getId() + "";

                                Pendiente pendiente = new Pendiente(0, Pendiente.ELIMINAR_META, datos);

                                new ConexionBaseDatosInsertar(activity).insertarPendiente(pendiente);
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        if (tipo == 0) {
                            String datos =
                                    meta.getId() + "";

                            Pendiente pendiente = new Pendiente(0, Pendiente.ELIMINAR_META, datos);

                            new ConexionBaseDatosInsertar(activity).insertarPendiente(pendiente);
                        }
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
