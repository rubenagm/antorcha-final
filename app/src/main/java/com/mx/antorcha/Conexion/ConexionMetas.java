package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

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
import com.mx.antorcha.Modelos.Pendiente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_META;
import static com.mx.antorcha.Modelos.Pendiente.SEP;

/**
 *
 */
public class ConexionMetas {

    private Activity activity;
    private Context context;
    private int id;
    private Meta meta;
    private int tipo;
    public ConexionMetas (Activity activity, Meta meta , int tipo) {
        this.activity = activity;
        this.meta = meta;
        this.tipo = tipo;
    }

    public ConexionMetas (Context activity, Meta meta , int tipo) {
        this.context = activity;
        this.meta = meta;
        this.tipo = tipo;
    }

    public void insertarMetas() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_META,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int idServidor = jsonObject.getInt("id");
                                if (activity != null) {
                                    new ConexionBaseDatosActualizar(activity).actualizarMetaIdServidor(meta.getId(), idServidor);
                                    activity.finish();
                                } else {
                                    new ConexionBaseDatosActualizar(context).actualizarMetaIdServidor(meta.getId(), idServidor);
                                    new ConexionBaseDatosEliminar(context).eliminarPendiente(tipo);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                                if (tipo == 0) {
                                    String datos =
                                            meta.getId()
                                            + SEP +meta.getInicio()
                                            + SEP + meta.getFin()
                                            + SEP + meta.getNombre()
                                            + SEP + meta.getFechaFin()
                                            + SEP + meta.getFechaInicio()
                                            + SEP + meta.getTipoMedida();

                                    new ConexionBaseDatosInsertar(activity).
                                            insertarPendiente(new Pendiente(0, Pendiente.META, datos));

                                }
                                 if (activity != null) {
                                     activity.finish();
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
                                        meta.getId()
                                                + SEP +meta.getInicio()
                                                + SEP + meta.getFin()
                                                + SEP + meta.getNombre()
                                                + SEP + meta.getFechaFin()
                                                + SEP + meta.getFechaInicio()
                                                + SEP + meta.getTipoMedida();

                                new ConexionBaseDatosInsertar(activity).
                                        insertarPendiente(new Pendiente(0, Pendiente.META, datos));
                            }
                            if (activity != null) {
                                activity.finish();
                            }
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    // the POST parameters:
                    params.put("idMiembro", id + "");
                    params.put("nombre", meta.getNombre());
                    params.put("fechaInicio", meta.getFechaInicio());
                    params.put("fechaFin", meta.getFechaFin());
                    params.put("inicio", meta.getInicio() + "");
                    params.put("fin", meta.getFin() + "");
                    params.put("tipoMedida", meta.getTipoMedida());
                    return params;
                }
            };

        if (activity != null) {
            Volley.newRequestQueue(activity).add(postRequest);
        } else {
            Volley.newRequestQueue(context).add(postRequest);
        }

    }

    public void setId(int id) {
        this.id = id;
    }
}
