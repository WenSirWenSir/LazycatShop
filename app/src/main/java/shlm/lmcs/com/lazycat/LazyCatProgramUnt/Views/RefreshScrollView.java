package shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.ImageCache;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;

/**
 * ����������View
 */
@SuppressLint("LongLogTag")
public class RefreshScrollView extends ScrollView {
    private int ViewWidth;//���
    private LinearLayout layout;
    private int _scrollY;//�����ľ���
    public static int CAN_REFRESH = 0;
    public static int ING_REFRESH = 1;
    public static int RUNNOW_REFRESH = 2;
    private ImageCache imageCache = new ImageCache();
    private int _downY;//��¼����
    private int _downH;//��¼�߶�
    private boolean b_down;
    private int viewHeight;//����ˢ�µĸ߶�
    private boolean onGetHeadimg = false;
    private int total_distance;
    private Handler handler;
    public boolean inLoadMessage = false;//�ɵ��õĵط��ֶ����� ����ⲿ�����˼��� �ͱ������ⲿ�����Ѿ����� �����ٴμ���
    public boolean onStopHandle = false;/*���ⲿ�������ֵ ���ж��ⲿ�Ƿ��Ѿ��ڴ�����ֹͣ�¼�*/
    private RefreshScrollViewListener _RefreshScrollViewListener;//������������
    private int RefershLog = 0;
    private int RefershImg = 0;

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
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    /**
     * ����ͷ��View
     *
     * @param view �����Ѿ�������ScrollView��
     */
    public void SetHeadView(LinearLayout view, int viewHeight, int refershLogid, int refreshImgid) {
        this.RefershLog = refershLogid;
        this.RefershImg = refreshImgid;
        layout = view;//�����ļ�
        if (layout == null) {
            Log.e(Config.DEBUG, "RefreshScrollView.java[+]viewΪ��");
        }
        LinearLayout.LayoutParams _params = null;
        try {
            _params = (LinearLayout.LayoutParams) layout.getLayoutParams();
        } catch (Exception e) {
            Log.e(Config.DEBUG, "RefreshScrollView.java[+]" + e.getMessage());
        }
        this.viewHeight = viewHeight;
        if (_params == null) {
            Log.e(Config.DEBUG, "RefreshScrollView.java[+]_paramsΪ��");
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
            Log.e(Config.DEBUG, "RefreshScrollView.java[+]�����¼�����Ϊ��");
        }
    }

    /**
     * ֹͣˢ��
     */
    public void stopRefresh() {

        LinearLayout.LayoutParams params = null;
        try {
            params = (LinearLayout.LayoutParams) this.layout.getLayoutParams();
        } catch (Exception e) {
            Log.e(Config.DEBUG, "RefreshScrollView.java[+]" + e.getMessage());
        }
        if (params != null) {
            params.width = ViewWidth;
            params.height = 0;
            this.layout.setLayoutParams(params);
            inLoadMessage = false;//���Լ����ⲿ��Ϣ
        }
    }


