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

import com.example.DetalleVehiculoActivity;
import com.example.ljnfastsafe.dao.VehiculoDAO;
import com.example.ljnfastsafe.model.Vehiculo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VehiculoDAO vehiculoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        vehiculoDAO = new VehiculoDAO();
        cargarVehiculosDesdeDB();

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

    private void cargarVehiculosDesdeDB() {
        new Thread(() -> {
            List<Vehiculo> vehiculos = vehiculoDAO.obtenerTodos();
            runOnUiThread(() -> {
                if (!vehiculos.isEmpty()) {
                    // Por simplicidad, mapeamos los primeros 3 a las tarjetas existentes
                    // Coche 2
                    actualizarTarjeta(R.id.cardCoche2, vehiculos.get(0));

                    // Coche 3
                    if (vehiculos.size() >= 2) {
                        actualizarTarjeta(R.id.cardCoche3, vehiculos.get(1));
                    }
                    // Coche 4
                    if (vehiculos.size() >= 3) {
                        actualizarTarjeta(R.id.cardCoche4, vehiculos.get(2));
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No se encontraron vehículos en la DB", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void actualizarTarjeta(int cardId, Vehiculo v) {
        View card = findViewById(cardId);
        // El layout es CardView -> LinearLayout -> [ImageView, LinearLayout(v) -> [TextView, TextView, TextView]]
        LinearLayout layoutHorizontal = (LinearLayout) ((CardView) card).getChildAt(0);
        LinearLayout layoutVertical = (LinearLayout) layoutHorizontal.getChildAt(1);
        
        TextView txtNombre = (TextView) layoutVertical.getChildAt(0);
        TextView txtPrecio = (TextView) layoutVertical.getChildAt(1);
        TextView txtEstado = (TextView) layoutVertical.getChildAt(2);
        
        txtNombre.setText(v.getNombre());
        txtPrecio.setText(v.getPrecio());
        txtEstado.setText(v.getEstado());
        
        // La imagen requeriría lógica para cargar desde drawable por nombre o URL
    }
}