package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Payments;
import lk.ucsc.NovelGeek.model.SellBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentsRepository extends CrudRepository<Payments, Long> {
    List<Payments> findAll();
    @Query(value="SELECT COUNT(p.payid) FROM payment_selling_books p WHERE p.order_id =?1",nativeQuery = true)
    public long checkIsSold(long sellingid);

    Payments findBySellbook(SellBook book);
}
