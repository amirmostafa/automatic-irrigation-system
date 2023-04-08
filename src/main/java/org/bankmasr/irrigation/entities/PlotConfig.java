package org.bankmasr.irrigation.entities;


import lombok.Getter;
import lombok.Setter;
import org.quartz.DateBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class PlotConfig extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "plot_id", nullable = false)
    private Plot plot;

    @Column
    private Double amountOfWater;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    @Enumerated(EnumType.STRING)
    private DateBuilder.IntervalUnit intervalUnit;

    @Column
    private Integer timeInterval;
}
