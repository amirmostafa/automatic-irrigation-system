package org.bankmasr.irrigation.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlotDetailsDto extends PlotDto {
    private List<IrrigationDto> irrigations = new ArrayList<>();
    private List<PlotConfigDto> plotConfig = new ArrayList<>();
}
