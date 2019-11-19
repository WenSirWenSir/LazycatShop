package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.os.Bundle;import android.os.Handler;import android.os.Message;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.widget.ImageView;import android.widget.LinearLayout;import com.bumptech.glide.Glide;import org.xmlpull.v1.XmlPullParser;import java.util.Timer;import java.util.TimerTask;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.RefreshScrollView;import shlm.lmcs.com.lazycat.LazyShopAct.ShowshopOffice;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;import static shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config.Windows.GET_WINDOW_VALUE_SHOP_ACTION;public class SystemSeeOrderDetailsact extends LazyCatAct {    private final int HANDLER_CALC_TIMER = 1;    private final int REFRESH_STOP_MESSAGELOAD = 2;    private String order_number;/*订单号码*/    private String MSG = "SystemSeeOrderDetailsact.java[+]";    private LocalProgramTools.UserToolsInstance userToolsInstance;    private RefreshScrollView _RefreshScrollView;    private Timer timer;    private Timer RefreshTimer;    private SeeOrderPage seeOrderPage;    private String action;    @SuppressLint("HandlerLeak")    private Handler handler = new Handler() {        @Override        public void handleMessage(Message msg) {            switch (msg.what) {                case HANDLER_CALC_TIMER:                    if (TextUnt.with(SystemSeeOrderDetailsact.this, R.id                            .activity_systemseeorder_detailsSurplusTime).getTexttoString().trim()                            .equals("")) {                    } else {                        int timenumber = Integer.parseInt(TextUnt.with(SystemSeeOrderDetailsact                                .this, R.id.activity_systemseeorder_detailsSurplusTime)                                .getTexttoString());                        timenumber = timenumber - 1;                        TextUnt.with(SystemSeeOrderDetailsact.this, R.id                                .activity_systemseeorder_detailsSurplusTime).setText(String                                .valueOf(timenumber));                    }                    break;                case REFRESH_STOP_MESSAGELOAD:                    RefreshTimer.cancel();                    RefreshTimer = null;                    _RefreshScrollView.stopRefresh();                    break;            }            super.handleMessage(msg);        }    };    @SuppressLint("ResourceType")    @Override    protected void onCreate(Bundle savedInstanceState) {        setContentView(R.layout.activity_systemseeorder_details);        /*设置标题*/        TextUnt.with(this, R.id.assembly_act_headTitle).setText("订单详情");        setTransparentBar();        /*载入订单查询系统*/        action = getBundlerValue(LocalAction.ACTION);        /*设置取消订单的按钮样式*/        if (action.equals("0")) {            TextUnt.with(this, R.id.activity_systemseeorder_details_btnCancaleOrder)                    .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color                            .colorPrice), getResources().getString(R.color.colorPrice), 5))                    .setTextColor("#ffffff").setVisibility(true);        } else {            TextUnt.with(this, R.id.activity_systemseeorder_details_btnCancaleOrder)                    .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color                            .colorPrice), getResources().getString(R.color.colorPrice), 5))                    .setTextColor("#ffffff").setVisibility(false);        }        /*设置载入的横向展示商品的控件 不显示距离*/        findViewById(R.id.item_searchshoplist_businessBody).setVisibility(View.GONE);        userToolsInstance = LocalProgramTools.getUserToolsInstance();        order_number = getBundlerValue(LocalAction.WINDOWS_TO_WINDOWS.ACTION_ORDERNUMBER);        /*判断是否是vip*/        TextUnt.with(SystemSeeOrderDetailsact.this, R.id.activity_systemseeorder_details_VipMsg)                .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color.colorVip)                        , getResources().getString(R.color.colorVip), 5)).setText("Vip超时赔付");        /*下拉刷新*/        _RefreshScrollView = findViewById(R.id.activity_systemseeorder_details_ScrollView);        init();        listener();        super.onCreate(savedInstanceState);    }    /**     * 监听事件     */    private void listener() {        /*设置下拉刷新*/        LinearLayout item = findViewById(R.id.activity_systemseeorder_details_refreshHead);        _RefreshScrollView.SetHeadView("http://120.79.63.36/Photos/ConfigMain/" +                "shzService/MainPage/bigHeadMsg.png", "http://www.baidu.com", item, 100, R.id                .activity_systemseeorder_details_refreshHeadProgressbar, R.id                .activity_systemseeorder_details_refreshHeadImg);        /*设置取消按钮*/        findViewById(R.id.activity_systemseeorder_details_btnCancaleOrder).setOnClickListener(new View.OnClickListener() {            @SuppressLint("ResourceType")            @Override            public void onClick(View v) {                AlertDialog.Builder builder = new AlertDialog.Builder(SystemSeeOrderDetailsact                        .this);                View item = LayoutInflater.from(getApplicationContext()).inflate(R.layout                        .alert_message, null);                TextUnt.with(item, R.id.alert_messageTitle).setText("取消提示").setTextColor                        (getResources().getString(R.color.colorPrice));                TextUnt.with(item, R.id.alert_messageText).setText(getResources().getString(R                        .string.cancaleOrder)).setTextColor(getResources().getString(R.color                        .colorPrice));                builder.setView(item);                TextUnt.with(item, R.id.alert_messageBtnConfirm).setText("我已知晓").setTag(builder                        .show()).setOnClick(new View.OnClickListener() {                    @Override                    public void onClick(View v) {                        AlertDialog alertDialog = (AlertDialog) v.getTag();                        alertDialog.dismiss();                    }                });            }        });        /**         * 设置下拉的监听事件         */        _RefreshScrollView.SetLinstener(new RefreshScrollView.RefreshScrollViewListener() {            @Override            public void onRefresh() {            }            @Override            public void onRefreshDone() {            }            @Override            public void onStopRefresh() {            }            @Override            public void onState(int _static) {            }            @Override            public void onLoadMore() {            }            @Override            public void onLoadBottom() {            }            @Override            public void onScrollStop() {            }            @Override            public void onloadMessage() {                init();/*重新尝试加载*/                RefreshTimer = new Timer();                RefreshTimer.schedule(new TimerTask() {                    @Override                    public void run() {                        Message msg = new Message();                        msg.what = REFRESH_STOP_MESSAGELOAD;                        handler.sendMessage(msg);                    }                }, 3000);            }            @Override            public void onScrollDistance(int distance) {            }            @Override            public void onScrollToleft(int _moveCount) {            }            @Override            public void onScrollToRight(int _moveCount) {            }        });        /**         * 退出监听         */        findViewById(R.id.assembly_act_headBackImg).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                finish();            }        });    }    /**     * 初始化     */    private void init() {        /*连接网络 ,获取数据信息*/        XmlBuilder.XmlInstance xmlInstance = new XmlBuilder.XmlInstance();        xmlInstance.initDom();        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                        .ACTION_LOCALUSERPAGE_ACCOUNT));        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TOKEN));        xmlInstance.setXmlTree(LocalAction.ACTION_SHOP.ACTION_ORDER_NUMBER, order_number);        xmlInstance.setXmlTree(LocalAction.ACTION, action);        xmlInstance.overDom();        Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_GETORDERPAGE, new                ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                Log.i(MSG, "返回的数据为:" + data.trim());                _refreshDialog.dismiss();                XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                    @Override                    public void onFaile() {                    }                    @Override                    public void onStartDocument(String tag) {                        seeOrderPage = new SeeOrderPage();                    }                    @Override                    public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                        try {                            /*设置剩余超时时间*/                            if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                    .ACTION_ORDER_SURPLUS)) {                                Log.i(MSG, "1");                                seeOrderPage.setSurplusTime(pullParser.nextText().trim());                            }                            /*获取VIP状态*/                            if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                    .ACTION_LOCALUSERPAGE_VIPSTATUS)) {                                Log.i(MSG, "2");                                seeOrderPage.setVipstatus(pullParser.nextText().trim());                            }                            /*获取商品的标题*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TITLE)) {                                Log.i(MSG, "3");                                seeOrderPage.setTitle(pullParser.nextText().trim());                            }                            /*商品的状态*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES                                    .ACTION_SHOPVALUES_STATIC)) {                                Log.i(MSG, "4");                                seeOrderPage.setShopstatus(pullParser.nextText().trim());                            }                            /*价格对应的单位*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES                                    .ACTION_SHOPVALUES_COMPANY)) {                                Log.i(MSG, "5");                                seeOrderPage.setCompany(pullParser.nextText().trim());                            }                            /*设置商品的批发起订数量*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SU)) {                                Log.i(MSG, "6");                                seeOrderPage.setSu(pullParser.nextText().trim());                            }                            /*设置商品的批发价格*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TP)) {                                Log.i(MSG, "7");                                seeOrderPage.setTp(pullParser.nextText().trim());                            }                            /*设置横线的价格*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_DLP)) {                                Log.i(MSG, "8");                                seeOrderPage.setDottenPrice(pullParser.nextText().trim());                            }                            /*设置图片地址*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_IMG)) {                                Log.i(MSG, "9");                                seeOrderPage.setShopImg(pullParser.nextText().trim());                            }                            /*设置商户地址*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES                                    .ACTION_SHOPVALUES_BUSINSS)) {                                Log.i(MSG, "10");                                seeOrderPage.setBusinessName(pullParser.nextText().trim());                            }                            /*设置保质期*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_PD)) {                                Log.i(MSG, "11");                                seeOrderPage.setPd(pullParser.nextText().trim());                            }                            /*设置生产日期*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_EXP)) {                                Log.i(MSG, "12");                                seeOrderPage.setExp(pullParser.nextText().trim());                            }                            /*设置最低的箱装单位*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES                                    .ACTION_SHOPVALUES_SPLITUNIT)) {                                Log.i(MSG, "13");                                seeOrderPage.setSplitUnit(pullParser.nextText().trim());                            }                            /*设置规格*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SPEC)) {                                Log.i(MSG, "14");                                seeOrderPage.setSpec(pullParser.nextText().trim());                            }                            /*设置时间戳*/                            if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                    .ACTION_ORDER_TIMESTAMP)) {                                Log.i(MSG, "15");                                seeOrderPage.setTimeStamp(pullParser.nextText().trim());                            }                            /*设置下单时间*/                            if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM.ACTION_SEND_TIME)) {                                seeOrderPage.setOrderTime(pullParser.nextText().trim());                            }                            /*设置订单号*/                            if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                    .ACTION_ORDERNUMBER)) {                                seeOrderPage.setOrderNumber(pullParser.nextText().trim());                            }                        } catch (Exception e) {                        }                    }                    @Override                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                    }                    @Override                    public void onEndDocument() {                        handlerShop();                        _RefreshScrollView.stopRefresh();                    }                });            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                /*初始化一个DIALOG*/                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog                        (SystemSeeOrderDetailsact.this);                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                wait_itme_dialogpage.setImg(R.id.item_wait_img);                wait_itme_dialogpage.setView(R.layout.item_wait);                wait_itme_dialogpage.setCanClose(false);                wait_itme_dialogpage.setTitle(R.id.item_wait_title);                refreshDialog.Init(wait_itme_dialogpage);                refreshDialog.showRefreshDialog("加载中...", false);                return refreshDialog;            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree().trim());    }    /**     * 整理订单界面     */    @SuppressLint("ResourceType")    private void handlerShop() {        /*设置剩余时间*/        TextUnt.with(this, R.id.activity_systemseeorder_detailsSurplusTime).setText(seeOrderPage                .getSurplusTime().trim());        /*整理时钟*/        onTimestartCalc();        /*设置标题*/        TextUnt.with(this, R.id.item_searchshoplist_title).setText(seeOrderPage.getTitle());        /*设置批发的价格*/        TextUnt.with(this, R.id.item_searchshoplist_price).setText(seeOrderPage.getTp());        /*设置价格对应的单位*/        TextUnt.with(this, R.id.item_searchshoplist_company).setText("/" + seeOrderPage                .getCompany());        /*判断状态*/        switch (seeOrderPage.getShopstatus()) {            case LocalValues.VALUES_SHOPPAGE.NORMAL:                /*正常*/                TextUnt.with(this, R.id.item_searchshoplist_price).setTextColor(getResources()                        .getString(R.color.colorPrice));                break;            case LocalValues.VALUES_SHOPPAGE.PROMOTION:                /*促销商品*/                TextUnt.with(this, R.id.item_searchshoplist_price).setTextColor(getResources()                        .getString(R.color.colorPromotion));                /*需要设置状态*/                TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setTextColor("#ffffff")                        .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color                                .colorPromotion), getResources().getString(R.color                                .colorPromotion), 5)).setText(getResources().getString(R.string                        .payPromotion));                /*需要设置画横线的价格*/                try {                    if (seeOrderPage.getDottenPrice() != null && !seeOrderPage.getDottenPrice()                            .equals("0")) {                        TextUnt.with(this, R.id.item_searchshoplist_dottedlinePrice).setText                                (getResources().getString(R.string.PriceSymbol) + seeOrderPage                                        .getDottenPrice()).setVisibility(true).setMidcourtLine();                    }                } catch (Exception e) {                    Log.e(MSG, "设置画横线的价格失败:" + e.getMessage());                }                break;            case LocalValues.VALUES_SHOPPAGE.REDUCTION:                /*满减*/                TextUnt.with(this, R.id.item_searchshoplist_price).setTextColor(getResources()                        .getString(R.color.colorReduction));                /*需要设置状态*/                TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setTextColor("#ffffff")                        .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color                                .colorReduction), getResources().getString(R.color                                .colorReduction), 5)).setText(getResources().getString(R.string                        .payReduction));                /*需要设置画横线的价格*/                try {                    if (seeOrderPage.getDottenPrice() != null && !seeOrderPage.getDottenPrice()                            .equals("0")) {                        TextUnt.with(this, R.id.item_searchshoplist_dottedlinePrice).setText                                (getResources().getString(R.string.PriceSymbol) + seeOrderPage                                        .getDottenPrice()).setVisibility(true).setMidcourtLine();                    }                } catch (Exception e) {                    Log.e(MSG, "设置画横线的价格失败:" + e.getMessage());                }                break;            case LocalValues.VALUES_SHOPPAGE.VOLUME:                /*领劵*/                TextUnt.with(this, R.id.item_searchshoplist_price).setTextColor(getResources()                        .getString(R.color.colorVolumn));                /*需要设置状态*/                TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setTextColor("#ffffff")                        .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color                                .colorVolumn), getResources().getString(R.color.colorVolumn), 5))                        .setText(getResources().getString(R.string.payVolumn));                /*需要设置画横线的价格*/                try {                    if (seeOrderPage.getDottenPrice() != null && !seeOrderPage.getDottenPrice()                            .equals("0")) {                        TextUnt.with(this, R.id.item_searchshoplist_dottedlinePrice).setText                                (getResources().getString(R.string.PriceSymbol) + seeOrderPage                                        .getDottenPrice()).setVisibility(true).setMidcourtLine();                    }                } catch (Exception e) {                    Log.e(MSG, "设置画横线的价格失败:" + e.getMessage());                }            case LocalValues.VALUES_SHOPPAGE.ONLY_VIP:                /*Vip专属*/                TextUnt.with(this, R.id.item_searchshoplist_price).setTextColor(getResources()                        .getString(R.color.colorVip));                /*需要设置状态*/                TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setTextColor("#ffffff")                        .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color                                .colorVip), getResources().getString(R.color.colorVip), 5))                        .setText(getResources().getString(R.string.payOnlyvip));                /*需要设置画横线的价格*/                try {                    if (seeOrderPage.getDottenPrice() != null && !seeOrderPage.getDottenPrice()                            .equals("0")) {                        TextUnt.with(this, R.id.item_searchshoplist_dottedlinePrice).setText                                (getResources().getString(R.string.PriceSymbol) + seeOrderPage                                        .getDottenPrice()).setVisibility(true).setMidcourtLine();                    }                } catch (Exception e) {                    Log.e(MSG, "设置画横线的价格失败:" + e.getMessage());                }                break;            case LocalValues.VALUES_SHOPPAGE.ONLY_ONE:                /*限定一件*/                TextUnt.with(this, R.id.item_searchshoplist_price).setTextColor(getResources()                        .getString(R.color.colorPayonly));                /*需要设置状态*/                TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setTextColor("#ffffff")                        .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color                                .colorPayonly), getResources().getString(R.color.colorPayonly),                                5)).setText(R.string.payOnlyone);                /*需要设置画横线的价格*/                try {                    if (seeOrderPage.getDottenPrice() != null && !seeOrderPage.getDottenPrice()                            .equals("0")) {                        TextUnt.with(this, R.id.item_searchshoplist_dottedlinePrice).setText                                (getResources().getString(R.string.PriceSymbol) + seeOrderPage                                        .getDottenPrice()).setVisibility(true).setMidcourtLine();                    }                } catch (Exception e) {                    Log.e(MSG, "设置画横线的价格失败:" + e.getMessage());                }                break;        }        /*设置不要显示商家距离*/        findViewById(R.id.item_searchshoplist_businessBody).setVisibility(View.GONE);        ImageView shopImg = findViewById(R.id.item_searchshoplist_img);/*图片控件*/        /*设置商户地址*/        TextUnt.with(this, R.id.item_searchshoplist_businessName).setText(seeOrderPage                .getBusinessName());        /*设置规格和规格X数量*/        TextUnt.with(this, R.id.activity_systemseeorder_details_toPayhow).setText(seeOrderPage                .getCompany() + "装x" + seeOrderPage.getSpec() + seeOrderPage.getSplitUnit());        /*生产和保质期*/        TextUnt.with(this, R.id.activity_systemseeorder_details_Expandpd).setText("生产日期:" +                seeOrderPage.getExp() + "|保质期:" + seeOrderPage.getPd().trim() + "天");        /*设置订单提交日期*/        TextUnt.with(this, R.id.activity_systemseeorder_details_sendOrderTime).setText                (seeOrderPage.getOrderTime());        /*设置订单号*/        TextUnt.with(this, R.id.activity_systemseeorder_details_Ordernumbr).setText(seeOrderPage                .getOrderNumber().trim());        /*设置时间戳*/        Log.i(MSG, "时间戳:" + seeOrderPage.getTimeStamp());        TextUnt.with(this, R.id.activity_systemseeorder_details_timeStamp).setText(seeOrderPage                .getTimeStamp());        /*设置服务*/        if (seeOrderPage.getVipstatus().equals(LocalValues.VALUES_USERCENTER.IS_VIP)) {            /*下单时候是VIP 设置有Vip服务的信息*/            TextUnt.with(this, R.id.activity_systemseeorder_details_servicemsg).setText                    (getResources().getString(R.string.orderVipServiceMsg)).setTextColor                    (getResources().getString(R.color.colorVip));        } else {            /*下单的时候不是VIP 设置无售后服务*/            TextUnt.with(this, R.id.activity_systemseeorder_details_servicemsg).setText                    (getResources().getString(R.string.ordernotVipServiceMsg)).setTextColor                    ("#020433");        }        /*设置监听事件*/        findViewById(R.id.item_searchshoplist_body).setTag(seeOrderPage);        findViewById(R.id.item_searchshoplist_body).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                SeeOrderPage sop = (SeeOrderPage) v.getTag();                LazyCatStartActivityWithBundler(ShowshopOffice.class, false, Config.Windows                        .GET_WINDOW_VALUE_SHOP_MESSAGE, sop.getTitle().trim(),                        GET_WINDOW_VALUE_SHOP_ACTION, LocalValues.VALUES_SEARCH                                .VALUES_TO_SEARCH_SHOPKEYWORD);            }        });        /*设置加载图片*/        Glide.with(getApplicationContext()).load(LocalValues.HTTP_ADDRS.HTTP_ADDR_IMG_URL +                seeOrderPage.getShopImg().trim()).into(shopImg);    }    /**     * 倒计时     */    private void onTimestartCalc() {        if (timer != null) {            timer.cancel();            timer = null;        }        timer = new Timer();        timer.schedule(new TimerTask() {            @Override            public void run() {                Message msg = new Message();                msg.what = HANDLER_CALC_TIMER;                handler.sendMessage(msg);            }        }, 0, 1000);    }    @Override    protected void onDestroy() {        try {            if (timer != null) {                timer.cancel();            }        } catch (Exception e) {        }        super.onDestroy();    }    @Override    protected void onPause() {        try {            if (timer != null) {                timer.cancel();            }        } catch (Exception e) {        }        super.onPause();    }    @Override    protected void onStop() {        try {            if (timer != null) {                timer.cancel();            }        } catch (Exception e) {        }        super.onStop();    }    @Override    protected void onResume() {        super.onResume();    }    @Override    protected void onRestart() {        super.onRestart();    }    class SeeOrderPage {        private String title;/*商品的标题*/        private String dottenPrice;/*虚线价格*/        private String businessName;/*对接商家*/        private String orderNumber;/*设置下单号*/        private String shopstatus;/*商品的状态*/        private String shopImg;/*商品的图片地址*/        private String surplusTime;/*剩余时间*/        private String su;/*批发起送*/        private String tp;/*起送价格*/        private String company;/*价格对应的单位*/        private String splitUnit;/*箱装最低的单位*/        private String exp;/*生产日期*/        private String pd;/*保质期*/        private String orderTime;/*下单日期*/        private String spec;/*箱规数量*/        private String distance;/*距离*/        private String vipstatus;/*下单的时候的VIP状态*/        private String timeStamp;/*商品的时间戳*/        public String getOrderNumber() {            return orderNumber;        }        public void setOrderNumber(String orderNumber) {            this.orderNumber = orderNumber;        }        public String getVipstatus() {            return vipstatus;        }        public void setVipstatus(String vipstatus) {            this.vipstatus = vipstatus;        }        public String getTimeStamp() {            return timeStamp;        }        public void setTimeStamp(String timeStamp) {            this.timeStamp = timeStamp;        }        public String getSplitUnit() {            return splitUnit;        }        public void setSplitUnit(String splitUnit) {            this.splitUnit = splitUnit;        }        public String getExp() {            return exp;        }        public void setExp(String exp) {            this.exp = exp;        }        public String getPd() {            return pd;        }        public void setPd(String pd) {            this.pd = pd;        }        public String getOrderTime() {            return orderTime;        }        public void setOrderTime(String orderTime) {            this.orderTime = orderTime;        }        public String getSpec() {            return spec;        }        public void setSpec(String spec) {            this.spec = spec;        }        public String getDistance() {            return distance;        }        public void setDistance(String distance) {            this.distance = distance;        }        public String getShopImg() {            return shopImg;        }        public void setShopImg(String shopImg) {            this.shopImg = shopImg;        }        public String getSu() {            return su;        }        public void setSu(String su) {            this.su = su;        }        public String getTp() {            return tp;        }        public void setTp(String tp) {            this.tp = tp;        }        public String getCompany() {            return company;        }        public void setCompany(String company) {            this.company = company;        }        public String getTitle() {            return title;        }        public void setTitle(String title) {            this.title = title;        }        public String getDottenPrice() {            return dottenPrice;        }        public void setDottenPrice(String dottenPrice) {            this.dottenPrice = dottenPrice;        }        public String getBusinessName() {            return businessName;        }        public void setBusinessName(String businessName) {            this.businessName = businessName;        }        public String getShopstatus() {            return shopstatus;        }        public void setShopstatus(String shopstatus) {            this.shopstatus = shopstatus;        }        public String getSurplusTime() {            return surplusTime;        }        public void setSurplusTime(String surplusTime) {            this.surplusTime = surplusTime;        }    }}