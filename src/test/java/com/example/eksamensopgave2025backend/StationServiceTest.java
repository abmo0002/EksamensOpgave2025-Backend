package com.example.eksamensopgave2025backend;


import com.example.eksamensopgave2025backend.model.Station;
import com.example.eksamensopgave2025backend.repository.StationRepository;
import com.example.eksamensopgave2025backend.service.StationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
public class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    private Station station;

    @BeforeEach
    public void setUp() {
        station = new Station();
        station.setId(1L);
        station.setLatitude(10.0);
        station.setLongitude(20.0);
    }

    @Test
    public void testGetAllStations() {
        when(stationRepository.findAll()).thenReturn(List.of(station));

        List<Station> result = stationService.getAllStations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(station, result.get(0));
    }

    @Test
    public void testGetAllStations_EmptyList() {
        when(stationRepository.findAll()).thenReturn(List.of());

        List<Station> result = stationService.getAllStations();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}