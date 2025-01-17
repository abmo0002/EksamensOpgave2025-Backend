package com.example.eksamensopgave2025backend;


import com.example.eksamensopgave2025backend.model.Delivery;
import com.example.eksamensopgave2025backend.model.Drone;
import com.example.eksamensopgave2025backend.model.DroneStatus;
import com.example.eksamensopgave2025backend.model.Pizza;
import com.example.eksamensopgave2025backend.repository.DeliveryRepository;
import com.example.eksamensopgave2025backend.repository.DroneRepository;
import com.example.eksamensopgave2025backend.repository.PizzaRepository;
import com.example.eksamensopgave2025backend.service.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    private Pizza pizza;
    private Drone drone;
    private Delivery delivery;

    @BeforeEach
    public void setUp() {
        pizza = new Pizza();
        pizza.setId(1L);

        drone = new Drone();
        drone.setId(1L);
        drone.setStatus(DroneStatus.IN_OPERATION);

        delivery = new Delivery();
        delivery.setId(1L);
        delivery.setPizza(pizza);
        delivery.setAddress("Test Address");
        delivery.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));
    }

    @Test
    public void testGetPendingDeliveries() {
        when(deliveryRepository.findByActualDeliveryTimeIsNull()).thenReturn(List.of(delivery));

        var result = deliveryService.getPendingDeliveries();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Address", result.get(0).getAddress());
    }

    @Test
    public void testAddDelivery_PizzaNotFound() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            deliveryService.addDelivery(1L, "New Address");
        });
    }

    @Test
    public void testScheduleDelivery_Success() {
        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));
        when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);

        Delivery result = deliveryService.scheduleDelivery(1L, 1L);

        assertNotNull(result);
        assertEquals(drone, result.getDrone());
    }

    @Test
    public void testScheduleDelivery_AlreadyScheduled() {
        delivery.setDrone(drone);

        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));

        assertThrows(IllegalStateException.class, () -> {
            deliveryService.scheduleDelivery(1L, 1L);
        });
    }

    @Test
    public void testScheduleDelivery_NoAvailableDrone() {
        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));
        when(droneRepository.findById(1L)).thenReturn(Optional.empty());
        when(droneRepository.findFirstByStatus(DroneStatus.IN_OPERATION)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> {
            deliveryService.scheduleDelivery(1L, null);
        });
    }


    @Test
    public void testFinishDelivery_NotFound() {
        when(deliveryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            deliveryService.finishDelivery(1L);
        });
    }

    @Test
    public void testFinishDelivery_NoDroneAssigned() {
        delivery.setDrone(null);

        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));

        assertThrows(ResponseStatusException.class, () -> {
            deliveryService.finishDelivery(1L);
        });
    }
}
