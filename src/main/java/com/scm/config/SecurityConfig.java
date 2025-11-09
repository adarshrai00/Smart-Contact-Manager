package com.scm.config;


import com.scm.serviceImpl.SecurityCustomUserDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService customUserDetailService;

    @Autowired
    private OAuthenticationSuccessHandler oAuthenticationSuccessHandler;
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails admin=  User.withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("admin").build();
//
//        UserDetails user=  User.withUsername("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("user").build();
//
//        return new InMemoryUserDetailsManager(admin,user);
//    }


//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(Auth-> {
                            Auth
                                    .requestMatchers("/user/**").authenticated();
                                    Auth.anyRequest().permitAll();
                        }





                );
//         this is form login
        http.formLogin(formlogin->
                {
                  formlogin.loginPage("/login")
                          .loginProcessingUrl("/authenticate")
                          .successForwardUrl("/user/pprofile")
//                          .failureUrl("/loginerr")
                          .usernameParameter("email")
                          .passwordParameter("password");


                });

        http.csrf(AbstractHttpConfigurer::disable);
        http.logout(logout->{
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout");
        });
        http.oauth2Login(oauth->
                oauth.loginPage("/login")
                        .successHandler(oAuthenticationSuccessHandler

                        )
        );


                return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return  daoAuthenticationProvider;
    }



     @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
     }






}
