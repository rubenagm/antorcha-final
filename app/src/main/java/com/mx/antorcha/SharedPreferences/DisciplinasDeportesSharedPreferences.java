package com.mx.antorcha.SharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 *
 */
public class DisciplinasDeportesSharedPreferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String NOMBRE_SP = "Antorcha";
    private String LLAVE_DEPORTES = "llave_deportes";
    private String LLAVE_DISCIPLINAS = "llave_disciplinas";

    public DisciplinasDeportesSharedPreferences (Context activity) {
        sharedPreferences = activity.getSharedPreferences(NOMBRE_SP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setDeportes (ArrayList<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string: strings) {
            stringBuilder.append(string).append(",");
        }

        editor.putString(LLAVE_DEPORTES, stringBuilder.toString());
        editor.apply();
    }

    public ArrayList<String> getDeportes () {
        String aux = sharedPreferences.getString(LLAVE_DEPORTES, "");
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

    public void setDisciplinas (ArrayList<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string: strings) {
            stringBuilder.append(string).append(",");
        }

        editor.putString(LLAVE_DISCIPLINAS, stringBuilder.toString());
        editor.apply();
    }

    public ArrayList<String> getDisciplinas () {
        String[] disciplinas = sharedPreferences.getString(LLAVE_DISCIPLINAS, "").split(",");

        ArrayList<String> strings = new ArrayList<>();

        if (disciplinas[0] == "") {
            return new ArrayList<String>();
        }

        for (String string : disciplinas) {
            strings.add(string);
        }

        return strings;
    }


}
