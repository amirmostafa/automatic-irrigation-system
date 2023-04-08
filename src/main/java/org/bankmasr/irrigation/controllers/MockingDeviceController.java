package org.bankmasr.irrigation.controllers;

import org.bankmasr.irrigation.dto.IntegrationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class MockingDeviceController {

    @PostMapping
    public ResponseEntity<Boolean> irrigatePlot(@RequestBody IntegrationRequestDto integrationRequestDto) throws InterruptedException {
        Thread.sleep(1000);
        return ResponseEntity.ok(true);
    }
}
