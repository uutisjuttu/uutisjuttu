package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.User;
import fi.uutisjuttu.uutisjuttu.domain.Comment;
import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.repository.UserRepository;
import fi.uutisjuttu.uutisjuttu.repository.CommentRepository;
import fi.uutisjuttu.uutisjuttu.repository.NewsRepository;
import fi.uutisjuttu.uutisjuttu.service.UserService;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/kommentit")
public class CommentController {

    @Autowired
    private CommentRepository kommenttiRepository;

    @Autowired
    private NewsRepository uutinenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService kayttajaService;

    @RequestMapping(method = RequestMethod.GET)
    public String listComments(Model model) {
        model.addAttribute("comments", kommenttiRepository.findAll());
        return "comments";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String lisaaKommentti(@RequestParam Long newsId, @RequestParam String content) {
        News uutinen = uutinenRepository.findOne(newsId);
        if (uutinen == null) {
            return "redirect:/uutiset/" + newsId;
        }
        User lahettaja = kayttajaService.getAuthenticatedUser();
        Comment kommentti = new Comment();
        kommentti.setNews(uutinen);
        kommentti.setAuthor(lahettaja);
        kommentti.setContent(content);
        kommentti = kommenttiRepository.save(kommentti);
        uutinen.getComments().add(kommentti);
        uutinenRepository.save(uutinen);
        if (lahettaja != null) {
            lahettaja.getComments().add(kommentti);
            userRepository.save(lahettaja);

        }
        return "redirect:/uutiset/" + newsId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/delete")
    @Transactional
    public String deleteComment(@PathVariable Long id) {
        Comment c = kommenttiRepository.findOne(id);
        if (c == null) {
            return "redirect:/uutiset/";
        }
        Long newsId = c.getNews().getId();
        if (c.getAuthor() != null) {
            c.getAuthor().getComments().remove(c);
            userRepository.save(c.getAuthor());
        }
        c.getNews().getComments().remove(c);
        uutinenRepository.save(c.getNews());
        kommenttiRepository.delete(c);
        return "redirect:/uutiset/" + newsId;
    }

}
