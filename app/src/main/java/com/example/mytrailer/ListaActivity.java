package com.example.mytrailer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextoAdapter textoAdapter;
    private List<String> textos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        textos = new ArrayList<>();
        textoAdapter = new TextoAdapter(textos);
        recyclerView.setAdapter(textoAdapter);

        // Obtener los textos guardados en Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("textos")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Bucle a través de los documentos
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Obtener el texto del documento y agregarlo a la lista
                            String texto = documentSnapshot.getString("texto");
                            textos.add(texto);
                        }
                        // Notificar al adaptador de los cambios en los datos
                        textoAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Ocurrió un error al obtener los textos de Firestore
                    }
                });
    }
}
