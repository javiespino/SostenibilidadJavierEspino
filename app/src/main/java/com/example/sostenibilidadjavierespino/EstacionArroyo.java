package com.example.sostenibilidadjavierespino;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class EstacionArroyo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<SensorItem> sensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estacion_arroyo);

        recyclerView = findViewById(R.id.recyclerSensores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarSensores();

        SensorAdapter adapter = new SensorAdapter(sensores, this);
        recyclerView.setAdapter(adapter);

        BottomNavigationView navAtras = findViewById(R.id.menu_atras);
        navAtras.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_volver) {
                finish();
                return true;
            }
            return false;
        });
    }

    // Cambiar estos sensores por los de la estacion del AH
    private void cargarSensores() {
        sensores = List.of(
                new SensorItem("", "Radiación Solar", R.drawable.ic_luminosidad),
                new SensorItem("", "Temperatura Actual", R.drawable.ic_temperatura),
                new SensorItem("", "Humedad Relativa (%)", R.drawable.ic_humedad),
                new SensorItem("", "Presión Atmosférica (hPa)", R.drawable.ic_presion),
                new SensorItem("", "Velocidad del viento", R.drawable.ic_viento),
                new SensorItem("", "Dirección del viento", R.drawable.ic_brujula),
                new SensorItem("", "Precipitación actual", R.drawable.ic_lluvia),
                new SensorItem("", "Precipitación diaria", R.drawable.ic_lluvia),
                new SensorItem("", "Precipitación anual", R.drawable.ic_lluvia)
        );
    }

    public void getSensor(String entityId, TextView textView, String label) {
        HomeAssistantApiEstacion.getSensorState(entityId, new HomeAssistantApiEstacion.SensorCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                String state = json.optString("state", "?");
                String unit;
                JSONObject attrs = json.optJSONObject("attributes");
                if (attrs != null)
                    unit = attrs.optString("unit_of_measurement", "");
                else {
                    unit = "";
                }

                runOnUiThread(() ->
                        textView.setText(label + ": " + state + " " + unit)
                );
            }

            @Override
            public void onError(IOException e, int code) {
                runOnUiThread(() ->
                        textView.setText(label + " Error: " + e.getMessage())
                );
            }
        });
    }
}
