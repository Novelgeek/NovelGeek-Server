package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.FanFiction;
import lk.ucsc.NovelGeek.model.Test;
import lk.ucsc.NovelGeek.service.AWSS3Service;
import lk.ucsc.NovelGeek.service.FanFictionService;
import lk.ucsc.NovelGeek.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.GroupSequence;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fan-fiction")
@CrossOrigin
public class FanFictionController {

    @Autowired
    FanFictionService fanFictionService;

    @Autowired
    GroupService groupService;

    @Autowired
    private AWSS3Service service;

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseEntity<?> add(@RequestParam String bookName,@RequestParam String title,@RequestParam String imageName,@RequestParam  String description, @RequestParam(value = "file", required = false) MultipartFile multipartFile){
        String fileUrl;
        if (multipartFile == null){
            fileUrl = null;
        } else {
            fileUrl = service.uploadFile(multipartFile);
        }
        FanFiction fanFiction = new FanFiction();
        fanFiction.setBookName(bookName);
        fanFiction.setTitle(title);
        fanFiction.setImageName(imageName);
        fanFiction.setDescription(description);
        fanFiction.setFileUrl(fileUrl);
        fanFiction.setUserId(groupService.getCurrentUser().getId());
        return ResponseEntity.ok(fanFictionService.addFanFiction(fanFiction));
    }

    @GetMapping(path = "/get-all")
    public List<FanFiction> getAll(){
        return fanFictionService.getFanFictions();
    }

    @DeleteMapping(path = "/delete/{id}")
    public void delete(@PathVariable long id) {
         fanFictionService.deleteFanFiction(id);
    }

    @GetMapping(path  = "/get-fanfinctions-by-userid")
    public List<FanFiction> getFanFictionsByUserid() {
        long userId = groupService.getCurrentUser().getId();
        return fanFictionService.getFanFictionsByuserID(userId);
    }
}
