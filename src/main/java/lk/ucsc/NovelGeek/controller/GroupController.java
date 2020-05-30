package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.Group;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewGroupRequest;
import lk.ucsc.NovelGeek.model.response.SuccessResponse;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import lk.ucsc.NovelGeek.service.GroupService;
import net.minidev.json.JSONValue;
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
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @PostMapping("new")
    public ResponseEntity<?> createGroup(@RequestBody NewGroupRequest newGroupRequest) {
        Group createdGroup = groupService.createGroup(newGroupRequest);
        return ResponseEntity.ok(createdGroup);
    }

    @PostMapping("{groupId}/update")
    public ResponseEntity<?> updateGroup(@RequestBody NewGroupRequest newGroupRequest, @PathVariable(value="groupId") Long groupId) {
        Group createdGroup = groupService.updateGroup(newGroupRequest, groupId);
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
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.ok("Member Could not be invited");
        }
    }

    @PostMapping("{groupId}/acceptInvite")
    public ResponseEntity<?> acceptInvite(@PathVariable(value="groupId") Long groupId){
        return  ResponseEntity.ok(groupService.acceptInvite(groupId));
    }

    @PostMapping("{groupId}/requestMembership")
    public ResponseEntity<?> requestMembership(@PathVariable(value="groupId") Long groupId){
        boolean success = groupService.requestMembership(groupId);
        if(success){
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.ok("Membership couldnt be requested");
        }
    }

    @GetMapping("{groupId}/getRequests")
    public ResponseEntity<?> getRequests(@PathVariable(value="groupId") Long groupId){
        return ResponseEntity.ok(groupService.getRequests(groupId));
    }

    @GetMapping("{groupId}/acceptRequest/{userId}")
    public ResponseEntity<?> acceptRequest(@PathVariable(value="userId") Long userId, @PathVariable(value="groupId") Long groupId){
        return ResponseEntity.ok(groupService.acceptRequest(userId, groupId));
    }

    @GetMapping("getMembers/{groupId}")
    public ResponseEntity<?> getGroupMembers(@PathVariable(value="groupId") Long groupId) {
        return ResponseEntity.ok(groupService.getMembers(groupId));
    }

    @GetMapping("{groupId}")
    public ResponseEntity<?> getSingleGroup(@PathVariable(value="groupId") Long groupId) {
        return ResponseEntity.ok(groupService.getSingleGroup(groupId));
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

    @GetMapping("invites")
    public ResponseEntity<?> getGroupInvites() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal)auth.getPrincipal();
        return ResponseEntity.ok(groupService.getGroupInvites(Long.valueOf(user.getId())));
    }

    @GetMapping("{groupId}/leaveGroup")
    public ResponseEntity<?> leaveGroup(@PathVariable(value="groupId") Long groupId){
        return ResponseEntity.ok(groupService.leaveGroup(groupId));
    }

    @GetMapping("{groupId}/removeUser/{userId}")
    public ResponseEntity<?> leaveGroup(@PathVariable(value="groupId") Long groupId, @PathVariable(value="userId") Long userId){
        return ResponseEntity.ok(groupService.removeUser(groupId, userId));
    }

}
