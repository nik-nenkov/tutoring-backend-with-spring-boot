package blib;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByOrderByIdAsc();
    List<Book> findAllByOrderByTitleAsc();
    List<Book> findAllByOrderByIsbnAsc();
}
