package shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;


/**
 * ����������ʱ��ؼ���
 */
public class ScrollViewBiggerPhoto extends ScrollView {
    private View img_body;
    private float myFirstMove = 0;
    private DisplayMetrics metrics;//

    public ScrollViewBiggerPhoto(Context context) {
        super(context);
    }

    public ScrollViewBiggerPhoto(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewBiggerPhoto(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageHead(View _imgbody, DisplayMetrics _metrics) {
        this.img_body = _imgbody;
        this.metrics = _metrics;//��Ļ����
        Log.e(Config.DEBUG, "ScrollViewBiggerPhoto.java[+]��Ļ���:" + this.metrics.widthPixels);
        Log.e(Config.DEBUG, "ScrollViewBiggerPhoto.java[+]��Ļ�߶�:" + this.metrics.heightPixels);
        /*���ÿؼ��ĸ߶ȺͿ��*/
        ViewGroup.LayoutParams params = this.img_body.getLayoutParams();
        params.width = this.metrics.widthPixels;
        params.height = this.metrics.heightPixels * 8 / 16;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewGroup.LayoutParams params = null;
        if (this.img_body != null) {
            params = this.img_body.getLayoutParams();
        } else {
            Log.e(Config.DEBUG, "ScrollViewBiggerPhoto.java[+]����������һ���ؼ�");

        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                ClearImage();
                break;
            case MotionEvent.ACTION_DOWN:
                myFirstMove = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                /*�ж��Ƿ��ڶ���*/
                if (getScrollY() == 0) {
                    Log.e(Config.DEBUG, "�ڶ���");
                    /*��¼λ��*/
                    /*�����������*/
                    int distance = (int) ((ev.getY() - myFirstMove) * 0.6);
                    if (distance < 0) {
                        /*���ܷŴ�*/
                        break;
                    }
                    /*��ʼ����Ŵ�*/
                    if (params != null) {
                        params.width = this.metrics.widthPixels + distance;
                        params.height = (this.metrics.heightPixels + distance) * 8 / 16;
                        this.img_body.setLayoutParams(params);
                    }

                }

                break;
        }


        return super.dispatchTouchEvent(ev);
    }


    /**
     * ���ImageView�ķŴ�Ч��
     */
    private void ClearImage() {
        ViewGroup.LayoutParams params = this.img_body.getLayoutParams();
        params.width = this.metrics.widthPixels;
        params.height = this.metrics.heightPixels * 8 / 16;
        this.img_body.setLayoutParams(params);
    }
}
