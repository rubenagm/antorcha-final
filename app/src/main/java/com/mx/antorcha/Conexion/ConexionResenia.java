package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.Dialogos.DialogoInsertarResenia;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_RESENIA;

/**
 *
 */
public class ConexionResenia extends AsyncTask<Void, Void, Void>{

    private Activity activity;
    private String resenia;
    private String id;
    private String tipo;
    private String titulo;
    private Dialog dialogoInsertarResenia;

    public ConexionResenia(Activity activity, String resenia, String id, String tipo, String titulo) {
        this.activity = activity;
        this.resenia = resenia;
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
    }


    @Override
    protected Void doInBackground(Void... params) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_RESENIA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Si todo sale correcto, después del registro se hace...
                        Log.i("Peticion registro", response);
                        dialogoInsertarResenia.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        dialogoInsertarResenia.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("resenia", resenia);
                params.put("idRelacion", id);
                params.put("idMiembro", (new MiembroSharedPreferences(activity).getId()) + "");
                params.put("tipo", tipo);
                params.put("titulo", titulo);

                return params;
            }
        };

        Volley.newRequestQueue(activity).add(postRequest);

        return null;
    }

    public void setDialogoInsertarResenia(Dialog dialogoInsertarResenia) {
        this.dialogoInsertarResenia = dialogoInsertarResenia;
    }
}
