package lk.ucsc.NovelGeek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.ucsc.NovelGeek.enums.MemberStatus;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "GroupMembers")
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @ManyToOne
    @JoinColumn(name = "id")
    Users users;

    @ManyToOne
    @JoinColumn(name = "groupId")
    Group group;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;


    private Date joinedDate;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }
}
