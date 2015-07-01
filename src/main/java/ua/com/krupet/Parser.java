package ua.com.krupet;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by krupet on 30.06.2015.
 */
public class Parser {

    private static FirefoxDriver driver = new FirefoxDriver();
    private static String searchUri = "https://www.google.com.ua/";
    private static String searchQuery;

    public static void main(String[] args) {

        /*
            getting search query as first arg
            (means it is passed to CLI surrounded by double quotes to be passes as one solid string)
         */
        searchQuery = args[0];
        System.out.println("you are searching for: " + searchQuery);

        driver.get(searchUri);

        /*
            getting window handler to shut down opened window after work is done
         */
        String baseWindowHandler = driver.getWindowHandle();

        /*
            searching by id because it is much reliable
         */
        driver.findElement(By.id("lst-ib")).clear();
        driver.findElement(By.id("lst-ib")).sendKeys(searchQuery);
        driver.findElement(By.name("btnG")).click();

        System.out.println(driver.getTitle());
        System.out.println(driver.getCurrentUrl());

        /*
            closing opened FF window after work is done
         */
        driver.switchTo().window(baseWindowHandler);
        driver.close();
    }
}
