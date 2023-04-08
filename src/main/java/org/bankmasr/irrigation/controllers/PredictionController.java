package org.bankmasr.irrigation.controllers;

import org.bankmasr.irrigation.dto.PlotConfigDto;
import org.bankmasr.irrigation.dto.PlotDetailsDto;
import org.bankmasr.irrigation.dto.PlotDto;
import org.bankmasr.irrigation.services.PlotService;
import org.bankmasr.irrigation.services.PredictionService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/predict")
@CrossOrigin
public class PredictionController {
    @Autowired
    private PredictionService predictionService;

    @GetMapping("{id}")
    public ResponseEntity<PlotDetailsDto> predict(@PathVariable Long id) throws SchedulerException {
        return ResponseEntity.ok(predictionService.predict(id));
    }

}