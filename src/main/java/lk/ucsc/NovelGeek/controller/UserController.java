package lk.ucsc.NovelGeek.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class UserController {

    @PostMapping(path="/changeName",consumes = "applicatoin/json",produces = "application/json")
    public String changeUserName(){
        System.out.println("came to server");
        return "User working";
    }

    @GetMapping(path = "/user/1")
    public String getUser(){
        return "User working";
    }
}
