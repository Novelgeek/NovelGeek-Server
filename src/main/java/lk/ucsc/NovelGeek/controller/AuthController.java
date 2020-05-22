package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.dto.UserDto;
import lk.ucsc.NovelGeek.model.request.UserSignInModel;
import lk.ucsc.NovelGeek.model.request.UserSignUpModel;
import lk.ucsc.NovelGeek.model.response.UserResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.UserRepository;
import lk.ucsc.NovelGeek.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthService authService;

//    @Autowired
//    private AuthenticationManager authenticationManager;



    @PostMapping("/signup")
    public UserResponse createUser(@RequestBody UserSignUpModel userSignUpModel) {
        UserResponse userResponse = new UserResponse();

        UserDto userDto = new UserDto();
        //copy details from userSignUpModel to userDto
        BeanUtils.copyProperties(userSignUpModel, userDto);

        UserDto createdUser = authService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, userResponse);
        return userResponse;
    }


    @GetMapping()
    public String greeting() {

        return "Working";
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserSignInModel loginRequest) {

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getEmail(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        //String token = tokenProvider.createToken(authentication);
        String token = "sdg";
        return ResponseEntity.ok(token);
    }
}
