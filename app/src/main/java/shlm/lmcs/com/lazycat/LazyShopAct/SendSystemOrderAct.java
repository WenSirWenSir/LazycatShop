package shlm.lmcs.com.lazycat.LazyShopAct;import android.os.Bundle;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.LinearLayout;import android.widget.ListView;import android.widget.Toast;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;public class SendSystemOrderAct extends LazyCatAct {    private String MSG = "SendSystemOrderAct.java[+]";    private LocalProgramTools.UserToolsInstance userToolsInstance;    private LinearLayout body;    private ArrayList<SendOrderpage> Ar_list = new ArrayList<SendOrderpage>();/*数据集合*/    private SendOrderpage sendOrderpage = null;    private ListView _listview;    @Override    protected void onCreate(Bundle savedInstanceState) {        setTransparentBar();        setContentView(R.layout.activity_sendsystemorderact);        userToolsInstance = LocalProgramTools.getUserToolsInstance();        body = findViewById(R.id.activity_sendsystemorderact_body);        /*listVIew*/        _listview = findViewById(R.id.activity_sendsystemorderact_listview);        /*测试加载布局文件*/        listAdapter listAdapter = new listAdapter();        _listview.setAdapter(listAdapter);        init();        super.onCreate(savedInstanceState);    }    /**     * 界面初始化     */    private void init() {        if (userToolsInstance.isLogin()) {            TextUnt.with(this, R.id.assembly_act_headTitle).setText("订货单列表");            final XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder();            xmlInstance.initDom();            /*账户*/            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, userToolsInstance                    .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                            .ACTION_LOCALUSERPAGE_ACCOUNT));            /*Token*/            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, userToolsInstance                    .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE                            .ACTION_LOCALUSERPAGE_TOKEN));            xmlInstance.overDom();            Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS                    .HTTP_ADDR_GETSENDORDER_LIST, new ProgramInterface() {                @Override                public void onSucess(String data, int code, WaitDialog.RefreshDialog                        _refreshDialog) {                    if (LocalValues.NET_ERROR.equals(data.trim())) {                        /*错误*/                        Toast.makeText(getApplicationContext(), "不好意思,没有您的货单记录", Toast                                .LENGTH_SHORT).show();                    } else {                        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                        xmlanalysisFactory.Startanalysis(new XmlanalysisFactory                                .XmlanalysisInterface() {                            @Override                            public void onFaile() {                            }                            @Override                            public void onStartDocument(String tag) {                            }                            @Override                            public void onStartTag(String tag, XmlPullParser pullParser, Integer                                    id) {                                try {                                    /*判断是否订货单的开头*/                                    if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                            .ACTION_ORDER_PAGE_BEGIN)) {                                        if (sendOrderpage != null) {                                            sendOrderpage = null;                                        }                                        sendOrderpage = new SendOrderpage();                                    }                                    /*商品的唯一ID*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_ONLYID)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_onlyId(pullParser.nextText().trim                                                    ());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的订购数量*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_TOPAYHOW)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_payHow(pullParser.nextText().trim                                                    ());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的定格批发价格*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_TP)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_tp(pullParser.nextText().trim());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的定格箱规*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_SPEC)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_spec(pullParser.nextText().trim());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的定格拆分单位*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_SPLITUNIT)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_splitunit(pullParser.nextText()                                                    .trim());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的保质期*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_PD)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_pd(pullParser.nextText().trim());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的生产日期*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_EXP)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_exp(pullParser.nextText().trim());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的订单号*/                                    if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                            .ACTION_ORDERNUMBER)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_orderNumber(pullParser.nextText()                                                    .trim());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的订单创建时间*/                                    if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                            .ACTION_SEND_TIME)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_sendTime(pullParser.nextText()                                                    .trim());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品订单的状态*/                                    if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                            .ACTION_ORDER_STATUS)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_status(pullParser.nextText().trim                                                    ());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                } catch (Exception e) {                                    Log.e(MSG, "解析发货单发生错误:" + e.getMessage());                                }                            }                            @Override                            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            }                            @Override                            public void onEndDocument() {                            }                        });                    }                    _refreshDialog.dismiss();                    Log.i(MSG, "获取订货单的数据回传：" + data.trim());                }                @Override                public WaitDialog.RefreshDialog onStartLoad() {                    /*初始化一个DIALOG*/                    final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog                            (SendSystemOrderAct.this);                    WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                    wait_itme_dialogpage.setImg(R.id.item_wait_img);                    wait_itme_dialogpage.setView(R.layout.item_wait);                    wait_itme_dialogpage.setCanClose(false);                    wait_itme_dialogpage.setTitle(R.id.item_wait_title);                    refreshDialog.Init(wait_itme_dialogpage);                    refreshDialog.showRefreshDialog("加载中...", false);                    return refreshDialog;                }                @Override                public void onFaile(String data, int code) {                }            }, xmlInstance.getXmlTree().trim());            Listener();        } else {            finish();        }    }    /**     * 事件监听     */    private void Listener() {        /**         * 点击关闭界面         */        findViewById(R.id.assembly_act_headBackImg).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                finish();            }        });    }    /**     * 整理发货单的适配器     */    class listAdapter extends BaseAdapter {        @Override        public int getCount() {            return 80;        }        @Override        public Object getItem(int position) {            return position;        }        @Override        public long getItemId(int position) {            return position;        }        @Override        public View getView(int position, View convertView, ViewGroup parent) {            valueId valueId;            if (convertView != null) {                valueId = (listAdapter.valueId) convertView.getTag();            } else {                /*设置重复布局文件*/                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout                        .item_sendsystemorderact_list, null);                valueId = new valueId();                convertView.setTag(valueId);            }            return convertView;        }        class valueId {        }    }    /**     * 发货单的类     */    class SendOrderpage {        String St_onlyId;        String St_payHow;        String St_tp;        String St_spec;        String St_splitunit;        String St_pd;        String St_exp;        String St_orderNumber;        String St_sendTime;        String St_status;        public String getSt_onlyId() {            return St_onlyId;        }        public void setSt_onlyId(String st_onlyId) {            St_onlyId = st_onlyId;        }        public String getSt_payHow() {            return St_payHow;        }        public void setSt_payHow(String st_payHow) {            St_payHow = st_payHow;        }        public String getSt_tp() {            return St_tp;        }        public void setSt_tp(String st_tp) {            St_tp = st_tp;        }        public String getSt_spec() {            return St_spec;        }        public void setSt_spec(String st_spec) {            St_spec = st_spec;        }        public String getSt_splitunit() {            return St_splitunit;        }        public void setSt_splitunit(String st_splitunit) {            St_splitunit = st_splitunit;        }        public String getSt_pd() {            return St_pd;        }        public void setSt_pd(String st_pd) {            St_pd = st_pd;        }        public String getSt_exp() {            return St_exp;        }        public void setSt_exp(String st_exp) {            St_exp = st_exp;        }        public String getSt_orderNumber() {            return St_orderNumber;        }        public void setSt_orderNumber(String st_orderNumber) {            St_orderNumber = st_orderNumber;        }        public String getSt_sendTime() {            return St_sendTime;        }        public void setSt_sendTime(String st_sendTime) {            St_sendTime = st_sendTime;        }        public String getSt_status() {            return St_status;        }        public void setSt_status(String st_status) {            St_status = st_status;        }    }}