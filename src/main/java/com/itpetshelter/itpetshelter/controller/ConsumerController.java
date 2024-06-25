package com.itpetshelter.itpetshelter.controller;


import com.itpetshelter.itpetshelter.dto.ConsumerJoinDTO;
import com.itpetshelter.itpetshelter.dto.upload.UploadResultDTO;
import com.itpetshelter.itpetshelter.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/consumer")
@Log4j2
@RequiredArgsConstructor
public class ConsumerController {
    private final ConsumerService consumerService;

    @GetMapping("/login")
    public void loginGet(String error, String logout,
                         RedirectAttributes redirectAttributes) {
        log.info("loginGet===================");
        log.info("logoutTest ===================" + logout);

        if (logout != null) {
            log.info("logoutTest user ====================");
        }
        if (error != null) {
            log.info("logoutTest error ====================" + error);
            redirectAttributes.addFlashAttribute(
                    "error", "");
        }

    }
    // 로그인 폼은 있지만, 로그인을 처리하는 로직처리가 없어요?
    // 누가 처리? 스프링의 시큐리티가 알아서 처리함.
    // 자동로그인, remember-me 이름으로 서버에 와요.

    //회원가입 폼
    @GetMapping("/join")
    public void joinGet() {
        log.info("joinGet====================");
    }

    // 회원 가입 로직 처리
    @PostMapping("/join")
    public String joinPost(ConsumerJoinDTO consumerJoinDTO, @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
                           RedirectAttributes redirectAttributes) {
        log.info("joinPost====================");
        log.info("consumerJoinDTO = " + consumerJoinDTO);
        log.info("consumerJoinDTO = 2 profileImage " + profileImage.getOriginalFilename());

        // 프로필 이미지가 있을 경우, 이미지를 먼저 업로드 함.
        String resultProfileImage = "";
        if(profileImage != null && !profileImage.isEmpty()) {
            UploadResultDTO uploadResultDTO = consumerService.uploadProfileImage(profileImage);
            resultProfileImage = uploadResultDTO.getLink();
            log.info("consumerJoinDTO 이미지가 있는 경우 : " + resultProfileImage);

//        memberJoinDTO.addProfileImage(resultProfileImage);
            String[] arr = resultProfileImage.split("_");
            consumerJoinDTO.setUuid(arr[1]);
            consumerJoinDTO.setFileName(arr[2]);
        }

        log.info("consumerJoinDTO = 3 프로필 이미지 있는 경우  " + consumerJoinDTO);
        // 회원 가입 로직 처리 없음.
        try {
            consumerService.join(consumerJoinDTO);
        } catch (ConsumerService.CidExistException e) {
            redirectAttributes.addFlashAttribute("error", "아이디 중복입니다");
            return "redirect:/consumer/join";
        }
        // 회원 가입 성공시
        redirectAttributes.addFlashAttribute("result","회원가입 성공");
        return "redirect:/consumer/login";
    }

}