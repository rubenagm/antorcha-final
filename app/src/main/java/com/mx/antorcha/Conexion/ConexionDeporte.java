package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosInsertar;
import com.mx.antorcha.Modelos.Deporte;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_DEPORTE;

/**
 *
 */
public class ConexionDeporte extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private ListView listView;

    public ConexionDeporte(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        final ConexionBaseDatosInsertar conexionBaseDatosInsertar = new ConexionBaseDatosInsertar(activity);

        StringRequest postRequest = new StringRequest(Request.Method.GET, URL_DEPORTE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Peticion", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int x = 0; x < jsonArray.length(); x++) {

                                //Se obtienen los datos y se mandan al deporte
                                Deporte deporte = new Deporte(
                                        Integer.parseInt(jsonArray.getJSONObject(x).getString("id")),
                                        jsonArray.getJSONObject(x).getString("nombre"),
                                        jsonArray.getJSONObject(x).getInt("disciplina"),
                                        jsonArray.getJSONObject(x).getString("imagen")
                                );

                                conexionBaseDatosInsertar.insertarDeporte(deporte);
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

        return null;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }
}
