package fi.uutisjuttu.uutisjuttu.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class IndexTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        this.driver = new HtmlUnitDriver();
    }

    @Test
    public void test() {
        driver.get("http://localhost:8080");
        assertFalse(driver.getPageSource().contains("surkea"));
//        assertTrue(driver.getPageSource().contains("eeppinen"));
    }

}
