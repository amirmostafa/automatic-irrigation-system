package org.bankmasr.irrigation.dto;

import lombok.Data;


@Data
public class PlotDto extends AbstractDto {
    private String name;
    private String location;
    private String crop;
    private double area;
}
