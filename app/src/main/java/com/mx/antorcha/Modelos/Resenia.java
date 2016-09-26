package com.mx.antorcha.Modelos;

/**
 *
 */
public class Resenia {

    private String titulo;
    private String resenia;
    private String nombre;

    public Resenia(String titulo, String resenia, String nombre) {
        this.titulo = titulo;
        this.resenia = resenia;
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResenia() {
        return resenia;
    }

    public void setResenia(String resenia) {
        this.resenia = resenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
