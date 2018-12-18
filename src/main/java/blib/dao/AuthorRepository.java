package blib.dao;

import blib.model.Author;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
  List<Author> findAllByNameContaining(String sample);

  Author findOneById(long id);
}
