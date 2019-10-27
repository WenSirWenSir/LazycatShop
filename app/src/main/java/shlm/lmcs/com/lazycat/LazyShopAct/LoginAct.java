package shlm.lmcs.com.lazycat.LazyShopAct;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.EditTextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;
import shlm.lmcs.com.lazycat.R;

@SuppressLint("NewApi")
public class LoginAct extends LazyCatAct {
    private RelativeLayout layout;
    private String MSG = "LoginAct.java[+]";
    private LinearLayout LLSelectBody;
    private RelativeLayout RLLoginBody;
    /**
     * 消失和显示的动画
     */
    private AlphaAnimation showAnimation;
    private AlphaAnimation clearAnimation;


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
        TextUnt.with(this, R.id.activity_login_ico).setFontFile(getApplicationContext(), "canLogo");
        TextUnt.with(this, R.id.activity_login_context).setFontFile(getApplicationContext(),
                "mvboli");
        /*登录的输入框*/
        RLLoginBody = findViewById(R.id.activity_login_Loginbody);
        showAnimation = Tools.createOnalpha(1000, true);
        clearAnimation = Tools.clearOnalpha(1000, true);
        init();
    }

    @SuppressLint("ResourceType")
    private void init() {
        /*设置有账户的登录的背景*/
        findViewById(R.id.activity_login_btnTologin).setBackground(Tools.CreateDrawable(1,
                getResources().getString(R.color.ThemeColor), getResources().getString(R.color
                        .ThemeColor), 50));
        /*用户请求加入仓库网络*/
        findViewById(R.id.activity_login_btnTojoin).setBackground(Tools.CreateDrawable(1,
                getResources().getString(R.color.ThemeColor), getResources().getString(R.color
                        .ThemeColor), 50));

        Listener();
    }

    private void Listener() {
        /**
         *加入仓库网络的按钮
         */
        findViewById(R.id.activity_login_btnTojoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LazyCatActStartActivity(MakebusinessAct.class, true);
            }
        });


        /**
         * 输入账户的监听
         */
        findViewById(R.id.activity_login_EtAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Et = (EditText) v;
                if (Et.getText().toString().equals(getResources().getString(R.string
                        .loginAccountMsg))) {
                    Et.setText("");
                }
            }
        });

        /**
         * 设置字体
         */
        final EditText etAccount = findViewById(R.id.activity_login_EtAccount);
        EditTextUnt.with(etAccount).setFont(getApplicationContext(), "font/price" + ".ttf");
        final EditText etToken = findViewById(R.id.activity_login_EtCode);
        EditTextUnt.with(etToken).setFont(getApplicationContext(), "font/price" + ".ttf");
        /**
         * 监听是否到达11位数  如果到了11位数 就发送验证码
         */
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    etAccount.setEnabled(false);
                    /*输入了账户就冻结 不准再次输入®*/
                    etAccount.setTextColor(Color.parseColor("#000000"));
                    EditTextUnt.with(etToken).requestFocus().setText("");

                    /**
                     * 发送登录验证码
                     */
                    XmlBuilder.XmlInstance xmlInstance = new XmlBuilder.XmlInstance();
                    xmlInstance.initDom();
                    xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, etAccount
                            .getText().toString().trim());
                    xmlInstance.overDom();
                    Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS
                            .HTTP_ADDR_SEND_LOGINSMS, new ProgramInterface() {
                        @Override
                        public void onSucess(String data, int code, WaitDialog.RefreshDialog
                                _refreshDialog) {
                            Log.i(MSG, "发送短信验证码提示:" + data.trim());
                        }

                        @Override
                        public WaitDialog.RefreshDialog onStartLoad() {
                            return null;
                        }

                        @Override
                        public void onFaile(String data, int code) {

                        }
                    }, xmlInstance.getXmlTree().trim());
                }

            }
        });


        /**
         * 输入手机验证的监听事件
         */
        etToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Et = (EditText) v;
                if (Et.getText().toString().trim().equals(getResources().getString(R.string
                        .loginTokenMsg))) {
                    Et.setText("");
                }
            }
        });
        /**
         * 输入验证码的长度监听
         */
        etToken.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    etToken.setEnabled(false);
                    /*输入了验证码就冻结 不准再次输入®*/
                    etToken.setTextColor(Color.parseColor("#000000"));
                }
            }
        });


        /**
         * 登录按钮的监听
         */
        findViewById(R.id.activity_login_btnTologin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "登录按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
