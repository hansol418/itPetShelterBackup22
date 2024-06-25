package com.itpetshelter.itpetshelter.controller;


import com.itpetshelter.itpetshelter.dto.BoardDTO;
import com.itpetshelter.itpetshelter.dto.BoardListAllDTO;
import com.itpetshelter.itpetshelter.dto.PageRequestDTO;
import com.itpetshelter.itpetshelter.dto.PageResponseDTO;
import com.itpetshelter.itpetshelter.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.util.List;


@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    @Value("${com.busanit501.upload.path}")
    private String uploadPath;

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("BoardController : /board/list  확인 중, pageRequestDTO : " + pageRequestDTO);


        PageResponseDTO<BoardListAllDTO> responseDTO
                = boardService.listWithAll(pageRequestDTO);

        log.info("BoardController 확인 중, responseDTO : " + responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }

    //글쓰기 폼
    @GetMapping("/register")
    public void registerForm() {
    }

    //글쓰기 처리
    @PostMapping("/register")
    public String register(@Valid BoardDTO boardDTO
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes
            , Model model) {

        if(bindingResult.hasErrors()) {
            log.info("register 중 오류 발생.");
            redirectAttributes.addFlashAttribute(
                    "errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }
        log.info("화면에서 입력 받은 내용 확인 : " + boardDTO);


        Long bno = boardService.register(boardDTO);


        redirectAttributes.addFlashAttribute("result",bno);
        redirectAttributes.addFlashAttribute("resultType","register");
        return "redirect:/board/list";

    }


    @GetMapping({"/read","/update"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {

        log.info("BoardController : /board/read  확인 중, pageRequestDTO : " + pageRequestDTO);


        BoardDTO boardDTO = boardService.read(bno);

        log.info("BoardController 확인 중, boardDTO : " + boardDTO);

        model.addAttribute("boardDTO", boardDTO);

    }

    //글수정 처리
    @PostMapping("/update")
    public String update(@Valid BoardDTO boardDTO
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes
            , Model model
            ,PageRequestDTO pageRequestDTO) {

        if(bindingResult.hasErrors()) {
            log.info("update 중 오류 발생.");
            redirectAttributes.addFlashAttribute(
                    "errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", boardDTO.getBno());
            return "redirect:/board/update"+pageRequestDTO.getLink();
        }
        log.info("화면에서 입력 받은 내용 update 확인 : " + boardDTO);


        boardService.update(boardDTO);


        redirectAttributes.addFlashAttribute("result",boardDTO.getBno());
        redirectAttributes.addFlashAttribute("resultType","update");

        return "redirect:/board/list?"+pageRequestDTO.getLink2();

    }

    //글삭제 처리
    @PostMapping("/delete")
    public String delete(BoardDTO boardDTO, PageRequestDTO pageRequestDTO, Long bno, RedirectAttributes redirectAttributes
    ) {



        boardService.deleteAll(bno);
        log.info("delete : boardDTO 확인 : " + boardDTO);

        List<String> fileNames = boardDTO.getFileNames();
        if(fileNames != null && fileNames.size()>0) {
            removeFiles(fileNames);
        }


        redirectAttributes.addFlashAttribute("result",bno);
        redirectAttributes.addFlashAttribute("resultType","delete");
        return "redirect:/board/list?"+pageRequestDTO.getLink2();

    }

    public void removeFiles(List<String> files) {
        for (String fileName : files) {
            Resource resource = new FileSystemResource(
                    uploadPath+ File.separator+fileName);
            String resourceName = resource.getFilename();

            boolean deleteCheck = false;
            try {

                String contentType = Files.probeContentType(resource.getFile().toPath());
                deleteCheck = resource.getFile().delete();
                if(contentType.startsWith("image")){
                    File thumbnailFile = new File(uploadPath+File.separator
                            +"s_"+fileName);
                    thumbnailFile.delete();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
    }


}
