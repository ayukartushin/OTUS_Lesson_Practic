import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class lesson8 {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(lesson8.class);

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер успешно закрыт");
        }
    }


    @Test
    public void Alert(){
        driver.get("http://subdomain.localhost/alert.html");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился alert");
        Alert alert = driver.switchTo().alert();
        String actual = alert.getText();
        logger.info(alert.getText());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alert.accept();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("Test Alert", actual);
    }

    @Test
    public void Confirm_Accept(){
        driver.get("http://subdomain.localhost/confirm.html");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился confirm");
        Alert alert = driver.switchTo().alert();
        logger.info(alert.getText());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alert.accept();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился alert");
        alert = driver.switchTo().alert();
        String actual = alert.getText();
        logger.info(actual);
        alert.accept();
        Assert.assertEquals("true", actual);
    }

    @Test
    public void Confirm_Dismiss(){
        driver.get("http://subdomain.localhost/confirm.html");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился confirm");
        Alert alert = driver.switchTo().alert();
        logger.info(alert.getText());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alert.dismiss();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился alert");
        alert = driver.switchTo().alert();
        String actual = alert.getText();
        logger.info(actual);
        alert.accept();
        Assert.assertEquals("false", actual);
    }

    @Test
    public void Prompt(){
        driver.get("http://subdomain.localhost/prompt.html");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился prompt");
        Alert alert = driver.switchTo().alert();
        logger.info(alert.getText());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alert.sendKeys("Anton");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alert.accept();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.alertIsPresent());
        logger.info("Появился alert");
        alert = driver.switchTo().alert();
        String actual = alert.getText();
        logger.info(actual);
        alert.accept();
        Assert.assertEquals("Hello Anton", actual);
    }

    //--------------------------------------------------------

    @Test
    public void Upload(){
        String filePath = "C:\\Users\\Администратор\\Desktop\\OTUS\\Урок 1\\work1-master\\junit\\src\\test\\resources\\";
        String fileName = "ali.png";
        driver.get("http://subdomain.localhost/deletefiles.php");
        logger.info("Удалили все картинки");
        driver.get("http://subdomain.localhost/upload.php");
        driver
                .findElement(By.id("inputfile"))
                .sendKeys(filePath + fileName);
        driver.findElement(By.id("submit")).click();
        logger.info("Загрузили картинку");
        driver.get("http://subdomain.localhost/files/" + fileName);
        List<WebElement> imgs = driver.findElements(By.tagName("img"));
        Assert.assertEquals(1, imgs.size());
    }

    //--------------------------------------------------------

    @Test
    public void Iframe(){
        driver.get("http://subdomain.localhost/iframe.html");
        WebElement frame = driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(frame);
        logger.info("Переходим в iframe");
        String actual = driver.findElement(By.tagName("H1")).getText();
        Assert.assertEquals("Wee", actual);
    }

    @Test
    public void Auth(){
        driver.get("http://admin:123456@subdomain.localhost/auth/index.html");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
