package ar.edu.frc.utn.backend.notificaciones.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract interface Servicio<T, K> {

    void create(T t);
    void update(T t);
    T delete(K id);
    boolean existById(Integer id);
    T findById(K id);
    List<T> findAll();

}
