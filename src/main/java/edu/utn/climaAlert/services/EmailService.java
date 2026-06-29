package edu.utn.climaAlert.services;

import edu.utn.climaAlert.models.DataClima;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private static final String[] DESTINATARIOS = {
            "admin@clima.com",
            "emergencias@clima.com",
            "meteorologia@clima.com"
    };

    public void enviarAlerta(DataClima clima) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(DESTINATARIOS);
        mensaje.setSubject("⚠️ Alerta Climática - " + clima.getCiudad());
        mensaje.setText(construirCuerpo(clima));
        mailSender.send(mensaje);
    }

    private String construirCuerpo(DataClima clima) {
        return """
                ⚠️ ALERTA CLIMÁTICA DETECTADA
                
                Ciudad: %s
                Temperatura: %.1f°C
                Humedad: %.1f%%
                Condición: %s
                Fecha y hora: %s
                
                Por favor tome las medidas necesarias.
                """.formatted(
                clima.getCiudad(),
                clima.getTemperatura(),
                clima.getHumedad(),
                clima.getDescripcion(),
                clima.getFechaConsulta()
        );
    }
}