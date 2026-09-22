package selenium;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Task2 {

    public static void main(String[] args) {
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

        try {
            driver.get("https://www.saucedemo.com/");

            
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

            
            String sortOption = "za"; 
            applySort(driver, wait, sortOption);

            List<Map<String, String>> topThree = extractTopN(driver, 3);

            System.out.println("Sort option: " + sortOption);
            for (int i = 0; i < topThree.size(); i++) {
                Map<String, String> item = topThree.get(i);
                System.out.println((i + 1) + ". " + item.get("title") + " - " + item.get("price"));
            }

        } finally {
            driver.quit();
        }
    }

   
    private static void applySort(WebDriver driver, WebDriverWait wait, String sortValue) {
        WebElement dropdownEl = wait.until(
                ExpectedConditions.elementToBeClickable(By.className("product_sort_container")));
        new Select(dropdownEl).selectByValue(sortValue);

        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item")));
    }

    
    private static List<Map<String, String>> extractTopN(WebDriver driver, int n) {
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        int count = Math.min(n, items.size());

        List<Map<String, String>> results = new java.util.ArrayList<>();
        for (int i = 0; i < count; i++) {
            WebElement item = items.get(i);
            String title = item.findElement(By.className("inventory_item_name")).getText();
            String price = item.findElement(By.className("inventory_item_price")).getText();

            Map<String, String> row = new HashMap<>();
            row.put("title", title);
            row.put("price", price);
            results.add(row);
        }
        return results;
    }
} 
