package com.example.tony.iphone7reservation;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    Map<String,String> colorMap = new HashMap<>();
    Map<String,String> sizeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMap(colorMap,sizeMap);
        run();
        IPhone iPhone = new IPhone("8V","R409",colorMap,sizeMap);
        iPhone.show();
    }

    OkHttpClient client = new OkHttpClient();
    private void run() {
        new AsyncTask<Void,Void,String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String url = "https://reserve.cdn-apple.com/HK/zh_HK/reserve/iPhone/availability.json";
                String result = null;
                Request request = new Request.Builder().url(url).build();
                try (Response response = client.newCall(request).execute())
                {
                    result = response.body().string();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                Scanner scanner = new Scanner(s);
                String store = null;
                String storePattern = "  \"R(\\d+)\" : \\{";
                String modelPattern = "    \"MN(.+)2ZP/A\" : \"ALL\",";


                Pattern r1 = Pattern.compile(storePattern);
                Pattern r2 = Pattern.compile(modelPattern);
                Matcher m1;
                Matcher m2;

                List<IPhone> iPhoneList = new ArrayList<>();

                while (scanner.hasNextLine()) {
                    String nextLine = scanner.nextLine();
                    m1 = r1.matcher(nextLine);

                    if (m1.find()) {
                        store = m1.group(1);
                    } else {
                        m2 = r2.matcher(nextLine);
                        if (m2.find()) {
                            //Log.d(TAG,);
                            iPhoneList.add(new IPhone(m2.group(1),store,colorMap,sizeMap));
                        }
                    }
                }
                //Log.d(TAG,iPhoneList.toString());
                for (IPhone iPhone: iPhoneList) {
                    Log.d(TAG,iPhone.getColor()+" "+iPhone.getSize()+" "+iPhone.getStore());
                }

            }
        }.execute();

    }

    private void initializeMap(Map<String,String> colorMap,Map<String,String> sizeMap) {
        colorMap.put("8V","256");
        colorMap.put("8G","32");
        colorMap.put("49","128");
        colorMap.put("QK","32");
        colorMap.put("8N","128");
        colorMap.put("8Q","128");
        colorMap.put("4L","256");
        colorMap.put("8K","32");
        colorMap.put("4F","256");
        colorMap.put("8U","256");
        colorMap.put("8R","256");
        colorMap.put("QJ","32");
        colorMap.put("8H","32");
        colorMap.put("4C","128");
        colorMap.put("4D","128");
        colorMap.put("4J","256");
        colorMap.put("4A","128");
        colorMap.put("8L","128");
        colorMap.put("8T","256");
        colorMap.put("QH","32");
        colorMap.put("8W","256");
        colorMap.put("4E","256");
        colorMap.put("8J","32");
        colorMap.put("8M","128");
        colorMap.put("4K","256");
        colorMap.put("48","128");
        colorMap.put("QL","32");
        colorMap.put("8P","128");

        sizeMap.put("8V","Rose");
        sizeMap.put("8G","Matte");
        sizeMap.put("49","Silver");
        sizeMap.put("QK","Gold");
        sizeMap.put("8N","Gold");
        sizeMap.put("8Q","JB");
        sizeMap.put("4L","JB");
        sizeMap.put("8K","Rose");
        sizeMap.put("4F","Silver");
        sizeMap.put("8U","Gold");
        sizeMap.put("8R","Matte");
        sizeMap.put("QJ","Silver");
        sizeMap.put("8H","Silver");
        sizeMap.put("4C","Rose");
        sizeMap.put("4D","JB");
        sizeMap.put("4J","Gold");
        sizeMap.put("4A","Gold");
        sizeMap.put("8L","Matte");
        sizeMap.put("8T","Silver");
        sizeMap.put("QH","Matte");
        sizeMap.put("8W","JB");
        sizeMap.put("4E","Matte");
        sizeMap.put("8J","Gold");
        sizeMap.put("8M","Silver");
        sizeMap.put("4K","Rose");
        sizeMap.put("48","Matte");
        sizeMap.put("QL","Rose");
        sizeMap.put("8P","Rose");
    }

}
