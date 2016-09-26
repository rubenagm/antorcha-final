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

import static com.mx.antorcha.Conexion.InfoConexion.URL_INSERTAR_MIEMBRO_ESPACIO;
import static com.mx.antorcha.Modelos.Pendiente.SEP;

/**
 * Created by root on 29/03/16.
 */
public class ConexionInsertarMiembroEspacio {

    private Context context;
    private int idMiembro;
    private int idEspacio;
    private int idMembresia;
    private int tipo;

    public ConexionInsertarMiembroEspacio(Context context, int idMiembro, int idEspacio, int idMembresia, int tipo) {
        this.context = context;
        this.idMiembro = idMiembro;
        this.idEspacio = idEspacio;
        this.idMembresia = idMembresia;
        this.tipo = tipo;
    }

    public void insertarMiembroEspacio(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_INSERTAR_MIEMBRO_ESPACIO,
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
                                            + SEP + idEspacio
                                            + SEP + idMembresia;

                                    Pendiente pendiente = new Pendiente(0, Pendiente.MIEMBRO_ESPACIO, datos);
                                    new ConexionBaseDatosInsertar(context).insertarPendiente(pendiente);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            if (tipo == 0) {
                                String datos =
                                        idMiembro
                                                + SEP + idEspacio
                                                + SEP + idMembresia;

                                Pendiente pendiente = new Pendiente(0, Pendiente.MIEMBRO_ESPACIO, datos);
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
                                            + SEP + idEspacio
                                            + SEP + idMembresia;

                            Pendiente pendiente = new Pendiente(0, Pendiente.MIEMBRO_ESPACIO, datos);
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
                params.put("idEspacioDeportivo", idEspacio + "");
                params.put("idMembresia", idMembresia + "");

                return params;
            }
        };

        Volley.newRequestQueue(context).add(postRequest);
    }
}
