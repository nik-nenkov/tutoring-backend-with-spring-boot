package blib.dao;

import blib.model.Author;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
  List<Author> findAllByNameContaining(String sample);

  Optional<Author> findByName(String name);
}
