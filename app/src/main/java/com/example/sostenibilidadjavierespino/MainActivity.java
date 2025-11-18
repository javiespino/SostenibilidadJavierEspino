package com.example.sostenibilidadjavierespino;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.menu_navegacion);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.ies1) {
                startActivity(new Intent(this, ArroyoHarnina.class));
                return true;
            } else if (id == R.id.ies2) {
                startActivity(new Intent(this, SuarezFigueroa.class));
                return true;
            }
            return false;
        });
    }
}
