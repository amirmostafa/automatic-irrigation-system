package org.bankmasr.irrigation.repositories;

import org.bankmasr.irrigation.entities.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findByCropAndArea(String crop, double area);
}
