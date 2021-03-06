package lk.ucsc.NovelGeek.controller;

import lk.ucsc.NovelGeek.model.group.Group;
import lk.ucsc.NovelGeek.model.request.NewGroupRequest;
import lk.ucsc.NovelGeek.model.request.NewPost;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import lk.ucsc.NovelGeek.service.AWSS3Service;
import lk.ucsc.NovelGeek.service.GroupService;
import lk.ucsc.NovelGeek.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    private PostService postService;

    // basic group functions


    @PostMapping("new")
    public ResponseEntity<?> createGroup(@RequestParam("groupName") String groupName,
                                         @RequestParam("description") String description,
                                         @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        String fileUrl;
        if (multipartFile == null){
            fileUrl = null;
        } else {
            fileUrl = awss3Service.uploadFile(multipartFile);
        }
        NewGroupRequest newGroupRequest = new NewGroupRequest();
        newGroupRequest.setDescription(description);
        newGroupRequest.setGroupName(groupName);
        newGroupRequest.setGroupAvatar(fileUrl);

        return ResponseEntity.ok(groupService.createGroup(newGroupRequest));
    }

    @PostMapping("{groupId}/update")
    public ResponseEntity<?> updateGroup(@RequestBody NewGroupRequest newGroupRequest, @PathVariable(value="groupId") Long groupId) {
        Group createdGroup = groupService.updateGroup(newGroupRequest, groupId);
        return ResponseEntity.ok(createdGroup);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllGroups(){
        return ResponseEntity.ok(groupService.getAllGroups());
    }


    @GetMapping("getAllUsers/{groupId}")
    public ResponseEntity<?> getAllUsers(@PathVariable(value="groupId") Long groupId) {
        return ResponseEntity.ok(groupService.getAllUsers(groupId));
    }

    @GetMapping("{groupId}")
    public ResponseEntity<?> getSingleGroup(@PathVariable(value="groupId") Long groupId) {
        return ResponseEntity.ok(groupService.getSingleGroup(groupId));
    }

    @GetMapping("getGroups/{userId}")
    public ResponseEntity<?> getGroupsOfGivenUser(@PathVariable(value="userId") Long userId) {
        return ResponseEntity.ok(groupService.getUserGroups(userId));
    }

    @GetMapping("getGroups")
    public ResponseEntity<?> getMyGroups() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal)auth.getPrincipal();
        return ResponseEntity.ok(groupService.getUserGroups(Long.valueOf(user.getId())));
    }


    //not in use
    @PostMapping("{groupId}/addMember/{userId}")
    public ResponseEntity<?> addSingleMember(@PathVariable(value="userId") Long userId, @PathVariable(value="groupId") Long groupId){
        return ResponseEntity.ok(groupService.addMember(groupId, userId));
    }



    // invites
    @PostMapping("{groupId}/inviteUser/{userId}")
    public ResponseEntity<?> inviteUser(@PathVariable(value="userId") Long userId, @PathVariable(value="groupId") Long groupId){
        return ResponseEntity.ok(groupService.inviteUser(groupId, userId));

    }

    @PostMapping("acceptInvite/{inviteId}")
    public ResponseEntity<?> acceptInvite(@PathVariable(value="inviteId") Long inviteId){
        return  ResponseEntity.ok(groupService.acceptInvite(inviteId));
    }

    @PostMapping("declineInvite/{inviteId}")
    public ResponseEntity<?> declineInvite(@PathVariable(value="inviteId") Long inviteId){
        return  ResponseEntity.ok(groupService.declineInvite(inviteId));
    }

    @GetMapping("invites")
    public ResponseEntity<?> getGroupInvites() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal)auth.getPrincipal();
        return ResponseEntity.ok(groupService.getGroupInvites(Long.valueOf(user.getId())));
    }



    // requests
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

    @GetMapping("acceptRequest/{requestId}")
    public ResponseEntity<?> acceptRequest(@PathVariable(value="requestId") Long requestId){
        return ResponseEntity.ok(groupService.acceptRequest(requestId));
    }


    @GetMapping("{groupId}/leaveGroup")
    public ResponseEntity<?> leaveGroup(@PathVariable(value="groupId") Long groupId){
        return ResponseEntity.ok(groupService.leaveGroup(groupId));
    }

    @GetMapping("{groupId}/removeUser/{userId}")
    public ResponseEntity<?> leaveGroup(@PathVariable(value="groupId") Long groupId, @PathVariable(value="userId") Long userId){
        return ResponseEntity.ok(groupService.removeUser(groupId, userId));
    }

    @DeleteMapping("{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable(value="groupId") Long groupId){
        return ResponseEntity.ok(groupService.deleteGroup(groupId));
    }


    @PostMapping("{groupId}/newpost")
    public ResponseEntity<?> createPost(@RequestParam("title") String title,
                                        @RequestParam("description") String description,
                                        @RequestParam("sharedtype") String sharedtype,
                                        @RequestParam(value = "file", required = false) MultipartFile file,
                                        @PathVariable(value="groupId") Long groupId)
    {
        String filePath;
        if (file == null) {
            filePath = null;
        } else {
            filePath = awss3Service.uploadFile(file);
        }

        NewPost newpost = new NewPost();
        newpost.setTitle(title);
        newpost.setDescription(description);
        newpost.setSharedType(sharedtype);
        newpost.setImagePath(filePath);

        return ResponseEntity.ok(postService.createGroupPost(newpost, groupId));
    }

}
