package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

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
public class PrincipalDetails implements UserDetails {

    private User user; // 콤포지션

    public PrincipalDetails(User user) {
        this.user = user;
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


    /**
     *
     *
     *
     * */

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
}
