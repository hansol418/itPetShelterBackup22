package com.itpetshelter.itpetshelter.repository;


import com.itpetshelter.itpetshelter.domain.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Integer> {
}
