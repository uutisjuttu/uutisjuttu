package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.repository.NewsRepository;
import fi.uutisjuttu.uutisjuttu.service.UutinenService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public String mostRecent(Model model) {
        model.addAttribute("news", newsRepository.findAll());
        return "uutiset";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/kommentoiduimmat")
    public String mostCommented(Model model) {
//        Pageable pageable = new PageRequest(0, 50, Sort.Direction.DESC, "numberOfComments");
//        Pageable pageable = new PageRequest(0, 50, Sort.Direction.DESC, "title");
        List<News> content = newsRepository.findAll();
        Collections.sort(content, new Comparator<News>(){
            @Override
            public int compare(News o1, News o2) {
                return o2.getComments().size()-o1.getComments().size();
            }
        });
        model.addAttribute("news", content);
        return "uutiset";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String add(@RequestParam String url) {
        uutinenService.lisaaUutinenOsoitteenPerusteella(url);
        //uutinenRepository.save(uutinen);
        return "redirect:/uutiset";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("news", newsRepository.findOne(id));
        return "uutinen";
    }

}
