package com.eduardo.desafiopicpay.service;

import com.eduardo.desafiopicpay.domain.entities.User;
import com.eduardo.desafiopicpay.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void createNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        ResponseEntity<String> notification = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notificationRequest, String.class);

        if (!(notification.getStatusCode() == HttpStatus.OK)){
            System.out.println("Envio para o usuário falhou");
            throw new Exception("Notification service is out");
        }
    }
}
