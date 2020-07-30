package lk.ucsc.NovelGeek.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionid;

    private String option;

    private Long score = 0L;

    @ManyToOne
    private Poll poll;

    @OneToMany(targetEntity = PollVotes.class, mappedBy = "options", cascade = CascadeType.ALL)
    Set<PollVotes> pollVotes;

    public Option(){

    }

    public Long getOptionid() {
        return optionid;
    }

    public void setOptionid(Long optionid) {
        this.optionid = optionid;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Set<PollVotes> getPollVotes() {
        return pollVotes;
    }

    public void setPollVotes(Set<PollVotes> pollVotes) {
        this.pollVotes = pollVotes;
    }
}
