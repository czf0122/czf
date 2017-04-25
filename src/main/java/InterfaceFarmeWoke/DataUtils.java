package InterfaceFarmeWoke;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

/**
 * Created by SuperMan on 2017/4/6.
 */
public class DataUtils {
    //解析Json数据，Json ——> String
    private static JSONObject string2Json(String stringResponse){
        //定义一个JSONObject变量接收stringResponse的值
        JSONObject value = null;
        //判断stringResponse的值是否为空
        if(stringResponse.equals("")){
            System.out.println("传入的参数为空");
            return null;
        }else{
            value = JSON.parseObject(stringResponse);
        }
        return value;
    }
    //定义解析Json方法
    private static String parseJson(JSONObject jsonObject,String path){
        String value = null;
        //判断path是否为空，如果path为空，则返回原来的数据
        if(path == null || path.equals("")){
            System.out.println("path为空,返回原来的数据");
            return jsonObject.toString();
        }else if(jsonObject == null){
            System.out.println("传入的数据为空");
            return null;
        }
        else{
            value = JSONPath.eval(jsonObject,path).toString();
        }
        return value;
    }

    public static String parseJson(String response,String path){
//        System.out.println(parseJson(string2Json(response),path));
        String value = null;
        if(response.equals("")||response == null){
            System.out.println("输入的参数为空");
            return null;
        }else{
            value = parseJson(string2Json(response),path);
        }
        return value;
    }

    private static Document string2HTML(String html){
        if(html == null || html.equals("")){
            return null;
        }else{
            return Jsoup.parse(html);
        }
    }

    private static Document url2HTML(String url) throws IOException {
        if(url == null || url.equals("")){
            return null;
        }else{
            return Jsoup.connect(url).get();
        }
    }

    //通过属性定位元素——获取属性
    private static String HtmlParserAttr(Document document,String path,String key){
        String value = null;
        if(path == null || path.equals("")){
            return document.toString();
        }else{
            value = document.select(path).attr(key);
        }
        return value;
    }

    //通过文本定位元素——获取文本
    private static String HtmlParserText(Document document,String path){
        String value = null;
        if(path == null || path.equals("")){
            return document.toString();
        }else{
            value = document.select(path).text();
        }
        return value;
    }

    public static String HtmlParse(String html,String path,String ... key){
        if (key.length == 0){
            return HtmlParserText(string2HTML(html),path);
        }else {
            return HtmlParserAttr(string2HTML(html),path,key[0]);
        }
    }
}
