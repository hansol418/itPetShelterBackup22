package com.itpetshelter.itpetshelter.repository;

import com.itpetshelter.itpetshelter.domain.Animal;
import com.itpetshelter.itpetshelter.domain.Shelter;
import com.itpetshelter.itpetshelter.domain.Type;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class AnimalRepositoryTest {

    @Autowired
    AnimalRepository animalRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    ShelterRepository shelterRepository;

    @Test
    public void testInsert() {
        //실제 디비 각자 데이터에 따라서 다름.
        // 현재 bno = 900
        Long tno = 1L;

        Type type = Type.builder()
                .Atype("고양이")
                .Tno(tno)
                .build();

        type = typeRepository.save(type);

        Shelter shelter = Shelter.builder()
                .Slocate("test")
                .Sname("test")
                .build();


        shelter = shelterRepository.save(shelter);

        Animal animal = Animal.builder()
                .type(type)
                .shelter(shelter)
                .Aage(3L)
                .Adisease(true)
                .Aneutered(true)
                .Aname("뽀삐")
                .build();



        Animal result = animalRepository.save(animal);




        log.info("더미 데이터 확인 : "+result);
    } //
}
