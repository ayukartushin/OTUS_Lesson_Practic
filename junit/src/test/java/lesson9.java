import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.print.DocFlavor;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class lesson9 {
    protected static WebDriver driver;
    protected Actions action;

    private Logger logger = LogManager.getLogger(lesson9.class);

    private static final String CONSOLE_LOG = "var test = 'I am text'; console.log(test);";
    private static final String RETURN_TEXT = "return 'text'";
    private static final String RETURN_NUMBER = "return 26+12";
    private static final String RETURN_BOOL = "return true";
    private static final String RETURN_ELEMENT = "return document.querySelector('#text');";

    @Test
    public void takeScreenshot(){
        driver.get("https://ya.ru");

        driver.findElement(By.cssSelector("#text")).clear();
        driver.findElement(By.cssSelector("#text")).sendKeys("Base64");
        String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        saveBase64(base64);

        driver.findElement(By.cssSelector("#text")).clear();
        driver.findElement(By.cssSelector("#text")).sendKeys("Bytes");
        byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        saveBytes(bytes);

        driver.findElement(By.cssSelector("#text")).clear();
        driver.findElement(By.cssSelector("#text")).sendKeys("File");
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        saveFile(file);
    }

    @Test
    public void elementScreenshot() {
        driver.get("https://ya.ru");

        driver.findElement(By.cssSelector("#text")).clear();
        driver.findElement(By.cssSelector("#text")).sendKeys("Base64");
        String base64 = driver.findElement(By.cssSelector("#text")).getScreenshotAs(OutputType.BASE64);
        saveBase64(base64);
    }

    @Test
    public void draw(){
        driver.get("http://www.htmlcanvasstudio.com/");
        WebElement canvas = driver.findElement(By.cssSelector("#imageTemp"));

        Actions beforeBuild = action
                .clickAndHold(canvas)
                .moveByOffset(100, 100)
                .moveByOffset(-50, -10)
                .release();
        beforeBuild.perform();
        saveFile(canvas.getScreenshotAs(OutputType.FILE));
    }

    @Test
    public void move(){
        driver.get("https://ng-bootstrap.github.io/#/components/popover/examples");

        WebElement popover = driver.findElement(By.cssSelector(".col-xl-9 > ng-component:nth-child(2) > ngbd-widget-demo:nth-child(10) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > ngbd-popover-config:nth-child(1) > button:nth-child(1)"));

        action.moveToElement(popover).pause(4000L).perform();

        String content = driver.findElement(By.cssSelector("#ngb-popover-19")).getText();
        logger.info(content);

    }

    @Test
    public void execute(){
        driver.get("https://ya.ru");

        Object willBeNull = ((JavascriptExecutor) driver).executeScript(CONSOLE_LOG);
        String string = (String) ((JavascriptExecutor) driver).executeScript(RETURN_TEXT);
        Long number = (Long) ((JavascriptExecutor) driver).executeScript(RETURN_NUMBER);
        Boolean bool = (Boolean) ((JavascriptExecutor) driver).executeScript(RETURN_BOOL);
        WebElement element = (WebElement) ((JavascriptExecutor) driver).executeScript(RETURN_ELEMENT);
        logger.info("Выполнение готово");
    }

    @Test
    public void download() throws IOException {
        driver.get("https://torrent-gox.ru/1932-chudo-zhenschina-1984-2020.html#download");
        WebElement el = driver.findElement(By.cssSelector("div.table__row:nth-child(2) > div:nth-child(4) > a:nth-child(1)"));
        String url = el.getAttribute("href");
        logger.info("Загружаю файл " + url);
        downloadfile(url, "torrent.torrent");
    }

    @Test
    public void proxy(){
        driver.get("http://blackhole.beeline.ru");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();

        DesiredCapabilities dc = new DesiredCapabilities();
        Proxy proxy = new Proxy();
        String proxyAdd = "127.0.0.1:8080";
        proxy.setHttpProxy(proxyAdd);
        proxy.setSslProxy(proxyAdd);
        dc.setCapability(CapabilityType.PROXY, proxy);
        driver = new ChromeDriver(dc);
        driver.get("http://blackhole.beeline.ru");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Upload(){
        String filePath = "C:\\Users\\Администратор\\Desktop\\OTUS\\Урок 1\\work1-master\\junit\\src\\test\\resources\\";
        String fileName = "ali.png";
        driver.get("https://www.pdf2go.com/ru/edit-pdf");
        WebElement upload = driver.findElement(By.id("fileUploadInput"));
        String script = "var el = document.getElementById('fileUploadInput');" +
                "el.classList.remove('hidden');";
        ((JavascriptExecutor) driver).executeScript(script);
        upload.sendKeys(filePath + fileName);
        logger.info("Загрузили картинку");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void downloadfile(String urlPath, String fileNameIn) throws IOException {
        String fileName = "target/" + fileNameIn;

        InputStream in = new URL(urlPath).openStream();
        Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
    }

    private void saveBase64(String data) {
        File file = OutputType.FILE.convertFromBase64Png(data);
        saveFile(file);
    }

    private void saveBytes(byte[] data) {
        File file = OutputType.FILE.convertFromPngBytes(data);
        saveFile(file);
    }

    private void saveFile(File data) {
        String fileName = "target/" + System.currentTimeMillis() + ".png";
        try {
            FileUtils.copyFile(data, new File(fileName));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(4L, TimeUnit.SECONDS);
        action = new Actions(driver);
        logger.info("Драйвер поднят");
    }
    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
