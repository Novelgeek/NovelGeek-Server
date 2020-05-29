package lk.ucsc.NovelGeek.model;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity(name = "Notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    private long targetUser;

    private long firedUser;

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

    public long getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(long targetUser) {
        this.targetUser = targetUser;
    }

    public long getFiredUser() {
        return firedUser;
    }

    public void setFiredUser(long firedUser) {
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
