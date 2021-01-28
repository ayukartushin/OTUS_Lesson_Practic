import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;


public class SampleTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SampleTest.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    @Before
    public void setUp() {
        //WebDriverManager.chromedriver().setup();
        try {
            DesiredCapabilities capability = DesiredCapabilities.chrome();
            driver = //new ChromeDriver();
                    new RemoteWebDriver(new URL("http://localhost:4444/wd/hub/"), capability);
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        logger.info("Драйвер поднят");
    }
    @Test
    public void openPage() {
        driver.get(cfg.url());
        logger.info("Открыта страница отус");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}










