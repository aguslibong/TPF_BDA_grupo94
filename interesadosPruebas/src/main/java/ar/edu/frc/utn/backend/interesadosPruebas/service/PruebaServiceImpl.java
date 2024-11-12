package ar.edu.frc.utn.backend.interesadosPruebas.service;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PosicionPeriodoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDetalladaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.VehiculoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.convert.PruebaToPruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Empleado;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.EmpleadoRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.InteresadoRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.PruebaRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.PruebaService;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.Servicio;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PruebaServiceImpl implements PruebaService {

    private final PruebaRepository pruebarepository;
    private final InteresadoRepository interesadorepository;
    private final EmpleadoRepository empleadorepository;
    private final PruebaRepository pruebaRepository;
    private final PruebaToPruebaDTO converter; //

    // Constructor con inyección de dependencias
    public PruebaServiceImpl(PruebaRepository pruebarepository, InteresadoRepository interesadorepository, EmpleadoRepository empleadoRepository, PruebaRepository pruebaRepository) {
        this.pruebarepository = pruebarepository;
        this.interesadorepository = interesadorepository;
        this.empleadorepository = empleadoRepository;
        this.pruebaRepository = pruebaRepository;
        this.converter = new PruebaToPruebaDTO(); // Inicializar el converter
    }

    //PUNTO 1 CREAR PRUEBA

    private void validatePruebaDTO(PruebaDTO pruebaDTO) {
        if (Integer.valueOf(pruebaDTO.getIdEmpleado()) == null || Integer.valueOf(pruebaDTO.getIdInteresado()) == null || Integer.valueOf(pruebaDTO.getIdVehiculo()) == null) {
            throw new IllegalArgumentException("No se han cargado todos los datos necesarios para la creación de la prueba");
        }
    }

    private Interesado getInteresado(Integer idInteresado) {
        return interesadorepository.findById(idInteresado)
                .orElseThrow(() -> new IllegalArgumentException("Interesado con ID " + idInteresado + " no encontrado."));
    }

    private Empleado getEmpleado(Integer idEmpleado) {
        return empleadorepository.findById(idEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("Empleado con ID " + idEmpleado + " no encontrado."));
    }

    private void validateInteresado(Interesado interesado) {
        LocalDate fechaActual = LocalDate.now();

        if (interesado.getFechaVencimientoLicencia().isBefore(fechaActual)) {
            throw new RuntimeException("La licencia del interesado está vencida.");
        }

        if (interesado.isRestringido()) {
            throw new RuntimeException("El Cliente esta restringido");
        }
    }

    private VehiculoDTO fetchVehiculo(Integer idVehiculo) {
        try {
            RestTemplate template = new RestTemplate();

            ResponseEntity<VehiculoDTO> res = template.getForEntity("http://localhost:8081/api/vehiculo/{id}", VehiculoDTO.class, idVehiculo);

            if (res.getStatusCode().is2xxSuccessful()) return res.getBody();

        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("No exitoso la respuesta el vehiculo no existe", ex);
        }
        return null;
    }

    private void validateVehiculo(Integer idVehiculo) {

        List<Prueba> listapruebaVehiculo = pruebarepository.findAllByIdVehiculoEquals(idVehiculo);
        listapruebaVehiculo.forEach(p -> {
            System.out.println(p.toString());
        });
        boolean isVehiculoOcupado = listapruebaVehiculo.stream()
                .anyMatch(prueba -> prueba.getFechaHoraFin().equals(prueba.getFechaHoraInicio()));


        if (isVehiculoOcupado) {
            throw new RuntimeException("El Vehiculo esta siendo usado en otra prueba");
        }
    }


    private Prueba createPrueba(PruebaDTO pruebaDTO, Interesado interesado, Empleado empleado) {
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        Prueba prueba = new Prueba();
        prueba.setInteresado(interesado);
        prueba.setEmpleado(empleado);
        prueba.setIdVehiculo(pruebaDTO.getIdVehiculo());
        prueba.setFechaHoraInicio(fechaHoraActual);
        prueba.setFechaHoraFin(fechaHoraActual);

        return prueba;
    }


    public Iterable<PruebaDTO> obtenerListaPruebasMomento() {
        Iterable<Prueba> listaPrueba = pruebarepository.findAllByFechaHoraFinNull();
        List<PruebaDTO> listaPruebaDTO = new ArrayList<>();

        for (Prueba prueba : listaPrueba) {
            listaPruebaDTO.add(converter.apply(prueba));
        }

        return listaPruebaDTO;
    }


    public ResponseEntity<String> finalizarPrueba(int idPruebaFinalizar, String comentario)  {
        try {
            Optional<Prueba> prueba = pruebaRepository.findById(idPruebaFinalizar);
            if(prueba.isEmpty()) {
                throw new RuntimeException("El Id no existe");
            }

            Prueba pruebaFinalizar = prueba.get();

            Iterable<PruebaDTO> iterable = obtenerListaPruebasMomento();
            List<PruebaDTO> listaMomento = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
            if(listaMomento.stream().filter(pruebaDTO -> pruebaDTO.getIdPrueba()==(idPruebaFinalizar)).count() == 0) {
                throw new RuntimeException("La prueba ya finalizo");
            }

            LocalDateTime fechaHoraActualfinalizar = LocalDateTime.now();
            pruebaFinalizar.setFechaHoraFin(fechaHoraActualfinalizar);
            pruebaFinalizar.setComentarios(comentario);
            pruebarepository.save(pruebaFinalizar);


            return ResponseEntity.ok("Prueba finalizada con éxito");

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    private void validatarInteresadoYEmpleado(Empleado empleado, Interesado interesado) {

        Iterable<PruebaDTO> pruebas = obtenerListaPruebasMomento();

        for(PruebaDTO pruebaDTO : pruebas) {

            if( pruebaDTO.getIdInteresado() == interesado.getID()){
                throw new RuntimeException("El interesado ya está en prueba");
            }
            if (pruebaDTO.getIdEmpleado() == empleado.getLEGAJO()){
                throw new RuntimeException("El empleado ya está en prueba");
            }
        }

    }


    @Override
    public void create(PruebaDTO pruebaDTO) throws Exception {
        try {
            validatePruebaDTO(pruebaDTO);

            Interesado interesado = getInteresado(pruebaDTO.getIdInteresado());

            Empleado empleado = getEmpleado(pruebaDTO.getIdEmpleado());

            validateInteresado(interesado);

            validatarInteresadoYEmpleado(empleado, interesado);

            VehiculoDTO vehiculoDTO = fetchVehiculo(pruebaDTO.getIdVehiculo());

            validateVehiculo(pruebaDTO.getIdVehiculo());

            Prueba prueba = createPrueba(pruebaDTO, interesado, empleado);

            pruebarepository.save(prueba);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PruebaDTO prueba) {

    }

    @Override
    public PruebaDTO delete(Integer id) {
        return null;
    }

    @Override
    public PruebaDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<PruebaDTO> findAll() {
        return pruebaRepository.findAll().stream().map(prueba -> converter.apply(prueba)).collect(Collectors.toList());
    }

    @Override
    public String cambiarIncidente(int id) {
        Optional<Prueba> optionalPrueba = pruebaRepository.findById(id);
        if (optionalPrueba.isPresent() ) {
            Prueba prueba = optionalPrueba.get();
            if (prueba.isIncidente() == false){
                prueba.setIncidente(true);
                pruebaRepository.save(prueba);
                return "Se modificó la prueba como incidente";
            } else {
                return "La prueba ya fue marcada como incidente";
            }
        } else {
            return "No se encontró el interesado con el id: " + id;
        }
    }

    private List <Prueba> listaDeIncidentes (){
        List <Prueba> listaPruebas= pruebaRepository.findAllByIncidente(true);
        return listaPruebas;
    }

    @Override
    public Iterable<PruebaDTO> incidenteReporte() {
        List <Prueba> listaPruebas= listaDeIncidentes();
        Iterable<PruebaDTO> listaPruebasDTO = listaPruebas.stream().map(prueba -> converter.apply(prueba)).collect(Collectors.toList());
        return listaPruebasDTO;
    }

    private List<Prueba> obtenerPruebasDeVehiculo(int idVehiculo) {
        return pruebaRepository.findAllByIdVehiculoEquals(idVehiculo);
    }

    @Override
    public Iterable<PruebaDetalladaDTO> reporteVehiculo(int idVehiculo) throws Exception {
        try {
            VehiculoDTO vehiculo = fetchVehiculo(idVehiculo);
            List<Prueba> prueba = obtenerPruebasDeVehiculo(idVehiculo);
            List <PruebaDetalladaDTO> listaPruebaDetallada = new ArrayList<>();
            prueba.forEach(i -> {
                PruebaDetalladaDTO pruebaDetallada = new PruebaDetalladaDTO(i,vehiculo);
                listaPruebaDetallada.add(pruebaDetallada);
            });

            return listaPruebaDetallada;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Iterable<PruebaDetalladaDTO> incidentesPorEmpleado(int idEmpleado) throws Exception {
        try {

            Optional<Empleado> empleado = empleadorepository.findById(idEmpleado);
            if (empleado.isPresent()) {
                List<Prueba> listaIncidentes = listaDeIncidentes();
                List<Prueba> listafiltradoempleado = listaIncidentes.stream().filter(i -> i.getEmpleado().getLEGAJO() == idEmpleado).collect(Collectors.toList());
                List<PruebaDetalladaDTO> listaPruebaDetallada = new ArrayList<>();
                listafiltradoempleado.forEach(i -> {
                    VehiculoDTO vehiculo = fetchVehiculo(i.getIdVehiculo());
                    PruebaDetalladaDTO pruebaDetallada = new PruebaDetalladaDTO(i, vehiculo);
                    listaPruebaDetallada.add(pruebaDetallada);
                });
                return listaPruebaDetallada;
            } else {
                throw new Exception("El id del empleado no existe");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Prueba getPruebaById(int idPrueba) {
        return pruebaRepository.getById(idPrueba);
    }

    @Override
    public Double calcularKilometros(PosicionPeriodoDTO posicionPeriodoDTO) throws Exception {

        try {
            if( posicionPeriodoDTO.getFechaFin().isAfter(posicionPeriodoDTO.getFechaInicio()))
            {
                Prueba prueba = getPruebaById(posicionPeriodoDTO.getIdPrueba());

                    RestTemplate template = new RestTemplate();
                    VehiculoDTO vehiculo = fetchVehiculo(prueba.getIdVehiculo());
                    posicionPeriodoDTO.setIdVehiculo(vehiculo.getIdVehiculo());
                    ResponseEntity<Double> res = template.postForEntity("http://localhost:8081/api/posicion/periodo", posicionPeriodoDTO, Double.class);
                    return res.getBody();
            } else {
                throw new RuntimeException("El periodo es incorrecto");
            }
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
