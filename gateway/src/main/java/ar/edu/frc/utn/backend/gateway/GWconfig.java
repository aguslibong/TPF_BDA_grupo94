package ar.edu.frc.utn.backend.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWconfig {
    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${microservicios.vehiculos}") String uriVehiculos,
                                        @Value("${microservicios.interesados}") String uriInteresados,
                                        @Value("${microservicios.notificaciones}") String uriNotificaciones) {
        return builder.routes()
                // Ruteo al Microservicio de interesados
                .route(p -> p.path("/api/prueba/**").uri(uriInteresados))
                // Ruteo al Microservicio de vehiculos
                .route(p -> p.path("/api/interesado/**").uri(uriInteresados))
                .route(p -> p.path("/api/empleado/**").uri(uriInteresados))
                .route(p -> p.path("/api/vehiculo/**").uri(uriVehiculos))
                .route(p -> p.path("/api/notificacion/**").uri(uriNotificaciones))
                .build();
    }
}
