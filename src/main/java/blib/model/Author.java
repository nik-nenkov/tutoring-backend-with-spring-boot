package blib;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
class Author {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
