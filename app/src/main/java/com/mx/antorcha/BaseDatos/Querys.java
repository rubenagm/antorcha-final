package com.mx.antorcha.BaseDatos;

/**
 *
 */
public class Querys {

    /* TAGS PARA LA BASE DE DATOS */
    static String TAG_INSERTAR = "Insertando dato";

    /* QUERYS DE CREACION DE TABLAS */
    static String CREAR_TABLA_METAS = "CREATE TABLE Meta (Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Nombre TEXT, Inicio DECIMAL, Fin DECIMAL, FechaInicio DATE, FechaFin DATE, TipoMedida TEXT, IdServidor INTEGER, Estado INTEGER)";
    static String CREAR_TABLA_META_PROGRESO = "CREATE TABLE MetaProgreso (Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, IdMeta INTEGER, Progreso DECIMAL, Fecha DATE, IdServidor INTEGER)";
    static String CREAR_TABLA_MEDALLAS = "CREATE TABLE Medalla (Id INTEGER PRIMARY KEY, Nombre TEXT, Tipo INTEGER, Descripcion TEXT, ComoSeLogra TEXT, Imagen TEXT);";
    static String CREAR_TABLA_DISCIPLINAS = "CREATE TABLE Disciplina (Id INTEGER PRIMARY KEY, Nombre TEXT, Descripcion TEXT);";
    static String CREAR_TABLA_DEPORTES = "CREATE TABLE Deporte (Id INTEGER PRIMARY KEY, Nombre TEXT, Disciplina INTEGER, Imagen TEXT)";
    static String CREAR_TABLA_ESPACIOS = "CREATE TABLE EspacioDeportivo (Id INTEGER PRIMARY KEY, Nombre TEXT, Descripcion TEXT, Domicilio TEXT, Colonia TEXT, CodigoPostal TEXT, Municipio TEXT, Ciudad TEXT, Estado TEXT, Telefono TEXT, Latitud FLOAT, Longitud FLOAT, Valoracion TEXT, Horario TEXT);";
    static String CREAR_TABLA_EVENTO = "CREATE TABLE Evento (Id INTEGER PRIMARY KEY, Nombre TEXT, Descripcion TEXT, Domicilio TEXT, Colonia TEXT, CodigoPostal TEXT, Municipio TEXT, Ciudad TEXT, Estado TEXT, Telefono TEXT, Latitud FLOAT, Longitud FLOAT, FechaInicio DATE, FechaFin DATE);";
    static String CREAR_TABLA_PENDIENTES = "CREATE TABLE Pendiente (Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Tipo TEXT, Datos TEXT)";
    /* QUERYS PARA OBTENER DATOS */

    //Metas
    static String OBTENER_METAS = "SELECT * FROM Meta WHERE Estado = 1";
    static String OBTENER_METAS_ELIMINAR = "SELECT * FROM Meta WHERE Estado = 0";
    static String OBTENER_METAS_PROGRESO = "SELECT * FROM MetaProgreso WHERE IdMeta = ";
    static String OBTENER_MEDALLAS = "SELECT * FROM Medalla";
    static String OBTENER_DISCIPLINAS = "SELECT * FROM Disciplina";
    static String OBTENER_DEPORTES = "SELECT * FROM Deporte";
    static String OBTENER_DEPORTE_POR_DISCPLINA = "SELECT * FROM Deporte WHERE Disciplina = ";
    static String OBTENER_UNA_MEDALLA = "SELECT * FROM Medalla WHERE Id = ";
    static String OBTENER_EVENTOS = "SELECT * FROM Evento";
    static String OBTENER_ESPACIOS_DEPORTIVOS = "SELECT * FROM EspacioDeportivo";
    static String OBTENER_UN_EVENTO = "SELECT * FROM Evento WHERE Id = ";
    static String OBTENER_UN_ESPACIO = "SELECT * FROM EspacioDeportivo WHERE Id = ";
    static String OBTENER_PENDIENTES = "SELECT * FROM Pendiente";
    static String OBTENER_UNA_META = "SELECT * FROM Meta WHERE Id = ";
}
