package com.dotori.planner.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Log4j2
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("=================== Security configure : securityFilterChain ===================");

        // 1. 로그인 과정 생략(제작시 편의를 위해)
        //http.csrf(c->c.disable()); // CSRF 요청 비활성화 RestAPI 에서는 비활성화 안됨
        http.httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        //2. 로그인 과정 설정
        http.formLogin(login->{
           login.loginPage("/member/login")
                   .defaultSuccessUrl("/",true)
                   .usernameParameter("loginId")
                   .passwordParameter("password")
                   .failureUrl("/member/login/error")
                   .successHandler(new AuthenticationSuccessHandler() {
                       @Override
                       public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                           response.sendRedirect("/");
                       }
                   })
                   .failureHandler(new AuthenticationFailureHandler() {
                       @Override
                       public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                           log.info("=>exception:"+exception.getMessage());
                           response.sendRedirect("/member/login/error");
                       }
                   });
        });

        //3. 로그인 성공시, 리소스 접근 권한 설정
        http.authorizeHttpRequests(auth ->{
            //3.1 정적 리소스 접근 권한 부여
            auth.requestMatchers("/bs/**","/budget/**","/images/**","/main/**","/header/**").permitAll();
            //3.2 특정 리소스 권한 부여
            auth.requestMatchers("/","/member/**","/login/**").permitAll();
            //3.3 유저별로 권한 설정
            auth.requestMatchers("/admin/**").hasRole("ADMIN");
        });

        //4. 로그아웃 관련 설정
        //4.1 로그아웃 기본 설정
        //http.logout(Customizer.withDefaults());

        //4.2 로그아웃 커스텀
        http.logout(logout ->{
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .deleteCookies("remember-me","JESSIONID")
                    .invalidateHttpSession(true);
        });

        return http.build();
    }
}
