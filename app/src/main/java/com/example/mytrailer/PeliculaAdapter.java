package com.example.mytrailer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder> {
    private List<String> peliculasList;

    public PeliculaAdapter(List<String> peliculasList) {
        this.peliculasList = peliculasList;
    }

    @NonNull
    @Override
    public PeliculaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelicula, parent, false);
        return new PeliculaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaViewHolder holder, int position) {
        String pelicula = peliculasList.get(position);
        holder.bind(pelicula);
    }

    @Override
    public int getItemCount() {
        return peliculasList.size();
    }

    public static class PeliculaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewPelicula;

        public PeliculaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPelicula = itemView.findViewById(R.id.textViewPelicula);
        }

        public void bind(String pelicula) {
            textViewPelicula.setText(pelicula);
        }
    }
}
