package org.bankmasr.irrigation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.quartz.DateBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class IntegrationRequestDto {

    private Long id;
    private double amountOfWater;
}
