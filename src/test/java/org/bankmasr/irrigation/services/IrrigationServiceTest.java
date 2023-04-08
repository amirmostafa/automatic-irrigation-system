package org.bankmasr.irrigation.services;

import static org.mockito.Mockito.*;


import org.bankmasr.irrigation.entities.Irrigation;
import org.bankmasr.irrigation.entities.Plot;
import org.bankmasr.irrigation.entities.PlotConfig;
import org.bankmasr.irrigation.repositories.IrrigationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IrrigationServiceTest {

    @Mock
    private IrrigationRepository irrigationRepository;

    @Mock
    private IntegrationService integrationService;

    @Mock
    private PlotConfigService plotConfigService;

    @Mock
    private MailService notificationService;

    @InjectMocks
    private IrrigationService irrigationService;


    @Test
    public void testIrrigatePlot_Successful() throws Exception {
        Plot plot = new Plot();
        plot.setId(1L);
        PlotConfig plotConfig = new PlotConfig();
        plotConfig.setId(2L);
        plotConfig.setAmountOfWater(20.0);
        plotConfig.setPlot(plot);
        plot.getPlotConfig().add(plotConfig);

        when(plotConfigService.getPlotConfig(2L)).thenReturn(plotConfig);
        when(irrigationRepository.save(any(Irrigation.class))).thenReturn(new Irrigation());

        when(integrationService.sendToDevice(1L, 20.0)).thenReturn(true);

        irrigationService.irrigatePlot(2L);

        verify(irrigationRepository, times(2)).save(any(Irrigation.class));
        verify(notificationService, never()).sendNotification(anyString(), anyString());
    }

    @Test
    public void testIrrigatePlot_FailedAfterRetry() throws Exception {
        Plot plot = new Plot();
        plot.setId(1L);
        PlotConfig plotConfig = new PlotConfig();
        plotConfig.setId(2L);
        plotConfig.setAmountOfWater(20.0);
        plot.getPlotConfig().add(plotConfig);
        plotConfig.setPlot(plot);

        when(plotConfigService.getPlotConfig(2L)).thenReturn(plotConfig);
        when(irrigationRepository.save(any(Irrigation.class))).thenReturn(new Irrigation());
        when(integrationService.sendToDevice(1L, 20.0)).thenReturn(false);

        irrigationService.irrigatePlot(2L);

        verify(irrigationRepository, times(2)).save(any(Irrigation.class));
        verify(notificationService, times(1)).sendNotification(anyString(), anyString());
    }
}