package ua.com.krupet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by krupet on 30.06.2015.
 */
public class Parser {

    private static FirefoxDriver driver = new FirefoxDriver();
    private static String searchUri = "https://www.google.com.ua/";
    private static String searchQuery;

    public static void main(String[] args) throws InterruptedException {

        /*
            getting search query as first arg
            (means it is passed to CLI surrounded by double quotes to be passes as one solid string)
         */
        if (args.length > 0) searchQuery = args[0];
        else searchQuery = "chuck norris"; // TODO: add some proper empty args handling

        System.out.println("you are searching for: " + searchQuery);

        driver.get(searchUri);

        /*
            getting window handler to shut down opened window after work is done
         */
        String baseWindowHandler = driver.getWindowHandle();

        /*
            searching by id because it is much reliable
         */
        driver.findElement(By.id("lst-ib")).clear(); // TODO: change ids to xpath
        driver.findElement(By.id("lst-ib")).sendKeys(searchQuery);
        driver.findElement(By.name("btnG")).click();

        /*
            waiting for page rendering
         */
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        /*
            getting list of search results on a first result page
         */
        List<WebElement> webElements = driver.findElements(By.xpath("//ol[@id='rso']/div[@class='srg']/li[@class='g']" +
                                                                    "/div[@class='rc']/h3[@class='r']/a"));

        /*
            because WebDriver using cache for webElements collection I need to copy
            all href attributes in other collection and work with it

            it is all because when I switch to another page I loosing collection of WebElements
            because of page reloading
         */
        List<String> stringLinks = webElements.stream().map(newLink -> newLink.getAttribute("href")).collect(Collectors.toList());

        for (String link: stringLinks) {
            driver.get(link);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            System.out.println("tab title: " + driver.getTitle());
            /*
                getting handler of current window to properly close window after work is done
             */
            baseWindowHandler = driver.getWindowHandle();
        }

        /*
            closing opened FF window after work is done
         */
        driver.switchTo().window(baseWindowHandler);
        driver.close();
    }
}
