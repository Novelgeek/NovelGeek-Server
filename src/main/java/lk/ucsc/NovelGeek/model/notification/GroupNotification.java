package lk.ucsc.NovelGeek.model.notification;

import lk.ucsc.NovelGeek.model.Group;
import lk.ucsc.NovelGeek.model.Notification;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "GroupNotification")
@PrimaryKeyJoinColumn(name="notificationId")
@DiscriminatorValue("Group")
public class GroupNotification extends Notification {
    @ManyToOne
    private Group group;

    private String notiType;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getNotiType() {
        return notiType;
    }

    public void setNotiType(String notiType) {
        this.notiType = notiType;
    }
}
