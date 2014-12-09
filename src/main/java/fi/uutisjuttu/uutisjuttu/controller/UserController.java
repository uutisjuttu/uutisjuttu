package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.Comment;
import fi.uutisjuttu.uutisjuttu.domain.User;
import fi.uutisjuttu.uutisjuttu.repository.UserRepository;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/kayttajat")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String view(@PathVariable String username, Model model) {
        model.addAttribute("user", userRepository.findByUsername(username));
        return "user";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String lisaaKayttaja(@Valid @ModelAttribute User kayttaja) {
        userRepository.save(kayttaja);
        return "redirect:/index";
    }
    
    
}
