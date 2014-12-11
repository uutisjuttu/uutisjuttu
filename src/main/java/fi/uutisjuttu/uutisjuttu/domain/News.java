package fi.uutisjuttu.uutisjuttu.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.persistence.PostLoad;
//import javax.persistence.Transient;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class News extends AbstractPersistable<Long> {

    private String url;
    private String description;
    private String title;
    @Temporal(TemporalType.DATE)
    private Date submitted;
    @OneToMany(fetch=FetchType.EAGER)
    private List<Comment> comments;
    @ManyToOne
    private Publisher publisher;
    
    public News() {
        this.submitted = new Date();
    }

//    @Transient
//    private int numberOfComments;
//    
//    public News() {
//        this.numberOfComments = 0;
//    }
//
//    @PostLoad
//    public void countComments() {
//        this.numberOfComments = this.comments.size();
//    }
//
//    public int getNumberOfComments() {
//        return this.numberOfComments;
//    }
//
//    public void setNumberOfComments(int numberOfComments) {
//        this.numberOfComments = numberOfComments;
//    }
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
    
    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date submitted) {
        this.submitted = submitted;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> kommentit) {
        this.comments = kommentit;
    }

}
