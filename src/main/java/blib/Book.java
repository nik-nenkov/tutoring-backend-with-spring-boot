package blib;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class Book{
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String isbn;
    private String image;
    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name="book_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="author_id",referencedColumnName = "id")
            )
    private List<Author> authors;
    public Book(){}
    public Book(JSONObject book){
        this.setTitle(book.getString("title"));
        this.setIsbn(book.getString("isbn"));
        if(book.has("authors")){
            List<Author> authArr = new ArrayList<>();
            JSONArray authObjArr = book.getJSONArray("authors");
            for(int i=0;i<authObjArr.length();i++){
                Author a = new Author();
                JSONObject authObj = authObjArr.getJSONObject(i);
                if(authObj.has("name")){
                    a.setName(authObj.getString("name"));
                }
                if(authObj.has("id")){
                    a.setId(authObj.getLong("id"));
                }
                authArr.add(a);
            }
            this.setAuthors(authArr);
        }
    }
}