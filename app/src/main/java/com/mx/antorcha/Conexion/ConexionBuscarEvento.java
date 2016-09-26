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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mx.antorcha.Adaptadores.AdaptadorListaEventos;
import com.mx.antorcha.Modelos.Evento;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.mx.antorcha.Conexion.InfoConexion.URL_BUSCAR_EVENTO;
import static com.mx.antorcha.Conexion.InfoConexion.URL_META;

/**
 *
 */
public class ConexionBuscarEvento extends AsyncTask<Void, Void, Void> {

    private ArrayList<Evento> eventos;
    private String latitud;
    private String longitud;
    private String idMiembro;
    private String cercania;
    private String espacios;
    private Activity activity;
    private ListView listView;
    private GoogleMap googleMap;
    private MiembroSharedPreferences miembroSharedPreferences;

    public ConexionBuscarEvento(Activity activity, GoogleMap googleMap ,String latitud, String longitud, String idMiembro, String cercania, String espacios) {
        this.activity = activity;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idMiembro = idMiembro;
        this.cercania = cercania;
        this.espacios = espacios;
        this.googleMap = googleMap;
        miembroSharedPreferences = new MiembroSharedPreferences(activity);
    }

    public ConexionBuscarEvento (Activity activity, GoogleMap googleMap) {
        miembroSharedPreferences = new MiembroSharedPreferences(activity);
        this.latitud = 20.699359689441785 + "";
        this.longitud = -103.29570472240448 + "";
        this.cercania = 10 + "";
        this.espacios = "si";
        this.idMiembro = miembroSharedPreferences.getId() + "";
        this.googleMap = googleMap;
        this.activity = activity;
    }

    public ConexionBuscarEvento(Activity activity, ListView listView, String latitud, String longitud, String idMiembro, String cercania, String espacios) {
        this.activity = activity;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idMiembro = idMiembro;
        this.cercania = cercania;
        this.espacios = espacios;
        eventos = new ArrayList<>();
        miembroSharedPreferences = new MiembroSharedPreferences(activity);
    }

    public ConexionBuscarEvento (Activity activity, ListView listView) {
        miembroSharedPreferences = new MiembroSharedPreferences(activity);
        this.latitud = 20.699359689441785 + "";
        this.longitud = -103.29570472240448 + "";
        this.cercania = 10 + "";
        this.espacios = "si";
        this.idMiembro = miembroSharedPreferences.getId() + "";
        this.listView = listView;
        this.activity = activity;
        eventos = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... params) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_BUSCAR_EVENTO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("ConexionBuscarEvento", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            eventos.clear();

                            for (int x = 0; x < jsonArray.length(); x++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(x);
                                    Evento evento = new Evento (
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("nombre"),
                                            jsonObject.getString("descripcion"),
                                            jsonObject.getString("direccion"),
                                            jsonObject.getString("colonia"),
                                            jsonObject.getString("codigoPostal"),
                                            jsonObject.getString("municipio"),
                                            jsonObject.getString("ciudad"),
                                            jsonObject.getString("estado"),
                                            jsonObject.getString("telefono"),
                                            jsonObject.getString("fechaInicio"),
                                            "",
                                            jsonObject.getDouble("latitud"),
                                            jsonObject.getDouble("longitud"));
                                    evento.setTipo(0);

                                    if (eventos.size() == 0) {
                                        Evento eventoMes = new Evento();
                                        eventoMes.setTipo(1);
                                        eventoMes.setMesAnio(obtenerMesAnio(evento.getFechaInicio()));
                                        eventos.add(eventoMes);
                                    } else  if (!obtenerMes(evento.getFechaInicio()).equals(obtenerMes(eventos.get(eventos.size() - 1).getFechaInicio()))) {
                                        Evento eventoMes = new Evento();
                                        eventoMes.setTipo(1);
                                        eventoMes.setMesAnio(obtenerMesAnio(evento.getFechaInicio()));
                                        eventos.add(eventoMes);
                                    }
                                    eventos.add(evento);

                                    if (googleMap != null) {
                                        googleMap.addMarker(
                                                new MarkerOptions().position(
                                                        new LatLng(evento.getLatitud(),
                                                                evento.getLongitud()))
                                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_marker_verde))
                                                        .title(evento.getNombre())
                                        );
                                    } else if (listView != null) {
                                        AdaptadorListaEventos adaptadorListaEventos = new AdaptadorListaEventos(activity, R.layout.item_lista_eventos, eventos);
                                        listView.setAdapter(adaptadorListaEventos);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
                params.put("latitud", latitud);
                params.put("longitud", longitud);
                params.put("espacios", espacios);
                params.put("cercania", cercania);
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

    public void setEventos(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }

    public String obtenerMesAnio (String fecha) {

        StringTokenizer stringTokenizer = new StringTokenizer(fecha, "-");
        String anio = stringTokenizer.nextToken();
        String mes = stringTokenizer.nextToken();

        switch (mes) {
            case "01" : {
                mes = "Enero";
                break;
            }
            case "02" : {
                mes = "Febrero";
                break;
            }
            case "03" : {
                mes = "Marzo";
                break;
            }
            case "04" : {
                mes = "Abril";
                break;
            }
            case "05" : {
                mes = "Mayo";
                break;
            }
            case "06" : {
                mes = "Junio";
                break;
            }
            case "07" : {
                mes = "Julio";
                break;
            }
            case "08" : {
                mes = "Agosto";
                break;
            }
            case "09" : {
                mes = "Septiembre";
                break;
            }
            case "10" : {
                mes = "Octubre";
                break;
            }
            case "11" : {
                mes = "Noviembre";
                break;
            }
            case "12" : {
                mes = "Diciembre";
                break;
            }
        }

        return mes + " del " + anio;
    }

    public String obtenerMes(String fecha) {
        StringTokenizer stringTokenizer = new StringTokenizer(fecha, "-");
        stringTokenizer.nextToken();

        return stringTokenizer.nextToken();
    }
}
