package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.Poll;
import lk.ucsc.NovelGeek.model.request.NewPollRequest;
import lk.ucsc.NovelGeek.model.response.PollResponse;
//import lk.ucsc.NovelGeek.service.PollService;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import lk.ucsc.NovelGeek.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("poll")
public class PollController {

    @Autowired
    private PollService pollService;

    //Pollresponse??
//    @GetMapping()
//    public List<Poll> list() {
//        return pollService.getAll();
//    }

    @PostMapping("/newpoll")
    public ResponseEntity<?> createPoll (@RequestBody NewPollRequest newPollRequest){
        return ResponseEntity.status(201).body(pollService.savePoll(newPollRequest));

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPolls(){return ResponseEntity.ok(pollService.getAllPolls());}


    @PostMapping("{pollid}/vote/{optionid}")
    public ResponseEntity<?> post(@PathVariable(value="pollid") Long pollid, @PathVariable(value="optionid") Long optionid){
        pollService.vote(pollid, optionid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getMyPolls(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal user = (UserPrincipal)auth.getPrincipal();
        return ResponseEntity.ok(pollService.getUserPolls());
    }



}
