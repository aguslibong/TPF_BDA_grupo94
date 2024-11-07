package ar.edu.frc.utn.backend.vehiculoPosiciones.service;

import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.NotificacionDTO;
import ar.edu.frc.utn.backend.vehiculoPosiciones.entities.Notificacion;
import ar.edu.frc.utn.backend.vehiculoPosiciones.repository.NotificacionRepository;
import ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces.NotificacionService;
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
        return "";
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
        notificacionRepository.save(this.crearNotificacion(notificacionDTO));
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
