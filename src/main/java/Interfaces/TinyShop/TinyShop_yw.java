package Interfaces.TinyShop;

import InterfaceFarmeWoke.DataUtils;
import Utils.ReportUtils;
import Utils.RequestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MrChen on 2017/4/23.
 */
public class TinyShop_yw {
    static String token = null;
    static String response = null;
    static ReportUtils report = new ReportUtils();
    //打开首页
    public static String openShouYe(Map<String,String> map) {
        response = RequestUtils.doGet(map.get("index"));
        if (response.contains(map.get("expected result"))) {
            report.log("打开首页成功！");
        } else {
            report.log("打开首页失败！");
            return  null;
        }
        return response;
    }
    //登录
    public static String Login(Map<String,String> map) throws IOException {
        //登录验证——获取token
        List<NameValuePair> getToken = new ArrayList<NameValuePair>();
        getToken.add(new BasicNameValuePair("con","simple"));
        getToken.add(new BasicNameValuePair("act","login"));
        response = RequestUtils.doGet(map.get("index"),getToken);
        token = DataUtils.HtmlParse(response,"input[name=tiny_token_login]","value");
        report.log("token为：\t"+token);
        //登录——参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("redirectURL",map.get("parameter_redirectURL")));
        list.add(new BasicNameValuePair("account",map.get("parameter_account")));
        list.add(new BasicNameValuePair("password",map.get("parameter_password")));
        list.add(new BasicNameValuePair("tiny_token_login",token));
        RequestUtils.doGet(map.get("index"),list);
        response = RequestUtils.post(map.get("请求地址_url"),list);
//        report.log("登录成功后的响应信息："+response);

        return response;
    }
}
