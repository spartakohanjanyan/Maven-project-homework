package basetest;

import business.JobsBusiness;
import driver.ConfigReader;
import driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.HomePage;
import pages.JobsPage;

public class BaseTest {

    protected WebDriver driver;

    protected HomePage homePage;
    protected JobsPage jobsPage;

    protected JobsBusiness jobsBusiness;

    @BeforeMethod
    public void setUp() {

        driver = DriverFactory.createDriver();

        driver.get(
                ConfigReader.get("base.url")
        );

        homePage = new HomePage(driver);
        jobsPage = homePage.clickJobsButton();

        jobsBusiness = new JobsBusiness(jobsPage);
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}