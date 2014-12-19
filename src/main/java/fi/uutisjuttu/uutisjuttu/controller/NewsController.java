package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.repository.NewsRepository;
import fi.uutisjuttu.uutisjuttu.service.NewsException;
import fi.uutisjuttu.uutisjuttu.service.NewsService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/uutiset")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsService newsService;

    @RequestMapping(method = RequestMethod.GET)
    public String mostRecent(Model model) {
        Pageable pageable = new PageRequest(0, 50, Sort.Direction.DESC, "submitted");
        model.addAttribute("news", newsRepository.findAll(pageable).getContent());
        return "uutiset";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/kommentoiduimmat")
    public String mostCommented(Model model) {
//        Pageable pageable = new PageRequest(0, 50, Sort.Direction.DESC, "numberOfComments");
//        Pageable pageable = new PageRequest(0, 50, Sort.Direction.DESC, "title");
        List<News> content = newsRepository.findAll();
        Collections.sort(content, new Comparator<News>() {
            @Override
            public int compare(News o1, News o2) {
                return o2.getComments().size() - o1.getComments().size();
            }
        });
        model.addAttribute("news", content);
        return "uutiset";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String add(@RequestParam String url, RedirectAttributes redirectAttributes) {
        try {
            newsService.addNewsArticleByUrl(url);
        } catch (NewsException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

//        if (!errors.isEmpty()) {
//            redirectAttributes.addFlashAttribute("errors", errors);
//        }
        //uutinenRepository.save(uutinen);
        return "redirect:/uutiset";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("news", newsRepository.findOne(id));
        return "uutinen";
    }

}
