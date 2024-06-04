package bridge.backend.domain.user.service;

import bridge.backend.domain.user.entity.Member;
import bridge.backend.domain.user.entity.dto.JoinDTO;
import bridge.backend.domain.user.repository.MemberRepository;
import bridge.backend.global.exception.BadRequestException;
import bridge.backend.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bridge.backend.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long join(JoinDTO joinDTO){
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        Boolean isExist = memberRepository.existsByUsername(username);

        if(isExist) return null;

        Member entity = new Member();
        entity.setUsername(username);
        entity.setPassword(bCryptPasswordEncoder.encode(password));
        entity.setRole("ROLE_ADMIN");
        memberRepository.save(entity);


        return entity.getId();
    }

    public Member findMemberByUsername(String username){
        return memberRepository.findByUsername(username).orElseThrow(()-> new BadRequestException(NOT_FOUND_MEMBER_ID));
    }
}
