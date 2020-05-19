package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User,Long> {
}
