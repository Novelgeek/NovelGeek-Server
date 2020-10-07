package lk.ucsc.NovelGeek.service;

import com.amazonaws.services.workdocs.model.User;
import lk.ucsc.NovelGeek.model.FanFiction;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.repository.FanFictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FanFictionService {

    @Autowired
    FanFictionRepository fanFictionRepository;

    @Autowired
    GroupService groupService;

    public List<FanFiction> getFanFictions() {
        return (List<FanFiction>) fanFictionRepository.findAll();
    }

    public FanFiction addFanFiction(FanFiction fanFiction) {

        return fanFictionRepository.save(fanFiction);
    }

    public void deleteFanFiction( long id) {
        fanFictionRepository.deleteById(id);
    }

    public List<FanFiction> getFanFictionsByuserID(long userId) {
        return fanFictionRepository.getFanFictionsByuserID(userId);
    }


    public FanFiction getSpecificFanFiction(long id) {
        return fanFictionRepository.findById(id).get();
    }
}
