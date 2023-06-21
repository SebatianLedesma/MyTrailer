package com.example.mytrailer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PeliculaAdapter peliculaAdapter;
    private List<String> peliculasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        peliculasList = new ArrayList<>();
        peliculaAdapter = new PeliculaAdapter(peliculasList);
        recyclerView.setAdapter(peliculaAdapter);

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
                                List<String> peliculas = (List<String>) documentSnapshot.get("peliculas");
                                if (peliculas != null) {
                                    peliculasList.clear(); // Limpiar la lista existente
                                    peliculasList.addAll(peliculas); // Agregar las nuevas películas
                                    peliculaAdapter.notifyDataSetChanged(); // Notificar al adaptador del cambio en los datos
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
}
