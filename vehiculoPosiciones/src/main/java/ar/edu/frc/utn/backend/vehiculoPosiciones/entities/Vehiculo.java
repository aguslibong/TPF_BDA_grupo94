package ar.edu.frc.utn.backend.vehiculoPosiciones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Vehiculos")
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PATENTE", nullable = false)
    private String patente;

    @ManyToOne
    @JoinColumn(name = "ID_MODELO", nullable = false)
    private Modelo modelo;

    public Vehiculo(String patente) {
        this.patente = patente;
    }
}
