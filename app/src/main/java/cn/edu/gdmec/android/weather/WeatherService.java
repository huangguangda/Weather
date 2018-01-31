package cn.edu.gdmec.android.weather;

import android.util.Xml;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2018/1/29.
 */

public class WeatherService {

    public static List<WeatherInfo> getInfoFromXml(InputStream is) throws Exception{
        List<WeatherInfo> weatherInfos = null;
        WeatherInfo weatherInfo = null;
        //获取pull 解析器
        XmlPullParser parser = Xml.newPullParser ();
        //初始化解析器，告诉解析器要解析的xml文件
        parser.setInput ( is,"utf-8" );
        //得到当前事件类型
        int type = parser.getEventType ();
        //不到文件结尾 就一直解析
        while (type!=XmlPullParser.END_DOCUMENT){
            //具体判断 一下解析的是开始标签，还是结束标签

            switch (type){
                case XmlPullParser.START_TAG://代表解析的是开始标签
                    if ("infos".equals ( parser.getName () )){
                        weatherInfos = new ArrayList<WeatherInfo> (  );
                    }else if ("city".equals ( parser.getName () )){
                        weatherInfo = new WeatherInfo ();
                        String idStr = parser.getAttributeValue ( 0 );
                        weatherInfo.setId ( idStr );

                    }else if ("temp".equals ( parser.getName () )){
                        String temp = parser.nextText ();
                        weatherInfo.setTemp ( temp );
                    }else if ("weather".equals ( parser.getName () )){
                        String weather = parser.nextText ();
                        weatherInfo.setWeather ( weather );
                    }else if ("name".equals ( parser.getName () )){
                        String name = parser.nextText ();
                        weatherInfo.setName ( name );
                    }else if ("pm".equals ( parser.getName () )){
                        String pm = parser.nextText ();
                        weatherInfo.setPm ( pm );
                    }else if ("wind".equals ( parser.getName () )){
                        String wind = parser.nextText ();
                        weatherInfo.setWind ( wind );
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("city".equals ( parser.getName () )){
                        //把bean对象加入到集合
                        weatherInfos.add ( weatherInfo );
                    }
                    break;
            }
            type = parser.next ();

        }

        return weatherInfos;
    }
    /*解析Json文件 返回天气信息集合*/
    public static List<WeatherInfo> getInfosFromJson(InputStream is) throws Exception{
        byte[] buffer = new byte[is.available ()];
        is.read (buffer);
        String json=new String ( buffer, "utf-8" );
        //使用gson进行解析
        Gson gson = new Gson();
        Type ListType = new TypeToken<List<WeatherInfo>>(){}.getType();
        List<WeatherInfo> weatherInfos = gson.fromJson(json, listType);

        return weatherInfos;
    }

}
