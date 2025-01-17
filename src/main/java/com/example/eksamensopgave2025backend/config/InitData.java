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


        if (pizzaRepository.count() == 0) {
            pizzaRepository.save(new Pizza("Margherita", 80));
            pizzaRepository.save(new Pizza("Pepperoni", 90));
            pizzaRepository.save(new Pizza("Kebab", 85));
            pizzaRepository.save(new Pizza("Vegetar", 75));
            pizzaRepository.save(new Pizza("Kylling", 95));
        }


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


        if (droneRepository.count() == 0) {
            Station station1 = stationRepository.findById(1L).orElse(null);
            if (station1 != null) {
                Drone drone1 = new Drone();
                drone1.setSerialNumber(UUID.randomUUID());
                drone1.setStation(station1);
                drone1.setStatus(DroneStatus.IN_OPERATION);
                droneRepository.save(drone1);

                Drone drone2 = new Drone();
                drone2.setSerialNumber(UUID.randomUUID());
                drone2.setStation(station1);
                drone2.setStatus(DroneStatus.IN_OPERATION);
                droneRepository.save(drone2);
            }
        }


        if (deliveryRepository.count() == 0) {
            Pizza pizza1 = pizzaRepository.findById(1L).orElse(null);
            Pizza pizza2 = pizzaRepository.findById(2L).orElse(null);
            Drone drone1 = droneRepository.findById(1L).orElse(null);
            Drone drone2 = droneRepository.findById(2L).orElse(null);


            if (pizza1 != null) {
                Delivery delivery1 = new Delivery();
                delivery1.setPizza(pizza1);
                delivery1.setDrone(null);
                delivery1.setAddress("Vesterbrogade 3, 1630 København V");
                delivery1.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));
                deliveryRepository.save(delivery1);

            }


            if (pizza2 != null) {
                Delivery delivery2 = new Delivery();
                delivery2.setPizza(pizza2);
                delivery2.setDrone(null);
                delivery2.setAddress("Gl. Hellebækvej, 2100 København Ø");
                delivery2.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(45));
                deliveryRepository.save(delivery2);
            }


            if (pizza1 != null) {
                Delivery delivery3 = new Delivery();
                delivery3.setPizza(pizza1);
                delivery3.setDrone(null);
                delivery3.setAddress("Christiansborg Slotsplads 1, 1240 København K");
                delivery3.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(60));
                deliveryRepository.save(delivery3);
            }

            if (pizza2 != null) {
                Delivery delivery4 = new Delivery();
                delivery4.setPizza(pizza2);
                delivery4.setDrone(null);
                delivery4.setAddress("Søren Kierkegaards Plads 1, 1219 København K");
                delivery4.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(75));
                deliveryRepository.save(delivery4);
            }
        }
    }
}
