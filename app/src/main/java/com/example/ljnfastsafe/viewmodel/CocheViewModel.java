package com.example.ljnfastsafe.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.ljnfastsafe.dao.CocheDAO;
import com.example.ljnfastsafe.model.Coche;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CocheViewModel extends ViewModel {

    private final CocheDAO cocheDAO;
    private final ExecutorService executorService;

    // Canales de comunicación hacia la Vista (UI)
    private final MutableLiveData<List<Coche>> cochesDisponibles = new MutableLiveData<>();
    private final MutableLiveData<Coche> cocheDetalle = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando = new MutableLiveData<>();

    public CocheViewModel() {
        this.cocheDAO = new CocheDAO();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Coche>> getCochesDisponibles() { return cochesDisponibles; }
    public LiveData<Coche> getCocheDetalle() { return cocheDetalle; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getCargando() { return cargando; }

    // Paso intermedio para traer el catálogo completo
    public void cargarCoches() {
        cargando.setValue(true);
        executorService.execute(() -> {
            try {
                List<Coche> lista = cocheDAO.obtenerCochesDisponibles();
                if (lista != null && !lista.isEmpty()) {
                    cochesDisponibles.postValue(lista);
                } else {
                    error.postValue("No hay coches disponibles en la tienda.");
                }
            } catch (Exception e) {
                error.postValue("Error de conexión al catálogo: " + e.getMessage());
            } finally {
                cargando.postValue(false);
            }
        });
    }

    // Paso intermedio para abrir la ficha de un coche concreto
    public void cargarDetalleCoche(String idCoche) {
        if (idCoche == null || idCoche.trim().isEmpty()) {
            error.setValue("El coche seleccionado no existe.");
            return;
        }

        cargando.setValue(true);
        executorService.execute(() -> {
            try {
                Coche coche = cocheDAO.obtenerPorId(idCoche);
                if (coche != null) {
                    cocheDetalle.postValue(coche);
                } else {
                    error.postValue("Detalles del coche no encontrados.");
                }
            } catch (Exception e) {
                error.postValue("Error al cargar los datos del vehículo.");
            } finally {
                cargando.postValue(false);
            }
        });
    }
}