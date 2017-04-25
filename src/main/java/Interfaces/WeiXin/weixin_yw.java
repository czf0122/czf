package Interfaces.WeiXin;

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
 * Created by MrChen on 2017/4/15.
 */
public class weixin_yw {
    static ReportUtils report = new ReportUtils();
    static String access_token = null;
    static String response = null;
    static String stringResponse = null;
    public static String getToken (Map<String,String> map) throws IOException {
        //请求地址：https://api.weixin.qq.com/cgi-bin/token
        stringResponse = map.get("请求地址");
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("grant_type",map.get("参数_grant_type")));
        list.add(new BasicNameValuePair("appid",map.get("参数_appid")));
        list.add(new BasicNameValuePair("secret",map.get("参数_secret")));
        response = RequestUtils.doGet(stringResponse,list);
        access_token = DataUtils.parseJson(response,"$.access_token");
        report.log("access_token为：\n"+access_token);

        //断言
        if(response.contains(map.get("预期结果_expires_in"))){
            report.log("已获得access_token");
        }else {
            report.log("获取access_token失败");
            return null;
        }
        return response;
    }
    public static String getOpenid (Map<String,String> map){
        String token = (map.get("请求地址"));
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("access_token",access_token ));
        response = RequestUtils.doGet(token,list);
        report.log("Openid响应信息为："+response);
        return response;

    }

}
