package com.example.ljnfastsafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ljnfastsafe.dao.ClienteDAO;
import com.example.ljnfastsafe.model.Cliente;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etApellido, etEmailReg, etPasswordReg, etConfirmPassword;
    private Button btnRegister;
    private TextView tvBackToLogin;
    private ClienteDAO clienteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        clienteDAO = new ClienteDAO();

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEmailReg = findViewById(R.id.etEmailReg);
        etPasswordReg = findViewById(R.id.etPasswordReg);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        ImageView logoApp = findViewById(R.id.logoApp);
        logoApp.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnRegister.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String email = etEmailReg.getText().toString();
            String pass = etPasswordReg.getText().toString();
            String confirmPass = etConfirmPassword.getText().toString();

            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor, completa los campos obligatorios", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirmPass)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else {
                // Registro en base de datos en hilo secundario
                new Thread(() -> {
                    Cliente nuevo = new Cliente();
                    nuevo.setIdCliente(UUID.randomUUID().toString());
                    nuevo.setNombre(nombre);
                    nuevo.setApellidos(apellido);
                    nuevo.setEmail(email);
                    nuevo.setContrasena(pass);
                    nuevo.setTelefono(""); // Evitar nulos si la DB los requiere
                    nuevo.setDireccion(""); // Evitar nulos si la DB los requiere

                    boolean exito = clienteDAO.insertar(nuevo);

                    runOnUiThread(() -> {
                        if (exito) {
                            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Error al registrar. Verifica la conexión o si el email ya existe.", Toast.LENGTH_LONG).show();
                        }
                    });
                }).start();
            }
        });

        tvBackToLogin.setOnClickListener(v -> finish());
    }
}