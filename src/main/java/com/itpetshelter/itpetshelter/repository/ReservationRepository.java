package com.itpetshelter.itpetshelter.repository;


import com.itpetshelter.itpetshelter.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
