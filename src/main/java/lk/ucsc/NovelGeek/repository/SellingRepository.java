package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.SellBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SellingRepository extends CrudRepository<SellBook,Long> {

    List<SellBook> findAll();
    SellBook findById(long id);


}
