package lk.ucsc.NovelGeek.service;

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

    public Object getAllUsers() {
//        UserResponse userResponse = new UserResponse();
//        authRepository.findAll().forEach((user) -> {
//            BeanUtils.copyProperties(userResponse, user);
//        });

        return authRepository.findAll();
    }
}
