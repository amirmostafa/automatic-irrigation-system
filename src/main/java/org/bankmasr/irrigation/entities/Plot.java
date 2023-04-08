package org.bankmasr.irrigation.entities;


import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Plot extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @Column
    private String location;
    @Column
    private String crop;
    @Column
    private double area;
    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlotConfig> plotConfig = new ArrayList<>();
    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Irrigation> irrigations = new ArrayList<>();

}
