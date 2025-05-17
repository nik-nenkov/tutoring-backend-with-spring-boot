package blib.controller;

import blib.model.Book;
import blib.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveBook(@RequestBody Book book) {
        boolean exists = bookService.existsByTitleOrIsbn(book.getTitle(), book.getIsbn());

        if (exists) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("A book with the same title or ISBN already exists.");
        }

        Book savedBook = bookService.persistBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Book>> listAllBooks(@RequestParam(value = "o", defaultValue = "") String order) {
        List<Book> books = bookService.findAllBooksOrdered(order);
        return ResponseEntity.ok(books);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Book> showOneBook(@PathVariable("id") Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
        boolean deleted = bookService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Book with id=" + id + " was removed from DB");
        } else {
            return ResponseEntity.status(404).body("Book with id=" + id + " not found");
        }
    }
}
