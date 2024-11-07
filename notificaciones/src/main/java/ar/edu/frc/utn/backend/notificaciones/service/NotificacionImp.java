package ar.edu.frc.utn.backend.notificaciones.service;

import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionDTO;
import ar.edu.frc.utn.backend.notificaciones.entities.Notificacion;
import ar.edu.frc.utn.backend.notificaciones.repository.NotificacionRepository;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionImp implements NotificacionService {
    @Override
    public void createAll(Iterable<NotificacionDTO> notificacion) {

    }

    @Override
    public String sendSmsMessage(NotificacionDTO notificacion) {
        return "";
    }

    @Override
    public void create(NotificacionDTO notificacionDTO) {

    }

    @Override
    public void update(NotificacionDTO notificacionDTO) {

    }

    @Override
    public NotificacionDTO delete(Integer id) {
        return null;
    }

    @Override
    public boolean existById(Integer id) {
        return false;
    }

    @Override
    public NotificacionDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<NotificacionDTO> findAll() {
        return List.of();
    }
}
