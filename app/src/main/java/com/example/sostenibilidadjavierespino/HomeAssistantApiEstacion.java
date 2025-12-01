package com.example.sostenibilidadjavierespino;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeAssistantApiEstacion {

    private static final OkHttpClient client = new OkHttpClient();

    private static final String BASE_URL = "http://suarezdefigueroa.es:8000";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI3ZTEzNjk2NzI2MTk0MTI0YmQ5MmM2MzFhNzc2YmMxZCIsImlhdCI6MTc2NDAwNzk4MiwiZXhwIjoyMDc5MzY3OTgyfQ.u4p9SWdXQbFpk7OibFm6pBO2stX9FGfiJmcPqXUO_a8";

    public interface SensorCallback {
        void onSuccess(JSONObject json);
        void onError(IOException e, int code);
    }

    public static void getSensorState(String entityId, SensorCallback cb) {
        String url = BASE_URL + "/api/states/" + entityId;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + TOKEN)
                .addHeader("Accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                cb.onError(e, -1);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try (ResponseBody body = response.body()) {

                    if (!response.isSuccessful() || body == null) {
                        cb.onError(new IOException("HTTP " + response.code()), response.code());
                        return;
                    }

                    String jsonStr = body.string();
                    JSONObject json = new JSONObject(jsonStr);
                    cb.onSuccess(json);

                } catch (Exception ex) {
                    cb.onError(new IOException(ex), response.code());
                }
            }
        });
    }
}
