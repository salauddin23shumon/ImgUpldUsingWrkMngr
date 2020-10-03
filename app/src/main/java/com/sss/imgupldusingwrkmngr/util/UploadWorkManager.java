package com.sss.imgupldusingwrkmngr.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.annotations.SerializedName;
import com.sss.imgupldusingwrkmngr.R;
import com.sss.imgupldusingwrkmngr.model.PostResponse;
import com.sss.imgupldusingwrkmngr.networking.RetrofitClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sss.imgupldusingwrkmngr.util.Constant.BASE_URL;

public class UploadWorkManager extends Worker {

    public static final String TAG="UploadWorkManager";

    public UploadWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String imagePath = getInputData().getString("imagePath");
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileupload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());



        Call<PostResponse> call = RetrofitClient.getInstance(BASE_URL).getApiService().postData(fileupload, filename);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if(response.isSuccessful()){
                    showNotification("Inducesmile", "Image uploaded successfully");
                    Log.d(TAG, "onResponse: "+response.body());
                }else
                    Log.d(TAG, "onResponse: "+response.code());
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Result.failure();
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
        return Result.success();
    }

    private void showNotification(String title, String task){
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("inducesmile", "inducesmile", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(),"inducesmile")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);
        notificationManager.notify(1, notification.build());
    }

}