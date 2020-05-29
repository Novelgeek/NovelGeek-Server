package lk.ucsc.NovelGeek.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "NotificationEvents")
public class NotificationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;

    private String type;

    private String description;

    private String entity;

    @OneToMany(targetEntity = Notification.class, mappedBy = "eventId")
    Set<Notification> notification;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Set<Notification> getNotification() {
        return notification;
    }

    public void setNotification(Set<Notification> notification) {
        this.notification = notification;
    }
}
