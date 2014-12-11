/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uutisjuttu.uutisjuttu.service;

import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.domain.Publisher;
import fi.uutisjuttu.uutisjuttu.repository.NewsRepository;
import fi.uutisjuttu.uutisjuttu.repository.PublisherRepository;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsRepository uutinenRepository;
    
    @Autowired
    private PublisherRepository publisherRepository;

    @Async
    public void addNewsArticleByUrl(String url) {
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

                if (e.attr("property").equals("og:site_name")) {
                    setPublisher(uutinen, e.attr("content"));
                    asetetutAttribuutit++;
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            uutinenRepository.delete(uutinen);
            return;
        }

        if (asetetutAttribuutit != 4) {
            System.out.println("asetetut attribuutit eri suuri kuin 4! (" + asetetutAttribuutit + ")");
            uutinenRepository.delete(uutinen);
        }

        uutinenRepository.save(uutinen);

        return;
    }
    
    private void setPublisher(News news, String publisher) {
        System.out.println("setPublisher " + news + " publisher " + publisher);
        Publisher p = publisherRepository.findByName(publisher);
        if (p == null) {
            p = new Publisher();
            p.setName(publisher);
            p.setNews(new ArrayList<News>());
            p = publisherRepository.save(p);
        }
        p.getNews().add(news);
        publisherRepository.save(p);
        news.setPublisher(p);
    }

}
