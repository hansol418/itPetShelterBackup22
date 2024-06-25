package com.itpetshelter.itpetshelter.service;


import com.itpetshelter.itpetshelter.domain.Consumer;
import com.itpetshelter.itpetshelter.domain.MemberRole;
import com.itpetshelter.itpetshelter.dto.ConsumerJoinDTO;
import com.itpetshelter.itpetshelter.dto.upload.UploadResultDTO;
import com.itpetshelter.itpetshelter.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {
    @Value("${com.busanit501.upload.path}")
    private String uploadPath;

    // 다른 기능들 도움 받기, 의존, 주입, 포함 관계 ,
    private final ModelMapper modelMapper;
    private final ConsumerRepository consumerRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void join(ConsumerJoinDTO consumerJoinDTO) throws CidExistException {
        //기존 아이디와 중복되는지 여부 확인
        String Cid = consumerJoinDTO.getCid();
        boolean existConsumer = consumerRepository.existsById(Cid);
        if (existConsumer) {
            throw new CidExistException();
        }

        // 중복이 아니니 회원 가입 처리하기.
//         Member member = modelMapper.map(memberJoinDTO, Member.class);
        log.info("consumerJoinDTO = 4 ConsumerServiceImpl 프로필 이미지 있는 경우  " + consumerJoinDTO);
        Consumer consumer = dtoToEntity(consumerJoinDTO);
        log.info("consumerJoinDTO = 5 consumer ConsumerServiceImpl 프로필 이미지 있는 경우  " + consumer);
        //패스워드는 현재 평문 -> 암호로 변경.
        consumer.changePassword(passwordEncoder.encode(consumer.getCpw()));
        // 역할 추가. 기본 USER
        consumer.addRole(MemberRole.USER);


        // 데이터 가 잘 알맞게 변경이 됐는지 여부,
        log.info("joinConsumer: " + consumer);
        log.info("joinConsumer: " + consumer.getRoleSet());

        // 디비에 적용하기.
        consumerRepository.save(consumer);

    }


    // 일반회원 정보 수정.
    @Override
    public void update(ConsumerJoinDTO consumerJoinDTO) throws CidExistException {

        log.info("consumerJoinDTO = 4 ConsumerServiceImpl 프로필 이미지 있는 경우  " + consumerJoinDTO);
        Consumer consumer = null;
        if(consumerJoinDTO.getUuid() == null) {
            // 기존 이미지 재사용.
            log.info("consumerJoinDTO = 4-2 ConsumerServiceImpl 기존 이미지 재사용  " + consumerJoinDTO);
            Optional<Consumer> result = consumerRepository.findById(consumerJoinDTO.getCid());
            Consumer oldConsumer = result.orElseThrow();
            consumerJoinDTO.setUuid(oldConsumer.getUuid());
            consumerJoinDTO.setFileName(oldConsumer.getFileName());
            log.info("consumerJoinDTO = 5 ConsumerServiceImpl 기존 이미지 재사용  " + consumerJoinDTO);
            consumer = dtoToEntity(consumerJoinDTO);
            log.info("consumerJoinDTO = 6 ConsumerServiceImpl 기존 이미지 재사용 consumer " + consumer);
            //패스워드는 현재 평문 -> 암호로 변경.
            consumer.changePassword(passwordEncoder.encode(consumer.getCpw()));
            // 역할 추가. 기본 USER
            consumer.addRole(MemberRole.USER);


            // 데이터 가 잘 알맞게 변경이 됐는지 여부,
            log.info("updateMember: " + consumer);
            log.info("updateMember: " + consumer.getRoleSet());

            // 디비에 적용하기. -> 수정하기.
            consumerRepository.save(consumer);
        }
        else {
            //새로운 이미지가 들어오는 경우
            consumer = dtoToEntity(consumerJoinDTO);
            log.info("consumerJoinDTO = 8 ConsumerServiceImpl 새로운 이미지가 들어오는 경우 consumer " + consumer);
            //패스워드는 현재 평문 -> 암호로 변경.
            consumer.changePassword(passwordEncoder.encode(consumer.getCpw()));
            // 역할 추가. 기본 USER
            consumer.addRole(MemberRole.USER);


            // 데이터 가 잘 알맞게 변경이 됐는지 여부,
            log.info("updateMember: " + consumer);
            log.info("updateMember: " + consumer.getRoleSet());

            // 디비에 적용하기. -> 수정하기.
            consumerRepository.save(consumer);
        }


    }



    @Override
    public UploadResultDTO uploadProfileImage(MultipartFile fileImageName) {
        log.info("ConsumerServiceImpl uploadFileDTO : " + fileImageName);
        if (fileImageName != null) {
            //multipartFile 객체 안에 이미지 파일들이 들어가 있다.
            log.info("파일명 확인: " + fileImageName.getOriginalFilename());
            //원본 파일명
            String originName = fileImageName.getOriginalFilename();
            // 랜덤한 16자리 문자열
            String uuid = UUID.randomUUID().toString();
            // 업로드 경로에 파일 객체를 만들기.
            Path savePath = Paths.get(uploadPath, uuid + "_" + originName);

            boolean imgCheck = false;

            try {
                //실제 파일에 저장.
                fileImageName.transferTo(savePath);

                //해당 파일의 종류를 확인하고, 타입 image/* 로 시작한다면
                // 썸네일로 변경하기.
                if (Files.probeContentType(savePath).startsWith("image")) {
                    // 이미지 상태 변수 변경.
                    imgCheck = true;
                    // uploadPath 해당 경로에 , 물리 파일 만들기 , 이름 :"s_"+uuid+"_"+originName
                    File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originName);
                    // 원본 이미지 -> thumbFile 곳에 축소해서 저장하기.
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            //각각 이미지 파일명, 임시 목록에 담기.


            UploadResultDTO uploadResultDTO = UploadResultDTO.builder()
                    .uuid(uuid)
                    .fileName(originName)
                    .imgCheck(imgCheck)
                    .build();




            return uploadResultDTO;
        } // if 파일(사진)이 있는 경우
        // 파일(사진)이 없을 경우
        return null;
    }
}
