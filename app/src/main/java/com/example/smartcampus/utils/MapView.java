package com.example.smartcampus.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

import com.example.smartcampus.R;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MapView extends View {

    /**
     * 线程池，用于加载xml文件
     */
    private ExecutorService mThreadPool;

    /**
     * 地图画笔
     */
    private Paint mPaint;

    /**
     * 地图资源id
     */
    private int mMapResId = -1;

    /**
     * 解析得到省份列表
     */
    private List<ProvinceItem> mItemList;

    /**
     * 整个地图的最大矩形边界
     */
    private RectF mMaxRect;


    /**
     * 当前选择的省份的item
     */
    private ProvinceItem mSelectItem;

    /**
     * 地图缩放比例
     */
    private float mScale = 1f;

    /**
     * 每个区域的中心坐标
     */
    private Map<String, RectF> rectFMap = new HashMap<>();


    public MapView(Context context) {
        super(context);

    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initMyOnTouchEvent();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置抗锯齿
        initThreadPool();//初始化线程池
        getMapResource(context, attrs, defStyleAttr);//解析自定义属性
    }

    /**
     * 初始化线程池
     */
    private void initThreadPool() {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setPriority(Thread.MAX_PRIORITY);//设置优先级
                return thread;
            }
        };
        Log.d("aaaaaaaaaaaaaaaaaaaaa1", "executeLoad: ");
        mThreadPool = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.MINUTES,
            new LinkedBlockingDeque<Runnable>(10), threadFactory,
            new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 解析自定义属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void getMapResource(Context context, AttributeSet attrs, int defStyleAttr) {
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MapView,
            defStyleAttr, 0);
        int resId = typedArray.getResourceId(R.styleable.MapView_map, -1);
        typedArray.recycle();
        setMapResId(resId);
    }

    private Map<String, Integer> colorMap;

    public void setColors(Map<String, Integer> colorMap) {
        this.colorMap = colorMap;
    }

    /**
     * 设置地图资源
     *
     * @param resId
     */
    public void setMapResId(int resId) {
        mMapResId = resId;
        executeLoad();
    }

    /**
     * 执行加载
     */
    private void executeLoad() {
        if (mMapResId <= 0) {
            return;
        }
        Log.d("aaaaaaaaaaaaaaaaaaaaa2", "executeLoad: ");
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                //获取xml文件输入流
                InputStream inputStream = getResources().openRawResource(mMapResId);

                //创建解析实例
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                try {
                    builder = factory.newDocumentBuilder();
                    //解析输入流得到Document实例
                    Document doc = builder.parse(inputStream);
                    //获取根节点，即vector节点
                    Element rootElement = doc.getDocumentElement();
                    //获取所有的path节点
                    NodeList items = rootElement.getElementsByTagName("path");

                    //一下四个变量用来保存地图四个边界，用于确定缩放比例（适配屏幕）
                    float left = -1;
                    float right = -1;
                    float top = -1;
                    float bottom = -1;

                    //解析path节点
                    List<ProvinceItem> provinceItemList = new ArrayList<>();
                    for (int i = 0; i < items.getLength(); i++) {
                        Element element = (Element) items.item(i);
                        //获取pathData内容
                        String pathData = element.getAttribute("android:pathData");
                        //将pathData转换为path
                        Path path = PathParser.createPathFromPathData(pathData);

                        String name = element.getAttribute("android:name");

                        String fillColor = element.getAttribute("android:fillColor");

                        //分装成ProvenceItem对象
                        ProvinceItem provinceItem = new ProvinceItem(path, name);

                        if (colorMap != null) {
                            Log.d("setDrawColor" , "--------"+name);
                            provinceItem.setDrawColor(colorMap.get(name));
                        } else {
                            provinceItem.setDrawColor(Color.parseColor(fillColor));
                        }

                        RectF rectF = new RectF();
                        //计算当前path区域的矩形边界
                        path.computeBounds(rectF, true);
                        //判断边界，最终获得的就是整个地图的最大矩形边界
                        left = left < 0 ? rectF.left : Math.min(left, rectF.left);
                        right = Math.max(right, rectF.right);
                        top = top < 0 ? rectF.top : Math.min(top, rectF.top);
                        bottom = Math.max(bottom, rectF.bottom);

                        provinceItemList.add(provinceItem);
                        rectFMap.put(name, rectF);
                    }

                    //解析完成，保存节点列表和最大边界
                    mItemList = provinceItemList;
                    mMaxRect = new RectF(left, top, right, bottom);
                    //通知重新布局和绘制
                    post(new Runnable() {
                        @Override
                        public void run() {
                            requestLayout();
                            invalidate();
                        }
                    });

                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 根据名称获取坐标
     *
     * @return
     */
    public RectF getRectF(String name) {
        return rectFMap.get(name);
    }

    /**
     * 手动发放点击事件
     * @param x
     * @param y
     * @return
     */
    public boolean setHandleTouch(int x , int y) {
        return handleTouch(x,y, null, null);
    }

    /**
     * 根据名称获取颜色
     * @param name
     * @return
     */
    public int getColor(String name) {
        Integer integer = colorMap.get(name);
        if (integer != null){
            return integer;
        }
        return 0;
    }

    /**
     * 获取缩放比例
     * @return
     */
    public float getMScale() {
        return mScale;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mThreadPool != null) {
            //释放线程池
            mThreadPool.shutdown();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (mMaxRect != null) {
            //获取缩放比例
            double mapWidth = mMaxRect.width();
            double mapHeight = mMaxRect.height();
            mScale = Math.min((float) (width / mapWidth), (float) (height / mapHeight));
        }

        //应用测量数据
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mItemList != null) {
            //使地图从画布左上角开始绘制（图片本身可能存在一定边距）
            canvas.translate(-mMaxRect.left, -mMaxRect.top);
            //设置画布缩放，以(-mMaxRect.left , -mMaxRect.top)为基准进行缩放
            //因为当前该点对应屏幕左上角（0,0）点
            canvas.scale(mScale, mScale, mMaxRect.left, mMaxRect.top);
            //绘制所有省份区域，并设置是否选中状态
            for (ProvinceItem provinceItem : mItemList) {
                provinceItem.drawItem(canvas, mPaint, mSelectItem == provinceItem);
            }
        }
    }

    private MotionEvent myEvent;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//         将事件分发给所有的区块，如果事件未被消费，则调用View的onTouchEvent，这里会默认范围false
        myEvent = event;
        return super.onTouchEvent(event);
    }

    private MyOnTouchEvent myOnTouchEvent;

    public interface MyOnTouchEvent {

        void onTouchEvent(MotionEvent event);
    }

    private void initMyOnTouchEvent() {
        myOnTouchEvent = new MyOnTouchEvent() {
            @Override
            public void onTouchEvent(MotionEvent event) {
                if (myEvent != null) {
                    handleTouch((int) (myEvent.getX() / mScale + mMaxRect.left),
                        (int) (myEvent.getY() / mScale + mMaxRect.top), myEvent, event);
                }
            }
        };
    }


    public MyOnTouchEvent getMyOnTouchEvent() {
        return myOnTouchEvent;
    }

    private int down = 0;

    //派发事件
    private boolean handleTouch(int x, int y, MotionEvent myEvent, MotionEvent event) {
        if (mItemList == null) {
            return false;
        }
        boolean isTouch = false;

        ProvinceItem selectItem = null;
        for (ProvinceItem provinceItem : mItemList) {
            //依次派发事件
            if (provinceItem.isTouch(x, y)) {
                //选中省份区块
                selectItem = provinceItem;
                isTouch = true;

                if (myEvent != null && event != null) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        down = 0;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (down == 1) {
                            if (onMapViewClickListener != null) {
                                onMapViewClickListener.onClick(provinceItem.getName());
                            }
                        }
                    } else if (myEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        down = 1;
                    }
                }
                break;
            }
        }

        if (selectItem != null && selectItem != mSelectItem) {
            mSelectItem = selectItem;
            //通知重绘
            postInvalidate();
        }

        return isTouch;
    }

    private OnMapViewClickListener onMapViewClickListener;

    public interface OnMapViewClickListener {

        void onClick(String name);
    }

    public void setOnMapViewClickListener(
        OnMapViewClickListener onMapViewClickListener) {
        this.onMapViewClickListener = onMapViewClickListener;
    }

}
