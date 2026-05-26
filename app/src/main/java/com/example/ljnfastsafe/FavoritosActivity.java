package com.example.ljnfastsafe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ljnfastsafe.dao.FavoritoDAO;
import com.example.ljnfastsafe.model.Coche;
import com.example.ljnfastsafe.utils.SessionManager;

import java.util.List;
import java.util.Locale;

public class FavoritosActivity extends AppCompatActivity {

    private FavoritoDAO favoritoDAO;
    private LinearLayout layoutListaFavoritos;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        session = new SessionManager(this);
        favoritoDAO = new FavoritoDAO();
        layoutListaFavoritos = findViewById(R.id.layoutListaFavoritos);
        ImageView logoApp = findViewById(R.id.logoApp);

        logoApp.setOnClickListener(v -> {
            Intent intent = new Intent(FavoritosActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        cargarFavoritos();
    }

    private void cargarFavoritos() {
        new Thread(() -> {
            List<Coche> favoritos = favoritoDAO.obtenerCochesFavoritos(session.getUserId());
            runOnUiThread(() -> {
                layoutListaFavoritos.removeAllViews();
                if (favoritos.isEmpty()) {
                    TextView tv = new TextView(this);
                    tv.setText("Aún no tienes vehículos favoritos");
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    layoutListaFavoritos.addView(tv);
                } else {
                    for (Coche c : favoritos) {
                        agregarTarjetaFavorito(c);
                    }
                }
            });
        }).start();
    }

    private void agregarTarjetaFavorito(Coche c) {
        View cardView = LayoutInflater.from(this).inflate(R.layout.item_coche_favorito, layoutListaFavoritos, false);
        
        ImageView img = cardView.findViewById(R.id.imgCocheFav);
        TextView txtNombre = cardView.findViewById(R.id.txtNombreFav);
        TextView txtPrecio = cardView.findViewById(R.id.txtPrecioFav);
        ImageButton btnRemove = cardView.findViewById(R.id.btnRemoveFav);

        txtNombre.setText(c.getMarca() + " " + c.getModelo());
        txtPrecio.setText(String.format(Locale.getDefault(), "$%.2f", c.getPrecio()));
        
        // Cargar imagen (reutilizando lógica similar a MainActivity)
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
            img.setImageResource(resId);
        } else {
            // Si no hay imagen, poner el logo o una por defecto
            img.setImageResource(R.drawable.ljnfastsafe_logo);
        }

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetalleVehiculoActivity.class);
            intent.putExtra("ID_COCHE", c.getIdCoche());
            startActivity(intent);
        });

        btnRemove.setOnClickListener(v -> {
            new Thread(() -> {
                boolean ok = favoritoDAO.eliminarFavorito(session.getUserId(), c.getIdCoche());
                runOnUiThread(() -> {
                    if (ok) {
                        Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
                        cargarFavoritos();
                    }
                });
            }).start();
        });

        layoutListaFavoritos.addView(cardView);
    }
}
