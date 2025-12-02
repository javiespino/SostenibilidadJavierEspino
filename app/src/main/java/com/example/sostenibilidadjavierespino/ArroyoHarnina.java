package com.example.sostenibilidadjavierespino;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ArroyoHarnina extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arroyo_harnina);

        ImageButton buttonInvernadero = findViewById(R.id.imageButtonInvernadero);
        ImageButton buttonEstacion = findViewById(R.id.imageButtonEstacion);

        buttonInvernadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArroyoHarnina.this, InvernaderoArroyo.class);
                startActivity(intent);
            }
        });

        buttonEstacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArroyoHarnina.this, EstacionArroyo.class);
                startActivity(intent);
            }
        });

        BottomNavigationView navAtras = findViewById(R.id.menu_info_atras);
        navAtras.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_volver) {
                    finish();
                    return true;

                } else if (item.getItemId() == R.id.item_info) {
                    mostrarDialogoCentroInfo();
                    return true;

                } else if (item.getItemId() == R.id.item_web) {
                    new AlertDialog.Builder(ArroyoHarnina.this)
                            .setTitle(getString(R.string.aviso_titulo))
                            .setMessage(getString(R.string.aviso_mensaje))
                            .setPositiveButton(getString(R.string.boton_si), (dialog, which) -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://iesarroyoharnina.educarex.es/"));
                                startActivity(intent);
                            })
                            .setNegativeButton(getString(R.string.boton_no), null)
                            .show();

                    return true;
                }

                return false;
            }

        });
    }

    private void mostrarDialogoCentroInfo() {

        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.info_arroyo_harnina, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.info_centro)

                .setView(dialogView)
                .setPositiveButton(R.string.volver, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
