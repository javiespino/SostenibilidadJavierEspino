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

    private List<SensorItem> sensores;
    private AppCompatActivity activity;

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
        holder.text.setText(item.label + ": cargando...");

        // Llama al método getSensor de la Activity correspondiente
        if (activity instanceof InvernaderoArroyo) {
            ((InvernaderoArroyo) activity).getSensor(item.entityId, holder.text, item.label);
        } else if (activity instanceof InvernaderoSuarez) {
            ((InvernaderoSuarez) activity).getSensor(item.entityId, holder.text, item.label);
        }
    }

    @Override
    public int getItemCount() {
        return sensores.size();
    }

    static class SensorViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView text;

        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iconSensor);
            text = itemView.findViewById(R.id.textSensor);
        }
    }
}
