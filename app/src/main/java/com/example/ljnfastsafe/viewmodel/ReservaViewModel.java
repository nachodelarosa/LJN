package com.example.ljnfastsafe.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.ljnfastsafe.dao.ReservaDAO;
import com.example.ljnfastsafe.model.Reserva;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReservaViewModel extends ViewModel {

    private final ReservaDAO reservaDAO;
    private final ExecutorService executorService;

    private final MutableLiveData<List<Reserva>> historialReservas = new MutableLiveData<>();
    private final MutableLiveData<Boolean> reservaCompletada = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando = new MutableLiveData<>();

    public ReservaViewModel() {
        this.reservaDAO = new ReservaDAO();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Reserva>> getHistorialReservas() { return historialReservas; }
    public LiveData<Boolean> getReservaCompletada() { return reservaCompletada; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getCargando() { return cargando; }

    // Paso intermedio para procesar la transacción
    public void procesarReserva(String idCliente, String idCoche, java.util.Date inicio, java.util.Date fin) {
        // Validaciones lógicas del Negocio
        if (idCliente == null || idCoche == null) {
            error.setValue("Error interno: Datos de usuario o coche nulos.");
            return;
        }
        if (inicio == null || fin == null) {
            error.setValue("Por favor, selecciona las fechas del periodo.");
            return;
        }
        if (fin.before(inicio)) {
            error.setValue("La fecha de devolución no puede ser previa a la de recogida.");
            return;
        }

        cargando.setValue(true);

        // El controlador genera de manera automatizada los datos de control
        Reserva reserva = new Reserva();
        reserva.setIdReserva("RES-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase());
        reserva.setIdCliente(idCliente);
        reserva.setIdCoche(idCoche);
        reserva.setFechaInicio(inicio);
        reserva.setFechaFin(fin);
        reserva.setEstado("CONFIRMADA");

        executorService.execute(() -> {
            try {
                boolean exito = reservaDAO.crearReserva(reserva);
                if (exito) {
                    reservaCompletada.postValue(true);
                } else {
                    error.postValue("El coche no se encuentra disponible para reserva.");
                }
            } catch (Exception e) {
                error.postValue("Error en el sistema de reservas: " + e.getMessage());
            } finally {
                cargando.postValue(false);
            }
        });
    }

    // Paso intermedio para ver el panel de "Mis Reservas"
    public void consultarReservasCliente(String idCliente) {
        if (idCliente == null) return;

        cargando.setValue(true);
        executorService.execute(() -> {
            try {
                List<Reserva> lista = reservaDAO.obtenerPorCliente(idCliente);
                historialReservas.postValue(lista);
            } catch (Exception e) {
                error.postValue("No se pudo descargar tu historial.");
            } finally {
                cargando.postValue(false);
            }
        });
    }
}
