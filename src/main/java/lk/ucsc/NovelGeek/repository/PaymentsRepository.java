package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Payments;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentsRepository extends CrudRepository<Payments, Long> {
    List<Payments> findAll();
}
