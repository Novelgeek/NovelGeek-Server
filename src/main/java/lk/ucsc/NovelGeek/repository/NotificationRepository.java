package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
//public interface NotificationRepository<T extends Notification> extends JpaRepository<T, Long> {
//
//}
//
//@Transactional
//public interface GroupNotificatioRepository extends NotificationRepository<GroupNotification>{
//
//}

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    //List<Notification> findByNotiType(String invited);
}

