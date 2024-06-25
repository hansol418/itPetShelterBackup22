package com.itpetshelter.itpetshelter.controller;

import com.itpetshelter.itpetshelter.domain.Reservation;
import com.itpetshelter.itpetshelter.repository.AnimalRepository;
import com.itpetshelter.itpetshelter.repository.ReservationRepository;
import com.itpetshelter.itpetshelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/itpetshelter")
@Log4j2
@RequiredArgsConstructor
public class IPSController {

    @Autowired
    private ReservationRepository reservationRepository;

    private final AnimalRepository animalRepository;

    private final ShelterRepository shelterRepository;

    @GetMapping("/index")
    public void index() {
    }

    @GetMapping("/reservation")
    public void showReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("animals", animalRepository.findAll());
        model.addAttribute("shelters", shelterRepository.findAll());
    }


    @GetMapping("/shelter")
    public String shelter() {
        return "page/shelter";
    }

    @GetMapping("/volunteer")
    public String volunteer() {
        return "page/volunteer";
    }


}
