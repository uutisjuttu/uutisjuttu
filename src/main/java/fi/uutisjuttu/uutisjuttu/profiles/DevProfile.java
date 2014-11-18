package fi.uutisjuttu.uutisjuttu.profiles;

import fi.uutisjuttu.uutisjuttu.domain.Kayttaja;
import fi.uutisjuttu.uutisjuttu.domain.Uutinen;
import fi.uutisjuttu.uutisjuttu.repository.KayttajaRepository;
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
    
    @Autowired
    private KayttajaRepository kayttajaRepository;
    
    @PostConstruct
    public void init() {
        Uutinen u1 = new Uutinen();
        u1.setUrl("http://yle.fi/uutiset/new_yorkin_uusi_wtc-rakennus_avautui_liiketoiminnalle__ensimmaiset_tyontekijat_muuttivat/7597536");
        u1.setTitle("New Yorkin uusi WTC-rakennus avautui liiketoiminnalle – ensimmäiset työntekijät muuttivat");
        u1.setDescription("One World Trade Centerin ensimmäinen vuokralainen on kustannusyhtiö Conde Nast.");
        uutinenRepository.save(u1);
        
        Uutinen u2 = new Uutinen();
        u2.setUrl("http://www.hs.fi/kotimaa/a1414991824044");
        u2.setTitle("Pirkanmaalla toiminut valelääkäri opiskelee Oulussa lääkäriksi romanialaisena vaihto-oppilaana");
        u2.setDescription("Pirkanmaalla valelääkärinä toiminut mies opiskelee parhaillaan Oulun yliopiston lääketieteellisessä tiedekunnassa. Asiasta kertoi Seiska verkkosivuillaan.");
        uutinenRepository.save(u2);
        
        Kayttaja k1 = new Kayttaja();
        k1.setTunnus("testaaja");
        k1.setSalasana("salasana");
        kayttajaRepository.save(k1);
    }
    
}
