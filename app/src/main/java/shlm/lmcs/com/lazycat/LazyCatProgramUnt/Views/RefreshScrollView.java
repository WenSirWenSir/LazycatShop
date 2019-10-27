package shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.ImageCache;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;

/**
 * 滑动监听的View
 */
@SuppressLint("LongLogTag")
public class RefreshScrollView extends ScrollView {
    private String MSG = "RefreshScrollView.java[+]";
    private int ViewWidth;//宽度
    private LinearLayout layout;
    private int _scrollY;//滑动的距离
    public static int CAN_REFRESH = 0;
    public static int ING_REFRESH = 1;
    public static int RUNNOW_REFRESH = 2;
    private ImageCache imageCache;
    private int _downY;//记录距离
    private int _downH;//记录高度
    private int lr_downCount;/*左右滑动的距离*/
    private int lr_downX;/*左右滑动记录点下的位置*/
    private boolean b_down;
    private int viewHeight;//可以刷新的高度
    private boolean onGetHeadimg = false;
    private int total_distance;
    private Handler handler;
    public boolean inLoadMessage = false;//由调用的地方手动调节 如果外部调用了加载 就必须在外部设置已经加载 不能再次加载
    public boolean onStopHandle = false;/*由外部给出这个值 来判断外部是否已经在处理滑动停止事件*/
    private RefreshScrollViewListener _RefreshScrollViewListener;//滑动监听对象
    private int RefershLog = 0;
    private int RefershImg = 0;
    private Boolean _CanListenMoveLeft = null;
    private Boolean _CanListenMoveRight = null;
    private String HeadMessageimg;/*头部的地址*/
    private String HeadMessageOnclick;/*头部的点击事件*/
    private Boolean isLoadMsgDone;/*标记加载广告图片是否完毕*/


    public RefreshScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshScrollView(Context context) {
        super(context);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewWidth = getWidth();
    }

