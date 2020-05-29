package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.Group;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewGroupRequest;
import lk.ucsc.NovelGeek.model.response.SuccessResponse;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import lk.ucsc.NovelGeek.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllGroups(){
        SuccessResponse groups = new SuccessResponse();
        groups.setData("Working");
        return ResponseEntity.ok(groups);
    }

    @PostMapping("new")
    public ResponseEntity<?> createGroup(@RequestBody NewGroupRequest newGroupRequest) {
        Group createdGroup = groupService.createGroup(newGroupRequest);
        return ResponseEntity.ok(createdGroup);
    }

    @PostMapping("{groupId}/addMember/{userId}")
    public ResponseEntity<?> addSingleMember(@PathVariable(value="userId") Long userId, @PathVariable(value="groupId") Long groupId){
        boolean success = groupService.addMember(groupId, userId);
        if(success){
            return ResponseEntity.ok("Member Added");
        } else {
            return ResponseEntity.ok("Member Could be Added");
        }
    }

    @PostMapping("{groupId}/inviteUser/{userId}")
    public ResponseEntity<?> inviteUser(@PathVariable(value="userId") Long userId, @PathVariable(value="groupId") Long groupId){
        boolean success = groupService.inviteUser(groupId, userId);
        if(success){
            return ResponseEntity.ok("Member Invited");
        } else {
            return ResponseEntity.ok("Member Could not be invited");
        }
    }

    @GetMapping("getMembers/{groupId}")
    public ResponseEntity<?> getGroupMembers(@PathVariable(value="groupId") Long groupId) {
        return ResponseEntity.ok(groupService.getMembers(groupId));
    }

    @GetMapping("getGroups/{userId}")
    public ResponseEntity<?> getGroupsOfGivenUser(@PathVariable(value="userId") Long userId) {
        return ResponseEntity.ok(groupService.getGroups(userId));
    }

    @GetMapping("getGroups")
    public ResponseEntity<?> getMyGroups() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal)auth.getPrincipal();
        return ResponseEntity.ok(groupService.getGroups(Long.valueOf(user.getId())));
    }

}
