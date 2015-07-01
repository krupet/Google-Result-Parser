package ua.com.krupet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        Thread.sleep(1000L); //TODO: change on wait implicitly

        System.out.println(driver.getTitle());
        System.out.println(driver.getCurrentUrl());


        /*
            getting list of search results on a first result page
         */

        List<WebElement> webElements = driver.findElements(By.xpath("//ol[@id='rso']/div[@class='srg']/li[@class='g']" +
                                                                    "/div[@class='rc']/h3[@class='r']/a"));
        System.out.println("number of elements: " + webElements.size());
        for (WebElement webElement: webElements) {
//            System.out.println(webElement.getAttribute("href"));
            System.out.println("a text:\t" + webElement.getText());
        }

        /*
            trying to "click" on first link
         */

        WebElement link = webElements.get(0);
        link.click();
        Thread.sleep(1000);
        System.out.println("\ntab title: " + driver.getTitle());
        System.out.println("tab url: " + driver.getCurrentUrl());
        baseWindowHandler = driver.getWindowHandle();

        /*
            closing opened FF window after work is done
         */
        driver.switchTo().window(baseWindowHandler);
        driver.close();
    }
}
