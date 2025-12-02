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

public class InvernaderoArroyo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<SensorItem> sensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invernadero_arroyo);

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
                new SensorItem("sensor.nodered_1e5a20d2b24a988a", "Presión atmosférica", "Cargando...", R.drawable.ic_presion),
                new SensorItem("sensor.nodered_401cd91308d88e24", "Temperatura", "Cargando...", R.drawable.ic_temperatura),
                new SensorItem("sensor.nodered_bc5a1c3b0f79325b", "Humedad", "Cargando...", R.drawable.ic_humedadtierra),
                new SensorItem("sensor.nodered_7542101674aa9aa0", "Luminosidad", "Cargando...", R.drawable.ic_luminosidad),
                new SensorItem("sensor.sensor_temperatura_invernadero", "Temp Tierra", "Cargando...", R.drawable.ic_temptierra),
                new SensorItem("sensor.sensor_humedad_invernadero", "Humedad Tierra", "Cargando...", R.drawable.ic_humedadtierra),
                new SensorItem("sensor.nodered_85fc6704af4125bc", "Temperatura Clase", "Cargando...", R.drawable.ic_clasetemp),
                new SensorItem("sensor.nodered_b9433cda6efee4ba", "Humedad Clase", "Cargando...", R.drawable.ic_humedad)
        );
    }

    public void getSensor(String entityId, TextView textViewTitulo, TextView textViewSensor, String titulo) {
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

                runOnUiThread(() -> {
                    textViewTitulo.setText(titulo);
                    textViewSensor.setText(state + " " + unit);
                });
            }

            @Override
            public void onError(IOException e, int code) {
                runOnUiThread(() -> {
                    textViewTitulo.setText(titulo);
                    textViewSensor.setText("Error: " + e.getMessage());
                });
            }
        });
    }
}