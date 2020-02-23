package shlm.lmcs.com.lazycat.LazyShopAct;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.os.Bundle;import android.os.Handler;import android.os.Message;import android.view.View;import android.view.animation.AlphaAnimation;import android.widget.ImageView;import android.widget.TextView;import com.bumptech.glide.Glide;import com.bumptech.glide.load.engine.DiskCacheStrategy;import java.util.Timer;import java.util.TimerTask;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.R;/** * 加载宣传广告 */public class PromotionAct extends LazyCatAct {    private ImageView promotion_img;/*图片的容器*/    private TextView btn_close;/*关闭广告*/    private Timer timer;    private int count = 4;/*2秒内关闭广告*/    private final int HANDLER_MSG = 1;    private Handler handler;    private AlertDialog alertDialog;    @SuppressLint("HandlerLeak")    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setTransparentBar();        setContentView(R.layout.activity_promotion);        promotion_img = findViewById(R.id.activity_promotionImageview);        btn_close = findViewById(R.id.activity_promotionBtnColose);        handler = new Handler() {            @Override            public void handleMessage(Message msg) {                switch (msg.what) {                    case HANDLER_MSG:                        if (msg.obj.toString().trim().equals("0")) {                            LazyCatActStartActivity(MainAct.class, true);                            timer.cancel();                        }                        break;                }                super.handleMessage(msg);            }        };        init();    }    @SuppressLint("NewApi")    private void init() {        /*测试加载*/        Glide.with(this).load("http://120.79.63.36/Photos/ConfigMain/shzService/Welcome" +                "/welcome_msg.png").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache                (false).into(promotion_img);        //*逐渐显示*/*/        AlphaAnimation alphaAnimation = Tools.createOnalpha(500, true);        promotion_img.startAnimation(alphaAnimation);        /*设置关闭按钮的颜色*/        btn_close.setBackground(Tools.CreateDrawable(1, "#000000", "#000000", 50));        btn_close.setAlpha(0.5f);        btn_close.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                timer.cancel();                LazyCatActStartActivity(MainAct.class, true);                finish();            }        });        /*开启线程*/        timer = new Timer();        timer.schedule(new TimerTask() {            @Override            public void run() {                Message msg = new Message();                msg.what = HANDLER_MSG;                msg.obj = count--;                if (count < 0) {                    timer.cancel();                }                handler.sendMessage(msg);            }        }, 0, 1000);    }}