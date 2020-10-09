package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.dto.GroupDetailedDto;
import lk.ucsc.NovelGeek.dto.GroupDto;
import lk.ucsc.NovelGeek.dto.GroupUsersDto;
import lk.ucsc.NovelGeek.enums.MemberStatus;
import lk.ucsc.NovelGeek.model.Posts;
import lk.ucsc.NovelGeek.model.group.Group;
import lk.ucsc.NovelGeek.model.group.Members;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.notification.GroupNotification;
import lk.ucsc.NovelGeek.model.request.NewGroupRequest;
import lk.ucsc.NovelGeek.model.response.PostResponse;
import lk.ucsc.NovelGeek.repository.*;
import lk.ucsc.NovelGeek.repository.group.GroupNotificatioRepository;
import lk.ucsc.NovelGeek.repository.group.GroupRepository;
import lk.ucsc.NovelGeek.repository.group.MemberRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    GroupNotificatioRepository groupNotificatioRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PostReportRepository postReportRepository;

    // helpers

    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        return currentUser;
    }

    public GroupDto addMember(Long groupId, Long userId){
        Optional<Users> user = authRepository.findById(userId);
        Optional<Group> group = groupRepository.findById(groupId);
        if(user.isPresent() && group.isPresent()){
            Members member = new Members();
            member.setUsers(user.get());
            member.setGroup(group.get());
            member.setMemberStatus(MemberStatus.MEMBER);
            member.setJoinedDate(new Date());
            Members createdMember = memberRepository.save(member);
            GroupDto groupDto = new GroupDto(createdMember.getGroup(), user.get());
            return groupDto;
        } else {
            return null;
        }
    }




    public GroupDto createGroup(NewGroupRequest newGroupRequest){
        //creating a new group
        Group newGroup = new Group();
        BeanUtils.copyProperties(newGroupRequest,newGroup);
        newGroup.setCreatedOn(new Date());
        Group createdGroup = groupRepository.save(newGroup);

        // adding the current user as the owner of the group
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Members newMember = new Members();
        newMember.setGroup(createdGroup);
        newMember.setJoinedDate(new Date());
        newMember.setMemberStatus(MemberStatus.CREATOR);
        newMember.setUsers(authRepository.findByEmail(auth.getName()));
        memberRepository.save(newMember);

        GroupDto returnGroup = new GroupDto();
        BeanUtils.copyProperties(createdGroup, returnGroup);
        returnGroup.setMember(true);
        returnGroup.setMemberCount(1);
        return returnGroup;
    }

    public Group updateGroup(NewGroupRequest newGroupRequest, Long groupId)  {
        Optional<Group> group = groupRepository.findById(groupId);
        if (group.get() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found");
        }

        BeanUtils.copyProperties(newGroupRequest, group.get());
        return groupRepository.save(group.get());
    }

    public List<GroupDto> getAllGroups() {
        List<GroupDto> groupDto = groupRepository.findAll().stream().map( group -> new GroupDto(group, this.getCurrentUser())).collect(Collectors.toList());
        return groupDto;
    }

    public List<GroupDto> getUserGroups(Long userId) {
        List<Members> groups = memberRepository.findByUsers(authRepository.findById(userId));
        List<GroupDto> groupDtos = groups.stream().map(member -> new GroupDto(member.getGroup(), authRepository.findById(userId).get())).collect(Collectors.toList());
        return groupDtos;
    }

    public List<GroupUsersDto> getAllUsers(Long groupId) {
        Set<Members> members = groupRepository.findById(groupId).get().getMembers();
        List<GroupUsersDto> groupUsersDtos =
                authRepository.findByEmailNotAndRole(this.getCurrentUser().getEmail(), "USER")
                .stream().map(user -> new GroupUsersDto(user, members)).collect(Collectors.toList());
        return groupUsersDtos;
    }



    // invites

    public Object inviteUser(Long groupId, Long userId) {
        Optional<Users> user = authRepository.findById(userId);
        Optional<Group> group = groupRepository.findById(groupId);
        Users currentUser = this.getCurrentUser();
        GroupNotification groupNotificationExists =
                groupNotificatioRepository.findByFiredUserAndTargetUserAndNotiType(currentUser, user.get(), "INVITED");
        // if already invited by the fired user send this
        if (groupNotificationExists != null) {
            return groupNotificationExists;
        }
        if(user.isPresent() && group.isPresent()){
            GroupNotification groupNotification = new GroupNotification();
            groupNotification.setGroup(group.get());
            groupNotification.setFiredUser(currentUser);
            groupNotification.setTargetUser(user.get());
            groupNotification.setNotiType("INVITED");
            groupNotification.setSeen(false);
            notificationRepository.save(groupNotification);

            return groupNotification;
        }
        else {
            return false;
        }
    }

    public GroupDto acceptInvite(Long inviteId) {

        Optional<GroupNotification> invite = groupNotificatioRepository.findById(inviteId);
        GroupDto groupDto = this.addMember(invite.get().getGroup().getGroupId(), invite.get().getTargetUser().getId());
        groupNotificatioRepository.deleteAll(groupNotificatioRepository.findByTargetUserAndGroup( invite.get().getTargetUser(), invite.get().getGroup()));
        return groupDto;
    }

    public Object declineInvite(Long inviteId) {
        groupNotificatioRepository.deleteById(inviteId);
        return null;
    }

    public List<?> getGroupInvites(Long userId) {
        List<Members> members = memberRepository.findByUsersAndMemberStatus(authRepository.findById(userId), MemberStatus.INVITED);
        List<GroupNotification> groupNotifications = groupNotificatioRepository.findByNotiTypeAndTargetUser("INVITED", authRepository.findById(userId).get());
        return groupNotifications;
    }



    // requests



    public boolean requestMembership(Long groupId) {
        Users currentUser = this.getCurrentUser();
        Optional<Group> group = groupRepository.findById(groupId);
        List<Members> member = memberRepository.findByUsersAndGroup(Optional.ofNullable(currentUser), group);
        if(member.size() != 0){

        }

        GroupNotification groupNotificationExists =
                groupNotificatioRepository.findByFiredUserAndTargetUserAndNotiType(currentUser, currentUser, "REQUESTED");
        // if already invited by the fired user send this
        if (groupNotificationExists != null) {
            return true;
        }

        if(group.isPresent()){
            GroupNotification groupNotification = new GroupNotification();
            groupNotification.setGroup(group.get());
            groupNotification.setFiredUser(currentUser);
            groupNotification.setTargetUser(currentUser);
            groupNotification.setNotiType("REQUESTED");
            groupNotification.setSeen(false);
            notificationRepository.save(groupNotification);
            return true;
        }
        else {
            return false;
        }

    }

    public List<GroupNotification> getRequests(Long groupId) {
        List<GroupNotification> groupNotifications = groupNotificatioRepository.findByNotiType("REQUESTED");
        return groupNotifications;
    }

    public GroupDto acceptRequest(Long requestId) {
        Optional<GroupNotification> request = groupNotificatioRepository.findById(requestId);
        GroupDto groupDto = this.addMember(request.get().getGroup().getGroupId(), request.get().getTargetUser().getId());
        groupNotificatioRepository.deleteAll(groupNotificatioRepository.findByTargetUserAndGroup( request.get().getTargetUser(), request.get().getGroup()));

        return groupDto;
    }




    public Object leaveGroup(Long groupId) {
        Users currentUser = this.getCurrentUser();

        List<Members> member = memberRepository.findByUsersAndGroup(Optional.ofNullable(currentUser), groupRepository.findById(groupId));
        memberRepository.delete(member.get(0));
        Group group = groupRepository.findById(groupId).get();
        List<Members> admins = memberRepository.findByGroupAndMemberStatus(group, MemberStatus.ADMIN);
        List<Members> creators = memberRepository.findByGroupAndMemberStatus(group, MemberStatus.CREATOR);
        if (((member.get(0).getMemberStatus() == MemberStatus.ADMIN) || (member.get(0).getMemberStatus() == MemberStatus.CREATOR))
                && ((admins.size() ==0 || creators.size() ==0)) ){
            List<Members> newAdmin = memberRepository.findByGroup(group);
            newAdmin.get(0).setMemberStatus(MemberStatus.ADMIN);
            memberRepository.save(newAdmin.get(0));
        }

        if (group.getMembers().size()==0) {
            groupRepository.deleteById(groupId);
        }

        return null;
    }

    public Object removeUser(Long groupId, Long userId) {
        memberRepository.deleteAll( memberRepository.findByUsersAndGroup(authRepository.findById(userId), groupRepository.findById(groupId)));
        return null;
    }

    public Object deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
        return null;
    }



    // getting group posts
    public GroupDetailedDto getSingleGroup(Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.get() == null)
            throw new NoSuchElementException("Cant find group");
        Users currentUser = this.getCurrentUser();
        GroupDetailedDto groupDetailedDto = new GroupDetailedDto();
        BeanUtils.copyProperties(group.get(), groupDetailedDto);
        group.get().getMembers().forEach(member -> {
            if(member.getUsers().getId() == currentUser.getId()) {
                groupDetailedDto.setMember(true);
                if(member.getMemberStatus() != MemberStatus.MEMBER) {
                    groupDetailedDto.setAdmin(true);
                }
            }
        });

        List<Posts> posts = group.get().getPosts().stream().map(groupPosts -> groupPosts.getPosts()).collect(Collectors.toList());
        List<PostResponse> postDetails = this.getPostDetails(posts);
        groupDetailedDto.setPosts(postDetails);

        return groupDetailedDto;
    }

    public List<PostResponse> getPostDetails(List<Posts> groupPosts){
        //List <Posts> posts = postRepository.findBySharedtype("public");
        List <PostResponse> posts = groupPosts.stream().map(post->{
            PostResponse response = new PostResponse();
            BeanUtils.copyProperties(post, response);

            //set owner
            response.setUsername(post.getUsers().getUsername());

            //check whether the request is send by owner or not
            if(post.getUsers().getId()==this.getCurrentUser().getId()){
                response.setOwned(true);
            }else{
                response.setOwned(false);
            }

            //check whether already liked or not
            if(postLikeRepository.checkIsLiked(post.getPostid(),this.getCurrentUser().getId())==1){
                response.setLiked(true);
            }else if (postLikeRepository.checkIsLiked(post.getPostid(),this.getCurrentUser().getId())==0){
                response.setLiked(false);
            }

            //check whether the current user reported the post or not
            if(postReportRepository.checkIsReported(post.getPostid(), this.getCurrentUser().getId())==1){
                response.setReported(true);
            }else if(postReportRepository.checkIsReported(post.getPostid(), this.getCurrentUser().getId())==0){
                response.setReported(false);
            }

            //setlikes counts of post
            long count = postLikeRepository.countLikes(post.getPostid());
            response.setLikecount(count);

            //setcomment count of post
            count = postCommentRepository.countComments(post.getPostid());
            response.setCommentcount(count);
            return response;
        }).collect(Collectors.toList());

        return posts;
    }

}
