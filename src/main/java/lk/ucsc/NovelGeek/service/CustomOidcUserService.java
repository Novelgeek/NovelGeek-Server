package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.exception.OAuth2AuthenticationProcessingException;
import lk.ucsc.NovelGeek.model.User;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.GoogleOAuth2UserInfo;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

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
        userInfo.setProviderId((String) attributes.get("sub"));
        userInfo.setImageUrl((String) attributes.get("picture"));
        userInfo.setName((String) attributes.get("given_name"));
        userInfo.setProvider(userRequest.getClientRegistration().getRegistrationId());

        updateUser(userInfo);

        return oidcUser;
    }

    private void updateUser(GoogleOAuth2UserInfo userInfo)  {
        Users user = userRepository.findByEmail(userInfo.getEmail());
        if(user == null) {
            user = new Users();
            user.setEmail(userInfo.getEmail());
            user.setUsername(userInfo.getName().toLowerCase());
            user.setImageUrl(userInfo.getImageUrl());
            user.setProvider(userInfo.getProvider());
            user.setProviderId(userInfo.getProviderId());
            user.setRole("USER");
            userRepository.save(user);
        }else {
            if(!user.getProvider().equals(userInfo.getProvider())){
                throw new OAuth2AuthenticationProcessingException("Looks like you're trying to sign with your "+ user.getProvider() + " account. Please use your " + user.getProvider() +  " account to login.");
            }
        }
        //TODO: update user
    }


}