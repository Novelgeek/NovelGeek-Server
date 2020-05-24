package lk.ucsc.NovelGeek.security;

import lk.ucsc.NovelGeek.model.Auth;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.util.CookieUtils;
import lk.ucsc.NovelGeek.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static lk.ucsc.NovelGeek.security.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private AuthRepository userRepository;

    private String homeUrl = "http://localhost:4200/";

    @Autowired
    private lk.ucsc.NovelGeek.security.HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
        if (response.isCommitted()) {
            return;
        }
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        System.out.println("lsjgj" + request.toString());

        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        Map attributes = oidcUser.getAttributes();
        String email = (String) attributes.get("email");
        Auth user = userRepository.findByEmail(email);
        //String token = JwtTokenUtil.generateToken(user);
        String token = "sg";
        System.out.println(token);
        String targetUrl = redirectUri.orElse(homeUrl);
        System.out.println(targetUrl);
        String redirectionUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("auth_token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
    }

}