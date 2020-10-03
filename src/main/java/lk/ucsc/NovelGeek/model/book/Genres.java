package lk.ucsc.NovelGeek.model.book;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long genreId;

    private String genreName;

    @OneToMany(targetEntity = LocalBook.class, mappedBy = "genres")
    Set<LocalBook> localBooks;

    public Genres(String genreName) {
        this.genreName = genreName;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<LocalBook> getLocalBooks() {
        return localBooks;
    }

    public void setLocalBooks(Set<LocalBook> localBooks) {
        this.localBooks = localBooks;
    }
}
