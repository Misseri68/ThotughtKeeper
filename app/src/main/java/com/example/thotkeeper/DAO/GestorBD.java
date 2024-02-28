package com.example.thotkeeper.DAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GestorBD extends SQLiteOpenHelper {

    private static GestorBD instancia = null;

    //Aquí pongo todos los nombres como final para que sea más facil modificarla si es necesario.
    private static final String NOMBRE_BASE_DE_DATOS = "thotKeeper.db";
    private static final int VERSION_BASE_DE_DATOS = 1;

    // Nombres de las tablas
    private static final String TABLA_CHAT = "Chat";
    private static final String TABLA_ENTRADA = "Entrada";

    // Columnas de la tabla Chat
    private static final String COLUMNA_ID_CHAT = "nombre_id";
    private static final String COLUMNA_FOTO_CHAT = "fotoChat";
    private static final String COLUMNA_COLOR = "color";
    private static final String COLUMNA_ULTIMA_FECHA = "ultimaFecha";

    // Columnas de la tabla Entrada
    private static final String COLUMNA_ID_ENTRADA = "id";
    private static final String COLUMNA_TIPO = "tipo";
    private static final String COLUMNA_CONTENIDO = "contenido";
    private static final String COLUMNA_FECHA = "fecha";
    private static final String COLUMNA_ID_CHAT_FK = "id_chat";
    private static final String COLUMNA_COLOR_ENTRADA = "color";

    private static final String CREAR_TABLA_CHAT = "CREATE TABLE " + TABLA_CHAT + "(" +
            COLUMNA_ID_CHAT + " TEXT PRIMARY KEY," +
            COLUMNA_FOTO_CHAT + " TEXT," +
            COLUMNA_COLOR + " INTEGER," +
            COLUMNA_ULTIMA_FECHA + " TEXT);";

    private static final String CREAR_TABLA_ENTRADA = "CREATE TABLE " + TABLA_ENTRADA + "(" +
            COLUMNA_ID_ENTRADA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMNA_TIPO + " TEXT NOT NULL," +
            COLUMNA_CONTENIDO + " TEXT NOT NULL," +
            COLUMNA_FECHA + " TEXT NOT NULL," +
            COLUMNA_ID_CHAT_FK + " TEXT NOT NULL," +
            COLUMNA_COLOR_ENTRADA + " INTEGER," +
            "FOREIGN KEY (" + COLUMNA_ID_CHAT_FK + ") REFERENCES " + TABLA_CHAT + "(" + COLUMNA_ID_CHAT + "));";

    public static final  String JOIN_QUERY = "SELECT E.*, C." + COLUMNA_ID_CHAT + " AS nombreChat FROM " + TABLA_ENTRADA + " E " +
            "JOIN " + TABLA_CHAT + " C ON E." + COLUMNA_ID_CHAT_FK + " = C." + COLUMNA_ID_CHAT +
            " WHERE E." + COLUMNA_ID_CHAT_FK + " = ?" +
            " ORDER BY E." + COLUMNA_FECHA + " DESC";


    private GestorBD(Context context) {
            super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
    }

    public static synchronized GestorBD obtenerInstancia(Context context) {
        if (instancia == null) {
            instancia = new GestorBD(context.getApplicationContext()); //Esto lo pongo aquí para no liarme instanciando varias bases a la vez y que no de error.
        }
        return instancia;
    }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREAR_TABLA_CHAT);
            db.execSQL(CREAR_TABLA_ENTRADA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Lo dejo aquí por si fuera necesario implementarlo en un futuro.
        }


}


