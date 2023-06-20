package com.example.mytrailer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private Button btnAccion;

    private void guardarTexto() {
        EditText editText = findViewById(R.id.editText);
        String texto = editText.getText().toString();

        // Crear un intent para iniciar ListaActivity y pasar el texto como extra
        Intent intent = new Intent(HomeActivity.this, ListaActivity.class);
        intent.putExtra("texto", texto);
        startActivity(intent);
    }

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
        // Obtener referencia al botón de "Suspenso"
        Button btnSuspenso = findViewById(R.id.btnSuspenso);

        // Configurar el evento de clic del botón "Suspenso"
        btnSuspenso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar SuspensoActivity
                Intent intent = new Intent(HomeActivity.this, SuspensoActivity.class);
                startActivity(intent);
            }
        });

        // Obtener referencia al botón de "Comedia"
        Button btnComedia = findViewById(R.id.btnComedia);

        // Configurar el evento de clic del botón "Comedia"
        btnComedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar ComediaActivity
                Intent intent = new Intent(HomeActivity.this, ComediaActivity.class);
                startActivity(intent);
            }
        });

        // Obtener referencia al botón de "Ficcion"
        Button btnFiccion = findViewById(R.id.btnFiccion);

        // Configurar el evento de clic del botón "Ficcion"
        btnFiccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar FiccionActivity
                Intent intent = new Intent(HomeActivity.this, FiccionActivity.class);
                startActivity(intent);
            }
        });

        // Obtener referencia al botón de "Drama"
        Button btnDrama = findViewById(R.id.btnDrama);

        // Configurar el evento de clic del botón "Drama"
        btnDrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar DramaActivity
                Intent intent = new Intent(HomeActivity.this, DramaActivity.class);
                startActivity(intent);
            }
        });

        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTexto();
            }
        });

    }
}

