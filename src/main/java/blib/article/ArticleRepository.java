package blib.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    Article findFirstByAuthorContaining(String author);
    List<Article> findAllByDateGreaterThanOrderByDateDesc(Long earliestDate);
}
