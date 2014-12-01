package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.User;
import fi.uutisjuttu.uutisjuttu.domain.Comment;
import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.repository.UserRepository;
import fi.uutisjuttu.uutisjuttu.repository.CommentRepository;
import fi.uutisjuttu.uutisjuttu.repository.NewsRepository;
import fi.uutisjuttu.uutisjuttu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(method = RequestMethod.POST)
    public String lisaaKommentti(@RequestParam Long newsId, @RequestParam String content) {
        News uutinen = uutinenRepository.findOne(newsId);
        if (uutinen == null) {
            return "redirect:/uutiset/" + newsId;
        }
        User lahettaja = kayttajaService.getAuthenticatedPerson();
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

}