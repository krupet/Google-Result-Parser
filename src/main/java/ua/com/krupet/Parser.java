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

    public static void main(String[] args) {

        driver.get(searchUri);
        driver.findElement(By.id("lst-ib")).clear();
        driver.findElement(By.id("lst-ib")).sendKeys("chuck norris");
        driver.findElement(By.name("btnG")).click();

        /*
            here is some bug:
            INFO: Command failed to close cleanly.
             Destroying forcefully (v2). org.openqa.selenium.os.UnixProcess$SeleniumWatchDog@78b729e6
         */
        driver.quit();
    }
}
