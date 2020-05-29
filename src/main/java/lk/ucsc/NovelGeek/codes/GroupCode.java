package lk.ucsc.NovelGeek.codes;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GroupCode {
    private int invited = 1;
    private int requested = 2;
    private int member = 3;
    private int admin = 4;
    private int creator = 5;

    public int getInvited() {
        return invited;
    }

    public void setInvited(int invited) {
        this.invited = invited;
    }

    public int getRequested() {
        return requested;
    }

    public void setRequested(int requested) {
        this.requested = requested;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }
}
