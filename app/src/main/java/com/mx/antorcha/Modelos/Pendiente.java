package com.mx.antorcha.Modelos;

/**
 * Created by root on 25/03/16.
 */
public class Pendiente {

    private int id;
    private String tipo;
    private String datos;

    //Constantes para giardar el tipo
    static public String SEP = "}";
    static final public String META = "Meta";
    static final public String META_PROGRESO = "MetaProgreso";
    static final public String DISCIPLINA = "Disciplina";
    static final public String DEPORTE = "Deporte";
    static final public String ELIMINAR_META = "EliminarMeta";
    static final public String MIEMBRO_ESPACIO = "MiembroEspacio";
    static final public String MIEMBRO_EVENTO = "MiembroEvento";
    static final public String BORRAR_MIEMBRO_ESPACIO = "BorrarMiembroEspacio";
    static final public String BORRAR_MIEMBRO_EVENTO = "BorrarMiembroEvento";

    public Pendiente(int id, String tipo, String datos) {
        this.id = id;
        this.tipo = tipo;
        this.datos = datos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    /* ORDEN De una meta
        private double inicio;
        private double fin;
        private String nombre;
        private String fechaFin;
        private String fechaInicio;
        private String tipoMedida;
     */

    /* ORDEN de una meta progresp
        private int id;
        private int idMeta;
        private double progreso;
        private String fecha;
     */
}
