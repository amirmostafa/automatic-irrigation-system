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
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PlotConfigService {
    @Autowired
    private PlotService plotService;
    @Autowired
    private PlotConfigRepository plotConfigRepository;
    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private PlotMapper plotMapper;

    @Autowired
    private QuartzUtils quartzUtils;

    public PlotDetailsDto configure(Long id, PlotConfigDto plotConfigDto) throws SchedulerException {
        Plot plot = plotService.getPlot(id);
        PlotConfig plotConfig = plotConfigRepository.save(constructPlotConfig(plotConfigDto, plot));
        plot.getPlotConfig().add(plotConfig);
        plot = plotRepository.save(plot);
        quartzUtils.startJob(plotConfig);
        return plotMapper.toDetailsDto(plot);
    }

    public PlotDetailsDto configure(Long id, List<PlotConfigDto> plotConfigDtos) throws SchedulerException {
        Plot plot = plotService.getPlot(id);
        for(PlotConfigDto plotConfigDto: plotConfigDtos) {
            PlotConfig plotConfig = plotConfigRepository.save(constructPlotConfig(plotConfigDto, plot));
            plot.getPlotConfig().add(plotConfig);
            quartzUtils.startJob(plotConfig);
        }
        plot = plotRepository.save(plot);
        return plotMapper.toDetailsDto(plot);
    }

    public PlotConfig getPlotConfig(Long plotConfigId) {
        return plotConfigRepository.findById(plotConfigId).orElseThrow(() -> new RuntimeException(Constants.PLOT_CONFIG_NOT_FOUND));
    }

    @Transactional
    public void removePlotConfig(Long plotConfigId) throws SchedulerException {
        PlotConfig plotConfig = getPlotConfig(plotConfigId);
        Plot plot = plotConfig.getPlot();
        plot.getPlotConfig().remove(plotConfig);
        plotRepository.save(plot);
        quartzUtils.deleteJob(plotConfig.getId());
    }

    private PlotConfig constructPlotConfig(PlotConfigDto plotDto, Plot plot) {
        PlotConfig plotConfig = new PlotConfig();
        plotConfig.setAmountOfWater(plotDto.getAmountOfWater());
        plotConfig.setStartTime(plotDto.getStartTime());
        plotConfig.setEndTime(plotDto.getEndTime());
        plotConfig.setIntervalUnit(plotDto.getIntervalUnit());
        plotConfig.setTimeInterval(plotDto.getTimeInterval());
        plotConfig.setPlot(plot);
        return plotConfig;
    }
}
