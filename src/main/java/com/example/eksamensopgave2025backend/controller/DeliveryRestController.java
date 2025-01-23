package com.example.eksamensopgave2025backend.controller;

import com.example.eksamensopgave2025backend.model.Delivery;
import com.example.eksamensopgave2025backend.service.DeliveryService;
import com.example.eksamensopgave2025backend.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@CrossOrigin
public class DeliveryRestController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DroneService droneService;

    @GetMapping
    @ResponseBody
    public List<Delivery> getPendingDeliveries() {
        return deliveryService.getPendingDeliveries();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDelivery(@RequestParam Long pizzaId, @RequestParam String address) {
        Delivery delivery = deliveryService.addDelivery(pizzaId, address);
        return ResponseEntity.status(201).body(delivery);
    }

    @GetMapping("/queue")
    public List<Delivery> getUnassignedDeliveries() {
        return deliveryService.getUnassignedDeliveries();
    }

    @PostMapping("/{deliveryId}/schedule")
    public ResponseEntity<?> scheduleDelivery(@PathVariable Long deliveryId, @RequestParam(required = false) Long droneId) {
        Delivery scheduledDelivery = deliveryService.scheduleDelivery(deliveryId, droneId);
        return ResponseEntity.ok(scheduledDelivery);
    }

    @PostMapping("/{deliveryId}/finish")
    public ResponseEntity<?> finishDelivery(@PathVariable Long deliveryId) {
        Delivery finishedDelivery = deliveryService.finishDelivery(deliveryId);
        return ResponseEntity.ok(finishedDelivery);
    }
}
