package edu.utn.climaAlert.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataClima {

    private String ciudad;
    private double temperatura;
    private double humedad;
    private String descripcion;
    private LocalDateTime fechaConsulta;
}
