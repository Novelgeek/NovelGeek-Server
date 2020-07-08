package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Group;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.response.BasicStat;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.GroupRepository;
import lk.ucsc.NovelGeek.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatService {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PostRepository postRepository;

    public Object getBasicStat() {
        BasicStat basicStat = new BasicStat();

        List<Users> users = authRepository.findByRole("USER");
        List<Users> admins = authRepository.findByRole("ADMIN");
        basicStat.setNoOfUsers(users.size());
        List<Group> groups = groupRepository.findAll();
        basicStat.setNoOfGroups(groups.size());
        basicStat.setNoOfPosts(postRepository.findAll().size());
        basicStat.setNoOfAdmins(admins.size());
        return basicStat;
    }

    public Object deleteAdmin(Long adminId) {
        List<Users> users = authRepository.findByRole("ADMIN");
        if (users.size() == 1){
            throw new RuntimeException("Cant delete only admin");
        }
        authRepository.deleteById(adminId);
        return null;
    }
}
