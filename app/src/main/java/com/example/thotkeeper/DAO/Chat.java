package com.example.thotkeeper.DAO;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.thotkeeper.MainActivity;
import com.example.thotkeeper.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Chat {
    private String nombre_id;
    private List<Entrada> entradas;
    private String fotoChat;
    private int color;
    private Date ultimaFecha;

    private static final int COLOR_POR_DEFECTO = R.color.white;
    private static final String FOTO_POR_DEFECTO = "res\\drawable\\nuevochat.png";

    // Constructor sin argumentos
    public Chat(){
        this.nombre_id = "Nuevo chat";
        this.fotoChat = FOTO_POR_DEFECTO;
        this.color = COLOR_POR_DEFECTO;
        this.ultimaFecha = new Date();
        this.entradas = new ArrayList<>();
    }


    public String getNombre_id() {
        return nombre_id;
    }

    public void setNombre_id(String nombre_id) {
        this.nombre_id = nombre_id;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void cargarEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public String getFotoChat() {
        return fotoChat;
    }

    public void setFotoChat(String fotoChat) {
        this.fotoChat = fotoChat;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Date getUltimaFecha() {
        return ultimaFecha;
    }

    //Aquí le pasamos la fecha en String y usamos el conversor a Date
    public void setUltimaFecha(String ultimaFecha) {
        this.ultimaFecha = convertirStringAFecha(ultimaFecha);
    }

    public static Date convertirStringAFecha(String fechaEnTexto) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        try {
            return formato.parse(fechaEnTexto);
        } catch (ParseException e) {
            try {
                return formato.parse("01/01/1970 01:00:00");
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}