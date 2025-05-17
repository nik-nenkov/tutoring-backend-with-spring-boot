package blib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String name;

  @JsonIgnore
  @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
  private List<Book> books;
}
