package lk.ucsc.NovelGeek.model;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity(name = "Notifications")
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

    @ManyToOne
    @JoinColumn(name = "eventId")
    NotificationEvent eventId;

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

    public NotificationEvent getEventId() {
        return eventId;
    }

    public void setEventId(NotificationEvent eventId) {
        this.eventId = eventId;
    }
}
