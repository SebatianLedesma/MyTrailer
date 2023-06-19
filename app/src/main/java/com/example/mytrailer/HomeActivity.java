package com.example.mytrailer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Obtener referencia al TextView de bienvenida
        tvWelcome = findViewById(R.id.tvWelcome);

        // Obtener el usuario actual
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Obtener el nombre del usuario actual
            String username = currentUser.getDisplayName();

            // Mostrar un mensaje de bienvenida
            String welcomeMessage = "¡Bienvenido!";
            tvWelcome.setText(welcomeMessage);
        }
    }
}
