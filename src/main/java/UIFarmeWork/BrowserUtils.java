package UIFarmeWork;

import Utils.ReportUtils;
import org.openqa.selenium.WebDriver;

/**
 * Created by MrChen on 2017/4/23.
 */
/*
    浏览器操作行为
 */
public class BrowserUtils {
    WebDriver driver;
//    ReportUtils report = new ReportUtils();
    //获取driver
    public WebDriver getdriver(){
        return driver;
    }
    //打开网页
    public void openWeb(String url){
        if(url.equals("") || url == null){
            System.out.println("url为空");
        }else{
            driver.get(url);
            System.out.println("打开"+url+"页面");
        }
    }
    //关闭网页
    public void closeWeb(){
        driver.close();
    }
    //暂停操作
    public void pause(long milliseconds){
        if(milliseconds <= 0){
            return;
        }try{
            Thread.sleep(milliseconds);
            System.out.println("暂停"+milliseconds+"结束");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    //退出
    public void quit(){
        driver.quit();
    }
    //获取当前页的URL
    public String getCurrentUrl(){
        String CurrentUrl = driver.getCurrentUrl();
        System.out.println("当前页面Url为："+CurrentUrl);
        return CurrentUrl;
    }
    //刷新
    public void refresh(){
        driver.navigate().refresh();
    }
    //返回上一页
    public void back(){
        driver.navigate().back();
    }
    //前进
    public void forword(){
        driver.navigate().forward();
    }
    //切换窗口
    public WebDriver switchTo_window(String windowHandle){
        WebDriver driver1 = driver.switchTo().window(windowHandle);
        return driver1;
    }
    //根据页面名称orID切换窗口
    public WebDriver switch_Frame(String frame){
        WebDriver driver1 = driver.switchTo().frame(frame);
        System.out.println("切换到"+frame+"页面");
        return driver1;
    }
    //根据索引位置切换窗口
    public WebDriver switch_Frame(int index){
        WebDriver driver1 = driver.switchTo().frame(index);
        System.out.println("切换到"+index+"页面");
        return driver1;
    }
    //根据元素切换窗口
    public WebDriver switch_Frame(WebDriver element){
        return null;
    }
    //截图
    public void screenShot(String aa){}
    public void screenShot(){}
    public void sshot(){}
}
