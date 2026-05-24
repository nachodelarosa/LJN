package com.example.ljnfastsafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ljnfastsafe.dao.CocheDAO;
import com.example.ljnfastsafe.dao.ReservaDAO;
import com.example.ljnfastsafe.model.Coche;
import com.example.ljnfastsafe.model.Reserva;
import com.example.ljnfastsafe.utils.SessionManager;

import java.util.Locale;
import java.util.UUID;

public class DetalleVehiculoActivity extends AppCompatActivity {

    private CocheDAO cocheDAO;
    private ReservaDAO reservaDAO;
    private SessionManager session;
    private ImageView imgVehiculo, logoApp;
    private TextView txtNombre, txtPrecio, txtAnio, txtKm, txtCombustible, txtTransmision, txtColor;
    private Button btnReservar, btnEliminarReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_vehiculo);

        cocheDAO = new CocheDAO();
        reservaDAO = new ReservaDAO();
        session = new SessionManager(this);

        inicializarVistas();

        String idCoche = getIntent().getStringExtra("ID_COCHE");
        if (idCoche != null) {
            cargarDatosVehiculo(idCoche);
        }

        logoApp.setOnClickListener(v -> {
            Intent intent = new Intent(DetalleVehiculoActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
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
        btnEliminarReserva = findViewById(R.id.btnEliminarReserva);
    }

    private void cargarDatosVehiculo(String id) {
        new Thread(() -> {
            try {
                Coche c = cocheDAO.obtenerPorId(id);
                Reserva r = reservaDAO.obtenerReservaPorCoche(id);
                
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

                        // Lógica de Reserva
                        configurarBotonesReserva(c, r);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void configurarBotonesReserva(Coche c, Reserva r) {
        if ("Reservado".equalsIgnoreCase(c.getEstado())) {
            btnReservar.setEnabled(false);
            btnReservar.setText("VEHÍCULO RESERVADO");
            btnReservar.setAlpha(0.6f);

            // Verificar si la reserva es del usuario actual
            if (r != null && r.getIdCliente().equals(session.getUserId())) {
                btnEliminarReserva.setVisibility(View.VISIBLE);
                btnEliminarReserva.setOnClickListener(v -> cancelarReserva(c));
            } else {
                btnEliminarReserva.setVisibility(View.GONE);
            }
        } else {
            btnReservar.setEnabled(true);
            btnReservar.setText("RESERVAR VEHÍCULO");
            btnReservar.setAlpha(1.0f);
            btnReservar.setOnClickListener(v -> reservarVehiculo(c));
            btnEliminarReserva.setVisibility(View.GONE);
        }
    }

    private void cancelarReserva(Coche c) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Cancelar Reserva")
                .setMessage("¿Deseas cancelar tu reserva de este " + c.getMarca() + "?")
                .setPositiveButton("Sí, Cancelar", (dialog, which) -> {
                    new Thread(() -> {
                        boolean okReserva = reservaDAO.eliminarReserva(c.getIdCoche());
                        boolean okCoche = cocheDAO.actualizarEstado(c.getIdCoche(), "Disponible");
                        
                        runOnUiThread(() -> {
                            if (okReserva && okCoche) {
                                Toast.makeText(this, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                                cargarDatosVehiculo(c.getIdCoche()); // Recargar
                            }
                        });
                    }).start();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void reservarVehiculo(Coche c) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Confirmar Reserva")
                .setMessage("¿Estás seguro de que deseas reservar este " + c.getMarca() + " " + c.getModelo() + "?")
                .setPositiveButton("Sí, Reservar", (dialog, which) -> {
                    new Thread(() -> {
                        // 1. Crear entrada en tabla RESERVAS
                        Reserva nueva = new Reserva();
                        nueva.setIdReserva(UUID.randomUUID().toString());
                        nueva.setIdCliente(session.getUserId());
                        nueva.setIdCoche(c.getIdCoche());
                        nueva.setEstado("Confirmada");

                        boolean exitoReserva = reservaDAO.crearReserva(nueva);
                        
                        // 2. Actualizar estado en tabla COCHES
                        boolean exitoCoche = cocheDAO.actualizarEstado(c.getIdCoche(), "Reservado");

                        runOnUiThread(() -> {
                            if (exitoReserva && exitoCoche) {
                                Toast.makeText(this, "Reserva realizada con éxito", Toast.LENGTH_LONG).show();
                                cargarDatosVehiculo(c.getIdCoche()); // Recargar UI
                            } else {
                                Toast.makeText(this, "Error al procesar la reserva", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }).start();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void cargarImagen(Coche c) {
        String marca = c.getMarca().toLowerCase().replace(" ", "");
        String modelo = c.getModelo().toLowerCase().replace(" ", "").replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
        
        // 1. Intentar marca + modelo (ej: hyundaitucson)
        int resId = getResources().getIdentifier(marca + modelo, "drawable", getPackageName());
        
        // 2. Intentar solo modelo (ej: corolla)
        if (resId == 0) {
            resId = getResources().getIdentifier(modelo, "drawable", getPackageName());
        }
        
        // 3. Intentar solo marca (ej: kia)
        if (resId == 0) {
            resId = getResources().getIdentifier(marca, "drawable", getPackageName());
        }

        // 4. Intentar marca + _ + modelo (ej: toyota_corolla)
        if (resId == 0) {
            resId = getResources().getIdentifier(marca + "_" + modelo, "drawable", getPackageName());
        }

        if (resId != 0) {
            imgVehiculo.setImageResource(resId);
        } else {
            // Si no hay imagen, poner el logo o una por defecto
            imgVehiculo.setImageResource(R.drawable.ljnfastsafe_logo);
        }
    }
}