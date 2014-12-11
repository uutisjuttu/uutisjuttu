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
    private PublisherRepository publisherRepository;

    @Async
    public void addNewsArticleByUrl(String url) {
        News news = new News();
//        news.setDescription("odotappas hetkinen...");
//        news = uutinenRepository.save(news);
        String publisher = "";
        try {
            Document doc = Jsoup.connect(url).
                    userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36")
                    .get();

            Elements elem = doc.head().getElementsByTag("meta");
            for (Element e : elem) {
//                System.out.println(e.attr("property") + ": " + e.attr("content"));
                if (e.attr("property").equals("og:description")) {
                    news.setDescription(e.attr("content"));
                }

                if (e.attr("property").equals("og:title")) {
                    news.setTitle(e.attr("content"));
                }

                if (e.attr("property").equals("og:url")) {
                    news.setUrl(e.attr("content"));
                }

                if (e.attr("property").equals("og:site_name")) {
                    publisher = e.attr("content");
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            uutinenRepository.delete(news);
            return;
        }

        Publisher p = publisherRepository.findByName(publisher);
        if (p == null) {
            p = new Publisher();
            p.setName(publisher);
            p.setNews(new ArrayList<News>());
            p = publisherRepository.save(p);
        }
        news.setPublisher(p);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        news = uutinenRepository.save(news);

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
    }
}
