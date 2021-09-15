package com.Project.yasejuega.Databases;

import java.security.PublicKey;

public class Utilidades {


    //---------------------BASE DE DATOS------------------------------------------------------------
    public  static  final  String BD_YASEJUEGA = "BD_YaSeJuega";

    //---------------------TABLA TORNEOS------------------------------------------------------------

    public static final String TABLA_LISTA_TORNEOS="LISTA_TORNEOS";

    public static final String CAMPO_LT_TORNEO_PK="TORNEO_PK";
    public static final String CAMPO_LT_EQUIPO="EQUIPO";
    public static final String CAMPO_LT_FECHA_INICIO="FECHA_INICIO";
    public static final String CAMPO_LT_PRECIO="PRECIO";
    public static final String CAMPO_LT_PRECIOxPARTIDO="PRECIOxPARTIDO";
    public static final String CAMPO_LT_PREDIO="PREDIO";
    public static final String CAMPO_LT_TELEFONO="TELEFONO";
    public static final String CAMPO_LT_DIRECCION="DIRECCION";
    public static final String CAMPO_LT_ZONA="ZONA";

    public static final String CREAR_TABLA_LISTA_TORNEOS= "CREATE TABLE "+TABLA_LISTA_TORNEOS+"("
            +CAMPO_LT_TORNEO_PK+" INTEGER,"
            +CAMPO_LT_PREDIO+" TEXT,"
            +CAMPO_LT_EQUIPO+" TEXT,"
            +CAMPO_LT_ZONA+" TEXT,"
            +CAMPO_LT_DIRECCION+" TEXT,"
            +CAMPO_LT_TELEFONO+" TEXT,"
            +CAMPO_LT_FECHA_INICIO+" TEXT,"
            +CAMPO_LT_PRECIO+" TEXT,"
            +CAMPO_LT_PRECIOxPARTIDO+" TEXT)";
    public static final String ACTUALIZAR_TABLA_LISTA_TORNEOS="DROP TABLE IF EXISTS "+TABLA_LISTA_TORNEOS;

    //---------------------TABLA MAPA---------------------------------------------------------------


  public static final String TABLA_MAPA="MAPA";

  public static final String CAMPO_M_GPS="GPS";
  public static final String CAMPO_M_PREDIO="PREDIO";
  public static final String CAMPO_M_PREDIO_PK="PREDIO_PK";


  public static final String CREAR_TABLA_MAPA= "CREATE TABLE "+TABLA_MAPA+"("
          +CAMPO_M_PREDIO_PK+" INTEGER,"
          +CAMPO_M_PREDIO+" TEXT,"
          +CAMPO_M_GPS+" TEXT)";
  public static final String ACTUALIZAR_TABLA_MAPA="DROP TABLE IF EXISTS "+TABLA_MAPA;
    //---------------------TABLA ZONA---------------------------------------------------------------


    public static final String TABLA_CAMPO_ZONA="CAMPO_ZONA";

    public static final String CAMPO_CZ_ZONA="ZONA";

    public static final String CREAR_TABLA_CAMPO_ZONA= "CREATE TABLE "+TABLA_CAMPO_ZONA+"("
            +CAMPO_CZ_ZONA+" TEXT)";
    public static final String ACTUALIZAR_TABLA_CAMPO_ZONA="DROP TABLE IF EXISTS "+TABLA_CAMPO_ZONA;

    //---------------------TABLA TIPO---------------------------------------------------------------


    public static final String TABLA_CAMPO_TIPO="CAMPO_TIPO";

    public static final String CAMPO_CT_TIPO="TIPO";

    public static final String CREAR_TABLA_CAMPO_TIPO= "CREATE TABLE "+TABLA_CAMPO_TIPO+"("
            +CAMPO_CT_TIPO+" TEXT)";
    public static final String ACTUALIZAR_TABLA_CAMPO_TIPO="DROP TABLE IF EXISTS "+TABLA_CAMPO_TIPO;

    //---------------------TABLA HORA---------------------------------------------------------------


    public static final String TABLA_CAMPO_HORA="CAMPO_HORA";

    public static final String CAMPO_CH_HORA="HORA";

    public static final String CREAR_TABLA_CAMPO_HORA= "CREATE TABLE "+TABLA_CAMPO_HORA+"("
            +CAMPO_CH_HORA+" TEXT)";
    public static final String ACTUALIZAR_TABLA_CAMPO_HORA="DROP TABLE IF EXISTS "+TABLA_CAMPO_HORA;

  //---------------------TABLA RESERVA--------------------------------------------------------------


  public static final String TABLA_LISTA_RESERVA="LISTA_RESERVA";

