package com.mx.antorcha.SharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 */
public class MiembroSharedPreferences {

    private String NOMBRE_SP = "Antorcha";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String LLAVE_NOMBRE = "llave_nombre";
    private String LLAVE_SEXO = "llave_sexo";
    private String LLAVE_ID = "llave_id";
    private String LLAVE_EDAD = "llave_edad";
    private String LLAVE_ID_FACEBOOK = "llave_facebook";
    private String LLAVE_ID_GOOGLE = "llave_google";
    private String LLAVE_CORREO = "llave_correo";
    private String LLAVE_DESCRIPCION = "llave_descripcion";
    private String LLAVE_INTERESES = "llave_intereses";
    private String DEFAULT = "default";
    private String LLAVE_FECHA_NACIMIENTO = "fecha_nacimiento";
    private String LLAVE_ACTUALIZAR = "llave_actualizar";
    private String LLAVE_GCM = "llave_gcm";
    private String LLAVE_REGISTRADO = "llave_registrado";
    private String PRIMERA_VEZ = "llave_primera_vez";

    public MiembroSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(NOMBRE_SP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setGCM (String gcm) {
        editor.putString(LLAVE_GCM, gcm);
        editor.apply();
    }

    public String getGCM () {
        return sharedPreferences.getString(LLAVE_GCM, "");
    }

    public void setRegistrado (int registrado) {
        editor.putInt(LLAVE_REGISTRADO, registrado);
        editor.apply();
    }

    public int getRegistrado () {
        return sharedPreferences.getInt(LLAVE_REGISTRADO, 0);
    }

    //0 NO - 1 SI
    public void setActualizar (int actualizar) {
        editor.putInt(LLAVE_ACTUALIZAR, actualizar);
        editor.apply();
    }

    public int getActualizar () {
        return sharedPreferences.getInt(LLAVE_ACTUALIZAR, 0);
    }

    public void setDescripcion (String descripcion){
        editor.putString(LLAVE_DESCRIPCION, descripcion);
        editor.apply();
    }

    public void setIntereses (String intereses) {
        editor.putString(LLAVE_INTERESES, intereses);
        editor.apply();
    }

    public String getIntereses () {
        return sharedPreferences.getString(LLAVE_INTERESES, "");
    }

    public String getDescripcion () {
        return sharedPreferences.getString(LLAVE_DESCRIPCION, "");
    }

    public void setNombre (String nombre) {
        editor.putString(LLAVE_NOMBRE, nombre);
        editor.apply();
    }

    public String getNombre () {
        return sharedPreferences.getString(LLAVE_NOMBRE, DEFAULT);
    }

    public void setSexo (String sexo) {
        editor.putString(LLAVE_SEXO, sexo);
        editor.apply();
    }

    public String getSexo () {
        return sharedPreferences.getString(LLAVE_SEXO, DEFAULT);
    }

    public void setId (int id) {
        editor.putInt(LLAVE_ID, id);
        editor.apply();
    }


    public int getId () {
        return sharedPreferences.getInt(LLAVE_ID, 0);
    }

    public void setIdGoogle (String google) {
        editor.putString(LLAVE_ID_GOOGLE, google);
        editor.apply();
    }

    public String getIdGoogle () {
        return  sharedPreferences.getString(LLAVE_ID_GOOGLE, DEFAULT);
    }

    public void setEdad (String edad) {
        editor.putString(LLAVE_EDAD, edad);
        editor.apply();
    }

    public String getEdad () {
        return sharedPreferences.getString(LLAVE_EDAD, DEFAULT);
    }

    public void setIdFacebook (String facebook) {
        editor.putString(LLAVE_ID_FACEBOOK, facebook);
        editor.apply();
    }

    public String getIdFacebook () {
        return sharedPreferences.getString(LLAVE_ID_FACEBOOK, DEFAULT);
    }

    public void setFechaNacimiento (String fechaNacimiento) {
        editor.putString(LLAVE_FECHA_NACIMIENTO, fechaNacimiento);
        editor.apply();
    }

    public String getFechaNacimiento () {
        return sharedPreferences.getString(LLAVE_FECHA_NACIMIENTO, DEFAULT);
    }

    public void setCorreo (String correo) {
        editor.putString(LLAVE_CORREO, correo);
        editor.apply();
    }

    public String getCorreo () {
        return sharedPreferences.getString(LLAVE_CORREO, DEFAULT);
    }

    public void borrarTodo () {
        editor.clear();
        editor.apply();
    }

    public boolean getPrimeraVez(){
        return sharedPreferences.getBoolean(PRIMERA_VEZ, true);
    }

    public void setPrimeraVez () {
        editor.putBoolean(PRIMERA_VEZ, false);
        editor.apply();
    }
}
