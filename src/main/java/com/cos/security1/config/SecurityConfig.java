package com.cos.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


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
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                .loginPage("/loginForm");
    }

}
