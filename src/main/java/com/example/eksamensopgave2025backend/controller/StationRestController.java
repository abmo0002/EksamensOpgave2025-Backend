package com.example.eksamensopgave2025backend.controller;

import com.example.eksamensopgave2025backend.model.Station;
import com.example.eksamensopgave2025backend.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stations")
@CrossOrigin
public class StationRestController {

    @Autowired
    private StationService stationService;

    @GetMapping
    public List<Station> getAllStations() {
        return stationService.getAllStations();
    }
}
