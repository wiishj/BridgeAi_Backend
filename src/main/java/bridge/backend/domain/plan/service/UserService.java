package bridge.backend.domain.plan.service;

import bridge.backend.domain.plan.entity.User;
import bridge.backend.domain.plan.entity.dto.UserRequestDTO;
import bridge.backend.domain.plan.entity.dto.UserResponseDTO;
import bridge.backend.domain.plan.repository.UserRepository;
import bridge.backend.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import static bridge.backend.global.exception.ExceptionCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final String PHONENUMBER_PATTERN = "\\d{3}-\\d{4}-\\d{4}";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final DateTimeFormatter BIRTH_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public User findMemberById(Long id){
        return userRepository.findById(id).orElseThrow(()->new BadRequestException(NOT_FOUND_USER_ID));
    }
    @Transactional
    public UserResponseDTO saveMember(UserRequestDTO userRequestDTO){
        if(userRequestDTO.isNull()){
            throw new BadRequestException(INVALID_MEMBER_REQUEST);
        }
        if(!Pattern.matches(PHONENUMBER_PATTERN, userRequestDTO.getPhoneNumber())){
            throw new BadRequestException(INVALID_MEMBER_PHONENUMBER);
        }
        if(!Pattern.matches(EMAIL_PATTERN, userRequestDTO.getEmail())){
            throw new BadRequestException(INVALID_MEMBER_EMAIL);
        }
        if(!isValidBirth(userRequestDTO.getBirth())){
            throw new BadRequestException(INVALID_MEMBER_BIRTH);
        }
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setBirth(userRequestDTO.getBirth());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());

        userRepository.save(user);
        return UserResponseDTO.from(user);
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
    public void updateMember(Long id, UserRequestDTO userRequestDTO){
        if(userRequestDTO.isNull()){
            throw new BadRequestException(INVALID_MEMBER_REQUEST);
        }
        if(!Pattern.matches(PHONENUMBER_PATTERN, userRequestDTO.getPhoneNumber())){
            throw new BadRequestException(INVALID_MEMBER_PHONENUMBER);
        }
        if(!Pattern.matches(EMAIL_PATTERN, userRequestDTO.getEmail())){
            throw new BadRequestException(INVALID_MEMBER_EMAIL);
        }
        if(!isValidBirth(userRequestDTO.getBirth())){
            throw new BadRequestException(INVALID_MEMBER_BIRTH);
        }
        User user= findMemberById(id);
        user.setName(userRequestDTO.getName());
        user.setBirth(userRequestDTO.getBirth());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());

    }

}
