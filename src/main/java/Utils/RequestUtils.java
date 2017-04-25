package Utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by SuperMan on 2017/4/6.
 */
public class RequestUtils {
    static ReportUtils report = new ReportUtils();
    static HttpClientBuilder builder = HttpClients.custom()
            .disableAutomaticRetries() //关闭自动处理重定向
            .setRedirectStrategy(new LaxRedirectStrategy());//利用LaxRedirectStrategy处理POST重定向问题
    static CloseableHttpClient httpClient = builder.build();// Create a local instance of cookie store

    static CookieStore cookieStore = new BasicCookieStore();

    // Create local HTTP context上下文
    static HttpClientContext localContext = HttpClientContext.create();

    private static String get(String url){

        //把cookieStore放到上下文
        localContext.setCookieStore(cookieStore);
        //定义一个字符串接收变量
        String stringResponse = null;
        //判断url是否为空
        if(url == null){
            report.log("url为空，请求不执行");
            return null;
        }else {
            //创建httpClient对象
//            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建response对象
            CloseableHttpResponse response = null;
            //声明请求方法
            HttpGet get = new HttpGet(url);
            try {
                //执行请求
                response = httpClient.execute(get,localContext);
                //判断response是否为空
                if(response != null){
                    //获取状态码
                    int statucode = response.getStatusLine().getStatusCode();
//                    report.log("状态码为："+statucode);
                    //判断状态码是否为 200
                    if(statucode == 200){
                        //获取实体
                        HttpEntity entity = response.getEntity();
                        //将实体转化为字符串
                        stringResponse = EntityUtils.toString(entity);
                        //输出相应内容
//                        report.log("响应内容为:\n"+stringResponse);
                    }else{
                        report.log("请求未通过");
                        return null;
                    }
                }else{
                    report.log("response为空");
                    return null;
                }
            } catch (ClientProtocolException e){
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            /*//关闭连接，释放资源，finally：无论如何，都要执行
            finally{
                try {
                    httpClient.close();
                    response.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }*/
        }
        return stringResponse;
    }
    public static String doGet(String  url,List<NameValuePair>...params){
        String stringResponse = null;
        //创建一个List数组
//        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        if(params.length == 0){
            stringResponse = get(url);
            return stringResponse;
        }else{
            url = url + "?";
            for(int i=0;i<params[0].size();i++){
                url = url + params[0].get(i)+"&";
            }
            report.log(url);
            stringResponse = get(url);
        }
        return stringResponse;
    }

    public static String post(String url, JSONObject jsonObject) {
        //声明HTTPClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //声明response对象，返回值String
        CloseableHttpResponse response = null;
        String stringResponse = null;
        //执行post请求
        HttpPost post = new HttpPost(url);
        //判断上传json数据是否为空，为空则不请求，直接返回空
        if (jsonObject != null) {
            //将请求数据放入实体，并设置请求的消息报头信息
            StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
            entity.setContentEncoding("utf-8");
            entity.setContentType("appLication/json");
            post.setEntity(entity);
            //执行post请求
            try {
                response = httpClient.execute(post);
                //判断返回值是否为空
                if (response != null) {
                    int statuCode = response.getStatusLine().getStatusCode();
                    //判断状态码
                    if (statuCode == 200) {
                        HttpEntity httpEntity = response.getEntity();
                        stringResponse = EntityUtils.toString(httpEntity);
                    } else {
                        report.log("状态码错误");
                        return null;
                    }
                } else {
                    report.log("response为空");
                    return null;
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 关闭连接,释放资源
            finally {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringResponse;
    }

    public static String post(File file, String url) throws IOException {
        //声明httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //声明response对象
        CloseableHttpResponse response = null;
        //定义一个返回值变量
        String stringresponse = null;
        //声明请求方法
        HttpPost post = new HttpPost(url);
        //创建一个上传文件对象
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody("file", file);
        post.setEntity(multipartEntityBuilder.build());
        try {
            //执行请求方法
            response = httpClient.execute(post);

            int statucode = response.getStatusLine().getStatusCode();
            //判断状态码
            if (statucode == 200) {
                HttpEntity entity = response.getEntity();
                stringresponse = EntityUtils.toString(entity);
            } else {
                report.log("请求错误");
                return null;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭连接,释放资源
        finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringresponse;
    }

    public static String post(String url, List<NameValuePair> params) throws IOException {

        localContext.setCookieStore(cookieStore);
        //声明httpclient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();

        //声明response对象
        CloseableHttpResponse response = null;
        //定义一个返回值变量
        String stringresponse = null;
        if (params != null) {
            HttpPost post = new HttpPost(url);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
            post.setEntity(entity);
            try {
                response = httpClient.execute(post,localContext);
                report.log("响应消息为："+response);
                int statu = response.getStatusLine().getStatusCode();
                if (statu == 200) {
                    HttpEntity httpEntity = response.getEntity();
                    stringresponse = EntityUtils.toString(httpEntity);
                } else {
                    report.log("响应错误，响应状态码为：" + statu);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            report.log("响应内容为空");
            return null;
        }
        return stringresponse;
    }
    public static String put(String url, List<NameValuePair> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String strresponse = null;
        try {
            HttpPut put = new HttpPut(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
            formEntity.setContentType("utf-8");
            formEntity.setContentType("*/*");
            put.setEntity(formEntity);
            //执行请求
            response = httpClient.execute(put);
            if (response != null) {
                int statucode = response.getStatusLine().getStatusCode();
                if (statucode == 200) {
                    strresponse = EntityUtils.toString(response.getEntity());
                } else {
                    report.log("put请求失败");
                    return null;
                }
            } else {
                report.log("response为空");
                return null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strresponse;
    }
}
