package com.example.eksamensopgave2025backend.repository;
import com.example.eksamensopgave2025backend.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}
