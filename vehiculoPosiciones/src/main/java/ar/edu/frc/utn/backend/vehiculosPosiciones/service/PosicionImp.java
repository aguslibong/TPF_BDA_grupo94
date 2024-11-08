package ar.edu.frc.utn.backend.vehiculosPosiciones.service;

import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.*;
import ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces.PosicionService;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PosicionImp extends ServicioImp<PosicionDTO, Integer> implements PosicionService {

    public ResponseEntity<String> corroborar(PosicionDTO posicion) {
        //Esto es la API que consumimos para que nos devuelva el listado
        //http://localhost:8082/api/prueba/momento
        try {
            PruebaDTO pruebaDTOActual = estaEnPrueba(posicion.getId_vehiculo());
            if (pruebaDTOActual == null) {
                return ResponseEntity.ok ("El vehiculo no se encuentra en Prueba");
            }
            int esZonapeligrosa = esZonaPeligro(posicion);
            if (esZonapeligrosa == 1 || esZonapeligrosa == 2) {
                EmpleadoDTO empleado = obtenerEmpleado(pruebaDTOActual);
                restringirInteresado(pruebaDTOActual);
                marcarPruebaIncidente(pruebaDTOActual);

                String mensaje = obtenerMensajeZonaPeligrosa(esZonapeligrosa);
                NotificacionDTO notificacionDTO = crearNotificacionDTO(empleado, mensaje);
                return enviarNotificacion(notificacionDTO);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok ("EL vehiculo se Encuentra en una posisicon Legal");
    }

    @Override
    public Iterable<PosicionDTO> getPosicionesEnPeriodo(PruebaPosicionPeriodoDTO pruebaPossicion) {
       ;
    }

    private String obtenerMensajeZonaPeligrosa(int esZonapeligrosa) {
        switch (esZonapeligrosa) {
            case 1:
                return "Se excedió del rango del Radio";
            case 2:
                return "Se encuentra en ZONA PELIGROSA";
            default:
                return "Mensaje desconocido"; // En caso de un valor inesperado
        }
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
        // Obtener las coordenadas
        double latitudAuto = posicionDTO.getLatitud();
        double longitudAuto = posicionDTO.getLongitud();
        double latitudZona = agenciaInfoDTO.getCoordenadasAgencia().getLat();
        double longitudZona = agenciaInfoDTO.getCoordenadasAgencia().getLon();

        // Factor de conversión en kilómetros
        double factorLatitud = 111.0; // 1 grado de latitud ≈ 111 km
        double factorLongitud = 111.0 * Math.cos(Math.toRadians(latitudAuto)); // Aproximación según latitud

        // Calcula la distancia euclídea ajustada
        double distancia = Math.sqrt(
                Math.pow((latitudZona - latitudAuto) * factorLatitud, 2) +
                        Math.pow((longitudZona - longitudAuto) * factorLongitud, 2)
        );
        System.out.println(distancia); // Distancia en kilómetros aproximada

        // Verifica si está dentro del radio admitido
        return distancia <= agenciaInfoDTO.getRadioAdmitidoKm();
    }

    private boolean estaEnZona(PosicionDTO posicionDTO, ZonaPeligrosaDTO zonaDTO) {
        double latitudAuto = posicionDTO.getLatitud();
        double longitudAuto = posicionDTO.getLongitud();

        // Obtener las coordenadas mínimas y máximas de la zona peligrosa
        double latitudMin = Math.min(zonaDTO.getNoroeste().getLat(), zonaDTO.getSureste().getLat());
        double latitudMax = Math.max(zonaDTO.getNoroeste().getLat(), zonaDTO.getSureste().getLat());
        double longitudMin = Math.min(zonaDTO.getNoroeste().getLon(), zonaDTO.getSureste().getLon());
        double longitudMax = Math.max(zonaDTO.getNoroeste().getLon(), zonaDTO.getSureste().getLon());

        // Verificar si la posición del vehículo está dentro del rectángulo
        return estaEnRago(latitudAuto, latitudMin, latitudMax)
                && estaEnRago(longitudAuto, longitudMin, longitudMax);
    }

    private boolean estaEnRago(double value, double min, double max) {
        return value >= min && value <= max;
    }

    //RECORDAR HACER LA API PARA INTERESADO
    private  ResponseEntity<String> restringirInteresado(PruebaDTO pruebaDTO) {
        RestTemplate restTemplate = new RestTemplate();
        int idInteresado = pruebaDTO.getIdInteresado();
        ResponseEntity<String> res = restTemplate.exchange(
                "http://localhost:8082/api/interesado/restringir/{id}",
                HttpMethod.PUT,
                null,
                String.class,
                idInteresado
        );
        return res;
    }
    private  ResponseEntity<String> marcarPruebaIncidente(PruebaDTO pruebaDTO) {
        RestTemplate restTemplate = new RestTemplate();
        int id = pruebaDTO.getIdPrueba();
        ResponseEntity<String> res = restTemplate.exchange(
                "http://localhost:8082/api/prueba/incidente/{id}",
                HttpMethod.PUT,
                null,
                String.class,
                id
        );
        return res;
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
        notificacionDTONueva.setTelefonoEmpleado(empleadoDTO.getTelefonoContacto());

        return notificacionDTONueva;
    }

    private ResponseEntity<String> enviarNotificacion(NotificacionDTO notificacionDTO) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.postForEntity(
                "http://localhost:8083/api/notificacion/empleado",
                notificacionDTO,
                String.class);

        // Extraer el cuerpo (body) del ResponseEntity devuelto por la API
        String body = response.getBody();

        // Devolver un nuevo ResponseEntity con el cuerpo de la respuesta
        return ResponseEntity.status(response.getStatusCode())
                .body(body);
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

        for(ZonaPeligrosaDTO zona : agenciaInfoDTO.getZonasRestringidas()){
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
