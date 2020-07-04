package lk.ucsc.NovelGeek.repository;

import lk.ucsc.NovelGeek.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Books, Long> {
    Books findByBookId(String bookId);
    Books findByTitle(String title);
}
