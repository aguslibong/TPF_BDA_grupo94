package ar.edu.frc.utn.backend.interesadosPruebas.service;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.DTO.VehiculoDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Empleado;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.EmpleadoRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.InteresadoRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.PruebaRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.PruebaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PruebaServiceImpl implements PruebaService {

    private final PruebaRepository pruebarepository;
    private final InteresadoRepository interesadorepository;
    private final EmpleadoRepository empleadorepository;

    // Constructor con inyección de dependencias
    public PruebaServiceImpl(PruebaRepository pruebarepository, InteresadoRepository interesadorepository, EmpleadoRepository empleadoRepository) {
        this.pruebarepository = pruebarepository;
        this.interesadorepository = interesadorepository;
        this.empleadorepository = empleadoRepository;
    }

    @Override
    public void crearPrueba(PruebaDTO pruebaDTO) {
        try {
            validatePruebaDTO(pruebaDTO);

            Interesado interesado = getInteresado(pruebaDTO.getIdInteresado());
            Empleado empleado = getEmpleado(pruebaDTO.getIdEmpleado());

            validateInteresado(interesado);

            VehiculoDTO vehiculoDTO = fetchVehiculo(pruebaDTO.getIdVehiculo());

            validateVehiculo(pruebaDTO.getIdVehiculo());

            Prueba prueba = createPruebaEntity(pruebaDTO, interesado, empleado);

            pruebarepository.save(prueba);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

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

            if (res.getStatusCode().is2xxSuccessful()) {
                return res.getBody();
            } else {
                throw new RuntimeException("El vehiculo no existe");
            }

        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("No exitoso la respuesta del cliente", ex);
        }
    }

    private void validateVehiculo(Integer idVehiculo) {

        List<Prueba> listapruebaVehiculo = pruebarepository.findAllByIdVehiculo(idVehiculo);
        boolean isVehiculoOcupado = listapruebaVehiculo.stream()
                .anyMatch(prueba -> prueba.getFechaHoraFin() == null);

        if (isVehiculoOcupado) {
            throw new RuntimeException("El Vehiculo esta siendo usado en otra prueba");
        }
    }

    private Prueba createPruebaEntity(PruebaDTO pruebaDTO, Interesado interesado, Empleado empleado) {
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        Prueba prueba = new Prueba();
        prueba.setInteresado(interesado);
        prueba.setEmpleado(empleado);
        prueba.setIdVehiculo(pruebaDTO.getIdVehiculo());
        prueba.setFechaHoraInicio(fechaHoraActual);


        return prueba;
    }


    //Listar todas las pruebas en curso en un momento dado
    private PruebaDTO convertirAPruebaDTO (Prueba prueba) {
        PruebaDTO dto = new PruebaDTO();
        dto.setIdInteresado(prueba.getInteresado().getID());
        dto.setIdVehiculo(prueba.getIdVehiculo());
        dto.setIdEmpleado(prueba.getEmpleado().getLEGAJO());
        return dto;
    }

    public Iterable<PruebaDTO> obetenerListaPruebasMomento() {
        Iterable<Prueba> listaPrueba = pruebarepository.findAllByFechaHoraFinNull();
        List<PruebaDTO> listaPruebaDTO = new ArrayList<>();
        for (Prueba prueba : listaPrueba) {
            listaPruebaDTO.add(convertirAPruebaDTO(prueba));
        }
        return listaPruebaDTO;
    }


}
