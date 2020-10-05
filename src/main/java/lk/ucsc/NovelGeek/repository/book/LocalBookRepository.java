package lk.ucsc.NovelGeek.repository.book;

import lk.ucsc.NovelGeek.model.book.LocalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalBookRepository extends JpaRepository<LocalBook, Long> {

}
