package com.example.mytrailer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> peliculasList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        listView = findViewById(R.id.listView);

        peliculasList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, peliculasList);
        listView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaActivity.this, HomeActivity.class));
                finish();
            }
        });

        // Obtener el usuario actual
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Obtener la referencia a la base de datos y la colección de películas
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("usuarios").document(userId).collection("peliculas").document("lista")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                List<String> peliculasFirestoreList = (List<String>) documentSnapshot.get("peliculas");
                                if (peliculasFirestoreList != null) {
                                    peliculasList.clear(); // Limpiar la lista existente
                                    peliculasList.addAll(peliculasFirestoreList); // Agregar las películas de Firestore
                                    adapter.notifyDataSetChanged(); // Notificar al adaptador del cambio en los datos
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Actualizar la lista y notificar al adaptador del cambio en los datos
            obtenerPeliculasFirestore();
            adapter.notifyDataSetChanged();
        }
    }

    private void obtenerPeliculasFirestore() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("usuarios").document(userId).collection("peliculas").document("lista")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                List<String> peliculasFirestoreList = (List<String>) documentSnapshot.get("peliculas");
                                if (peliculasFirestoreList != null) {
                                    peliculasList.clear();
                                    peliculasList.addAll(peliculasFirestoreList);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Manejar el error al obtener la lista de películas de Firestore
                        }
                    });
        }
    }




}
