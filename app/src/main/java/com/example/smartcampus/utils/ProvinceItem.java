package com.example.smartcampus.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;

public class ProvinceItem {
    /**
     * 省份路径
     */
    private Path mPath;

    /**
     * 名称
     */
    private String name;

    /**
     * 区块颜色
     */
    private int mDrawColor;

    /**
     * path的有效区域
     */
    private Region mRegion;

    public String getName() {
        return name;
    }

    public ProvinceItem(Path path , String name){
        this.mPath = path;
        this.name = name;

        RectF rectF = new RectF();

        //计算path的边界
        mPath.computeBounds(rectF , true);

        mRegion = new Region();
        //得到path和其最大矩形范围的交集区域
        mRegion.setPath(mPath , new Region((int) rectF.left , (int) rectF.top ,
                (int) rectF.right , (int) rectF.bottom));
    }

    /**
     * 设置区块绘制颜色
     * @param drawColor
     */
    public void setDrawColor(int drawColor){
        mDrawColor = drawColor;
    }

    public void drawItem(Canvas canvas , Paint paint , boolean isSelect){

        if (isSelect){
            //选中状态
            paint.clearShadowLayer();
            paint.setStrokeWidth(1);

            //绘制填充
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(mDrawColor);
            canvas.drawPath(mPath , paint);

            //绘制描边
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            canvas.drawPath(mPath , paint);
        } else {
            //普通状态
            paint.setStrokeWidth(2);

            //绘制底色+阴影
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(8 , 0 , 0 , 0xffffff);
            canvas.drawPath(mPath , paint);

            //绘制填充
            paint.clearShadowLayer();
            paint.setColor(mDrawColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(mPath , paint);
        }
    }

    public boolean isTouch(int x , int y){
        //判断是否在有效区域内

        Rect rect = mRegion.getBounds();
        boolean isTouch = mRegion.contains(x, y);

        if (!isTouch){
            if (x/10 == mRegion.getBounds().centerX()/10 && y/10 == mRegion.getBounds().centerY()/10){
                isTouch = true;
            }
        }

        return isTouch;
    }
}
