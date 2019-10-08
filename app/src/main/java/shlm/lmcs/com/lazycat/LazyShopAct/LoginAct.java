package shlm.lmcs.com.lazycat.LazyShopAct;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RelativeLayout;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.R;

@SuppressLint("NewApi")
public class LoginAct extends LazyCatAct {
    private RelativeLayout layout;
    private String MSG = "LoginAct.java[+]";
    /*登录按钮*/
    private RelativeLayout btnLoginin;
    private RelativeLayout btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTransparentBar();
        layout = findViewById(R.id.activity_login_main);
        layout.post(new Runnable() {
            @Override
            public void run() {
                Animator animation = Tools.createRoundAnimation(layout, 600);
                animation.start();
            }
        });
        /*登录的按钮*/
        btnLoginin = findViewById(R.id.activity_login_BtnLoginin);
        /*注册的按钮*/
        btnReg = findViewById(R.id.activity_login_BtnReg);
/*
        TextUnt.with(log_propaganda).setFontFile(getApplicationContext(), "hyxjtj");
        TextUnt.with(log_propagandaB).setFontFile(getApplicationContext(), "hyxjtj");
        TextUnt.with(log_context).setFontFile(getApplicationContext(), "mvboli");
*/
        TextUnt.with(this, R.id.activity_login_ico).setFontFile(getApplicationContext(), "canLogo");
        TextUnt.with(this, R.id.activity_login_context).setFontFile(getApplicationContext(),
                "mvboli");
        TextUnt.with(this, R.id.activity_login_Propaganda).setFontFile(getApplicationContext(),
                "hyxjtj");
        TextUnt.with(this, R.id.activity_login_Propaganda_b).setFontFile(getApplicationContext(),
                "hyxjtj");
        init();
    }

    private void init() {
        /*设置登录的按钮背景*/
        btnLoginin.setBackground(Tools.CreateDrawable(1, "#000000", "#ffffff", 50));
        /*设置登录的按钮背景*/
        btnReg.setBackground(Tools.CreateDrawable(1, "#08c299", "#08c299", 50));
    }

}
