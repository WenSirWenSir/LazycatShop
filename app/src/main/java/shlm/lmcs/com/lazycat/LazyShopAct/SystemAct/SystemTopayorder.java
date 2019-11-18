package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.annotation.SuppressLint;import android.os.Bundle;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.RelativeLayout;import android.widget.Toast;import com.bumptech.glide.Glide;import com.bumptech.glide.load.engine.DiskCacheStrategy;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.RelativeLayoutUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopPage.LocalOrderpage;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;public class SystemTopayorder extends LazyCatAct {    private String MSG = "SystemTopayorder.java[+]";    private String systemId;    private LinearLayout ll_shoplistboyd;    private LocalProgramTools.UserToolsInstance userToolsInstance;    private ArrayList<LocalOrderpage> a_list = new ArrayList<LocalOrderpage>();    private LocalOrderpage localOrderpage;/*订单表格*/    private Boolean isVip;/*判断是否为VIP*/    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setTransparentBar();        setContentView(R.layout.activity_systemtopayorder);        systemId = getBundlerValue(LocalAction.ACTION_SYSTEMID.ACTION_ORDER);        TextUnt.with(this, R.id.assembly_act_headTitle).setText("收货清单");        userToolsInstance = LocalProgramTools.getUserToolsInstance();/*获取用户管理工具*/        init();        listener();        /*        Toast.makeText(getApplicationContext(),systemId.toString().trim(),Toast.LENGTH_LONG).show();        */    }    /**     * 监听事件     */    private void listener() {        /**         * 清单完毕  进入收银台         */        TextUnt.with(this, R.id.activity_systemtopayorder_btnDetailedOk).setOnClick(new View                .OnClickListener() {            @Override            public void onClick(View v) {                LazyCatStartActivityWithBundler(SystemCashier.class, false, LocalAction                        .WINDOWS_TO_WINDOWS.ACTION_TO_PAYMONEY, "299");            }        });    }    /**     * 初始化操作     */    public void init() {        /*重新获取商户的数据*/        getUserpageAgain();        /*找到显示商品的body*/        ll_shoplistboyd = findViewById(R.id.activity_systemtopayorder_shoplistbody);        /*清空数据信息*/        a_list.clear();        ll_shoplistboyd.removeAllViews();        XmlBuilder.XmlInstance xmlInstance = new XmlBuilder.XmlInstance();        xmlInstance.initDom();        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                        .ACTION_LOCALUSERPAGE_ACCOUNT));        xmlInstance.setXmlTree(LocalAction.ACTION_SENDORDER_SYSTEM.ACTION_ORDERNUMBER, systemId);        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TOKEN));        xmlInstance.overDom();        Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_TOSCANORDERLIST,                new ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                _refreshDialog.dismiss();                if (data.trim().equals(LocalValues.NET_ERROR)) {                    Toast.makeText(getApplicationContext(), "无法获取订单包数据信息,请检查", Toast                            .LENGTH_SHORT).show();                } else {                    Log.i(MSG, "获取的数据信息为:" + data.trim());                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            try {                                /*判断开头*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_START)) {                                    localOrderpage = new LocalOrderpage();                                }                                /*下单时候的Vip的状态*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDER_INVIPSTATUS)) {                                    localOrderpage.setSt_inorderVipstatus(pullParser.nextText()                                            .trim());                                    Log.i(MSG, "1");                                }                                /*订单状态*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDER_STATUS)) {                                    localOrderpage.setSt_orderstatus(pullParser.nextText().trim());                                    Log.i(MSG, "2");                                }                                /*设置商品价格对应的单位*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_COMPANY)) {                                    localOrderpage.setSt_company(pullParser.nextText().trim());                                    Log.i(MSG, "3");                                }                                /*设置商品的虚线的价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_DLP)) {                                    localOrderpage.setSt_dlp(pullParser.nextText().trim());                                    Log.i(MSG, "4");                                }                                /*设置商品的下单时间*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_SEND_TIME)) {                                    localOrderpage.setSt_ordertime(pullParser.nextText().trim());                                    Log.i(MSG, "5");                                }                                /*设置商品的距离*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDER_DISTANCE)) {                                    localOrderpage.setSt_distance(pullParser.nextText().trim());                                    Log.i(MSG, "6");                                }                                /*设置商品的图片*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_IMG)) {                                    localOrderpage.setSt_img(pullParser.nextText().trim());                                    Log.i(MSG, "7");                                }                                /*获取商品标题*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_TITLE)) {                                    localOrderpage.setSt_title(pullParser.nextText().trim());                                    Log.i(MSG, "8");                                }                                /*获取商品的唯一ID*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_ONLYID)) {                                    localOrderpage.setSt_onlyId(pullParser.nextText().trim());                                    Log.i(MSG, "9");                                }                                /*获取商品的订购数量*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_TOPAYHOW)) {                                    localOrderpage.setSt_payHow(pullParser.nextText().trim());                                    Log.i(MSG, "10");                                }                                /*设置商品的批发价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_TP)) {                                    localOrderpage.setSt_tp(pullParser.nextText().trim());                                    Log.i(MSG, "11");                                }                                /*设置商品的规格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_SPEC)) {                                    localOrderpage.setSt_spec(pullParser.nextText().trim());                                    Log.i(MSG, "12");                                }                                /*获取分割的最低单位*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_SPLITUNIT)) {                                    localOrderpage.setSt_splitunit(pullParser.nextText().trim());                                    Log.i(MSG, "13");                                }                                /*设置保质期*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_PD)) {                                    localOrderpage.setSt_pd(pullParser.nextText().trim());                                    Log.i(MSG, "14");                                }                                /*设置生产日期*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_EXP)) {                                    localOrderpage.setSt_exp(pullParser.nextText().trim());                                    Log.i(MSG, "15");                                }                                /*设置订单号*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDERNUMBER)) {                                    localOrderpage.setSt_orderNumber(pullParser.nextText().trim());                                    Log.i(MSG, "16");                                }                                /*设置到期时间戳*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDER_TIMESTAMP)) {                                    localOrderpage.setSt_arriveeventStamp(pullParser.nextText()                                            .trim());                                    Log.i(MSG, "17");                                }                                /*设置订单的状态*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDER_STATUS)) {                                    localOrderpage.setSt_orderstatus(pullParser.nextText().trim());                                    Log.i(MSG, "18");                                }                                /*商品的条码*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_BARCODE)) {                                    localOrderpage.setSt_barcode(pullParser.nextText().trim());                                    Log.i(MSG, "19");                                }                                /*设置商品状态*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_SAVESTATUS)) {                                    localOrderpage.setSt_shopstatus(pullParser.nextText().trim());                                    Log.i(MSG, "20");                                }                            } catch (Exception e) {                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_START)) {                                a_list.add(localOrderpage);                                localOrderpage = null;                            }                        }                        @Override                        public void onEndDocument() {                            initMainpage();                        }                    });                }            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                /*初始化一个DIALOG*/                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog                        (SystemTopayorder.this);                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                wait_itme_dialogpage.setImg(R.id.item_wait_img);                wait_itme_dialogpage.setView(R.layout.item_wait);                wait_itme_dialogpage.setCanClose(false);                wait_itme_dialogpage.setTitle(R.id.item_wait_title);                refreshDialog.Init(wait_itme_dialogpage);                refreshDialog.showRefreshDialog("加载中...", false);                return refreshDialog;            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree().trim());    }    /**     * 初始化 整理界面     */    @SuppressLint("ResourceType")    private void initMainpage() {        /*尝试载入30个ITEM*/        for (int i = 0; i < a_list.size(); i++) {            View item = LayoutInflater.from(getApplicationContext()).inflate(R.layout                    .item_sendsystemorderact_list, null);            ItemValuepage itemValuepage = new ItemValuepage();            itemValuepage.item = item;            /*设置订单号*/            itemValuepage.orderNumber = a_list.get(i).getSt_orderNumber();            /*找到控件ID和商品的唯一ID*/            itemValuepage.rl = item.findViewById(R.id.item_sendSystemorderAct_coverlayer);            /*设置标题*/            TextUnt.with(item, R.id.item_sendsystemorderact_list_shoptitle).setText(a_list.get(i)                    .getSt_title());            /*设置规格*/            TextUnt.with(item, R.id.item_sendsystemorderact_list_spec).setText(a_list.get(i)                    .getSt_company() + "装x" + a_list.get(i).getSt_spec() + a_list.get(i)                    .getSt_splitunit());            /*设置订单号*/            TextUnt.with(item, R.id.item_sendsystemorderact_list_orderNumber).setText(a_list.get                    (i).getSt_orderNumber());            /*设置订购数量*/            TextUnt.with(item, R.id.item_sendsystemorderact_list_sendTopay).setText("共计订购:" +                    a_list.get(i).getSt_payHow() + a_list.get(i).getSt_company());            /*设置保质期和生产日期*/            TextUnt.with(item, R.id.item_sendsystemorderact_list_expandpd).setText("生产日期:" +                    a_list.get(i).getSt_exp() + "|保质期:" + a_list.get(i).getSt_pd() + "天");            /*设置订单创建日期*/            TextUnt.with(item, R.id.item_sendsystemorderact_list_createTime).setText("订货单创建日期:" +                    a_list.get(i).getSt_ordertime());            /*计算需要支付的金额*/            TextUnt.with(item, R.id.item_sendsystemorderact_list_tp).setText(Tools.calcToRide                    (a_list.get(i).getSt_payHow(), a_list.get(i).getSt_tp()));            /*判断订单的状态*/            /*设置订单用户是否需要退货的按钮*/            /**             * 设置点击需要退货的监听事件             */            TextUnt.with(item, R.id.item_sendsystemorderact_list_userknow).setVisibility(true)                    .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color                            .ThemeColor), getResources().getString(R.color.ThemeColor), 5))                    .setTextColor("#ffffff").setOnClick(new View.OnClickListener() {                @Override                public void onClick(View v) {                    ItemValuepage vl = (ItemValuepage) v.getTag();                    LazyCatStartActivityWithBundler(SystemRefuseshop.class, false, LocalAction                            .WINDOWS_TO_WINDOWS.ACTION_ORDERNUMBER, vl.orderNumber);                }            }).setTag(itemValuepage);            /*设置是否显示*/            RelativeLayoutUnt.with(item, R.id.item_sendsystemorderact_list_userknowBody)                    .setVisibility(true);            TextUnt.with(item, R.id.item_sendsystemorderact_list_status).setVisibility(false);            switch (a_list.get(i).getSt_shopstatus()) {                case LocalValues.VALUES_SHOPPAGE.NORMAL:                    break;                case LocalValues.VALUES_SHOPPAGE.PROMOTION:                    TextUnt.with(item, R.id.item_sendsystemorderact_list_Shopstatus).setText                            (getResources().getString(R.string.payPromotion)).setTextColor                            ("#ffffff").setBackground(Tools.CreateDrawable(1, getResources()                            .getString(R.color.colorPromotion), getResources().getString(R.color                            .colorPromotion), 5));                    break;                case LocalValues.VALUES_SHOPPAGE.REDUCTION:                    TextUnt.with(item, R.id.item_sendsystemorderact_list_Shopstatus).setText                            (getResources().getString(R.string.payReduction)).setBackground(Tools                            .CreateDrawable(1, getResources().getString(R.color.colorReduction),                                    getResources().getString(R.color.colorReduction), 5))                            .setTextColor("#ffffff");                    break;                case LocalValues.VALUES_SHOPPAGE.VOLUME:                    TextUnt.with(item, R.id.item_sendsystemorderact_list_Shopstatus).setText                            (getResources().getString(R.string.payVolumn)).setBackground(Tools                            .CreateDrawable(1, getResources().getString(R.color.colorVolumn),                                    getResources().getString(R.color.colorVolumn), 5))                            .setTextColor("#ffffff");                    break;                case LocalValues.VALUES_SHOPPAGE.ONLY_VIP:                    TextUnt.with(item, R.id.item_sendsystemorderact_list_Shopstatus).setText                            (getResources().getString(R.string.payOnlyvip)).setBackground(Tools                            .CreateDrawable(1, getResources().getString(R.color.colorVip),                                    getResources().getString(R.color.colorVip), 5)).setTextColor                            ("#ffffff");                    break;                case LocalValues.VALUES_SHOPPAGE.ONLY_ONE:                    TextUnt.with(item, R.id.item_sendsystemorderact_list_Shopstatus).setText                            (getResources().getString(R.string.payOnlyone)).setBackground(Tools                            .CreateDrawable(1, getResources().getString(R.color.colorPayonly),                                    getResources().getString(R.color.colorPayonly), 5))                            .setTextColor("#ffffff");                    break;            }            itemValuepage.onlyId = "123";            itemValuepage.iscover = false;            item.setTag(itemValuepage);            item.setOnLongClickListener(new View.OnLongClickListener() {                @Override                public boolean onLongClick(View v) {                    ItemValuepage vl = (ItemValuepage) v.getTag();                    /*添加罩层*/                    if (vl.iscover) {                        vl.iscover = false;                        vl.rl.setVisibility(View.GONE);                        RelativeLayoutUnt.with(vl.item, R.id                                .item_sendsystemorderact_list_userknowBody).setVisibility(true);                    } else {                        vl.iscover = true;                        /*添加罩层*/                        vl.rl.setVisibility(View.VISIBLE);                        RelativeLayoutUnt.with(vl.item, R.id                                .item_sendsystemorderact_list_userknowBody).setVisibility(false);                    }                    return false;                }            });            /*加载图片*/            ImageView img = item.findViewById(R.id.item_sendsystemorderact_list_img);            Glide.with(getApplicationContext()).load(LocalValues.HTTP_ADDRS.HTTP_ADDR_IMG_URL +                    a_list.get(i).getSt_img()).diskCacheStrategy(DiskCacheStrategy.NONE)                    .skipMemoryCache(false).into(img);            ll_shoplistboyd.addView(item);        }    }    class ItemValuepage {        RelativeLayout rl;/*商品的点货的遮罩层*/        String onlyId;/*商品的唯一ID*/        String orderNumber;/*商品的唯一ID*/        View item;/*控件*/        boolean iscover;    }    /**     * 重新支付吊起  就重新获取数据信息     */    @Override    protected void onRestart() {        Log.i(MSG, "onRestart重新吊起");        init();        super.onRestart();    }    /**     * 展示用户的确定界面     */    private void getUserpageAgain() {        /**         * 重新获取用户的信息         *         * 用户的地址是不能由用户自己修改的 只能用户使用客服服务进行修改 并且需要重新登录         *         */        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder();        xmlInstance.initDom();        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                        .ACTION_LOCALUSERPAGE_ACCOUNT));/*存入账户*/        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TOKEN)        );/*存入密码*/        xmlInstance.overDom();        Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_GETUSER_VALUES,                new ProgramInterface() {            @Override            public void onSucess(String data, int code, final WaitDialog.RefreshDialog                    _refreshDialog) {                Log.i(MSG, "重新获取用户信息的请求:" + data.trim());                _refreshDialog.dismiss();                if (data.trim().equals(LocalValues.VALUES_LOGIN.LOGIN_ERROR)) {                    Toast.makeText(getApplicationContext(), "登录过期,请重新登录", Toast.LENGTH_SHORT)                            .show();                    if (userToolsInstance != null) {                        userToolsInstance.ClearLocalCach();                    }                } else {                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            /*判断是否是Vip客户*/                            try {                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE                                        .ACTION_LOCALUSERPAGE_VIPSTATUS)) {                                    if (pullParser.nextText().trim().equals(LocalValues                                            .VALUES_USERCENTER.IS_VIP)) {                                        isVip = true;                                    } else {                                        isVip = false;                                    }                                }                            } catch (Exception e) {                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                        }                        @Override                        public void onEndDocument() {                            _refreshDialog.dismiss();                            /*开始整理用户的界面*/                            initMainuser();                        }                    });                }            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                /*初始化一个DIALOG*/                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog                        (SystemTopayorder.this);                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                wait_itme_dialogpage.setImg(R.id.item_wait_img);                wait_itme_dialogpage.setView(R.layout.item_wait);                wait_itme_dialogpage.setCanClose(false);                wait_itme_dialogpage.setTitle(R.id.item_wait_title);                refreshDialog.Init(wait_itme_dialogpage);                refreshDialog.showRefreshDialog("加载中...", false);                return refreshDialog;            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree());    }    /**     * 整理用户的界面     */    private void initMainuser() {        /*设置用户的店铺的名称*/        TextUnt.with(this, R.id.activity_systemtopayorder_userName).setText(userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                        .ACTION_LOCALUSERPAGE_SHOPNAME));        /*设置用户的手机*/        TextUnt.with(this, R.id.activity_systemtopayorder_shopTel).setText(userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                        .ACTION_LOCALUSERPAGE_SHOPTEL));        /*设置用户的地址*/        TextUnt.with(this, R.id.activity_systemtopayorder_userAddr).setText(userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                        .ACTION_LOCALUSERPAGE_SHOPADDR));        /*设置用户的店铺负责人*/        TextUnt.with(this, R.id.activity_systemtopayorder_shopusePeople).setText                (userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                        .ACTION_LOCALUSERPAGE_SHOPUSEPEOPLE));    }}