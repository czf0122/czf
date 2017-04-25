package TinyShop;

import Interfaces.TinyShop.TinyShop_yw;
import Utils.ExcelIterator;
import Utils.ReportUtils;
import Utils.RequestUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by MrChen on 2017/4/24.
 */
public class TinyShopTest {
    ReportUtils report = new ReportUtils();
    Assertion assertion = new Assertion();
    //打开首页
    @DataProvider(name = "shouye")
    public Iterator<Object[]> datashouye() throws IOException {
        return new ExcelIterator("E:\\CZF_Project\\TestData\\TinyShop_data\\TinyShopTest", "shouye");
    }
    @Test(dataProvider = "shouye",priority = 0)
    public void OpenShouYe(Map<String,String> map) {

        TinyShop_yw.openShouYe(map);
    }
    //登录
    @DataProvider(name = "login")
    public Iterator<Object[]> datalogin() throws IOException {
        return new ExcelIterator("E:\\CZF_Project\\TestData\\TinyShop_data\\TinyShopTest", "Login");
    }
    @Test(dataProvider = "login",priority = 1)
    public String Login(Map<String,String > map) throws IOException {
        String response = TinyShop_yw.Login(map);
        report.log("登录成功");
//        //业务逻辑判断
//        if(response.contains(map.get("expected result"))){
//            report.log("登录成功");
//            return response;
//        }else{
//            report.log("登录失败，请重新登录");
//        }
        return response;
    }
}
