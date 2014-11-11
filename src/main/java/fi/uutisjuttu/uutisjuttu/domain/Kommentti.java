package fi.uutisjuttu.uutisjuttu.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Kommentti extends AbstractPersistable<Long>{
    @ManyToOne
    private Kayttaja lahettaja;
    @ManyToOne
    private Uutinen uutinen;
    private String sisalto;

    public Kayttaja getLahettaja() {
        return lahettaja;
    }

    public void setLahettaja(Kayttaja lahettaja) {
        this.lahettaja = lahettaja;
    }

    public Uutinen getUutinen() {
        return uutinen;
    }

    public void setUutinen(Uutinen uutinen) {
        this.uutinen = uutinen;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }
    
}
