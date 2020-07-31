package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.PaymentCustomer;
import lk.ucsc.NovelGeek.model.PaymentData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentCustomerRepository extends CrudRepository <PaymentCustomer, Long>{
    List<PaymentCustomer> findAll();
    PaymentCustomer findById(long id);
}
