package com.cos.security1.config.oauth;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipleOauth2UserService extends DefaultOAuth2UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    /**
     * loadUser라는 것이 구글로부터 받은 userRequest 데이터에 대한 후처리해줌
     *
     * 프로세스 설명
     * 구글로그인 버튼 클릭 => 구글로그인창 => 로그인 완료 => 코드를 리턴 (OAuth-Client 라이브러리)
     *  => AccessToken 요청 => UserRequest 정보 => loadUser함수 => 회원프로필을 받을 수 있음
     * 해당 함수 종료시 @AuthenticationPrincipal 어노테이션 만들어짐.
     * */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest : "+ userRequest.getClientRegistration());
        System.out.println("userRequest : "+ userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes : "+ super.loadUser(userRequest).getAttributes());

        String provider = userRequest.getClientRegistration().getClientId(); // google
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "-" +providerId;
        String email = oAuth2User.getAttribute("email");
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            System.out.println("구글로그인이 최초입니다.");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        } else {
            System.out.println("구글로그인이 이미 한적이 있습니다. 당신은 자동회원가입 되었습니다. ");
        }
        // 회원가입을 강제로 진행해볼 예정
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
