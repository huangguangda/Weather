package cn.edu.gdmec.android.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvCity;
    private TextView tvWeather;
    private TextView tvTemp;
    private TextView tvWind;
    private TextView tvPm;
    private ImageView ivIcon;

    private List<Map<String, String>> list;
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        //初始化
        initView();


        //解析weather,xml信息
        InputStream inputStream=getResources ().openRawResource ( R.raw.weather2 );
        try {
            List<WeatherInfo> weatherInfos = WeatherService.getInfoFromXml ( inputStream );
            list = new ArrayList<Map<String, String>> (  );
            for (WeatherInfo info: weatherInfos){

                map = new HashMap<> (  );
                map.put ( "temp",info.getTemp () );
                map.put ( "weather",info.getWeather () );
                map.put ( "name",info.getName () );
                map.put ( "pm",info.getPm() );
                map.put ( "wind",info.getWind () );
                list.add ( map );

            }

        } catch (Exception e) {
            e.printStackTrace ();
        }
        //自定义getMap方法 显示天气信息到文本控件中 默认显示北京
        getMap(0,R.drawable.sun);


    }

    //讲天气信息分条显示到界面上
    private void getMap(int number, int iconNumber) {
        Map<String, String> cityMap = list.get ( number );

        String temp=cityMap.get ( "temp" );
        String weather=cityMap.get ( "weather" );
        String name=cityMap.get ( "name" );
        String pm=cityMap.get ( "pm" );
        String wind=cityMap.get ( "wind" );

        tvCity.setText ( name );
        tvWeather.setText ( weather );
        tvTemp.setText ( ""+temp );
        tvWind.setText ( "风力"+wind );
        tvPm.setText ( "pm:"+pm );
        ivIcon.setImageResource ( iconNumber );
    }

    private void initView() {
        tvCity = (TextView) findViewById ( R.id.tv_city );
        tvWeather = (TextView) findViewById ( R.id.tv_weather );
        tvTemp = (TextView) findViewById ( R.id.tv_temp );
        tvWind = (TextView) findViewById ( R.id.tv_wind );
        tvPm = (TextView) findViewById ( R.id.tv_pm );
        ivIcon = (ImageView) findViewById ( R.id.iv_icon );

        findViewById ( R.id.btn_bj ).setOnClickListener ( this );
        findViewById ( R.id.btn_sh ).setOnClickListener ( this );
        findViewById ( R.id.btn_gz ).setOnClickListener ( this );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()){


            case R.id.btn_bj:
                getMap ( 0,R.drawable.sun );
                break;
            case R.id.btn_gz:
                getMap ( 1,R.drawable.cloud_sun );
                break;
            case R.id.btn_sh://点击的是上海
                getMap ( 2,R.drawable.clouds );
                break;
        }
    }
}
