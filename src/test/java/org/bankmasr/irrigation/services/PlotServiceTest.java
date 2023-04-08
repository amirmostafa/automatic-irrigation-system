package org.bankmasr.irrigation.services;

import org.bankmasr.irrigation.dto.PlotConfigDto;
import org.bankmasr.irrigation.dto.PlotDetailsDto;
import org.bankmasr.irrigation.dto.PlotDto;
import org.bankmasr.irrigation.entities.Plot;
import org.bankmasr.irrigation.entities.PlotConfig;
import org.bankmasr.irrigation.entities.Prediction;
import org.bankmasr.irrigation.mappers.PlotMapper;
import org.bankmasr.irrigation.repositories.PlotConfigRepository;
import org.bankmasr.irrigation.repositories.PlotRepository;
import org.bankmasr.irrigation.repositories.PredictionRepository;
import org.bankmasr.irrigation.utils.Constants;
import org.bankmasr.irrigation.utils.QuartzUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.SchedulerException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlotServiceTest {

    @Mock
    private PlotRepository plotRepository;

    @Mock
    private PlotMapper plotMapper;

    @Mock
    private QuartzUtils quartzUtils;

    @InjectMocks
    private PlotService plotService;

    @Test
    public void testGetAllPlots() {
        
        List<Plot> plots = Arrays.asList(new Plot(), new Plot());
        given(plotRepository.findAll()).willReturn(plots);

        List<PlotDto> plotDtos = Arrays.asList(new PlotDto(), new PlotDto());
        given(plotMapper.toDtoList(plots)).willReturn(plotDtos);

        List<PlotDto> result = plotService.getAllPlots();

        assertEquals(2, result.size());
        assertEquals(plotDtos, result);
    }

    @Test
    public void testGetPlotDetails() {
        
        Plot plot = new Plot();
        given(plotRepository.findById(1L)).willReturn(Optional.of(plot));

        PlotDetailsDto plotDetailsDto = new PlotDetailsDto();
        given(plotMapper.toDetailsDto(plot)).willReturn(plotDetailsDto);

        PlotDetailsDto result = plotService.getPlotDetails(1L);

        assertEquals(plotDetailsDto, result);
    }

    @Test
    public void testCreate() {

        PlotDto plotDto = new PlotDto();
        plotDto.setName("test");
        Plot plot = new Plot();
        given(plotMapper.toEntity(plotDto)).willReturn(plot);

        Plot savedPlot = new Plot();
        given(plotRepository.save(plot)).willReturn(savedPlot);

        PlotDto savedPlotDto = new PlotDto();
        given(plotMapper.toDto(savedPlot)).willReturn(savedPlotDto);

        PlotDto result = plotService.create(plotDto);

        assertEquals(savedPlotDto, result);
    }

    @Test
    public void testCreate_existingName_throwException() {

        PlotDto plotDto = new PlotDto();
        plotDto.setName("test");

        given(plotRepository.existsByName("test")).willReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> plotService.create(plotDto));

        assertEquals(Constants.PLOT_NAME_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    public void testUpdate() {
        
        Long id = 1L;
        PlotDto plotDto = new PlotDto();
        plotDto.setName("test");
        plotDto.setArea(10);
        plotDto.setLocation("test");

        Plot plot = new Plot();
        given(plotRepository.findById(id)).willReturn(Optional.of(plot));
        given(plotRepository.save(plot)).willReturn(plot);

        PlotDto updatedPlotDto = new PlotDto();
        given(plotMapper.toDto(plot)).willReturn(updatedPlotDto);

        PlotDto result = plotService.update(id, plotDto);

        assertEquals(updatedPlotDto, result);
    }

    @Test
    public void testUpdate_existingName_throwException() {

        PlotDto plotDto = new PlotDto();
        plotDto.setName("test");

        given(plotRepository.findById(1L)).willReturn(Optional.of(new Plot()));
        given(plotRepository.existsByNameAndIdNot("test", 1L)).willReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> plotService.update(1L, plotDto));

        assertEquals(Constants.PLOT_NAME_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    public void testDeleteById() throws SchedulerException {
        Long id = 1L;
        Plot plot = new Plot();
        given(plotRepository.findById(id)).willReturn(Optional.of(plot));

        plotService.deleteById(id);

        verify(plotRepository).delete(plot);
        verify(quartzUtils).deleteJobs(plot.getPlotConfig());

    }
}
