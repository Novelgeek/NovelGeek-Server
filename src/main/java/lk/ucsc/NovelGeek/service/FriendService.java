package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.dto.FriendDto;
import lk.ucsc.NovelGeek.dto.GroupDto;
import lk.ucsc.NovelGeek.model.Friends;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendService {
    
    @Autowired
    AuthRepository authRepository;
    
    @Autowired
    FriendRepository friendRepository;

    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        return currentUser;
    }

    public Object getMyFriends() {
        List<FriendDto> friendDto = this.getCurrentUser().getFriends().stream().map(friend -> new FriendDto(friend)).collect(Collectors.toList());
        return friendDto;
    }

    public Object getAllUsers() {
        Users currentUser = this.getCurrentUser();
        List<FriendDto> friendDto = authRepository.findByEmailNotAndRole(currentUser.getEmail(),"USER")
                .stream().map(user -> new FriendDto(user, currentUser.getFriends())).collect(Collectors.toList());
        return friendDto;
    }

    public Object sendFriendRequest(Long userId) {
        Optional<Users> targetUser = authRepository.findById(userId);
        Users currentUser = this.getCurrentUser();

        Friends friendTest = friendRepository.findByUser1AndUser2(targetUser.get(), currentUser);
        if (friendTest != null) {
            return null;
        }

        Friends friend = new Friends();
        Friends friendMirror = new Friends();
        
        friend.setUser1(targetUser.get());
        friend.setUser2(currentUser);
        friend.setStatus("PENDING");

        friendMirror.setUser1(currentUser);
        friendMirror.setUser2(targetUser.get());
        friendMirror.setStatus("REQUESTED");
        
        friendRepository.save(friend);
        friendRepository.save(friendMirror);
        
        return null;
    }

    public Object getFriendRequests() {
        Users currentUser = this.getCurrentUser();
        List<Users> users = friendRepository.findByUser1AndStatus(currentUser, "PENDING")
                .stream().map(friend -> friend.getUser2()).collect(Collectors.toList());

        return users;
    }

    public Object acceptFriendRequest(Long userId) {
        Optional<Users> targetUser = authRepository.findById(userId);
        Users currentUser = this.getCurrentUser();

        Friends friend = friendRepository.findByUser1AndUser2(targetUser.get(), currentUser);
        if (friend.getStatus() == "FRIEND"){
            return null;
        }
        Friends friendMirror = friendRepository.findByUser1AndUser2(currentUser, targetUser.get());

        friend.setStatus("FRIEND");
        friendMirror.setStatus("FRIEND");

        friend.setDateAccepted(new Date());
        friendMirror.setDateAccepted(new Date());

        friendRepository.save(friend);
        friendRepository.save(friendMirror);

        FriendDto friendDto = new FriendDto();
        friendDto.setFriend(true);
        friendDto.setEmail(targetUser.get().getEmail());
        friendDto.setId(targetUser.get().getId());
        friendDto.setImageUrl(targetUser.get().getImageUrl());
        friendDto.setStatus("FRIEND");
        friendDto.setUsername(targetUser.get().getUsername());
        return friendDto;
    }

    public Object unFriendUser(Long userId) {
        Optional<Users> targetUser = authRepository.findById(userId);
        Users currentUser = this.getCurrentUser();

        Friends friend = friendRepository.findByUser1AndUser2(targetUser.get(), currentUser);
        Friends friendMirror = friendRepository.findByUser1AndUser2(currentUser, targetUser.get());

        if (friend == null) {
            return null;
        }

        friendRepository.delete(friend);
        friendRepository.delete(friendMirror);

        return null;
    }

    public Object cancelFriendRequest(Long userId) {
        Optional<Users> targetUser = authRepository.findById(userId);
        Users currentUser = this.getCurrentUser();

        Friends friend = friendRepository.findByUser1AndUser2(targetUser.get(), currentUser);
        Friends friendMirror = friendRepository.findByUser1AndUser2(currentUser, targetUser.get());
        if (friend == null) {
            return null;
        }
        friendRepository.delete(friend);
        friendRepository.delete(friendMirror);

        return null;
    }

    public Object declineFriendRequest(Long userId) {
        Optional<Users> targetUser = authRepository.findById(userId);
        Users currentUser = this.getCurrentUser();

        Friends friend = friendRepository.findByUser1AndUser2(targetUser.get(), currentUser);
        Friends friendMirror = friendRepository.findByUser1AndUser2(currentUser, targetUser.get());
        if (friend == null) {
            return null;
        }
        friendRepository.delete(friend);
        friendRepository.delete(friendMirror);

        return null;
    }
}