  public static final String CAMPO_LR_RESERVA_PK="RESERVA_PK";
  public static final String CAMPO_LR_CANCHA="CANCHA";
  public static final String CAMPO_LR_DIRECCION="DIRECCION";
  public static final String CAMPO_LR_ESTADO="ESTADO";
  public static final String CAMPO_LR_FECHA="FECHA";
  public static final String CAMPO_LR_HORA="HORA";
  public static final String CAMPO_LR_TELEFONO="TELEFONO";
  public static final String CAMPO_LR_PRECIO="PRECIO";
  public static final String CAMPO_LR_PREDIO="PREDIO";
  public static final String CAMPO_LR_SENA="SENA";
  public static final String CAMPO_LR_SUPERFICIE="SUPERFICIE";
  public static final String CAMPO_LR_TECHADO="TECHADO";
  public static final String CAMPO_LR_TIMESTAMP="TIMESTAMP";
  public static final String CAMPO_LR_TIPO="TIPO";
  public static final String CAMPO_LR_ZONA="ZONA";
  public static final String CAMPO_LR_NRESERVA="NRESERVA";

  public static final String CREAR_TABLA_LISTA_RESERVA= "CREATE TABLE "+TABLA_LISTA_RESERVA+"("
          +CAMPO_LR_CANCHA+" TEXT,"
          +CAMPO_LR_DIRECCION+" TEXT,"
          +CAMPO_LR_ESTADO+" TEXT,"
          +CAMPO_LR_FECHA+" TEXT,"
          +CAMPO_LR_HORA+" TEXT,"
          +CAMPO_LR_PRECIO+" TEXT,"
          +CAMPO_LR_PREDIO+" TEXT,"
          +CAMPO_LR_RESERVA_PK+" INTEGER,"
          +CAMPO_LR_SENA+" TEXT,"
          +CAMPO_LR_SUPERFICIE+" TEXT,"
          +CAMPO_LR_TECHADO+" TEXT,"
          +CAMPO_LR_TELEFONO+" TEXT,"
          +CAMPO_LR_TIMESTAMP+" TEXT,"
          +CAMPO_LR_TIPO+" TEXT,"
          +CAMPO_LR_ZONA+" TEXT,"
          +CAMPO_LR_NRESERVA+" TEXT)";
  public static final String ACTUALIZAR_TABLA_LISTA_RESERVA="DROP TABLE IF EXISTS "+TABLA_LISTA_RESERVA;

  //---------------------TABLA LISTA BUSQUEDA-------------------------------------------------------------


    public static final String TABLA_LISTA_BUSQUEDA="LISTA_BUSQUEDA";

    public static final String CAMPO_LB_PREDIO_PK="PREDIO_PK";
    public static final String CAMPO_LB_DIRECCION="DIRECCION";
    public static final String CAMPO_LB_TELEFONO="TELEFONO";
    public static final String CAMPO_LB_PRECIO="PRECIO";
    public static final String CAMPO_LB_PREDIO="PREDIO";
    public static final String CAMPO_LB_ZONA="ZONA";
    public static final String CAMPO_LB_EXTRA="EXTRA";
    public static final String CAMPO_LB_GPS="GPS";
    public static final String CAMPO_LB_VALORACION="VALORACION";
    public static final String CAMPO_LB_N_VALORACION="N_VALORACION";
    public static final String CAMPO_LB_URL="URL";




    public static final String CREAR_TABLA_LISTA_BUSQUEDA= "CREATE TABLE "+TABLA_LISTA_BUSQUEDA+"("
            +CAMPO_LB_PREDIO_PK+" INTEGER,"
            +CAMPO_LB_PREDIO+" TEXT,"
            +CAMPO_LB_DIRECCION+" TEXT,"
            +CAMPO_LB_ZONA+" TEXT,"
            +CAMPO_LB_TELEFONO+" TEXT,"
            +CAMPO_LB_PRECIO+" TEXT,"
            +CAMPO_LB_VALORACION+" INTEGER,"
            +CAMPO_LB_N_VALORACION+" INTEGER,"
            +CAMPO_LB_EXTRA+" INTEGER,"
            +CAMPO_LB_GPS+" TEXT,"
            +CAMPO_LB_URL+" TEXT)";
    public static final String ACTUALIZAR_TABLA_LISTA_BUSQUEDA="DROP TABLE IF EXISTS "+TABLA_LISTA_BUSQUEDA;

    //---------------------TABLA LISTA BUSQUEDA-------------------------------------------------------------


    public static final String TABLA_LISTA_DISPONIBILIDAD="LISTA_DISPONIBILIDAD";

    public static final String CAMPO_LD_CANCHA_PK="CANCHA_PK";
    public static final String CAMPO_LD_FECHA="FECHA";
    public static final String CAMPO_LD_HORA="HORA";
    public static final String CAMPO_LD_CANCHA="CANCHA";


    public static final String CREAR_TABLA_LISTA_DISPONIBILIDAD= "CREATE TABLE "+TABLA_LISTA_DISPONIBILIDAD+"("
            +CAMPO_LD_CANCHA_PK+" INTEGER,"
            +CAMPO_LD_CANCHA+" TEXT,"
            +CAMPO_LD_FECHA+" TEXT,"
            +CAMPO_LD_HORA+" TEXT)";
    public static final String ACTUALIZAR_TABLA_LISTA_DISPONIBILIDAD="DROP TABLE IF EXISTS "+TABLA_LISTA_DISPONIBILIDAD;

}


