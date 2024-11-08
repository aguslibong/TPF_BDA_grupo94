package ar.edu.frc.utn.backend.vehiculosPosiciones.service;

import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.NotificacionEmpleadoDTO;
import ar.edu.frc.utn.backend.vehiculosPosiciones.entities.NotificacionEmpleado;
import ar.edu.frc.utn.backend.vehiculosPosiciones.repository.NotificacionEmpleadoRepository;
import ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces.NotificacionEmpleadoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionEmpleadoImp implements NotificacionEmpleadoService {

    private final NotificacionEmpleadoRepository notificacionEmpleadoRepository;

    public NotificacionEmpleadoImp(NotificacionEmpleadoRepository notificacionEmpleadoRepository) {
        this.notificacionEmpleadoRepository = notificacionEmpleadoRepository;
    }

    @Override
    public String sendSmsMessage(NotificacionEmpleadoDTO notificacion) {
        create(notificacion);
        return "Se Envio notificacion";
    }

    private NotificacionEmpleado crearNotificacion(NotificacionEmpleadoDTO notificacionEmpleadoDTO) {
        NotificacionEmpleado notificacionEmpleado = new NotificacionEmpleado();

        notificacionEmpleado.setMensaje(notificacionEmpleadoDTO.getMensaje());
        notificacionEmpleado.setTelefono(notificacionEmpleadoDTO.getTelefonoEmpleado());
        notificacionEmpleado.setLegajoEmpleado(notificacionEmpleadoDTO.getLegajoEmpleado());
        return notificacionEmpleado;
    }

    @Override
    public void create(NotificacionEmpleadoDTO notificacionEmpleadoDTO) {
        notificacionEmpleadoRepository.save(crearNotificacion(notificacionEmpleadoDTO));
    }

    @Override
    public void update(NotificacionEmpleadoDTO notificacionEmpleadoDTO) {
    }

    @Override
    public NotificacionEmpleadoDTO delete(Integer id) {
        return null;
    }

    @Override
    public boolean existById(Integer id) {
        return false;
    }

    @Override
    public NotificacionEmpleadoDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<NotificacionEmpleadoDTO> findAll() {
        return List.of();
    }
}
