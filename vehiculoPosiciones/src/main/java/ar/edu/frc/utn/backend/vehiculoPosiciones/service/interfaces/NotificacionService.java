package ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces;


import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.NotificacionDTO;

import java.util.List;

public interface NotificacionService extends Servicio<NotificacionDTO, Integer> {
    void createAll(Iterable<NotificacionDTO> notificacionDTO);
}
