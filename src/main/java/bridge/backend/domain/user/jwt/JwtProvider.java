package bridge.backend.domain.user.jwt;

import bridge.backend.domain.user.entity.dto.TokenDTO;
import bridge.backend.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JWTUtil jwtUtil;
    private final RedisService redisService;
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    public TokenDTO createJWT(String role, String username){
        String accessToken = jwtUtil.createJwt("access", role, username, ACCESS_TOKEN_EXPIRE_TIME);
        String refreshToken = jwtUtil.createJwt("refresh", role, username, REFRESH_TOKEN_EXPIRE_TIME);
        redisService.setValues(username, refreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        return TokenDTO.of(accessToken, refreshToken);
    }

    public TokenDTO reissue(String refreshToken){
        String category = jwtUtil.getCategory(refreshToken);
        if(!category.equals("refresh")){
            //에러처리
        }
        String subject = jwtUtil.getSubject(refreshToken);
        String valueToken = redisService.getValues(subject);
        if(valueToken==null || !valueToken.equals(refreshToken) || !jwtUtil.getCategory(valueToken).equals("refresh")){
            //에러처리
        }
        String role = jwtUtil.getRole(refreshToken);
        return createJWT(role, subject);
    }
}
