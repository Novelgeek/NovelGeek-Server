package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @GetMapping("my-friends")
    public Object getMyFriends(){
        friendService.getMyFriends();
        return null;
    }

    @GetMapping("not-friends")
    public Object getNonFriends(){
        friendService.getNonFriends();
        return null;
    }

    @PostMapping("{userId}")
    public Object sendFriendRequest( @PathVariable(value="userId") Long userId) {
        friendService.sendFriendRequest(userId);
        return null;
    }

    @PostMapping("requests")
    public Object getFriendRequests() {
        friendService.getFriendRequests();
        return null;
    }


}
