package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Auth;
import lk.ucsc.NovelGeek.model.GoogleOAuth2UserInfo;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private AuthRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map attributes = oidcUser.getAttributes();
        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setId((String) attributes.get("sub"));
        userInfo.setImageUrl((String) attributes.get("picture"));
        userInfo.setName((String) attributes.get("name"));
        updateUser(userInfo);
        return oidcUser;
    }

    private void updateUser(GoogleOAuth2UserInfo userInfo) {
        Auth user = userRepository.findByEmail(userInfo.getEmail());
        if(user == null) {
            user = new Auth();
        }
        user.setEmail(userInfo.getEmail());
        user.setUsername(userInfo.getName());
        user.setImageUrl(userInfo.getImageUrl());
        userRepository.save(user);
    }
}