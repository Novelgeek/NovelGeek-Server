package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.dto.GroupDto;
import lk.ucsc.NovelGeek.enums.MemberStatus;
import lk.ucsc.NovelGeek.model.Group;
import lk.ucsc.NovelGeek.model.Members;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewGroupRequest;
import lk.ucsc.NovelGeek.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    NotiEventRepository notiEventRepository;


    public Group createGroup(NewGroupRequest newGroupRequest){
        //creating a new group
        Group newGroup = new Group();
        BeanUtils.copyProperties(newGroupRequest,newGroup);
        newGroup.setCreatedOn(new Date());
        newGroup.setMemberCount(1);
        Group createdGroup = groupRepository.save(newGroup);

        // adding the current user as the owner of the group
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Members newMember = new Members();
        newMember.setGroup(createdGroup);
        newMember.setJoinedDate(new Date());
        newMember.setMemberStatus(MemberStatus.CREATOR);
        newMember.setUsers(authRepository.findByEmail(auth.getName()));
        memberRepository.save(newMember);

        return createdGroup;
    }

    public boolean addMember(Long groupId, Long userId){
        Optional<Users> user = authRepository.findById(userId);
        Optional<Group> group = groupRepository.findById(groupId);
        if(user.isPresent() && group.isPresent()){
            Members member = new Members();
            member.setUsers(user.get());
            member.setGroup(group.get());
            member.setMemberStatus(MemberStatus.MEMBER);
            member.setJoinedDate(new Date());
            memberRepository.save(member);
            return true;
        }
        else {
            return false;
        }

    }

    public List<Members> getMembers(Long groupId) {
        List<Members> members = memberRepository.findByGroup(groupRepository.findById(groupId));
        return members;
    }

    public List<GroupDto> getGroups(Long userId) {
        List<Members> groups = memberRepository.findByUsersAndMemberStatus(authRepository.findById(userId), MemberStatus.MEMBER);
        groups.addAll(memberRepository.findByUsersAndMemberStatus(authRepository.findById(userId), MemberStatus.CREATOR));
        groups.addAll(memberRepository.findByUsersAndMemberStatus(authRepository.findById(userId), MemberStatus.ADMIN));
        List<GroupDto> groupDtos = groups.stream().map(member -> new GroupDto(member.getGroup())).collect(Collectors.toList());
        return groupDtos;
    }

    public Object inviteUser(Long groupId, Long userId) {
        Optional<Users> user = authRepository.findById(userId);
        Optional<Group> group = groupRepository.findById(groupId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        List<Members> memberCheck = memberRepository.findByUsersAndGroup(user, group);
        if (memberCheck.size() != 0) {
            return false;
        }
        if(user.isPresent() && group.isPresent()){
            Members member = new Members();
            member.setUsers(user.get());
            member.setGroup(group.get());
            member.setMemberStatus(MemberStatus.INVITED);
            memberRepository.save(member);

//            Notification notification = new Notification();
//            notification.setTargetUser(user.get());
//            notification.setFiredUser(currentUser);
//            notification.setSeen(false);
//            notification.setEventId(notiEventRepository.findById((long) 1).get());
//            notificationRepository.save(notification);
            return group;
        }
        else {
            return false;
        }
    }

    public boolean requestMembership(Long groupId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        Optional<Group> group = groupRepository.findById(groupId);
        List<Members> memberCheck = memberRepository.findByUsersAndGroup(Optional.ofNullable(currentUser), group);
        if (memberCheck.size() != 0) {
            return false;
        }
        if(group.isPresent()){
            Members member = new Members();
            member.setUsers(currentUser);
            member.setGroup(group.get());
            member.setMemberStatus(MemberStatus.REQUESTED);
            member.setJoinedDate(new Date());
            memberRepository.save(member);
            return true;
        }
        else {
            return false;
        }

    }

    public List<GroupDto> getAllGroups() {

//        List<Group> group = groupRepository.findAll();
//        List<GroupDto> groupDto = new ArrayList<GroupDto>(group.size());
//        for (int i = 0; i < group.size(); i++) {
//            groupDto.add(new GroupDto());
//            BeanUtils.copyProperties(group.get(i), groupDto.get(i));
//        }
        List<GroupDto> groupDto = groupRepository.findAll().stream().map( group -> new GroupDto(group)).collect(Collectors.toList());
        return groupDto;
    }

    public List<?> getGroupInvites(Long userId) {
        List<Members> members = memberRepository.findByUsersAndMemberStatus(authRepository.findById(userId), MemberStatus.INVITED);
        return members;
    }

    public Optional<Group> getSingleGroup(Long groupId) {
//        Optional<Group> group = groupRepository.findById(groupId);
//        GroupDto groupDto = new GroupDto();
//        BeanUtils.copyProperties(group.get(), groupDto);
//        System.out.print(groupRepository.findById(groupId).get().getMembers().contains());
        return groupRepository.findById(groupId);
    }

    public List<Members> acceptInvite(Long groupId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Members> members = memberRepository.findByGroupAndUsersAndMemberStatus(groupRepository.findById(groupId), authRepository.findByEmail(auth.getName()), MemberStatus.INVITED);
        members.get(0).setMemberStatus(MemberStatus.MEMBER);
        memberRepository.save(members.get(0));
        return members;
    }

    public List<Members> getRequests(Long groupId) {
        return  memberRepository.findByGroupAndMemberStatus(groupRepository.findById(groupId), MemberStatus.REQUESTED);
    }

    public Object acceptRequest(Long userId, Long groupId) {
        List<Members> members = memberRepository.findByUsersAndGroup(authRepository.findById(userId), groupRepository.findById(groupId));
        members.get(0).setMemberStatus(MemberStatus.MEMBER);
        return memberRepository.save(members.get(0));
    }

    public Object leaveGroup(Long groupId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        List<Members> member = memberRepository.findByUsersAndGroup(Optional.ofNullable(currentUser), groupRepository.findById(groupId));
        memberRepository.delete(member.get(0));
        return null;
    }

    public Object removeUser(Long groupId, Long userId) {
        memberRepository.deleteAll( memberRepository.findByUsersAndGroup(authRepository.findById(userId), groupRepository.findById(groupId)));
        return null;
    }

    public Group updateGroup(NewGroupRequest newGroupRequest, Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        BeanUtils.copyProperties(newGroupRequest, group.get());
        return groupRepository.save(group.get());
    }

    public Object getRole(Long groupId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        List<Members> member = memberRepository.findByUsersAndGroup(Optional.ofNullable(currentUser), groupRepository.findById(groupId));
        return member.get(0);
    }
}
