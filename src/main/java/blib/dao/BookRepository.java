package blib.dao;

import blib.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findAllByOrderByIdAsc();

  List<Book> findAllByOrderByTitleAsc();

  List<Book> findAllByOrderByIsbnAsc();

  Book findOneById(long id);

  void deleteOneById(long id);

  boolean existsByTitle(String title);
  boolean existsByIsbn(String isbn);
}
