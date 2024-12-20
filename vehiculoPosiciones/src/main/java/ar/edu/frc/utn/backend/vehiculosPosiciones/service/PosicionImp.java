package ar.edu.frc.utn.backend.vehiculosPosiciones.service;

import ar.edu.frc.utn.backend.vehiculosPosiciones.DTO.*;
import ar.edu.frc.utn.backend.vehiculosPosiciones.entities.Posicion;
import ar.edu.frc.utn.backend.vehiculosPosiciones.entities.Vehiculo;
import ar.edu.frc.utn.backend.vehiculosPosiciones.repository.PosicionRepository;
import ar.edu.frc.utn.backend.vehiculosPosiciones.repository.VehiculoRepository;
import ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces.PosicionService;
import ar.edu.frc.utn.backend.vehiculosPosiciones.service.interfaces.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PosicionImp extends ServicioImp<PosicionDTO, Integer> implements PosicionService {

    private PosicionRepository posicionRepository;
    private VehiculoRepository vehiculoRepository;

    @Autowired
    public PosicionImp(PosicionRepository posicionRepository, VehiculoRepository vehiculoRepository) {
        this.posicionRepository = posicionRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public ResponseEntity<String> corroborar(PosicionDTO posicion) {
        //Esto es la API que consumimos para que nos devuelva el listado
        //http://localhost:8082/api/prueba/momento
        LocalDateTime fechaActual = LocalDateTime.now();
        try {

            Optional<Vehiculo> vehiculo = vehiculoRepository.findById(posicion.getId_vehiculo());
            if(vehiculo.isPresent()) {
                PruebaDTO pruebaDTOActual = estaEnPrueba(posicion.getId_vehiculo());
                if (pruebaDTOActual == null) {
                    return ResponseEntity.ok("El vehiculo no se encuentra en Prueba");
                }
                int esZonapeligrosa = esZonaPeligro(posicion);
                if (esZonapeligrosa == 1 || esZonapeligrosa == 2) {
                    EmpleadoDTO empleado = obtenerEmpleado(pruebaDTOActual);
                    restringirInteresado(pruebaDTOActual);
                    marcarPruebaIncidente(pruebaDTOActual);
                    String mensaje = obtenerMensajeZonaPeligrosa(esZonapeligrosa);
                    NotificacionDTO notificacionDTO = crearNotificacionDTO(empleado, mensaje);
                    Posicion posicionNueva = crearPosicion(posicion, fechaActual);
                    posicionRepository.save(posicionNueva);
                    return enviarNotificacion(notificacionDTO);
                }
            } else {
                return ResponseEntity.ok("El vehiculo no existe");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        Posicion posicionNueva = crearPosicion(posicion, fechaActual);
        posicionRepository.save(posicionNueva);
        return ResponseEntity.ok("EL vehiculo se Encuentra en una posisicon Legal");
    }

    private Posicion crearPosicion(PosicionDTO posicion, LocalDateTime fechaActual) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(posicion.getId_vehiculo());
        if (vehiculo.isPresent()) {
            Posicion posicionNueva = new Posicion();
            posicionNueva.setVehiculo(vehiculo.get());
            posicionNueva.setLatitud(posicion.getLatitud());
            posicionNueva.setLongitud(posicion.getLongitud());
            posicionNueva.setFechaHora(fechaActual);
            return posicionNueva;

        } else {
            throw new RuntimeException("El vehiculo no existe");
        }

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


        // Calcula la distancia euclídea ajustada
        double distancia = CalculoDisatancia(latitudAuto,longitudAuto,latitudZona,longitudZona);
        System.out.println(distancia); // Distancia en kilómetros aproximada

        // Verifica si está dentro del radio admitido
        return distancia <= agenciaInfoDTO.getRadioAdmitidoKm();
    }

    private double CalculoDisatancia(double latitudA, double longitudA, double latitudB, double longitudB  ){
        double factorLatitud = 111.0; // 1 grado de latitud ≈ 111 km
        double factorLongitud = 111.0 * Math.cos(Math.toRadians(latitudA)); // Aproximación según latitud

        double distancia = Math.sqrt(
                Math.pow((latitudB - latitudA) * factorLatitud, 2) +
                        Math.pow((longitudB - longitudA) * factorLongitud, 2)
        );
        return distancia;
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

    @Override
    public double getPosicionesEnPeriodo(PosicionPeriodoDTO pruebaPosicion) {
        double cantKilometros = 0d;
        try {
            // Obtener las posiciones de la base de datos
            Optional<Vehiculo> vehiculo = vehiculoRepository.findById(pruebaPosicion.getIdVehiculo());
            if(vehiculo.isPresent()) {
                List<Posicion> listaPosiciones = posicionRepository.findAllByVehiculoAndFechaHoraBetween(
                        vehiculo.get(),
                        pruebaPosicion.getFechaInicio(),
                        pruebaPosicion.getFechaFin()
                );
                //List<Posicion> listaPosiciones = posicionRepository.findAllByVehiculo(vehiculo.get());
                System.out.println(listaPosiciones);
                System.out.println(pruebaPosicion.getFechaFin());

                // Verificamos si hay más de una posición para realizar el cálculo
                for (int i = 0; i < listaPosiciones.size() - 1; i++) {
                    // Obtener las coordenadas de la posición actual y la siguiente
                    Posicion posicionActual = listaPosiciones.get(i);
                    Posicion posicionSiguiente = listaPosiciones.get(i + 1);
                    System.out.println(posicionActual);

                    // Calcular la distancia entre la posición actual y la siguiente
                    double distancia = CalculoDisatancia(
                            posicionActual.getLatitud(),
                            posicionActual.getLongitud(),
                            posicionSiguiente.getLatitud(),
                            posicionSiguiente.getLongitud()
                    );

                    // Acumulamos la distancia
                    cantKilometros += distancia;
                }

                // Aquí ya tenemos la cantidad total de kilómetros recorridos
                System.out.println("Kilómetros recorridos: " + cantKilometros);
            }
            else {
                throw new RuntimeException("Vehiculo no encontrado");
            }
        } catch (DataAccessException e) {
            // Manejo de excepciones relacionadas con el acceso a datos
            System.err.println("Error al acceder a los datos: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // Manejo de excepciones relacionadas con argumentos inválidos
            System.err.println("Argumento inválido proporcionado: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Manejo de cualquier otra excepción
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        return cantKilometros;
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
