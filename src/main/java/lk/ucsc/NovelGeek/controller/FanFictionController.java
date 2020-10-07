package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.FanFiction;
import lk.ucsc.NovelGeek.model.FanFictionReview;
import lk.ucsc.NovelGeek.model.Test;
import lk.ucsc.NovelGeek.repository.FanFictionRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/fan-fiction")
@CrossOrigin
public class FanFictionController {

    @Autowired
    FanFictionService fanFictionService;

    @Autowired
    FanFictionRepository fanFictionRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    private AWSS3Service service;

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseEntity<?> add(@RequestParam String bookName,@RequestParam String title,@RequestParam String imageName,@RequestParam  String description, @RequestParam(value = "file", required = false) MultipartFile multipartFile){

        FanFiction fanFiction = new FanFiction();
        fanFiction.setBookName(bookName);
        fanFiction.setTitle(title);
        fanFiction.setDescription(description);
        fanFiction.setUserId(groupService.getCurrentUser().getId());
        return ResponseEntity.ok(fanFictionService.addFanFiction(fanFiction));
    }

    @PostMapping(path="/add-review")
    public ResponseEntity<?> addReview(@RequestBody Map<String, Object> payload){

        FanFictionReview fanFictionReview = new FanFictionReview();
        fanFictionReview.setUserId(groupService.getCurrentUser());
        fanFictionReview.setReview((String) payload.get("review"));
        fanFictionReview.setFanFictionId(fanFictionRepository.findById(Long.valueOf((String) payload.get("fanFictionId"))).get());
        fanFictionReview.setTimestamp(new Date());

        return ResponseEntity.ok(fanFictionService.addReview(fanFictionReview));
    }

    @GetMapping(path = "/get-all")
    public List<FanFiction> getAll(){
        return fanFictionService.getFanFictions();
    }

    @GetMapping(path = "/{id}")
    public FanFiction getSpecific(@PathVariable long id){
        return fanFictionService.getSpecificFanFiction(id);
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
