package com.example.thotkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Autenticacion extends AppCompatActivity {
    //Esta clase se encarga de que no puedan acceder a mi aplicación si no ponen una forma de verificarse, como cuando usas tu huella para abrir el movil. En mi caso será una contraseña.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autenticacion);
        EditText editText = findViewById(R.id.editText);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) { //IME_ACTION_DONE es como decir "hecho!" o como "confirmar".
                String inputText = editText.getText().toString();

                if (inputText.equals("DragosChan")) { //Me lo dijo un amigo, no fui yo, lo juro.
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(), "No te la sabeeeess jaja.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });


    }
}
