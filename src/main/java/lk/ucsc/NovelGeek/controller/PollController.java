package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.Poll;
import lk.ucsc.NovelGeek.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @GetMapping()
    public List<Poll> list(){
        return pollService.getAll();
    }

    @GetMapping("user/{username}")
    public List<poll> getUserPolls(@PathVariable String username, Principal p){
        if(username,equals(p.getName())){
            return pollService.getAllForUser(username);
        }else{
            return pollService.getAllVisibleForUser(username);
        }
    }

    @GetMapping("/{id}")
    public Poll get(@PathVariable String id){
        return pollService.getPollById(Long.parseLong(id));
    }


}
