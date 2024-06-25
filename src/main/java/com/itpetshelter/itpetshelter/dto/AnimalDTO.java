package com.itpetshelter.itpetshelter.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AnimalDTO {

    // NotEmpty는 String에만 사용 가능

    private Long Ano;

    @NotEmpty
    private String Atype;

    @NotEmpty
    private String Aname;


    private Long Aage;

    private Boolean Aneutered;
    private Boolean Adisease;

    @NotEmpty
    private String Alocate;
}
