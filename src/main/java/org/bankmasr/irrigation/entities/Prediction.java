package org.bankmasr.irrigation.entities;


import lombok.Getter;
import lombok.Setter;
import org.quartz.DateBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Prediction {
    @Id
    private Long id;
    @Column
    private String crop;
    @Column
    private double area;
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
