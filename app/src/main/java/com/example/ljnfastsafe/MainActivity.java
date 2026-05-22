package com.example.ljnfastsafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.DetalleVehiculoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ScrollView scrollViewMain = findViewById(R.id.scrollViewMain);
        ImageView logoApp = findViewById(R.id.logoApp);
        logoApp.setOnClickListener(v -> {
            // Al pulsar el logo en la Home, volvemos arriba del todo
            scrollViewMain.fullScroll(ScrollView.FOCUS_UP);
        });

        Button btnVerDetalle = findViewById(R.id.btnVerDetalle);
        btnVerDetalle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetalleVehiculoActivity.class);
            startActivity(intent);
        });

        setupFavoriteButton(R.id.btnFavCoche2);
        setupFavoriteButton(R.id.btnFavCoche3);
        setupFavoriteButton(R.id.btnFavCoche4);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
            //hoolña
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
}