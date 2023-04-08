package org.bankmasr.irrigation.services;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SchedulerJob implements Job {

    @Autowired
    private IrrigationService irrigationService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Long plotConfigId = dataMap.getLong("plotConfigId");

        log.info("start executing job for plotConfig id: {}", plotConfigId);
        irrigationService.irrigatePlot(plotConfigId);
        log.info("finished executing job for plotConfig id: {}", plotConfigId);
    }
}
