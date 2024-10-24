package ar.edu.frc.utn.backend.vehiculoPosiciones.service;

import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.PosicionDTO;
import ar.edu.frc.utn.backend.vehiculoPosiciones.DTO.PruebaDTO;
import ar.edu.frc.utn.backend.vehiculoPosiciones.entities.Posicion;
import ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces.PosicionService;
import ar.edu.frc.utn.backend.vehiculoPosiciones.service.interfaces.Servicio;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PosicionImp extends ServicioImp<PosicionDTO, Integer> implements PosicionService {

   public Posicion corroborar(PosicionDTO posicion) {
      //Esto es la API que consumimos para que nos devuelva el listado
      //http://localhost:8082/api/prueba/momento
       try {
           if(!estaEnPrueba(posicion.getId_vehiculo())){
               throw new RuntimeException("El vehiculo no se encuentra en Prueba");
           }


       } catch (RuntimeException e) {
           throw new RuntimeException(e);
       }

   }
//aca le pasamos la posicion del auto
   private boolean estaEnPrueba(int IdVehiculo){
         try {
            RestTemplate template = new RestTemplate();

            ResponseEntity<PruebaDTO[]> res = template.getForEntity("http://localhost:8082/api/prueba/momento", PruebaDTO[].class);

            if (!res.getStatusCode().is2xxSuccessful()) {
               throw new RuntimeException("Error al llamar la API");
            }

            List<PruebaDTO> listaPruebas = Arrays.asList(res.getBody());

            if (listaPruebas.stream().filter(p -> p.getIdVehiculo() == IdVehiculo).count() != 0){
                return true;
            } else {
                return false;
            }


         } catch (HttpClientErrorException ex) {
            throw new RuntimeException("No exitoso la respuesta del cliente", ex);
         }
   }
    //aca recibimos los datos de la poscion y el radio
    private boolean estaEnRadio(PosicionDTO posicionDTO, float radioLimite) {
        // Obtén las coordenadas (catetos)
        float latitud = posicionDTO.getLatitud();
        float longitud = posicionDTO.getLongitud();

        // Calcula la distancia desde el origen (hipotenusa)
        double distancia = Math.sqrt(Math.pow(latitud, 2) + Math.pow(longitud, 2));

        // Verifica si la distancia está dentro del radio límite
        if (distancia <= radioLimite){
            return true;
        }
        else {
            return false;
        }
    }



    @Override
    public void create(PosicionDTO posicionDTO) {

    }

    @Override
    public void update(PosicionDTO posicionDTO) {

    }

    @Override
    public PosicionDTO delete(Integer id) {
        return null;
    }

    @Override
    public boolean existById(Integer id) {
        return false;
    }

    @Override
    public PosicionDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<PosicionDTO> findAll() {
        return List.of();
    }
}
