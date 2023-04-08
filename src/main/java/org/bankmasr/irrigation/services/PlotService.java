package org.bankmasr.irrigation.services;

import org.bankmasr.irrigation.dto.PlotDetailsDto;
import org.bankmasr.irrigation.dto.PlotDto;
import org.bankmasr.irrigation.entities.Plot;
import org.bankmasr.irrigation.mappers.PlotMapper;
import org.bankmasr.irrigation.repositories.PlotConfigRepository;
import org.bankmasr.irrigation.repositories.PlotRepository;
import org.bankmasr.irrigation.utils.Constants;
import org.bankmasr.irrigation.utils.QuartzUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlotService {
    @Autowired
    private PlotRepository plotRepository;
    @Autowired
    private PlotConfigRepository plotConfigRepository;

    @Autowired
    private PlotMapper plotMapper;

    @Autowired
    private QuartzUtils quartzUtils;

    public List<PlotDto> getAllPlots() {
        List<Plot> plots = plotRepository.findAll();
        return plotMapper.toDtoList(plots);
    }

    public PlotDetailsDto getPlotDetails(Long id) {
        Plot plot = getPlot(id);
        return plotMapper.toDetailsDto(plot);
    }

    public PlotDto create(PlotDto plotDto) {
        validatePlotName(plotDto.getName(), null);
        Plot plot = plotMapper.toEntity(plotDto);
        Plot savedPlot = plotRepository.save(plot);
        return plotMapper.toDto(savedPlot);
    }

    public PlotDto update(Long id, PlotDto plotDto) {
        Plot plot = getPlot(id);
        validatePlotName(plotDto.getName(), id);
        plot.setName(plotDto.getName());
        plot.setArea(plotDto.getArea());
        plot.setCrop(plotDto.getCrop());
        plot.setLocation(plotDto.getLocation());
        plot.setModificationDate(null);
        Plot updatedPlot = plotRepository.save(plot);
        return plotMapper.toDto(updatedPlot);
    }

    public void deleteById(Long id) throws SchedulerException {
        Plot plot = getPlot(id);
        quartzUtils.deleteJobs(plot.getPlotConfig());
        plotRepository.delete(plot);
    }

    public Plot getPlot(Long id) {
        return plotRepository.findById(id).orElseThrow(() -> new RuntimeException(Constants.PLOT_NOT_FOUND));
    }

    private void validatePlotName(String name, Long id) {
        if(id == null) {
            if(plotRepository.existsByName(name)) {
                throw new RuntimeException(Constants.PLOT_NAME_ALREADY_EXISTS);
            }
        } else {
            if(plotRepository.existsByNameAndIdNot(name, id)) {
                throw new RuntimeException(Constants.PLOT_NAME_ALREADY_EXISTS);
            }
        }
    }
}
