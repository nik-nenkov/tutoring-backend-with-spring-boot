package blib.service;

import blib.dao.AuthorRepository;
import blib.dao.BookRepository;
import blib.model.Author;
import blib.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public boolean existsByTitleOrIsbn(String title, String isbn) {
        return bookRepository.existsByTitle(title) || bookRepository.existsByIsbn(isbn);
    }


    public Book persistBook(Book book) {
        List<Author> resolvedAuthors = book.getAuthors().stream()
                .map(this::resolveAuthor)
                .toList();

        book.setAuthors(resolvedAuthors);

        return bookRepository.save(book);
    }

    private Author resolveAuthor(Author inputAuthor) {
        return authorRepository.findByName(inputAuthor.getName())
                .orElseGet(() -> authorRepository.save(inputAuthor));
    }

    public List<Book> findAllBooksOrdered(String order) {
        return switch (order) {
            case "id" -> bookRepository.findAllByOrderByIdAsc();
            case "title" -> bookRepository.findAllByOrderByTitleAsc();
            case "isbn" -> bookRepository.findAllByOrderByIsbnAsc();
            default -> bookRepository.findAll();
        };
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookRepository.findOneById(id));
    }

    public boolean deleteById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteOneById(id);
            return true;
        }
        return false;
    }
}
