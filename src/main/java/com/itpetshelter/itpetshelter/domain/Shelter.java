package com.itpetshelter.itpetshelter.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Shelter {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Sno;

    private String Sname;
    private String Slocate;
}
