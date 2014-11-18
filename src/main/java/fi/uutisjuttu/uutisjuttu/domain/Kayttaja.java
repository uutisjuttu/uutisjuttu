package fi.uutisjuttu.uutisjuttu.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
public class Kayttaja extends AbstractPersistable<Long>{
    private String tunnus;
    private String salasana;
    private String salt;
    
    @OneToMany
    private List<Kommentti> kommentit;

    public String getTunnus() {
        return tunnus;
    }

    public void setTunnus(String tunnus) {
        this.tunnus = tunnus;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salt = BCrypt.gensalt();
        this.salasana = BCrypt.hashpw(salasana, this.salt);
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Kommentti> getKommentit() {
        return kommentit;
    }

    public void setKommentit(List<Kommentti> kommentit) {
        this.kommentit = kommentit;
    }
    
    
    
}
