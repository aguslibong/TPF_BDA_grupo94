package ar.edu.frc.utn.backend.notificaciones.service;

import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionEmpleadoDTO;
import ar.edu.frc.utn.backend.notificaciones.DTO.NotificacionPromocionDTO;
import ar.edu.frc.utn.backend.notificaciones.entities.NotificacionEmpleado;
import ar.edu.frc.utn.backend.notificaciones.entities.NotificacionPromocion;
import ar.edu.frc.utn.backend.notificaciones.repository.NotificacionPromocionRepository;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.NotificacionPromocionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionPromocionImp implements NotificacionPromocionService {

    private final NotificacionPromocionRepository notificacionPromocionRepository;

    public NotificacionPromocionImp(NotificacionPromocionRepository notificacionPromocionRepository) {
        this.notificacionPromocionRepository = notificacionPromocionRepository;
    }

    @Override
    public void createAll(List<NotificacionPromocionDTO> notificacion) {
        try{
            List<NotificacionPromocion> notificaciones = notificacion.stream()
                    .map(noti -> crearNotificacion(noti))
                    .collect(Collectors.toList());

            notificacionPromocionRepository.saveAll(notificaciones);
        }
        catch(RuntimeException e){
            throw new RuntimeException("Error al crear notificaciones", e);
        }

    }
    private NotificacionPromocion crearNotificacion(NotificacionPromocionDTO notificacionPromocionDTO) {
        NotificacionPromocion notificacionPromocion = new NotificacionPromocion();
        notificacionPromocion.setMensaje(notificacionPromocionDTO.getMensaje());
        notificacionPromocion.setTelefono(notificacionPromocionDTO.getTelefono());
        return notificacionPromocion;
    }

    @Override
    public void create(NotificacionPromocionDTO notificacionPromocionDTO) {

    }

    @Override
    public void update(NotificacionPromocionDTO notificacionPromocionDTO) {

    }

    @Override
    public NotificacionPromocionDTO delete(Integer id) {
        return null;
    }

    @Override
    public boolean existById(Integer id) {
        return false;
    }

    @Override
    public NotificacionPromocionDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<NotificacionPromocionDTO> findAll() {
        return List.of();
    }
}
