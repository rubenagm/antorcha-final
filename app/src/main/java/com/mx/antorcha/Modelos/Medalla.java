package com.mx.antorcha.Modelos;

/**
 * Created by Ruben on 18/12/2015.
 */
public class Medalla {
    private int id;
    private String nombre;
    private int tipo;
    private String descripcion;
    private String comoSeLogra;
    private String imagen;

    public Medalla () {

    }

    public Medalla(int id, String nombre, int tipo, String descripcion, String comoSeLogra, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.comoSeLogra = comoSeLogra;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getComoSeLogra() {
        return comoSeLogra;
    }

    public void setComoSeLogra(String comoSeLogra) {
        this.comoSeLogra = comoSeLogra;
    }
}
