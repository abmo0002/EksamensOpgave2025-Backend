package com.example.eksamensopgave2025backend.config;

import com.example.eksamensopgave2025backend.model.Delivery;
import com.example.eksamensopgave2025backend.model.Drone;
import com.example.eksamensopgave2025backend.model.DroneStatus;
import com.example.eksamensopgave2025backend.model.Pizza;
import com.example.eksamensopgave2025backend.model.Station;
import com.example.eksamensopgave2025backend.repository.DeliveryRepository;
import com.example.eksamensopgave2025backend.repository.DroneRepository;
import com.example.eksamensopgave2025backend.repository.PizzaRepository;
import com.example.eksamensopgave2025backend.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class InitData {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @PostConstruct
    public void init() {

        // Opret pizzaer hvis der ikke findes nogen
        if (pizzaRepository.count() == 0) {
            pizzaRepository.save(new Pizza("Margherita", 80));
            pizzaRepository.save(new Pizza("Pepperoni", 90));
            pizzaRepository.save(new Pizza("Kebab", 85));
            pizzaRepository.save(new Pizza("Vegetar", 75));
            pizzaRepository.save(new Pizza("Kylling", 95));
        }

        // Opret stations hvis der ikke findes nogen
        if (stationRepository.count() == 0) {
            Station station1 = new Station();
            station1.setLatitude(55.417);
            station1.setLongitude(12.345);
            stationRepository.save(station1);

            Station station2 = new Station();
            station2.setLatitude(55.426);
            station2.setLongitude(12.347);
            stationRepository.save(station2);

            Station station3 = new Station();
            station3.setLatitude(55.436);
            station3.setLongitude(12.340);
            stationRepository.save(station3);
        }

        // Opret droner hvis der ikke findes nogen
        if (droneRepository.count() == 0) {
            Station station1 = stationRepository.findById(1L).orElse(null);  // Få station1 fra databasen
            if (station1 != null) {
                Drone drone1 = new Drone();
                drone1.setSerialNumber(UUID.randomUUID());  // Generer en UUID for serienummeret
                drone1.setStation(station1);
                drone1.setStatus(DroneStatus.IN_OPERATION);
                droneRepository.save(drone1);

                Drone drone2 = new Drone();
                drone2.setSerialNumber(UUID.randomUUID());  // Generer en UUID for serienummeret
                drone2.setStation(station1);
                drone2.setStatus(DroneStatus.IN_OPERATION);
                droneRepository.save(drone2);
            }
        }

        // Opret leveringer hvis der ikke findes nogen
        // Opret leveringer - nogle med drone og nogle uden
        if (deliveryRepository.count() == 0) {
            Pizza pizza1 = pizzaRepository.findById(1L).orElse(null);  // Find pizza fra databasen
            Pizza pizza2 = pizzaRepository.findById(2L).orElse(null);  // Find pizza fra databasen
            Drone drone1 = droneRepository.findById(1L).orElse(null);  // Find drone fra databasen
            Drone drone2 = droneRepository.findById(2L).orElse(null);  // Find drone fra databasen

            // Levering med drone
            if (pizza1 != null && drone1 != null) {
                Delivery delivery1 = new Delivery();
                delivery1.setPizza(pizza1);
                delivery1.setDrone(drone1);  // Tildeler drone
                delivery1.setAddress("Address 1");
                delivery1.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));  // Forventet leveringstid
                deliveryRepository.save(delivery1);
            }

            // Levering uden drone
            if (pizza2 != null) {
                Delivery delivery2 = new Delivery();
                delivery2.setPizza(pizza2);
                delivery2.setDrone(null);  // Ingen drone tilknyttet
                delivery2.setAddress("Address 2");
                delivery2.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(45));  // Forventet leveringstid
                deliveryRepository.save(delivery2);
            }

            // Flere leveringer uden drone
            if (pizza1 != null) {
                Delivery delivery3 = new Delivery();
                delivery3.setPizza(pizza1);
                delivery3.setDrone(null);  // Ingen drone tilknyttet
                delivery3.setAddress("Address 3");
                delivery3.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(60));  // Forventet leveringstid
                deliveryRepository.save(delivery3);
            }

            if (pizza2 != null) {
                Delivery delivery4 = new Delivery();
                delivery4.setPizza(pizza2);
                delivery4.setDrone(null);  // Ingen drone tilknyttet
                delivery4.setAddress("Address 4");
                delivery4.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(75));  // Forventet leveringstid
                deliveryRepository.save(delivery4);
            }
        }
    }
}
