package ua.com.krupet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by krupet on 30.06.2015.
 */
public class Parser {

    private static String searchUri = "https://www.google.com.ua/";

    public static void main(String[] args) {

        System.out.println("Good day! Please, enter your request: ");

        Scanner scanIn = new Scanner(System.in);
        String inputStringRequest = scanIn.nextLine();
        scanIn.close();

        System.out.println("You are searching for: " + inputStringRequest);

        parseRequestString(inputStringRequest);
    }

    private static void parseRequestString(String request) {

        FirefoxDriver driver = new FirefoxDriver();
        driver.get(searchUri);

        /*
            getting opened window handler to shut down opened window after work is done
         */
        String windowHandler = driver.getWindowHandle();

        driver.findElement(By.xpath("//input[@id='lst-ib']")).clear(); // TODO: change ids to xpath
        driver.findElement(By.xpath("//input[@id='lst-ib']")).sendKeys(request);
        driver.findElement(By.xpath("//button[@name='btnG']")).click();

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
        List<String> stringLinks = webElements.stream().map(newLink -> newLink.getAttribute("href"))
                                                       .collect(Collectors.toList());

        for (String link: stringLinks) {
            driver.get(link);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            System.out.println("tab title: " + driver.getTitle());
            /*
                getting handler of current window to properly close window after work is done
             */
            windowHandler = driver.getWindowHandle();
        }

        /*
            closing opened FF window after work is done
         */
        driver.switchTo().window(windowHandler);
        driver.close();
    }
}
