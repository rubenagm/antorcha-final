package com.mx.antorcha.Modelos;

/**
 * Created by Ruben on 21/12/2015.
 */
public class MetaProgreso {

    private int id;
    private int idMeta;
    private double progreso;
    private String fecha;
    private int idServidor;

    public MetaProgreso(int id, int idMeta, double progreso, String fecha, int idServidor) {
        this.id = id;
        this.idMeta = idMeta;
        this.progreso = progreso;
        this.fecha = fecha;
        this.idServidor = idServidor;
    }

    public MetaProgreso () {

    }

    public int getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(int idMeta) {
        this.idMeta = idMeta;
    }

    public double getProgreso() {
        return progreso;
    }

    public void setProgreso(double progreso) {
        this.progreso = progreso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(int idServidor) {
        this.idServidor = idServidor;
    }
}
