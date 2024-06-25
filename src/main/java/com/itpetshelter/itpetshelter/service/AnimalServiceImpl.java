package com.itpetshelter.itpetshelter.service;

import com.itpetshelter.itpetshelter.domain.Animal;
import com.itpetshelter.itpetshelter.dto.AnimalDTO;
import com.itpetshelter.itpetshelter.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public List<AnimalDTO> getAllAnimals() {
        return animalRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AnimalDTO getAnimalById(Long id) {
        return animalRepository.findById(id)
                .map(this::entityToDTO)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
    }

    @Override
    public AnimalDTO entityToDTO(Animal animal) {
        AnimalDTO animalDTO = AnimalDTO.builder()
                .Ano(animal.getAno())
                .Atype(animal.getType().getAtype())
                .Aname(animal.getAname())
                .Aage(animal.getAage())
                .Aneutered(animal.getAneutered())
                .Adisease(animal.getAdisease())
                .Alocate(animal.getShelter().getSlocate())
                .build();

        return animalDTO;
    }

}
