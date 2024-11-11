package ar.edu.frc.utn.backend.interesadosPruebas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Pruebas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prueba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "ID_VEHICULO")
    private int idVehiculo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_INTERESADO")
    private Interesado Interesado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_EMPLEADO")
    private Empleado Empleado;

    @Column(name = "FECHA_HORA_INICIO", nullable = false)
    private LocalDateTime fechaHoraInicio;

    @Column(name = "FECHA_HORA_FIN", nullable = false)
    private LocalDateTime fechaHoraFin;

    @Column(name = "COMENTARIOS", nullable = false)
    private String comentarios;

    @Column(name = "INCIDENTE", nullable = false)
    private boolean incidente ;

    public boolean isIncidente() {
        return incidente;
    }

    public void setIncidente(boolean incidente) {
        this.incidente = incidente;
    }
}
