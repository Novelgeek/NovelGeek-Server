package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.FanFiction;
import lk.ucsc.NovelGeek.repository.FanFictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FanFictionService {

    @Autowired
    FanFictionRepository fanFictionRepository;

    public List<FanFiction> getFanFictions() {
        return (List<FanFiction>) fanFictionRepository.findAll();
    }

    public FanFiction addFanFiction(FanFiction fanFiction) {
        return fanFictionRepository.save(fanFiction);
    }

    public void deleteFanFiction(long id) {
         fanFictionRepository.deleteById(id);
    }


}
