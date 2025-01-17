package com.example.eksamensopgave2025backend;

import com.example.eksamensopgave2025backend.model.Pizza;
import com.example.eksamensopgave2025backend.repository.PizzaRepository;
import com.example.eksamensopgave2025backend.service.PizzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @InjectMocks
    private PizzaService pizzaService;
    private Pizza pizza;

    @BeforeEach
    public void setUp() {
        pizza = new Pizza();
        pizza.setId(1L);
        pizza.setTitle("Margherita");
        pizza.setPrice(49.99);
    }

    @Test
    public void testGetAllPizzas() {
        when(pizzaRepository.findAll()).thenReturn(List.of(pizza));
        List<Pizza> result = pizzaService.getAllPizzas();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(pizza, result.get(0));
    }

    @Test
    public void testGetAllPizzas_EmptyList() {
        when(pizzaRepository.findAll()).thenReturn(List.of());
        List<Pizza> result = pizzaService.getAllPizzas();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
