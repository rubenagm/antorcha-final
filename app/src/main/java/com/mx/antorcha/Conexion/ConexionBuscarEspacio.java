package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mx.antorcha.Modelos.EspacioDeportivo;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.FiltroSharedPreferences;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_BUSCAR_ESPACIO;

/**
 *
 */
public class ConexionBuscarEspacio extends AsyncTask<Void, Void, Void> {

    private String latitud;
    private  ArrayList<EspacioDeportivo> espacioDeportivos;
    private String longitud;
    private String idMiembro;
    private String cercania;
    private String espacios;
    private Activity activity;
    private GoogleMap googleMap;
    private MiembroSharedPreferences miembroSharedPreferences;

    public ConexionBuscarEspacio(Activity activity, GoogleMap googleMap ,String latitud, String longitud, String idMiembro, String cercania, String espacios) {
        this.activity = activity;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idMiembro = idMiembro;
        this.cercania = cercania;
        this.espacios = espacios;
        this.googleMap = googleMap;
        miembroSharedPreferences = new MiembroSharedPreferences(activity);
    }

    public ConexionBuscarEspacio (Activity activity, GoogleMap googleMap) {
        miembroSharedPreferences = new MiembroSharedPreferences(activity);
        this.latitud = 20.699359689441785 + "";
        this.longitud = -103.29570472240448 + "";
        this.cercania = 10 + "";
        this.espacios = "si";
        this.idMiembro = miembroSharedPreferences.getId() + "";
        this.googleMap = googleMap;
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_BUSCAR_ESPACIO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("ConexionBuscarEspacio", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            espacioDeportivos.clear();

                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(x);

                                String valoracion = "0.0";

                                if (!jsonObject.getString("valoracion").equals("null")) {
                                    valoracion = jsonObject.getString("valoracion");
                                }
                                EspacioDeportivo espacioDeportivo = new EspacioDeportivo(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("nombre"),
                                        jsonObject.getString("descripcion"),
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        jsonObject.getString("telefono"),
                                        jsonObject.getDouble("latitud"),
                                        jsonObject.getDouble("longitud"),
                                        valoracion,
                                        jsonObject.getString("horario"));

                                espacioDeportivos.add(espacioDeportivo);

                                //Se agrega el mapa a la vista
                                googleMap.addMarker(
                                        new MarkerOptions().position(
                                                new LatLng(espacioDeportivo.getLatitud(),
                                                        espacioDeportivo.getLongitud()))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_marker_negro))
                                        .title(espacioDeportivo.getNombre())
                                );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Se obtienen los datos

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

                if (new FiltroSharedPreferences(activity).getActividadesPreferencia()) {
                    espacios = "1";
                } else {
                    espacios = "0";
                }

                // the POST parameters:
                params.put("latitud", latitud);
                params.put("longitud", longitud);
                params.put("espacios", espacios);
                params.put("cercania", new FiltroSharedPreferences(activity).getRango() + "");
                params.put("idMiembro", idMiembro);

                return params;
            }
        };
        Volley.newRequestQueue(activity).add(postRequest);

        return null;
    }

    public void agregarMarker (double latitud, double longitud) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitud, longitud))
        );
    }

    public void setEspacioDeportivos(ArrayList<EspacioDeportivo> espacioDeportivos) {
        this.espacioDeportivos = espacioDeportivos;
    }
}

/*
EspacioDeportivo espacioDeportivo = new EspacioDeportivo(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("nombre"),
                                        jsonObject.getString("descripcion"),
                                        jsonObject.getString("domicilio"),
                                        jsonObject.getString("colonia"),
                                        jsonObject.getString("codigoPostal"),
                                        jsonObject.getString("municipio"),
                                        jsonObject.getString("ciudad"),
                                        jsonObject.getString("estado"),
                                        jsonObject.getString("telefono"),
                                        jsonObject.getDouble("latitud"),
                                        jsonObject.getDouble("longitud")
                                        );
 */