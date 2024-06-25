package com.itpetshelter.itpetshelter.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class AnimalServiceTest {

    @Autowired
    AnimalService animalService;

    @Test
    public void test() {

//        AnimalDTO animalDTO = AnimalDTO.builder()
//                .Atype("s")
//                .Aage(1L)
//                .Adisease(true)
//                .Aneutered(true)
//                .Aname("뽀삐")
//                .Alocate("test")
//                .build();
//
//
//        animalService.entityToDTO()


    }
}

