package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tilastot")
public class StatisticsController {

    @Autowired
    private PublisherRepository publisherRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String viewAll(Model model) {
        model.addAttribute("publishers", publisherRepository.findAll());
        return "tilastot";
    }

}
