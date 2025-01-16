package com.example.eksamensopgave2025backend.controller;

import com.example.eksamensopgave2025backend.model.Drone;
import com.example.eksamensopgave2025backend.model.DroneStatus;
import com.example.eksamensopgave2025backend.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drones")
@CrossOrigin(origins = "http://localhost:63342")
public class DroneRestController {

    @Autowired
    private DroneService droneService;

    @GetMapping
    public List<Drone> getAllDrones() {
        return droneService.getAllDrones();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDrone() {
        Drone newDrone = droneService.addDrone();
        return ResponseEntity.status(201).body(newDrone);
    }

    @PostMapping("/{id}/enable")
    public ResponseEntity<?> enableDrone(@PathVariable Long id) {
        return changeDroneStatus(id, DroneStatus.IN_OPERATION);
    }

    @PostMapping("/{id}/disable")
    public ResponseEntity<?> disableDrone(@PathVariable Long id) {
        return changeDroneStatus(id, DroneStatus.OUT_OF_OPERATION);
    }

    @PostMapping("/{id}/retire")
    public ResponseEntity<?> retireDrone(@PathVariable Long id) {
        return changeDroneStatus(id, DroneStatus.PHASED_OUT);
    }

    private ResponseEntity<?> changeDroneStatus(Long id, DroneStatus status) {
        Drone updatedDrone = droneService.changeDroneStatus(id, status);
        return ResponseEntity.ok(updatedDrone);
    }

}
