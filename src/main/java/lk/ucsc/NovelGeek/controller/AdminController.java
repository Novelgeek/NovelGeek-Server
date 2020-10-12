package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.dto.GenreStats;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.UserSignInModel;
import lk.ucsc.NovelGeek.model.request.UserSignUpModel;
import lk.ucsc.NovelGeek.model.response.AuthResponse;
import lk.ucsc.NovelGeek.service.AdminService;
import lk.ucsc.NovelGeek.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    StatService statService;

    @PostMapping("auth/signup")
    public Users createAdmin(@RequestBody UserSignUpModel userSignUpModel) {
        Users admin = adminService.createAdmin(userSignUpModel);
        return admin;
    }

    @GetMapping("all")
    public Object getAdmins(){
        return adminService.getAllAdmins();
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> loginAdmin(@RequestBody UserSignInModel loginRequest) {
        AuthResponse authResponse = adminService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("test")
    public String adminTest() {
       return  "Admin working";
    }

    @GetMapping("basic-stat")
    public ResponseEntity<?> getBasicStat(){

        return ResponseEntity.ok(statService.getBasicStat());
    }
    @GetMapping("genrestats")
    public ResponseEntity<GenreStats> getGenreStats(){
        return adminService.getGenreStats();

    }
    @GetMapping("userstats")
    public int[] getUserStats() throws Exception{
        return adminService.getUserStats();
    }

}
