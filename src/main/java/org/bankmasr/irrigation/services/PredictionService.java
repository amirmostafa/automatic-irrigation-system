package org.bankmasr.irrigation.services;

import org.bankmasr.irrigation.dto.PlotConfigDto;
import org.bankmasr.irrigation.dto.PlotDetailsDto;
import org.bankmasr.irrigation.entities.Plot;
import org.bankmasr.irrigation.entities.Prediction;
import org.bankmasr.irrigation.mappers.PredictionMapper;
import org.bankmasr.irrigation.repositories.PredictionRepository;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;
    @Autowired
    private PredictionMapper predictionMapper;

    @Autowired
    private PlotService plotService;

    @Autowired
    private PlotConfigService configService;


    public PlotDetailsDto predict(Long id) throws SchedulerException {
        Plot plot = plotService.getPlot(id);
        List<Prediction> predictions = predictionRepository.findByCropAndArea(plot.getCrop(), plot.getArea());
        return configService.configure(plot.getId(), predictionMapper.toPlotConfigDtoList(predictions));
    }
}
