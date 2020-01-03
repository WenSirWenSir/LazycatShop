package shlm.lmcs.com.lazycat.LazyShopFrg;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.os.Bundle;import android.text.TextUtils;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.TextView;import android.widget.Toast;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.RelativeLayoutUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.WxPay.WxpayinitInstance;import shlm.lmcs.com.lazycat.LazyShopAct.BrowseAct;import shlm.lmcs.com.lazycat.LazyShopAct.LoginAct;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemIntegralPostal;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemPayvipRecordAct;import shlm.lmcs.com.lazycat.LazyShopAct.ToorderService;import shlm.lmcs.com.lazycat.LazyShopAct.UserAct.UsertrackAct;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;public class UserCenterfrg extends LazyCatFragment {    private String MSG = "UserCenterfrg.java[+]";    private LinearLayout CardBody;    private XmlTagValuesFactory.XMLValuesUserpageCard userpageCard = null;    private XmlTagValuesFactory.XMLValuesUserpageBtnItem item = null;/*控件BTN*/    private XmlTagValuesFactory.Init_UserCenterValues init_userCenter = null;    private ArrayList<XmlTagValuesFactory.XMLValuesUserpageCard> cardList = new            ArrayList<XmlTagValuesFactory.XMLValuesUserpageCard>();    private TextView business_name;    private TextView business_vip;    private TextView TvBrowshow;    private TextView TvDeliveryhow;    private TextView TvBusinessLonglat;/*店铺的经纬度*/    private TextView TvIntegral;    private String St_account;    private String St_token;/*缓存登录钥匙*/    private String St_long;/*维度*/    private String St_lat;/*经度*/    private String St_busniessname;/*店铺全称*/    private String St_businessaddr;/*店铺的地址*/    private String St_businessPeople;/*店铺的负责人*/    private String St_businessTel;/*店铺的电话*/    private View body;    private WxpayinitInstance wxpayinitInstance;    private AlertDialog alertDialog;/*退出*/    private String Paykey;    private String _perpayId;    private LocalProgramTools.UserToolsInstance userToolsInstance;    private LocalValues.HTTP_ADDRS http_addrs;/*地址工具类*/    @SuppressLint({"NewApi", "ResourceType"})    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle            savedInstanceState) {        body = inflater.inflate(R.layout.fragment_user, null);        /*获取地址工具类*/        http_addrs = LocalValues.getHttpaddrs(getContext());        /*获取主题的颜色  */        setStatusBar(getResources().getString(R.color.ThemeColor));        /*我的功能body*/        body.findViewById(R.id.usercenterfrg_userValuesBody).setBackground(Tools.CreateDrawable                (1, "#ffffff", "#ffffff", 15));        /*设置订单导航的body*/        body.findViewById(R.id.fragment_user_orderNav).setBackground(Tools.CreateDrawable(1,                "#ffffff", "#ffffff", 15));        /*设置其他功能的导航的Body*/        body.findViewById(R.id.fragment_user_otherNav).setBackground(Tools.CreateDrawable(1,                "#ffffff", "#ffffff", 15));        CardBody = body.findViewById(R.id.usercenterfrg_cardBody);/*添加card的body*/        /*设置Logo字体*/        TextUnt.with(body, R.id.fragment_user_ico).setFontFile(getContext(), "canLogo");        TextUnt.with(body, R.id.fragment_user_icoTitle).setFontFile(getContext(), "mvboli");/*        business_balance.setValues(80.98f);*/        /*这个现实商户的名称*/        business_name = body.findViewById(R.id.fragmen_user_business_name);        /*这个现实VIP*/        business_vip = body.findViewById(R.id.fragment_user_vip);        /*设置商品的积分*/        TvIntegral = body.findViewById(R.id.fragment_user_integral);        /*这个显示交易的次数*/        /*充值按钮*/        /*圆形的显示数量*/        TvBrowshow = body.findViewById(R.id.fragment_user_TransactionHow);        TextUnt.with(TvBrowshow).setBackground(Tools.CreateDrawable(1, getResources().getString(R                .color.ThemeColor), getResources().getString(R.color.ThemeColor), 50))                .setVisibility(false);        /*圆形的显示数量*/        TextUnt.with(body, R.id.fragment_user_TvSendSystemhow).setVisibility(false).setBackground                (Tools.CreateDrawable(1, getResources().getString(R.color.ThemeColor),                        getResources().getString(R.color.ThemeColor), 50)).setVisibility(false);        /*圆形的显示数量*/        TvDeliveryhow = body.findViewById(R.id.fragment_user_TvDeliveryhow);        TextUnt.with(TvDeliveryhow).setBackground(Tools.CreateDrawable(1, getResources()                .getString(R.color.ThemeColor), getResources().getString(R.color.ThemeColor), 50)        ).setVisibility(false);        /*店铺的经纬度*/        TvBusinessLonglat = body.findViewById(R.id.fragment_user_TvLongLat);        setTransparentBar();        init(body);        listener(body);        return body;    }    /**     * 控件监听     *     * @param body     */    @SuppressLint("NewApi")    private void listener(final View body) {        /**         * 点击进入VIP充值记录中心         */        RelativeLayoutUnt.with(body, R.id.fragment_user_BtnpayStatusRecord).setOnclick(new View                .OnClickListener() {            @Override            public void onClick(View v) {                LazyCatFragmetStartAct(SystemPayvipRecordAct.class);            }        });        /**         * 点击帮助中心         */        RelativeLayoutUnt.with(body, R.id.fragment_userBtntoService).setOnclick(new View                .OnClickListener() {            @Override            public void onClick(View v) {                WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT(http_addrs.HTTP_ADDR_TO_HELP);                web_values_act.set_ScanFitXY(false);                web_values_act.set_StaticColor("#ffffff");                web_values_act.set_TitleBackColor("#ffffff");                web_values_act.set_TitleColor("#020433");                LazyCatFragmentStartWevact(web_values_act);            }        });        /**         * 点击用户查询物流界面         */        RelativeLayoutUnt.with(body, R.id.fragment_user_btnDelivery).setOnclick(new View                .OnClickListener() {            @Override            public void onClick(View v) {                /*载入订单查询系统*/                LazyCatFragmentStartActivityWithBundler(ToorderService.class, LocalAction.ACTION,                        "1");            }        });        /*用户的足迹的按钮*/        body.findViewById(R.id.fragment_user_btnTrack).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                LazyCatFragmetStartAct(UsertrackAct.class);            }        });        /*关于我们的按钮*/        body.findViewById(R.id.fragment_user_btnAboutAs).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT(http_addrs.HTTP_ADDR_ABOUTTO_AS);                web_values_act.set_ScanFitXY(false);                web_values_act.set_StaticColor("#ffffff");                web_values_act.set_TitleBackColor("#ffffff");                web_values_act.set_TitleColor("#020433");                LazyCatFragmentStartWevact(web_values_act);            }        });        /*优惠卷的按钮*/        body.findViewById(R.id.fragment_user_btnInteger).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                if (TextUtils.isEmpty(St_account) || TextUtils.isEmpty(St_token)) {                    Toast.makeText(getContext(), getResources().getString(R.string.noLoginMsg),                            Toast.LENGTH_SHORT).show();                } else {                    LazyCatFragmetStartAct(SystemIntegralPostal.class);                }            }        });        /**         * 手动选择登录的         *         */        business_name.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                TextView tv = (TextView) v;                /*如果是没有登录的  就跳转去实现登录或者注册*/                if (tv.getText().toString().trim().equals(getResources().getString(R.string                        .noLoginView))) {                    LazyCatFragmetStartAct(LoginAct.class);                }            }        });        /**         * 交易记录的点击事件         */        body.findViewById(R.id.fragment_user_btnBrowse).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                if (!userToolsInstance.isLogin()) {                    Toast.makeText(getContext(), getResources().getString(R.string.noLoginMsg),                            Toast.LENGTH_SHORT).show();                } else {                    LazyCatFragmetStartAct(BrowseAct.class);                }            }        });        /**         * 通知系统发货记录         */        body.findViewById(R.id.fragment_user_btnSendSystem).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                if (!userToolsInstance.isLogin()) {                    Toast.makeText(getContext(), getResources().getString(R.string.noLoginMsg),                            Toast.LENGTH_SHORT).show();                } else {                    /*载入订单查询系统*/                    LazyCatFragmentStartActivityWithBundler(ToorderService.class, LocalAction                            .ACTION, "0");                }            }        });        /**         * 退出登录的按钮         */        body.findViewById(R.id.fragment_user_btnExit).setOnClickListener(new View.OnClickListener                () {            @SuppressLint("ResourceType")            @Override            public void onClick(View v) {                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());                View item = LayoutInflater.from(getContext()).inflate(R.layout.alert_message, null);                /*点击的按钮*/                TextView Tv_btnConfirm = item.findViewById(R.id.alert_messageBtnConfirm);                TextUnt.with(Tv_btnConfirm).setText("确定并清空登录数据").setTextColor("#ffffff")                        .setBackColor(getResources().getString(R.color.colorPrice));                Tv_btnConfirm.setOnClickListener(new View.OnClickListener() {                    @Override                    public void onClick(View v) {                        if (userToolsInstance.ClearLocalCach()) {                            Toast.makeText(getContext(), "数据清空成功", Toast.LENGTH_SHORT).show();                            init(body);                        } else {                            Toast.makeText(getContext(), "数据清空失败", Toast.LENGTH_SHORT).show();                        }                        alertDialog.dismiss();                    }                });                /*显示内容*/                TextView Tv_context = item.findViewById(R.id.alert_messageText);                Tv_context.setText(getResources().getString(R.string.LoginToout));                /*显示标题*/                TextView Tv_title = item.findViewById(R.id.alert_messageTitle);                TextUnt.with(Tv_title).setText("重要提示").setTextColor(getResources().getString(R                        .color.colorPrice));                builder.setView(item);                alertDialog = builder.show();            }        });    }    @SuppressLint({"NewApi", "ResourceType"})    private void init(final View item) {        /**         *登录检查         */        userToolsInstance = LocalProgramTools.getUserToolsInstance();        if (!userToolsInstance.isLogin()) {            /**             * 设置没有登录的界面             */            Log.e(MSG, "用户没有登录");            handlerUsernologin();        } else {            /*账户*/            St_account = userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                    .ACTION_LOCALUSERPAGE_ACCOUNT);            /*缓存KEY*/            St_token = userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                    .ACTION_LOCALUSERPAGE_TOKEN);            TextUnt.with(body, R.id.fragment_user_TvSendSystemhow).setVisibility(false);            TextUnt.with(TvBrowshow).setVisibility(true);            TextUnt.with(TvDeliveryhow).setVisibility(true);            /*开始登录检查*/            XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(false);            xmlInstance.initDom();            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, St_account);            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, St_token);            xmlInstance.overDom();            Net.doPostXml(getContext(), http_addrs.HTTP_ADDR_GETUSER_CENTERVALUES, new                    ProgramInterface() {                @Override                public void onSucess(String data, int code, WaitDialog.RefreshDialog                        _refreshDialog) {                    Log.i(MSG, "获取登录的用户的信息为:" + data.trim());                    /*获取数据信息进行处理*/                    _refreshDialog.dismiss();                    if (data.trim().equals(LocalValues.NET_ERROR)) {                        /*清空本地的数据库*/                        if (userToolsInstance.ClearLocalCach()) {                            Toast.makeText(getContext(), getResources().getString(R.string                                    .logintotime), Toast.LENGTH_SHORT).show();                        } else {                            /*无法删除文件*/                            Toast.makeText(getContext(), getResources().getString(R.string                                    .clearloginxmlerror), Toast.LENGTH_SHORT).show();                        }                        handlerUsernologin();                    } else {                        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                        xmlanalysisFactory.Startanalysis(new XmlanalysisFactory                                .XmlanalysisInterface() {                            @Override                            public void onFaile() {                            }                            @Override                            public void onStartDocument(String tag) {                                if (init_userCenter != null) {                                    init_userCenter = null;                                    init_userCenter = XmlTagValuesFactory                                            .getXmlServiceInitCenterInstance();                                } else {                                    init_userCenter = XmlTagValuesFactory                                            .getXmlServiceInitCenterInstance();                                }                            }                            @Override                            public void onStartTag(String tag, XmlPullParser pullParser, Integer                                    id) {                                try {                                    /*用户的余额*/                                    if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_BLANCE)) {                                        init_userCenter.setSt_userBalance(Float.parseFloat                                                (pullParser.nextText().trim()));                                    }                                    /*用户的状态*/                                    if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_STATUS)) {                                        init_userCenter.setSt_userStatus(pullParser.nextText()                                                .trim());                                    }                                    /*用户的VIP状态*/                                    if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_VIPSTATUS)) {                                        init_userCenter.setSt_userVip(pullParser.nextText().trim());                                    }                                    /*VIP的过期时间*/                                    if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_VIPOVERDATE)) {                                        init_userCenter.setSt_userVipoverDate(pullParser.nextText                                                ().trim());                                    }                                    /*抵用券的数量*/                                    if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_COUPON)) {                                        init_userCenter.setSt_coupon(pullParser.nextText().trim());                                    }                                    /*交易记录*/                                    if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_TRANSACTION)) {                                        init_userCenter.setSt_transaction(pullParser.nextText()                                                .trim());                                    }                                    /*通知发货记录*/                                    if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_SENDSYSTEM)) {                                        init_userCenter.setSt_sendsystem(pullParser.nextText()                                                .trim());                                    }                                    /*充值记录*/                                    if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_RECHARGERECORD)) {                                        init_userCenter.setSt_rechargerecord(pullParser.nextText                                                ().trim());                                    }                                    /*派送记录*/                                    if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_DELIVER)) {                                        init_userCenter.setSt_deliver(pullParser.nextText().trim());                                    }                                    /*获取积分*/                                    if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_INTEGER)) {                                        init_userCenter.setSt_integral(pullParser.nextText().trim                                                ());                                    }                                } catch (Exception e) {                                    Log.e(MSG, "解析服务器回传的数据失败:" + e.getMessage());                                }                            }                            @Override                            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            }                            @Override                            public void onEndDocument() {                                if (init_userCenter.getSt_userVip().equals(LocalValues                                        .VALUES_USERCENTER.IS_NOT_VIP)) {                                    /*不是VIP*/                                    TextUnt.with(business_vip).setText("加入Vip").setBackground                                            (Tools.CreateDrawable(1, getResources().getString(R                                                    .color.colornoVip), getResources().getString                                                    (R.color.colornoVip), 50)).setTextColor                                            ("#ffffff");                                } else {                                    /*是VIP*/                                    TextUnt.with(business_vip).setText("Vip").setTextColor                                            (getResources().getString(R.color.colorVip));                                    TextUnt.with(business_vip).setText("Vip").setBackground(Tools                                            .CreateDrawable(1, getResources().getString(R.color                                                    .colorVip), getResources().getString(R.color                                                    .colorVip), 50)).setTextColor("#ffffff");                                    TextUnt.with(item, R.id.fragment_user_UservipDone).setText                                            (Tools.StamptoDate(init_userCenter                                                    .getSt_userVipoverDate()));                                }                                /*设置商户名称*/                                TextUnt.with(business_name).setText(userToolsInstance                                        .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                                                .ACTION_LOCALUSERPAGE_SHOPNAME));                                /*设置店铺的经纬度*/                                TextUnt.with(TvBusinessLonglat).setText("经度:" + userToolsInstance                                        .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                                                .ACTION_LOCALUSERPAGE_SHOPLONG) + "|维度:" +                                        userToolsInstance.GetUserpageOnAction(LocalAction                                                .ACTION_LOCALUSERPAGE                                                .ACTION_LOCALUSERPAGE_SHOPLAT)).setTextColor                                        ("#020433");                                /*设置积分数量*/                                TextUnt.with(TvIntegral).setText(init_userCenter                                        .getSt_integral().trim());                                /*设置交易记录*/                                TextUnt.with(TvBrowshow).setText(init_userCenter                                        .getSt_transaction().trim());                                /*设置通知系统发货数*/                                if (init_userCenter.getSt_sendsystem().equals("0")) {                                    TextUnt.with(body, R.id.fragment_user_TvSendSystemhow)                                            .setVisibility(false);                                } else {                                    TextUnt.with(body, R.id.fragment_user_TvSendSystemhow)                                            .setVisibility(true).setText(init_userCenter                                            .getSt_sendsystem());                                }                                /*设置派送的数量*/                                if (init_userCenter.getSt_deliver().equals("0") ||                                        init_userCenter.getSt_deliver().equals("")) {                                    TextUnt.with(TvDeliveryhow).setVisibility(false);                                } else {                                    TextUnt.with(TvDeliveryhow).setText(init_userCenter                                            .getSt_deliver().trim()).setVisibility(true);                                }                                /*设置交易成功的数量*/                                if (init_userCenter.getSt_transaction().equals("") ||                                        init_userCenter.getSt_transaction().equals("0")) {                                    TextUnt.with(item, R.id.fragment_user_TransactionHow)                                            .setVisibility(false);                                } else {                                    TextUnt.with(item, R.id.fragment_user_TransactionHow).setText                                            (init_userCenter.getSt_transaction()).setVisibility                                            (true);                                }                            }                        });                    }                }                @Override                public WaitDialog.RefreshDialog onStartLoad() {                    final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog                            (getActivity());                    WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                    wait_itme_dialogpage.setImg(R.id.item_wait_img);                    wait_itme_dialogpage.setView(R.layout.item_wait);                    wait_itme_dialogpage.setTitle(R.id.item_wait_title);                    refreshDialog.Init(wait_itme_dialogpage);                    refreshDialog.showRefreshDialog("请稍后...", false);                    return refreshDialog;                }                @Override                public void onFaile(String data, int code) {                }            }, xmlInstance.getXmlTree().trim());        }    }    /**     * 设置用户没有登录的界面     */    @SuppressLint("ResourceType")    private void handlerUsernologin() {        TextUnt.with(business_vip).setText("Vip").setBackground(Tools.CreateDrawable(1,                getResources().getString(R.color.colornoVip), getResources().getString(R.color                        .colornoVip), 50)).setTextColor("#ffffff");        /*设置没有登录的显示标题*/        TextUnt.with(business_name).setText(getResources().getString(R.string.noLoginView));        TextUnt.with(TvBusinessLonglat).setVisibility(false);/*设置经纬度不显示*/        TextUnt.with(TvIntegral).setText("0");/*设置积分为0*/        TextUnt.with(body, R.id.fragment_user_TvSendSystemhow).setVisibility(false);        TextUnt.with(TvBrowshow).setVisibility(false);        TextUnt.with(TvDeliveryhow).setVisibility(false);    }    @Override    public void onResume() {        Log.i(MSG, "onResume");        init(body);        super.onResume();    }    @Override    public void onPause() {        Log.i(MSG, "onPause");        super.onPause();    }    @Override    public void onStop() {        Log.i(MSG, "onStop");        super.onStop();    }    @Override    public void onStart() {        Log.i(MSG, "onStart");        super.onStart();    }}