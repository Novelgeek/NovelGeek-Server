package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.model.Option;
import lk.ucsc.NovelGeek.model.Poll;
import lk.ucsc.NovelGeek.model.PollVotes;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.request.NewPollRequest;
import lk.ucsc.NovelGeek.model.response.PollResponse;
import lk.ucsc.NovelGeek.repository.*;
import lk.ucsc.NovelGeek.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    PollVotesRepository pollVotesRepository;

    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        return currentUser;
    }

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
        Optional<Poll> returnPoll = pollRepository.findById(savedPoll.getPollid());
        //PollResponse pollResponse = new PollResponse(returnPoll.get(), this.getCurrentUser());
      return savedPoll;
    }

    public List<PollResponse> getAllPolls() {
        List<PollResponse> pollResponses= pollRepository.findAll().stream().map( poll -> new PollResponse(poll, this.getCurrentUser())).collect(Collectors.toList());
        return pollResponses;
    }

    public void vote(Long pollid, Long optionid)  {


        Poll poll = pollRepository.findById(pollid).get();

        //check whether poll has ended
        if (poll.getEndDate().before(new Date())) {
            throw new RuntimeException("Voting has already ended!");
        }

        //check whether already voted
        PollVotes pollVotes = pollVotesRepository.findByPollAndUsers(poll, this.getCurrentUser());

        if(pollVotes !=null){
            throw new RuntimeException("You can only vote once!");
        }

        Option option = optionRepository.findById(optionid).get();
        PollVotes newPollVotes = new PollVotes();
        newPollVotes.setPoll(poll);
        newPollVotes.setOptions(option);
        newPollVotes.setUsers(this.getCurrentUser());
        pollVotesRepository.save(newPollVotes);
        option.setScore(option.getScore()+1);
        optionRepository.save(option);
    }

    public List<?> getUserPolls() {
        List<PollResponse> pollResponses= pollRepository.findByUsers(this.getCurrentUser()).stream().map( poll -> new PollResponse(poll, this.getCurrentUser())).collect(Collectors.toList());
//        return  pollRepository.findByUsers(this.getCurrentUser());
        return pollResponses;
    }

    public void deletePollById(Long pollid){
        pollRepository.deleteById(pollid);
    }
}
