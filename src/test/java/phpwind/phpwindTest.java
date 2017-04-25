package phpwind;import Interfaces.phpwind.phpwind_yw;
import Utils.ExcelIterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/*
  Created by SuperMan on 2017/4/7.

*/
public class phpwindTest {

    //断言
    Assertion assertion = new Assertion();

    //参数传递
  //打开首页-获取csrf_token
    @DataProvider(name = "crsftoken")
    public Iterator<Object[]> datashouye() throws IOException {
        return new ExcelIterator("E:\\CZF_Project\\TestData\\phpwind\\phpwindTest", "shouye");
    }
    //注册-register
    @DataProvider(name = "register")
    public Iterator<Object[]> dataregister() throws IOException {
        return new ExcelIterator("E:\\CZF_Project\\TestData\\phpwind\\phpwindTest", "register");
    }

    //登陆-Login
    @DataProvider(name = "Login")
    public Iterator<Object[]> dataLogin() throws IOException {
        return new ExcelIterator("E:\\CZF_Project\\TestData\\phpwind\\phpwindTest", "Login");
    }

    //发帖-posting
    @DataProvider(name = "posting")
    public Iterator<Object[]> dataposting() throws IOException {
        return new ExcelIterator("E:\\CZF_Project\\TestData\\phpwind\\phpwindTest", "posting");
    }
    //退出
    @DataProvider(name = "quit")
    public Iterator<Object[]> dataquit() throws IOException {
        return new ExcelIterator("E:\\CZF_Project\\TestData\\phpwind\\phpwindTest","quit");
    }

    //测试类

    //打开首页
    @Test(dataProvider = "crsftoken",priority = 0)
    public void shouye(Map<String,String> map) {
        phpwind_yw.openShouYe(map);
    }
    //注册
    @Test(dataProvider = "register",priority = 1)
    public void register(Map<String,String> map) throws IOException {
        String response = phpwind_yw.register(map);
        System.out.println("响应信息为：\n"+response);
        //断言
        Document document = null;
        document = Jsoup.parse(response);
        //通过第一个title来进行断言
        Element element = document.getElementsByTag("title").get(0);
        //获取title文本信息
        String title = element.text();
        if(title == map.get("title")){
            Element element1 = document.getElementsByTag("h1").get(0);
            String content = element1.text();
            if(content.contains(map.get("参数username")+"，恭喜您注册成为phpwind 9.0会员！")){
                assertion.assertEquals(document,map.get("预期结果_expectation"));
                System.out.println("注册成功");
            }
        }else{
            Element element2 = document.getElementsByTag("h1").get(0);
            String content = element2.text();
            assertion.assertEquals(content, map.get("预期结果_expectation"));


            System.out.println("注册失败");
        }
    }
//    //退出
//    @Test(dataProvider = "quit",priority = 2)
//    public void quit(Map<String,String> map){
//        phpwind_yw.quit(map.get("请求地址"));
//    }
    //登录
    @Test(dataProvider = "Login", priority = 3)
    public void Login(Map<String,String> map) throws IOException {
        System.out.println(map.get("请求地址_url"));
        phpwind_yw.Login(map);
    }
//    //发帖
//    @Test(dataProvider = "posting",priority = 4)
//    public void posting(Map<String,String> map) throws IOException {
//        System.out.println(map);
//        phpwind_yw.posting(map);
//    }
}
