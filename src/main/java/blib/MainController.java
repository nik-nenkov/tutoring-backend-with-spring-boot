package blib;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/")
public class MainController {
    //                                                                  CREATE point
    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public Book createBook(@RequestBody String receivedBook)
    {
        JSONObject bookObj = new JSONObject(receivedBook);
        Book b = new Book(bookObj);
        return persistBook(b);
    }
    //                                                                  READ point
    @RequestMapping("/books")
    public List<Book> listAllBooks(@RequestParam(value = "o", defaultValue = "") String order)
    {
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
    @RequestMapping(value="/book", produces = "application/json")
    public String showOneBook(@RequestParam("i") String id)
    {
        ObjectMapper mapper = new ObjectMapper();
        Book b = bookRepository.findOne(Long.parseLong(id));
        try {
            return mapper.writeValueAsString(b);
        }catch (Exception e){
            return "something went wrong";
        }

    }
    //                                                                  UPDATE point
    @RequestMapping("/update")
    public String updateBook(
            @RequestParam("i") String id,
            @RequestParam("t") String title,
            @RequestParam("n") String isbn
    ){
        try {
            Book b = bookRepository.findOne(Long.parseLong(id));
            b.setTitle(title);
            b.setIsbn(isbn);
            bookRepository.save(b);
            return "Book with id="+id+" was edited!";
        }catch(Exception e){
            return "No books were edited!";
        }
    }
    //                                                                  DELETE point
    @RequestMapping("/delete")
    public String deleteBook(
            @RequestParam("i") String id
    ){
        try {
            bookRepository.delete(Long.parseLong(id));
            return "Book with id="+id+" was removed from DB";
        }catch(Exception e){
            return "No books were deleted";
        }
    }
    //                                                                  JpaRepository<Book,Long>
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authRepository;
    @Transactional
    public Book persistBook(Book b){
        List<Author> mutatedList = b.getAuthors();
        try {
            for (int i = 0; i < mutatedList.size(); i++) {
                if (authRepository.exists(mutatedList.get(i).getId())) {
                    Author a = authRepository.findOne(mutatedList.get(i).getId());
                    mutatedList.set(i, a);
                }
            }
        }finally{
            b.setAuthors(mutatedList);
            authRepository.save(mutatedList);
        }
        return bookRepository.save(b);
    }
}
