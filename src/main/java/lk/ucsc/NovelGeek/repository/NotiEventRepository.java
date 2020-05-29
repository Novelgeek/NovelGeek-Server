package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.NotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiEventRepository extends JpaRepository<NotificationEvent, Long> {
}
