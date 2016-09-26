package com.mx.antorcha.Conexion;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mx.antorcha.Activities.BuscarActividad;
import com.mx.antorcha.Servicios.ServicioDescarga;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mx.antorcha.Conexion.InfoConexion.URL_FACEBOOK_IMAGEN_1;
import static com.mx.antorcha.Conexion.InfoConexion.URL_FACEBOOK_IMAGEN_2;
import static com.mx.antorcha.Conexion.InfoConexion.URL_LOGIN;

/**
 *
 */
public class ConexionLogin extends AsyncTask<Void, Void, Void>{

    private Activity activity;
    private String correo;
    private String password;
    private String idGoogle;
    private String idFacebook;
    private String nombre;
    private String fechaNacimiento;
    private String genero;

    public ConexionLogin(Activity activity, String correo, String password) {
        this.activity = activity;
        this.correo = correo;
        this.password = password;
    }

    public ConexionLogin (Activity activity, String idGoogle) {
        this.activity = activity;
        this.idGoogle = idGoogle;
    }

    public ConexionLogin (String idFacebook, Activity activity) {
        this.activity = activity;
        this.idFacebook = idFacebook;
    }


    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }

    public void login () {
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Login", response);

                        if (response.equals("FAlSE") && idFacebook != null) {
                            ConexionRegistro conexionRegistro= new ConexionRegistro(
                                    nombre,
                                    genero.substring(0,1),
                                    correo,
                                    "facebook", //No va ningún password
                                    fechaNacimiento,
                                    activity);
                            conexionRegistro.setFacebook(idFacebook);
                            conexionRegistro.execute();
                        } else {

                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if (!jsonObject.isNull("nombre")) {

                                    //Se obtuvo correcto el login
                                    //Se guardan los cambios en el Shared preferences
                                    MiembroSharedPreferences miembroSharedPreferences = new MiembroSharedPreferences(activity);
                                    miembroSharedPreferences.setId(jsonObject.getInt("id"));
                                    miembroSharedPreferences.setNombre(jsonObject.getString("nombre"));
                                    miembroSharedPreferences.setCorreo(jsonObject.getString("correo"));
                                    miembroSharedPreferences.setFechaNacimiento(jsonObject.getString("fechaNacimiento").replace(" 00:00:00", ""));
                                    miembroSharedPreferences.setSexo(jsonObject.getString("sexo"));
                                    miembroSharedPreferences.setIntereses(jsonObject.getString("queBusco"));
                                    miembroSharedPreferences.setDescripcion(jsonObject.getString("descripcion"));

                                    if (idFacebook != null && idFacebook.length() > 0) {
                                        miembroSharedPreferences.setIdFacebook(idFacebook);
                                        DescargarImagen.guardarImagen(activity, URL_FACEBOOK_IMAGEN_1 + idFacebook + URL_FACEBOOK_IMAGEN_2, miembroSharedPreferences.getId() +".jpg");

                                    }

                                    //Se arranca el hilo que actualizará
                                    Intent intentService = new Intent(activity, ServicioDescarga.class);
                                    activity.startService(intentService);

                                    Intent intent = new Intent(activity, BuscarActividad.class);

                                    activity.startActivity(intent);
                                    activity.finish();
                                } else if (!jsonObject.isNull("login")) {

                                    if (idFacebook != null && idFacebook != "") {
                                        ConexionRegistro conexionRegistro= new ConexionRegistro(
                                                nombre,
                                                genero.substring(0,1),
                                                correo,
                                                "facebook", //No va ningún password
                                                fechaNacimiento,
                                                activity);
                                        conexionRegistro.setFacebook(idFacebook);
                                        conexionRegistro.execute();
                                    } else if (idGoogle != null && idGoogle != "") {
                                        ConexionRegistro conexionRegistro= new ConexionRegistro(
                                                nombre,
                                                genero.substring(0,1),
                                                correo,
                                                "facebook", //No va ningún password
                                                fechaNacimiento,
                                                activity);
                                        conexionRegistro.setGoogle(idGoogle);
                                        conexionRegistro.execute();
                                    } else {
                                        //No se pudo acceder con el login

                                        Toast.makeText(activity,
                                                "Lo sentimos, la información que introduciste " +
                                                        "es incorrecta, intentalo de nuevo",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        if (idFacebook != null) {
                            ConexionRegistro conexionRegistro= new ConexionRegistro(
                                    nombre,
                                    genero.substring(0,1),
                                    correo,
                                    "facebook", //No va ningún password
                                    fechaNacimiento,
                                    activity);
                            conexionRegistro.setFacebook(idFacebook);
                            conexionRegistro.execute();
                        } else if (idGoogle != null && idGoogle != "") {
                            ConexionRegistro conexionRegistro= new ConexionRegistro(
                                    nombre,
                                    genero.substring(0,1),
                                    correo,
                                    "facebook", //No va ningún password
                                    fechaNacimiento,
                                    activity);
                            conexionRegistro.setGoogle(idGoogle);
                            conexionRegistro.execute();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                //Dentro de Java aquí agrego los parametros de el regsitro y el login
                if (correo != null && password != null) {
                    params.put("correo", correo);
                    params.put("pass", password);
                } else if (idFacebook != null) {
                    params.put("idFacebook", idFacebook);
                } else if (idGoogle != null) {
                    params.put("idGoogle", idGoogle);
                } else {
                    Toast.makeText(activity,
                            "Estamos teniendo un problema para iniciar sesión, " +
                                    "por favor verifica tus datos y vuelve a intentarlo",
                            Toast.LENGTH_LONG).show();
                }
                return params;
            }
        };
        Volley.newRequestQueue(activity).add(postRequest);
    }

    @Override
    public void onPostExecute (Void v) {
        //lo que se hace una ves que se fue logueado.
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setCorreo (String correo) {
        this.correo = correo;
    }
}