    private void init() {
        imageCache = new ImageCache(getContext());
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    /**
     * 设置头部View
     *
     * @param view 必须已经存在于ScrollView中
     */
    public void SetHeadView(String _headMessageimg, String _headMessageOnclick, LinearLayout
            view, int viewHeight, int refershLogid, int refreshImgid) {
        isLoadMsgDone = false;
        this.RefershLog = refershLogid;
        this.RefershImg = refreshImgid;
        this.HeadMessageimg = _headMessageimg;
        this.HeadMessageOnclick = _headMessageOnclick;
        layout = view;//加载文件
        if (layout == null) {
            Log.e(Config.DEBUG, "RefreshScrollView.java[+]view为空");
        }
        LinearLayout.LayoutParams _params = null;
        try {
            _params = (LinearLayout.LayoutParams) layout.getLayoutParams();
        } catch (Exception e) {
            Log.e(Config.DEBUG, "RefreshScrollView.java[+]" + e.getMessage());
        }
        this.viewHeight = viewHeight;
        if (_params == null) {
            Log.e(Config.DEBUG, "RefreshScrollView.java[+]_params为空");
        } else {
            _params.width = ViewWidth;
            _params.height = 0;
            _params.gravity = Gravity.CENTER;
        }

    }

    public void SetLinstener(RefreshScrollViewListener i) {
        if (i != null) {
            this._RefreshScrollViewListener = i;
        } else {
            Log.e(Config.DEBUG, "RefreshScrollView.java[+]监听事件对象为空");
        }
    }

    /**
     * 停止刷新
     */
    public void stopRefresh() {

        LinearLayout.LayoutParams params = null;
        try {
            params = (LinearLayout.LayoutParams) this.layout.getLayoutParams();
            if (params != null) {
                params.width = ViewWidth;
                params.height = 0;
                this.layout.setLayoutParams(params);
                inLoadMessage = false;//可以加载外部信息
            }
        } catch (Exception e) {
            Log.e(Config.DEBUG, "RefreshScrollView.java[+]" + e.getMessage());
        }

    }


    /**
     * 关键代码
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            /*判断onStophandle*/
            if (onStopHandle) {
                //可以回调滑动停止事件
                onStopHandle = false;
            }
            _downY = (int) ev.getY();
            lr_downX = (int) ev.getX();
            total_distance = 0;
            //按下鼠标
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            /*记录X轴的坐标*/
            lr_downCount = (int) ((ev.getX() - lr_downX) * 1 / 2);
            Log.i(MSG, "滑动的距离" + lr_downCount);

            //鼠标移动
            if (_scrollY == 0) {
                //可以进行下拉刷新  在顶部位置
                Log.i(Config.DEBUG, "RefreshScrollView.java[+]在顶部");
                if (ev.getY() - _downY > 0) {
                    //向下滑动  可以刷新
                    int downRange = (int) ((ev.getY() - _downY * 1) / 2);
                    /*计算出来滑动距离 就进行回调*/
                    if (_RefreshScrollViewListener != null) {
                        _RefreshScrollViewListener.onScrollDistance(downRange);
                    }
                    b_down = false;//刚刚开始滑动 松手不能刷新
                    if (layout != null) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout
                                .getLayoutParams();
                        final ImageView head_img = (ImageView) layout.findViewById(this.RefershImg);
                        if (head_img != null) {
                            /*这边要加载网络图片*/
                            if (!TextUtils.isEmpty(HeadMessageimg) && !isLoadMsgDone) {
                                Glide.with(getContext()).load(HeadMessageimg).into(head_img);
                                isLoadMsgDone = true;/*加载完毕*/
                            } else {
                                Log.i(MSG, "已经加载图片完毕");
                            }

                        } else {
                            Log.e(MSG, "没有获取到加载的图片的控件");
                        }
                        params.width = ViewWidth;
                        params.height = downRange;
                        layout.setLayoutParams(params);
                    }
                    if (downRange >= viewHeight) {
                        if (_RefreshScrollViewListener != null) {
                            _RefreshScrollViewListener.onState(RefreshScrollView.CAN_REFRESH);

                        }
                        if (this.RefershLog == 0) {
                            Log.e(Config.DEBUG, "RefreshScrollView.java[+]你必须要设置一个加载的等待ID");
                        } else {
                            ProgressBar progressBar = layout.findViewById(this.RefershLog);
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        b_down = true;
                    } else {
                        if (this.RefershLog == 0) {
                            Log.e(Config.DEBUG, "RefreshScrollView.java[+]你必须要设置一个加载的等待ID");

                        } else {
                            ProgressBar progressBar = layout.findViewById(this.RefershLog);
                            progressBar.setVisibility(View.GONE);
                        }
                        if (_RefreshScrollViewListener != null) {
                            _RefreshScrollViewListener.onState(RefreshScrollView.ING_REFRESH);

                        }

                        b_down = false;
                    }
                    total_distance = downRange;//设置拉动的距离
                } else {
                    b_down = false;
                    return super.dispatchTouchEvent(ev);
                }
            } else {
                b_down = false;
                return super.dispatchTouchEvent(ev);
                //不在顶部位置  就不进行操作
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //鼠标抬起
            isLoadMsgDone = false;/*鼠标一抬起 就代表可以加载控件的广告了*/
            if (b_down) {
                //可以刷新
                if (inLoadMessage == false) {
                    if (total_distance >= viewHeight * 1) {
                        //下拉大于2倍就开始加载广告
                        if (_RefreshScrollViewListener != null) {
                            _RefreshScrollViewListener.onloadMessage();
                        }
                    }
                } else {
                    Log.i(Config.DEBUG, "RefreshScrollView.java[+]外部已经开始打开广告窗口");
                }
                if (layout != null) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout
                            .getLayoutParams();
                    params.width = ViewWidth;
                    params.height = viewHeight;
                    layout.setLayoutParams(params);
                    if (_RefreshScrollViewListener != null) {
                        _RefreshScrollViewListener.onState(RefreshScrollView.RUNNOW_REFRESH);
                        //可以加载信息了
                        _RefreshScrollViewListener.onLoadMore();
                    }
                }

            } else {
                //不可以刷新 就停止
                stopRefresh();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        _scrollY = t;//滑动的距离
        Log.i(Config.DEBUG, "RefreshScrooView.java[+]滑动的距离为:" + _scrollY);
        if (t + this.getMeasuredHeight() == this.getChildAt(0).getMeasuredHeight()) {
            Log.i(Config.DEBUG, "RefreshScrollView.java[+]滑动到底部");
            if (_RefreshScrollViewListener != null) {
                _RefreshScrollViewListener.onLoadBottom();//滑动到底部
            }
        }
    }


    /*是否可以调用左向滑动的监听器*/
    public void setCanListenMoveLeft(boolean b) {
        this._CanListenMoveLeft = b;
    }

    /*是否可以调用左向滑动的监听器*/
    public void setCanListenMoveRight(boolean b) {
        this._CanListenMoveRight = b;
    }


    /**
     * 监听
     */

    public interface RefreshScrollViewListener {
        void onRefresh();//正在刷新

        void onRefreshDone();//刷新完成

        void onStopRefresh();//停止刷新

        void onState(int _static);//状态

        void onLoadMore();//加载信息

        void onLoadBottom();//在底部

        void onScrollStop();//滑动停止

        void onloadMessage();//加载广告

        void onScrollDistance(int distance);//滑动的距离

        void onScrollToleft(int _moveCount);/*向左滑动*/

        void onScrollToRight(int _moveCount);/*向右滑动*/
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int
            scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean
            isTouchEvent) {
        if (deltaY <= 2 && deltaY >= -2 && !isTouchEvent) {
            if (onStopHandle) {
                //表示外部已经在处理 不用重复提交
            } else {
                if (_RefreshScrollViewListener != null) {
                    _RefreshScrollViewListener.onScrollStop();
                }
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


}
