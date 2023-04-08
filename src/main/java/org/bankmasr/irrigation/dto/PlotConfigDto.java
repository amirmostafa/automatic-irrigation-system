package org.bankmasr.irrigation.dto;

import lombok.Data;
import org.quartz.DateBuilder;

import java.time.LocalDateTime;

@Data
public class PlotConfigDto extends AbstractDto {

    private Double amountOfWater;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private DateBuilder.IntervalUnit intervalUnit;
    private Integer timeInterval;
}
