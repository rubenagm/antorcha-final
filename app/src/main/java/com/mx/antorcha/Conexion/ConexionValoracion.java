package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_RESENIA;
import static com.mx.antorcha.Conexion.InfoConexion.URL_VALORACION;

/**
 *
 */
public class ConexionValoracion {

    private Activity activity;
    private String id;
    private String valoracion;
    private Dialog dialog;

    public ConexionValoracion(Activity activity, String id, String valoracion) {
        this.activity = activity;
        this.id = id;
        this.valoracion = valoracion;
    }

    public void enviar(){

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_VALORACION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Si todo sale correcto, después del registro se hace...
                        Log.i("Peticion registro", response);
                        Toast.makeText(activity, "Se ha guardado tu valoración", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(activity, "Hubo un problema al enviar la valoración", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("valoracion", valoracion);
                params.put("idReferencia", id);
                params.put("tipo", 0 + "");
                params.put("idMiembro", new MiembroSharedPreferences(activity).getId() + "");

                return params;
            }
        };

        Volley.newRequestQueue(activity).add(postRequest);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
