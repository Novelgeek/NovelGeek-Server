package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Admin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByEmail(String email);
}