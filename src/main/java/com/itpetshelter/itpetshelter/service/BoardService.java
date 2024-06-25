package com.itpetshelter.itpetshelter.service;


import com.itpetshelter.itpetshelter.domain.Board;
import com.itpetshelter.itpetshelter.dto.*;

import java.util.List;
import java.util.stream.Collectors;


public interface BoardService {
  Long register(BoardDTO boardDTO);

  BoardDTO read(Long bno);

  void update(BoardDTO boardDTO);
  void delete(Long bno);
  void deleteAll(Long bno);


  PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
  //댓글 숫자 포함해서 목록 출력하기.
  PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

  // 댓글 갯수 + 첨부 이미지들
  PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);


  default Board dtoToEntity(BoardDTO boardDTO){

    Board board = Board.builder()
            .bno(boardDTO.getBno())
            .title(boardDTO.getTitle())
            .content(boardDTO.getContent())
            .writer(boardDTO.getWriter())
            .build();

    // 첨부 이미지들이 추가
    if(boardDTO.getFileNames() != null) {
      boardDTO.getFileNames().forEach(fileName ->
      {
        String[] arr = fileName.split("_");
        board.addImage(arr[0],arr[1]);
      });
    }
    return board;
  }


  default BoardDTO entityToDTO(Board board) {
    BoardDTO boardDTO = BoardDTO.builder()
            .bno(board.getBno())
            .title(board.getTitle())
            .content(board.getContent())
            .writer(board.getWriter())
            .regDate(board.getRegDate())
            .modDate(board.getModDate())
            .build();

    // 첨부된 이미지 파일들.
    List<String> fileNames = board.getImageSet().stream().sorted().map(
            boardImage -> boardImage.getUuid()+"_"+boardImage.getFileName()
    ).collect(Collectors.toList());

    boardDTO.setFileNames(fileNames);

    return boardDTO;
  }



}







