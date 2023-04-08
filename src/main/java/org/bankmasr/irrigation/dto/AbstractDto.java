package org.bankmasr.irrigation.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AbstractDto {
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}
