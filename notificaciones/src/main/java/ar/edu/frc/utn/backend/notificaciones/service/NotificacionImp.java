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

    private static final String TWILIO_SMS_URL = "https://api.twilio.com/2010-04-01/Accounts/{AccountSid}/Messages.json";
    private static final String TWILIO_SMS_FROM = "+15307385799";  // Cambia al número de envío de Twilio para SMS

    @Autowired
    private RestTemplate restTemplate;

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    private final NotificacionRepository notificacionRepository;

    @Autowired
    public NotificacionImp(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public void create(NotificacionDTO notificacionDTO) {
        validateNotificacionDTO(notificacionDTO);
        Notificacion notificacion = convertToEntity(notificacionDTO);
        notificacionRepository.save(notificacion);
    }

    @Override
    public void update(NotificacionDTO notificacionDTO) {
        validateNotificacionDTO(notificacionDTO);
        Notificacion notificacion = convertToEntity(notificacionDTO);
        notificacionRepository.save(notificacion);
    }

    @Override
    public NotificacionDTO delete(Integer id) {
        Optional<Notificacion> notificacion = notificacionRepository.findById(id);
        if (notificacion.isEmpty()) {
            throw new RuntimeException("Notificación no encontrada con ID: " + id);
        }
        notificacionRepository.deleteById(id);
        return convertToDTO(notificacion.get());
    }

    @Override
    public boolean existById(Integer id) {
        return id != null && notificacionRepository.existsById(id);
    }

    @Override
    public NotificacionDTO findById(Integer id) {
        return notificacionRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
    }

    @Override
    public List<NotificacionDTO> findAll() {
        return null;
    }

    @Override
    public void createAll(Iterable<NotificacionDTO> notificacionesDTO) {
        List<Notificacion> notificaciones = new ArrayList<>();

        for (NotificacionDTO dto : notificacionesDTO) {
            validateNotificacionDTO(dto);
            notificaciones.add(convertToEntity(dto));
        }

        if (notificaciones.isEmpty()) {
            throw new RuntimeException("No se proporcionaron notificaciones válidas para crear");
        }

        notificacionRepository.saveAll(notificaciones);
    }

    @Override
    public String sendSmsMessage(NotificacionDTO notificacionDTO) {
        validateNotificacionDTO(notificacionDTO);

        // Reemplazar el marcador {AccountSid} con el SID de cuenta real
        String url = TWILIO_SMS_URL.replace("{AccountSid}", accountSid);

        HttpHeaders headers = createAuthenticatedHeaders();
        LinkedMultiValueMap<String, String> params = createSmsParams(notificacionDTO);
        HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,  // URL con el SID de cuenta real
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // Guardar la notificación después del envío exitoso
            create(notificacionDTO);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            handleSmsError(e);
            return null;
        }
    }

    // Métodos auxiliares
    private void validateNotificacionDTO(NotificacionDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("La notificación no puede ser null");
        }
        if (dto.getMensaje() == null || dto.getMensaje().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }
        if (dto.getTelefonoEmpleado() == null || dto.getTelefonoEmpleado().trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (dto.getLegajoEmpleado() <= 0) {
            throw new IllegalArgumentException("El legajo del empleado debe ser un número positivo");
        }
    }

    private HttpHeaders createAuthenticatedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(accountSid, authToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private LinkedMultiValueMap<String, String> createSmsParams(NotificacionDTO dto) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("From", TWILIO_SMS_FROM);
        params.add("To", dto.getTelefonoEmpleado());
        params.add("Body", dto.getMensaje());
        return params;
    }

    private void handleSmsError(HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new RuntimeException("Error: El número no es válido o no se puede enviar un SMS.");
        }
        throw new RuntimeException("Error al enviar el mensaje SMS: " + e.getMessage());
    }

    private Notificacion convertToEntity(NotificacionDTO dto) {
        Notificacion notificacion = new Notificacion();
        notificacion.setTelefono(dto.getTelefonoEmpleado());
        notificacion.setLegajoEmpleado(dto.getLegajoEmpleado());
        notificacion.setMensaje(dto.getMensaje());
        return notificacion;
    }

    private NotificacionDTO convertToDTO(Notificacion entity) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setTelefonoEmpleado(entity.getTelefono());
        dto.setLegajoEmpleado(entity.getLegajoEmpleado());
        dto.setMensaje(entity.getMensaje());
        return dto;
    }
}
