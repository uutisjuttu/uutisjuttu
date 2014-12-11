package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/julkaisija")
public class PublisherController {
    
    @Autowired
    private PublisherRepository publisherRepository;
        
    @RequestMapping(method = RequestMethod.GET)
    public String get(Model model) {
        return "redirect:/tilastot";
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    public String get(Model model, @PathVariable Long id) {
        model.addAttribute("publisher", publisherRepository.findOne(id));
        return "publisher";
    }
    
}
