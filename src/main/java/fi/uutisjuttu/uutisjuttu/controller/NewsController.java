package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.repository.NewsRepository;
import fi.uutisjuttu.uutisjuttu.service.UutinenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/uutiset")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;
    
    @Autowired
    private UutinenService uutinenService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("news", newsRepository.findAll());
        return "uutiset";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String add(@RequestParam String url) {
        uutinenService.lisaaUutinenOsoitteenPerusteella(url);
        //uutinenRepository.save(uutinen);
        return "redirect:/uutiset";
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("news", newsRepository.findOne(id));
        return "uutinen";
    }
    

}
