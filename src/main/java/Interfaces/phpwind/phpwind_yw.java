package Interfaces.phpwind;

import InterfaceFarmeWoke.DataUtils;
import Utils.RequestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MrChen on 2017/4/13.
 */
public class phpwind_yw {
     static String csrf_token = null;
     static String response = null;
    //打开首页
    public static String openShouYe(Map<String,String>map) {
        response = RequestUtils.doGet(map.get("请求地址_url"));
        if (response.contains(map.get("预期结果_expectation"))) {
            System.out.println("成功打开首页");
        } else {
            System.out.println("打开首页失败");
            return  null;
        }
        return response;
    }

    //注册
    public static String register(Map<String,String> map) throws IOException {
        String registerResponse = null;
        //获取csrf_token
        String url = map.get("get_csrf_token");
        //注册的接口地址
        String url2 = map.get("请求地址_url");
        response = RequestUtils.doGet(url);
        csrf_token = DataUtils.HtmlParse(response,"input[name=csrf_token]","value");
        System.out.println("csrf_token为："+csrf_token);

        //组装用户信息
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("username",map.get("参数_username")));
        list.add(new BasicNameValuePair("password",map.get("参数_password")));
        list.add(new BasicNameValuePair("repassword",map.get("参数_repassword")));
        list.add(new BasicNameValuePair("email",map.get("参数_email")));
        list.add(new BasicNameValuePair("csrf_token",csrf_token));
        registerResponse = RequestUtils.post(url2,list);
        return registerResponse;
    }

    //退出
    public static String quit(String url){
        String quitResponse = null;
        quitResponse = RequestUtils.doGet(url);
        System.out.println("退出登陆");
        return quitResponse;
    }

    //登录
    public static String Login(Map<String,String> map) throws IOException {

        String stringResponse = null;
        //获取csrf_token
        String shouye_url = map.get("get_csrf_token");
        response = RequestUtils.doGet(shouye_url);
        csrf_token = DataUtils.HtmlParse(response,"input[name=csrf_token]","value");
        //组装参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("username",map.get("参数_username")));
        list.add(new BasicNameValuePair("password",map.get("参数_password")));
        list.add(new BasicNameValuePair("csrf_token",csrf_token));
        list.add(new BasicNameValuePair("csrf_token",csrf_token));
        stringResponse = RequestUtils.post(map.get("请求地址_url"),list);
        //业务逻辑判断
        if(stringResponse.contains("成功提示")){
            System.out.println("登录成功");
            return stringResponse;
        }else{
            System.out.println("登录失败，请重新登录");
        }
        return response;
    }

    //发帖
    public static String posting(Map<String,String>map) throws IOException {
        String csrf_token = null;
        String stresponse = null;
        String _statu = null;
        String response = RequestUtils.doGet(map.get("get_csrf_token"));
        csrf_token = DataUtils.HtmlParse(response, "input", "value");
        System.out.println("csrf_token:" + csrf_token);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", map.get("参数_username")));
        params.add(new BasicNameValuePair("password", map.get("参数_password")));
        params.add(new BasicNameValuePair("csrf_token", csrf_token));
        params.add(new BasicNameValuePair("csrf_token", csrf_token));
        stresponse = RequestUtils.post(map.get("login_url"), params);

        if (stresponse.contains("成功提示")) {
            System.out.println("登录成功");
            Document doc = null;
            doc = Jsoup.parse(stresponse);
            Element a = doc.getElementsByAttributeValue("class", "error_return").get(0).getElementsByTag("a").get(0);
            String href = a.attr("href");
            _statu = href.split("_statu=")[1];
            System.out.println("_statu为" + _statu);
            String uri_welcome = map.get("Welcome_url") + _statu;
            System.out.println(uri_welcome);
            String responseWelcome = RequestUtils.doGet("请求地址_url");
            System.out.println(responseWelcome);
        } else {
            System.out.println("登录失败，请重新登录");
        }
        String respones = null;
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("atc_title",map.get("atc_title")));
        list.add(new BasicNameValuePair("atc_content",map.get("atc_content")));
        list.add(new BasicNameValuePair("pid",""));
        list.add(new BasicNameValuePair("tid",""));
        list.add(new BasicNameValuePair("special","default"));
        list.add(new BasicNameValuePair("reply_notice","2"));
        list.add(new BasicNameValuePair("csrf_token",csrf_token));
        System.out.println("获取的表为："+list);
        System.out.println("获取的url"+map.get("请求地址_url"));
        respones =RequestUtils.post(map.get("请求地址_url"),list);
        System.out.println("获取的响应为："+respones);
        if (respones.contains("fail")){
            System.out.println("发帖失败");
        }else{
            System.out.println("发帖成功");
        }
        return  respones;
    }
}
