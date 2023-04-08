package org.bankmasr.irrigation.services;

import org.bankmasr.irrigation.dto.IntegrationRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class IntegrationServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private IntegrationService integrationService;

    @Test
    public void testSendToDeviceSuccess() {
        Long plotId = 1L;
        Double amountOfWater = 10.0;
        String deviceUrl = "http://example.com/device";
        ReflectionTestUtils.setField(integrationService, "deviceUrl", "http://example.com/device");
        IntegrationRequestDto requestDto = new IntegrationRequestDto(plotId, amountOfWater);
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(true, HttpStatus.OK);

        given(restTemplate.exchange(deviceUrl, HttpMethod.POST, new HttpEntity<>(requestDto), Boolean.class))
                .willReturn(responseEntity);

        Boolean result = integrationService.sendToDevice(plotId, amountOfWater);

        assertTrue(result);
    }

    @Test
    public void testSendToDeviceFailure() {
        Long plotId = 1L;
        Double amountOfWater = 10.0;
        String deviceUrl = "http://example.com/device";
        ReflectionTestUtils.setField(integrationService, "deviceUrl", "http://example.com/device");
        IntegrationRequestDto requestDto = new IntegrationRequestDto(plotId, amountOfWater);
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        given(restTemplate.exchange(deviceUrl, HttpMethod.POST, new HttpEntity<>(requestDto), Boolean.class))
                .willReturn(responseEntity);

        Boolean result = integrationService.sendToDevice(plotId, amountOfWater);

        assertFalse(result);
    }
}