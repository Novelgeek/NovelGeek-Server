package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    //getting details of logged in user (user who sends the request)
    @GetMapping("/me")
    public String getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal)auth.getPrincipal();
        //now you can use the user object to get the details of the user who sends the request
        System.out.println(user.getId());
        System.out.println(user.getAuthorities());
        System.out.println(user.getEmail());
        return "User working";
    }
}
