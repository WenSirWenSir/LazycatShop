package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.annotation.SuppressLint;import android.os.Bundle;import android.view.View;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.LazyShopVip.SystemVip;import shlm.lmcs.com.lazycat.R;import shlm.lmcs.com.lazycat.TerminalSystemOS.PagesgetIntegral;/** * 积分主界面 */public class SystemIntegralPostal extends LazyCatAct {    private String MSG = "SystemIntegralPostal.java[+]";    private LocalValues.HTTP_ADDRS http_addrs;    /*是否为VIP*/    private Boolean isVip = false;    @Override    protected void onCreate(Bundle savedInstanceState) {        setStatusBar("#efefef");        setContentView(R.layout.activity_integralpostal);        http_addrs = new LocalValues.HTTP_ADDRS(getApplicationContext());        TextUnt.with(this, R.id.assembly_act_headTitle).setText("积分");        setBackListener(R.id.assembly_act_headBackImg);        init();        listener();        super.onCreate(savedInstanceState);    }    /**     * 程序初始化     */    @SuppressLint("NewApi")    private void init() {        /**         * 获取数据信息         */        PagesgetIntegral._get(SystemIntegralPostal.this, new PagesgetIntegral.onIntegralGet() {            @Override            public void onGet(PagesgetIntegral.IntegralPages _integralPages) {                TextUnt.with(SystemIntegralPostal.this, R.id.activity_integralpostalIntegral).setText("￥" + Tools.calcToAdd(_integralPages._Integral, "0"));                if (_integralPages._Profit.equals("")) {                    findViewById(R.id.activity_integralpostalProfitBody).setVisibility(View.GONE);                } else {                    findViewById(R.id.activity_integralpostalProfitBody).setVisibility(View.VISIBLE);                    TextUnt.with(SystemIntegralPostal.this, R.id.activity_integralpostalProfit).setVisibility(true).setText("收益" + _integralPages._Profit + "%");                }            }            @Override            public void onNotlogin() {                Tools.showError(SystemIntegralPostal.this, "错误信息", "您还没有登录系统");            }            @Override            public void onError() {                Tools.showError(SystemIntegralPostal.this, "错误信息", getResources().getString(R.string.SystemError0002));            }        });    }    /**     * 开始整理界面     */    private void initMain() {    }    /**     * 监听事件     */    private void listener() {        /**         * 查看积分提现等的操作         */        findViewById(R.id.activity_integralpostalIntegralBody).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                SystemVip systemVip = new SystemVip(SystemIntegralPostal.this);                systemVip.Start(new SystemVip.OnVipcheck() {                    @Override                    public void onIsvip() {                    }                    @Override                    public void onIsnovip() {                    }                    @Override                    public void onIsnologin() {                        Tools.showError(getApplicationContext(), "错误信息", "你没有登录系统");                    }                    @Override                    public void onIslogin() {                        LazyCatActStartActivity(SystemIntegral.class, false);                    }                });            }        });        /**         * 积分宝         */        findViewById(R.id.activity_integralpostalIntegralBBody).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Tools.showError(SystemIntegralPostal.this, "抱歉", "积分宝暂时只对内测商家开放,您暂时不符合要求。敬请期待");            }        });        /**         * 添加银行卡         */        findViewById(R.id.activity_integralpostalBank).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                /*判断是否登录*/                SystemVip systemVip = new SystemVip(SystemIntegralPostal.this);                systemVip.Start(new SystemVip.OnVipcheck() {                    @Override                    public void onIsvip() {                    }                    @Override                    public void onIsnovip() {                    }                    @Override                    public void onIsnologin() {                        Tools.showError(getApplicationContext(), "错误信息", "你没有登录系统");                    }                    @Override                    public void onIslogin() {                        LazyCatActStartActivity(SystemAddBankcard.class, false);                    }                });            }        });        /**         * 我的权益         */        findViewById(R.id.activity_integralpostalMyequity).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatActStartActivity(Systemequity.class, false);            }        });        /**         * 加盟中心         */        findViewById(R.id.activity_integralpostalJoinVip).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatActStartActivity(SystemAddtoVip.class, false);            }        });        /**         * 帮助中心         */        findViewById(R.id.activity_integralpostalBtnhelp).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {            }        });        /**         * 关于加盟         */        findViewById(R.id.activity_integralpostalBtnaboutJoin).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatActStartActivity(SystemAboutwhyJoin.class, false);            }        });    }}