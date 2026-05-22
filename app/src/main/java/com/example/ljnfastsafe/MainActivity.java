package com.example.ljnfastsafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
            // Al pulsar el logo en la Home, volvemos arriba
            scrollViewMain.fullScroll(ScrollView.FOCUS_UP);
        });

        Button btnVerDetalle = findViewById(R.id.btnVerDetalle);
        btnVerDetalle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetalleVehiculoActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnIrDialogos).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DialogsActivity.class);
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
        btn.setTag(false); // false = not favorite
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
            List<Coche> coches = cocheDAO.obtenerCochesDisponibles();
            runOnUiThread(() -> {
                if (!coches.isEmpty()) {
                    // Coche 2
                    actualizarTarjeta(R.id.cardCoche2, coches.get(0));

                    // Coche 3
                    if (coches.size() >= 2) {
                        actualizarTarjeta(R.id.cardCoche3, coches.get(1));
                    }
                    // Coche 4
                    if (coches.size() >= 3) {
                        actualizarTarjeta(R.id.cardCoche4, coches.get(2));
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_coches_encontrados, Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void actualizarTarjeta(int cardId, Coche c) {
        View card = findViewById(cardId);
        LinearLayout layoutHorizontal = (LinearLayout) ((CardView) card).getChildAt(0);
        LinearLayout layoutVertical = (LinearLayout) layoutHorizontal.getChildAt(1);
        
        TextView txtNombre = (TextView) layoutVertical.getChildAt(0);
        TextView txtPrecio = (TextView) layoutVertical.getChildAt(1);
        TextView txtEstado = (TextView) layoutVertical.getChildAt(2);
        
        String nombreCompleto = c.getMarca() + " " + c.getModelo();
        txtNombre.setText(nombreCompleto);
        txtPrecio.setText(String.format(Locale.getDefault(), "$%.2f", c.getPrecio()));
        txtEstado.setText(c.getEstado());
    }
}