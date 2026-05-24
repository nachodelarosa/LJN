package com.example.ljnfastsafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ljnfastsafe.utils.SessionManager;

public class PerfilActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        session = new SessionManager(this);

        ImageView logoApp = findViewById(R.id.logoApp);
        TextView txtEmail = findViewById(R.id.txtPerfilEmail);
        TextView txtPassword = findViewById(R.id.txtPerfilPassword);
        Button btnLogout = findViewById(R.id.btnLogout);

        txtEmail.setText(session.getUserEmail());
        txtPassword.setText(session.getUserPassword());

        logoApp.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            session.logoutUser();
            Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
