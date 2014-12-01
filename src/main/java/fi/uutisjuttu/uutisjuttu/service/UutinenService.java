/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uutisjuttu.uutisjuttu.service;

import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.repository.NewsRepository;
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
    private NewsRepository uutinenRepository;

    @Async
    public void lisaaUutinenOsoitteenPerusteella(String url) {
        News uutinen = new News();
        uutinen.setDescription("odotappas hetkinen...");
        uutinen = uutinenRepository.save(uutinen);
        int asetetutAttribuutit = 0;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elem = doc.head().getElementsByTag("meta");
            for (Element e : elem) {
                if (e.attr("property").equals("og:description")) {
                    uutinen.setDescription(e.attr("content"));
                    asetetutAttribuutit++;
                }
                
                if (e.attr("property").equals("og:title")) {
                    uutinen.setTitle(e.attr("content"));
                    asetetutAttribuutit++;
                }
                
                if (e.attr("property").equals("og:url")) {
                    uutinen.setUrl(e.attr("content"));
                    asetetutAttribuutit++;
                }
            }

        } catch (Exception ex) {
            uutinenRepository.delete(uutinen);
            return;
        }
        
        if (asetetutAttribuutit != 3) {
            uutinenRepository.delete(uutinen);
        }

        uutinenRepository.save(uutinen);

        return;
    }

}
