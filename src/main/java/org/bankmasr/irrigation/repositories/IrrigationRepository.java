package org.bankmasr.irrigation.repositories;

import org.bankmasr.irrigation.entities.Irrigation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IrrigationRepository extends JpaRepository<Irrigation, Long> {
}
