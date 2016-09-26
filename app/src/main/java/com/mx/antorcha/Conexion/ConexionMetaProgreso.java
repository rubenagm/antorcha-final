package com.mx.antorcha.Conexion;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosActualizar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosEliminar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Modelos.Meta;
import com.mx.antorcha.Modelos.MetaProgreso;
import com.mx.antorcha.Modelos.Pendiente;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_META_PROGRESO;
import static com.mx.antorcha.Modelos.Pendiente.SEP;

/**
 *
 */
public class ConexionMetaProgreso {

    private Context activity;
    private MetaProgreso metaProgreso;
    private int tipo;
    private ConexionBaseDatosActualizar conexionBaseDatosActualizar;

    public ConexionMetaProgreso(Context activity, MetaProgreso metaProgreso, int tipo) {
        this.activity = activity;
        this.metaProgreso = metaProgreso;
        this.tipo = tipo;
        conexionBaseDatosActualizar = new ConexionBaseDatosActualizar(activity);
    }

    public void insertarProgreso() {
        final Meta meta = new ConexionBaseDatosObtener(activity).obtenerMetas(metaProgreso.getIdMeta());

        if (meta != null && meta.getIdServidor() != 0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST, URL_META_PROGRESO,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Peticion", response);

                            try {
                                int idMetaProgteso = new JSONObject(response).getInt("id");

                                conexionBaseDatosActualizar.actualizarMetaProgreso(metaProgreso.getId(), idMetaProgteso);

                                if (tipo != 0) {
                                    new ConexionBaseDatosEliminar(activity).eliminarPendiente(tipo);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                                if (tipo == 0) {
                                    String datos = metaProgreso.getId()
                                            + SEP + metaProgreso.getIdMeta()
                                            + SEP + metaProgreso.getProgreso()
                                            + SEP + metaProgreso.getFecha();

                                    Pendiente pendiente = new Pendiente(0, Pendiente.META_PROGRESO, datos);
                                    ConexionBaseDatosInsertar conexionBaseDatosInsertar = new ConexionBaseDatosInsertar(activity);

                                    conexionBaseDatosInsertar.insertarPendiente(pendiente);
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();


                            if (tipo == 0) {
                                String datos = metaProgreso.getId()
                                        + SEP + metaProgreso.getIdMeta()
                                        + SEP + metaProgreso.getProgreso()
                                        + SEP + metaProgreso.getFecha();

                                Pendiente pendiente = new Pendiente(0, Pendiente.META_PROGRESO, datos);
                                ConexionBaseDatosInsertar conexionBaseDatosInsertar = new ConexionBaseDatosInsertar(activity);

                                conexionBaseDatosInsertar.insertarPendiente(pendiente);
                            }
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<>();
                    // the POST parameters:
                    params.put("idMeta", meta.getIdServidor() + "");
                    params.put("progreso", metaProgreso.getProgreso() + "");
                    params.put("fecha", metaProgreso.getFecha());

                    return params;
                }
            };

            Volley.newRequestQueue(activity).add(postRequest);

        }
    }
}
