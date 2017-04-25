package UIFarmeWork;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by MrChen on 2017/4/24.
 */
public class BrowserTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver;
        driver = new FirefoxDriver();
        driver.get("https://www.baidu.com");
        Thread.sleep(2000);
        WebElement Login=driver.findElement(By.linkText("登录"));
        Login.click();
        Thread.sleep(3000);
        driver.quit();
    }
}
