package ar.edu.utn.frc.backend.TPF_BDA.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Modelos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "ID_MARCA", nullable = false)
    private Marca idMarca;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
}
