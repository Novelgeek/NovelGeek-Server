package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Group;
import lk.ucsc.NovelGeek.model.Members;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewGroupRequest;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.GroupRepository;
import lk.ucsc.NovelGeek.repository.MemberRepository;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Date;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthRepository authRepository;

    public Group createGroup(NewGroupRequest newGroupRequest){
        Group newGroup = new Group();
        BeanUtils.copyProperties(newGroupRequest,newGroup);
        newGroup.setCreatedOn(new Date());

        Group createdGroup = groupRepository.save(newGroup);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //UserPrincipal user = (UserPrincipal)auth.getPrincipal();
        Members newMember = new Members();

        newMember.setGroup(createdGroup);
        newMember.setJoinedDate(new Date());
        newMember.setMembershipType("Owner");
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
            member.setMembershipType("Member");
            member.setJoinedDate(new Date());
            memberRepository.save(member);
            return true;
        }
        else {
            return false;
        }

    }

    public void getMembers(Long groupId) {
        System.out.println(memberRepository.findByUsers(groupId));
    }
}
