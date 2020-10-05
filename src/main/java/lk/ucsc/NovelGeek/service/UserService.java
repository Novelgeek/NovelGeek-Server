package lk.ucsc.NovelGeek.service;


import lk.ucsc.NovelGeek.model.UserDetails;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.response.UserDetailsResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.UserRepository;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.fasterxml.jackson.databind.util.BeanUtil;
import lk.ucsc.NovelGeek.model.response.UserResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//for getting details of all users, updating users and stuff
@Service
public class UserService {
    @Autowired
    AuthRepository authRepository;

    @Autowired
    UserRepository userRepository;
    public Object getUserDetails(Long id) {

        Optional<Users> users = authRepository.findById(id);
        UserDetails userDetail = userRepository.findByUser(users.get());

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsername(users.get().getUsername());
        userDetailsResponse.setCity(userDetail.getCity());
        userDetailsResponse.setContact(userDetail.getContact());
        userDetailsResponse.setCity(userDetail.getCity());
        userDetailsResponse.setCountry(userDetail.getCountry());
        userDetailsResponse.setDescription(userDetail.getDescription());

        return userDetailsResponse;

    }

    public Object saveUserDetails(UserDetailsResponse userDetailsResponse) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();

        Optional<Users> users = authRepository.findById(user.getId());
        UserDetails userDetails = userRepository.findByUser(users.get());

        users.get().setUsername(userDetailsResponse.getUsername());
        userDetails.setName(userDetailsResponse.getUsername());
        userDetails.setContact(userDetailsResponse.getContact());
        userDetails.setCountry(userDetailsResponse.getCountry());
        userDetails.setCity(userDetailsResponse.getCity());
        userDetails.setDescription(userDetailsResponse.getDescription());

        authRepository.save(users.get());
        userRepository.save(userDetails);

        return null;
    }

        public Object getAllUsers () {

//        UserResponse userResponse = new UserResponse();
//        authRepository.findAll().forEach((user) -> {
//            BeanUtils.copyProperties(userResponse, user);
//        });

            return authRepository.findAll();
        }

    public Object getAllUsersExceptMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Users> usersList = authRepository.findByEmailNotAndRole(auth.getName(), "USER");
        List<UserResponse> users = usersList.stream().map(users1 -> new UserResponse(users1)).collect(Collectors.toList());
        return users;
    }
}
