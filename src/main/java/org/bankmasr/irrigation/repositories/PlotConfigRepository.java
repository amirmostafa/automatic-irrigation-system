package org.bankmasr.irrigation.repositories;

import org.bankmasr.irrigation.entities.PlotConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlotConfigRepository extends JpaRepository<PlotConfig, Long> {
}
