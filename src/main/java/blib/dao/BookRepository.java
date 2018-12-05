package blib.dao;

import blib.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByOrderByIdAsc();

    List<Book> findAllByOrderByTitleAsc();

    List<Book> findAllByOrderByIsbnAsc();
}
