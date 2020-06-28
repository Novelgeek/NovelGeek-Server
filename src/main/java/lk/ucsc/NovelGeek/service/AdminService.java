package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.UserSignInModel;
import lk.ucsc.NovelGeek.model.request.UserSignUpModel;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Users createAdmin(UserSignUpModel userDto) {
        if(authRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("Email already exists, Please try a different email");
        }

        Users users = new Users();

        BeanUtils.copyProperties(userDto, users);
        users.setUsername(userDto.getUsername().toLowerCase());
        users.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        users.setRole("ADMIN");
        users.setProvider("local");
        Users storedUsers = authRepository.save(users);

        return storedUsers;
    }

    public AuthResponse login(UserSignInModel loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Users user = authRepository.findByEmail(loginRequest.getEmail());
            String token = jwtTokenUtil.generateToken(user);
            return new AuthResponse(token, loginRequest.getEmail());
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password", e);
        }
    }

}
