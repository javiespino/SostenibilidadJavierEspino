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

public class InvernaderoSuarez extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<SensorItem> sensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invernadero_suarez);

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

    private void cargarSensores() {
        sensores = List.of(
                new SensorItem("", "Presión atmosférica", R.drawable.ic_presion),
                new SensorItem("sensor.agsex_sdf_huerto_lht65n_temperatura", "Temperatura", R.drawable.ic_temperatura),
                new SensorItem("sensor.agsex_sdf_huerto_lht65n_humedad", "Humedad", R.drawable.ic_humedadtierra),
                new SensorItem("sensor.agsex_sdf_huerto_lht65n_iluminacionexterno", "Luminosidad", R.drawable.ic_luminosidad),
                new SensorItem("sensor.agsex_sdf_invernadero_lht65n_temperatura", "Temp Tierra", R.drawable.ic_temptierra),
                new SensorItem("sensor.agsex_sdf_invernadero_lht65n_humedad", "Humedad Tierra", R.drawable.ic_humedadtierra),
                new SensorItem("sensor.agsex_sdf_pasillo_lht52_temperatura", "Temperatura Clase", R.drawable.ic_clasetemp),
                new SensorItem("sensor.agsex_sdf_pasillo_lht52_humedad", "Humedad Clase", R.drawable.ic_humedad)
        );
    }

    public void getSensor(String entityId, TextView textView, String label) {
        HomeAssistantApi.getSensorState(entityId, new HomeAssistantApi.SensorCallback() {
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
