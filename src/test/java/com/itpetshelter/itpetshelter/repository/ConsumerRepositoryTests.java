package com.itpetshelter.itpetshelter.repository;


import com.itpetshelter.itpetshelter.domain.Consumer;
import com.itpetshelter.itpetshelter.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ConsumerRepositoryTests {
    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMemberTest() {
        // 샘플로 100명의 더미 디비 넣기. 병렬처리
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Consumer consumer = Consumer.builder()
                    .Cid("이상용" + i)
                    // 주의사항, 멤버 넣을 때, 패스워드 평문 안됨, 암호화 필수.
                    .Cpw(passwordEncoder.encode("1234"))
                    .email("lsy" + i + "@gmail.com")
                    .build();
            // 권한주기. USER, ADMIN
            consumer.addRole(MemberRole.USER);
            // 90번 이상부터는, 동시권한, USER 이면서 ADMIN 주기.
            if (i >= 90) {
                consumer.addRole(MemberRole.ADMIN);
            }

            // 엔티티 클래스를 저장, 실제 디비 반영이되는 비지니스 모델.
            consumerRepository.save(consumer);

        });
    } // 닫기

    @Test
    public void testRead() {
        Optional<Consumer> result = consumerRepository.getWithRoles("이상용100");
        Consumer consumer = result.orElseThrow();

        log.info("ConsumerRepositoryTests testRead, consumer:  " + consumer);
        log.info("ConsumerRepositoryTests testRead, consumer.getRoleSet():  " + consumer.getRoleSet());

        consumer.getRoleSet().forEach(memberRole -> {
            log.info("ConsumerRepositoryTests testRead, memberRole:  " + memberRole);
        });
    }
}
