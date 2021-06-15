package com.example.smartcampuslibrary.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpImageTo extends Thread {
    private String url;
    private OkHttpImageLo okHttpImageLo;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1)
                okHttpImageLo.onResponse((Bitmap) msg.obj);
            else if (msg.what == 2)
                okHttpImageLo.onFailure((IOException) msg.obj);
            return false;
        }
    });

    public OkHttpImageTo setUrl(String url){
        this.url = url;
        return this;
    }

    public OkHttpImageTo setOkHttpImageLo(OkHttpImageLo okHttpImageLo){
        this.okHttpImageLo = okHttpImageLo;
        return this;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        final Message message = new Message();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                message.what = 2;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message.what = 1;
                message.obj = BitmapFactory.decodeStream(response.body().byteStream());
                handler.sendMessage(message);
            }
        });
    }
}
