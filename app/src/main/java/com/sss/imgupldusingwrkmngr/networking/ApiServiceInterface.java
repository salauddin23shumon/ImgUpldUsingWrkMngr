package com.sss.imgupldusingwrkmngr.networking;


import com.sss.imgupldusingwrkmngr.model.PostResponse;
import com.sss.imgupldusingwrkmngr.util.UploadWorkManager;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiServiceInterface {

    @Multipart
    @POST("index.php")
    Call<PostResponse> postData(@Part MultipartBody.Part file, @Part("name") RequestBody name);
}
