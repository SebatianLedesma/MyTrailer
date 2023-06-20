package com.example.mytrailer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class TextoAdapter extends RecyclerView.Adapter<TextoAdapter.TextoViewHolder> {

    private List<String> listaTextos;

    public TextoAdapter(List<String> listaTextos) {
        this.listaTextos = listaTextos;
    }

    @NonNull
    @Override
    public TextoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_texto, parent, false);
        return new TextoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TextoViewHolder holder, int position) {
        String texto = listaTextos.get(position);
        holder.textViewTexto.setText(texto);
    }

    @Override
    public int getItemCount() {
        return listaTextos.size();
    }

    public static class TextoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTexto;

        public TextoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTexto = itemView.findViewById(R.id.textViewTexto);
        }
    }
}
