package com.mx.antorcha.Modelos;

/**
 * Created by Ruben on 19/12/2015.
 */
public class Deporte {
    private int id;
    private String nombre;
    private int disciplina;
    private String imagen;

    public Deporte(int id, String nombre, int disciplina, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.imagen = imagen;
    }

    public Deporte () {

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

    public int getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(int disciplina) {
        this.disciplina = disciplina;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
