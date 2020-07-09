package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.Poll;
import lk.ucsc.NovelGeek.model.request.NewPollRequest;
import lk.ucsc.NovelGeek.model.response.PollResponse;
import lk.ucsc.NovelGeek.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("poll")
public class PollController {

    @Autowired
    private PollService pollService;

    @PostMapping("/newpoll")
    public ResponseEntity<?> createPoll (@RequestBody NewPollRequest newPollRequest){
        PollResponse savedPoll = pollService.savePoll(newPollRequest);
        return ResponseEntity.status(201).body(savedPoll);
    }


}
