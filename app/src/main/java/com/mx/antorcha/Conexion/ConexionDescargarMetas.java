package com.mx.antorcha.Conexion;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.Adaptadores.AdaptadorListaMetas;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Modelos.Meta;
import com.mx.antorcha.Modelos.MetaProgreso;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_DESCARGAR_METAS;
import static com.mx.antorcha.Conexion.InfoConexion.URL_DESCARGAR_PROGRESO_METAS;

/**
 *
 */
public class ConexionDescargarMetas {

    private Context activity;
    private MiembroSharedPreferences miembroSharedPreferences;
    private ConexionBaseDatosInsertar conexionBaseDatosInsertar;
    private FragmentManager fragmentManager;
    private ListView listView;

    public ConexionDescargarMetas(Context activity) {
        this.activity = activity;
        conexionBaseDatosInsertar = new ConexionBaseDatosInsertar(activity);
        miembroSharedPreferences = new MiembroSharedPreferences(activity);
    }


    public void obtenerMetas () {

        StringRequest postRequest = new StringRequest(Request.Method.GET, URL_DESCARGAR_METAS
                + miembroSharedPreferences.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(x);

                                Meta meta = new Meta();
                                meta.setFechaFin(jsonObject.getString("fechaFin"));
                                meta.setFechaInicio(jsonObject.getString("fechaInicio"));
                                meta.setFin(jsonObject.getInt("fin"));
                                meta.setInicio(jsonObject.getInt("inicio"));
                                meta.setIdServidor(jsonObject.getInt("id"));
                                meta.setNombre(jsonObject.getString("nombre"));
                                meta.setTipoMedida(jsonObject.getString("tipoMedida"));
                                meta.setEstado(1);

                                final long id = conexionBaseDatosInsertar.insertarMeta(meta);

                                StringRequest post = new StringRequest(Request.Method.GET, URL_DESCARGAR_PROGRESO_METAS
                                        + meta.getIdServidor(),
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {
                                                    JSONArray jsonArray = new JSONArray(response);

                                                    for (int x = 0; x < jsonArray.length(); x++) {
                                                        JSONObject jsonObject = jsonArray.getJSONObject(x);

                                                        MetaProgreso metaProgreso = new MetaProgreso();
                                                        metaProgreso.setIdServidor(jsonObject.getInt("id"));
                                                        metaProgreso.setFecha(jsonObject.getString("fecha"));
                                                        metaProgreso.setIdMeta((int) id);
                                                        metaProgreso.setProgreso(jsonObject.getDouble("progreso"));

                                                        conexionBaseDatosInsertar.insertarMetaProgreso(metaProgreso);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                if (fragmentManager != null && listView != null) {
                                                    ConexionBaseDatosObtener conexionBaseDatosObtener = new ConexionBaseDatosObtener(activity);

                                                    ArrayList<Meta> metas =conexionBaseDatosObtener.obtenerMetas();

                                                    AdaptadorListaMetas adaptadorListaMetas = new AdaptadorListaMetas(activity, metas, fragmentManager);
                                                    listView.setAdapter(adaptadorListaMetas);
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


                                        return params;
                                    }
                                };

                                Volley.newRequestQueue(activity).add(post);
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


                return params;
            }
        };

        Volley.newRequestQueue(activity).add(postRequest);


    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }
}
