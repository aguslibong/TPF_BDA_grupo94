package ar.edu.frc.utn.backend.vehiculosPosiciones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Posiciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_VEHICULO")
    private Vehiculo vehiculo;

    @Column(name = "FECHA_HORA", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "LATITUD", nullable = false)
    private double latitud;

    @Column(name = "LONGITUD", nullable = false)
    private double longitud;
}
