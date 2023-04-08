package org.bankmasr.irrigation.controllers;

import org.bankmasr.irrigation.dto.PlotDto;
import org.bankmasr.irrigation.services.IrrigationService;
import org.bankmasr.irrigation.services.PlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/irrigation")
public class IrrigationController {
    @Autowired
    private IrrigationService irrigationService;

    @PostMapping("/{plotId}")
    public ResponseEntity<Void> irrigatePlot(@PathVariable Long plotId) {
        irrigationService.irrigatePlot(plotId);
        return ResponseEntity.ok().build();
    }
}