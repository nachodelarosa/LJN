package com.example.ljnfastsafe.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.ljnfastsafe.dao.ClienteDAO;
import com.example.ljnfastsafe.model.Cliente;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClienteViewModel extends ViewModel {

    private final ClienteDAO clienteDAO;
    private final ExecutorService executorService;

    private final MutableLiveData<List<Cliente>> clientes = new MutableLiveData<>();
    private final MutableLiveData<Boolean> registroExitoso = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando = new MutableLiveData<>();

    public ClienteViewModel() {
        this.clienteDAO = new ClienteDAO();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Cliente>> getClientes() { return clientes; }
    public LiveData<Boolean> getRegistroExitoso() { return registroExitoso; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getCargando() { return cargando; }

    // Paso intermedio para dar de alta un usuario nuevo con filtro de datos
    public void registrarNuevoCliente(Cliente nuevoCliente) {
        // Validación del controlador antes de tocar la BD
        if (nuevoCliente == null) {
            error.setValue("Los campos están vacíos.");
            return;
        }
        if (nuevoCliente.getIdCliente() == null || nuevoCliente.getIdCliente().trim().isEmpty()) {
            error.setValue("El DNI/ID es obligatorio.");
            return;
        }
        if (nuevoCliente.getEmail() == null || !nuevoCliente.getEmail().contains("@")) {
            error.setValue("Formato de correo electrónico inválido.");
            return;
        }

        cargando.setValue(true);
        executorService.execute(() -> {
            try {
                boolean insertado = clienteDAO.insertar(nuevoCliente);
                if (insertado) {
                    registroExitoso.postValue(true);
                } else {
                    error.postValue("No se pudo registrar. Comprueba si el ID ya existe.");
                }
            } catch (Exception e) {
                error.postValue("Fallo en el servidor al guardar el usuario.");
            } finally {
                cargando.postValue(false);
            }
        });
    }
}
