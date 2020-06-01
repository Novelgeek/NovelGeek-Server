package lk.ucsc.NovelGeek.model;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity(name = "Notifications")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Notification_Type")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    @ManyToOne
    @JoinColumn(name = "targetUserId")
    Users targetUser;

    @ManyToOne
    @JoinColumn(name = "firedUserId")
    Users firedUser;

    private boolean seen;

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public Users getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(Users targetUser) {
        this.targetUser = targetUser;
    }

    public Users getFiredUser() {
        return firedUser;
    }

    public void setFiredUser(Users firedUser) {
        this.firedUser = firedUser;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

}
