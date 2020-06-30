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

        return friendService.getMyFriends();
    }

    @GetMapping("all-users")
    public Object getNonFriends(){

        return friendService.getAllUsers();
    }

    @PostMapping("send-request/{userId}")
    public Object sendFriendRequest( @PathVariable(value="userId") Long userId) {
        friendService.sendFriendRequest(userId);
        return null;
    }

    @PostMapping("accept-request/{userId}")
    public Object acceptFriendRequest( @PathVariable(value="userId") Long userId) {
        friendService.acceptFriendRequest(userId);
        return null;
    }

    @PostMapping("requests")
    public Object getFriendRequests() {
        friendService.getFriendRequests();
        return null;
    }


}
