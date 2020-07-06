package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.exception.OAuth2AuthenticationProcessingException;
import lk.ucsc.NovelGeek.model.UserDetails;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.GoogleOAuth2UserInfo;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.UserRepository;
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

    @Autowired
    private UserRepository userDetailsRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        Map attributes = oidcUser.getAttributes();
        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setProviderId((String) attributes.get("sub"));
        userInfo.setImageUrl((String) attributes.get("picture"));
        userInfo.setName((String) attributes.get("given_name"));
        userInfo.setVerified(true);
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
            user.setVerified(true);
            user.setRole("USER");
            Users storedUsers = userRepository.save(user);

            UserDetails userDetails = new UserDetails();
            userDetails.setName(userInfo.getName());
            userDetails.setUser(storedUsers);
            userDetailsRepository.save(userDetails);

        }else {
            if(!user.getProvider().equals(userInfo.getProvider())){
                throw new OAuth2AuthenticationProcessingException("Looks like you're trying to sign with your "+ user.getProvider() + " account. Please use your " + user.getProvider() +  " account to login.");
            }
        }
        //TODO: update user
    }


}