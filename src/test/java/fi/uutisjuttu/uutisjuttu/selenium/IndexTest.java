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

//    @Test
//    public void etusivullaOnSanaEeppinen() {
//        driver.get("http://localhost:8080");
//        assertTrue(driver.getPageSource().contains("Eeppinen"));
//    }
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
    public void pitkanKommentinLaittaminenToimii() {
        String s1000 = "alku01I4fv0SnVAn3Frpg1aeGZzgzjhI7wRuu9C8E7qTBaCOga52UDclzonAlmg3C07nOiGWhjis4PHtLPx0GLCFpr1rxpxDK9uv815QvvxnrmotiyQKXV1xVGl400qkBbepgJMTRWnN4ETP361oljCR5VgZVkGvAyZI9Y4JYqGiqKzaPfCkJtKh5xIRgmmMz7hqtgJpJfHugOhP6Pb0bTD3IMFGOIrZCqs9eRWNv7wTOSPQqzD6IY7ChMmoQ0FE906uXu7zIjMqZNkkY4aCNvIxHDW030T3EKUs0oMCu7Kqqt7NJkhpcUrZcBB863Xul1WtUxWYVacl0bmbbI9rPBgXKeEQO0wggeo5ojalKmvnQLaDaXhEyNGCABRb32JyLAOhGlwiUr9TAI7rie6qeNRc14VlRDY1sJI6oIEmHxoykJix4HILBQC5pJbPXCNoywJY5TUDh9zbrF5T6obirKNjq5mE6MxH2czwsJEtWeOCNcqBs60f4N4nHb61FvNRZCAYo6EFtcpXwESpOK4Nkf4uKa534QSXUbjErHBPwDCI8gQGM46i5HckeVnIt2ibMHfX1AXA2LQWBuLPiTW2a4rjHOoLcF6Nslwy2IRn7h4uwymaCiyLkAIB5zJ9vFTf9LiRjaRCivbK9XPE8TpXhwjogB9lm0Bsbk0EXGpDivJ7YJtQHxwTKglBLupi3U1ZSJma8YuJ1SlzjZZnU26lqNeck3e0CUN2LiNsLvUX8A1Max5P88S4jfa8SbC8i6HiCgJukwFo1LxXiAzHjx2f4UKklRpxtHWTKrKLD3lhFMmVCzTD30I8FNbCGqKL9u4GuRxtFiI917yV18obFhEbOAz7MLLUq8aGr3eMnN64HGL3IAmJuWGBZBATDMrAFezW4msSa2EqJq4c0ZIvFwEwNsoGQ3UFSrr2lqvKKa2p2YDRFgXnkjuEJR5HiCNhMWN1fqRB6lhFjXY3cAY9f0iPfixpw2ZhCxhpO6nloppu";
        //String pitkakommentti = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vestibulum, purus ut cursus consequat, ipsum tortor tincidunt justo, sed pellentesque dolor massa et quam. Suspendisse facilisis pellentesque massa, at elementum elit. Etiam mollis enim erat, quis efficitur quam ornare quis. Duis quis nunc quis dolor maximus cursus. Aliquam bibendum orci sit amet erat accumsan, ut tincidunt dui fringilla. Morbi sollicitudin risus sit amet augue ornare, et tempor nibh dictum. Phasellus feugiat maximus leo nec vestibulum. Nulla aliquam viverra nisl nec volutpat. Phasellus congue lobortis magna, et vestibulum dui tincidunt ac. Donec sed arcu mattis, commodo odio a, dapibus lacus. Mauris.";
        navigateToNewsWithTitle("New Yorkin uusi WTC-rakennus");
        commentToNews(s1000);
        assertTrue(driver.getPageSource().contains("alku"));
        assertTrue(driver.getPageSource().contains("loppu"));
    }

    @Test
    public void liianPitkaKommenttiTrimmataan() {
        String s1001 = "aloitusObi4qsX0ILhxAvgkUeatW4I72ExGMtsfGXXi7H1bF2pvt58FCE136SVGVc0gc1ERs1D3pBjaUDEQg3ck86Pb1tbfmR5y4ZkJ2yB8emfMLTbPWok9gnSbNvERe8jHtHMPI9PTszmira9AIGmHR69RiH4L523xFjlurrNcDIzbuAaweOOiCOLUBpsWLpcN8NsgUP6xYUQOPlJ8KWnLGlhwh9OBXbHk35I56VEXStotmygTJGTFO5UKmSC1ilUrvjZFzmTFqW6CY1PQFOXghEPkeQDSqGjBViKTisDUk9rvShHFpZxSLmMgelv7j3h6NRU3VN5XI2p9L4qU8PhgbIW64qAfIAm3CvoXyPg8PPHguax9bCODW2ryrcB1SmXyawBb5SRbzNZIrWmfxTjavJ1KDs7JHxAYqZHSVnJ6242vrva1iopOJvHSvsH2bANCOuL0DhCzewpCfsBGGzYZZffioIlL0WQyBGfY9Gi1Y5m2wSR3i9vTph5WZbqZtU3AvGr8nQzFV50EJHchc2NfpFRSTRRmTXEsPxaNJaQf4LMapJZRytGhE14BxZQNBxxOK5cH4mAXOwqMxFLy4xquuttqc8tP6p7neKMzhKxVkp1gvHJ54hcL0IRq4qTDgf1s6vJjwNgPoYLz9usxt10DfK5NoeRtC8ws2QzppGak8N0QsCWTCCTBVfLFhhzbYMv17nDlCaAKPE3vPcZ1Vy6YuyPU7E1YR28rzK7hYgbMVMb2vgyEe4GUqglNoDeSFXHqwVwOFy9n7c0Evj8GeCzUoZbDRXtWePDTn59BLIJJXQSA3v4OgUH46C0KoiQQEuzg7yXxH3jWgNOU7zAy5lsZrfBFwQhMzYpHxH5WLWEwTBPOe80bDQeWM4ZFETWBkwK4aipYJ1HwnYnlyApwb8NYk8EzjYQM8PLv03hguRa8fqvfXDR0iD1AYuLz0sYFLo7ZE9UtmOTTlzuRpJoWvp9YAQY6qKKnEcJlopetus";
        //String pitkakommentti = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vestibulum, purus ut cursus consequat, ipsum tortor tincidunt justo, sed pellentesque dolor massa et quam. Suspendisse facilisis pellentesque massa, at elementum elit. Etiam mollis enim erat, quis efficitur quam ornare quis. Duis quis nunc quis dolor maximus cursus. Aliquam bibendum orci sit amet erat accumsan, ut tincidunt dui fringilla. Morbi sollicitudin risus sit amet augue ornare, et tempor nibh dictum. Phasellus feugiat maximus leo nec vestibulum. Nulla aliquam viverra nisl nec volutpat. Phasellus congue lobortis magna, et vestibulum dui tincidunt ac. Donec sed arcu mattis, commodo odio a, dapibus lacus. Mauris.";
        navigateToNewsWithTitle("New Yorkin uusi WTC-rakennus");
        commentToNews(s1001);
        assertTrue(driver.getPageSource().contains("aloitus"));
        assertTrue(driver.getPageSource().contains("lopetu"));
        assertFalse(driver.getPageSource().contains("lopetus"));
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
    public void julkaisijanSivuNakyy() {
        kirjauduSisaan("testaaja", "salasana");
        driver.get("http://localhost:8080/julkaisija/2");
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
