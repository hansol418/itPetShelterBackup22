package com.itpetshelter.itpetshelter.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString


@Entity
public class Board extends BaseEntity {


  @Id

  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long bno;

  @Column(length = 500,nullable = false)
  private String title;

  @Column(length = 2000, nullable = false)
  private String content;

  @Column(length = 50, nullable = false)
  private String writer;

  @OneToMany(mappedBy = "board",

          cascade = {CascadeType.ALL},

          fetch = FetchType.LAZY,

          orphanRemoval = true)
  @Builder.Default

  @BatchSize(size = 20)
  private Set<BoardImage> imageSet = new HashSet<>();

  //이미지들 추가
  public void addImage(String uuid, String fileName) {
    BoardImage boardImage = BoardImage.builder()
            .uuid(uuid)
            .fileName(fileName)
            .board(this)
            .ord(imageSet.size())
            .build();

    imageSet.add(boardImage);
  }

  // 이미지들 삭제
  public void clearImages(){

    imageSet.forEach(boardImage -> boardImage.changeBoard(null));
    this.imageSet.clear();
  }

  public void changeTitleAndContent(String title, String content) {
    this.title = title;
    this.content = content;
  }

}





