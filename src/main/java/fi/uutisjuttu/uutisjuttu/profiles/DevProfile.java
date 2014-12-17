package fi.uutisjuttu.uutisjuttu.profiles;

import fi.uutisjuttu.uutisjuttu.domain.User;
import fi.uutisjuttu.uutisjuttu.domain.Comment;
import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.domain.Publisher;
import fi.uutisjuttu.uutisjuttu.repository.UserRepository;
import fi.uutisjuttu.uutisjuttu.repository.CommentRepository;
import fi.uutisjuttu.uutisjuttu.repository.NewsRepository;
import fi.uutisjuttu.uutisjuttu.repository.PublisherRepository;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"dev", "default"})
public class DevProfile {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private NewsRepository uutinenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository kommenttiRepository;

    @PostConstruct
    public void init() {

        Publisher p1 = new Publisher();
        p1.setName("Yle Uutiset");
        p1.setShortname("yle");
        p1.setNews(new ArrayList<News>());
        p1 = publisherRepository.save(p1);

        Publisher p2 = new Publisher();
        p2.setName("HS.fi");
        p2.setShortname("hs");
        p2.setNews(new ArrayList<News>());
        p2 = publisherRepository.save(p2);

        News u1 = new News();
        u1.setUrl("http://yle.fi/uutiset/new_yorkin_uusi_wtc-rakennus_avautui_liiketoiminnalle__ensimmaiset_tyontekijat_muuttivat/7597536");
        u1.setTitle("New Yorkin uusi WTC-rakennus avautui liiketoiminnalle – ensimmäiset työntekijät muuttivat");
        u1.setDescription("One World Trade Centerin ensimmäinen vuokralainen on kustannusyhtiö Conde Nast.");
        u1.setPublisher(p1);
        u1.setComments(new ArrayList<Comment>());
        u1 = uutinenRepository.save(u1);

        p1.getNews().add(u1);
        publisherRepository.save(p1);

        News u2 = new News();
        u2.setUrl("http://www.hs.fi/kotimaa/a1414991824044");
        u2.setTitle("Pirkanmaalla toiminut valelääkäri opiskelee Oulussa lääkäriksi romanialaisena vaihto-oppilaana");
        u2.setDescription("Pirkanmaalla valelääkärinä toiminut mies opiskelee parhaillaan Oulun yliopiston lääketieteellisessä tiedekunnassa. Asiasta kertoi Seiska verkkosivuillaan.");
        u2.setPublisher(p2);
        u2.setComments(new ArrayList<Comment>());
        u2 = uutinenRepository.save(u2);

        p2.getNews().add(u2);
        p2 = publisherRepository.save(p2);

        News u3 = new News();
        u3.setUrl("http://jotain");
        u3.setTitle("Suomalaistutkimus selvitti että kissat koiria sghsdfgassagdsfg");
        u3.setDescription("Asiasta kertoi Seiska verkkosivuillaan.");
        u3.setPublisher(p2);
        u3.setComments(new ArrayList<Comment>());
        u3 = uutinenRepository.save(u3);

        p2.getNews().add(u3);
        p2 = publisherRepository.save(p2);

        News u4 = new News();
        u4.setUrl("http://jotainmuuta");
        u4.setTitle("Avaruuskulttuuri on täällä");
        u4.setDescription("Asiasta kertoi Iltalehti verkkosivuillaan.");
        u4.setPublisher(p2);
        u4.setComments(new ArrayList<Comment>());
        u4 = uutinenRepository.save(u4);

        p2.getNews().add(u4);
        p2 = publisherRepository.save(p2);

        User k1 = new User();
        k1.setUsername("testaaja");
        k1.setPassword("salasana");
        k1.setKommentit(new ArrayList<Comment>());
        k1 = userRepository.save(k1);

        User k2 = new User();
        k2.setUsername("anssi");
        k2.setPassword("kela");
        k2.setKommentit(new ArrayList<Comment>());
        k2 = userRepository.save(k2);

        Comment kommentti1 = new Comment();
        kommentti1.setAuthor(k1);
        kommentti1.setNews(u2);
        kommentti1.setContent("Nyt on siellä paikassa missä potilaat olisivat toivoneet olleen jo paljon aikaisemmin");
        kommentti1 = kommenttiRepository.save(kommentti1);
        k1.getComments().add(kommentti1);
        u2.getComments().add(kommentti1);
        uutinenRepository.save(u2);
        userRepository.save(k1);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setSuperuser(true);
        userRepository.save(admin);

    }

}
