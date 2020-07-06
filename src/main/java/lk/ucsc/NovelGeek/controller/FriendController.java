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

        return friendService.acceptFriendRequest(userId);
    }

    @PostMapping("decline-request/{userId}")
    public Object declineFriendRequest( @PathVariable(value="userId") Long userId) {
        friendService.declineFriendRequest(userId);
        return null;
    }

    @GetMapping("requests")
    public Object getFriendRequests() {
        return friendService.getFriendRequests();
    }

    @PostMapping("unfriend/{userId}")
    public Object unfriendUser( @PathVariable(value="userId") Long userId) {
        friendService.unFriendUser(userId);
        return null;
    }

    @PostMapping("cancel-friend-request/{userId}")
    public Object cancelFriendRequest( @PathVariable(value="userId") Long userId) {
        friendService.cancelFriendRequest(userId);
        return null;
    }


}
