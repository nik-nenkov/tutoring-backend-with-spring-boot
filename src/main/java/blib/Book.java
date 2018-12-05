package blib;


import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    private String title;
    private String isbn;
    private String image;
    //Проблема с премахването на ненужни автори които вече не са свързани с никоя книга
    //е решен чрез създаване на trigger в базата данни който се изпълнява след delete на книга
    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private List<Author> authors;

    public Book() {
    }

    public Book(JSONObject book) {
        if (book.has("id")) {
            this.setId(book.getLong("id"));
        }
        this.setTitle(book.getString("title"));
        this.setIsbn(book.getString("isbn"));
        if (book.has("authors")) {
            List<Author> authArr = new ArrayList<>();
            JSONArray authObjArr = book.getJSONArray("authors");
            for (int i = 0; i < authObjArr.length(); i++) {
                Author a = new Author();
                JSONObject authObj = authObjArr.getJSONObject(i);
                if (authObj.has("name")) {
                    a.setName(authObj.getString("name"));
                }
                if (authObj.has("id")) {
                    a.setId(authObj.getLong("id"));
                }
                authArr.add(a);
            }
            this.setAuthors(authArr);
        }
    }
}