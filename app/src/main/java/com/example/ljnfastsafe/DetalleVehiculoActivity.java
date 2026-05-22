package com.example.ljnfastsafe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ljnfastsafe.dao.CocheDAO;
import com.example.ljnfastsafe.model.Coche;

import java.util.Locale;

public class DetalleVehiculoActivity extends AppCompatActivity {

    private CocheDAO cocheDAO;
    private ImageView imgVehiculo, logoApp;
    private TextView txtNombre, txtPrecio;
    private Button btnReservar, btnContactar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_vehiculo);

        cocheDAO = new CocheDAO();
        inicializarVistas();

        String idCoche = getIntent().getStringExtra("ID_COCHE");
        if (idCoche != null) {
            cargarDatosVehiculo(idCoche);
        }

        logoApp.setOnClickListener(v -> finish());

        btnReservar.setOnClickListener(v ->
                Toast.makeText(this, "Reserva solicitada para el vehículo", Toast.LENGTH_SHORT).show());

        btnContactar.setOnClickListener(v ->
                Toast.makeText(this, "Contactando con el asesor de ventas...", Toast.LENGTH_SHORT).show());
    }

    private void inicializarVistas() {
        logoApp = findViewById(R.id.logoApp);
        imgVehiculo = findViewById(R.id.imgVehiculo);
        txtNombre = findViewById(R.id.txtNombre);
        txtPrecio = findViewById(R.id.txtPrecio);
        btnReservar = findViewById(R.id.btnReservar);
        btnContactar = findViewById(R.id.btnContactar);
    }

    private void cargarDatosVehiculo(String id) {
        new Thread(() -> {
            Coche c = cocheDAO.obtenerPorId(id);
            if (c != null) {
                runOnUiThread(() -> {
                    String nombreCompleto = c.getMarca() + " " + c.getModelo();
                    txtNombre.setText(nombreCompleto);
                    txtPrecio.setText(String.format(Locale.getDefault(), "$%.2f", c.getPrecio()));
                    cargarImagen(c);
                    actualizarEspecificaciones(c);
                });
            }
        }).start();
    }

    private void actualizarEspecificaciones(Coche c) {
        android.widget.GridLayout grid = findViewById(R.id.main_grid_specs);
        if (grid != null && grid.getChildCount() >= 10) {
            ((TextView) grid.getChildAt(1)).setText(String.valueOf(c.getAnio()));
            String kmText = c.getKilometros() + " km";
            ((TextView) grid.getChildAt(3)).setText(kmText);
            ((TextView) grid.getChildAt(5)).setText(c.getCombustible());
            ((TextView) grid.getChildAt(7)).setText(c.getTransmision());
            ((TextView) grid.getChildAt(9)).setText(c.getColor());
        }
    }

    private void cargarImagen(Coche c) {
        String modelo = c.getModelo().toLowerCase().replace(" ", "").replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
        String marca = c.getMarca().toLowerCase();

        int resId = getResources().getIdentifier(modelo, "drawable", getPackageName());
        if (resId == 0) {
            resId = getResources().getIdentifier(marca, "drawable", getPackageName());
        }

        if (resId != 0) {
            imgVehiculo.setImageResource(resId);
        }
    }
}