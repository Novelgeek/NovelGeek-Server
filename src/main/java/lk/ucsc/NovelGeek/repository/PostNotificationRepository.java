package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.PostNotification;
import lk.ucsc.NovelGeek.model.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostNotificationRepository extends CrudRepository<PostNotification, Long> {
    List<PostNotification> findByUserAndNotificationType(Users user, String type);
}
