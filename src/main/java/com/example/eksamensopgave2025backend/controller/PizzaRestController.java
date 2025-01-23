package com.example.eksamensopgave2025backend.controller;

import com.example.eksamensopgave2025backend.model.Pizza;
import com.example.eksamensopgave2025backend.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pizzas")
@CrossOrigin
public class PizzaRestController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public List<Pizza> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }
}
