package lk.ucsc.NovelGeek.model.response;

import lk.ucsc.NovelGeek.model.Option;
import lk.ucsc.NovelGeek.model.Poll;
import lk.ucsc.NovelGeek.model.Users;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class PollResponse {

    private Long pollid;
    private String title;
    private Date endDate;
    private Boolean voted;
    private Set<Option> options;

    public PollResponse(Poll poll){
        this.pollid= poll.getPollid();
        this.title=poll.getTitle();
        this.endDate=poll.getEndDate();
        this.options=poll.getOptions();
    }

    public PollResponse(Poll poll, Users user){
        this.pollid= poll.getPollid();
        this.title=poll.getTitle();
        this.endDate=poll.getEndDate();
        this.options=poll.getOptions();
        this.voted=false;

        poll.getPollVotes().forEach(votedusers ->{
            if(votedusers.getUsers()==user){
                this.voted=true;
            }
        });
    }
    public Long getPollid() {
        return pollid;
    }

    public void setPollid(Long pollid) {
        this.pollid = pollid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getVoted() {
        return voted;
    }

    public void setVoted(Boolean voted) {
        this.voted = voted;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }
}
