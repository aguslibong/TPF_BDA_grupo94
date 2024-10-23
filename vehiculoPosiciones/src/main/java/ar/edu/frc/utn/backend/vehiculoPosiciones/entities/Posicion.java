package ar.edu.frc.utn.backend.vehiculoPosiciones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Posiciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculo idVehiculo;

    @Column(name = "FECHA_HORA", nullable = false)
    private String fechaHora;

    @Column(name = "LATITUD", nullable = false)
    private float latitud;

    @Column(name = "LONGITUD", nullable = false)
    private float longitud;
}
