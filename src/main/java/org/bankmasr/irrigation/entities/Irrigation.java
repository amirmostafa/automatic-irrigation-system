package org.bankmasr.irrigation.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bankmasr.irrigation.utils.IrrigationStatuses;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Irrigation extends AbstractEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IrrigationStatuses status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plot_id", nullable = false)
    private Plot plot;

    public Irrigation(IrrigationStatuses status, Plot plot) {
        this.status = status;
        this.plot = plot;
    }
}