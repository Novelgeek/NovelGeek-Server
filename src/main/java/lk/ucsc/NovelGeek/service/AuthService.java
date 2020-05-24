package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.dto.UserDto;
import lk.ucsc.NovelGeek.model.Auth;
import lk.ucsc.NovelGeek.model.request.UserSignInModel;
import lk.ucsc.NovelGeek.model.response.AuthResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.util.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public UserDto createUser(UserDto userDto) {
        if(authRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("Record already exists");
        }
        Auth auth = new Auth();
        BeanUtils.copyProperties(userDto, auth);

        auth.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        auth.setRole("user");

        Auth storedAuth = authRepository.save(auth);
        UserDto returnUser = new UserDto();
        BeanUtils.copyProperties(storedAuth, returnUser);

        return returnUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Auth auth = authRepository.findByEmail(email);

        if(auth == null) {
            throw new RuntimeException("Username not found");
        }
        return new User(auth.getEmail(), auth.getEncryptedPassword(), new ArrayList<>());
    }

    public AuthResponse login(UserSignInModel loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            //SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtil.generateToken(authentication);
            return new AuthResponse(token, loginRequest.getEmail());
        }
        catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }

    }
}
