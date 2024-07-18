package com.icodeap.ecommerce.backend.infrastructure.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.icodeap.ecommerce.backend.domain.model.MailMessage;
import com.icodeap.ecommerce.backend.domain.port.IMailSenderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailSenderBrevoService implements IMailSenderService {
	public void sendSimpleMessage(MailMessage message) {
        String apiKey = ""; // Reemplaza con tu API Key de Brevo
        String senderEmail = "edgar.mh.0307@gmail.com"; // Reemplaza con una dirección verificada en Brevo
        String recipientEmail = message.getTo();
        String subject = message.getTitle();
        String htmlContent = message.getMessage();

        // Crear el JSON del correo
        JSONObject emailData = new JSONObject();
        emailData.put("sender", new JSONObject().put("email", senderEmail).put("name", "edgar.mh.0307@gmail.com"));
        emailData.put("to", new JSONArray().put(new JSONObject().put("email", recipientEmail).put("name", "Cliente")));
        emailData.put("subject", subject);
        emailData.put("htmlContent", htmlContent);

        try {
            // Crear el cliente HTTP
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.sendinblue.com/v3/smtp/email"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("api-key", apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(emailData.toString()))
                    .build();

            // Enviar la solicitud
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Imprimir la respuesta
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            if (response.statusCode() == 201) {
                System.out.println("Correo enviado con éxito");
            } else {
                System.out.println("Error al enviar el correo");
            }
        } catch (Exception e) {
           log.error(e.getMessage());
        }
	}
}
