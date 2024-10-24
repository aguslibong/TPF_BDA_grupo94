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
            System.out.println(pruebaDTO.getIdInteresado());
            //verificar si llegan toddos los datos necesarios
            if (Integer.valueOf(pruebaDTO.getIdEmpleado()) == null || Integer.valueOf(pruebaDTO.getIdInteresado()) == null || Integer.valueOf(pruebaDTO.getIdVehiculo()) == null) {
                throw new IllegalArgumentException("No se han cargado todos los datos necesarios para la creación de la prueba");
            }
            // Buscar el interesado por ID
            Optional<Interesado> optionalInteresado = interesadorepository.findById(pruebaDTO.getIdInteresado());
            Optional<Empleado> optionalEmpleado = empleadorepository.findById(pruebaDTO.getIdEmpleado());
            // Verificar si el interesado existe
            if (optionalInteresado.isEmpty()) {
                throw new IllegalArgumentException("Interesado con ID " + pruebaDTO.getIdInteresado() + " no encontrado.");
            }
            if (optionalEmpleado.isEmpty()) {
                throw new IllegalArgumentException("Empleado con ID " + pruebaDTO.getIdEmpleado() + " no encontrado.");
            }
            // Obtener el objeto Interesado y empleado del Optional
            Empleado empleado = optionalEmpleado.get();
            Interesado interesado = optionalInteresado.get();
            // Parsear la fecha de vencimiento desde String a LocalDate

            LocalDateTime fechaHoraActual = LocalDateTime.now();
            LocalDateTime fechaHoraFinal = LocalDateTime.now().plusHours(1);
            LocalDate fechaActual = LocalDate.now();

            // Verificar si la licencia está vencida

            if (interesado.getFechaVencimientoLicencia().isBefore(fechaActual)) {
                throw new RuntimeException("La licencia del interesado está vencida.");
            }

            // Verificar si la Interesado restringido
            if (interesado.isRestringido()) {
                throw new RuntimeException("El Cliente esta restringido");
            }

            try {
                RestTemplate template = new RestTemplate();

                ResponseEntity<VehiculoDTO> res = template.getForEntity(
                        "api/vehiculo/{id}", VehiculoDTO.class, pruebaDTO.getIdVehiculo()
                );

                if (res.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Se encontró exitosamente");
                } else {
                    throw new RuntimeException("El vehiculo no existe");
                }
                ;


            } catch (HttpClientErrorException ex) {
                // La repuesta no es exitosa.
                throw new RuntimeException("No exitoso la respuesta del cliente", ex);
            }

            //verificacion Vehiculo
            List<Prueba> listapruebaVehiculo = pruebarepository.findAllByIdVehiculo(pruebaDTO.getIdVehiculo());
            if (listapruebaVehiculo.stream().filter(prueba -> prueba.getFechaHoraFin().isBefore(fechaHoraActual)).count() != 0) {
                throw new RuntimeException("El Vehiculo esta siendo usado en otra prueba");
            }

            // Crear la entidad Prueba, Tiene el argsContructor recordar usar eso
            Prueba prueba = new Prueba();
            prueba.setInteresado(interesado);
            prueba.setEmpleado(empleado);
            prueba.setIdVehiculo(pruebaDTO.getIdVehiculo());
            prueba.setFechaHoraInicio(fechaHoraActual);
            prueba.setFechaHoraFin(fechaHoraFinal);
            prueba.setComentarios(pruebaDTO.getComentario());

            // Setear otros atributos según corresponda...

            // Guardar en la base de datos y devolver la entidad guardada
            pruebarepository.save(prueba);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
