package com.mx.antorcha.SharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 *
 */
public class MedallasSharedPreferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String NOMBRE_SP = "Antorcha";
    private String LLAVE_MEDALLAS = "llave_medallas";
    private ArrayList<String> medallas;

    public MedallasSharedPreferences (Context context) {
        sharedPreferences = context.getSharedPreferences(NOMBRE_SP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        medallas = obtenerMedallas();
    }

    public void agregarMedalla(int idMedalla){
        ArrayList<String> strings = medallas;

        if (!strings.contains(idMedalla + "")) {
            strings.add(idMedalla + "");
            StringBuilder stringBuilder = new StringBuilder();

            for (String string: strings) {
                stringBuilder.append(string).append(",");
            }

            editor.putString(LLAVE_MEDALLAS, stringBuilder.toString());
            editor.apply();
        }
    }

    public ArrayList<String> obtenerMedallas(){
        String aux = sharedPreferences.getString(LLAVE_MEDALLAS, "");
        String[] deportes = aux.split(",");

        ArrayList<String> strings = new ArrayList<>();

        if (deportes[0] == "") {
            return new ArrayList<String>();
        }

        for (String string : deportes) {
            strings.add(string);
        }

        return strings;
    }

    public boolean medallaObtenida(int idMedalla){

        if (medallas.contains(idMedalla + "")) {

            return true;
        } else {

            return false;
        }
    }

    public int totalObtenidas(){
        return medallas.size();
    }
}
