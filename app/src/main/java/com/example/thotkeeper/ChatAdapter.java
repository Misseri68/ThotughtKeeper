package com.example.thotkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.thotkeeper.DAO.Chat;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private final Context context;
    private final List<Chat> chats;
    private  MainActivity mainA;

    public ChatAdapter(Context context, List<Chat> chats) {
        super(context, R.layout.chat_item_view, chats);
        this.context = context;
        this.chats = chats;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_item_view, parent, false);
        }
        if (getContext() instanceof MainActivity) {
            mainA = (MainActivity) getContext();
        }

        LinearLayout layout = convertView.findViewById(R.id.linearLayout);

        TextView chatName = convertView.findViewById(R.id.chatName);
        TextView chatDate = convertView.findViewById(R.id.chatDate);
        ImageView chatImage = convertView.findViewById(R.id.chatImage);

        Chat chat = chats.get(position);
        chatName.setText(chat.getNombre_id());
        chatDate.setText(chat.getUltimaFecha().toString());
        chatImage.setImageResource(R.drawable.nuevochat);
        layout.setBackgroundColor(chat.getColor());
        cambiarColorTexto(chat, chatName, chatDate);
        layout.setOnClickListener(v -> {
                    mainA.siguienteFragment(chat);
                });
        layout.setOnLongClickListener(v -> {
                mainA = (MainActivity) getContext();
                mainA.getChatsFragment().ventanaOpcionChat(chat);
            return false;
            });
        return convertView;
    }

    public void cambiarColorTexto(Chat chat, TextView nombre, TextView fecha){
      int colorChat = chat.getColor();
        int colorRojo = ContextCompat.getColor(mainA, R.color.red);
        int colorNaranja = ContextCompat.getColor(mainA, R.color.orange);
        int colorVerde = ContextCompat.getColor(mainA, R.color.green);
        int colorAzul = ContextCompat.getColor(mainA, R.color.blue);
        int colorVioleta = ContextCompat.getColor(mainA, R.color.violet);
        if (colorChat == colorRojo || colorChat == colorNaranja || colorChat == colorVerde || colorChat == colorAzul || colorChat == colorVioleta) {
            nombre.setTextColor(ContextCompat.getColor(mainA, R.color.white));
        } else {
            nombre.setTextColor(ContextCompat.getColor(mainA, R.color.black));
        }
    }
}
