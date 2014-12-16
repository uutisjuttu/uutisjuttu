package fi.uutisjuttu.uutisjuttu.selenium;

import fi.uutisjuttu.uutisjuttu.Application;
import fi.uutisjuttu.uutisjuttu.SynchronizedApplicationRunner;
import org.jsoup.Jsoup;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class IndexTest {

    private WebDriver driver;

    @BeforeClass
    public static void setUpClass() {
        SynchronizedApplicationRunner.run();
    }

    @Before
    public void setUp() {
        this.driver = new HtmlUnitDriver();
    }

    @Test
    public void etusivullaOnSanaEeppinen() {
        driver.get("http://localhost:8080");
        assertTrue(driver.getPageSource().contains("eeppinen"));
    }

    @Test
    public void etusivullaEiOleSanaaSurkea() {
        driver.get("http://localhost:8080");
        assertFalse(driver.getPageSource().contains("surkea"));
    }

    @Test
    public void aluksiEiOllaKirjauduttuSisaan() {
        driver.get("http://localhost:8080");
        assertTrue(driver.getPageSource().contains("Kirjaudu sisään"));
        assertFalse(driver.getPageSource().contains("kirjautunut:"));
    }

    @Test
    public void kirjautuminenTestitunnuksellaToimii() {
        kirjauduSisaan("testaaja", "salasana");
        assertTrue(driver.getPageSource().contains("kirjautunut:"));
    }

    @Test
    public void kirjautuminenVaarallaSalasanallaEiToimi() {
        kirjauduSisaan("testaaja", "vaarasalasana");
        assertFalse(driver.getPageSource().contains("kirjautunut:"));
    }

    private void kirjauduSisaan(String tunnus, String salasana) {
        driver.get("http://localhost:8080");
        WebElement element = driver.findElement(By.linkText("Kirjaudu sisään"));
        element.click();
        element = driver.findElement(By.name("username"));
        element.sendKeys(tunnus);
        element = driver.findElement(By.name("password"));
        element.sendKeys(salasana);
        element.submit();
    }

    @Test
    public void tunnuksenLuominenToimii() {
        driver.get("http://localhost:8080");
        driver.findElement(By.linkText("Tee tunnus")).click();
        driver.findElement(By.name("username")).sendKeys("selenium");
        driver.findElement(By.name("password")).sendKeys("orava");
        driver.findElement(By.name("password")).submit();
        kirjauduSisaan("selenium", "orava");
        assertTrue(driver.getPageSource().contains("kirjautunut:"));
    }

    @Test
    public void uloskirjautuminenToimii() {
        driver = new HtmlUnitDriver(true);
        kirjauduSisaan("testaaja", "salasana");
        driver.findElement(By.linkText("Kirjaudu ulos")).click();
        assertFalse(driver.getPageSource().contains("kirjautunut:"));
        assertTrue(driver.getPageSource().contains("Kirjaudu sisään"));
    }

    @Test
    public void kommentointiToimiiAnonyymisti() {
        navigateToNewsWithTitle("New Yorkin uusi WTC-rakennus");
        commentToNews("lentokone tulee, oletko valmis?");
        assertTrue(driver.getPageSource().contains("lentokone tulee, oletko valmis?"));
        assertTrue(driver.getPageSource().contains("Anonyymi"));
    }

    @Test
    public void kommentointiToimiiRekisteroityneena() {
        kirjauduSisaan("testaaja", "salasana");
        navigateToNewsWithTitle("New Yorkin uusi WTC-rakennus");
        commentToNews("kuinkahan kauan meinaa pysya pystyssa");
        assertTrue(driver.getPageSource().contains("kuinkahan kauan meinaa pysya pystyssa"));
    }

    @Test
    public void kommentoiduimmatUutisetSivuNakyy() {
        kirjauduSisaan("testaaja", "salasana");
        driver.get("http://localhost:8080/uutiset/kommentoiduimmat");
        assertTrue(driver.getPageSource().contains("kirjautunut:"));
    }

    @Test
    public void kayttajaListausEiNayKirjautumattomalle() {
        driver.get("http://localhost:8080/kayttajat");
        assertFalse(driver.getPageSource().contains("testaaja"));
    }

    @Test
    public void kayttajaListausEiNayKirjautuneelle() {
        kirjauduSisaan("anssi", "kela");
        driver.get("http://localhost:8080/kayttajat");
        assertFalse(driver.getPageSource().contains("testaaja"));
    }

    @Test
    public void kayttajaListausNakyyYllapitajalle() {
        kirjauduSisaan("admin", "admin");
        driver.get("http://localhost:8080/kayttajat");
        assertTrue(driver.getPageSource().contains("testaaja"));
    }

    @Test
    public void kayttajanProfiiliEiNayKirjautumattomalle() {
        driver.get("http://localhost:8080/kayttajat/testaaja");
        assertFalse(driver.getPageSource().contains("testaaja"));
    }

    @Test
    public void kayttajanProfiiliNakyyKirjautuneelle() {
        kirjauduSisaan("anssi", "kela");
        driver.get("http://localhost:8080/kayttajat/testaaja");
        assertTrue(driver.getPageSource().contains("testaaja"));
    }

    @Test
    public void kayttajaTilinPoistamisNappiEiNayMuilleKuinAdminille() {
        kirjauduSisaan("anssi", "kela");
        driver.get("http://localhost:8080/kayttajat/testaaja");
        assertFalse(elementExists("deleteuser"));
    }

    private boolean elementExists(String id) {
        try {
            driver.findElement(By.id(id));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    @Test
    public void adminVoiPoistaaKayttajatunnuksen() {
        driver.get("http://localhost:8080");
        driver.findElement(By.linkText("Tee tunnus")).click();
        driver.findElement(By.name("username")).sendKeys("poistettava1");
        driver.findElement(By.name("password")).sendKeys("roska");
        driver.findElement(By.name("password")).submit();
        kirjauduSisaan("admin", "admin");
        driver.get("http://localhost:8080/kayttajat");
        assertTrue(driver.getPageSource().contains("poistettava1"));
        driver.get("http://localhost:8080/kayttajat/poistettava1");
        driver.findElement(By.id("deleteuser")).submit();
        driver.get("http://localhost:8080/kayttajat");
        assertFalse(driver.getPageSource().contains("poistettava1"));
    }

    @Test
    public void kayttajaVoiPoistaaOmanKommenttinsa() {
        kirjauduSisaan("anssi", "kela");
        navigateToNewsWithTitle("Suomalaistutkimus selvitti");
        commentToNews("turha rokote");
        assertTrue(driver.getPageSource().contains("turha rokote"));
        driver.findElement(By.className("deletionform")).submit();
        navigateToNewsWithTitle("Suomalaistutkimus selvitti");
        assertFalse(driver.getPageSource().contains("turha rokote"));
    }

    @Test
    public void adminVoiPoistaaKommentteja() {
        driver = new HtmlUnitDriver(true);
        navigateToNewsWithTitle("Avaruuskulttuuri");
        commentToNews("onhan se jo ollutkin mutta siita ei saa puhua");

        kirjauduSisaan("admin", "admin");
        navigateToNewsWithTitle("Avaruuskulttuuri");
        driver.findElement(By.className("deletionform")).submit();
        navigateToNewsWithTitle("Avaruuskulttuuri");
        assertFalse(driver.getPageSource().contains("ei saa puhua"));

        driver.findElement(By.linkText("Kirjaudu ulos")).click();
        kirjauduSisaan("testaaja", "salasana");
        navigateToNewsWithTitle("Avaruuskulttuuri");
        commentToNews("sanoiko joku jotain?");
        driver.findElement(By.linkText("Kirjaudu ulos")).click();

        kirjauduSisaan("admin", "admin");
        navigateToNewsWithTitle("Avaruuskulttuuri");
        driver.findElement(By.className("deletionform")).submit();
        navigateToNewsWithTitle("Avaruuskulttuuri");
        assertFalse(driver.getPageSource().contains("sanoiko joku jotain"));
    }

    private void navigateToNewsWithTitle(String text) {
        driver.get("http://localhost:8080/uutiset");
        driver.findElement(By.partialLinkText(text)).click();
    }

    private void commentToNews(String text) {
        driver.findElement(By.name("content")).sendKeys(text);
        driver.findElement(By.name("content")).submit();
    }

//    @Test
//    public void muuKuinAdminEiVoiPoistaaKayttajaTunnusta() {
//        driver = new HtmlUnitDriver(true);
//        driver.get("http://localhost:8080");
//        driver.findElement(By.linkText("Tee tunnus")).click();
//        driver.findElement(By.name("username")).sendKeys("poistettava2");
//        driver.findElement(By.name("password")).sendKeys("roska");
//        driver.findElement(By.name("password")).submit();
//        driver.get("http://localhost:8080");
//        kirjauduSisaan("anssi", "kela");
//        driver.get("http://localhost:8080/kayttajat/poistettava2");
//        driver.findElement(By.id("trydeleteuser")).submit();
//        driver.get("http://localhost:8080");
//        driver.findElement(By.linkText("Kirjaudu ulos")).click();
//        kirjauduSisaan("admin", "admin");
//        driver.get("http://localhost:8080/kayttajat");
//        assertTrue(driver.getPageSource().contains("poistettava2"));
//    }
}
