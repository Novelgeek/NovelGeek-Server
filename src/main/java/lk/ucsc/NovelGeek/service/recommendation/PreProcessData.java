package lk.ucsc.NovelGeek.service.recommendation;

import lk.ucsc.NovelGeek.model.book.Books;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PreProcessData {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthRepository authRepository;

    //protected static List<Item> items = Arrays.asList(new Item("Candy"), new Item("Drink"), new Item("Soda"), new Item("Popcorn"), new Item("Snacks"));
    public static List<Book> books = new ArrayList<Book>();
    public Map<User, HashMap<Book, Double>> initializeData() {
        books.removeAll(books);
        Map<User, HashMap<Book, Double>> data = new HashMap<>();

        List<Books> books= bookRepository.findAll();
        List<Users> users= authRepository.findByRole("USER");


        books.forEach(book -> {
            if (book.getBookRatings().size() > 0){
                if(!PreProcessData.books.contains(new Book(book.getTitle()))){
                    PreProcessData.books.add(new Book(book.getTitle()));
                }
            }
        });

        users.forEach(user -> {
            if(user.getBookRatings().size() > 0) {
                HashMap<Book, Double> newUser = new HashMap<Book, Double>();
                user.getBookRatings().forEach(rating -> {
                    PreProcessData.books.forEach(book -> {
                        if (book.getItemName().equals(rating.getBook().getTitle())){
                            newUser.put(book, (double) rating.getRating());
                        }
                    });
                });

                data.put(new User(user.getEmail()), newUser);
            }


        });

        return data;
    }

}