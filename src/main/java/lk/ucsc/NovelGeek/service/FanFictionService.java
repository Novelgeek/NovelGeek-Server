package lk.ucsc.NovelGeek.service;

import com.amazonaws.services.workdocs.model.User;
import lk.ucsc.NovelGeek.model.FanFiction;
import lk.ucsc.NovelGeek.model.FanFictionReview;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.repository.FanFictionRepository;
import lk.ucsc.NovelGeek.repository.FanFictionReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FanFictionService {

    @Autowired
    FanFictionRepository fanFictionRepository;

    @Autowired
    FanFictionReviewRepository fanFictionReviewRepository;

    @Autowired
    GroupService groupService;

    public List<FanFiction> getFanFictions() {
        return (List<FanFiction>) fanFictionRepository.findAll();
    }

    public FanFiction addFanFiction(FanFiction fanFiction) {

        return fanFictionRepository.save(fanFiction);
    }

    public void deleteFanFiction( long id) {
        List<FanFictionReview> reviews = fanFictionReviewRepository.findByFanFictionId(fanFictionRepository.findById(id).get());
        if(reviews.size() != 0){
            fanFictionReviewRepository.deleteAll(reviews);
        }
        fanFictionRepository.deleteById(id);
    }

    public List<FanFiction> getFanFictionsByuserID(long userId) {
        return fanFictionRepository.getFanFictionsByuserID(userId);
    }


    public FanFiction getSpecificFanFiction(long id) {
        return fanFictionRepository.findById(id).get();
    }

    public Object addReview(FanFictionReview fanFictionReview) {
        return fanFictionReviewRepository.save(fanFictionReview);
    }

    public Object getFanFictionReviews(Long bookId) {
        return fanFictionReviewRepository.findByFanFictionId(fanFictionRepository.findById(bookId).get());
    }

    public Object editFanFiction(FanFiction fanFiction) {
        FanFiction editFan = fanFictionRepository.findById(fanFiction.getId()).get();
        editFan.setBookName(fanFiction.getBookName());
        editFan.setDescription(fanFiction.getDescription());
        editFan.setTitle(fanFiction.getTitle());
        return fanFictionRepository.save(editFan);
    }
}
