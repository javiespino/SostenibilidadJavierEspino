package com.example.sostenibilidadjavierespino;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EstacionArroyo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<SensorItem> sensores;
    private OkHttpClient client = new OkHttpClient();

    private final String URL = "https://api.weather.com/v2/pws/observations/current?stationId=IALMEN70&format=json&units=m&apiKey=908477f6f2b84c6c8477f6f2b80c6c03";

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

    private void cargarSensores() {
        sensores = List.of(
                new SensorItem("solarRadiation", "Radiación Solar", "Cargando...", R.drawable.ic_luminosidad),
                new SensorItem("temp", "Temperatura", "Cargando...", R.drawable.ic_temperatura),
                new SensorItem("humidity", "Humedad", "Cargando...", R.drawable.ic_humedad),
                new SensorItem("pressure", "Presión", "Cargando...", R.drawable.ic_presion),
                new SensorItem("windSpeed", "Velocidad del viento", "Cargando...", R.drawable.ic_viento),
                new SensorItem("wind_dir", "Dirección del viento", "Cargando...", R.drawable.ic_brujula),
                new SensorItem("precipRate", "Precipitación actual", "Cargando...", R.drawable.ic_lluvia),
                new SensorItem("precipTotal", "Precipitación total", "Cargando...", R.drawable.ic_lluvia)
        );
    }

    public void getSensor(String entityId, TextView textViewTitulo, TextView textViewSensor, String titulo) {
        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> textViewSensor.setText("Error: " + e.getMessage()));
                Log.e("API", "Error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    JSONObject obj = new JSONObject(json);
                    JSONArray obsArray = obj.getJSONArray("observations");
                    JSONObject obs = obsArray.getJSONObject(0);
                    JSONObject metric = obs.getJSONObject("metric");

                    String valor = "?";

                    switch (entityId) {
                        case "solarRadiation":
                            valor = obs.optDouble("solarRadiation", 0) + " W/m²";
                            break;
                        case "temp":
                            valor = metric.optDouble("temp", 0) + " °C";
                            break;
                        case "humidity":
                            valor = obs.optDouble("humidity", 0) + " %";
                            break;
                        case "pressure":
                            valor = metric.optDouble("pressure", 0) + " hPa";
                            break;
                        case "windSpeed":
                            valor = metric.optDouble("windSpeed", 0) + " m/s";
                            break;
                        case "wind_dir":
                            double grados = obs.optDouble("winddir", 0);
                            valor = grados + "° " + gradosADireccion(grados);
                            break;
                        case "precipRate":
                            valor = metric.optDouble("precipRate", 0) + " mm/h";
                            break;
                        case "precipTotal":
                            valor = metric.optDouble("precipTotal", 0) + " mm";
                            break;
                    }

                    String finalValor = valor;
                    runOnUiThread(() -> textViewSensor.setText(finalValor));
                } catch (Exception e) {
                    runOnUiThread(() -> textViewSensor.setText("Error: " + e.getMessage()));
                    Log.e("API", "Parsing error: " + e.getMessage());
                }
            }
        });

        runOnUiThread(() -> textViewTitulo.setText(titulo));
    }

    private String gradosADireccion(double grados) {
        String[] direcciones = {"N", "NE", "E", "SE", "S", "SO", "O", "NO"};
        int index = (int) ((grados + 22.5) / 45) % 8;
        return direcciones[index];
    }
}
