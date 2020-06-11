package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.ConfirmationToken;
import lk.ucsc.NovelGeek.model.request.UserSignInModel;
import lk.ucsc.NovelGeek.model.request.UserSignUpModel;
import lk.ucsc.NovelGeek.model.response.AuthResponse;
import lk.ucsc.NovelGeek.model.response.UserResponse;
import lk.ucsc.NovelGeek.repository.ConfirmationTokenRepository;
import lk.ucsc.NovelGeek.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;



    @PostMapping("auth/signup")
    public UserResponse createUser(@RequestBody UserSignUpModel userSignUpModel) {
        UserResponse userResponse = new UserResponse();


        UserResponse createdUser = authService.createUser(userSignUpModel);
        BeanUtils.copyProperties(createdUser, userResponse);
        return userResponse;
    }


    @GetMapping("auth")
    public String greeting() {
        return "Working";
    }


    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody UserSignInModel loginRequest) throws Exception {
        AuthResponse authResponse =  authService.login(loginRequest);

        return ResponseEntity.ok(authResponse);
    }

    @RequestMapping(value="confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        authService.confirmUser(confirmationToken);
        return ResponseEntity.ok("Confirmed user");
    }

    @PostMapping("auth/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) {
        authService.forgotPassword(payload.get("email"));
        return ResponseEntity.ok(null);
    }

    @PostMapping("auth/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        authService.resetPassword(payload.get("password"), payload.get("token"));
        return ResponseEntity.ok(null);
    }



}
