package com.example.sostenibilidadjavierespino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sostenibilidadjavierespino.R;

import java.util.List;

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter.FotoViewHolder> {

    private List<Integer> imagenes;

    public FotosAdapter(List<Integer> imagenes) {
        this.imagenes = imagenes;
    }

    @NonNull
    @Override
    public FotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foto, parent, false);
        return new FotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FotoViewHolder holder, int position) {
        holder.imageView.setImageResource(imagenes.get(position));
        holder.tvContador.setText((position + 1) + "/8");
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    static class FotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvContador;

        FotoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewFoto);
            tvContador = itemView.findViewById(R.id.tvContador);
        }
    }
}