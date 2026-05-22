package com.example.ljnfastsafe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DialogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);

        // 1. Error de validación
        findViewById(R.id.btnError).setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Error de Validación")
                .setMessage("Por favor, completa todos los campos obligatorios.")
                .setPositiveButton("Entendido", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());

        // 2. Operación realizada correctamente
        findViewById(R.id.btnSuccess).setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Operación Exitosa")
                .setMessage("Los datos se han guardado correctamente.")
                .setPositiveButton("Aceptar", null)
                .show());

        // 3. Confirmación antes de eliminar o modificar
        findViewById(R.id.btnConfirm).setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Confirmar Acción")
                .setMessage("¿Estás seguro de que deseas eliminar este registro? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show());

        // 4. Restricciones de acceso
        findViewById(R.id.btnRestricted).setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Acceso Restringido")
                .setMessage("No tienes permisos suficientes para realizar esta acción. Contacta con el administrador.")
                .setPositiveButton("Cerrar", null)
                .setIcon(android.R.drawable.ic_lock_lock)
                .show());

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}