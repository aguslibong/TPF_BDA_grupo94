package ar.edu.frc.utn.backend.interesadosPruebas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Pruebas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prueba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_VEHICULO")
    private long idVehiculo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_INTERESADO")
    private Interesado idInteresado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado idEmpleado;

    @Column(name = "FECHA_HORA_INICIO", nullable = false)
    private String fechaHoraInicio;

    @Column(name = "FECHA_HORA_FIN", nullable = false)
    private String fechaHoraFin;

    @Column(name = "COMENTARIOS", nullable = false)
    private String comentarios;
}
