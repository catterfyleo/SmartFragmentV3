package lib.smart.fragmentv4.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import lib.smart.fragment.R;


/**
 * Created by Augustine on 2018/3/20.
 * <p>
 * email:nice_ohoh@163.com
 */

public class SwipeBackLayout extends FrameLayout {

    private View contentView;

    private Context context;

    private View shadowView;

    //界面左侧阴影
    private int shadowViewWidth = 25;

    //最小的触发范围
    private int minToucheSlide = 75;

    private int viewWidth = 0;

    private OnSwipeListener swipeListener;

    //页面越界值，不要超过0.5 不然削弱效果
    private float pageOverBorderValue = 0.25f;

    //是否触摸任意位置开始侧滑关闭
    private boolean isFullTouchSlide = false;

    //是否开启侧滑关闭
    private boolean isOpenTouchSlide = true;

    public SwipeBackLayout(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        if(isFullTouchSlide){
            minToucheSlide = viewWidth;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(1);
    }

    private void initView(){
        setBackgroundColor(Color.parseColor("#00000000"));
        shadowView = new View(context);
        LayoutParams shadowParams =
                new LayoutParams(shadowViewWidth, LayoutParams.MATCH_PARENT);
        shadowView.setLayoutParams(shadowParams);
        shadowView.setX(-shadowViewWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            shadowView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_shadow));
        }
        addView(shadowView);
    }


    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
        addView(contentView);
    }

    //复位
    public void resetContentView(){
        int contentStartX = (int)contentView.getX();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(contentStartX,0);
        valueAnimator.setDuration(50);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                contentView.setX(value);
                shadowView.setX(value -shadowViewWidth);
                if(swipeListener != null){
                    swipeListener.onSwipeLayoutChange(value);
                }
                if(value == 0){
                    if(swipeListener != null){
                        swipeListener.onSwipeLayoutReset();
                    }
                }
            }
        });
        valueAnimator.start();
    }

    //关闭
    public void closeContentView(){
        int contentStartX = (int)contentView.getX();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(contentStartX,viewWidth + shadowViewWidth);
        valueAnimator.setDuration(50);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                contentView.setX(value);
                shadowView.setX(value -shadowViewWidth);
                if(swipeListener != null){
                    swipeListener.onSwipeLayoutChange(value);
                }
                if(value == viewWidth + shadowViewWidth){
                    if(swipeListener != null){
                        swipeListener.onSwipeLayoutClosed();
                    }
                }
            }
        });
        valueAnimator.start();
    }

    public void setSwipeListener(OnSwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    public float getPageOverBorderValue() {
        return pageOverBorderValue;
    }

    public void setPageOverBorderValue(float pageOverBorderValue) {
        this.pageOverBorderValue = pageOverBorderValue;
    }

    public boolean isFullTouchSlide() {
        return isFullTouchSlide;
    }

    public void setFullTouchSlide(boolean fullTouchSlide) {
        isFullTouchSlide = fullTouchSlide;
        if(isFullTouchSlide){
            minToucheSlide = viewWidth;
        }else{
            minToucheSlide = 50;
        }
    }

    public boolean isOpenTouchSlide() {
        return isOpenTouchSlide;
    }

    public void setOpenTouchSlide(boolean openTouchSlide) {
        isOpenTouchSlide = openTouchSlide;
    }

    public OnSwipeListener getSwipeListener() {
        return swipeListener;
    }

    public void setContentViewX(int x){

        float progress = (float) x / (float)viewWidth;

        int flagX = (int) -(viewWidth * pageOverBorderValue);

        int value = (int) (flagX - (progress * flagX));

        contentView.setX(value);
        shadowView.setX(value -shadowViewWidth);

        if(x >= viewWidth){
            contentView.setX(0);
            shadowView.setX(0 -shadowViewWidth);
        }
    }

    public int getContentX(){
        return (int) contentView.getX();
    }

    private int touchFlag = -1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isOpenTouchSlide){
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                try {
                    actionIndex = ev.getActionIndex();
                    pointerId = ev.getPointerId(actionIndex);
                    downY = (int) ev.getY(pointerId);
                    downX = (int) ev.getX(pointerId);
                    if(downX < minToucheSlide){
                        isSlide = true;
                    }
                    touchFlag = -1;
                }catch (IllegalArgumentException i){}
                break;
            case MotionEvent.ACTION_MOVE:
                try {
                    moveY = (int) ev.getY(pointerId);
                    moveX = (int) ev.getX(pointerId);
                    if(Math.abs(moveY - downY) > 10 || Math.abs(moveX - downX) > 10){
                        if(Math.abs(moveX - downX) + 5 > Math.abs(moveY - downY) && isSlide){
                            if(touchFlag == -1){
                                touchFlag = 1;
                            }
                        }else{
                            touchFlag = 2;
                        }
                    }
                }catch (IllegalArgumentException i){

                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(touchFlag == 1){
            return true;
        }
        if(touchFlag == 2){
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    private int actionIndex = 0;
    private int pointerId = 0;

    private int downX = 0;
    private int downY = 0;

    private int moveX = 0;
    private int moveY = 0;

    private int maxMoveX = 0;

    private boolean isSlide = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                if(moveX >= downX && isSlide){
                    if(maxMoveX < moveX){
                        maxMoveX = moveX;
                    }
                    int distance = moveX - downX;
                    contentView.setX(distance);
                    shadowView.setX(distance - shadowViewWidth);
                    if(swipeListener != null){
                        swipeListener.onSwipeLayoutChange(distance);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(contentView.getX() != 0 && moveX < maxMoveX){
                    resetContentView();
                } else if(contentView.getX() != 0 && contentView.getX() <= viewWidth/4 ){
                    resetContentView();
                }else if(contentView.getX() != 0 && contentView.getX() >= viewWidth/4){
                    closeContentView();
                }
                clear();
                break;
        }
        return true;
    }

    private void clear(){
        downX = 0;
        downY = 0;
        moveY = 0;
        moveX = 0;
        maxMoveX = 0;
        actionIndex = 0;
        pointerId = 0;
        isSlide = false;
    }


    public interface OnSwipeListener{

        public void onSwipeLayoutChange(int x);

        public void onSwipeLayoutClosed();

        public void onSwipeLayoutReset();

    }
}
