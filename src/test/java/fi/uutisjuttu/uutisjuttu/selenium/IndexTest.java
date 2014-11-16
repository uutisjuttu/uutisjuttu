package fi.uutisjuttu.uutisjuttu.selenium;

import fi.uutisjuttu.uutisjuttu.Application;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.SpringApplication;

public class IndexTest {

    private WebDriver driver;

    @BeforeClass
    public static void setUpClass() {
        SpringApplication.run(Application.class);
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

}
