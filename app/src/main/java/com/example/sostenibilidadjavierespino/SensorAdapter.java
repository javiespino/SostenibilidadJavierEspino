package com.example.sostenibilidadjavierespino;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {

    private final List<SensorItem> sensores;
    private final AppCompatActivity activity;

    public SensorAdapter(List<SensorItem> sensores, AppCompatActivity activity) {
        this.sensores = sensores;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sensor, parent, false);
        return new SensorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        SensorItem item = sensores.get(position);

        holder.icon.setImageResource(item.iconRes);
        holder.textTitulo.setText(item.titulo);
        holder.textSensor.setText(item.sensor);

        if (activity instanceof InvernaderoArroyo) {
            ((InvernaderoArroyo) activity).getSensor(item.entityId, holder.textTitulo, holder.textSensor, item.titulo);
        } else if (activity instanceof InvernaderoSuarez) {
            ((InvernaderoSuarez) activity).getSensor(item.entityId, holder.textTitulo, holder.textSensor, item.titulo);
        } else if (activity instanceof EstacionSuarez) {
            ((EstacionSuarez) activity).getSensor(item.entityId, holder.textTitulo, holder.textSensor, item.titulo);
        } else if (activity instanceof EstacionArroyo) {
            ((EstacionArroyo) activity).getSensor(item.entityId, holder.textTitulo, holder.textSensor, item.titulo);
        }
    }

    @Override
    public int getItemCount() {
        return sensores.size();
    }

    static class SensorViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView textTitulo;
        TextView textSensor;

        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iconSensor);
            textTitulo = itemView.findViewById(R.id.textTitulo);
            textSensor = itemView.findViewById(R.id.textSensor);
        }
    }
}