package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"users"})
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollid;

    private String title;

    private Date endDate;

    @OneToMany(targetEntity = Option.class, mappedBy = "poll", cascade = CascadeType.ALL)
    private Set<Option> options;

    @ManyToOne
    private Users users;

    @OneToMany(targetEntity = PollVotes.class, mappedBy = "users", cascade = CascadeType.ALL)
    Set<PollVotes> pollVotes;

    public Poll(){
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

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Set<PollVotes> getPollVotes() {
        return pollVotes;
    }

    public void setPollVotes(Set<PollVotes> pollVotes) {
        this.pollVotes = pollVotes;
    }
}
