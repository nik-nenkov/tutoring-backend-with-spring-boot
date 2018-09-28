package blib.article;


import blib.feed.Feed;
import blib.feed.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/articles")
public class ArticleController {
    private static int i =1;
    private final ArticleRepository articleRepository;
    private final FeedRepository feedRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, FeedRepository feedRepository) {
        this.articleRepository = articleRepository;
        this.feedRepository = feedRepository;
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
        articlesToHtml(sb, articles);
        sb.append("</br>");
        sb.append("</br>");
        i=1;
        return sb.toString();
    }

    @GetMapping("/all_with_source")
    public String allFromTheLastEightDaysWithSources(){
        StringBuilder sb = new StringBuilder("</br>");
        List<Feed> feeds = feedRepository.findAll();
        feeds.forEach(f->{
            sb.append("</br><h2>").append(f.getName()).append("</h2>");
            articlesToHtml(sb,articleRepository.findAllByIdFeedAndDateGreaterThanOrderByDateDesc(f.getId(),1537477200L));
        });
        return sb.toString();
    }

    private void articlesToHtml(StringBuilder sb, List<Article> articles) {
        articles.forEach(a->{
            sb.append("</br>").append("<div class=article>").append(i).append(". <a href=\"").append(a.getLink()).append(">");
            sb.append(a.getTitle()).append("</a>");
            Date date = Date.from( Instant.ofEpochSecond( a.getDate() ) );
            sb.append(", ").append(date).append("</div>");
            i++;
        });
        i=1;
    }
}
