package wang.yuchao.android.library.https.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

import wang.yuchao.android.library.http.HttpRequest;
import wang.yuchao.android.library.http.HttpResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = "";
                HashMap<String, String> allParams = new HashMap<String, String>();
                HttpResponse httpResponse = HttpRequest.get(url, allParams);
            }
        }.start();
    }
}
