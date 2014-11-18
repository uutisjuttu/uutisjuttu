package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.Kayttaja;
import fi.uutisjuttu.uutisjuttu.domain.Kommentti;
import fi.uutisjuttu.uutisjuttu.domain.Uutinen;
import fi.uutisjuttu.uutisjuttu.repository.KayttajaRepository;
import fi.uutisjuttu.uutisjuttu.repository.KommenttiRepository;
import fi.uutisjuttu.uutisjuttu.repository.UutinenRepository;
import fi.uutisjuttu.uutisjuttu.service.KayttajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/kommentit")
public class KommenttiController {

    @Autowired
    private KommenttiRepository kommenttiRepository;

    @Autowired
    private UutinenRepository uutinenRepository;

    @Autowired
    private KayttajaRepository kayttajaRepository;

    @Autowired
    private KayttajaService kayttajaService;

    @RequestMapping(method = RequestMethod.POST)
    public String lisaaKommentti(@RequestParam Long newsId, @RequestParam String content) {
        Uutinen uutinen = uutinenRepository.findOne(newsId);
        if (uutinen == null) {
            return "redirect:/uutiset/" + newsId;
        }
        Kayttaja lahettaja = kayttajaService.getAuthenticatedPerson();
        Kommentti kommentti = new Kommentti();
        kommentti.setUutinen(uutinen);
        kommentti.setLahettaja(lahettaja);
        kommentti.setSisalto(content);
        kommentti = kommenttiRepository.save(kommentti);
        uutinen.getKommentit().add(kommentti);
        uutinenRepository.save(uutinen);
        if (lahettaja != null) {
            lahettaja.getKommentit().add(kommentti);
            kayttajaRepository.save(lahettaja);

        }

        return "redirect:/uutiset/" + newsId;
    }

}
