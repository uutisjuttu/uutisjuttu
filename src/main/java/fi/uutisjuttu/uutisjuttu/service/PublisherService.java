/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.uutisjuttu.uutisjuttu.service;

import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.domain.Publisher;
import fi.uutisjuttu.uutisjuttu.repository.PublisherRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    private static final Map<String, String> publishers;
    static {
        publishers = new HashMap<>();
        publishers.put("Uutisjutun Sanomat", "uutisjuttu");
        publishers.put("HS.fi", "hs");
        publishers.put("Yle Uutiset", "yle");
        publishers.put("mtv.fi", "mtv");
        publishers.put("Kauppalehti.fi", "kauppalehti");
        publishers.put("Nyt.fi", "nyt");
        publishers.put("Uusi Suomi", "uusisuomi");
        publishers.put("Metro.fi", "metro");
        publishers.put("Kaleva.fi", "kaleva");
    }
    
    @Autowired
    private PublisherRepository publisherRepository;
    
    public PublisherService() {
        
    }
    
    public Publisher getPublisherByName(String name) {
        if (!publishers.containsKey(name)) {
            return null;
        }

        System.out.print("tutkitaan löytyykö julkaisija " + name + " jo tietokannasta...");
        Publisher p = publisherRepository.findByName(name);
        System.out.println("valmis.");
        if (p == null) {
            System.out.println("julkaisijaa " + name + " ei löytynyt. luodaan se nyt.");
            p = new Publisher();
            p.setName(name);
            p.setShortname(publishers.get(name));
            p.setNews(new ArrayList<News>());
            p = publisherRepository.save(p);
            System.out.println("julkaisija " + p.getName() + " tallennettu tietokantaan.");
        } else {
            System.out.println("julkaisija " + p.getName() + " löytyi. julkaisijalla on jo entuudestaan " + p.getNews().size() + " uutista.");
        }
        return p;
    }
    
    

}
