package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Poll;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewPollRequest;
import lk.ucsc.NovelGeek.model.response.PollResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.OptionRepository;
import lk.ucsc.NovelGeek.repository.PollRepository;
import lk.ucsc.NovelGeek.repository.UserRepository;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    private OptionRepository optionRepository;

    public PollResponse savePoll(NewPollRequest newPollRequest){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();

        Optional<Users> user1 = authRepository.findById(user.getId());

        Poll newPoll = new Poll();
        newPoll.setUser(user1);
        newPoll.setTitle(newPollRequest.getTitle());
        newPoll.setEndDate(newPollRequest.getEndDate());
        newPoll.setVisible(newPollRequest.getVisible());
        newPoll.setOptions(newPollRequest.getOptions());

        Poll savedPoll = pollRepository.save(newPoll);

        newPollRequest.getOptions().stream().forEach(option -> {
        option.setPoll(savedPoll);
        optionRepository.save(option);
        });

        PollResponse pollResponse = new PollResponse();
        pollResponse.setTitle(savedPoll.getTitle());
        pollResponse.setOptions(savedPoll.getOptions());
        pollResponse.setEndDate(savedPoll.getEndDate());
        pollResponse.setVisible(savedPoll.getVisible());

        return pollResponse;
    }

}
