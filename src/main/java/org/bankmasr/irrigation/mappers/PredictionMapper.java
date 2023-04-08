package org.bankmasr.irrigation.mappers;

import org.bankmasr.irrigation.dto.IrrigationDto;
import org.bankmasr.irrigation.dto.PlotConfigDto;
import org.bankmasr.irrigation.dto.PlotDetailsDto;
import org.bankmasr.irrigation.dto.PlotDto;
import org.bankmasr.irrigation.entities.Irrigation;
import org.bankmasr.irrigation.entities.Plot;
import org.bankmasr.irrigation.entities.PlotConfig;
import org.bankmasr.irrigation.entities.Prediction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PredictionMapper {

    PlotConfigDto toPlotConfigDto(Prediction prediction);
    List<PlotConfigDto> toPlotConfigDtoList(List<Prediction> predictions);

}