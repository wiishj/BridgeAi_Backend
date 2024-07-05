package bridge.backend.domain.user.service;

import bridge.backend.domain.user.entity.Member;
import bridge.backend.domain.user.entity.Role;
import bridge.backend.domain.user.entity.dto.LoginRequestDTO;
import bridge.backend.domain.user.entity.dto.TokenDTO;
import bridge.backend.domain.user.jwt.JwtProvider;
import bridge.backend.domain.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Transactional
    public void join(LoginRequestDTO requestDTO){
        Member member = Member.builder()
                .username(requestDTO.getUsername())
                .password(requestDTO.getPassword())
                .role(Role.ROLE_ADMIN)
                .build();
        memberRepository.save(member);
    }
    @Transactional
    public TokenDTO reissue(String refreshToken){
        return jwtProvider.reissue(refreshToken);
    }

}
