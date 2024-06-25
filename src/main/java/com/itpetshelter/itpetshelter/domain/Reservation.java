package com.itpetshelter.itpetshelter.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Reservation")
@Entity
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int Rno;

    @ManyToOne
    @JoinColumn(name = "Mno")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "Ano")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name="Cid")
    private Consumer consumer;

    @ManyToOne
    @JoinColumn(name = "Sno")
    private Shelter shelter;

    private LocalDateTime RDate;
    private LocalDateTime RTime;

    private Boolean Rcheck;

}

