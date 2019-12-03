package shlm.lmcs.com.lazycat.LazyShopAct;

import android.Manifest;
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

import org.xmlpull.v1.XmlPullParser;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.EditTextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.JsonEndata;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;
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

    /**
     * 存入用户的表格的工具类
     */
    private LocalProgramTools.UserToolsInstance userToolsInstance;

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
         * 关闭界面
         */
        findViewById(R.id.activity_login_btnToclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                    LocalValues.HTTP_ADDRS http_addrs = LocalValues.getHttpaddrs(getApplicationContext());
                    Net.doPostXml(getApplicationContext(), http_addrs
                            .HTTP_ADDR_SEND_LOGINSMS, new ProgramInterface() {
                        @Override
                        public void onSucess(String data, int code, WaitDialog.RefreshDialog
                                _refreshDialog) {
                            Log.i(MSG, "发送短信验证码提示:" + data.trim());
                            try {
                                JsonEndata jsonEndata = new JsonEndata(data.trim());
                                if (jsonEndata.getJsonKeyValue("return_code").equals("00000")) {
                                    Toast.makeText(getApplicationContext(), "短信验证码发送成功", Toast
                                            .LENGTH_SHORT).show();
                                } else if (jsonEndata.getJsonKeyValue("return_code").equals
                                        ("00001")) {
                                    Toast.makeText(getApplicationContext(), "该账户没有在仓库网络注册过¬",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e(MSG, "请求发送登录验证码失败");
                                Toast.makeText(getApplicationContext(), "提交信息错误,请联系管理人员", Toast
                                        .LENGTH_SHORT).show();
                            }
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
                XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(false);
                xmlInstance.initDom();
                xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, etAccount.getText()
                        .toString().trim());
                xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_CODE, etToken.getText()
                        .toString().trim());
                xmlInstance.overDom();
                LocalValues.HTTP_ADDRS http_addrs = LocalValues.getHttpaddrs(getApplicationContext());
                Net.doPostXml(getApplicationContext(), http_addrs
                        .HTTP_ADDR_INSPECT_LOGIN, new ProgramInterface() {
                    @Override
                    public void onSucess(String data, int code, WaitDialog.RefreshDialog
                            _refreshDialog) {
                        Log.i(MSG, "登录验证码检查返回数据：" + data.trim());
                        if (data.trim().equals("-1")) {
                            Toast.makeText(getApplicationContext(), "登录失败!", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data
                                    .trim());
                            xmlanalysisFactory.Startanalysis(new XmlanalysisFactory
                                    .XmlanalysisInterface() {
                                @Override
                                public void onFaile() {

                                }

                                @Override
                                public void onStartDocument(String tag) {
                                    userToolsInstance = LocalProgramTools.getUserToolsInstance();
                                }

                                @Override
                                public void onStartTag(String tag, XmlPullParser pullParser,
                                                       Integer id) {
                                    try {
                                        /*用户的TOKEN*/
                                        if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE
                                                .ACTION_LOCALUSERPAGE_TOKEN)) {
                                            userToolsInstance.setToken(pullParser.nextText().trim
                                                    ());
                                        }
                                        /*用户的账户*/
                                        if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE
                                                .ACTION_LOCALUSERPAGE_ACCOUNT)) {
                                            userToolsInstance.setAccount(pullParser.nextText()
                                                    .trim());
                                        }
                                        /*店铺的地址*/
                                        if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE
                                                .ACTION_LOCALUSERPAGE_SHOPADDR)) {
                                            userToolsInstance.setShopaddr(pullParser.nextText());
                                        }
                                        /*店铺的全称*/
                                        if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE
                                                .ACTION_LOCALUSERPAGE_SHOPNAME)) {
                                            userToolsInstance.setShopname(pullParser.nextText()
                                                    .trim());
                                        }
                                        /*店铺的负责人*/
                                        if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE
                                                .ACTION_LOCALUSERPAGE_SHOPUSEPEOPLE)) {
                                            userToolsInstance.setShopusePeople(pullParser
                                                    .nextText().trim());
                                        }
                                        /*店铺的经度*/
                                        if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE
                                                .ACTION_LOCALUSERPAGE_SHOPLONG)) {
                                            userToolsInstance.setShoplong(pullParser.nextText()
                                                    .trim());
                                        }
                                        /*店铺的电话*/
                                        if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE
                                                .ACTION_LOCALUSERPAGE_SHOPTEL)) {
                                            userToolsInstance.setShoptel(pullParser.nextText()
                                                    .trim());
                                        }
                                        /*店铺的维度*/
                                        if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE
                                                .ACTION_LOCALUSERPAGE_SHOPLAT)) {
                                            userToolsInstance.setShoplat(pullParser.nextText()
                                                    .trim());
                                        }
                                    } catch (Exception e) {

                                        Log.e(MSG, "登录整理用户的数据错误输出:" + e.getMessage());
                                    }

                                }

                                @Override
                                public void onEndTag(String tag, XmlPullParser pullParser,
                                                     Integer id) {

                                }

                                @Override
                                public void onEndDocument() {

                                    /*判断是否拥有写入权限*/
                                    if (!Tools.isPermission(getApplicationContext(), Manifest
                                            .permission.READ_EXTERNAL_STORAGE)) {
                                        /*没有写入权限 要求客户重新打开权限后重试*/
                                    } else if (!Tools.isPermission(getApplicationContext(),
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    } else {
                                        /*两个权限都有了*/
                                        if (userToolsInstance.SaveingUserPageXml
                                                (getApplicationContext())) {
                                            Toast.makeText(getApplicationContext(), "登录成功!" +
                                                    userToolsInstance.getShopname().trim() +
                                                    "欢迎您", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                        }
                                    }

                                }
                            });
                        }

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
        });
    }

}
