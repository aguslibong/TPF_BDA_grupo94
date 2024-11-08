package ar.edu.frc.utn.backend.notificaciones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionEmpleado {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "LEGAJO_EMPLEADO")
    private int legajoEmpleado;

    @Column(name="TELEFONO", nullable = false)
    private String telefono;

    @Column(name="MENSAJE", nullable = false)
    private String mensaje;

}
