package com.itpetshelter.itpetshelter.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Mno;

    @ManyToOne
    @JoinColumn(name = "Sno")
    private Shelter shelter;

    private String Mpw;
    private String Mname;
    private String Mid;
}

