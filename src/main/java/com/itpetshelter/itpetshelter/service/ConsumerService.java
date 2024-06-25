package com.itpetshelter.itpetshelter.service;


import com.itpetshelter.itpetshelter.domain.Consumer;
import com.itpetshelter.itpetshelter.dto.ConsumerJoinDTO;
import com.itpetshelter.itpetshelter.dto.upload.UploadResultDTO;
import org.springframework.web.multipart.MultipartFile;


public interface ConsumerService {
    // 중복 아이디 예외처리
    static class CidExistException extends Exception {

    }
    void join(ConsumerJoinDTO consumerJoinDTO) throws CidExistException;

    default Consumer dtoToEntity(ConsumerJoinDTO memberJoinDTO) {

        Consumer consumer = Consumer.builder()
                .Cid(memberJoinDTO.getCid())
                .Cpw(memberJoinDTO.getCpw())
                .email(memberJoinDTO.getEmail())
                .uuid(memberJoinDTO.getUuid())
                .fileName(memberJoinDTO.getFileName())
                .build();


        return consumer;

    } // dtoToEntity 닫기.

    // entityToDTO
    // 화면(DTO) ->  컨트롤러 ->서비스(각 변환작업을함.) - Entity 타입으로 - DB
    default ConsumerJoinDTO entityToDTO(Consumer consumer) {
        ConsumerJoinDTO consumerJoinDTO = ConsumerJoinDTO.builder()
                .Cid(consumer.getCid())
                .Cpw(consumer.getCpw())
                .email(consumer.getEmail())
                .uuid(consumer.getUuid())
                .fileName(consumer.getFileName())
                .build();


        return consumerJoinDTO;
    }

    //프로필 이미지 업로드
    public UploadResultDTO uploadProfileImage(MultipartFile fileImageName);


}
