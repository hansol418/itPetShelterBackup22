package com.itpetshelter.itpetshelter.service;

import com.itpetshelter.itpetshelter.dto.PageRequestDTO;
import com.itpetshelter.itpetshelter.dto.PageResponseDTO;
import com.itpetshelter.itpetshelter.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);
    ReplyDTO read(Long rno);
    void update(ReplyDTO replyDTO);
    void delete(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);
}
