package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.Activities.Compartir;
import com.mx.antorcha.Adaptadores.AdaptadorSpinner;
import com.mx.antorcha.Modelos.EspacioDeportivo;
import com.mx.antorcha.Modelos.Resenia;
import com.mx.antorcha.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *
 */
public class ConexionInformacionEspacio {

    private Activity activity;
    private String idEspacio;
    private View view;
    private ArrayList<Resenia> resenias;

    public ConexionInformacionEspacio(Activity activity, String idEspacio) {
        this.activity = activity;
        this.idEspacio = idEspacio;
    }

    public void obtenerEspacio() {

        StringRequest postRequest = new StringRequest(Request.Method.GET, InfoConexion.URL_BUSCAR_UN_ESPACIO + idEspacio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("ConexionInformacionEsp", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(x);

                                final EspacioDeportivo espacioDeportivo = new EspacioDeportivo(
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
                                        jsonObject.getDouble("longitud"),
                                        jsonObject.getString("valoracion"),
                                        jsonObject.getString("horario"));

                                if (view != null) {
                                    ((TextView) view.findViewById(R.id.sliding_buscar_espacio_descripcion)).setText(espacioDeportivo.getDescripcion());

                                    //Se obtienen las reseñas
                                    JSONArray jsonArrayResenias = jsonObject.getJSONArray("resenias");
                                    resenias.clear();

                                    for (int y = 0; y < jsonArrayResenias.length(); y++) {
                                        JSONObject jsonObjectResenia = jsonArrayResenias.getJSONObject(y);

                                        ((TextView) view.findViewById(R.id.sliding_buscar_espacio_resenia)).setText(jsonObjectResenia.getString("resenia"));
                                        ((TextView) view.findViewById(R.id.sliding_buscar_espacio_titulo_resenia)).setText(jsonObjectResenia.getString("titulo"));
                                        ((TextView) view.findViewById(R.id.sliding_buscar_espacio_nombre_resenia)).setText(jsonObjectResenia.getString("idMiembro"));
                                        resenias.add(new Resenia(jsonObjectResenia.getString("titulo"), jsonObjectResenia.getString("resenia"), jsonObjectResenia.getString("idMiembro")));
                                    }

                                    //Se obtienen los nombres de los deportes
                                    JSONArray jsonArrayDeportes = jsonObject.getJSONArray("deportes");
                                    ArrayList<String> deportes = new ArrayList<>();

                                    for (int y = 0; y < jsonArrayDeportes.length(); y++) {
                                        deportes.add(jsonArrayDeportes.getJSONObject(y).getString("nombre"));
                                        //Se cargan los deportes
                                    }

                                    //Se obtienen los servicios
                                    JSONArray jsonArrayServicios = jsonObject.getJSONArray("servicios");
                                    ArrayList<String> servicios = new ArrayList<>();

                                    for (int y = 0; y < jsonArrayServicios.length(); y++) {
                                        servicios.add(jsonArrayServicios.getJSONObject(y).getString("nombre"));
                                        //Se cargan los deportes
                                    }


                                    LayoutInflater layoutInflater = LayoutInflater.from(activity);
                                    //Se muestran los tags de los deportes
                                    LinearLayout linearLayoutActividades = (LinearLayout) view.findViewById(R.id.sliding_buscar_espacio_tags_actividades);
                                    linearLayoutActividades.removeAllViews();


                                    for (int y = 0; y < deportes.size(); y++) {
                                        int indice = 0;
                                        LinearLayout linearLayoutTagActividades = (LinearLayout) layoutInflater.inflate(R.layout.item_tag_actividades, null);

                                        while (indice < 3 && y < deportes.size()) {
                                            TextView textView = (TextView) linearLayoutTagActividades.getChildAt(indice);
                                            textView.setText(deportes.get(y));
                                            textView.setVisibility(View.VISIBLE);
                                            indice++;
                                            y++;
                                        }
                                        y--;
                                        linearLayoutActividades.addView(linearLayoutTagActividades);
                                    }

                                    //Se muestran los tags de los deportes
                                    LinearLayout linearLayoutServicios = (LinearLayout) view.findViewById(R.id.sliding_buscar_espacio_tags_servicios);
                                    linearLayoutServicios.removeAllViews();

                                    for (int y = 0; y < servicios.size(); y++) {
                                        int indice = 0;
                                        LinearLayout linearLayoutTagsServicios = (LinearLayout) layoutInflater.inflate(R.layout.item_tag_servicios, null);

                                        while (indice < 3 && y < servicios.size()) {
                                            TextView textView = (TextView) linearLayoutTagsServicios.getChildAt(indice);
                                            textView.setText(servicios.get(y));
                                            textView.setVisibility(View.VISIBLE);
                                            indice++;
                                            y++;
                                        }
                                        y--;
                                        linearLayoutServicios.addView(linearLayoutTagsServicios);
                                    }

                                    //Se obtienen las membresias
                                    ArrayList<String> membresias = new ArrayList<>();

                                    JSONArray jsonArrayMembresias = jsonObject.getJSONArray("tipoMembresia");

                                    if (jsonArrayMembresias.length() == 0) {
                                        membresias.add("Este espacio no cuenta con membresias por el momento");
                                    } else {

                                        for (int y = 0; y < jsonArrayMembresias.length(); y++) {
                                            JSONObject jsonObjectMembresia = jsonArrayMembresias.getJSONObject(y);
                                            membresias.add(jsonObjectMembresia.getString("duracion") + " $ " + jsonObjectMembresia.getString("costo"));
                                        }
                                    }
                                    AdaptadorSpinner adaptadorSpinner = new AdaptadorSpinner(activity, membresias);
                                    Spinner spinnerMembresias = (Spinner) view.findViewById(R.id.buscar_espacio_spinner_membresias);
                                    spinnerMembresias.setAdapter(adaptadorSpinner);

                                    //Se coloca el click en el contacto y en compartir

                                    ImageView imageViewCompartir = (ImageView) view.findViewById(R.id.sliding_buscar_actividades_espacio_compartir);
                                    ImageView imageViewContacto = (ImageView) view.findViewById(R.id.sliding_buscar_actividades_espacio_contacto);

                                    imageViewContacto.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + espacioDeportivo.getTelefono()));
                                            activity.startActivity(intent);
                                        }
                                    });

                                    imageViewCompartir.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(activity, Compartir.class);
                                            intent.putExtra("id", espacioDeportivo.getId());
                                            intent.putExtra("tipo", "espacio");

                                            activity.startActivity(intent);
                                        }
                                    });

                                    ImageView imageViewImagenGaleria = (ImageView) view.findViewById(R.id.sliding_buscar_espacio_imagen_galeria);
                                    DescargarImagen.cargarImagen(activity, InfoConexion.URL_DESCARGAR_IMAGEN_ESPACIO + espacioDeportivo.getId() + "_2_.png", imageViewImagenGaleria);

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
                //No hay parametros
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(postRequest);

    }

    public void setView(View view) {
        this.view = view;
    }

    public void cargarInformacionEspacio () {

    }

    public void setResenias (ArrayList<Resenia> resenias) {
        this.resenias = resenias;
    }
}
