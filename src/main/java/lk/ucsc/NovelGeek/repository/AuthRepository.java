package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Auth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends CrudRepository<Auth, Long> {
    Auth findByEmail(String email);
}
