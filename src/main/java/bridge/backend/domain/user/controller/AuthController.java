package bridge.backend.domain.user.controller;

import bridge.backend.domain.plan.entity.dto.ItemResponseDTO;
import bridge.backend.domain.plan.entity.dto.PlanRequestDTO;
import bridge.backend.domain.plan.entity.dto.PlanResponseDTO;
import bridge.backend.domain.plan.entity.dto.UserResponseDTO;
import bridge.backend.domain.user.entity.dto.LoginRequestDTO;
import bridge.backend.domain.user.entity.dto.TokenDTO;
import bridge.backend.domain.user.security.CustomOAuth2UserService;
import bridge.backend.domain.user.service.MemberService;
import bridge.backend.global.exception.BadRequestException;
import bridge.backend.global.exception.ExceptionCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static bridge.backend.global.exception.ExceptionCode.IS_NOT_REFRESHTOKEN;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;

//    @PostMapping("/join")
//    public ResponseEntity<?> join(@RequestBody LoginRequestDTO loginRequestDTO){
//        memberService.join(loginRequestDTO);
//        return ResponseEntity.ok("회원가입성공");
//    }
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refresh")){
                refreshToken = cookie.getValue();;
            }
        }
        if(refreshToken==null){
            throw new BadRequestException(IS_NOT_REFRESHTOKEN);
        }
        TokenDTO tokens = memberService.reissue(refreshToken);
        response.setHeader("Authorization", tokens.getAccessToken());
        response.addCookie(createCookie("refresh", tokens.getRefreshToken()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
