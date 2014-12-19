package fi.uutisjuttu.uutisjuttu.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.persistence.PostLoad;
//import javax.persistence.Transient;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class News extends AbstractPersistable<Long> {

//    @URL
//    @NotNull
    private String url;
//    @NotNull
    @Column(length=1000)
    private String description;
//    @NotNull
    private String title;
//    @URL
    private String imageUrl;
    @Temporal(TemporalType.TIMESTAMP)
    private Date submitted;
    @OneToMany
    private List<Comment> comments;
    @ManyToOne
    private Publisher publisher;

    public News() {
        this.submitted = new Date();
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        if (description.length()>1000) {
            description = description.substring(0, 1000);
        }
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
