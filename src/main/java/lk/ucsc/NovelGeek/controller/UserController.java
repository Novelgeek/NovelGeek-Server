package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.request.UserSignInModel;
import lk.ucsc.NovelGeek.model.response.UserDetailsResponse;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import lk.ucsc.NovelGeek.service.AWSS3Service;
import lk.ucsc.NovelGeek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private AWSS3Service awsService;

    //getting details of logged in user (user who sends the request)
    @GetMapping("/me")
    public Object getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal)auth.getPrincipal();
        //now you can use the user object to get the details of the user who sends the request

        System.out.println(user.getId());
        System.out.println(user.getAuthorities());
        System.out.println(user.getEmail());
        return  userService.getUserDetails(user.getId());

    }

    @GetMapping("/{userId}")
    public Object getUser(@PathVariable(value = "userId")Long userId){
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping("/save")
    public Object saveUser(@RequestBody UserDetailsResponse userDetailsResponse){
        System.out.println(userDetailsResponse.getUsername());
        userService.saveUserDetails(userDetailsResponse);
        return null;
    }



    @GetMapping()
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/{userId}/updateImage")
    public ResponseEntity<?> uploadImage(@RequestParam(value = "file", required = false) MultipartFile file){

        String filePath;
        if (file == null) {
            filePath = null;
        } else {
            filePath = awsService.uploadFile(file);
        }



        return ResponseEntity.ok(userService.uploadImage(filePath));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser (@RequestBody UserSignInModel password ){
        return ResponseEntity.ok(userService.deleteUser(password.getPassword()));
    }

}
