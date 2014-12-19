package fi.uutisjuttu.uutisjuttu.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Comment extends AbstractPersistable<Long> {

    @ManyToOne
    private User author;
    @ManyToOne
    private News news;
    @Column(length = 1000)
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;

    public Comment() {
        this.postDate = new Date();
    }

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
        if (sisalto.length() > 1000) {
            sisalto = sisalto.substring(0, 1000);
        }
        this.content = sisalto;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

}