    /**
     * �ؼ�����
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            /*�ж�onStophandle*/
            if (onStopHandle) {
                //���Իص�����ֹͣ�¼�
                onStopHandle = false;
            }
            _downY = (int) ev.getY();
            total_distance = 0;
            //�������
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            //����ƶ�
            if (_scrollY == 0) {
                //���Խ�������ˢ��  �ڶ���λ��
                Log.i(Config.DEBUG, "RefreshScrollView.java[+]�ڶ���");
                if (ev.getY() - _downY > 0) {
                    //���»���  ����ˢ��
                    int downRange = (int) ((ev.getY() - _downY * 1) / 2);
                    /*��������������� �ͽ��лص�*/
                    if (_RefreshScrollViewListener != null) {
                        _RefreshScrollViewListener.onScrollDistance(downRange);
                    }
                    b_down = false;//�ոտ�ʼ���� ���ֲ���ˢ��
                    if (layout != null) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout
                                .getLayoutParams();
                        final ImageView head_img = (ImageView) layout.findViewById(this.RefershImg);
                        /*���Ҫ��������ͼƬ*/
                        if (imageCache.getImage("225522") != null || onGetHeadimg == true) {
                            Log.i(Config.DEBUG, "�����д���ͼƬ�����Ѿ��ڿ�ʼ����");
                            head_img.setImageBitmap(imageCache.getImage("225522"));
                        } else {
                            Net.doGetimg("/photos/225522.png", new ProgramInterface.doGetImg() {
                                @Override
                                public void onSucess(Bitmap bitmap) {
                                    //�ɹ� ��ȡ������ͼƬ
                                    Log.i(Config.DEBUG, "��ȡ��ͼƬ��Ϣ��");
                                    imageCache.saveImage("225522", bitmap);
                                    head_img.setImageBitmap(imageCache.getImage("225522"));
                                }

                                @Override
                                public void onFain() {

                                }
                            });

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
                            Log.e(Config.DEBUG, "RefreshScrollView.java[+]�����Ҫ����һ�����صĵȴ�ID");
                        } else {
                            ProgressBar progressBar = layout.findViewById(this.RefershLog);
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        b_down = true;
                    } else {
                        if (this.RefershLog == 0) {
                            Log.e(Config.DEBUG, "RefreshScrollView.java[+]�����Ҫ����һ�����صĵȴ�ID");

                        } else {
                            ProgressBar progressBar = layout.findViewById(this.RefershLog);
                            progressBar.setVisibility(View.GONE);
                        }
                        if (_RefreshScrollViewListener != null) {
                            _RefreshScrollViewListener.onState(RefreshScrollView.ING_REFRESH);

                        }

                        b_down = false;
                    }
                    total_distance = downRange;//���������ľ���
                } else {
                    b_down = false;
                    return super.dispatchTouchEvent(ev);
                }
            } else {
                b_down = false;
                return super.dispatchTouchEvent(ev);
                //���ڶ���λ��  �Ͳ����в���
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //���̧��
            if (b_down) {
                //����ˢ��
                if (inLoadMessage == false) {
                    if (total_distance >= viewHeight * 2) {
                        //��������2���Ϳ�ʼ���ع��
                        if (_RefreshScrollViewListener != null) {
                            _RefreshScrollViewListener.onloadMessage();
                        }
                    }
                } else {
                    Log.i(Config.DEBUG, "RefreshScrollView.java[+]�ⲿ�Ѿ���ʼ�򿪹�洰��");
                }
                if (layout != null) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout
                            .getLayoutParams();
                    params.width = ViewWidth;
                    params.height = viewHeight;
                    layout.setLayoutParams(params);
                    if (_RefreshScrollViewListener != null) {
                        _RefreshScrollViewListener.onState(RefreshScrollView.RUNNOW_REFRESH);
                        //���Լ�����Ϣ��
                        _RefreshScrollViewListener.onLoadMore();
                    }
                }

            } else {
                //������ˢ�� ��ֹͣ
                stopRefresh();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        _scrollY = t;//�����ľ���
        Log.i(Config.DEBUG, "RefreshScrooView.java[+]�����ľ���Ϊ:" + _scrollY);
        if (t + this.getMeasuredHeight() == this.getChildAt(0).getMeasuredHeight()) {
            Log.i(Config.DEBUG, "RefreshScrollView.java[+]�������ײ�");
            if (_RefreshScrollViewListener != null) {
                _RefreshScrollViewListener.onLoadBottom();//�������ײ�
            }
        }
    }


    /**
     * ����
     */

    public interface RefreshScrollViewListener {
        void onRefresh();//����ˢ��

        void onRefreshDone();//ˢ�����

        void onStopRefresh();//ֹͣˢ��

        void onState(int _static);//״̬

        void onLoadMore();//������Ϣ

        void onLoadBottom();//�ڵײ�

        void onScrollStop();//����ֹͣ

        void onloadMessage();//���ع��

        void onScrollDistance(int distance);//�����ľ���
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int
            scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean
            isTouchEvent) {
        if (deltaY <= 2 && deltaY >= -2 && !isTouchEvent) {
            if (onStopHandle) {
                //��ʾ�ⲿ�Ѿ��ڴ��� �����ظ��ύ
            } else {
                _RefreshScrollViewListener.onScrollStop();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
}
