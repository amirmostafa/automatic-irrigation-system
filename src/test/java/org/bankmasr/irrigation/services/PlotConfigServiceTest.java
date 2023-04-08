package org.bankmasr.irrigation.services;

import org.bankmasr.irrigation.dto.PlotConfigDto;
import org.bankmasr.irrigation.dto.PlotDetailsDto;
import org.bankmasr.irrigation.dto.PlotDto;
import org.bankmasr.irrigation.entities.Plot;
import org.bankmasr.irrigation.entities.PlotConfig;
import org.bankmasr.irrigation.mappers.PlotMapper;
import org.bankmasr.irrigation.repositories.PlotConfigRepository;
import org.bankmasr.irrigation.repositories.PlotRepository;
import org.bankmasr.irrigation.utils.QuartzUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.SchedulerException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PlotConfigServiceTest {

    @Mock
    private PlotRepository plotRepository;

    @Mock
    private PlotService plotService;

    @Mock
    private PlotConfigRepository plotConfigRepository;

    @Mock
    private PlotMapper plotMapper;

    @Mock
    private QuartzUtils quartzUtils;

    @InjectMocks
    private PlotConfigService plotConfigService;

    @Test
    public void testConfigure() throws SchedulerException {
        
        Long id = 1L;
        PlotConfigDto plotConfigDto = new PlotConfigDto();

        Plot plot = new Plot();
        given(plotService.getPlot(id)).willReturn(plot);

        Plot updatedPlot = new Plot();
        given(plotRepository.save(plot)).willReturn(updatedPlot);

        PlotDetailsDto updatedPlotDto = new PlotDetailsDto();
        given(plotMapper.toDetailsDto(updatedPlot)).willReturn(updatedPlotDto);

        PlotDto result = plotConfigService.configure(id, plotConfigDto);

        assertEquals(updatedPlotDto, result);
    }

    @Test
    public void testRemovePlotConfig() throws Exception {
        Plot plot = new Plot();
        plot.setId(1L);
        PlotConfig plotConfig = new PlotConfig();
        plotConfig.setId(2L);
        plotConfig.setPlot(plot);
        plot.getPlotConfig().add(plotConfig);
        given(plotConfigRepository.findById(2L)).willReturn(Optional.of(plotConfig));

        plotConfigService.removePlotConfig(2L);

        verify(plotRepository, times(1)).save(plot);
        verify(quartzUtils, times(1)).deleteJob(2L);
    }
}
