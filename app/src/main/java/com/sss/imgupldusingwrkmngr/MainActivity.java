package com.sss.imgupldusingwrkmngr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sss.imgupldusingwrkmngr.util.UploadWorkManager;

import java.io.File;
import java.net.URI;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_STORAGE_ACCESS = 111;
    private static final int PHOTO_PICK_RQST = 222;
    private static final String TAG = "MainActivity ";
    private Button pickpicture;
    private Button upload;
    private String imagePathUri;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickpicture = (Button) findViewById(R.id.pickpicture);
        imageView = findViewById(R.id.imageView);
        upload = (Button) findViewById(R.id.upload);

        chkStoragePermission(this);

        pickpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkStoragePermission(MainActivity.this)){
                    choseFile();
                }
                else {
                    Toast.makeText(MainActivity.this, "Allow permission to access storage", Toast.LENGTH_SHORT).show();
                }

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWork();
                imageView.setImageResource(0);
            }
        });
    }

    private void choseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), PHOTO_PICK_RQST);
    }

    private void startWork(){
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(UploadWorkManager.class)
                .setInputData(createInputData(imagePathUri))
                .setInitialDelay(2, TimeUnit.SECONDS).build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }
    //result is been return here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_PICK_RQST) {
                if (data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
//                    Picasso.get().load(selectedImage).into(imageView);
                    imagePathUri=getRealPathFromURIPath(selectedImage);
                }
            }
        }
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        String path="";
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            Log.d(TAG, "getRealPathFromURIPath: path: "+path);
            return path;
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private Data createInputData(String imagePath){
        Data data = new Data.Builder()
                .putString("imagePath", imagePath)
                .build();
        return data;
    }
    //geting real path from gallery

    private String getRealPathFromURIPath(Uri contentURI) {
        String doc_id="";

        try {
            Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
            cursor.moveToFirst();
            doc_id = cursor.getString(0);
            doc_id = doc_id.substring(doc_id.lastIndexOf(":") + 1);
            Log.d(TAG, "getRealPathFromURIPath: id " + doc_id);
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return doc_id;
    }



        private Boolean chkStoragePermission(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , REQUEST_STORAGE_ACCESS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_STORAGE_ACCESS && grantResults[0]==Activity.RESULT_OK){
//            choseFile();
        }
    }
}