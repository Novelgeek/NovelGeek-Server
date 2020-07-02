package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Group;
import lk.ucsc.NovelGeek.model.Notification;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.notification.GroupNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupNotificatioRepository extends JpaRepository<GroupNotification, Long> {
    List<GroupNotification> findByNotiType(String invited);
    List<GroupNotification> findByNotiTypeAndTargetUser(String invited, Users user);
    List<GroupNotification> findByTargetUserAndGroup(Users user, Group group);
    GroupNotification findByFiredUserAndTargetUserAndNotiType(Users firedUser, Users targetUser, String notiType);
}