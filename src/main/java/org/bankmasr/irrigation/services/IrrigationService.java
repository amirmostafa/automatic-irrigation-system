package org.bankmasr.irrigation.services;

import org.bankmasr.irrigation.entities.Irrigation;
import org.bankmasr.irrigation.entities.Plot;
import org.bankmasr.irrigation.entities.PlotConfig;
import org.bankmasr.irrigation.repositories.IrrigationRepository;
import org.bankmasr.irrigation.utils.IrrigationStatuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IrrigationService {
    @Autowired
    private IrrigationRepository irrigationRepository;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private PlotService plotService;
    @Autowired
    private PlotConfigService plotConfigService;

    @Autowired
    private MailService notificationService;

    @Transactional
    public void irrigatePlot(Long plotConfigId) {
        PlotConfig plotConfig = plotConfigService.getPlotConfig(plotConfigId);
        Plot plot = plotConfig.getPlot();
        // create new irrigation IN_PROGRESS
        Irrigation irrigation = new Irrigation(IrrigationStatuses.IN_PROGRESS, plot);
        irrigation = irrigationRepository.save(irrigation);

        int retryCount = 3;
        while (retryCount > 0 && irrigation.getStatus() != IrrigationStatuses.SUCCESS) {
            irrigation = irrigate(irrigation, plot.getId(), plotConfig.getAmountOfWater());
            retryCount--;
        }

        if (irrigation.getStatus() == IrrigationStatuses.FAILED) {
            notificationService.sendNotification("Irrigation failed for plot id:" + plot.getId(), "Irrigation Status");
        }

        irrigationRepository.save(irrigation);
    }


    public Irrigation irrigate(Irrigation irrigation, Long plotId, double amountOfWater) {
        try {
            // send REST API call to the sensor device to irrigate the plot
            Boolean isSuccess = integrationService.sendToDevice(plotId, amountOfWater);
            irrigation.setStatus(isSuccess ? IrrigationStatuses.SUCCESS: IrrigationStatuses.FAILED);
        } catch (Exception e) {
            irrigation.setStatus(IrrigationStatuses.FAILED);
        }
        return irrigation;
    }
}