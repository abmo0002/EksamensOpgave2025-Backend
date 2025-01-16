package com.example.eksamensopgave2025backend.service;

import com.example.eksamensopgave2025backend.model.Drone;
import com.example.eksamensopgave2025backend.model.DroneStatus;
import com.example.eksamensopgave2025backend.model.Station;
import com.example.eksamensopgave2025backend.repository.DroneRepository;
import com.example.eksamensopgave2025backend.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private StationRepository stationRepository;

    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    public Drone addDrone() {
        List<Station> stations = stationRepository.findAll();
        if (stations.isEmpty()) {
            throw new IllegalStateException("Ingen stationer tilgængelige.");
        }

        Station leastBusyStation = stations.stream()
                .min((s1, s2) -> Integer.compare(s1.getDrones().size(), s2.getDrones().size()))
                .orElseThrow();

        Drone newDrone = new Drone();
        newDrone.setSerialNumber(UUID.randomUUID());
        newDrone.setStatus(DroneStatus.IN_OPERATION);
        newDrone.setStation(leastBusyStation);

        return droneRepository.save(newDrone);
    }

    public Drone changeDroneStatus(Long id, DroneStatus status) {
        Optional<Drone> droneOptional = droneRepository.findById(id);
        if (droneOptional.isEmpty()) {
            throw new IllegalArgumentException("Drone ikke fundet.");
        }

        Drone drone = droneOptional.get();
        drone.setStatus(status);
        return droneRepository.save(drone);
    }
}
