package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.Comment;
import fi.uutisjuttu.uutisjuttu.domain.User;
import fi.uutisjuttu.uutisjuttu.repository.UserRepository;
import fi.uutisjuttu.uutisjuttu.service.UserService;
import java.util.ArrayList;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

    @Secured("ROLE_SUPERUSER")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String view(@PathVariable String username, Model model) {
        model.addAttribute("user", userRepository.findByUsername(username));
        return "user";
    }

    @Secured("ROLE_SUPERUSER")
    @Transactional
    @RequestMapping(value = "/{username}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);

        for (Comment c : user.getComments()) {
            c.getNews().getComments().remove(c);
            c.setAuthor(null);
        }

        userRepository.delete(user);
        return "redirect:/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String lisaaKayttaja(@Valid @ModelAttribute User kayttaja) {
        if ("admin".equals(kayttaja.getUsername())) {
            if (userRepository.findByUsername("admin") == null) {
                kayttaja.setSuperuser(true);
            }
        }
        userRepository.save(kayttaja);
        return "redirect:/index";
    }

}
