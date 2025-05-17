package blib.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String title;

  @Column(unique = true)
  private String isbn;

  private String image;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "author_book",
      joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
  private List<Author> authors;
}
