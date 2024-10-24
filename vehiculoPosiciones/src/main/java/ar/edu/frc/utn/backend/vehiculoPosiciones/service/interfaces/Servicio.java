package ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Servicio<T, K> {

    void create(T t);
    void update(T t);
    T delete(K id);
    boolean existById(Integer id);
    T findById(K id);
    List<T> findAll();

}
