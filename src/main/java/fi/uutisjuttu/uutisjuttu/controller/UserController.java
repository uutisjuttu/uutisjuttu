package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.User;
import fi.uutisjuttu.uutisjuttu.repository.UserRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/kayttajat")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping(method = RequestMethod.POST)
    public String lisaaKayttaja(@Valid @ModelAttribute User kayttaja) {
        userRepository.save(kayttaja);
        return "redirect:/index";
    }
    
    
}
