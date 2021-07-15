import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.ResultsPage;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TradeMeTest {

    private HomePage homePage;
    private WebDriver driver;
    private ResultsPage resultsPage;


    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setupBrowser() {
        driver = new ChromeDriver();
        driver.get("https://www.tmsandbox.co.nz/");
        homePage = new HomePage(driver);

    }

    @AfterEach
    public void teardownBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void checkTitle() {
        assertEquals("TRADEME SANDBOX - Buy online and sell with NZ's #1 auction & classifieds site | Trade Me SANDBOX", driver.getTitle());
    }

    @Test
    public void testGoldSendKeys() throws Exception {
        homePage.searchFor("gold");
        Thread.sleep(5000);
    }

    @Test
    public void testGoldSubmit() throws Exception {
        WebElement queryBox = driver.findElement(By.cssSelector("#searchString"));
        queryBox.sendKeys("gold");
        queryBox.submit();
        Thread.sleep(5000);
    }

    @Test
    public void testGoldClick() throws Exception {
        WebElement queryBox = driver.findElement(By.cssSelector("#searchString"));
        queryBox.sendKeys("gold");
        WebElement submitButton = driver.findElement(By.cssSelector("#generalSearch > div.field.field-right > button"));
        submitButton.click();
        Thread.sleep(5000);
    }

    @Test
    public void printOutNumberOfListings() throws InterruptedException {
        resultsPage = homePage.searchFor("gold");
        int listingCount = resultsPage.getTotalCount();
        assertEquals(listingCount, "27", "Checking for the number of listings");
    }


    @Test
    public void printOutPriceCurrentItem() throws InterruptedException {
        homePage.searchFor("gold");
        Thread.sleep(5000);

        String listingPriceTop = driver.findElement(By.cssSelector("#SuperGridGallery_BucketList_ClassifiedPrice_listingClassifiedPriceAmountPoa"))
                .getText();
        System.out.println(listingPriceTop);
        assertEquals(listingPriceTop, "Price by negotiation", "Checking for the price of the top listing");
    }

    @Test
    public void clickOnTheListView() throws InterruptedException {
        homePage.searchFor("gold");
        Thread.sleep(5000);

        WebElement listView = driver.findElement(By.cssSelector("#ListingViewBar_listViewTab_icon_a"));
        listView.click();
        Thread.sleep(5000);
    }

    @Test
    public void ListTopTen() throws InterruptedException {
        homePage.searchFor("gold");
//        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#Footer_FooterLinks_announcementsLink")));

        WebElement listView = driver.findElement(By.cssSelector("#ListingViewBar_listViewTab_icon_a"));
        listView.click();
//        Thread.sleep(5000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#Footer_FooterLinks_announcementsLink")));

        List<WebElement> topTen = driver.findElements(By.cssSelector(".title"));

        for (int i = 0; i < 10; i++) {
            System.out.println(topTen.get(i).getText());
        }

    }

    @Test
    public void testGoldSendKeysExplicitWait() throws Exception {
        homePage.searchFor("gold");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#Footer_FooterLinks_announcementsLink")));

    }

    @Test
    public void testGoldSendKeysSortLowest() throws Exception {
        homePage.searchFor("gold");


        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#Footer_FooterLinks_announcementsLink")));

        Select select = new Select(driver.findElement(By.name("sort_order")));
        select.selectByVisibleText("Lowest Buy Now");


        WebElement listView = driver.findElement(By.cssSelector("#ListingViewBar_listViewTab_icon_a"));
        listView.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#Footer_FooterLinks_announcementsLink")));

        List<WebElement> topThree = driver.findElements(By.cssSelector("#SuperListView_BucketList_BidInfo_listingBidPrice"));
        topThree.addAll(driver.findElements(By.cssSelector("#SuperListView_BucketList_ClassifiedPrice_listingClassifiedPriceAmount")));

        for (int i = 0; i < 3; i++) {
            System.out.println(topThree.get(i).getText());
        }





    }

}


