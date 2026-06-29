package edu.utn.climaAlert.controllers;

import edu.utn.climaAlert.models.DataClima;
import edu.utn.climaAlert.services.ClimaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clima")
@RequiredArgsConstructor
public class ClimaController {

    private final ClimaService climaService;

    @GetMapping("/ultimo")
    public ResponseEntity<DataClima> getUltimoClima() {
        return ResponseEntity.ok(climaService.getUltimoClima());
    }

    @GetMapping("/historial")
    public ResponseEntity<List<DataClima>> getHistorial() {
        return ResponseEntity.ok(climaService.getHistorialClima());
    }

    @GetMapping("/simular-alerta")
    public ResponseEntity<String> simularAlerta() {
        climaService.simularAlerta();
        return ResponseEntity.ok("Alerta simulada enviada!");
    }
}