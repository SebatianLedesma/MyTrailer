package com.example.mytrailer;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    private EditText etNombre, etApellido, etUsuario, etEmail, etContraseña;
    private Button btnRegistrar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Obtener referencias a los elementos de la interfaz de usuario
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etUsuario = findViewById(R.id.etUsuario);
        etEmail = findViewById(R.id.etEmail);
        etContraseña = findViewById(R.id.etContraseña);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Configurar el evento de clic del botón de registro
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        ImageButton btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la página principal
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Opcional: finalizar la actividad actual si no se desea volver a ella
            }
        });
    }

    private void registrarUsuario() {
        final String nombre = etNombre.getText().toString();
        final String apellido = etApellido.getText().toString();
        final String usuario = etUsuario.getText().toString();
        String email = etEmail.getText().toString();
        String contraseña = etContraseña.getText().toString();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || email.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(RegistroActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el formato del correo electrónico
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(RegistroActivity.this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar la longitud de la contraseña (puedes personalizar según tus requisitos)
        if (contraseña.length() < 6) {
            Toast.makeText(RegistroActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // El usuario se ha registrado correctamente, guarda los datos adicionales en Firestore
                                String userId = user.getUid();

                                // Crea un nuevo documento para el usuario en una colección "usuarios"
                                DocumentReference userRef = db.collection("usuarios").document(userId);

                                // Crea un objeto Map para almacenar los datos del usuario
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("nombre", nombre);
                                userData.put("apellido", apellido);
                                userData.put("usuario", usuario);
                                userData.put("email", email);

                                // Guarda los datos del usuario en Firestore
                                userRef.set(userData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                                                // Redirige al usuario a la pantalla de inicio de sesión
                                                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RegistroActivity.this, "Error al guardar los datos del usuario.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            // Error en el registro
                            Toast.makeText(RegistroActivity.this, "Error al registrar el usuario.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}