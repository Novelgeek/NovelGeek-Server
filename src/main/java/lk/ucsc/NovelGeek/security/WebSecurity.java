package lk.ucsc.NovelGeek.security;

import lk.ucsc.NovelGeek.service.AuthService;
import lk.ucsc.NovelGeek.service.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final AuthService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomOidcUserService customOidcUserService;

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public AuthorizationRequestRepository customAuthorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    public WebSecurity(AuthService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/auth/signup")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login().userInfoEndpoint()
                .oidcUserService(customOidcUserService).and().authorizationEndpoint()
                .authorizationRequestRepository(customAuthorizationRequestRepository()).
                and().successHandler(customAuthenticationSuccessHandler);

        http.addFilter(new AuthenticationFilter(authenticationManager()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }



}
