package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.Locale;

public class DriverFactory {

    public static WebDriver createDriver() {

        String browser = ConfigReader.get("browser").toLowerCase(Locale.ROOT);

        WebDriver driver;

        switch (browser) {

            case "chrome":
                driver = new ChromeDriver();
                break;

            case "firefox":
                driver = new FirefoxDriver();
                break;

            case "edge":
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown browser: " + browser
                );
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(30)
        );

        return driver;
    }
}