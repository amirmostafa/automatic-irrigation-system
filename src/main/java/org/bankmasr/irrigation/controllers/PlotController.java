package org.bankmasr.irrigation.controllers;

import org.bankmasr.irrigation.dto.PlotConfigDto;
import org.bankmasr.irrigation.dto.PlotDetailsDto;
import org.bankmasr.irrigation.dto.PlotDto;
import org.bankmasr.irrigation.services.PlotConfigService;
import org.bankmasr.irrigation.services.PlotService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plots")
@CrossOrigin
public class PlotController {
    @Autowired
    private PlotService plotService;
    @Autowired
    private PlotConfigService plotConfigService;

    @GetMapping
    public ResponseEntity<List<PlotDto>> getAllPlots() {
        return ResponseEntity.ok(plotService.getAllPlots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlotDetailsDto> getPlot(@PathVariable Long id) {
        return ResponseEntity.ok(plotService.getPlotDetails(id));
    }

    @PostMapping
    public ResponseEntity<PlotDto> create(@RequestBody PlotDto plot) {
        return ResponseEntity.ok(plotService.create(plot));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlotDto> update(@PathVariable Long id, @RequestBody PlotDto plot) {
        return ResponseEntity.ok(plotService.update(id, plot));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlotDetailsDto> configure(@PathVariable Long id, @RequestBody PlotConfigDto plot) throws SchedulerException {
        return ResponseEntity.ok(plotConfigService.configure(id, plot));
    }

    @PostMapping("removePlotConfig/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) throws SchedulerException {
        plotConfigService.removePlotConfig(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws SchedulerException {
        plotService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}