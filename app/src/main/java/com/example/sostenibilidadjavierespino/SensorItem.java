package com.example.sostenibilidadjavierespino;

public class SensorItem {
    public String entityId;
    public String titulo;
    public String sensor;
    public int iconRes;

    public SensorItem(String entityId, String titulo, String sensor, int iconRes) {
        this.entityId = entityId;
        this.titulo = titulo;
        this.sensor = sensor;
        this.iconRes = iconRes;
    }
}