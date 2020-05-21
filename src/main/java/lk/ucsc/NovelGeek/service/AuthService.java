package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.dto.UserDto;
import lk.ucsc.NovelGeek.model.Auth;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthRepository authRepository;

    public UserDto createUser(UserDto userDto) {
        Auth auth = new Auth();
        BeanUtils.copyProperties(userDto, auth);

        auth.setEncryptedPassword("test");
        auth.setUserId("testuserID");

        Auth storedAuth = authRepository.save(auth);
        UserDto returnUser = new UserDto();
        BeanUtils.copyProperties(storedAuth, returnUser);

        return returnUser;
    }
}
