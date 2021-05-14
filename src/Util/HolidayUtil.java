package Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HolidayUtil {
    public static String request( String date) {
        String httpUrl = "https://tool.bitefu.net/jiari/?d=" + date;
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        String d="";
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            if ((strRead = reader.readLine()) != null) {
                sbf = sbf.append(strRead);
                //System.out.println("day:"+sbf);
                d = String.valueOf(sbf);
                //System.out.println("d" + d);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String ifHoliday(String today){
        String str = "";
        if(request(today).equals("2")){
            System.out.println("Holiday------0.85");
        }else if(request(today).equals("1")){
            System.out.println("Resting------0.95");
        }else if(request(today).equals("0")){
            System.out.println("Resting------1");
        }

        return str;
    }
/*
    public static void main(String[] args) {
        //判断今天是否是工作日 周末 还是节假日
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        //String date = df.format(new Date());
        String date = "2021-10-10";
        String discount=ifHoliday(date);
        System.out.println(discount);
    }*/
}
