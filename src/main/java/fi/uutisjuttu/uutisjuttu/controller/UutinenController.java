package fi.uutisjuttu.uutisjuttu.controller;

import fi.uutisjuttu.uutisjuttu.repository.UutinenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/uutiset")
public class UutinenController {

    @Autowired
    private UutinenRepository uutinenRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("uutiset", uutinenRepository.findAll());
        return "uutiset";
    }

}
