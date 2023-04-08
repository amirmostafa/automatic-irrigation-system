package org.bankmasr.irrigation.dto;

import lombok.Data;
import org.bankmasr.irrigation.utils.IrrigationStatuses;


@Data
public class IrrigationDto extends AbstractDto {
    private IrrigationStatuses status;
}
