package edu.utn.climaAlert.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataClimaDTO {

    @JsonProperty("location")
    private Ubicacion ubicacion;

    @JsonProperty("current")
    private ClimaActual climaActual;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Ubicacion {
        @JsonProperty("name")
        private String ciudad;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ClimaActual {
        @JsonProperty("temp_c")
        private double temperatura;

        @JsonProperty("humidity")
        private double humedad;

        @JsonProperty("condition")
        private Condicion condicion;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Condicion {
        @JsonProperty("text")
        private String descripcion;
    }
}


