package com.example.mytrailer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class IntermediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermedia);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntermediaActivity.this, ListaActivity.class);
                startActivity(intent);
                finish(); // Opcionalmente, puedes finalizar la actividad intermedia si no necesitas volver a ella
            }
        }, 500); // Cambia el valor de 500 según tus necesidades (en milisegundos)
    }
}