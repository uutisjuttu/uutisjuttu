package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.Kayttaja;
import fi.uutisjuttu.uutisjuttu.repository.KayttajaRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/kayttajat")
public class KayttajaController {
    
    @Autowired
    private KayttajaRepository kayttajaRepository;
    
    @RequestMapping(method = RequestMethod.POST)
    public String lisaaKayttaja(@Valid @ModelAttribute Kayttaja kayttaja) {
        kayttajaRepository.save(kayttaja);
        return "redirect:/index";
    }
    
    
}
