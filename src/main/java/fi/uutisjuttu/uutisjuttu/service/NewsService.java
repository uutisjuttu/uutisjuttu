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
import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
    private PublisherService publisherService;

    @Autowired
    private PublisherRepository publisherRepository;

    public void addNewsArticleByUrl(String url) {
        System.out.println("========================================");
        System.out.println("yritetään lisätä uutista osoitteella: " + url);
        News news = new News();
        String publisher = "";
        try {
            Document doc = Jsoup.connect(url).
                    userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36")
                    .get();
            System.out.println("muodostettiin yhteys palvelimeen.");
            Elements elem = doc.head().getElementsByTag("meta");
            for (Element e : elem) {
//                System.out.println(e.attr("property") + ": " + e.attr("content"));
                if (e.attr("property").equals("og:description")) {
                    System.out.println("description: " + e.attr("content"));
                    news.setDescription(e.attr("content"));
                }

                if (e.attr("property").equals("og:title")) {
                    System.out.println("title: " + e.attr("content"));
                    news.setTitle(e.attr("content"));
                }

                if (e.attr("property").equals("og:url")) {
                    System.out.println("url: " + e.attr("content"));
                    news.setUrl(e.attr("content"));
                }

                if (e.attr("property").equals("og:site_name")) {
                    System.out.println("site name: " + e.attr("content"));
                    publisher = e.attr("content");
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            uutinenRepository.delete(news);
            return;
        }

        Publisher p = publisherService.getPublisherByName(publisher);

        if (p == null) {
            System.out.println("julkaisijaa " + publisher + " ei löydy tuettujen listalta, lopetetaan");
            System.out.println("========================================");
            return;
        }

        news.setPublisher(p);
        System.out.println("uutisen julkaisijaksi asetettu " + p.getName());

//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
        System.out.print("tallennetaan uutinen tietokantaan...");
        news = uutinenRepository.save(news);
        System.out.println("valmis.");

//        Set<ConstraintViolation<News>> errors = validator.validate(news, News.class);
//        if (errors.size() > 0) {
//            System.out.println("ERRORS:");
//            for (ConstraintViolation<News> c : errors) {
//                System.out.println(c);
//            }
//            uutinenRepository.delete(news);
//            return;
//        }
        p.getNews().add(news);
        publisherRepository.save(p);
        System.out.println("uutinen lisätty julkaisijalle " + p.getName() + ". julkaisijalla on nyt " + p.getNews().size() + " uutista.");
        System.out.println("========================================");
    }
}
