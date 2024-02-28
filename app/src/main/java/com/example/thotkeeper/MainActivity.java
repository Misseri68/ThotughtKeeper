package com.example.thotkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Toast;

import com.example.thotkeeper.DAO.Chat;
import com.example.thotkeeper.DAO.ChatDAO;
import com.example.thotkeeper.DAO.EntradaDAO;
import com.example.thotkeeper.DAO.GestorBD;

public class MainActivity extends AppCompatActivity {
    private GestorBD gestorBD;
    private ChatDAO chatDAO;
    private EntradaDAO entradaDAO;
    private Chat chatSeleccionado;
    ViewPager2 viewPager;

    ChatsFragment chatsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(this));

        chatDAO = new ChatDAO(getApplicationContext(), this);
        entradaDAO =  new EntradaDAO(getApplicationContext());
    }


    public void siguienteFragment(Chat chat) {
        if (viewPager != null) {
            int fragmentActual = viewPager.getCurrentItem();
            if(fragmentActual == 0){
                viewPager.setCurrentItem(1); //1 sería el fragment entrada.
                this.chatSeleccionado = chat; //Establezco el chatSeleccionado (que cogerá luego el fragmentEntrada para cargar datos si no es null)
                mostrarToast("Has clickado el item " + chat.getNombre_id());

            }
        }
    }


    public void mostrarToast(String message){
       if(message!=null) Toast.makeText(this, message, Toast.LENGTH_SHORT ).show();
    }

    public ChatDAO getChatDAO() {
        return chatDAO;
    }


    public EntradaDAO getEntradaDAO() {
        return entradaDAO;
    }

    public void setChatsFragment(ChatsFragment chatsFragment){
        this.chatsFragment = chatsFragment;
    }
    public ChatsFragment getChatsFragment(){
        return chatsFragment;
    }

//Para que al presoinar el boton atras no me saque del todo, solo me lleve al otro fragment.
    @Override
    public void onBackPressed() {
        int fragmentActual = viewPager.getCurrentItem();
        if(fragmentActual == 1){
            viewPager.setCurrentItem(0);
        }
        else if(fragmentActual == 0){
            //Si se ha realizado una busqueda, y presionas "atrás", volverá la lista COMPLETA de sql.
            chatsFragment.setChatList(chatDAO.leerChats());
            chatsFragment.cargarLista();
        }
        else  super.onBackPressed(); //Si no es el fragment 1 (el del chat), al presionar atrás que lo maneje el default del sistema.
    }
}