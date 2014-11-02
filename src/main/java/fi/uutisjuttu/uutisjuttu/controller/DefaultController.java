package fi.uutisjuttu.uutisjuttu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author mikko
 */
@Controller
@RequestMapping("*")
public class DefaultController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String getDefaultPage() {
        return "index";
    }
    
}
