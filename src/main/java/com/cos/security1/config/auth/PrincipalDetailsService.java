package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 시큐리티 설정에서 loginProcessingUrl("/login")
 * /login 요청이 오면 자동으로 UserDetailsService 타입으로 IOC 되어 있는 loadUserByUsername 함수가 실행
 * @service로 IOC 등록한 것.
 * */
@Service
public class PrincipalDetailsService implements UserDetailsService {

    /**
     * login에서 넘어올 때 form의 name 어트리뷰트의 값이 username이면 자동으로 주입됨.
     * 만약 다른 name으로 하고싶다면, SecurityConfig에서 .usernameParameter("원하는 name value")로
     * 변경해줘야함.
     * */

    @Autowired
    private UserRepository userRepository;

    /**
     * 시큐리티 session( 내부 Authentication내무 ( UserDetails ) )
     * 함수 종료시 @AuthenticationPrincipal 어노테이션 만들어짐.
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null ){
            return new PrincipalDetails(userEntity);
        } else {
            return null;
        }
    }

}
