package org.bankmasr.irrigation.utils;

import org.bankmasr.irrigation.entities.PlotConfig;
import org.bankmasr.irrigation.services.SchedulerJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuartzUtils {

    @Autowired
    private Scheduler scheduler;

    public void startJob(PlotConfig plotConfig) throws SchedulerException {
        if(plotConfig.getStartTime() == null) {
            return;
        }
        deleteJob(plotConfig.getId());
        JobDetail job = JobBuilder.newJob(SchedulerJob.class)
                .withIdentity("JOB_" + plotConfig.getId())
                .usingJobData("plotConfigId", plotConfig.getId())
                .build();

        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                .startAt(Date.from(plotConfig.getStartTime().atZone(ZoneId.systemDefault()).toInstant()))
                .endAt(plotConfig.getEndTime() == null ? null
                        : Date.from(plotConfig.getEndTime().atZone(ZoneId.systemDefault()).toInstant()));
        Trigger trigger = triggerBuilder.build();

        if(Arrays.asList(DateBuilder.IntervalUnit.MINUTE,  DateBuilder.IntervalUnit.HOUR).contains(plotConfig.getIntervalUnit())) {
            trigger = triggerBuilder.withSchedule(getRepeatInterval(plotConfig)).build();
        }
        scheduler.scheduleJob(job, trigger);
    }

    private DailyTimeIntervalScheduleBuilder getRepeatInterval(PlotConfig plotConfig) {
        return DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .withInterval(plotConfig.getTimeInterval(), plotConfig.getIntervalUnit());
    }

    public void deleteJob(Long id) throws SchedulerException {
        scheduler.deleteJob(new JobKey("JOB_" + id));
    }

    public void deleteJobs(List<PlotConfig> plotConfigs) throws SchedulerException {
        scheduler.deleteJobs(plotConfigs.stream().map(p -> new JobKey("JOB_" + p.getId())).collect(Collectors.toList()));
    }
}
