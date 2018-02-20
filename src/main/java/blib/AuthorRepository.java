package blib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    List<Author> findAllByNameContaining(String sample);
    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM author WHERE author.id NOT IN (SELECT author_book.author_id FROM author_book)",
            nativeQuery = true)
    String clearThoseOrphans();
}
