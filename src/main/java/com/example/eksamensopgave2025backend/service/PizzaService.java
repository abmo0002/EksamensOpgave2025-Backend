package com.example.eksamensopgave2025backend.service;

import com.example.eksamensopgave2025backend.model.Pizza;
import com.example.eksamensopgave2025backend.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }
}
