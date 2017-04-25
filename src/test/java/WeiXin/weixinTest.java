package WeiXin;

import InterfaceFarmeWoke.DataUtils;
import Interfaces.WeiXin.weixin_yw;
import Utils.ExcelIterator;
import Utils.ReportUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by MrChen on 2017/4/15.
 */
public class weixinTest {
    ReportUtils report = new ReportUtils();
    //声明断言
    Assertion assertion = new Assertion();
    @DataProvider(name = "gettoken")
    public Iterator<Object[]> DataToken() throws IOException {
        return new ExcelIterator("E:\\CZF_Project\\TestData\\WeiXin\\WeiXinTest","getToken");
    }
    @Test(dataProvider = "gettoken",priority = 0)
    public void Login(Map<String,String> map) throws IOException {
       String response = weixin_yw.getToken(map);
//        report.log(response);
       if(response.length()!= 0){
           String expires_in = DataUtils.parseJson(response,"expires_in");
           if(expires_in.contains(map.get("预期结果_expires_in"))){
               assertion.assertEquals(expires_in,map.get("预期结果_expires_in"));
               report.log("成功获取access_token");
           }else{
               report.log("获取access_token失败");
           }
       }else {
           report.log("response为空，不做断言");
           return;
       }
    }
    @DataProvider(name = "getOpenid")
    public Iterator<Object[]>  DataOpenid() throws IOException {
        return new ExcelIterator("E:\\CZF_Project\\TestData\\WeiXin\\WeiXinTest","getOpenid");
    }
    @Test(dataProvider = "getOpenid",priority = 1)
    public void getOpenid(Map<String,String> map){
        String Openid = weixin_yw.getOpenid(map);
        report.log("Openid为："+Openid);
    }
}
