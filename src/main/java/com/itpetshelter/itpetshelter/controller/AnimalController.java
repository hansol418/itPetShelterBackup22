package com.itpetshelter.itpetshelter.controller;

import com.itpetshelter.itpetshelter.dto.AnimalDTO;
import com.itpetshelter.itpetshelter.service.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/itpetshelter")
@Log4j2
@RequiredArgsConstructor
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping("/animalList")
    public String getAllAnimals(Model model){
        List<AnimalDTO> animalList = animalService.getAllAnimals();
        model.addAttribute("animalList", animalList);
        return "itpetshelter/animalList";
    }

    @GetMapping("/animalRead")
    public String getAnimalById(Long id, Model model){
        AnimalDTO animalDTO = animalService.getAnimalById(id);
        model.addAttribute("animal", animalDTO);
        return "itpetshelter/animalRead";
    }
}