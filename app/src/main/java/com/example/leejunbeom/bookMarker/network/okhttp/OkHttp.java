package com.example.leejunbeom.bookMarker.network.okhttp;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Jun on 16. 4. 12..
 */
public class OkHttp {


    public static final String BASE_URL="";
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public String doPostRequest(String url, String requestData) throws IOException {

        RequestBody formBody = new FormEncodingBuilder().add("cid",requestData).build();
        //RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,requestData);
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
