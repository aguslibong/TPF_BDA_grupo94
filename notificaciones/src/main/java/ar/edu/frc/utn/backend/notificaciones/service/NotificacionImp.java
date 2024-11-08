package ar.edu.frc.utn.backend.notificaciones.service;

import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionDTO;
import ar.edu.frc.utn.backend.notificaciones.entities.Notificacion;
import ar.edu.frc.utn.backend.notificaciones.repository.NotificacionRepository;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.NotificacionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionImp implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionImp(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public void createAll(Iterable<NotificacionDTO> notificacion) {

    }

    @Override
    public String sendSmsMessage(NotificacionDTO notificacion) {
        create(notificacion);
        return "Se Envio notificacion";
    }

    private Notificacion crearNotificacion(NotificacionDTO notificacionDTO) {
        Notificacion notificacion = new Notificacion();

        notificacion.setMensaje(notificacionDTO.getMensaje());
        notificacion.setTelefono(notificacionDTO.getTelefonoEmpleado());
        notificacion.setLegajoEmpleado(notificacionDTO.getLegajoEmpleado());
        return notificacion;
    }

    @Override
    public void create(NotificacionDTO notificacionDTO) {
        notificacionRepository.save(crearNotificacion(notificacionDTO));
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
