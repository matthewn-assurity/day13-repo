package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ResultsPage searchFor(String searchString) {
        WebElement queryBox = driver.findElement(By.cssSelector("#searchString"));
        queryBox.sendKeys(searchString);
        queryBox.sendKeys(Keys.RETURN);
        return new ResultsPage(driver);
    }


}
