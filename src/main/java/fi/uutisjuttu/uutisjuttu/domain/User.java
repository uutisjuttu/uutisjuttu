package fi.uutisjuttu.uutisjuttu.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Table(name = "useraccount")
public class User extends AbstractPersistable<Long> {

    private String username;
    private String password;
    private String salt;
    private boolean superuser;
    @Temporal(TemporalType.DATE)
    private Date registerDate;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;

    public User() {
        this.registerDate = new Date();
    }
    
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setKommentit(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isSuperuser() {
        return superuser;
    }

    public void setSuperuser(boolean superuser) {
        this.superuser = superuser;
    }

}
