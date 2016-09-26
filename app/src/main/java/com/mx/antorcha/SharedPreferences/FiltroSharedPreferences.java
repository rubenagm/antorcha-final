package com.mx.antorcha.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 10/04/16.
 */
public class FiltroSharedPreferences {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String NOMBRE_SP = "Antorcha";
    private String LLAVE_RANGO = "llave_rango";
    private String LLAVE_ACTIVIDADES_PREFRENCIA = "llave_actividades_preferencia";

    public FiltroSharedPreferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NOMBRE_SP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setActividadesPreferencia (boolean actividadesPreferencia) {
        editor.putBoolean(LLAVE_ACTIVIDADES_PREFRENCIA, actividadesPreferencia);
        editor.apply();
    }

    public void setRango (int rango) {
        editor.putInt(LLAVE_RANGO, rango);
        editor.apply();
    }

    public boolean getActividadesPreferencia () {
         return sharedPreferences.getBoolean(LLAVE_ACTIVIDADES_PREFRENCIA, true);
    }

    public int getRango () {
        return sharedPreferences.getInt(LLAVE_RANGO, 2);

    }


}
