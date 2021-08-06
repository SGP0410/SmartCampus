package com.example.smartcampuslibrary.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import com.example.smartcampuslibrary.ZhcsConfig;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class OkHttpTo extends Thread {
    private String url = ZhcsConfig.getUrl();
    private JSONObject jsonObject = new JSONObject();
    private OkHttpLo okHttpLo;
    private String requestType;
    private int time;
    private boolean isLoop;
    private ProgressDialog progressDialog;
    private String toKen = ZhcsConfig.getToKen();
    private File file;
    private String fileK;
    private boolean isForm;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (msg.what == 1) {
                okHttpLo.onResponse((JSONObject) msg.obj);
            } else if (msg.what == 2) {
                okHttpLo.onFailure((IOException) msg.obj);
            }
            return false;
        }
    });

    public OkHttpTo setIsForm(boolean isForm){
        this.isForm = isForm;
        return this;
    }

    public OkHttpTo setFile(String fileK, File file) {
        this.fileK = fileK;
        this.file = file;
        return this;
    }

    public OkHttpTo setToKen(String toKen) {
        this.toKen = toKen;
        return this;
    }

    public OkHttpTo setUrl(String url) {
        this.url += url;
        return this;
    }

    public OkHttpTo setJSONObject(String bean) {
        try {
            jsonObject = new JSONObject(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public OkHttpTo setJSONObject(String k, Object v) {
        try {
            jsonObject.put(k, v);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public OkHttpTo setOkHttpLo(OkHttpLo okHttpLo) {
        this.okHttpLo = okHttpLo;
        return this;
    }

    public OkHttpTo setRequestType(String type) {
        this.requestType = type;
        return this;
    }

    public OkHttpTo setTime(int time) {
        this.time = time;
        return this;
    }

    public OkHttpTo setLoop(boolean loop) {
        this.isLoop = loop;
        return this;
    }

    public OkHttpTo setDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("网络请求中。。。");
        progressDialog.setTitle("提示");
        progressDialog.show();
        return this;
    }

    @Override
    public void run() {
    
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS)
            .readTimeout(60000, TimeUnit.MILLISECONDS)
            .build();
        do {

            RequestBody body = null;
            if (file == null && !isForm) {
                body = RequestBody.create(MediaType.parse("application/json;chart=utf-8"), jsonObject.toString());
            } else {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                if (fileK != null && file != null){
                    builder.addFormDataPart(fileK, file.getName(),
                            RequestBody.create(MediaType.parse("application/octet-stream"), file));
                }
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    builder.addFormDataPart(key, jsonObject.optString(key));
                }
                body = builder.build();
            }

            Request request = new Request.Builder()
                    .url(url)
                    .method(requestType.toUpperCase(), requestType.toUpperCase().equals("GET") ? null : body)
                    .addHeader("Authorization", toKen)
                    .build();

            final Message message = new Message();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    message.what = 2;
                    message.obj = e;
                    if (progressDialog != null) {
                        handler.sendMessageDelayed(message, 1000);
                    } else {
                        handler.sendMessage(message);
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    message.what = 1;
                    try {
                        assert response.body() != null;
                        message.obj = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (progressDialog != null) {
                        handler.sendMessageDelayed(message, 1000);
                    } else {
                        handler.sendMessage(message);
                    }
                }
            });

            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (isLoop);
    }
}
