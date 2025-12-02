package com.example.sostenibilidadjavierespino;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class FotosActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private LinearLayout indicadorLayout;
    private List<Integer> imagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        viewPager = findViewById(R.id.viewPager);
        indicadorLayout = findViewById(R.id.indicadorLayout);

        imagenes = new ArrayList<>();
        imagenes.add(R.drawable.foto1);
        imagenes.add(R.drawable.foto2);
        imagenes.add(R.drawable.foto3);
        imagenes.add(R.drawable.foto4);
        imagenes.add(R.drawable.foto5);
        imagenes.add(R.drawable.foto6);
        imagenes.add(R.drawable.foto7);
        imagenes.add(R.drawable.foto8);

        FotosAdapter adapter = new FotosAdapter(imagenes);
        viewPager.setAdapter(adapter);

        crearIndicadores(imagenes.size());
        actualizarIndicador(0);

        // Listener para cambio de página
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                actualizarIndicador(position);
            }
        });
    }

    private void crearIndicadores(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            View punto = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(8, 0, 8, 0);
            punto.setLayoutParams(params);
            punto.setBackgroundResource(R.drawable.indicador_inactivo);
            indicadorLayout.addView(punto);
        }
    }

    private void actualizarIndicador(int posicion) {
        for (int i = 0; i < indicadorLayout.getChildCount(); i++) {
            View punto = indicadorLayout.getChildAt(i);
            if (i == posicion) {
                punto.setBackgroundResource(R.drawable.indicador_activo);
            } else {
                punto.setBackgroundResource(R.drawable.indicador_inactivo);
            }
        }
    }
}