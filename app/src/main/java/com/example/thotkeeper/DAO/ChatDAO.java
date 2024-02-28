package com.example.thotkeeper.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.content.ContextCompat;

import com.example.thotkeeper.DAO.GestorBD;
import com.example.thotkeeper.MainActivity;
import com.example.thotkeeper.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatDAO {
    private final GestorBD gestorBD;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final MainActivity mainA;

    public ChatDAO(Context context, MainActivity mainA) {
        this.gestorBD = GestorBD.obtenerInstancia(context);
        this.mainA = mainA;
    }

    public boolean crearChat(Chat chat) {
        if(buscarChatPorNombre(chat.getNombre_id())==null){
            SQLiteDatabase db = gestorBD.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("nombre_id", chat.getNombre_id());
            valores.put("fotoChat", chat.getFotoChat());
            valores.put("color", ContextCompat.getColor(mainA, R.color.white)); //Si no hago esto se guarda la referencia del recurso, no el int color, cosa que me fallaba.
            valores.put("ultimaFecha", dateFormat.format(chat.getUltimaFecha()));

            long resultado = db.insert("Chat", null, valores);
            return resultado != -1; //Si nos devuelve -1, hubo error.
        }
        mainA.mostrarToast("El chat que intentaste crear ya existe.");
        return false; //Si no es null es porque ya existe.

    }

    public List<Chat> leerChats() {
        List<Chat> chats = new ArrayList<>();
        SQLiteDatabase db = gestorBD.getReadableDatabase();
        Cursor cursor = db.query("Chat", null, null, null, null, null, "ultimaFecha DESC");

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("nombre_id");
            int fotoChatIndex = cursor.getColumnIndex("fotoChat");
            int colorIndex = cursor.getColumnIndex("color");
            int ultimaFechaIndex = cursor.getColumnIndex("ultimaFecha");

            if (idIndex != -1  && fotoChatIndex != -1 && colorIndex != -1 && ultimaFechaIndex != -1) {
                // Suponiendo que todos los índices están bien, los leemos (si no, dará nullPointerException :(   )
                String id = cursor.getString(idIndex);
                String fotoChat = cursor.getString(fotoChatIndex);
                int color = cursor.getInt(colorIndex);
                String ultimaFecha = cursor.getString(ultimaFechaIndex);

                Chat chat = new Chat();
                chat.setNombre_id(id);
                chat.setFotoChat(fotoChat);
                chat.setColor(color);
                chat.setUltimaFecha(ultimaFecha);
                chats.add(chat);
            }
        }
        cursor.close();
        return chats;
    }

    public Chat buscarChatPorNombre(String nombreBusqueda) {
        SQLiteDatabase db = gestorBD.getReadableDatabase();
        Cursor cursor = db.query("Chat", null, "lower(nombre_id) = ?", new String[]{nombreBusqueda.trim().toLowerCase()}, null, null, null); //uso el new String[] porque es mas seguro y previene inyecciones sql

        if (cursor.moveToFirst()) {
            int nombreIdIndex = cursor.getColumnIndex("nombre_id");
            int fotoChatIndex = cursor.getColumnIndex("fotoChat");
            int colorIndex = cursor.getColumnIndex("color");
            int ultimaFechaIndex = cursor.getColumnIndex("ultimaFecha");

            // Verificamos que ninguno de los índices sea -1
            if (nombreIdIndex != -1 && fotoChatIndex != -1 && colorIndex != -1 && ultimaFechaIndex != -1) {
                String id = cursor.getString(nombreIdIndex);
                String fotoChat = cursor.getString(fotoChatIndex);
                int color = cursor.getInt(colorIndex);
                String ultimaFechaStr = cursor.getString(ultimaFechaIndex);

                Chat chat = new Chat();
                chat.setNombre_id(id);
                chat.setFotoChat(fotoChat);
                chat.setColor(color);
                chat.setUltimaFecha(ultimaFechaStr);

                cursor.close();
                return chat;
            }
        }
        cursor.close();
        return null; //No se encontró el chat o los índices no funcionan bien.
    }

    public List<Chat> buscarChatsPorNombre(String busqueda) {
        List<Chat> chatsEncontrados = new ArrayList<>();
        SQLiteDatabase db = gestorBD.getReadableDatabase();

        String busquedaEnMinusculas = busqueda.toLowerCase();

        Cursor cursor = db.query("Chat", null, "LOWER(nombre_id) LIKE ?", new String[]{"%" + busquedaEnMinusculas + "%"}, null, null, "ultimaFecha DESC");

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("nombre_id");
            int fotoChatIndex = cursor.getColumnIndex("fotoChat");
            int colorIndex = cursor.getColumnIndex("color");
            int ultimaFechaIndex = cursor.getColumnIndex("ultimaFecha");

            if (idIndex != -1 && fotoChatIndex != -1 && colorIndex != -1 && ultimaFechaIndex != -1) {
                String nombreId = cursor.getString(idIndex);
                String fotoChat = cursor.getString(fotoChatIndex);
                int color = cursor.getInt(colorIndex);
                String ultimaFechaStr = cursor.getString(ultimaFechaIndex);

                Chat chat = new Chat();
                chat.setNombre_id(nombreId);
                chat.setFotoChat(fotoChat);
                chat.setColor(color);
                chat.setUltimaFecha(ultimaFechaStr);

                chatsEncontrados.add(chat);
            }
        }
        cursor.close();
        return chatsEncontrados;
    }


    public boolean actualizarChat(Chat chat, String nombreIdAntiguo) {
        // Verifica si el nuevo nombre_id ya está en uso por otro chat
        if (!chat.getNombre_id().equals(nombreIdAntiguo) && buscarChatPorNombre(chat.getNombre_id()) != null) {
            // Si entra aquí, significa que el nuevo nombre_id ya está en uso
            mainA.mostrarToast("El nombre del chat ya está en uso.");
            return false;
        }

        SQLiteDatabase db = gestorBD.getWritableDatabase();
        ContentValues valores = new ContentValues();

        // Obtén los nuevos valores del objeto chat
        valores.put("nombre_id", chat.getNombre_id());
        valores.put("color", chat.getColor());
        valores.put("fotoChat", chat.getFotoChat()); // Asumiendo que también podrías querer actualizar la foto
        valores.put("ultimaFecha", dateFormat.format(chat.getUltimaFecha())); // Actualizar la fecha si es necesario

        // Actualiza la fila en la base de datos donde el nombre_id antiguo coincide
        int filasAfectadas = db.update("Chat", valores, "nombre_id=?", new String[]{nombreIdAntiguo});

        if (filasAfectadas > 0) {
            mainA.mostrarToast("Chat actualizado correctamente.");
            return true; // Retorna true si se actualizó al menos una fila
        } else {
            mainA.mostrarToast("Error al actualizar el chat.");
            return false;
        }
    }

    public boolean eliminarChat(String nombre_id) {
        SQLiteDatabase db = gestorBD.getWritableDatabase();
        int resultado = db.delete("Chat", "nombre_id=?", new String[]{nombre_id});
        return resultado > 0; // Si resultado es 0, no se eliminó ningúna fila.
    }

}
