package fi.uutisjuttu.uutisjuttu.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Comment extends AbstractPersistable<Long>{
    @ManyToOne
    private User author;
    @ManyToOne
    private News news;
    private String content;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String sisalto) {
        this.content = sisalto;
    }
    
}
