package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.os.Bundle;import android.view.LayoutInflater;import android.view.View;import android.widget.LinearLayout;import android.widget.RelativeLayout;import android.widget.Toast;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyShopPage.LocalOrderpage;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;public class SystemTopayorder extends LazyCatAct {    private String MSG = "SystemTopayorder.java[+]";    private String systemId;    private LinearLayout ll_shoplistboyd;    private LocalProgramTools.UserToolsInstance userToolsInstance;    private ArrayList<LocalOrderpage> a_list = new ArrayList<LocalOrderpage>();    private LocalOrderpage localOrderpage;/*订单表格*/    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setTransparentBar();        setContentView(R.layout.activity_systemtopayorder);        systemId = getBundlerValue(LocalAction.ACTION_SYSTEMID.ACTION_ORDER);        TextUnt.with(this, R.id.assembly_act_headTitle).setText("收货清单");        userToolsInstance = LocalProgramTools.getUserToolsInstance();/*获取用户管理工具*/        init();        listener();        /*        Toast.makeText(getApplicationContext(),systemId.toString().trim(),Toast.LENGTH_LONG).show();        */    }    /**     * 监听事件     */    private void listener() {    }    /**     * 初始化操作     */    public void init() {        /*找到显示商品的标题*/        ll_shoplistboyd = findViewById(R.id.activity_systemtopayorder_shoplistbody);        XmlBuilder.XmlInstance xmlInstance = new XmlBuilder.XmlInstance();        xmlInstance.initDom();        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                        .ACTION_LOCALUSERPAGE_ACCOUNT));        xmlInstance.setXmlTree(LocalAction.ACTION_SENDORDER_SYSTEM.ACTION_ORDERNUMBER, systemId);        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, userToolsInstance                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TOKEN));        xmlInstance.overDom();        Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_TOSCANORDERLIST,                new ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                if (data.trim().equals(LocalValues.NET_ERROR)) {                    Toast.makeText(getApplicationContext(), "无法获取订单包数据信息,请检查", Toast                            .LENGTH_SHORT).show();                } else {                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            try {                                /*判断开头*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_START)) {                                    localOrderpage = new LocalOrderpage();                                }                                /*下单时候的Vip的状态*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDER_INVIPSTATUS)) {                                    localOrderpage.setSt_inorderVipstatus(pullParser.nextText()                                            .trim());                                }                                /*订单状态*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDER_STATUS)) {                                    localOrderpage.setSt_orderstatus(pullParser.nextText().trim());                                }                                /*设置商品价格对应的单位*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_COMPANY)) {                                    localOrderpage.setSt_company(pullParser.nextText().trim());                                }                                /*设置商品的虚线的价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_DLP)) {                                    localOrderpage.setSt_dlp(pullParser.nextText().trim());                                }                                /*设置商品的下单时间*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_SEND_TIME)) {                                    localOrderpage.setSt_ordertime(pullParser.nextText().trim());                                }                                /*设置商品的距离*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDER_DISTANCE)) {                                    localOrderpage.setSt_distance(pullParser.nextText().trim());                                }                                /*设置商品的图片*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_IMG)) {                                    localOrderpage.setSt_img(pullParser.nextText().trim());                                }                                /*获取商品标题*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_TITLE)) {                                    localOrderpage.setSt_title(pullParser.nextText().trim());                                }                                /*获取商品的唯一ID*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_ONLYID)) {                                    localOrderpage.setSt_onlyId(pullParser.nextText().trim());                                }                                /*获取商品的订购数量*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_TOPAYHOW)) {                                    localOrderpage.setSt_payHow(pullParser.nextText().trim());                                }                                /*设置商品的批发价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_TP)) {                                    localOrderpage.setSt_tp(pullParser.nextText().trim());                                }                                /*设置商品的规格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_SPEC)) {                                    localOrderpage.setSt_spec(pullParser.nextText().trim());                                }                                /*获取分割的最低单位*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_SPLITUNIT)) {                                    localOrderpage.setSt_splitunit(pullParser.nextText().trim());                                }                                /*设置保质期*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_PD)) {                                    localOrderpage.setSt_pd(pullParser.nextText().trim());                                }                                /*设置生产日期*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_EXP)) {                                    localOrderpage.setSt_exp(pullParser.nextText().trim());                                }                                /*设置订单号*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDERNUMBER)) {                                    localOrderpage.setSt_orderNumber(pullParser.nextText().trim());                                }                                /*设置到期时间戳*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDER_TIMESTAMP)) {                                    localOrderpage.setSt_arriveeventStamp(pullParser.nextText()                                            .trim());                                }                                /*设置订单的状态*/                                if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                        .ACTION_ORDER_STATUS)) {                                    localOrderpage.setSt_status(pullParser.nextText().trim());                                }                                /*商品的条码*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_BARCODE)) {                                    localOrderpage.setSt_orderNumber(pullParser.nextText().trim());                                }                            } catch (Exception e) {                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_START)) {                                a_list.add(localOrderpage);                                localOrderpage = null;                            }                        }                        @Override                        public void onEndDocument() {                        }                    });                }            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                return null;            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree().trim());        /*尝试载入30个ITEM*/        for (int i = 0; i < 30; i++) {            View item = LayoutInflater.from(getApplicationContext()).inflate(R.layout                    .item_searchshoplist, null);            ItemValuepage itemValuepage = new ItemValuepage();            /*找到控件ID和商品的唯一ID*/            itemValuepage.rl = item.findViewById(R.id.item_searchshoplist_coverlayer);            itemValuepage.onlyId = "123";            itemValuepage.iscover = false;            item.setTag(itemValuepage);            item.setOnLongClickListener(new View.OnLongClickListener() {                @Override                public boolean onLongClick(View v) {                    ItemValuepage vl = (ItemValuepage) v.getTag();                    /*添加罩层*/                    if (vl.iscover) {                        vl.iscover = false;                        vl.rl.setVisibility(View.GONE);                    } else {                        vl.iscover = true;                        /*添加罩层*/                        vl.rl.setVisibility(View.VISIBLE);                    }                    return false;                }            });            ll_shoplistboyd.addView(item);        }    }    class ItemValuepage {        RelativeLayout rl;        String onlyId;        boolean iscover;    }}