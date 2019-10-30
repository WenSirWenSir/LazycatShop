package shlm.lmcs.com.lazycat.LazyShopFrg;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.os.Bundle;import android.text.TextUtils;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.TextView;import android.widget.Toast;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import java.util.LinkedList;import java.util.List;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.Numberincrease;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.WxPay.SingValues;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.WxPay.WxpayinitInstance;import shlm.lmcs.com.lazycat.LazyShopAct.BrowseAct;import shlm.lmcs.com.lazycat.LazyShopAct.LoginAct;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;public class UserCenterfrg extends LazyCatFragment {    private String MSG = "UserCenterfrg.java[+]";    private LinearLayout CardBody;    private XmlTagValuesFactory.XMLValuesUserpageCard userpageCard = null;    private XmlTagValuesFactory.XMLValuesUserpageBtnItem item = null;/*控件BTN*/    private XmlTagValuesFactory.Init_UserCenterValues init_userCenter = null;    private ArrayList<XmlTagValuesFactory.XMLValuesUserpageCard> cardList = new            ArrayList<XmlTagValuesFactory.XMLValuesUserpageCard>();    private Numberincrease business_balance;    private TextView business_name;    private TextView business_vip;    private TextView business_coupon;    private TextView business_integral;    private TextView business_rechargerecord;    private TextView btn_addVip;    private TextView btn_recharge;    private TextView TvBrowshow;    private TextView TvSendSystemhow;    private TextView TvDeliveryhow;    private TextView TvBusinessLonglat;/*店铺的经纬度*/    private String St_account;    private String St_token;/*缓存登录钥匙*/    private String St_long;/*维度*/    private String St_lat;/*经度*/    private String St_busniessname;/*店铺全称*/    private String St_businessaddr;/*店铺的地址*/    private String St_businessPeople;/*店铺的负责人*/    private String St_businessTel;/*店铺的电话*/    private View body;    private WxpayinitInstance wxpayinitInstance;    private AlertDialog alertDialog;/*退出*/    private String Paykey;    private String _perpayId;    private LocalProgramTools.UserToolsInstance userToolsInstance;    @SuppressLint({"NewApi", "ResourceType"})    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle            savedInstanceState) {        body = inflater.inflate(R.layout.fragment_user, null);        /*获取主题的颜色  */        setStatusBar(getResources().getString(R.color.ThemeColor));        /*我的功能body*///        LinearLayout mefuctionBody = item.findViewById(R.id.usercenterfrg_mefuctionBody);        body.findViewById(R.id.usercenterfrg_userValuesBody).setBackground(Tools.CreateDrawable                (1, "#ffffff", "#ffffff", 15));        /*设置订单导航的body*/        body.findViewById(R.id.fragment_user_orderNav).setBackground(Tools.CreateDrawable(1,                "#ffffff", "#ffffff", 15));        /*设置其他功能的导航的Body*/        body.findViewById(R.id.fragment_user_otherNav).setBackground(Tools.CreateDrawable(1,                "#ffffff", "#ffffff", 15));        CardBody = body.findViewById(R.id.usercenterfrg_cardBody);/*添加card的body*/        /*这个显示余额*/        business_balance = body.findViewById(R.id.fragmen_user_balance);/*        business_balance.setValues(80.98f);*/        /*这个现实商户的名称*/        business_name = body.findViewById(R.id.fragmen_user_business_name);        /*这个现实VIP*/        business_vip = body.findViewById(R.id.fragment_user_vip);        /*这个是显示优惠卷*/        business_coupon = body.findViewById(R.id.fragmen_user_coupon);        /*这个显示积分*/        business_integral = body.findViewById(R.id.fragment_user_TvIntegral);        /*这个显示交易的次数*/        business_rechargerecord = body.findViewById(R.id.fragment_user_TvRechargerecord);        /*加入VIP的选项*/        btn_addVip = body.findViewById(R.id.fragmen_user_btnAddVip);        /*充值按钮*/        btn_recharge = body.findViewById(R.id.fragmen_user_btnRecharge);        /*圆形的显示数量*/        TvBrowshow = body.findViewById(R.id.fragment_user_TvBrowshow);        TextUnt.with(TvBrowshow).setBackground(Tools.CreateDrawable(1, getResources().getString(R                .color.ThemeColor), getResources().getString(R.color.ThemeColor), 50));        /*圆形的显示数量*/        TvSendSystemhow = body.findViewById(R.id.fragment_user_TvSendSystemhow);        TextUnt.with(TvSendSystemhow).setBackground(Tools.CreateDrawable(1, getResources()                .getString(R.color.ThemeColor), getResources().getString(R.color.ThemeColor), 50));        /*圆形的显示数量*/        TvDeliveryhow = body.findViewById(R.id.fragment_user_TvDeliveryhow);        TextUnt.with(TvDeliveryhow).setBackground(Tools.CreateDrawable(1, getResources()                .getString(R.color.ThemeColor), getResources().getString(R.color.ThemeColor), 50));        /*店铺的经纬度*/        TvBusinessLonglat = body.findViewById(R.id.fragment_user_TvLongLat);        /*初始化充值按钮的样式*/        TextUnt.with(btn_recharge).setBackground(Tools.CreateDrawable(1, "#08c299", "#08c299", 5));        /**         /*初始化Card*/        /* *//*4个导航*//*        for (int i = 0; i < 4; i++) {            RelativeLayout rl = new RelativeLayout(getContext());*//*创建布局*//*            rl.setBackground(Tools.CreateDrawable(1, "#e9e9e9", "#e9e9e9", 5));            *//*设置权重*//*            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout                    .LayoutParams.WRAP_CONTENT, 1.0f);            params.setMargins(10, 10, 10, 10);            *//*设置图片*//*            RelativeLayout.LayoutParams ivParams = new RelativeLayout.LayoutParams(100, 100);            ImageView iv = new ImageView(getContext());            ivParams.addRule(RelativeLayout.CENTER_HORIZONTAL);            ivParams.setMargins(0, 50, 0, 0);            iv.setLayoutParams(ivParams);            iv.setImageResource(R.drawable.ico_bell);            rl.addView(iv);            *//*加入图片布局*//*            rl.setLayoutParams(params);            *//*设置文字*//*            RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(RelativeLayout                    .LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);            TextView title = new TextView(getContext());            title.setText("这是第" + i);            title.setTextColor(Color.parseColor("#ffffff"));            title.setTextSize(12);            tvParams.setMargins(0, 160, 0, 0);            title.setLayoutParams(tvParams);            title.setGravity(Gravity.CENTER);            rl.addView(title);            mefuctionBody.addView(rl);        }*//*        item.findViewById(R.id.usercenterfrg_mefuction).setBackground(Tools.CreateDrawable(1,                "#e9e9e9", "#ffffff", 5));*/        ;        setTransparentBar();        init(body);        listener(body);        return body;    }    /**     * 控件监听     *     * @param body     */    @SuppressLint("NewApi")    private void listener(final View body) {        /*优惠卷的按钮*/        body.findViewById(R.id.fragment_user_btnCoupon).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                if (TextUtils.isEmpty(St_account) || TextUtils.isEmpty(St_token)) {                    Toast.makeText(getContext(), getResources().getString(R.string.noLoginMsg),                            Toast.LENGTH_SHORT).show();                }            }        });        /**         * 选择充值         */        btn_recharge.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                /**                 * 尝试获取订单                 */                List<SingValues> singvalues = new LinkedList<SingValues>();/*                WxpayinitInstance wxpayinitInstance = new WxpayinitInstance("支付测试", "支付测试",                        "jfkdj93jg8djskl238", "2948839929", "1", "APP");*/                wxpayinitInstance = new WxpayinitInstance(getContext(), "支付测试", "支付测试",                        "jfkdj93jg8djskl238", "2948839929", "1", "APP");                Net.doPostXml(getContext(), "https://api.mch.weixin.qq.com/pay/unifiedorder", new                        ProgramInterface() {                    @Override                    public void onSucess(String data, int code, WaitDialog.RefreshDialog                            _refreshDialog) {                        Log.i(MSG, "微信初始化申请订单的回传数据:" + data.trim());                        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                        xmlanalysisFactory.Startanalysis(new XmlanalysisFactory                                .XmlanalysisInterface() {                            @Override                            public void onFaile() {                            }                            @Override                            public void onStartDocument(String tag) {                            }                            @Override                            public void onStartTag(String tag, XmlPullParser pullParser, Integer                                    id) {                                try {                                    if (tag.equals("prepay_id")) {                                        _perpayId = pullParser.nextText().trim();                                        //开始注册APP到微信                                    }                                } catch (Exception e) {                                }                            }                            @Override                            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            }                            @Override                            public void onEndDocument() {                                wxpayinitInstance.startWxPay(_perpayId);                            }                        });                    }                    @Override                    public WaitDialog.RefreshDialog onStartLoad() {                        return null;                    }                    @Override                    public void onFaile(String data, int code) {                    }                }, wxpayinitInstance.getXmldata());            }        });        /*充值记录*/        body.findViewById(R.id.fragment_user_btnRechargerecord).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                if (TextUtils.isEmpty(St_account) || TextUtils.isEmpty(St_token)) {                    Toast.makeText(getContext(), getResources().getString(R.string.noLoginMsg),                            Toast.LENGTH_SHORT).show();                }            }        });        /*积分*/        body.findViewById(R.id.fragment_user_Btnintegral).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                if (TextUtils.isEmpty(St_account) || TextUtils.isEmpty(St_token)) {                    Toast.makeText(getContext(), getResources().getString(R.string.noLoginMsg),                            Toast.LENGTH_SHORT).show();                }            }        });        /**         * 手动选择登录的         *         */        business_name.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                TextView tv = (TextView) v;                /*如果是没有登录的  就跳转去实现登录或者注册*/                if (tv.getText().toString().trim().equals(getResources().getString(R.string                        .noLoginView))) {                    LazyCatFragmetStartAct(LoginAct.class);                }            }        });        /**         * 浏览记录的点击事件         */        body.findViewById(R.id.fragment_user_btnBrowse).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                if (!userToolsInstance.isLogin()) {                    Toast.makeText(getContext(), getResources().getString(R.string.noLoginMsg),                            Toast.LENGTH_SHORT).show();                } else {                    LazyCatFragmetStartAct(BrowseAct.class);                }            }        });        /**         * 通知系统发货记录         */        body.findViewById(R.id.fragment_user_btnSendSystem).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                if (!userToolsInstance.isLogin()) {                    Toast.makeText(getContext(), getResources().getString(R.string.noLoginMsg),                            Toast.LENGTH_SHORT).show();                } else {                }            }        });        /**         * 正在派送的订单记录         */        body.findViewById(R.id.fragment_user_btnDelivery).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                if (!userToolsInstance.isLogin()) {                    Toast.makeText(getContext(), getResources().getString(R.string.noLoginMsg),                            Toast.LENGTH_SHORT).show();                } else {                }            }        });        /**         * 退出登录的按钮         */        body.findViewById(R.id.fragment_user_btnExit).setOnClickListener(new View.OnClickListener                () {            @SuppressLint("ResourceType")            @Override            public void onClick(View v) {                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());                View item = LayoutInflater.from(getContext()).inflate(R.layout.alert_message, null);                /*点击的按钮*/                TextView Tv_btnConfirm = item.findViewById(R.id.alert_messageBtnConfirm);                TextUnt.with(Tv_btnConfirm).setText("确定并清空登录数据").setTextColor("#ffffff")                        .setBackColor(getResources().getString(R.color.colorPrice));                Tv_btnConfirm.setOnClickListener(new View.OnClickListener() {                    @Override                    public void onClick(View v) {                        if (userToolsInstance.ClearLocalCach()) {                            Toast.makeText(getContext(), "数据清空成功", Toast.LENGTH_SHORT).show();                            init(body);                        } else {                            Toast.makeText(getContext(), "数据清空失败", Toast.LENGTH_SHORT).show();                        }                        alertDialog.dismiss();                    }                });                /*显示内容*/                TextView Tv_context = item.findViewById(R.id.alert_messageText);                Tv_context.setText(getResources().getString(R.string.LoginToout));                /*显示标题*/                TextView Tv_title = item.findViewById(R.id.alert_messageTitle);                TextUnt.with(Tv_title).setText("重要提示").setTextColor(getResources().getString(R                        .color.colorPrice));                builder.setView(item);                alertDialog = builder.show();            }        });    }    @SuppressLint({"NewApi", "ResourceType"})    private void init(View item) {        /**         *登录检查         */        userToolsInstance = LocalProgramTools.getUserToolsInstance();        if (!userToolsInstance.isLogin()) {            /**             * 设置没有登录的界面             */            Log.e(MSG, "用户没有登录");            TextUnt.with(btn_addVip).setText("加入VIP").setBackground(Tools.CreateDrawable(1,                    getResources().getString(R.color.colornoVip), getResources().getString(R                            .color.colornoVip), 50)).setTextColor("#ffffff");            TextUnt.with(business_vip).setText("VIP").setBackground(Tools.CreateDrawable(1,                    getResources().getString(R.color.colornoVip), getResources().getString(R                            .color.colornoVip), 50)).setTextColor("#ffffff");            /*设置没有登录的显示标题*/            TextUnt.with(business_name).setText(getResources().getString(R.string.noLoginView));            TextUnt.with(TvBusinessLonglat).setVisibility(false);/*设置经纬度不显示*/            TextUnt.with(business_coupon).setText("0");/*设置优惠卷为0*/            TextUnt.with(business_integral).setText("0");/*设置积分为0*/            TextUnt.with(business_rechargerecord).setText("0");/*设置交易记录*/            business_balance.setText("0.00");            TextUnt.with(TvSendSystemhow).setVisibility(false);            TextUnt.with(TvBrowshow).setVisibility(false);            TextUnt.with(TvDeliveryhow).setVisibility(false);        } else {            /*账户*/            St_account = userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                    .ACTION_LOCALUSERPAGE_ACCOUNT);            /*缓存KEY*/            St_token = userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                    .ACTION_LOCALUSERPAGE_TOKEN);            TextUnt.with(TvSendSystemhow).setVisibility(true);            TextUnt.with(TvBrowshow).setVisibility(true);            TextUnt.with(TvDeliveryhow).setVisibility(true);            /*开始登录检查*/            XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder();            xmlInstance.initDom();            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, St_account);            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, St_token);            xmlInstance.overDom();            Net.doPostXml(getContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_GETUSER, new                    ProgramInterface() {                @Override                public void onSucess(String data, int code, WaitDialog.RefreshDialog                        _refreshDialog) {                    Log.i(MSG, "获取登录的用户的信息为:" + data.trim());                    /*获取数据信息进行处理*/                    _refreshDialog.dismiss();                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                            if (init_userCenter != null) {                                init_userCenter = null;                                init_userCenter = XmlTagValuesFactory                                        .getXmlServiceInitCenterInstance();                            } else {                                init_userCenter = XmlTagValuesFactory                                        .getXmlServiceInitCenterInstance();                            }                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            try {                                /*用户的余额*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_BLANCE)) {                                    init_userCenter.setSt_userBalance(Float.parseFloat(pullParser                                            .nextText().trim()));                                }                                /*用户的状态*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_STATUS)) {                                    init_userCenter.setSt_userStatus(pullParser.nextText().trim());                                }                                /*用户的VIP状态*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_VIPSTATUS)) {                                    init_userCenter.setSt_userVip(pullParser.nextText().trim());                                }                                /*VIP的过期时间*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_VIPOVERDATE)) {                                    init_userCenter.setSt_userVipoverDate(pullParser.nextText()                                            .trim());                                }                                /*抵用券的数量*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_COUPON)) {                                    init_userCenter.setSt_coupon(pullParser.nextText().trim());                                }                                /*积分的数量*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_INTEGER)) {                                    init_userCenter.setSt_integer(pullParser.nextText().trim());                                }                                /*交易记录*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_TRANSACTION)) {                                    init_userCenter.setSt_transaction(pullParser.nextText().trim());                                }                                /*通知发货记录*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_SENDSYSTEM)) {                                    init_userCenter.setSt_sendsystem(pullParser.nextText().trim());                                }                                /*充值记录*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_RECHARGERECORD)) {                                    init_userCenter.setSt_rechargerecord(pullParser.nextText()                                            .trim());                                }                                /*派送记录*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_DELIVER)) {                                    init_userCenter.setSt_deliver(pullParser.nextText().trim());                                }                            } catch (Exception e) {                                Log.e(MSG, "解析服务器回传的数据失败:" + e.getMessage());                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                        }                        @Override                        public void onEndDocument() {                            /**                             * 处理界面                             */                            Log.i(MSG, "余额:" + init_userCenter.getSt_userBalance());                            /*设置余额*/                            business_balance.setValues(init_userCenter.getSt_userBalance());                            /*余额动画*/                            new Thread(business_balance).start();                            if (init_userCenter.getSt_userVip().equals(LocalValues                                    .VALUES_USERCENTER.IS_NOT_VIP)) {                                /*不是VIP*/                                TextUnt.with(business_vip).setText("VIP").setBackground(Tools                                        .CreateDrawable(1, getResources().getString(R.color                                                .colornoVip), getResources().getString(R.color                                                .colornoVip), 50)).setTextColor("#ffffff");                                /*显示加入vip*/                                TextUnt.with(btn_addVip).setText("加入VIP").setBackground(Tools                                        .CreateDrawable(1, getResources().getString(R.color                                                .colornoVip), getResources().getString(R.color                                                .colornoVip), 50)).setTextColor("#ffffff");                            } else {                                /*是VIP*/                                TextUnt.with(business_vip).setText("VIP").setTextColor                                        (getResources().getString(R.color.colorVip));                                TextUnt.with(business_vip).setText("VIP").setBackground(Tools                                        .CreateDrawable(1, getResources().getString(R.color                                                .colorVip), getResources().getString(R.color                                                .colorVip), 50)).setTextColor("#ffffff");                                /*显示VIP特权*/                                TextUnt.with(btn_addVip).setText("VIP特权").setBackground(Tools                                        .CreateDrawable(1, getResources().getString(R.color                                                .colorVip), getResources().getString(R.color                                                .colorVip), 50)).setTextColor("#ffffff");                            }                            /*设置商户名称*/                            TextUnt.with(business_name).setText(userToolsInstance                                    .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_SHOPNAME));                            /*设置店铺的经纬度*/                            TextUnt.with(TvBusinessLonglat).setText("经度:" + userToolsInstance                                    .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                                            .ACTION_LOCALUSERPAGE_SHOPLONG) + "|维度:" +                                    userToolsInstance.GetUserpageOnAction(LocalAction                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_SHOPLAT))                                    .setTextColor("#020433");                            /*设置抵用券的数量*/                            TextUnt.with(business_coupon).setText(init_userCenter.getSt_coupon()                                    .trim());                            /*设置积分数量*/                            TextUnt.with(business_integral).setText(init_userCenter.getSt_integer                                    ().trim());                            /*设置充值记录*/                            TextUnt.with(business_rechargerecord).setText(init_userCenter                                    .getSt_rechargerecord().trim());                            /*设置交易记录*/                            TextUnt.with(TvBrowshow).setText(init_userCenter.getSt_transaction()                                    .trim());                            /*设置通知系统发货数*/                            TextUnt.with(TvSendSystemhow).setText(init_userCenter                                    .getSt_sendsystem().trim());                            /*设置派送的数量*/                            TextUnt.with(TvDeliveryhow).setText(init_userCenter.getSt_deliver()                                    .trim());                        }                    });                }                @Override                public WaitDialog.RefreshDialog onStartLoad() {                    final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog                            (getActivity());                    WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                    wait_itme_dialogpage.setImg(R.id.item_wait_img);                    wait_itme_dialogpage.setView(R.layout.item_wait);                    wait_itme_dialogpage.setTitle(R.id.item_wait_title);                    refreshDialog.Init(wait_itme_dialogpage);                    refreshDialog.showRefreshDialog("请稍后...", false);                    return refreshDialog;                }                @Override                public void onFaile(String data, int code) {                }            }, xmlInstance.getXmlTree().trim());        }    }    @Override    public void onResume() {        Log.i(MSG, "onResume");        init(body);        super.onResume();    }    @Override    public void onPause() {        Log.i(MSG, "onPause");        super.onPause();    }    @Override    public void onStop() {        Log.i(MSG, "onStop");        super.onStop();    }    @Override    public void onStart() {        Log.i(MSG, "onStart");        super.onStart();    }}