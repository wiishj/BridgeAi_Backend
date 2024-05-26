package bridge.backend.domain.service;

import bridge.backend.domain.entity.Business;
import bridge.backend.domain.entity.Member;
import bridge.backend.domain.entity.Type;
import bridge.backend.domain.entity.dto.BusinessRequestDTO;
import bridge.backend.domain.entity.dto.MemberRequestDTO;
import bridge.backend.domain.repository.MemberRepository;
import bridge.backend.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bridge.backend.global.exception.ExceptionCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private static final String PHONENUMBER_PATTERN = "\\d{3}-\\d{4}-\\d{4}";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final DateTimeFormatter BIRTH_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Member findMemberById(Long id){
        return memberRepository.findById(id).orElseThrow(()->new BadRequestException(NOT_FOUND_MEMBER_ID));
    }
    @Transactional
    public Long saveMember(MemberRequestDTO memberRequestDTO){
        if(memberRequestDTO.isNull()){
            throw new BadRequestException(INVALID_MEMBER_REQUEST);
        }
        if(!Pattern.matches(PHONENUMBER_PATTERN, memberRequestDTO.getPhoneNumber())){
            throw new BadRequestException(INVALID_MEMBER_PHONENUMBER);
        }
        if(!Pattern.matches(EMAIL_PATTERN, memberRequestDTO.getEmail())){
            throw new BadRequestException(INVALID_MEMBER_EMAIL);
        }
        if(!isValidBirth(memberRequestDTO.getBirth())){
            throw new BadRequestException(INVALID_MEMBER_BIRTH);
        }
        Member member = new Member();
        member.setName(memberRequestDTO.getName());
        member.setBirth(memberRequestDTO.getBirth());
        member.setEmail(memberRequestDTO.getEmail());
        member.setPhoneNumber(memberRequestDTO.getPhoneNumber());

        memberRepository.save(member);
        return member.getId();
    }
    private static Boolean isValidBirth(LocalDate birth){
        try{
            BIRTH_PATTERN.format(birth);
            return true;
        }catch(DateTimeParseException e){
            return false;
        }
    }
    @Transactional
    public void updateMember(Long id, MemberRequestDTO memberRequestDTO){
        if(memberRequestDTO.isNull()){
            throw new BadRequestException(INVALID_MEMBER_REQUEST);
        }
        if(!Pattern.matches(PHONENUMBER_PATTERN, memberRequestDTO.getPhoneNumber())){
            throw new BadRequestException(INVALID_MEMBER_PHONENUMBER);
        }
        if(!Pattern.matches(EMAIL_PATTERN, memberRequestDTO.getEmail())){
            throw new BadRequestException(INVALID_MEMBER_EMAIL);
        }
        if(!isValidBirth(memberRequestDTO.getBirth())){
            throw new BadRequestException(INVALID_MEMBER_BIRTH);
        }
        Member member = findMemberById(id);
        member.setName(memberRequestDTO.getName());
        member.setBirth(memberRequestDTO.getBirth());
        member.setEmail(memberRequestDTO.getEmail());
        member.setPhoneNumber(memberRequestDTO.getPhoneNumber());
    }

}
