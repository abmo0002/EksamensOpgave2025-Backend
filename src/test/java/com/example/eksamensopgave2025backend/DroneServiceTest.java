package com.example.eksamensopgave2025backend;

import com.example.eksamensopgave2025backend.model.Drone;
import com.example.eksamensopgave2025backend.model.DroneStatus;
import com.example.eksamensopgave2025backend.model.Station;
import com.example.eksamensopgave2025backend.repository.DroneRepository;
import com.example.eksamensopgave2025backend.repository.StationRepository;
import com.example.eksamensopgave2025backend.service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private DroneService droneService;

    private Station station;
    private Drone drone;

    @BeforeEach
    public void setUp() {

        station = new Station();
        station.setId(1L);
        station.setDrones(List.of());


        drone = new Drone();
        drone.setId(1L);
        drone.setSerialNumber(UUID.randomUUID());
        drone.setStatus(DroneStatus.IN_OPERATION);
        drone.setStation(station);
    }

    @Test
    public void testGetAllDrones() {

        when(droneRepository.findAll()).thenReturn(List.of(drone));

        List<Drone> result = droneService.getAllDrones();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(drone, result.get(0));
    }

    @Test
    public void testAddDrone_Success() {

        when(stationRepository.findAll()).thenReturn(List.of(station));
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        Drone result = droneService.addDrone();

        assertNotNull(result);
        assertEquals(DroneStatus.IN_OPERATION, result.getStatus());
        assertEquals(station, result.getStation());
        assertNotNull(result.getSerialNumber());
    }

    @Test
    public void testAddDrone_NoStationsAvailable() {
        when(stationRepository.findAll()).thenReturn(List.of());
        assertThrows(IllegalStateException.class, () -> {
            droneService.addDrone();
        });
    }

    @Test
    public void testChangeDroneStatus_Success() {
        when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        Drone result = droneService.changeDroneStatus(1L, DroneStatus.OUT_OF_OPERATION);

        assertNotNull(result);
        assertEquals(DroneStatus.OUT_OF_OPERATION, result.getStatus());
    }

    @Test
    public void testChangeDroneStatus_DroneNotFound() {
        when(droneRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            droneService.changeDroneStatus(1L, DroneStatus.OUT_OF_OPERATION);
        });
    }
}
