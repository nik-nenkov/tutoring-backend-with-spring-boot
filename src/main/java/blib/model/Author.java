package blib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Author {
  @Id @GeneratedValue private long id;
  private String name;

  @JsonIgnore
  @ManyToMany(mappedBy = "authors")
  private List<Book> books;
}
