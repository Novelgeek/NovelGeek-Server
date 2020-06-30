package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.UserDetails;
import lk.ucsc.NovelGeek.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends CrudRepository<UserDetails,Long> {
UserDetails findByUser(Users user);
}
