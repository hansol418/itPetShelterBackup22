package com.itpetshelter.itpetshelter.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@Table(name = "reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno")
})
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;


    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText;

    private String replyer;


    public void chageText(String text) {
        this.replyText = text;
    }

}
