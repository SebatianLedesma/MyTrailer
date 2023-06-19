package com.example.mytrailer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {
        private EditText etNombre;
        private EditText etApellido;
        private EditText etUsuario;
        private Button btnEditar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_profile);

            // Obtener referencias a los elementos de la interfaz
            etNombre = findViewById(R.id.etNombre);
            etApellido = findViewById(R.id.etApellido);
            etUsuario = findViewById(R.id.etUsuario);
            btnEditar = findViewById(R.id.btnEditar);

            // Obtener los datos del usuario actual y mostrarlos en los campos de texto
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String nombre = currentUser.getDisplayName();
                String email = currentUser.getEmail();

                etNombre.setText(nombre);
                etUsuario.setText(email);
            }

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editarDatosUsuario();
                }
            });
        }

    private void editarDatosUsuario() {
        // Obtiene los nuevos valores ingresados en los campos de texto
        String nuevoNombre = etNombre.getText().toString();
        String nuevoApellido = etApellido.getText().toString();
        String nuevoUsuario = etUsuario.getText().toString();

        // Crea un mapa con los campos que deseas actualizar en Firestore
        Map<String, Object> datosActualizados = new HashMap<>();
        datosActualizados.put("nombre", nuevoNombre);
        datosActualizados.put("apellido", nuevoApellido);
        datosActualizados.put("usuario", nuevoUsuario);

        // Obtiene la referencia al documento del usuario actual en Firestore
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DocumentReference usuarioRef = FirebaseFirestore.getInstance().collection("usuarios").document(uid);

            // Actualiza los datos del usuario en Firestore
            usuarioRef.update(datosActualizados)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Los datos se actualizaron correctamente
                            Toast.makeText(UserProfileActivity.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Ocurrió un error al actualizar los datos
                            Toast.makeText(UserProfileActivity.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}
