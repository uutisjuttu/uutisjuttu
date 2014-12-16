package fi.uutisjuttu.uutisjuttu.service;

import fi.uutisjuttu.uutisjuttu.Application;
import fi.uutisjuttu.uutisjuttu.SynchronizedApplicationRunner;
import fi.uutisjuttu.uutisjuttu.domain.News;
import fi.uutisjuttu.uutisjuttu.repository.NewsRepository;
import org.jsoup.Jsoup;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
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
        uutinenService.addNewsArticleByUrl("http://localhost:8080/test/uutinen1");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            fail("Thread.sleep(1000) heitti poikkeuksen!");
            e.printStackTrace();
        }
        News u = uutinenRepository.findByUrl("http://localhost:8080/test/uutinen1?jako=1");

        assertNotNull(u);
        assertEquals("Kehitteillä oleva uutiskommentointisivusto julkaisi tänään ensimmäisen puhtaasti testitarkoituksessa käytetyn uutisen.", u.getDescription());
        assertEquals("Ensimmäinen testiuutinen on julkaistu", u.getTitle());
        assertEquals("http://localhost:8080/test/uutinen1?jako=1", u.getUrl());
        assertEquals("Uutisjutun Sanomat", u.getPublisher().getName());

    }

//    @Test
//    public void uutisenLisaamisenJalkeenUutistenMaaraOnKolme() throws InterruptedException {
//        Thread.sleep(1000);
//        uutinenService.lisaaUutinenOsoitteenPerusteella("http://localhost:8080/test/uutinen1");
//        Thread.sleep(1000);
//        assertEquals(3, uutinenRepository.findAll().size());
//    }
}
