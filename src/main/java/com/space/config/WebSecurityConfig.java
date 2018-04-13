package com.space.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 请求授权
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable().headers().disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            //其他地址都要验证
            .anyRequest().authenticated()
            .and()
                .formLogin().loginPage("/eduInsLogin")
                .defaultSuccessUrl("/eduInsView/")
                .and()
                .logout()
                .logoutSuccessUrl("/eduInsLogin")
                .permitAll();



    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("abc")
                .password("abc")
                .roles("ADMIN");

    }
}
