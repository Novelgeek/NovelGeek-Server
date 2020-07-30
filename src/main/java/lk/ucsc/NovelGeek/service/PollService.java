package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Option;
import lk.ucsc.NovelGeek.model.Poll;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewPollRequest;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.OptionRepository;
import lk.ucsc.NovelGeek.repository.PollRepository;
import lk.ucsc.NovelGeek.repository.UserRepository;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionRepository optionRepository;



    public Poll savePoll(NewPollRequest newPollRequest){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();

        Optional<Users> user1 = authRepository.findById(user.getId());

        Poll newPoll = new Poll();
        newPoll.setUsers(user1.get());
        newPoll.setTitle(newPollRequest.getTitle());
        newPoll.setEndDate(new Date(newPollRequest.getEndDate()));


        Poll savedPoll = pollRepository.save(newPoll);

        newPollRequest.getOptions().stream().forEach(option -> {
            Option option1 = new Option();
            option1.setOption(option);
            option1.setPoll(savedPoll);
            option1.setScore((long) 0);
            optionRepository.save(option1);
        });



      return savedPoll;
    }

    public List<Poll> getAll() {
        return null;
    }



}
