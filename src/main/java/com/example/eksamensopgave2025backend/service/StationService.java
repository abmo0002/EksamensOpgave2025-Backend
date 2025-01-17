package com.example.eksamensopgave2025backend.service;

import com.example.eksamensopgave2025backend.model.Station;
import com.example.eksamensopgave2025backend.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }
}
