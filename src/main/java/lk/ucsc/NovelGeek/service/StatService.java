package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Group;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.response.BasicStat;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatService {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    GroupRepository groupRepository;

    public Object getBasicStat() {
        BasicStat basicStat = new BasicStat();

        List<Users> users = authRepository.findByRole("USER");
        basicStat.setNoOfUsers(users.size());
        List<Group> groups = groupRepository.findAll();
        basicStat.setNoOfGroups(groups.size());
        basicStat.setNoOfPosts(5);
        return basicStat;
    }
}
