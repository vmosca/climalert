package edu.utn.climaAlert.services;

import edu.utn.climaAlert.dto.DataClimaDTO;
import edu.utn.climaAlert.models.DataClima;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClimaService {

    private final RestTemplate restTemplate;
    private final EmailService emailService;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.city}")
    private String ciudad;

    private static final double TEMPERATURA_LIMITE = 35.0;
    private static final double HUMEDAD_LIMITE = 60.0;


    @Getter
    private DataClima ultimoClima;
    private final List<DataClima> historialClima = new ArrayList<>();
    @Scheduled(fixedRate = 300000) // cada 5 minutos
    public void actualizarClima() {
        String url = apiUrl + "?key=" + apiKey + "&q=" + ciudad;
        DataClimaDTO dto = restTemplate.getForObject(url, DataClimaDTO.class);

        if (dto != null) {
            ultimoClima = new DataClima(
                    dto.getUbicacion().getCiudad(),
                    dto.getClimaActual().getTemperatura(),
                    dto.getClimaActual().getHumedad(),
                    dto.getClimaActual().getCondicion().getDescripcion(),
                    LocalDateTime.now()
            );
            historialClima.add(ultimoClima);
        }
    }

    @Scheduled(fixedRate = 60000) // cada 1 minuto
    public void verificarAlerta() {
        if (ultimoClima == null) return;

        if (ultimoClima.getTemperatura() > TEMPERATURA_LIMITE &&
                ultimoClima.getHumedad() > HUMEDAD_LIMITE) {
            emailService.enviarAlerta(ultimoClima);
        }
    }

    public List<DataClima> getHistorialClima() {
        return Collections.unmodifiableList(historialClima);
    }

    public void simularAlerta() {
        ultimoClima = new DataClima(
                "Buenos Aires",
                36.0,   // temperatura mayor a 35°
                65.0,   // humedad mayor a 60%
                "Calor extremo",
                LocalDateTime.now()
        );
        historialClima.add(ultimoClima);
        emailService.enviarAlerta(ultimoClima);
    }
}