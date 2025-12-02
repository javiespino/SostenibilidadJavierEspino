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

public class EstacionSuarez extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<SensorItem> sensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estacion_suarez);

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
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_solar_radiation", "Radiación Solar", "Cargando...", R.drawable.ic_luminosidad),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_feels_like_temperature", "Temperatura Actual", "Cargando...", R.drawable.ic_temperatura),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_humidity", "Humedad Relativa", "Cargando...", R.drawable.ic_humedad),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_absolute_pressure", "Presión Atmosférica", "Cargando...", R.drawable.ic_presion),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_wind_speed", "Velocidad del viento", "Cargando...", R.drawable.ic_viento),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_wind_direction", "Dirección del viento", "Cargando...", R.drawable.ic_brujula),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_hourly_rain_rate", "Precipitación actual", "Cargando...", R.drawable.ic_lluvia),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_daily_rain_rate", "Precipitación diaria", "Cargando...", R.drawable.ic_lluvia),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_yearly_rain_rate", "Precipitación anual", "Cargando...", R.drawable.ic_lluvia)
        );
    }

    public void getSensor(String entityId, TextView textViewTitulo, TextView textViewSensor, String titulo) {
        HomeAssistantApiEstacion.getSensorState(entityId, new HomeAssistantApiEstacion.SensorCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                String state = json.optString("state", "?");
                String unit = "";

                JSONObject attrs = json.optJSONObject("attributes");
                if (attrs != null) {
                    unit = attrs.optString("unit_of_measurement", "");
                }

                if (entityId.contains("wind_direction")) {
                    try {
                        double grados = Double.parseDouble(state);
                        state = gradosADireccion(grados);
                        unit = "";
                    } catch (Exception ignored) {}
                }

                final String finalState = state;
                final String finalUnit = unit;

                runOnUiThread(() -> {
                    textViewTitulo.setText(titulo);
                    textViewSensor.setText(finalState + " " + finalUnit);
                });
            }

            @Override
            public void onError(IOException e, int code) {
                runOnUiThread(() -> {
                    textViewTitulo.setText(titulo);
                    textViewSensor.setText(getString(R.string.error));
                });
            }
        });
    }

    private String gradosADireccion(double grados) {
        String[] direcciones = {"N", "NE", "E", "SE", "S", "SO", "O", "NO"};
        int index = (int) ((grados + 22.5) / 45) % 8;
        return direcciones[index];
    }
}
