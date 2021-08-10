package com.example.smartcampus.fragment.applyFragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcampus.R;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

public class XiaoYuanKaEWM extends BaseFragment {


    private ImageView back;
    private TextView title;
    private Button souKuan;
    private Button zhiFu;
    private ImageView ewm;
    private TextView content;

    @Override
    protected int layoutResId() {
        return R.layout.xiaouianka_ewm;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        title.setText("收付款");
        souKuan = view.findViewById(R.id.sou_kuan);
        souKuan.setOnClickListener(this);
        zhiFu = view.findViewById(R.id.zhi_fu);
        zhiFu.setOnClickListener(this);
        ewm = view.findViewById(R.id.ewm);
        content = view.findViewById(R.id.content);
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sou_kuan:
                content.setText("收款二维码");
                showEWM("收款二维码");
                break;
            case R.id.zhi_fu:
                content.setText("付款二维码");
                showEWM("付款二维码");
                break;
        }
    }

    private void showEWM(String Url){
        Map<EncodeHintType, String> map = new HashMap<>();
        map.put(EncodeHintType.CHARACTER_SET , "UTF-8");

        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(Url , BarcodeFormat.QR_CODE , 300 , 300 , map);
            int[] arr = new int[bitMatrix.getWidth() * bitMatrix.getHeight()];
            for (int i = 0; i < bitMatrix.getWidth(); i++) {
                for (int j = 0; j < bitMatrix.getHeight(); j++) {
                    if (bitMatrix.get(i , j)){
                        arr[j * 300 + i] = Color.BLACK;
                    }else {
                        arr[j * 300 + i] = Color.WHITE;
                    }
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(bitMatrix.getWidth() , bitMatrix.getHeight() , Bitmap.Config.ARGB_8888);
            bitmap.setPixels(arr , 0 , 300 , 0 , 0 , bitmap.getWidth() , bitmap.getHeight());
            ewm.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
