package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.PaymentData;
import lk.ucsc.NovelGeek.model.SellBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentDataRepository extends CrudRepository<PaymentData, Long> {
    List<PaymentData> findAll();
    PaymentData findById(long id);
}
