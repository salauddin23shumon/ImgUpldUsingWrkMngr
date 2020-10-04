package com.sss.imgupldusingwrkmngr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.util.UUID;

import static com.sss.imgupldusingwrkmngr.util.Constant.BASE_URL;
import static com.sss.imgupldusingwrkmngr.util.Constant.BASE_URL2;

public class MainActivity2 extends AppCompatActivity {


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
        setContentView(R.layout.activity_main2);

        pickpicture = (Button) findViewById(R.id.pickpicture);
        imageView = findViewById(R.id.imageView);
        upload = (Button) findViewById(R.id.upload);

        chkStoragePermission(this);

        pickpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkStoragePermission(MainActivity2.this)) {
                    choseFile();
                } else {
                    Toast.makeText(MainActivity2.this, "Allow permission to access storage", Toast.LENGTH_SHORT).show();
                }

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMultipart(MainActivity2.this, imagePathUri);
                imageView.setImageResource(0);
            }
        });

    }

    private void choseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), PHOTO_PICK_RQST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_PICK_RQST) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    Picasso.get().load(selectedImage).into(imageView);
                    imagePathUri = getRealPathFromURIPath(selectedImage);
                }
            }
        }
    }

    private String getRealPathFromURIPath(Uri selectedImage) {
        String doc_id = "";

        try {
            Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
            cursor.moveToFirst();
            doc_id = cursor.getString(0);
            doc_id = doc_id.substring(doc_id.lastIndexOf(":") + 1);
            Log.d(TAG, "getRealPathFromURIPath: id " + doc_id);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc_id;
    }

    public void uploadMultipart(final Context context, String filePath) {
        Log.d(TAG, "uploadMultipart: " + filePath);
        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(context, uploadId, BASE_URL+"media.php")
                    .addFileToUpload(filePath, "file")
//                    .addParameter("name", "any")
//                    .addParameter("email", "any@gmail.com")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(1)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            Log.e(TAG, "onProgress: ");
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            Log.e(TAG, "onError: ");
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.e(TAG, "onCompleted: ");
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            Log.e(TAG, "onCancelled: ");
                        }
                    })
                    .startUpload();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
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
}