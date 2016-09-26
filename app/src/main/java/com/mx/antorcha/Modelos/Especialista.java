package com.mx.antorcha.Modelos;

/**
 *
 */
public class Especialista {

    private String nombre;
    private String titulo;
    private String especialidad;
    private String descripcion;
    private String telefono;
    private String correo;
    private String sexo;

    public Especialista(String nombre, String titulo, String especialidad, String descripcion, String telefono, String correo, String sexo) {
        this.nombre = nombre;
        this.titulo = titulo;
        this.especialidad = especialidad;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.correo = correo;
        this.sexo = sexo;
    }

    public Especialista () {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
