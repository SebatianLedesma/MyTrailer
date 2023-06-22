package com.example.mytrailer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private Button btnAccion;

    private List<String> peliculasList = new ArrayList<>();

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
                Intent intent = new Intent(HomeActivity.this, IntermediaActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        Button btnGoToLista = findViewById(R.id.btnLista);
        btnGoToLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, IntermediaActivity.class);
                startActivityForResult(intent, 1);
            }
        });


    }

    private void guardarTexto() {
        EditText editText = findViewById(R.id.editText);
        String pelicula = editText.getText().toString().trim();

        if (pelicula.isEmpty()) {
            // El campo está vacío, muestra un mensaje de error
            Toast.makeText(this, "Campo vacío", Toast.LENGTH_SHORT).show();
        } else {
            // Obtener referencia al usuario actual
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();

                // Obtener referencia a la base de datos
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Obtener referencia a la colección de películas del usuario
                CollectionReference peliculasCollection = db.collection("usuarios").document(userId).collection("peliculas");

                // Verificar si la colección "peliculas" existe
                peliculasCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                // La colección "peliculas" no existe, crearla
                                peliculasCollection.document("lista").set(new HashMap<>());
                            }

                            // Agregar la nueva película a la lista existente
                            peliculasCollection.document("lista").update("peliculas", FieldValue.arrayUnion(pelicula))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            setResult(RESULT_OK); // Indicar que la acción fue exitosa
                                            finish(); // Finalizar la actividad y volver a ListaActivity

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Ocurrió un error al guardar la película en la lista
                                        }
                                    });
                        } else {
                            // Ocurrió un error al verificar la existencia de la colección "peliculas"
                        }
                    }
                });
            }
        }
    }


}

