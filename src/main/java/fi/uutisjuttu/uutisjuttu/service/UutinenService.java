/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uutisjuttu.uutisjuttu.service;

import fi.uutisjuttu.uutisjuttu.domain.Uutinen;
import fi.uutisjuttu.uutisjuttu.repository.UutinenRepository;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UutinenService {

    @Autowired
    private UutinenRepository uutinenRepository;

    @Async
    public void lisaaUutinenOsoitteenPerusteella(String url) {
        Uutinen uutinen = new Uutinen();
        uutinen.setDescription("odotappas hetkinen...");
        uutinen = uutinenRepository.save(uutinen);
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elem = doc.head().getElementsByTag("meta");
//            System.out.println("Meta elementit sivulla:");
            for (Element e : elem) {
                if (e.attr("property").equals("og:description")) {
                    uutinen.setDescription(e.attr("content"));
                }
                
                if (e.attr("property").equals("og:title")) {
                    uutinen.setTitle(e.attr("content"));
                }
                
                if (e.attr("property").equals("og:url")) {
                    uutinen.setUrl(e.attr("content"));
                }
            }

        } catch (Exception ex) {
            uutinenRepository.delete(uutinen);
            return;
        }

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException ex) {
//        }
//        uutinen.setUrl(url);
        uutinenRepository.save(uutinen);

    }

}
