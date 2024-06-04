package bridge.backend.domain.user.controller;

import bridge.backend.domain.user.entity.dto.JoinDTO;
import bridge.backend.domain.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@ResponseBody
public class MemberController {
    private final MemberService memberService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDTO joinDTO){
        memberService.join(joinDTO);
        return new ResponseEntity<>("회원가입이 정상적으로 완료되었습니다", HttpStatus.CREATED);
    }

    @PostMapping("/test")
    public String test(){
        return "success test";
    }
}
