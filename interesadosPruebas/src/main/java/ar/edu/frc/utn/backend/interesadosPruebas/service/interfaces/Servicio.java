package ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces;

import java.util.List;

public interface Servicio<T, K> {
    void create(T t);
    void update(T t);
    T delete(K id);
    T findById(K id);
    List<T> findAll();
}
