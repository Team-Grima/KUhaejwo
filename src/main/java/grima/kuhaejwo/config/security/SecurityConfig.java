package grima.kuhaejwo.config.security;

import grima.kuhaejwo.domain.user.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    UserAuthenticationFailureHandler getFailurlHandler() {
//        return new UserAuthenticationFailureHandler();
//    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
                "/v3/api-docs/**","/swagger-resources/**","/swagger-ui/**","/webjars/**","/swagger/**","/swagger-ui.html"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        authenticationManager = authenticationManagerBuilder.build();


        http

                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/",
                        "/api/login").permitAll()
                .antMatchers("/admin/**")
                .hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .authenticationManager(authenticationManager)
//                .formLogin()
//                .loginPage("/member/login")
//                .defaultSuccessUrl("/")
//                .failureHandler(getFailurlHandler()).permitAll()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true)
//                .and()
                .exceptionHandling()
                .accessDeniedPage("/error/denied");
        ;
        return http.build();
    }

//    @Bean
//    public AuthenticationManager configure(AuthenticationManagerBuilder auth) throws Exception {
//        return auth
//                .userDetailsService(memberService)
//                .passwordEncoder(getPasswordEncoder()).and().build();
//    }
}