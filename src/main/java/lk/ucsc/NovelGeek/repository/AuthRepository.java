package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends CrudRepository<Users, Long> {
    Users findByEmail(String email);
    List<Users> findByRole(String role);
    List<Users> findByEmailNotAndRole(String email, String role);

//    void delete(Optional<Users> currentuser);
}
