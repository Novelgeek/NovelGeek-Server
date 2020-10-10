package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.ConfirmationToken;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.service.recommendation.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("confirmationTokenRepository")
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    ConfirmationToken findByUser(Users user);

}
