package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.domain.Uutinen;
import fi.uutisjuttu.uutisjuttu.repository.UutinenRepository;
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
public class UutinenController {

    @Autowired
    private UutinenRepository uutinenRepository;
    
    @Autowired
    private UutinenService uutinenService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("uutiset", uutinenRepository.findAll());
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
        model.addAttribute("uutinen", uutinenRepository.findOne(id));
        return "uutinen";
    }
    

}
