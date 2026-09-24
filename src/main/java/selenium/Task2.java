package selenium;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Task2 {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

            
            String sortOption = "za";
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.className("product_sort_container")));
            new Select(dropdown).selectByValue(sortOption);

            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item")));

           
            List<WebElement> items = driver.findElements(By.className("inventory_item"));

            
            int count = Math.min(3, items.size());

            System.out.println("Sort option: " + sortOption);
            for (int i = 0; i < count; i++) {
                String title = items.get(i).findElement(By.className("inventory_item_name")).getText();
                String price = items.get(i).findElement(By.className("inventory_item_price")).getText();
                System.out.println((i + 1) + ". " + title + " - " + price);
            }

        } finally {
            driver.quit();
        }
    }
}
