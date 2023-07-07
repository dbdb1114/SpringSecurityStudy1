package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 *  @EnableWebSecurity
 *      현재 작성하는 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
 *
 * .antMatchers("/user/**").authenticated()
 *  권한을 확인한다.
 * .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
 * .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN')")
 *  권한을 확인할 뿐만 아니라 해당하는 ROLE이 있는지 확인한다.
 *
 *  EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
 *  secured 어노테이션 활성화하는 것.
 *  @Secured("ROLE_ADMIN")라고 설정해둔 것에 조건을 걸어주는 것.
 *   prePostEnabled = true preAuthorize라는 어노테이션을 활성화해줌.
 *   preAuthorize와 postAuthorize 를 걸어둔 매퍼는 해당 매퍼 실행 직전에 활성화됨.
 *
 *  하나만 설정해주고 싶을땐 Secured를 걸어주고, 여러개 걸어주고싶으면 preAuthorize를 걸어주는게 좋음.
 *
 *  -->
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @Bean
     * 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
     * */
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                /**
                 * 아래는 loginForm으로 와서 로그인을 했을 때 슬래쉬로 보내주는 것이고,
                 * 만약에 다른 페이지로 요청을 했는데, 로그인 권한이 필요하여 로그인을 했다면,
                 * 처음에 요청한 다른 페이지로 보내준다.
                 * */
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // /login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행함.
                .defaultSuccessUrl("/");
    }

}
