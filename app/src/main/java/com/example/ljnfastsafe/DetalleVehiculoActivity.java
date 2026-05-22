package com.example.ljnfastsafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleVehiculoActivity extends AppCompatActivity {

    Button btnReservar, btnContactar;
    ImageView logoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_vehiculo);

        logoApp = findViewById(R.id.logoApp);
        logoApp.setOnClickListener(v -> {
            // Volver al inicio (MainActivity)
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        btnReservar = findViewById(R.id.btnReservar);
        btnContactar = findViewById(R.id.btnContactar);

        btnReservar.setOnClickListener(v ->
                Toast.makeText(this,
                        "Vehículo reservado",
                        Toast.LENGTH_SHORT).show());

        btnContactar.setOnClickListener(v ->
                Toast.makeText(this,
                        "Contactando vendedor",
                        Toast.LENGTH_SHORT).show());
    }
}