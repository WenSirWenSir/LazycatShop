package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.annotation.SuppressLint;import android.graphics.Color;import android.os.Bundle;import android.text.TextUtils;import android.util.Log;import android.view.View;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.RelativeLayout;import android.widget.TextView;import android.widget.Toast;import org.xmlpull.v1.XmlPullParser;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.WxPay.WxpayinitInstance;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;/** * 加入VIP */public class SystemAddtoVip extends LazyCatAct {    private String MSG = "SystemAddtoVip.java[+]";    private String createTrrdeno = "";/*预支付调试返回的订单*/    private LocalValues.HTTP_ADDRS http_addrs;    private TextView tv_paymoney;/*支付的金额*/    @SuppressLint("ResourceType")    @Override    protected void onCreate(Bundle savedInstanceState) {        setStatusBar(getResources().getString(R.color.colorPaywindowBack));        setContentView(R.layout.activity_systemaddtovip);        tv_paymoney = findViewById(R.id.activity_systemaddtoVipPaymoney);/*获取支付的控件*/        init();        listener();        super.onCreate(savedInstanceState);    }    /**     * 监听事件     */    @SuppressLint("ResourceType")    private void listener() {        /**         * 退出         */        findViewById(R.id.assembly_act_headBackImg).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                finish();            }        });        /**         * 点击启动微信支付         */        TextUnt.with(this, R.id.activity_systemaddtoVipBtn_topay).setBackground(Tools                .CreateDrawable(1, getResources().getString(R.color.colorPaywindowBack),                        getResources().getString(R.color.colorPaywindowBack), 25)).setOnClick(new View.OnClickListener() {            @Override            public void onClick(View v) {                /*为云仓库添加一个支付的KEY 用来判断回调的时候可以去处理信息*/                XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);                /*附加数据*/                xmlInstance.setXmlTree(LocalAction.ACTION_TRRADENO.ACTION_TRRADENO_ATTACH,                        LocalValues.VALUES_TRRADENO.TRRADENO_PAY_VIP_ATTACH);                /*支付信息*/                xmlInstance.setXmlTree(LocalAction.ACTION_TRRADENO.ACTION_TRRADENO_BODY,                        LocalValues.VALUES_TRRADENO.TRRADENO_PAY_VIP_BODY);                /*支付金额*/                xmlInstance.setXmlTree(LocalAction.ACTION_TRRADENO.ACTION_TRRADENO_TOTAL, "1");                /*支付类型*/                xmlInstance.setXmlTree(LocalAction.ACTION_TRRADENO.ACTION_TRRADENO_PAYSTATUS,                        LocalValues.VALUES_TRRADENO.TRRADENO_PAY_STATUS);                xmlInstance.overDom();                Net.doPostXml( String.format(http_addrs                        .HTTP_ADDR_WXPAY_CREATEOUTTRADENO), new ProgramInterface() {                    @Override                    public void onSucess(String data, int code, WaitDialog.RefreshDialog                            _refreshDialog) {                        _refreshDialog.dismiss();                        Log.i(MSG, "云仓库预支付订单获取数据回调:" + data.trim());                        if (data.equals(LocalValues.NET_ERROR)) {                            Toast.makeText(getApplicationContext(), "生成预支付订单失败,请检查您是否登录云仓库",                                    Toast.LENGTH_SHORT).show();                        } else {                            XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data                                    .trim());                            xmlanalysisFactory.Startanalysis(new XmlanalysisFactory                                    .XmlanalysisInterface() {                                @Override                                public void onFaile() {                                }                                @Override                                public void onStartDocument(String tag) {                                }                                @Override                                public void onStartTag(String tag, XmlPullParser pullParser,                                                       Integer id) {                                    try {                                        /*预支付订单号*/                                        if (tag.equals(LocalAction.ACTION_TRRADENO                                                .ACTION_TRRADENO_NUMBER)) {                                            createTrrdeno = pullParser.nextText().trim();                                        }                                    } catch (Exception e) {                                        Log.e(MSG, "预支付初始化解析错误,错误信息:" + e.getMessage());                                    }                                }                                @Override                                public void onEndTag(String tag, XmlPullParser pullParser,                                                     Integer id) {                                }                                @Override                                public void onEndDocument() {                                    if (!TextUtils.isEmpty(createTrrdeno)) {                                        /**                                         * 预支付成功  开始启动微信支付                                         */                                        WxpayinitInstance wxpayinitInstance = new                                                WxpayinitInstance(SystemAddtoVip.this,                                                "云仓库VIP月费", "Vip加盟费用", createTrrdeno, "1");                                        wxpayinitInstance.startWxPay();                                    }                                }                            });                        }                    }                    @Override                    public WaitDialog.RefreshDialog onStartLoad() {                        /*初始化一个DIALOG*/                        final WaitDialog.RefreshDialog refreshDialog = new WaitDialog                                .RefreshDialog(SystemAddtoVip.this);                        WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                        wait_itme_dialogpage.setImg(R.id.item_wait_img);                        wait_itme_dialogpage.setView(R.layout.item_wait);                        wait_itme_dialogpage.setCanClose(false);                        wait_itme_dialogpage.setTitle(R.id.item_wait_title);                        refreshDialog.Init(wait_itme_dialogpage);                        refreshDialog.showRefreshDialog("生成预支付订单中", false);                        return refreshDialog;                    }                    @Override                    public void onFaile(String data, int code) {                    }                }, xmlInstance.getXmlTree());            }        });    }    /**     * 初始化     */    @SuppressLint({"NewApi", "ResourceType"})    private void init() {        /*执行数字的动画*/        Tools.NumberAddAnimator(0.00f, 68.00f, tv_paymoney);        /*获取地址工具类*/        http_addrs = LocalValues.getHttpaddrs(getApplicationContext());        ImageView backImg = findViewById(R.id.assembly_act_headBackImg);        backImg.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.ico_back,                "#ffffff"));        RelativeLayout rl = findViewById(R.id.assembly_act_headBody);        rl.setBackgroundColor(Color.parseColor(getResources().getString(R.color                .colorPaywindowBack)));        /*设置支付信息列表框的样式*/        LinearLayout layout = findViewById(R.id.activity_systemaddtoVipPayMsgbody);        layout.setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff", 10));        /*设置过期时间*/        TextUnt.with(this, R.id.activity_systemaddtoVipoverdue).setText(Tools.StamptoDate(Tools                .getVipStamp()));    }}