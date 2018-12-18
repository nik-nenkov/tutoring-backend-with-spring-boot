package blib.controller;

import blib.dao.AuthorRepository;
import blib.dao.BookRepository;
import blib.model.Author;
import blib.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/")
public class MainController {

  private final BookRepository bookRepository; // JpaRepositories and a "Transactional"
  private final AuthorRepository authRepository;

  @Autowired
  public MainController(BookRepository bookRepository, AuthorRepository authRepository) {
    this.bookRepository = bookRepository;
    this.authRepository = authRepository;
  }

  @PostMapping(value = "/save_book", consumes = "application/json", produces = "application/json")
  // CREATE/UPDATE
  public Book saveBook(@RequestBody String receivedBook) {
    JSONObject bookObj = new JSONObject(receivedBook);
    Book b = new Book(bookObj);
    return persistBook(b);
  } // save by received json [id is optional]

  @RequestMapping(value = "/books", produces = "application/json")
  public List<Book> listAllBooks(@RequestParam(value = "o", defaultValue = "") String order) {
    switch (order) {
      case "id":
        return bookRepository.findAllByOrderByIdAsc();
      case "title":
        return bookRepository.findAllByOrderByTitleAsc();
      case "isbn":
        return bookRepository.findAllByOrderByIsbnAsc();
      default:
        return bookRepository.findAll();
    }
  }

  @RequestMapping(value = "/book", produces = "application/json")
  public String showOneBook(@RequestParam("i") String id) {
    ObjectMapper mapper = new ObjectMapper();
    Book b = bookRepository.findOneById(Long.parseLong(id));
    try {
      return mapper.writeValueAsString(b);
    } catch (Exception e) {
      return "something went wrong";
    }
  }

  @DeleteMapping(value = "/delete")
  public String deleteBook(@RequestParam("i") String id) {
    try {
      bookRepository.deleteOneById(Long.parseLong(id));
      return "Book with id=" + id + " was removed from DB";
    } catch (Exception e) {
      return "No books were deleted";
    }
  }

  @Transactional
  public Book persistBook(Book b) {
    List<Author> mutatedList = b.getAuthors();
    try {
      for (int i = 0; i < mutatedList.size(); i++) {
        if (authRepository.existsById(mutatedList.get(i).getId())) {
          Author a = authRepository.findOneById(mutatedList.get(i).getId());
          mutatedList.set(i, a);
        }
      }
    } finally {
      b.setAuthors(mutatedList);
      authRepository.saveAll(mutatedList);
    }
    return bookRepository.save(b);
  }
}
