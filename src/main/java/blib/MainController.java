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
    @RequestMapping(
            value = "/save_book",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")                                                                      //CREATE/UPDATE
    public Book         saveBook(@RequestBody String receivedBook) {
        JSONObject bookObj = new JSONObject(receivedBook);
        Book b = new Book(bookObj);
        return persistBook(b);
    }        //save by received json [id is optional]
    @RequestMapping(
            value = "/authors",
            produces = "application/json")                                                                      //READ points:
    public List<Author> suggestAuth(@RequestParam(value = "s", defaultValue = "") String sampleName){
        List<Author> sample = null;
        if(sampleName.length()>=1){
            sample = authRepository.findAllByNameContaining(sampleName);
        }
        return sample;
    }
    @RequestMapping(
            value = "/books",
            produces = "application/json")
    public List<Book>   listAllBooks(@RequestParam(value = "o", defaultValue = "") String order) {
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
    @RequestMapping(
            value="/book",
            produces = "application/json")
    public String       showOneBook(@RequestParam("i") String id) {
        ObjectMapper mapper = new ObjectMapper();
        Book b = bookRepository.findOne(Long.parseLong(id));
        try {
            return mapper.writeValueAsString(b);
        }catch (Exception e){
            return "something went wrong";
        }

    }
    @RequestMapping(
            value="/delete"
    )                                                                       //DELETE point
    public String       deleteBook(@RequestParam("i") String id) {
        try {
            bookRepository.delete(Long.parseLong(id));
            return "Book with id="+id+" was removed from DB";
        }catch(Exception e){
            return "No books were deleted";
        }
    }
    @Autowired BookRepository bookRepository;                                    //JpaRepositories and a "Transactional"
    @Autowired AuthorRepository authRepository;
    @Transactional public Book persistBook(Book b){
        try {
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
        }catch (Exception e){
            e.getMessage();
        }
        return bookRepository.save(b);
    }
}
