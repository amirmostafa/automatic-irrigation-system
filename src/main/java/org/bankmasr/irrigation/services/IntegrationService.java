package org.bankmasr.irrigation.services;

import lombok.extern.slf4j.Slf4j;
import org.bankmasr.irrigation.dto.IntegrationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class IntegrationService {

    @Value("${sensor.device.url}")
    private String deviceUrl;

    @Autowired
    private RestTemplate restTemplate;

    public Boolean sendToDevice(Long plotId, Double amountOfWater) {
        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(deviceUrl, HttpMethod.POST, new HttpEntity<>(new IntegrationRequestDto(plotId, amountOfWater)), Boolean.class);
            if(response.getStatusCode() == HttpStatus.OK) {
                log.info("request sent successfully");
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("error while sending to device", e);
            return false;
        }
        return false;
    }
}
