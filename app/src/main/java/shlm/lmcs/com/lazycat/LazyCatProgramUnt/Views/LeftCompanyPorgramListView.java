package shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;

public class LeftCompanyPorgramListView extends ListView implements AbsListView.OnScrollListener {
    private Context mcontext;
    private View bottomview;//β���ļ�
    private View headview;//ͷ���ļ�
    private int totalItemcounts;
    private int lassvisible;//����
    private int firstvisible;//����
    private LoadListener loadListener;//�ӿڻص�
    private int bottomHeight;
    private int headHeight;
    private int Yload;
    boolean isLoading;
    private TextView headTv, headTime;
    private ProgressBar progressBar;


    public LeftCompanyPorgramListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext = context;
        init(context);
    }

    public LeftCompanyPorgramListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
        init(context);
    }

    public LeftCompanyPorgramListView(Context context) {
        super(context);
        mcontext = context;
        init(context);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
            totalItemCount) {
    }

    public void setHeadView(int v_id) {
        headview = LinearLayout.inflate(mcontext,v_id,null);
        //���²���
        headview.measure(0,0);
        headHeight = headview.getMeasuredHeight();//������²����ĸ߶�
        headview.setPadding(0,-headHeight,0,0);
        this.addHeaderView(headview);
    }

    public void setBottomview(int v_id){
        bottomview = LinearLayout.inflate(mcontext,v_id,null);
        //���²���
        bottomview.measure(0,0);
        bottomHeight = bottomview.getMeasuredHeight();//���»�ø߶�
        bottomview.setPadding(0,-bottomHeight,0,0);
        this.addFooterView(bottomview);
    }

    private void init(Context tContext) {
        this.setOnScrollListener(this);
    }

    //�ӿڻص�
    public interface LoadListener {
        void onLoad();//����

        void PullLoad();//����
    }


    //�������
    public void localComplete() {
        isLoading = false;
        bottomview.setPadding(0, -bottomHeight, 0, 0);
        headview.setPadding(0, -headHeight, 0, 0);
    }

    public void setInterface(LoadListener loadListener) {
        this.loadListener = loadListener;
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Yload = (int) ev.getY();//��λ
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();

                int paddingY = 0;
                try {
                    paddingY = headHeight + (moveY - Yload) / 2;
                } catch (Exception e) {
                    Log.i(Config.DEBUG,"listviewû��ͷ���ļ�");
                }
                if(paddingY < 0){
                    Toast.makeText(mcontext,"����ˢ��",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(mcontext,"�ɿ�ˢ��",Toast.LENGTH_SHORT).show();
                }
                if(headview != null){
                    headview.setPadding(0,paddingY,0,0);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
