package com.itpetshelter.itpetshelter.service;

import com.itpetshelter.itpetshelter.domain.Animal;
import com.itpetshelter.itpetshelter.dto.AnimalDTO;

import java.util.List;

public interface AnimalService {

    List<AnimalDTO> getAllAnimals();
    AnimalDTO getAnimalById(Long id);
    AnimalDTO entityToDTO(Animal animal);

}
