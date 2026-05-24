package com.example.ljnfastsafe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ljnfastsafe.dao.CocheDAO;
import com.example.ljnfastsafe.model.Coche;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CocheDAO cocheDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cocheDAO = new CocheDAO();
        cargarCochesDesdeDB();

        ScrollView scrollViewMain = findViewById(R.id.scrollViewMain);
        ImageView logoApp = findViewById(R.id.logoApp);
        logoApp.setOnClickListener(v -> {
            // Al pulsar el logo, refrescamos los datos además de subir
            scrollViewMain.fullScroll(ScrollView.FOCUS_UP);
            cargarCochesDesdeDB();
            Toast.makeText(this, "Refrescando datos...", Toast.LENGTH_SHORT).show();
        });

        ImageButton btnLoginIcon = findViewById(R.id.btnLogin);
        btnLoginIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        setupFavoriteButton(R.id.btnFavCoche2);
        setupFavoriteButton(R.id.btnFavCoche3);
        setupFavoriteButton(R.id.btnFavCoche4);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupFavoriteButton(int resId) {
        ImageButton btn = findViewById(resId);
        btn.setTag(false);
        btn.setOnClickListener(v -> {
            boolean isFavorite = (boolean) v.getTag();
            if (isFavorite) {
                btn.setImageResource(R.drawable.ic_heart);
                v.setTag(false);
            } else {
                btn.setImageResource(R.drawable.ic_heart_filled);
                v.setTag(true);
            }
        });
    }

    private void cargarCochesDesdeDB() {
        new Thread(() -> {
            try {
                List<Coche> coches = cocheDAO.obtenerCochesDisponibles();
                runOnUiThread(() -> {
                    if (coches != null && !coches.isEmpty()) {
                        Log.d("MainActivity", "Datos recibidos de la DB. Actualizando UI...");
                        
                        // Oferta Mensual (Cualquier coche, por ejemplo el primero)
                        actualizarOferta(coches.get(0));

                        // Otros Vehículos (Mapeo dinámico por posición)
                        if (coches.size() >= 2) actualizarTarjeta(R.id.cardCoche2, coches.get(1));
                        if (coches.size() >= 3) actualizarTarjeta(R.id.cardCoche3, coches.get(2));
                        if (coches.size() >= 4) actualizarTarjeta(R.id.cardCoche4, coches.get(3));
                        
                    } else {
                        Log.w("MainActivity", "No se recibieron coches de la DB");
                        Toast.makeText(MainActivity.this, "No se pudieron cargar datos de MySQL", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                Log.e("MainActivity", "Error en hilo de carga: " + e.getMessage());
            }
        }).start();
    }

    private void actualizarOferta(Coche oferta) {
        Button btn = findViewById(R.id.btnVerDetalle);
        btn.setOnClickListener(v -> abrirDetalle(oferta.getIdCoche()));
        
        ImageView imgOferta = findViewById(R.id.imgOferta);
        cargarImagenVehiculo(imgOferta, oferta);
    }

    private void abrirDetalle(String idCoche) {
        Intent intent = new Intent(MainActivity.this, DetalleVehiculoActivity.class);
        intent.putExtra("ID_COCHE", idCoche);
        startActivity(intent);
    }

    private void actualizarTarjeta(int cardId, Coche c) {
        CardView card = findViewById(cardId);
        if (card == null) return;

        card.setOnClickListener(v -> abrirDetalle(c.getIdCoche()));

        LinearLayout layoutHorizontal = (LinearLayout) card.getChildAt(0);
        ImageView imgVehiculo = (ImageView) layoutHorizontal.getChildAt(0);
        LinearLayout layoutVertical = (LinearLayout) layoutHorizontal.getChildAt(1);
        
        TextView txtNombre = (TextView) layoutVertical.getChildAt(0);
        TextView txtPrecio = (TextView) layoutVertical.getChildAt(1);
        TextView txtEstado = (TextView) layoutVertical.getChildAt(2);
        
        txtNombre.setText(c.getMarca() + " " + c.getModelo());
        txtPrecio.setText(String.format(Locale.getDefault(), "$%.2f", c.getPrecio()));
        txtEstado.setText(c.getEstado());

        cargarImagenVehiculo(imgVehiculo, c);
    }

    private void cargarImagenVehiculo(ImageView img, Coche c) {
        String modelo = c.getModelo().toLowerCase().replace(" ", "").replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
        String marca = c.getMarca().toLowerCase();

        int resId = getResources().getIdentifier(modelo, "drawable", getPackageName());
        if (resId == 0) {
            resId = getResources().getIdentifier(marca, "drawable", getPackageName());
        }

        if (resId != 0) {
            img.setImageResource(resId);
        }
    }
}