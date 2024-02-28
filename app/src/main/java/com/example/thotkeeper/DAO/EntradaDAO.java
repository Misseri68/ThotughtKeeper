package com.example.thotkeeper.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EntradaDAO {
    private final GestorBD gestorBD;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public EntradaDAO(Context context) {
        this.gestorBD = GestorBD.obtenerInstancia(context);
    }

    public boolean crearEntrada(Entrada entrada) {
        SQLiteDatabase db = gestorBD.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("tipo", entrada.getTipo());
        valores.put("contenido", entrada.getContenido());
        valores.put("fecha", dateFormat.format(entrada.getFecha()));
        valores.put("idChat", entrada.getIdChat());
        valores.put("color", entrada.getColor());

        long resultado = db.insert("Entrada", null, valores);
        return resultado != -1;
    }

    //TODO me gustaria implementar un LIMIT para que no se carguen todas de golpe, con un boton "leer mas" pero no se hacerlo por ahora
    public List<Entrada> leerEntradas() {
        List<Entrada> entradas = new ArrayList<>();
        SQLiteDatabase db = gestorBD.getReadableDatabase();
        Cursor cursor = db.query("Entrada", null, null, null, null, null, "fecha DESC");

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int contenidoIndex = cursor.getColumnIndex("contenido");
            int fechaIndex = cursor.getColumnIndex("fecha");
            int idChatIndex = cursor.getColumnIndex("idChat");
            int colorIndex = cursor.getColumnIndex("color");

            if (idIndex != -1 && contenidoIndex != -1 && fechaIndex != -1 && idChatIndex != -1 && colorIndex != -1) {
                int id = cursor.getInt(idIndex);
                String contenido = cursor.getString(contenidoIndex);
                String fechaStr = cursor.getString(fechaIndex);
                String idChat = cursor.getString(idChatIndex);
                int color = cursor.getInt(colorIndex);

                Entrada entrada = new Entrada();
                entrada.setId(id);
                entrada.setContenido(contenido);
                entrada.setFecha(fechaStr);
                entrada.setIdChat(idChat);
                entrada.setColor(color);
                entradas.add(entrada);
            }
        }
        cursor.close();
        return entradas;
    }


    public List<Entrada> buscarEntradasPorContenido(String busqueda) {
        List<Entrada> entradasEncontradas = new ArrayList<>();
        SQLiteDatabase db = gestorBD.getReadableDatabase();
        Cursor cursor = db.query("Entrada", null, "contenido LIKE ?", new String[]{"%" + busqueda + "%"}, null, null, "fecha DESC");

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int contenidoIndex = cursor.getColumnIndex("contenido");
            int fechaIndex = cursor.getColumnIndex("fecha");
            int idChatIndex = cursor.getColumnIndex("idChat");
            int colorIndex = cursor.getColumnIndex("color");

            if (idIndex != -1 && contenidoIndex != -1 && fechaIndex != -1 && idChatIndex != -1 && colorIndex != -1) {
                int id = cursor.getInt(idIndex);
                String contenido = cursor.getString(contenidoIndex);
                String fechaStr = cursor.getString(fechaIndex);

                String idChat = cursor.getString(idChatIndex);
                Integer color = cursor.isNull(colorIndex) ? null : cursor.getInt(colorIndex);
                Entrada entrada = new Entrada();
                entrada.setId(id);
                entrada.setContenido(contenido);
                entrada.setFecha(fechaStr);
                entrada.setIdChat(idChat);
                entrada.setColor(color);
                entradasEncontradas.add(entrada);
            }
        }
        cursor.close();
        return entradasEncontradas;
    }


    public List<Entrada> buscarEntradasPorColor(Integer colorBusqueda) {
        List<Entrada> entradasEncontradas = new ArrayList<>();
        SQLiteDatabase db = gestorBD.getReadableDatabase();
        // Realizamos la consulta filtrando por el campo color.
        Cursor cursor = db.query("Entrada", null, "color = ?", new String[]{String.valueOf(colorBusqueda)}, null, null, "fecha DESC");

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int contenidoIndex = cursor.getColumnIndex("contenido");
            int fechaIndex = cursor.getColumnIndex("fecha");
            int idChatIndex = cursor.getColumnIndex("idChat");
            int colorIndex = cursor.getColumnIndex("color");

            if (idIndex != -1 && contenidoIndex != -1 && fechaIndex != -1 && idChatIndex != -1 && colorIndex != -1) {
                int id = cursor.getInt(idIndex);
                String contenido = cursor.getString(contenidoIndex);
                String fechaStr = cursor.getString(fechaIndex);
                String idChat = cursor.getString(idChatIndex);
                int color = cursor.getInt(colorIndex);

                Entrada entrada = new Entrada();
                entrada.setId(id);
                entrada.setContenido(contenido);
                entrada.setFecha(fechaStr);
                entrada.setIdChat(idChat);
                entrada.setColor(color);
                entradasEncontradas.add(entrada);
            }
        }
        cursor.close();
        return entradasEncontradas;
    }


    public boolean actualizarEntrada(Entrada entrada) {
        SQLiteDatabase db = gestorBD.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("tipo", entrada.getTipo());
        valores.put("contenido", entrada.getContenido());
        valores.put("fecha", dateFormat.format(entrada.getFecha()));
        valores.put("idChat", entrada.getIdChat());
        valores.put("color", entrada.getColor());

        int resultado = db.update("Entrada", valores, "id = ?", new String[]{String.valueOf(entrada.getId())});
        return resultado > 0;
    }

    public boolean eliminarEntrada(int id) {
        SQLiteDatabase db = gestorBD.getWritableDatabase();
        int resultado = db.delete("Entrada", "id = ?", new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    public ArrayList<Entrada> consultaEntradasJOIN(String idChat) {
        ArrayList<Entrada> entradas = new ArrayList<>();
        SQLiteDatabase db = gestorBD.getReadableDatabase();

        // Ajustamos la consulta para incluir un JOIN con la tabla Chat y obtener el nombre
        String query = GestorBD.JOIN_QUERY;
        Cursor cursor = db.rawQuery(query, new String[]{idChat});

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("id");
                int contenidoIndex = cursor.getColumnIndex("contenido");
                int fechaIndex = cursor.getColumnIndex("fecha");
                int idChatIndex = cursor.getColumnIndex("id_chat");
                int colorIndex = cursor.getColumnIndex("color");
                int nombreChatIndex = cursor.getColumnIndex("nombreChat");

                if (idIndex != -1 && colorIndex != -1 && contenidoIndex != -1 && fechaIndex != -1 && idChatIndex != -1 && nombreChatIndex != -1) {
                    Entrada entrada = new Entrada();
                    entrada.setId(cursor.getInt(idIndex));
                    entrada.setContenido(cursor.getString(contenidoIndex));
                    entrada.setFecha(cursor.getString(fechaIndex));
                    entrada.setIdChat(cursor.getString(idChatIndex));
                    entrada.setColor(cursor.getInt(colorIndex));
                    entrada.setNombreChat(cursor.getString(nombreChatIndex));
                    entradas.add(entrada);
                } else {
                    System.out.println("Mal error jajaja pero lo he puesto en un sout porque me gusta hacer sufrir a los demás");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return entradas;
    }

}
