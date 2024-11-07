package ar.edu.frc.utn.backend.notificaciones.service;

import ar.edu.frc.utn.backend.notificaciones.DTO.*;
import ar.edu.frc.utn.backend.notificaciones.service.interfaces.PosicionService;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PosicionImp extends ServicioImp<PosicionDTO, Integer> implements PosicionService {

    public String corroborar(PosicionDTO posicion) {
        //Esto es la API que consumimos para que nos devuelva el listado
        //http://localhost:8082/api/prueba/momento
        try {
            PruebaDTO pruebaDTOActual = estaEnPrueba(posicion.getId_vehiculo());
            if (pruebaDTOActual == null) {
                return ("El vehiculo no se encuentra en Prueba");
            }
            int esZonapeligrosa = esZonaPeligro(posicion);
            if (esZonapeligrosa == 1) {
                EmpleadoDTO empleado = obtenerEmpleado(pruebaDTOActual);
                restringirInteresado(pruebaDTOActual);
                //* falta saber si es por zona peligrosa o por fuera de radio
                String mensaje = "Se excidio del rango del Radio"; // este mensaje se mofifica segun el caso
                NotificacionDTO notificacionDTO = crearNotificacionDTO(empleado, mensaje);
                enviarNotificacion(notificacionDTO);
                return notificacionDTO.toString();
            }
            if (esZonapeligrosa == 2) {
                EmpleadoDTO empleado = obtenerEmpleado(pruebaDTOActual);
                restringirInteresado(pruebaDTOActual);
                //* falta saber si es por zona peligrosa o por fuera de radio
                String mensaje = "Se encuentra en ZONA PELIGROSA"; // este mensaje se mofifica segun el caso
                NotificacionDTO notificacionDTO = crearNotificacionDTO(empleado, mensaje);
                enviarNotificacion(notificacionDTO);
                return notificacionDTO.toString();
            }



        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return "EL vehiculo se Encuentra en una posisicon Legal";
    }

    //aca le pasamos la posicion del auto
    private PruebaDTO estaEnPrueba(int IdVehiculo) {
        try {
            RestTemplate template = new RestTemplate();

            // Llamada a la API para obtener la lista de pruebas
            ResponseEntity<PruebaDTO[]> res = template.getForEntity("http://localhost:8082/api/prueba/momento", PruebaDTO[].class);

            if (!res.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Error al llamar la API");
            }

            // Convertir el array a lista
            List<PruebaDTO> listaPruebas = Arrays.asList(res.getBody());

            // Filtrar y obtener la primera prueba que coincida con el IdVehiculo
            return listaPruebas.stream()
                    .filter(p -> p.getIdVehiculo() == IdVehiculo)
                    .findFirst() // Retorna un Optional<PruebaDTO>
                    .orElse(null); // Retorna null si no hay coincidencias

        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("No fue exitosa la respuesta del cliente", ex);
        }
    }

    //aca recibimos los datos de la poscion y el radio
    private boolean estaEnRadio(PosicionDTO posicionDTO, AgenciaInfoDTO agenciaInfoDTO) {
        // Obtén las coordenadas (catetos)
        double latitudAuto = posicionDTO.getLatitud();
        double longitudAuto = posicionDTO.getLongitud();

        double latitudZona = agenciaInfoDTO.getCoordenadaAgencia().getLatitud();
        double longitudZona = agenciaInfoDTO.getCoordenadaAgencia().getLongitud();


        // Calcula la distancia desde el origen (hipotenusa)
        double distancia = Math.sqrt(Math.pow((latitudZona-latitudAuto), 2) + Math.pow((longitudZona-longitudAuto), 2));

        // Verifica si la distancia está dentro del radio límite
        if (distancia <= agenciaInfoDTO.getRadio()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean estaEnZona(PosicionDTO posicionDTO, ZonaPeligrosaDTO zonaDTO) {
        double latitudAuto = posicionDTO.getLatitud();
        double longitudAuto = posicionDTO.getLongitud();

        // Obtener las coordenadas mínimas y máximas de la zona peligrosa
        double latitudMin = Math.min(zonaDTO.getNoreste().getLatitud(), zonaDTO.getSureste().getLatitud());
        double latitudMax = Math.max(zonaDTO.getNoreste().getLatitud(), zonaDTO.getSureste().getLatitud());
        double longitudMin = Math.min(zonaDTO.getNoreste().getLongitud(), zonaDTO.getSureste().getLongitud());
        double longitudMax = Math.max(zonaDTO.getNoreste().getLongitud(), zonaDTO.getSureste().getLongitud());

        // Verificar si la posición del vehículo está dentro del rectángulo
        return estaEnRago(latitudAuto, latitudMin, latitudMax)
                && estaEnRago(longitudAuto, longitudMin, longitudMax);
    }

    private boolean estaEnRago(double value, double min, double max) {
        return value >= min && value <= max;
    }

    //RECORDAR HACER LA API PARA INTERESADO
    private void restringirInteresado(PruebaDTO pruebaDTO) {
        RestTemplate restTemplate = new RestTemplate();
        int idInteresado = pruebaDTO.getIdInteresado();
        ResponseEntity<String> res = restTemplate.exchange(
                "http://localhost:8082/api/interesado/{id}",
                HttpMethod.PUT,
                null,
                String.class,
                idInteresado
        );
    }

    private EmpleadoDTO obtenerEmpleado(PruebaDTO pruebaDTOActual) {
        RestTemplate template = new RestTemplate();
        // Llamada a la API para obtener el empleadoDTO
        ResponseEntity<EmpleadoDTO> res = template.getForEntity(
                "http://localhost:8082/api/empleado/" + pruebaDTOActual.getIdEmpleado(),
                EmpleadoDTO.class
        );
        EmpleadoDTO empleado = res.getBody();
        return empleado;
    }

    private NotificacionDTO crearNotificacionDTO(EmpleadoDTO empleadoDTO, String mensaje) {
        NotificacionDTO notificacionDTONueva = new NotificacionDTO();
        notificacionDTONueva.setMensaje(mensaje);
        notificacionDTONueva.setLegajoEmpleado(empleadoDTO.getLegajo());
        notificacionDTONueva.setTelefonoEmpleado(notificacionDTONueva.getTelefonoEmpleado());

        return notificacionDTONueva;
    }

    private void enviarNotificacion(NotificacionDTO notificacionDTO) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<NotificacionDTO> res = template.postForEntity(
                "http://localhost:8083/api/notificacion",
                notificacionDTO,  // este es el body
                NotificacionDTO.class);
    }

    private int esZonaPeligro(PosicionDTO posicionDTO){
        RestTemplate template = new RestTemplate();

        //COMPLETAR API
        ResponseEntity res = template.getForEntity(
                "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/",
                AgenciaInfoDTO.class);
        AgenciaInfoDTO agenciaInfoDTO = (AgenciaInfoDTO) res.getBody();

        if(!estaEnRadio(posicionDTO, agenciaInfoDTO)){
            return 1;
        };

        for(ZonaPeligrosaDTO zona : agenciaInfoDTO.getListaZonaPeligrosas()){
            if (estaEnZona(posicionDTO, zona)) {
                return 2;
            }
        }

        return 3;

    }
//metodo en PosicionService

    @Override
    public void create(PosicionDTO posicionDTO) {

    }

    @Override
    public void update(PosicionDTO posicionDTO) {

    }

    @Override
    public PosicionDTO delete(Integer id) {
        return null;
    }

    @Override
    public boolean existById(Integer id) {
        return false;
    }

    @Override
    public PosicionDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<PosicionDTO> findAll() {
        return List.of();
    }
}
