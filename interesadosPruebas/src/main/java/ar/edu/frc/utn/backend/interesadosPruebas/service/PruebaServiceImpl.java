package ar.edu.frc.utn.backend.interesadosPruebas.service;

import ar.edu.frc.utn.backend.interesadosPruebas.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Interesado;
import ar.edu.frc.utn.backend.interesadosPruebas.entities.Prueba;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.InteresadoRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.repository.PruebaRepository;
import ar.edu.frc.utn.backend.interesadosPruebas.service.interfaces.PruebaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class PruebaServiceImpl implements PruebaService {

    private final PruebaRepository pruebarepository;
    private final InteresadoRepository interesadorepository;

    // Constructor con inyección de dependencias
    public PruebaServiceImpl(PruebaRepository pruebarepository, InteresadoRepository interesadorepository) {
        this.pruebarepository = pruebarepository;
        this.interesadorepository = interesadorepository;
    }

    @Override
    public Prueba crearPrueba(PruebaDTO pruebaDTO) throws Exception {
        try {
            // Buscar el interesado por ID
            Optional<Interesado> optionalInteresado = interesadorepository.findById(pruebaDTO.getIdInteresado());

            // Verificar si el interesado existe
            if (optionalInteresado.isEmpty()) {
                throw new IllegalArgumentException("Interesado con ID " + pruebaDTO.getIdInteresado() + " no encontrado.");
            }

            // Obtener el objeto Interesado del Optional
            Interesado interesado = optionalInteresado.get();

            // Formato de la fecha (ajusta si es necesario)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Parsear la fecha de vencimiento desde String a LocalDate
            LocalDate fechaVencimientoLicencia;
            try {
                fechaVencimientoLicencia = LocalDate.parse(interesado.getFechaVencimientoLicencia(), formatter);
            }catch (DateTimeParseException e) {
                throw new RuntimeException("Formato de fecha inválido: " + interesado.getFechaVencimientoLicencia());
            }

            // Verificar si la licencia está vencida
            LocalDate fechaActual = LocalDate.now();
            if (fechaVencimientoLicencia.isBefore(fechaActual)) {
                throw new RuntimeException("La licencia del interesado está vencida.");
            }

            // Verificar si la Interesado restringido



            // Crear la entidad Prueba, Tiene el argsContructor recordar usar eso
            Prueba prueba = new Prueba();//<------- USAR ARGSCONTRUCTOR

            // Setear otros atributos según corresponda...

            // Guardar en la base de datos y devolver la entidad guardada
            return pruebarepository.save(prueba);

        } catch (Exception e) {
            // Captura cualquier excepción y lanza una nueva manteniendo la causa original
            throw new Exception("No se puede crear la prueba", e);
        }
    }
}
