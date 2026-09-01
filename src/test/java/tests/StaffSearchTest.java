package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.JobResultsPage;

public class StaffSearchTest {

    private WebDriver driver;
    private HomePage homePage;
    private JobResultsPage jobResultsPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        jobResultsPage = new JobResultsPage(driver);
    }

    @Test
    public void clearFiltersTest() {
        homePage.open();

        homePage.switchToStandardSearch();

        homePage.selectRandomCategory();

        homePage.clickSearch();

        Assert.assertTrue(
                jobResultsPage.isClearFiltersButtonDisplayed(),
                "Clear filters button is not displayed"
        );

        jobResultsPage.clickClearFilters();

        Assert.assertTrue(
                jobResultsPage.isClearFiltersButtonInvisible(),
                "Clear filters button did not disappear"
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}