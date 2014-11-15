package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.Kayttaja;
import fi.uutisjuttu.uutisjuttu.service.KayttajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author mikko
 */
@Controller
@RequestMapping("*")
public class DefaultController {

    @Autowired
    private KayttajaService kayttajaService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String getDefaultPage() {
        Kayttaja k = kayttajaService.getAuthenticatedPerson();
        if (k != null) {
            System.out.println(k.getTunnus());
        } else {
            System.out.println("ei autentikoitunut");
        }
        return "index";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String viewLogin(Model model) {
        return "login";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String viewSignup(Model model) {
        return "signup";
    }

}
