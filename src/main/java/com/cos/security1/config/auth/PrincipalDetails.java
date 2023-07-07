package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *  시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킴.
 *  로그인 진행이 완료되먄, 시큐리티의 session을 만들어준다.
 *  같은 세션 공간이지만, 시큐리티가 자신만의 시큐리티 세션 공간을 가짐.
 *  key 값으로 구분함. (Security ContextHolder 여기세 세션 정보를 저장함. )
 *
 *  오브젝트 => Authentication 타입 객체
 *  Authentication 안에는 User 정보가 있어야함.
 *  User오브젝트타입 => UserDetails 타입 객체
 *
 *  Security Session => Authentication => UserDeatils(PrincipalDetails) 타입의 정보
 *
 * */
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; // 콤포지션
    private Map<String,Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user,Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // 해당 User의 권한을 return 하는 곳.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 오래 사용했는지
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        /**
         *  우리 사이트 1년 동안 회원으로 로그인 안하면, 휴면 계정으로 하기로 함.
         * */
        return true;
    }


    @Override
    public Map<String, Object> getAttributes() {


        return attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("sub");
    }
}
