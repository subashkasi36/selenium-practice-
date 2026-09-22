package selenium;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Task1_AddToCart {
    public static void main(String[] args) throws Exception {
        WebDriverManager.chromedriver().setup();

       
        ChromeOptions op = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        op.setExperimentalOption("prefs", prefs);
        op.addArguments("--disable-save-password-bubble");

        WebDriver driver = new ChromeDriver(op);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(2000);

        new Select(driver.findElement(By.className("product_sort_container"))).selectByValue("lohi");
        Thread.sleep(2000);

        String expectedName = driver.findElements(By.className("inventory_item_name")).get(0).getText();
        String expectedPrice = driver.findElements(By.className("inventory_item_price")).get(0).getText();
        System.out.println("First product: " + expectedName + " " + expectedPrice);

        driver.findElements(By.xpath("//div[@class='inventory_item']//button")).get(0).click();
        Thread.sleep(2000);

        driver.findElement(By.className("shopping_cart_link")).click();

        
        String cartName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name"))).getText();
        String cartPrice = driver.findElement(By.className("inventory_item_price")).getText();
        String cartQty = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cart_quantity']"))).getText();

        if (cartName.equals(expectedName)) System.out.println("Name OK");
        else System.out.println("Name WRONG");

        if (cartPrice.equals(expectedPrice)) System.out.println("Price OK");
        else System.out.println("Price WRONG");

        if (cartQty.equals("1")) System.out.println("Quantity OK");
        else System.out.println("Quantity WRONG");

        driver.quit();
    }
}
