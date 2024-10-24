package ar.edu.frc.utn.backend.vehiculoPosiciones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="TELEFONO", nullable = false)
    private int telefono;

    @Column(name="MENSAJE", nullable = false)
    private String mensaje;

}
