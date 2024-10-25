package ar.edu.frc.utn.backend.vehiculoPosiciones.service;

import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.NotificacionDTO;
import ar.edu.frc.utn.backend.vehiculoPosiciones.entities.Notificacion;
import ar.edu.frc.utn.backend.vehiculoPosiciones.repository.NotificacionRepository;
import ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces.NotificacionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificacionImp extends ServicioImp<NotificacionDTO, Integer> implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    public NotificacionImp(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
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

    @Override
    public void createAll(Iterable<NotificacionDTO> notificacionDTOs) {
        // Mapeo de DTOs a Entidades
        try{
            List<Notificacion> notificaciones = new ArrayList<>();
            for (NotificacionDTO dto : notificacionDTOs) {
                if(dto.getMensaje() == null || dto.getTelefono() == null){
                    continue;
                }
                Notificacion notificacion = new Notificacion();
                notificacion.setTelefono(dto.getTelefono()); // Mapea cada campo del DTO a la entidad
                notificacion.setMensaje(dto.getMensaje());
                // etc. para todos los campos
                notificaciones.add(notificacion);
            }
            // Guardar las entidades en el repositorio
            notificacionRepository.saveAll(notificaciones);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
