package fi.uutisjuttu.uutisjuttu.service;

import fi.uutisjuttu.uutisjuttu.Application;
import fi.uutisjuttu.uutisjuttu.SynchronizedApplicationRunner;
import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.repository.NewsRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UutinenServiceTest {

    @Autowired
    private NewsService uutinenService;

    @Autowired
    private NewsRepository uutinenRepository;

    @BeforeClass
    public static void laitaServeriPaalle() {
        SynchronizedApplicationRunner.run();
    }

    @Test
    public void alussaOnNeljaUutista() {
        assertEquals(4, uutinenRepository.findAll().size());
    }

    @Test
    public void uutisestaSaadaanLuettuaMetatiedotOikein() {
        try {
            uutinenService.addNewsArticleByUrl("http://localhost:8080/static/testpages/uutinen1.html");
        } catch (NewsException ex) {
            fail(ex.getMessage());
        }

        News u = uutinenRepository.findByUrl("http://localhost:8080/test/uutinen1?jako=1");

        assertNotNull(u);
        assertEquals("Kehitteillä oleva uutiskommentointisivusto julkaisi tänään ensimmäisen puhtaasti testitarkoituksessa käytetyn uutisen.", u.getDescription());
        assertEquals("Ensimmäinen testiuutinen on julkaistu", u.getTitle());
        assertEquals("http://localhost:8080/test/uutinen1?jako=1", u.getUrl());
        assertEquals("Uutisjutun Sanomat", u.getPublisher().getName());

    }

}
