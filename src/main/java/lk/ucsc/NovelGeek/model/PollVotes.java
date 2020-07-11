package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"users","poll","options"})
public class PollVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteid;

    @ManyToOne
    Poll poll;

    @ManyToOne
    Users users;

    @ManyToOne
    Option options;

    public PollVotes(){

    }

    public Long getVoteid() {
        return voteid;
    }

    public void setVoteid(Long voteid) {
        this.voteid = voteid;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Option getOptions() {
        return options;
    }

    public void setOptions(Option options) {
        this.options = options;
    }
}
