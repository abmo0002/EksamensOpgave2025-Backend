package com.example.eksamensopgave2025backend.repository;
import com.example.eksamensopgave2025backend.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByActualDeliveryTimeIsNull();

    List<Delivery> findByDroneIsNull();
}
