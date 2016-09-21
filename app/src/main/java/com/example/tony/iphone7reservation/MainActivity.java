package com.example.tony.iphone7reservation;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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
    Map<String,Boolean> plusMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMap(colorMap,sizeMap);
        run();
        IPhone iPhone = new IPhone("8V","R409",colorMap,sizeMap,plusMap);
        Log.d(TAG,iPhone.toString());

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run();
            }
        });
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
                            iPhoneList.add(new IPhone(m2.group(1),store,colorMap,sizeMap,plusMap));
                        }
                    }
                }

                sortIPhone(iPhoneList);

                for (IPhone iPhone: iPhoneList) {
                    Log.d(TAG,iPhone.toString());
                }

                ListAdapter theAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,iPhoneList);

                ListView theListView = (ListView) findViewById(R.id.listView);

                theListView.setAdapter(theAdapter);

            }
        }.execute();

    }

    private void initializeMap(Map<String,String> colorMap,Map<String,String> sizeMap) {
        sizeMap.put("8V","256");
        sizeMap.put("8G","32");
        sizeMap.put("49","128");
        sizeMap.put("QK","32");
        sizeMap.put("8N","128");
        sizeMap.put("8Q","128");
        sizeMap.put("4L","256");
        sizeMap.put("8K","32");
        sizeMap.put("4F","256");
        sizeMap.put("8U","256");
        sizeMap.put("8R","256");
        sizeMap.put("QJ","32");
        sizeMap.put("8H","32");
        sizeMap.put("4C","128");
        sizeMap.put("4D","128");
        sizeMap.put("4J","256");
        sizeMap.put("4A","128");
        sizeMap.put("8L","128");
        sizeMap.put("8T","256");
        sizeMap.put("QH","32");
        sizeMap.put("8W","256");
        sizeMap.put("4E","256");
        sizeMap.put("8J","32");
        sizeMap.put("8M","128");
        sizeMap.put("4K","256");
        sizeMap.put("48","128");
        sizeMap.put("QL","32");
        sizeMap.put("8P","128");

        colorMap.put("8V","Rose");
        colorMap.put("8G","Matte");
        colorMap.put("49","Silver");
        colorMap.put("QK","Gold");
        colorMap.put("8N","Gold");
        colorMap.put("8Q","JB");
        colorMap.put("4L","JB");
        colorMap.put("8K","Rose");
        colorMap.put("4F","Silver");
        colorMap.put("8U","Gold");
        colorMap.put("8R","Matte");
        colorMap.put("QJ","Silver");
        colorMap.put("8H","Silver");
        colorMap.put("4C","Rose");
        colorMap.put("4D","JB");
        colorMap.put("4J","Gold");
        colorMap.put("4A","Gold");
        colorMap.put("8L","Matte");
        colorMap.put("8T","Silver");
        colorMap.put("QH","Matte");
        colorMap.put("8W","JB");
        colorMap.put("4E","Matte");
        colorMap.put("8J","Gold");
        colorMap.put("8M","Silver");
        colorMap.put("4K","Rose");
        colorMap.put("48","Matte");
        colorMap.put("QL","Rose");
        colorMap.put("8P","Rose");

        plusMap.put("8V",false);
        plusMap.put("8G",false);
        plusMap.put("49",true);
        plusMap.put("QK",true);
        plusMap.put("8N",false);
        plusMap.put("8Q",false);
        plusMap.put("4L",true);
        plusMap.put("8K",false);
        plusMap.put("4F",true);
        plusMap.put("8U",false);
        plusMap.put("8R",false);
        plusMap.put("QJ",true);
        plusMap.put("8H",false);
        plusMap.put("4C",true);
        plusMap.put("4D",true);
        plusMap.put("4J",true);
        plusMap.put("4A",true);
        plusMap.put("8L",false);
        plusMap.put("8T",false);
        plusMap.put("QH",true);
        plusMap.put("8W",false);
        plusMap.put("4E",true);
        plusMap.put("8J",false);
        plusMap.put("8M",false);
        plusMap.put("4K",true);
        plusMap.put("48",true);
        plusMap.put("QL",true);
        plusMap.put("8P",false);
    }

    private void sortIPhone(List<IPhone> iPhoneList) {

        Collections.sort(iPhoneList, new Comparator<IPhone>() {
            List colorPosition = Arrays.asList("Silver","Gold","Rose","Matte","JB");
            @Override
            public int compare(IPhone iPhone, IPhone t1) {
                if (iPhone.getPlus().equals("plus") && t1.getPlus().equals("")) {
                    return 1;
                } else if (iPhone.getPlus().equals("") && t1.getPlus().equals("plus")) {
                    return -1;
                } else {
                    if ((colorPosition.indexOf(t1.getColor()) - colorPosition.indexOf(iPhone.getColor())) == 0) {
                        return t1.getSize() - iPhone.getSize();
                    } else {
                        return colorPosition.indexOf(t1.getColor()) - colorPosition.indexOf(iPhone.getColor());
                    }
                }
            }
        });
    }
}
