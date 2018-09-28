package blib.article;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/articles")
public class ArticleController {
    static int i =1;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/v")
    public String findVenkat(){
        StringBuilder sb = new StringBuilder("\n\n");
        Article a = articleRepository.findFirstByAuthorContaining("Venkat");
        sb.append("</br>Title: ").append(a.getTitle());
        sb.append("</br>Author: ").append(a.getAuthor());
        Date date = Date.from( Instant.ofEpochSecond( a.getDate() ) );
        sb.append("</br>Date: ").append(date);
        return sb.toString();
    }

    @GetMapping("/all")
    public String allFromTheLastEightDays(){
        StringBuilder sb = new StringBuilder("</br>");
        List<Article> articles = articleRepository.findAllByDateGreaterThanOrderByDateDesc(1537477200L);
//        List<Article> articles = articleRepository.findAll();
        articles.forEach(a->{
            sb.append("</br>").append("<div class=article>").append(i).append(". <a href=\"").append(a.getLink()).append(">");
            sb.append(a.getTitle()).append("</a>");
            Date date = Date.from( Instant.ofEpochSecond( a.getDate() ) );
            sb.append(", ").append(date).append("</div>");
            i++;
        });
        sb.append("</br>");
        sb.append("</br>");
        i=0;
        return sb.toString();
    }

    @GetMapping("/all_with_source")
    public String allFromTheLastEightDaysWithSources(){
        StringBuilder sb = new StringBuilder("</br>");


        return sb.toString();
    }
}
