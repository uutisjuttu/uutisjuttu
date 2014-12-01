package fi.uutisjuttu.uutisjuttu.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
public class User extends AbstractPersistable<Long>{
    private String username;
    private String password;
    private String salt;
    
    @OneToMany
    private List<Comment> comments;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, this.salt);
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setKommentit(List<Comment> comments) {
        this.comments = comments;
    }
    
    
    
}
