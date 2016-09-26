package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.Fragment.FragmentPerfilPerfil;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_SUBIR_IMAGEN;

/**
 *
 */
public class SubirImagen {

    static public void subirImagen(Activity activity, final int id){

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_SUBIR_IMAGEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Subir imagen", response);
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
                Bitmap bitmap = FragmentPerfilPerfil.obtenerImagen(id);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte imagen [] = byteArrayOutputStream.toByteArray();

                String encodedImage = Base64.encodeToString(imagen, Base64.DEFAULT);

                params.put("imagen", encodedImage);
                params.put("id", id + "");

                return params;
            }
        };

        Volley.newRequestQueue(activity).add(postRequest);
    }

}
