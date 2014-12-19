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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsRepository uutinenRepository;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private PublisherRepository publisherRepository;

    public void addNewsArticleByUrl(String url) throws NewsException {
        URL parsedURL = parsiURL(url);
        Elements elem = haeMetatiedotPalvelimelta(url);
        News news = luoUutinenElementtienPerusteella(elem, parsedURL);
        tarkistaEtteiUutistaLoydyJo(news);
        tarkistaEttaUrlVastaaJulkaisijanOsoitetta(parsedURL, news.getPublisher(), url);
        tallennaUutinen(news);
    }

    private URL parsiURL(String url) throws NewsException {
        URL parsedURL;
        try {
            parsedURL = new URL(url);
        } catch (MalformedURLException ex) {
            throw new NewsException("osoite " + url + " ei vaikuta oikealta www-osoitteelta");
        }
        return parsedURL;
    }

    private void tallennaUutinen(News news) throws NewsException {
        try {
            news = uutinenRepository.save(news);
        } catch (Exception e) {
            throw new NewsException("Tapahtui virhe talletettaessa uutista tietokantaan.");
        }

        news.getPublisher().getNews().add(news);
        publisherRepository.save(news.getPublisher());
    }

    private News luoUutinenElementtienPerusteella(Elements elem, URL parsedURL) throws NewsException {
        String og_description = null;
        String description = null;
        String og_title = null;
        String og_url = null;
        String og_sitename = null;

        for (Element e : elem) {
//                System.out.println(e.attr("property") + ": " + e.attr("content"));
            if (e.attr("property").equals("og:description")) {
                og_description = e.attr("content");
                System.out.println("og:description: " + e.attr("content"));
            }

            if (e.attr("property").equals("description")) {
                System.out.println("description: " + e.attr("content"));
                description = e.attr("content");
            }

            if (e.attr("property").equals("og:title")) {
                System.out.println("title: " + e.attr("content"));
                og_title = e.attr("content");
            }

            if (e.attr("property").equals("og:url")) {
                System.out.println("url: " + e.attr("content"));
                og_url = e.attr("content");
            }

            if (e.attr("property").equals("og:site_name")) {
                og_sitename = e.attr("content");
                System.out.println("site name: " + e.attr("content"));
            }
        }
        News news = luoUutinenAttribuuttienPerusteella(og_description, description, og_url, parsedURL, og_sitename, og_title);
        return news;

    }

    private News luoUutinenAttribuuttienPerusteella(String og_description, String description, String og_url, URL parsedURL, String og_sitename, String og_title) throws NewsException {
        News news = new News();
        if (og_description == null) {
            if (description == null) {
                throw new NewsException("Virhe luettaessa uutista. (sivulta puuttui vaadittu meta-attribuutti \"og:description\")");
            } else {
                news.setDescription(description);
            }
        } else {
            news.setDescription(og_description);
        }

        if (og_url == null) {
            news.setUrl(parsedURL.getProtocol() + "://" + parsedURL.getHost() + parsedURL.getPath());
        } else {
            news.setUrl(og_url);
        }

        if (og_sitename == null) {
            throw new NewsException("Virhe luettaessa uutista. (sivulta puuttui vaadittu meta-attribuutti \"og:site_name\")");
        } else {
            news.setPublisher(publisherService.getPublisherByName(og_sitename));
        }

        if (og_title == null) {
            throw new NewsException("Virhe luettaessa uutista. (sivulta puuttui vaadittu meta-attribuutti \"og:title\")");
        }
        news.setTitle(og_title);
        return news;
    }

    private Elements haeMetatiedotPalvelimelta(String url) throws NewsException {
        Elements elem;
        try {
            Document doc = Jsoup.connect(url).
                    userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36")
                    .get();
            System.out.println("muodostettiin yhteys palvelimeen.");
            elem = doc.head().getElementsByTag("meta");
        } catch (IOException ex) {
            throw new NewsException("Virhe tapahtui yhdistettäessä palvelimeen osoitteessa " + url);
        }
        return elem;
    }

    private void tarkistaEtteiUutistaLoydyJo(News news) throws NewsException {
        if (uutinenRepository.findByUrl(news.getUrl()) != null) {
            throw new NewsException("Uutinen annetulla osoitteella löytyi jo!");
        }
    }

    private void tarkistaEttaUrlVastaaJulkaisijanOsoitetta(URL parsedURL, Publisher p, String url) throws NewsException {
        if (!parsedURL.getHost().endsWith(p.getShortname() + ".fi") && !parsedURL.getHost().endsWith(p.getShortname())) {
            throw new NewsException("antamasi osoite " + url + " ei loppunut " + p.getShortname() + ".fi");
        }
    }
}
