package com.example.mytrailer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private Button btnAccion;

    private List<String> peliculasList = new ArrayList<>();

    private void guardarTexto() {
        EditText editText = findViewById(R.id.editText);
        String pelicula = editText.getText().toString();

        // Obtener referencia al usuario actual
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Obtener referencia a la base de datos
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Obtener la lista existente de películas desde la base de datos
            db.collection("usuarios").document(userId).collection("peliculas").document("lista")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                List<String> peliculasList = (List<String>) documentSnapshot.get("peliculas");
                                if (peliculasList != null) {
                                    // Agregar la nueva película a la lista existente
                                    peliculasList.add(pelicula);

                                    // Crear un objeto Map con la lista actualizada de películas
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("peliculas", peliculasList);

                                    // Guardar la lista de películas actualizada en la base de datos
                                    db.collection("usuarios").document(userId).collection("peliculas").document("lista")
                                            .set(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Película agregada exitosamente a la lista en la base de datos
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Ocurrió un error al guardar la lista de películas actualizada
                                                }
                                            });
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Ocurrió un error al obtener la lista de películas de Firestore
                        }
                    });
        }
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

        //Acción
        btnAccion = findViewById(R.id.btnAccion);
        btnAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia AccionActivity
                Intent intent = new Intent(HomeActivity.this, AccionActivity.class);
                startActivity(intent);
            }
        });

        //"Suspenso"
        Button btnSuspenso = findViewById(R.id.btnSuspenso);
        btnSuspenso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inicia SuspensoActivity
                Intent intent = new Intent(HomeActivity.this, SuspensoActivity.class);
                startActivity(intent);
            }
        });

        //Comedia"
        Button btnComedia = findViewById(R.id.btnComedia);
        btnComedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inicia ComediaActivity
                Intent intent = new Intent(HomeActivity.this, ComediaActivity.class);
                startActivity(intent);
            }
        });

        // "Ficcion"
        Button btnFiccion = findViewById(R.id.btnFiccion);
        btnFiccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia FiccionActivity
                Intent intent = new Intent(HomeActivity.this, FiccionActivity.class);
                startActivity(intent);
            }
        });

        //"Drama"
        Button btnDrama = findViewById(R.id.btnDrama);
        btnDrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia DramaActivity
                Intent intent = new Intent(HomeActivity.this, DramaActivity.class);
                startActivity(intent);
            }
        });

        // Metodo pra guardar peliculas
        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTexto();

                // Redirecciona a ListaActivity al guardar
                Intent intent = new Intent(HomeActivity.this, ListaActivity.class);
                startActivity(intent);
            }
        });



    }
}

