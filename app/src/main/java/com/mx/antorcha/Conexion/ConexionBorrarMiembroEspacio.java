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
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_BORRAR_MIEMBRO_ESPACIO;
import static com.mx.antorcha.Conexion.InfoConexion.URL_INSERTAR_DISCIPLINA;
import static com.mx.antorcha.Modelos.Pendiente.SEP;

/**
 * Created by root on 29/03/16.
 */
public class ConexionBorrarMiembroEspacio {

    private Context context;
    private int idMiembro;
    private int idEspacio;
    private int tipo;

    public ConexionBorrarMiembroEspacio(Context context, int idMiembro, int idEspacio, int tipo) {
        this.context = context;
        this.idMiembro = idMiembro;
        this.idEspacio = idEspacio;
        this.tipo = tipo;
    }

    public void borrarMiembroEspacio () {
        StringRequest postRequest = new StringRequest(Request.Method.DELETE, URL_BORRAR_MIEMBRO_ESPACIO,
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
                                                    + SEP + idEspacio;

                                    Pendiente pendiente = new Pendiente(0, Pendiente.BORRAR_MIEMBRO_ESPACIO, datos);
                                    new ConexionBaseDatosInsertar(context).insertarPendiente(pendiente);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            if (tipo == 0) {
                                String datos =
                                        idMiembro
                                                + SEP + idEspacio;

                                Pendiente pendiente = new Pendiente(0, Pendiente.BORRAR_MIEMBRO_ESPACIO, datos);
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
                                            + SEP + idEspacio;

                            Pendiente pendiente = new Pendiente(0, Pendiente.BORRAR_MIEMBRO_ESPACIO, datos);
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
                params.put("idEspacio", idEspacio + "");

                return params;
            }
        };

        Volley.newRequestQueue(context).add(postRequest);
    }
}
