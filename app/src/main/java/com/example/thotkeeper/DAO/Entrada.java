package com.example.thotkeeper.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Entrada {
    private int id;
    private String tipo = "Texto";//"texto" o "imagen"
    private String contenido = ""; //Texto del mensaje o URL de la imagen. Por el momento sólo será texto.
    private Date fecha;
    private String idChat; //Clave foranea (FK) al chat al que pertenece
    private String nombreChat; //Esto se pondrá bien después de hacer una consulta con join usando l aFK de idChat.
    private Integer color;

    public Entrada(int id, String contenido, String idChat, Integer color) {
        this.id = id;
        this.tipo = "Texto";
        this.contenido = contenido;
        this.fecha = new Date(); // Se inicializa con la fecha actual
        this.idChat = idChat;
        this.color = color;
    }

    public Entrada(int id, String contenido, Date fecha, String idChat, Integer color) {
        this.id = id;
        this.tipo = "Texto";
        this.contenido = contenido;
        this.fecha = fecha;
        this.idChat = idChat;
        this.color = color;
    }

    public Entrada() {
        this.fecha = new Date();
        this.tipo = "Texto";


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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(String fechaStr) {
        this.fecha = convertirStringAFecha(fechaStr);
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public static Date convertirStringAFecha(String fechaEnTexto) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        try {
            return formato.parse(fechaEnTexto);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getNombreChat() {
        return nombreChat;
    }

    public void setNombreChat(String nombreChat) {
        this.nombreChat = nombreChat;
    }
}