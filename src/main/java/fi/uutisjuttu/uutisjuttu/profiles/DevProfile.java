package fi.uutisjuttu.uutisjuttu.profiles;

import fi.uutisjuttu.uutisjuttu.domain.Uutinen;
import fi.uutisjuttu.uutisjuttu.repository.UutinenRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"dev", "default"})
public class DevProfile {
    
    @Autowired
    private UutinenRepository uutinenRepository;
    
    @PostConstruct
    public void init() {
        Uutinen u1 = new Uutinen();
        u1.setUrl("http://yle.fi/uutiset/new_yorkin_uusi_wtc-rakennus_avautui_liiketoiminnalle__ensimmaiset_tyontekijat_muuttivat/7597536");
        uutinenRepository.save(u1);
        
        Uutinen u2 = new Uutinen();
        u2.setUrl("http://www.hs.fi/kotimaa/a1414991824044");
        uutinenRepository.save(u2);
    }
    
}
