package ar.edu.frc.utn.backend.notificaciones.service;

import ar.edu.frc.utn.backend.notificaciones.entities.Notificacion;
import ar.edu.frc.utn.backend.notificaciones.repository.NotificacionRepository;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificacionImp implements NotificacionService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    private static final String TWILIO_WHATSAPP_URL = "https://api.twilio.com/2010-04-01/Accounts/{AccountSid}/Messages.json";


    private final NotificacionRepository notificacionRepository;
    public NotificacionImp(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }



    @Override
    public void create(Notificacion notificacion) {
        notificacionRepository.save(Notificacion notificacion);
    }

    @Override
    public void update(Notificacion notificacion) {

    }

    @Override
    public Notificacion delete(Integer id) {
        return null;
    }

    @Override
    public boolean existById(Integer id) {
        return false;
    }

    @Override
    public Notificacion findById(Integer id) {
        return null;
    }

    @Override
    public List<Notificacion> findAll() {
        return List.of();
    }

    @Override
    public void createAll(Iterable<Notificacion> notificacion) {
        // Mapeo de DTOs a Entidades
        /*try{
            List<Notificacion> notificaciones = new ArrayList<>();
            for (Notificacion dto : notificacion) {
                if(dto.getMensaje() == null || dto.getTelefono() == null || dto.getLegajoEmpleado() == 0) {
                    continue;
                }
                Notificacion notificacion = new Notificacion();
                notificacion.setTelefono(dto.getTelefono()); // Mapea cada campo del DTO a la entidad
                notificacion.setMensaje(dto.getMensaje());
                notificacion.setIdEmpleado(dto.getLegajoEmpleado());
                // etc. para todos los campos
                notificaciones.add(notificacion);
            }
            if (notificaciones.isEmpty()){
                throw new RuntimeException("No se cargó ninguna notificacion");
            }
            // Guardar las entidades en el repositorio
            notificacionRepository.saveAll(notificaciones);
        } catch(RuntimeException e){
            throw new RuntimeException("Error en el tiempo de ejecucion");
        }*/
    }


    @Override
    public Notificacion sendWhatsAppMessage(Notificacion notificacion) {

        if (notificacion.getTelefono().isEmpty() || notificacion.getMensaje().isEmpty() || notificacion.getLegajoEmpleado() == 0) {
            throw new RuntimeException("Uno de los paramétros vino vacío");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(accountSid, authToken);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> params = new HashMap<>();
        params.put("From", "whatsapp:+14155238886");  // Número de WhatsApp de Twilio
        params.put("To", "whatsapp:" + get);
        params.put("Body", message);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    TWILIO_WHATSAPP_URL,
                    HttpMethod.POST,
                    entity,
                    String.class,
                    accountSid
            );

            NotificacionDTO notificacionDTO = new NotificacionDTO(to, message);

            create(notificacionDTO);

            return response.getBody(); // Devuelve la respuesta en caso de éxito
        } catch (HttpClientErrorException e) {
            // Verifica si es el código de error de Twilio para un número no válido
            if (e.getStatusCode().value() == 400) {
                // Opcional: puedes hacer un análisis del mensaje para verificar si es el código 21614 específicamente
                return "Error: El número no está registrado en WhatsApp.";
            } else {
                throw new RuntimeException("Error al enviar el mensaje de WhatsApp: " + e.getMessage());
            }
        }

}
}
