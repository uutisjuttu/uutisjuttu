package fi.uutisjuttu.uutisjuttu.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Publisher extends AbstractPersistable<Long>{
    
    private String name;
    @OneToMany(fetch=FetchType.EAGER)
    private List<News> news;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
