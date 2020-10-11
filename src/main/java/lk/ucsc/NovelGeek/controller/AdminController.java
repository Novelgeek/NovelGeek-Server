package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.dto.GenreStats;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.UserSignInModel;
import lk.ucsc.NovelGeek.model.request.UserSignUpModel;
import lk.ucsc.NovelGeek.model.response.AuthResponse;
import lk.ucsc.NovelGeek.service.AdminService;
import lk.ucsc.NovelGeek.service.PostService;
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

    @Autowired
    PostService postService;

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
    public List<Integer> getUserStats(){
        return adminService.getUserStats();
    }


    @DeleteMapping("{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable(value="adminId") Long adminId){
        return ResponseEntity.ok(statService.deleteAdmin(adminId));
    }


    //ADMIN REPORTED POST HANDLING
    @GetMapping("/posts/getreports")
    public ResponseEntity<?> getReports(){
        return ResponseEntity.ok(postService.getReports());
    }

    @GetMapping("/posts/getreportedpost/{postid}")
    public ResponseEntity<?> getReportedPost(@PathVariable(value="postid")long postid){
        return ResponseEntity.ok(postService.getReportedPost(postid));
    }

    @GetMapping("/posts/getreporteddata/{postid}")
    public ResponseEntity<?> getReportedData(@PathVariable(value="postid")long postid){
        return ResponseEntity.ok(postService.getReportedData(postid));
    }

    @DeleteMapping("posts/deletereportedpost/{postid}")
    public ResponseEntity<?> deleteReportedPost(@PathVariable(value="postid") long postid ){
        return ResponseEntity.ok(postService.deleteReportedPost(postid));
    }

    @DeleteMapping("posts/cancelreport/{postid}")
    public ResponseEntity<?> cancelReportedPost(@PathVariable(value="postid") long postid ){
        return ResponseEntity.ok(postService.cancelReportedPost(postid));
    }

}
