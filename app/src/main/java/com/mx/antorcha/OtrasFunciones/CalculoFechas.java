package com.mx.antorcha.OtrasFunciones;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 *
 */
public class CalculoFechas {

    public static int calcularEdad(String fecha) {

        try {
            //Se separa la fecha
            StringTokenizer stringTokenizer = new StringTokenizer(fecha, "-");
            int anio = Integer.parseInt(stringTokenizer.nextToken());
            int mes = Integer.parseInt(stringTokenizer.nextToken());
            int dia = Integer.parseInt(stringTokenizer.nextToken());

            Calendar calendar = Calendar.getInstance();

            int anioActual = calendar.get(Calendar.YEAR);
            int mesActual = 12 - calendar.getActualMaximum(Calendar.MONTH);
            int diaActual = calendar.get(Calendar.DAY_OF_MONTH);

            anio = anioActual - anio;

            if (mesActual - mes <= 0 && diaActual - dia <= 0) {
                anio--;
            }

            return anio;
        } catch (Exception e) {

            return 0;
        }

    }

    //cambiar formato de facebook a sql

    static public String cambiarFormato (String fecha) {
        StringTokenizer stringTokenizer = new StringTokenizer(fecha, "-");
        String anio = stringTokenizer.nextToken();
        String mes = stringTokenizer.nextToken();
        String dia = stringTokenizer.nextToken();

        return dia + "-" + mes + "-" + anio;
    }

    static public String cambiarFormatoFacebook (String fecha) {
        StringTokenizer stringTokenizer = new StringTokenizer(fecha, "/");
        String anio = stringTokenizer.nextToken();
        String mes = stringTokenizer.nextToken();
        String dia = stringTokenizer.nextToken();

        return dia + "-" + mes + "-" + anio;
    }
}
