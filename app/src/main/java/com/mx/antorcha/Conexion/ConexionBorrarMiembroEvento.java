package com.mx.antorcha.Conexion;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosEliminar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.Modelos.Pendiente;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_BORRAR_MIEMBRO_EVENTO;
import static com.mx.antorcha.Modelos.Pendiente.SEP;

/**
 * Created by root on 29/03/16.
 */
public class ConexionBorrarMiembroEvento {

    private Context context;
    private int idMiembro;
    private int idEvento;
    private int tipo;

    public ConexionBorrarMiembroEvento(Context context, int idMiembro, int idEvento, int tipo) {
        this.context = context;
        this.idMiembro = idMiembro;
        this.idEvento = idEvento;
        this.tipo = tipo;
    }

    public void borrarMiembroEvento () {
        StringRequest postRequest = new StringRequest(Request.Method.DELETE, URL_BORRAR_MIEMBRO_EVENTO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Si todo sale correcto, después del registro se hace...
                        Log.i("Peticion registro", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("Respuesta")) {
                                //Nada todo correcto
                                new ConexionBaseDatosEliminar(context).eliminarPendiente(tipo);
                            } else {

                                if (tipo == 0) {
                                    String datos =
                                            idMiembro
                                                    + SEP + idEvento;

                                    Pendiente pendiente = new Pendiente(0, Pendiente.BORRAR_MIEMBRO_EVENTO, datos);
                                    new ConexionBaseDatosInsertar(context).insertarPendiente(pendiente);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            if (tipo == 0) {
                                String datos =
                                        idMiembro
                                                + SEP + idEvento;

                                Pendiente pendiente = new Pendiente(0, Pendiente.BORRAR_MIEMBRO_EVENTO, datos);
                                new ConexionBaseDatosInsertar(context).insertarPendiente(pendiente);
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
                                    idMiembro
                                            + SEP + idEvento;

                            Pendiente pendiente = new Pendiente(0, Pendiente.BORRAR_MIEMBRO_EVENTO, datos);
                            new ConexionBaseDatosInsertar(context).insertarPendiente(pendiente);
                        }

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("idMiembro", idMiembro + "");
                params.put("idEvento", idEvento + "");

                return params;
            }
        };

        Volley.newRequestQueue(context).add(postRequest);
    }
}
