package ar.edu.frc.utn.backend.vehiculosPosiciones.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NotificacionPromocion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionPromocion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    @Column(name = "NUMERO", nullable = false)
    private String telefono;
    @Column(name = "MENSAJE", nullable = false)
    private String mensaje;

}
