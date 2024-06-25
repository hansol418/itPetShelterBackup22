package com.itpetshelter.itpetshelter.controller;


import com.itpetshelter.itpetshelter.dto.PageRequestDTO;
import com.itpetshelter.itpetshelter.dto.PageResponseDTO;
import com.itpetshelter.itpetshelter.dto.ReplyDTO;
import com.itpetshelter.itpetshelter.service.ReplyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @Tag(name = "댓글 등록 post 방식", description = "댓글 등록 post 방식")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)

    public Map<String,Long> register(

           @Valid @RequestBody ReplyDTO replyDTO,
            BindingResult bindingResult) throws BindException {
        log.info("ReplyController의 register ,replyDTO 확인: "+replyDTO);


        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }


        Map<String,Long> resultMap = new HashMap<>();
        Long rno = replyService.register(replyDTO);
        resultMap.put("rno",rno);


        return resultMap;

    }

    @Tag(name = "댓글 목록 조회 get 방식", description = "댓글 목록 조회 get 방식")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(

        @PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO
    ) {
        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno, pageRequestDTO);
        return  responseDTO;
    } //getList

    @Tag(name = "특정 댓글 한개 조회 get 방식", description = "특정 댓글 한개 조회 get 방식")
    @GetMapping(value = "/{rno}")
    public ReplyDTO getReadOne(

            @PathVariable("rno") Long rno
    ) {
        ReplyDTO replyDTO= replyService.read(rno);
        return  replyDTO;
    } //getList

    @Tag(name = "특정 댓글 삭제 delete 방식", description = "특정 댓글 삭제 delete 방식")
    @DeleteMapping(value = "/{rno}")
    public Map<String,Long> delete(

            @PathVariable("rno") Long rno
    ) {
        replyService.delete(rno);
        Map<String,Long> resultMap = new HashMap<>();
        resultMap.put("rno",rno);
        return  resultMap;
    }


    @Tag(name = "특정 댓글 수정 put 방식", description = "특정 댓글 수정 put 방식")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)

    public Map<String,Long> update(

            @PathVariable("rno") Long rno,
            @Valid @RequestBody ReplyDTO replyDTO,
            BindingResult bindingResult) throws BindException {
        log.info("ReplyController의 update ,replyDTO 확인: "+replyDTO);


        replyDTO.setRno(rno);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }


        Map<String,Long> resultMap = new HashMap<>();
        replyService.update(replyDTO);

        resultMap.put("rno",rno);


        return resultMap;

    }

}
