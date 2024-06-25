package com.itpetshelter.itpetshelter.service;


import com.itpetshelter.itpetshelter.domain.Board;
import com.itpetshelter.itpetshelter.domain.Reply;
import com.itpetshelter.itpetshelter.dto.*;
import com.itpetshelter.itpetshelter.repository.BoardRepository;
import com.itpetshelter.itpetshelter.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;
  private final ReplyRepository replyRepository;
  private final ModelMapper modelMapper;

  @Override
  public Long register(BoardDTO boardDTO) {

    Board board = dtoToEntity(boardDTO);
    Long bno = boardRepository.save(board).getBno();
    return bno;
  }

  @Override
  public BoardDTO read(Long bno) {

    Optional<Board> result = boardRepository.findByIdWithImages(bno);

    Board board = result.orElseThrow();

    BoardDTO boardDTO = entityToDTO(board);

    return boardDTO;
  }

  @Override
  public void update(BoardDTO boardDTO) {
    Optional<Board> result = boardRepository.findById(boardDTO.getBno());
    Board board = result.orElseThrow();

    board.changeTitleAndContent(boardDTO.getTitle(),boardDTO.getContent());


    board.clearImages();


    if(boardDTO.getFileNames() != null) {
      for(String fileName : boardDTO.getFileNames()){
        String[] arr = fileName.split("_");
        board.addImage(arr[0],arr[1]);
      }
    }


    boardRepository.save(board);
  }

  @Override
  public void delete(Long bno) {

    boardRepository.deleteById(bno);
  }

  @Override
  public void deleteAll(Long bno) {

    List<Reply> result = replyRepository.findByBoardBno(bno);

    log.info("result.isEmpty() 값  확인:" + result.isEmpty());
    boolean checkReply = result.isEmpty() ? false : true;
    log.info("result.isEmpty() 값  확인2 checkReply:" + checkReply);
    if(checkReply){
      replyRepository.deleteByBoard_Bno(bno);
    }

    //게시글 삭제와 첨부 이미지 삭제(썸네일 이미지 삭제도 포함) 포함
    boardRepository.deleteById(bno);
  }

  @Override
  public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
    String[] types = pageRequestDTO.getTypes();
    String keyword = pageRequestDTO.getKeyword();
    Pageable pageable = pageRequestDTO.getPageable("bno");

    Page<Board> result = boardRepository.searchAll(types,keyword,pageable);

    List<BoardDTO> dtoList = result.getContent().stream()
            .map(board -> modelMapper.map(board,BoardDTO.class))
            .collect(Collectors.toList());


    // 1)페이지 2) 사이즈 3) 전쳇갯수 4) 검색 결과 내역10개(엔티티-> DTO)
    PageResponseDTO pageResponseDTO = PageResponseDTO.<BoardDTO>withAll()
            .pageRequestDTO(pageRequestDTO)
            .dtoList(dtoList)
            .total((int) result.getTotalElements())
            .build();


    return pageResponseDTO;
  }

  @Override
  public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount
          (PageRequestDTO pageRequestDTO) {
    String[] types = pageRequestDTO.getTypes();
    String keyword = pageRequestDTO.getKeyword();
    Pageable pageable = pageRequestDTO.getPageable("bno");


    Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types,keyword,pageable);



    // 1)페이지 2) 사이즈 3) 전쳇갯수 4) 검색 결과 내역10개(엔티티-> DTO)
    PageResponseDTO pageResponseDTO = PageResponseDTO.<BoardListReplyCountDTO>withAll()
            .pageRequestDTO(pageRequestDTO)
            .dtoList(result.getContent())
            .total((int) result.getTotalElements())
            .build();

    return pageResponseDTO;
  }

  @Override
  public PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {

    String[] types = pageRequestDTO.getTypes();
    String keyword = pageRequestDTO.getKeyword();
    Pageable pageable = pageRequestDTO.getPageable("bno");

    Page<BoardListAllDTO> result = boardRepository.searchWithAll(types,keyword,pageable);

    PageResponseDTO<BoardListAllDTO> pageResponseDTO =
            PageResponseDTO.<BoardListAllDTO>withAll()
                    .pageRequestDTO(pageRequestDTO)
                    .dtoList(result.getContent())
                    .total((int) result.getTotalElements())
                    .build();

    return pageResponseDTO;
  }
}







