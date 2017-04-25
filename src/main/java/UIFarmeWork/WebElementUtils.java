package UIFarmeWork;

import Utils.ReportUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by MrChen on 2017/4/23.
 */
public class WebElementUtils extends Browser {
    ReportUtils report = new ReportUtils();
    WebElement element = null;
    public WebElementUtils(int driverType) {
        super(driverType);
    }

        //id
        public WebElement byId(String strId){
            if(strId.equals("") || strId == null){
                report.log("元素没有获取到");
            }else {
                element = driver.findElement(By.id(strId));
            }
            return element;
        }

        //name
        public WebElement byName(String strName){
            if(strName == null || strName.equals(""))
            {
                report.log("无法找到元素");
            }else{
             element= driver.findElement(By.name(strName));
            }
            return element;
        }

        //ClassName
        public WebElement byClassName(String strClass){
            if(strClass == null || strClass.equals("")){
                report.log("无法找到元素");
            }else {
                element = driver.findElement(By.className(strClass));
            }
            return element;
        }
        //tagName
        public WebElement byTagName(String tagName){
            if(tagName == null || tagName.equals("")){
                report.log("没有找到元素");
            }else{
             element= driver.findElement(By.tagName(tagName));
            }
            return element;
        }
        //linkText
        public WebElement byLink(String linkText){
            if(linkText == null || linkText.equals("")){
                report.log("linkText为空");
            }else{
             element = driver.findElement(By.linkText(linkText));
            }
            return element;
        }

        //xpath
        public WebElement byXpath(String xpath) {
            if (xpath == null || xpath.equals("")) {
                report.log("xpath为空");
            } else {
                 element = driver.findElement(By.xpath(xpath));
            }
            return element;
        }
    }
