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
    private TextView txtNombre, txtPrecio, txtAnio, txtKm, txtCombustible, txtTransmision, txtColor;
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
    }

    private void inicializarVistas() {
        logoApp = findViewById(R.id.logoApp);
        imgVehiculo = findViewById(R.id.imgVehiculo);
        txtNombre = findViewById(R.id.txtNombre);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtAnio = findViewById(R.id.txtAnioDetalle);
        txtKm = findViewById(R.id.txtKmDetalle);
        txtCombustible = findViewById(R.id.txtCombustibleDetalle);
        txtTransmision = findViewById(R.id.txtTransmisionDetalle);
        txtColor = findViewById(R.id.txtColorDetalle);
        btnReservar = findViewById(R.id.btnReservar);
        btnContactar = findViewById(R.id.btnContactar);
    }

    private void cargarDatosVehiculo(String id) {
        new Thread(() -> {
            try {
                Coche c = cocheDAO.obtenerPorId(id);
                runOnUiThread(() -> {
                    if (c != null) {
                        txtNombre.setText(c.getMarca() + " " + c.getModelo());
                        txtPrecio.setText(String.format(Locale.getDefault(), "$%.2f", c.getPrecio()));
                        txtAnio.setText(String.valueOf(c.getAnio()));
                        txtKm.setText(c.getKilometros() + " km");
                        txtCombustible.setText(c.getCombustible());
                        txtTransmision.setText(c.getTransmision());
                        txtColor.setText(c.getColor());
                        cargarImagen(c);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void cargarImagen(Coche c) {
        String modelo = c.getModelo().toLowerCase().replace(" ", "").replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
        int resId = getResources().getIdentifier(modelo, "drawable", getPackageName());
        if (resId != 0) imgVehiculo.setImageResource(resId);
    }
}