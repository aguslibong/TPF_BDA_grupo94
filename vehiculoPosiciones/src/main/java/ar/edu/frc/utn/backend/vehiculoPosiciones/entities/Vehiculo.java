package ar.edu.frc.utn.backend.vehiculoPosiciones.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Vehiculos")
public class Vehiculo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "PATENTE", nullable = false)
    private String patente;

    @ManyToOne
    @JoinColumn(name = "ID_MODELO", nullable = false)
    private Modelo modelo;

    public Vehiculo(String patente) {
        this.patente = patente;
    }
}
