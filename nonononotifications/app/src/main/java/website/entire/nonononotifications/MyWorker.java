package website.entire.nonononotifications;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public static final String IMG_URL_KEY = "ImageURL";
    public static final String IMG_DATA_KEY = "ImageByteArray";

    public MyWorker(Context context, WorkerParameters params){
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        return null;
    }
}
