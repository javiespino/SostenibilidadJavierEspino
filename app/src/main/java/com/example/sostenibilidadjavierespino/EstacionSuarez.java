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
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_solar_radiation", getString(R.string.sensor_radiacion_solar), getString(R.string.cargando), R.drawable.ic_luminosidad),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_feels_like_temperature", getString(R.string.sensor_temperatura_actual), getString(R.string.cargando), R.drawable.ic_temperatura),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_humidity", getString(R.string.sensor_humedad_relativa), getString(R.string.cargando), R.drawable.ic_humedad),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_absolute_pressure", getString(R.string.sensor_presion_atmosferica), getString(R.string.cargando), R.drawable.ic_presion),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_wind_speed", getString(R.string.sensor_velocidad_viento), getString(R.string.cargando), R.drawable.ic_viento),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_wind_direction", getString(R.string.sensor_direccion_viento), getString(R.string.cargando), R.drawable.ic_brujula),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_hourly_rain_rate", getString(R.string.sensor_precipitacion_actual), getString(R.string.cargando), R.drawable.ic_lluvia),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_daily_rain_rate", getString(R.string.sensor_precipitacion_diaria), getString(R.string.cargando), R.drawable.ic_lluvia),
                new SensorItem("sensor.hp1000se_pro_pro_v1_9_0_yearly_rain_rate", getString(R.string.sensor_precipitacion_anual), getString(R.string.cargando), R.drawable.ic_lluvia)
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
                        String direccion = gradosADireccion(grados);
                        state = grados + "º " + direccion;
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
        String[] direcciones = {
                getString(R.string.direccion_n), getString(R.string.direccion_ne), getString(R.string.direccion_e), getString(R.string.direccion_se),
                getString(R.string.direccion_s), getString(R.string.direccion_so), getString(R.string.direccion_o), getString(R.string.direccion_no)
        };

        int index = (int) ((grados + 22.5) / 45) % 8;
        return direcciones[index];
    }

}
