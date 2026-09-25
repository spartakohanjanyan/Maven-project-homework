import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.JobDetailsPage;
import pages.JobsPage;

public class JobTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        String url = "https://staff.am/jobs";
        driver.get(url);
        ((JavascriptExecutor) driver).executeScript(
                "var elem = document.querySelector('.Toastify') || document.querySelector('[class*=\"cookie\"]');" +
                        "if(elem) { elem.remove(); }"
        );
    }
    @Test
    public void testJobsDetails() throws InterruptedException {
        JobsPage jobsPage = new JobsPage(driver);
        WebElement randomJobCard = jobsPage.getRandomJobCard();
        String expectedTitle = jobsPage.getJobTitle(randomJobCard);
        String expectedCompany = jobsPage.getCompanyName(randomJobCard);
        String expectedLocation = jobsPage.getLocation(randomJobCard);
        String expectedDate = jobsPage.getDate(randomJobCard);
        jobsPage.clickToJobDetails(randomJobCard);
        JobDetailsPage jobDetailsPage = new JobDetailsPage(driver);

        Assert.assertEquals(jobDetailsPage.getJobTitle(), expectedTitle);
        Assert.assertEquals(jobDetailsPage.getCompanyName(), expectedCompany);
        Assert.assertEquals(jobDetailsPage.getLocation(), expectedLocation);
        Assert.assertEquals(jobDetailsPage.getDate(), expectedDate);
    }

    @AfterMethod
    public void closeWebPage(){
        driver.quit();
        driver = null;
    }
}