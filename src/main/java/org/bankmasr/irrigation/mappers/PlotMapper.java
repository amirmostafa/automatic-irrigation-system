package org.bankmasr.irrigation.mappers;

import org.bankmasr.irrigation.dto.IrrigationDto;
import org.bankmasr.irrigation.dto.PlotConfigDto;
import org.bankmasr.irrigation.dto.PlotDetailsDto;
import org.bankmasr.irrigation.dto.PlotDto;
import org.bankmasr.irrigation.entities.Irrigation;
import org.bankmasr.irrigation.entities.Plot;
import org.bankmasr.irrigation.entities.PlotConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlotMapper {

    @Named("toDetailsDto")
    PlotDetailsDto toDetailsDto(Plot plot);

    PlotDto toDto(Plot plot);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    Plot toEntity(PlotDto plotDto);

    List<PlotDto> toDtoList(List<Plot> plots);


    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    List<Plot> toEntityList(List<PlotDto> plotDtos);

    IrrigationDto toIrrigationDto(Irrigation irrigation);

    PlotConfigDto toPlotConfigDto(PlotConfig plotConfig);

}