package com.example.thotkeeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.thotkeeper.DAO.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {
    private MainActivity mainA;

    List<Chat> chatList;
    ListView listView;
    ImageButton nuevoChat, buscar;
    ChatAdapter chatAdapter;

    public ChatsFragment() {
    }

    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mainA.getChatDAO().leerChats().isEmpty()){
            Chat chat = new Chat();
            chat.setNombre_id("Prueba");
            mainA.getChatDAO().crearChat(chat);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        chatList = new ArrayList<>();
        return inflater.inflate(R.layout.chats_fragment, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);

        cargarLista();

        nuevoChat = view.findViewById(R.id.addChat);
        nuevoChat.setOnClickListener(v -> {
            addChatVentanaEmergente();
        });

        buscar = view.findViewById(R.id.buscar);
        buscar.setOnClickListener(v -> busqueda());
    }



//APPBAR METODOS:

    //Al pulsar el boton de la derecha "+"
    public void addChatVentanaEmergente() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainA);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ventana_emergente, null);
        final EditText editTextChatName = dialogView.findViewById(R.id.editTextNombre);
        builder.setView(dialogView)
                .setPositiveButton("Añadir", (dialog, id) -> {

                    String chatName = editTextChatName.getText().toString();
                    Chat chat = new Chat();
                    chat.setNombre_id(chatName);
                    mainA.getChatDAO().crearChat(chat);
                    cargarLista();
                })
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Al mantener pulsado un chat:

    public void ventanaOpcionChat(Chat chat) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mainA);
            builder.setTitle("Editar Chat");
            builder.setMessage(R.string.editareliminar);
            //Aquí invierto porque me gusta mas otro orden
            builder.setNegativeButton(R.string.editar, (dialog, which) -> ventanaEdicion(chat));
            builder.setPositiveButton(R.string.borrar, (dialog, which) -> {
               mainA.getChatDAO().eliminarChat(chat.getNombre_id());
               cargarLista();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
    }

    public void ventanaEdicion(Chat chat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainA);
        LayoutInflater inflater = mainA.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ventana_editar_chat, null);

        EditText editTextNuevoNombre = dialogView.findViewById(R.id.editTextNuevoNombre);
        editTextNuevoNombre.setText(chat.getNombre_id());
        Spinner spinnerColor = dialogView.findViewById(R.id.spinnerColor);
        String[] colores = {"Rojo", "Salmón", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Blanco"}; // Reemplaza con tus colores
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mainA, android.R.layout.simple_spinner_item, colores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adapter);

        builder.setView(dialogView);
        builder.setTitle("Editar Chat");
        builder.setPositiveButton("Ok", (dialog, which) -> {
            String nombreAntiguo = chat.getNombre_id();
            String nuevoNombre = editTextNuevoNombre.getText().toString();
            String colorSeleccionado = spinnerColor.getSelectedItem().toString();
            switchColorElegido(colorSeleccionado, chat);
            chat.setNombre_id(nuevoNombre);
            mainA.getChatDAO().actualizarChat(chat, nombreAntiguo);
            cargarLista();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void switchColorElegido(String color, Chat chat){
        // Por ejemplo, si necesitas hacer algo específico para cada color
        int colorResourceId = R.color.black;
        switch (color) {
            case "Rojo":
                colorResourceId = R.color.red;
                break;
            case "Salmón":
                colorResourceId = R.color.salmon; // Asegúrate de tener un color llamado salmon en tus recursos
                break;
            case "Naranja":
                colorResourceId = R.color.orange;
                break;
            case "Amarillo":
                colorResourceId = R.color.yellow;
                break;
            case "Verde":
                colorResourceId = R.color.green;
                break;
            case "Azul":
                colorResourceId = R.color.blue;
                break;
            case "Violeta":
                colorResourceId = R.color.violet;
                break;
            case "Blanco":
                colorResourceId = R.color.white;
                break;
        }
        chat.setColor(ContextCompat.getColor(mainA, colorResourceId));
    }

    private void busqueda() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ventana_buscar, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        EditText editTextBuscar = dialogView.findViewById(R.id.editTextBuscar);

        builder.setPositiveButton("Buscar", (dialog, which) -> {

            String textoBusqueda = editTextBuscar.getText().toString();
            realizarBusqueda(textoBusqueda);
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Método stub para realizar la búsqueda
    private void realizarBusqueda(String textoBusqueda) {
      this.chatList = mainA.getChatDAO().buscarChatsPorNombre(textoBusqueda);
        chatAdapter = new ChatAdapter(getActivity() , chatList);
        listView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }

    //Cosas que se inicializan cuando empieza el Fragment
    public void cargarLista(){
        chatList = mainA.getChatDAO().leerChats();
        chatAdapter = new ChatAdapter(getActivity() , chatList);
        listView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();

    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public void setMainActivity(MainActivity activity) {
        this.mainA = activity;
    }


}