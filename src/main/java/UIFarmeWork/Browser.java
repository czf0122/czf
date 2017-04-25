package UIFarmeWork;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by MrChen on 2017/4/23.
 */
public class Browser {
    WebDriver driver;

    //封装启动浏览器方法
    public  void setupFirfox(){
//        System.setProperty("webdriver.gecko.driver","E:\\CZF_Project\\Driver.geckodriver.exe");
        driver = new FirefoxDriver();
    }
    public  void setupChrome(){
//        System.setProperty("webdriver.chrome.driver","C:\\Users\\MrChen\\AppData\\Local\\Google\\Chrome\\Application\\Driver.chromedriver.exe");
        driver = new ChromeDriver();
    }
    //liu'浏览器最大化
    public void maxBrowser(){
        driver.manage().window().maximize();
    }
    //选择浏览器
    public void setDriver(int driverType){
        switch (driverType){
            case 1:
                setupFirfox();
                break;
            case 2:
                setupChrome();
                break;
        }
    }
    //创建构造方法
     public Browser(int driverType){
        setDriver(driverType);
        maxBrowser();
    }

    public static void main(String[] args) {
        //创建一个对象
        Browser browser = new Browser(2);

    }
}
