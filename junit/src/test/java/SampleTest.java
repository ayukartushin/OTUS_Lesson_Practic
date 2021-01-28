import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.Key;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SampleTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SampleTest.class);

    @Test
    public void Log(){
        logger.info("Я информационный лог");
        WebElement el = driver.findElement(By.cssSelector(""));
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void G_A(){
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
    }

    @Test
    public void G_B() {
        driver.manage().window().setSize(new Dimension(800, 600));
        driver.get("https://otus.ru");
        logger.info(driver.manage().window().getPosition());
    }

    @Test
    public void G_C() {
        driver.manage().window().setSize(new Dimension(800, 600));
        driver.get("https://otus.ru");
        logger.info(driver.manage().window().getPosition());
        Point point = driver.manage().window().getPosition();
        point.x = point.x + 100;
        driver.manage().window().setPosition(point);
        point.y = point.y + 100;
        driver.manage().window().setPosition(point);
        point.x = point.x - 100;
        driver.manage().window().setPosition(point);
        point.y = point.y - 100;
        driver.manage().window().setPosition(point);
    }

    @Test
    public void openPage() {
        driver.get("https://otus.ru/");
        logger.info("Открыта страница отус");
    }

    @Test
    public void Cookie(){
        driver.get("https://otus.ru/");
        driver.manage().addCookie(new Cookie("Otus1","Value1"));
        driver.manage().addCookie(new Cookie("Otus2", "Vaue2"));
        Cookie cookie = new Cookie("Otus3", "Value3");
        driver.manage().addCookie(cookie);
        driver.manage().addCookie(new Cookie("Otus4","Value4"));

        logger.info(driver.manage().getCookies());
        logger.info(driver.manage().getCookieNamed("Otuswrw1"));
        driver.manage().deleteCookieNamed("Otus2");
        driver.manage().deleteCookie(cookie);
        driver.manage().deleteAllCookies();
        logger.info(driver.manage().getCookies().size());
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер успешно закрыт");
        }
    }


    @Test
    public void mi_chat1(){
        driver.get("https://ru-mi.com/");
        new WebDriverWait(driver, 120)
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#jivo_close_button")));
    }

    @Test
    public void mi_chat2(){
        driver.get("https://ru-mi.com/");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);
        logger.info(driver.findElement(By.cssSelector("#jivo_close_button")).getAttribute("class"));
    }

    @Test
    //Проверка количества результатов яндекс
    public void yandex_results_size(){
        driver.get("https://yandex.ru");
        driver.manage().window().maximize();

        driver
                .findElement(By.id("text"))
                .sendKeys("Otus" + Keys.DOWN + Keys.ENTER);

        List<WebElement> results = driver.findElements(By.cssSelector("#search-result li"));
        Assert.assertEquals(21, results.size());
    }

    @Test
    public void Lesson6(){
        //1. Открыть otus.ru
        driver.get("https://otus.ru");
        driver.manage().window().maximize();
        logger.info("Открыта страница отус");
        //2. Авторизоваться на сайте
        logger.info("Начата авторизация на сайте отус");
        auth();
        logger.info("Окончена авторизация на сайте отус");
        //3. Войти в личный кабинет
        logger.info("Начат вход в личный кабинет");
        enterLK();
        logger.info("Окончен вход в личный кабинет");
        //4. В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        logger.info("Очистка полей");
        driver.findElement(By.id("id_fname_latin")).clear();
        driver.findElement(By.id("id_lname")).clear();
        driver.findElement(By.id("id_lname_latin")).clear();
        driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).clear();

        logger.info("Ввод данных анкеты");
        driver.findElement(By.id("id_fname_latin")).sendKeys("Anton");
        driver.findElement(By.id("id_lname")).sendKeys("Картушин");
        driver.findElement(By.id("id_lname_latin")).sendKeys("Kartushin");
        driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).sendKeys("08.02.1992");
        //Страна
        if(!driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).getText().contains("Россия"))
        {
            driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).click();
            driver.findElement(By.xpath("//*[contains(text(), 'Россия')]")).click();
        }
        //Город
        if(!driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).getText().contains("Москва"))
        {
            driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).click();
            driver.findElement(By.xpath("//*[contains(text(), 'Москва')]")).click();
        }
        //уровень англ.
        if(!driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).getText().contains("Начальный уровень (Beginner)"))
        {
            driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).click();
            driver.findElement(By.xpath("//*[contains(text(), 'Начальный уровень (Beginner)')]")).click();
        }
        //5. Нажать сохранить
        logger.info("Сохранение данных");
        driver.findElement(By.xpath("//*[contains(text(), 'Сохранить и продолжить')]")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlToBe("https://otus.ru/lk/biography/skills/"));
        //6. Открыть https://otus.ru в “чистом браузере”
        logger.info("Закрытие браузера");
        driver.quit();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        logger.info("Драйвер поднят");
        driver.get("https://otus.ru");
        //7. Авторизоваться на сайте
        logger.info("Начата авторизация на сайте отус");
        auth();
        logger.info("Окончена авторизация на сайте отус");
        //8. Войти в личный кабинет
        logger.info("Начат вход в личный кабинет");
        enterLK();
        logger.info("Окончен вход в личный кабинет");
        //9. Проверить, что в разделе о себе отображаются указанные ранее данные
        logger.info("Проведение проверок");
        Assert.assertEquals("Anton", driver.findElement(By.id("id_fname_latin")).getAttribute("value"));
        Assert.assertEquals("Картушин", driver.findElement(By.id("id_lname")).getAttribute("value"));
        Assert.assertEquals("Kartushin", driver.findElement(By.id("id_lname_latin")).getAttribute("value"));
        Assert.assertEquals("08.02.1992", driver.findElement(By.cssSelector(".input-icon > input:nth-child(1)")).getAttribute("value"));
        Assert.assertEquals("Россия", driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).getText());
        Assert.assertEquals("Москва", driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).getText());
        Assert.assertEquals("Начальный уровень (Beginner)", driver.findElement(By.cssSelector("div.container__col_12:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).getText());
    }

    @Test
    public void Lerya(){
        driver.get("https://leroymerlin.ru/");
        driver.manage().window().maximize();
        driver.findElement(By.name("q")).click();
        driver.findElement(By.name("q")).sendKeys("Щебень" + Keys.ENTER);
    }

    private void auth()
    {
        String login = "fawodo3192@chikd73.com";
        String password = "Qazwsx123";
        String locator = "button.header2__auth";
        driver.findElement(By.cssSelector(locator)).click();
        driver.findElement(By.cssSelector("div.new-input-line_slim:nth-child(3) > input:nth-child(1)")).sendKeys(login);
        driver.findElement(By.cssSelector(".js-psw-input")).sendKeys(password);
        driver.findElement(By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)")).submit();
        logger.info("Авторизация прошла успешно");
    }

    private void  enterLK()
    {
        String locator = ".ic-blog-default-avatar";
        WebElement icon = driver.findElement(By.cssSelector(locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(icon).build().perform();
        driver.get("https://otus.ru/lk/biography/personal/");
        logger.info("Перешел в личный кабинет");
    }

}










