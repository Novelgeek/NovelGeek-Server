package lk.ucsc.NovelGeek.repository.book;

import lk.ucsc.NovelGeek.model.book.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Books, Long> {
    Books findByBookId(String bookId);
    Books findByTitle(String title);
}
