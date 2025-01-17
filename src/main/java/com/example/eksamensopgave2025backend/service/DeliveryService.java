package com.example.eksamensopgave2025backend.service;

import com.example.eksamensopgave2025backend.model.Delivery;
import com.example.eksamensopgave2025backend.model.Drone;
import com.example.eksamensopgave2025backend.model.DroneStatus;
import com.example.eksamensopgave2025backend.model.Pizza;
import com.example.eksamensopgave2025backend.repository.DeliveryRepository;
import com.example.eksamensopgave2025backend.repository.DroneRepository;
import com.example.eksamensopgave2025backend.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DroneRepository droneRepository;

    public List<Delivery> getPendingDeliveries() {
        return deliveryRepository.findByActualDeliveryTimeIsNull();
    }

    public Delivery addDelivery(Long pizzaId, String address) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(pizzaId);
        if (pizzaOptional.isEmpty()) {
            throw new IllegalArgumentException("Pizza ikke fundet.");
        }

        Delivery delivery = new Delivery();
        delivery.setPizza(pizzaOptional.get());
        delivery.setAddress(address);
        delivery.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));

        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getUnassignedDeliveries() {
        return deliveryRepository.findByDroneIsNull();
    }

    public Delivery scheduleDelivery(Long deliveryId, Long droneId) {
        Optional<Delivery> deliveryOptional = deliveryRepository.findById(deliveryId);
        if (deliveryOptional.isEmpty()) {
            throw new IllegalArgumentException("Levering ikke fundet.");
        }

        Delivery delivery = deliveryOptional.get();
        if (delivery.getDrone() != null) {
            throw new IllegalStateException("Levering er allerede oprettet.");
        }

        Drone drone;
        if (droneId != null) {
            Optional<Drone> droneOptional = droneRepository.findById(droneId);
            if (droneOptional.isEmpty() || droneOptional.get().getStatus() != DroneStatus.IN_OPERATION) {
                throw new IllegalArgumentException("Ugyldig drone.");
            }
            drone = droneOptional.get();
        } else {
            drone = droneRepository.findFirstByStatus(DroneStatus.IN_OPERATION)
                    .orElseThrow(() -> new IllegalStateException("Ingen tilgængelige droner."));
        }

        delivery.setDrone(drone);
        return deliveryRepository.save(delivery);
    }

    public Delivery finishDelivery(Long deliveryId) {
        Optional<Delivery> deliveryOptional = deliveryRepository.findById(deliveryId);
        if (deliveryOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Levering ikke fundet.");
        }

        Delivery delivery = deliveryOptional.get();
        if (delivery.getDrone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Levering har ingen drone.");
        }

        delivery.setActualDeliveryTime(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }
}