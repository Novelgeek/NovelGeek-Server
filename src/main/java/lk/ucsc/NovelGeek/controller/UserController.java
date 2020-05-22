package lk.ucsc.NovelGeek.controller;

import org.springframework.web.bind.annotation.PostMapping;

public class UserController {

    @PostMapping(path="/changeName",consumes = "applicatoin/json",produces = "application/json")
    public void changeUserName(){
        System.out.println("came to server");
    }
}
