package com.example.mytrailer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private Button btnAccion;

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

        // Obtener referencia al botón de Acción
        btnAccion = findViewById(R.id.btnAccion);

        // Configurar el evento de clic del botón Acción
        btnAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar AccionActivity
                Intent intent = new Intent(HomeActivity.this, AccionActivity.class);
                startActivity(intent);
            }
        });
    }
}

