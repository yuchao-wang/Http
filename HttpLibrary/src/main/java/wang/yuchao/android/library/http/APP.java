package wang.yuchao.android.library.http;

import android.app.Application;
import android.content.Context;

/**
 * Created by wangyuchao on 17/2/27.
 */

public class APP extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.context = getApplicationContext();
    }
}
