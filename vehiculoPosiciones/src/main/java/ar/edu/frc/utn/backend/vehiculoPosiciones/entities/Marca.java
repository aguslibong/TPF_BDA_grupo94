package ar.edu.utn.frc.backend.TPF_BDA.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="Marcas")
@Data
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    public Marca(String nombre) {
        this.nombre = nombre;
    }
}
