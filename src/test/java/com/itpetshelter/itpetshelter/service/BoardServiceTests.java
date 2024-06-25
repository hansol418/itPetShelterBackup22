package com.itpetshelter.itpetshelter.service;


import com.itpetshelter.itpetshelter.dto.BoardDTO;
import com.itpetshelter.itpetshelter.dto.BoardListReplyCountDTO;
import com.itpetshelter.itpetshelter.dto.PageRequestDTO;
import com.itpetshelter.itpetshelter.dto.PageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {
  @Autowired
  BoardService boardService;

  @Test
  public void testInsert() {
    // 화면에서 넘어온 더미 데이터 만들기. DTO 타입.
    BoardDTO boardDTO = BoardDTO.builder()
        .title("내일 모하니?")
        .content("부모님 인사하기")
        .writer("이상용")
        .build();

    Long bno = boardService.register(boardDTO);
    log.info("추가 후에 반환 게시글 번호 bno : " + bno);

  }

  @Test
  public void testRead() {

    BoardDTO boardDTO = boardService.read(101L);
    log.info("하나 조회 boardDTO : " + boardDTO);

  }

  @Test
  public void testUpdate() {
    // 변경시, 변경할 더미 데이터, 임시, 601L
// 화면에서 넘어온 더미 데이터 만들기. DTO 타입.
    BoardDTO boardDTO = BoardDTO.builder()
        .bno(101L)
        .title("내일 모하니?수정버전")
        .content("부모님 인사하기 : 수정버전")
        .writer("이상용 : 수정버전")
        .build();

    //디비에서 조회하기.
    boardService.update(boardDTO);


  }


  @Test
  public void testDelete() {
        //디비에서 조회하기.
    // 좋은 질문,
    // 서버에서 화면, 데이터 같이 처리, Controller,
    // 질문, 게시글 삭제 할려면, 화면에 보일까요? 안보일까요?

    // RestController, 데이터만 처리.
    // 화면이 없어서, method delete 형식으로 명령이 오면,
    boardService.delete(101L);


  }

  @Test
  public void testList() {
    // 화면에서 전달할 내용을 담은 PageRequestDTO 더미가 필요.
    PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
            .type("tcw")
            .keyword("오늘")
            .page(1)
            .size(10)
            .build();

  PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
    log.info("list 테스트 responseDTO : " + responseDTO);

  }


  @Test
  public void testListWithCount() {
    // 화면에서 전달할 내용을 담은 PageRequestDTO 더미가 필요.
    PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
            .type("tcw")
            .keyword("오늘")
            .page(1)
            .size(10)
            .build();

    PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);
    log.info("list 테스트 responseDTO : " + responseDTO);

  }

}







