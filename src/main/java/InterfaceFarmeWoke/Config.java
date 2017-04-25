package InterfaceFarmeWoke;

import java.util.ResourceBundle;

/**
 * Created by MrChen on 2017/4/14.
 */
public class Config {
    //根据文件名获取数据
    private static ResourceBundle rb = ResourceBundle.getBundle("Config");
    public final static String HOST_URL = rb.getString("host.url");
    public final static String Requset_TYPE = rb.getString("requset.type");
}
