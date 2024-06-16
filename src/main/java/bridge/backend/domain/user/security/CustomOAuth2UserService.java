package bridge.backend.domain.user.security;

import bridge.backend.domain.user.entity.Member;
import bridge.backend.domain.user.entity.Role;
import bridge.backend.domain.user.infra.SocialType;
import bridge.backend.domain.user.infra.google.GoogleResponse;
import bridge.backend.domain.user.infra.kakao.KakaoResponse;
import bridge.backend.domain.user.infra.naver.NaverResponse;
import bridge.backend.domain.user.infra.OAuth2Response;
import bridge.backend.domain.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = null;
        OAuth2Response oAuth2Response = null;
        if(registrationId.equals("naver")){
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
            socialType = SocialType.NAVER;
        }else if(registrationId.equals("kakao")){
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
            socialType = SocialType.KAKAO;
        }else if(registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
            socialType = SocialType.GOOGLE;
        }else{
            //에러처리
            return null;
        }
        String username = oAuth2Response.getProviderId()+"@"+oAuth2Response.getProvider();
        Member member = findOrCreateMember(username, socialType);

        return new CustomOAuth2User(member);
    }
    private Member findOrCreateMember(String username, SocialType socialType){
        return memberRepository.findByUsername(username).orElseGet(()->createMember(username, socialType));
    }
    private Member createMember(String userame, SocialType socialType){
        Member member = Member.builder()
                .username(userame)
                .role(Role.ROLE_USER)
                .socialType(socialType)
                .build();
        memberRepository.save(member);
        return member;
    }
}
