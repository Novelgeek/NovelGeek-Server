package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.dto.UserDto;
import lk.ucsc.NovelGeek.model.request.UserSignInModel;
import lk.ucsc.NovelGeek.model.request.UserSignUpModel;
import lk.ucsc.NovelGeek.model.response.AuthResponse;
import lk.ucsc.NovelGeek.model.response.UserResponse;
import lk.ucsc.NovelGeek.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthService authService;





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
    public AuthResponse login(@RequestBody UserSignInModel loginRequest) {
        AuthResponse authResponse =  authService.login(loginRequest);

        return authResponse;
    }
}
