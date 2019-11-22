package shlm.lmcs.com.lazycat.LazyShopAct;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.os.Bundle;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.AdapterView;import android.widget.BaseAdapter;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.ListView;import android.widget.Toast;import com.bumptech.glide.Glide;import com.bumptech.glide.load.engine.DiskCacheStrategy;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.RelativeLayoutUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemSeeOrderDetailsact;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramShopTools;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;/** * 该activity为2个窗口公有  订单系统  派送系统 */public class ToorderService extends LazyCatAct {    private String MSG = "ToorderService.java[+]";    private LocalProgramTools.UserToolsInstance userToolsInstance;    private LinearLayout body;    private String action;    private ArrayList<SendOrderpage> Ar_list = new ArrayList<SendOrderpage>();/*数据集合*/    private SendOrderpage sendOrderpage = null;    private ListView _listview;    /*适配器*/    private listAdapter listAdapter;/*处理订单界面的适配器*/    /*没有货的POSITION*/    private int _refusPosition = -1;    @Override    protected void onCreate(Bundle savedInstanceState) {        setTransparentBar();        setContentView(R.layout.activity_sendsystemorderact);        userToolsInstance = LocalProgramTools.getUserToolsInstance();        body = findViewById(R.id.activity_sendsystemorderact_body);        /*listVIew*/        _listview = findViewById(R.id.activity_sendsystemorderact_listview);        /*获取ACTION*/        action = getBundlerValue(LocalAction.ACTION);        if (action.equals("0")) {            TextUnt.with(this, R.id.assembly_act_headTitle).setText("订货单列表");        } else {            TextUnt.with(this, R.id.assembly_act_headTitle).setText("派送单列表");        }        init();        super.onCreate(savedInstanceState);    }    /**     * 界面初始化     */    private void init() {        /*清空数据*/        Ar_list.clear();        if (userToolsInstance.isLogin()) {            final XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);            /*aciton*/            xmlInstance.setXmlTree(LocalAction.ACTION, action);            xmlInstance.overDom();            Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_ORDER_TOOLS,                    new ProgramInterface() {                @Override                public void onSucess(String data, int code, WaitDialog.RefreshDialog                        _refreshDialog) {                    Log.i(MSG, "获取订货单的数据回传：" + data.trim());                    if (LocalValues.NET_ERROR.equals(data.trim())) {                        /*错误*/                        _listview.setVisibility(View.GONE);                        RelativeLayoutUnt.with(ToorderService.this, R.id                                .activity_sendsystemOrderact_nodataBody).setVisibility(true);                    } else {                        /*设置显示ListView*/                        _listview.setVisibility(View.VISIBLE);                        RelativeLayoutUnt.with(ToorderService.this, R.id                                .activity_sendsystemOrderact_nodataBody).setVisibility(false);                        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                        xmlanalysisFactory.Startanalysis(new XmlanalysisFactory                                .XmlanalysisInterface() {                            @Override                            public void onFaile() {                            }                            @Override                            public void onStartDocument(String tag) {                            }                            @Override                            public void onStartTag(String tag, XmlPullParser pullParser, Integer                                    id) {                                try {                                    /*判断是否订货单的开头*/                                    if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                            .ACTION_ORDER_PAGE_BEGIN)) {                                        if (sendOrderpage != null) {                                            sendOrderpage = null;                                        }                                        sendOrderpage = new SendOrderpage();                                    }                                    /*商品的唯一ID*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_ONLYID)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_onlyId(pullParser.nextText().trim                                                    ());                                            Log.i(MSG, "1");                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的订购数量*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_TOPAYHOW)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_payHow(pullParser.nextText().trim                                                    ());                                            Log.i(MSG, "2");                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的定格批发价格*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_TP)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_tp(pullParser.nextText().trim());                                            Log.i(MSG, "3");                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的定格箱规*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_SPEC)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_spec(pullParser.nextText().trim());                                            Log.i(MSG, "4");                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的定格拆分单位*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_SPLITUNIT)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_splitunit(pullParser.nextText()                                                    .trim());                                            Log.i(MSG, "5");                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的保质期*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_PD)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_pd(pullParser.nextText().trim());                                            Log.i(MSG, "6");                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的生产日期*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_EXP)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_exp(pullParser.nextText().trim());                                            Log.i(MSG, "7");                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的订单号*/                                    if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                            .ACTION_ORDERNUMBER)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_orderNumber(pullParser.nextText()                                                    .trim());                                            Log.i(MSG, "8");                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的订单创建时间*/                                    if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                            .ACTION_SEND_TIME)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_sendTime(pullParser.nextText()                                                    .trim());                                            Log.i(MSG, "9");                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品订单的状态*/                                    if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                            .ACTION_ORDER_STATUS)) {                                        if (sendOrderpage != null) {                                            sendOrderpage.setSt_status(pullParser.nextText().trim                                                    ());                                        } else {                                            Log.e(MSG, "解析发货单的子类发生错误 sendorderpage is null");                                        }                                    }                                    /*商品的价格对应的单位*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_COMPANY)) {                                        sendOrderpage.setSt_company(pullParser.nextText().trim());                                    }                                    /*商品的定格图片地址*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_IMG)) {                                        sendOrderpage.setSt_img(pullParser.nextText().trim());                                    }                                    /*商品的定格标题*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_TITLE)) {                                        sendOrderpage.setSt_title(pullParser.nextText().trim());                                    }                                } catch (Exception e) {                                    Log.e(MSG, "解析发货单发生错误:" + e.getMessage());                                }                            }                            @Override                            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                                try {                                    if (tag.equals(LocalAction.ACTION_SENDORDER_SYSTEM                                            .ACTION_ORDER_PAGE_BEGIN)) {                                        Ar_list.add(sendOrderpage);                                        sendOrderpage = null;                                    }                                } catch (Exception e) {                                    Log.e(MSG, "XML信息解读错误,错误信息:" + e.getMessage());                                }                            }                            @Override                            public void onEndDocument() {                                /*订单结束 开始整理界面*/                                Log.i(MSG, "XML处理完毕");                                Log.i(MSG, "获取的订单的记录:" + Ar_list.size());                                initMainpage();                            }                        });                    }                    _refreshDialog.dismiss();                }                @Override                public WaitDialog.RefreshDialog onStartLoad() {                    /*初始化一个DIALOG*/                    final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog                            (ToorderService.this);                    WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                    wait_itme_dialogpage.setImg(R.id.item_wait_img);                    wait_itme_dialogpage.setView(R.layout.item_wait);                    wait_itme_dialogpage.setCanClose(false);                    wait_itme_dialogpage.setTitle(R.id.item_wait_title);                    refreshDialog.Init(wait_itme_dialogpage);                    refreshDialog.showRefreshDialog("加载中...", false);                    return refreshDialog;                }                @Override                public void onFaile(String data, int code) {                }            }, xmlInstance.getXmlTree().trim());            Listener();        } else {            finish();        }    }    /**     * 处理完XML整理界面     */    private void initMainpage() {        /*测试加载布局文件*/        listAdapter = new listAdapter();        _listview.setAdapter(listAdapter);    }    /**     * 事件监听     */    private void Listener() {        /**         * 点击关闭界面         */        findViewById(R.id.assembly_act_headBackImg).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                finish();            }        });        /**         * item的点击事件         */        _listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {            @Override            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {                LazyCatStartActivityWithBundler(SystemSeeOrderDetailsact.class, false,                        LocalAction.WINDOWS_TO_WINDOWS.ACTION_ORDERNUMBER, Ar_list.get(position)                                .getSt_orderNumber().trim(), LocalAction.ACTION, action);            }        });    }    /**     * 整理发货单的适配器     */    class listAdapter extends BaseAdapter {        @Override        public int getCount() {            return Ar_list.size();        }        @Override        public Object getItem(int position) {            return position;        }        @Override        public long getItemId(int position) {            return position;        }        @SuppressLint("ResourceType")        @Override        public View getView(int position, View convertView, ViewGroup parent) {            valueId valueId;            if (convertView != null) {                valueId = (listAdapter.valueId) convertView.getTag();            } else {                /*设置重复布局文件*/                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout                        .item_sendsystemorderact_list, null);                valueId = new valueId();                /*保存IMG的控件*/                valueId.img = convertView.findViewById(R.id.item_sendsystemorderact_list_img);                /*保存订单号*/                valueId.St_orderNumber = Ar_list.get(position).getSt_orderNumber();                convertView.setTag(valueId);            }            /**             * 开始整理ITEM的界面             */            TextUnt.with(convertView, R.id.item_sendsystemorderact_list_orderNumber).setText                    (Ar_list.get(position).getSt_orderNumber().trim());            /*检查商品的接单状态*/            switch (Ar_list.get(position).getSt_status()) {                case LocalValues.VALUES_SENDSYSTEM.IN_SEND:                    /*业务订单开始,用户提交。服务器未知状态*/                    Log.i(MSG, "业务订单开始,用户提交。服务器未知状态");                    TextUnt.with(convertView, R.id.item_sendsystemorderact_list_status).setText                            (getResources().getString(R.string.SendOrderStatusIN_SNED))                            .setBackColor(getResources().getString(R.color.ThemeColor));                    TextUnt.with(convertView, R.id.item_sendsystemorderact_list_userknow)                            .setVisibility(false);                    break;                case LocalValues.VALUES_DELIVER.IN_NOTSTOCKNUMBER:                    /*业务订单第三阶段,核对商品后没有库存*/                    TextUnt.with(convertView, R.id.item_sendsystemorderact_list_status).setText                            (getResources().getString(R.string.SendOrderStatusIN_REFUSE))                            .setBackColor(getResources().getString(R.color.colorMoney));                    Log.i(MSG, "业务订单第三阶段,核对商品后没有库存");                    /*显示我已知晓的按钮*/                    TextUnt.with(convertView, R.id.item_sendsystemorderact_list_userknow)                            .setVisibility(true).setBackground(Tools.CreateDrawable(1,                            getResources().getString(R.color.ThemeColor), getResources()                                    .getString(R.color.ThemeColor), 5)).setText("查看原因");                    break;                case LocalValues.VALUES_SENDSYSTEM.IN_GETSEND:                    TextUnt.with(convertView, R.id.item_sendsystemorderact_list_status).setText                            (getResources().getString(R.string.SendOrderStatusIN_GETSEND))                            .setBackColor(getResources().getString(R.color.ThemeColor));                    TextUnt.with(convertView, R.id.item_sendsystemorderact_list_userknow)                            .setVisibility(false);                    /*业务订单第二阶段,用户提交。已经打印成功*/                    Log.i(MSG, "业务订单第二阶段,用户提交。已经打印成功");                    break;                case LocalValues.VALUES_SENDSYSTEM.IN_REFUSE:                    /*拒绝接受订单*/                    TextUnt.with(convertView, R.id.item_sendsystemorderact_list_status).setText                            (getResources().getString(R.string.SendOrderStatusIN_REFUSE))                            .setBackColor(getResources().getString(R.color.ThemeColor));                    TextUnt.with(convertView, R.id.item_sendsystemorderact_list_userknow)                            .setVisibility(false);                    /*业务订单第二阶段,用户提交。已经打印成功*/                    Log.i(MSG, "业务订单第二阶段,用户提交。已经打印成功");                    break;            }            /*载入图片*/            Glide.with(getApplicationContext()).load("http://f.freep.cn/583105/SHOP_DATABASE/" +                    Ar_list.get(position).getSt_img()).diskCacheStrategy(DiskCacheStrategy.NONE)                    .skipMemoryCache(false).into(valueId.img);            /*商品的定格标题*/            TextUnt.with(convertView, R.id.item_sendsystemorderact_list_shoptitle).setText                    (Ar_list.get(position).getSt_title());            /*订单的时间*/            TextUnt.with(convertView, R.id.item_sendsystemorderact_list_createTime).setText                    ("订货单创建日期:" + Ar_list.get(position).getSt_sendTime());            /*生产日期和保质期*/            TextUnt.with(convertView, R.id.item_sendsystemorderact_list_expandpd).setText("生产日期:"                    + Ar_list.get(position).getSt_exp() + "|保质期:" + Ar_list.get(position)                    .getSt_pd() + "天");            /*设置打包的规格  需要修改服务器回传的打包大卫开始组合*/            TextUnt.with(convertView, R.id.item_sendsystemorderact_list_spec).setText("商品的规格:" +                    Ar_list.get(position).getSt_company() + "X" + Ar_list.get(position)                    .getSt_spec() + Ar_list.get(position).getSt_splitunit());            /*设置订购的数量  以打包的单位*/            TextUnt.with(convertView, R.id.item_sendsystemorderact_list_sendTopay).setText("每" +                    Ar_list.get(position).getSt_company() + "的价格:" + Ar_list.get(position)                    .getSt_tp());            /*设置价钱*/            TextUnt.with(convertView, R.id.item_sendsystemorderact_list_tp).setText(Tools                    .calcToRide(Ar_list.get(position).getSt_tp(), Ar_list.get(position)                            .getSt_payHow()));            LocalProgramShopTools shopTools = new LocalProgramShopTools();          /*  shopTools.setAt_activity(SendSystemOrderAct.this);            shopTools.setSt_dottenPrice("");            shopTools.setSt_status(Ar_list.get(position).getSt_status());            shopTools.setVe_status(R.id.item_sendsystemorderact_list_Shopstatus);            shopTools.handlerBegin();*/            /*移除ITEM的点击事件*/            /*设置移除的POSITION*/            TextUnt.with(convertView, R.id.item_sendsystemorderact_list_userknow).setOnClick(new View.OnClickListener() {                @Override                public void onClick(View v) {                    _refusPosition = (int) v.getTag();                    AlertDialog.Builder builder = new AlertDialog.Builder(ToorderService.this);                    View item = LayoutInflater.from(getApplicationContext()).inflate(R.layout                            .alert_message, null);                    builder.setView(item);                    /*设置提示的MSG*/                    TextUnt.with(item, R.id.alert_messageText).setText(getResources().getString(R                            .string.norefusMsg));                    TextUnt.with(item, R.id.alert_messageTitle).setText("商品无货通知");                    TextUnt.with(item, R.id.alert_messageBtnConfirm).setOnClick(new View                            .OnClickListener() {                        @Override                        public void onClick(View v) {                            if (_refusPosition >= 0) {                                Ar_list.remove(_refusPosition);                                listAdapter.notifyDataSetChanged();                                Toast.makeText(getApplicationContext(), "订单取消成功", Toast                                        .LENGTH_SHORT).show();                                AlertDialog alertDialog = (AlertDialog) v.getTag();                                alertDialog.dismiss();                            }                        }                    }).setTag(builder.show()).setText("我已经知晓");                }            }).setTag(position);            return convertView;        }        class valueId {            ImageView img;            String St_orderNumber;/*订单号码*/        }    }    /**     * 发货单的类     */    class SendOrderpage {        String St_onlyId;        String St_payHow;        String St_tp;        String St_spec;        String St_splitunit;        String St_pd;        String St_exp;        String St_orderNumber;        String St_sendTime;        String St_status;        String St_barcode;        String St_title;        String St_img;        String St_company;        public String getSt_barcode() {            return St_barcode;        }        public void setSt_barcode(String st_barcode) {            St_barcode = st_barcode;        }        public String getSt_title() {            return St_title;        }        public void setSt_title(String st_title) {            St_title = st_title;        }        public String getSt_img() {            return St_img;        }        public void setSt_img(String st_img) {            St_img = st_img;        }        public String getSt_company() {            return St_company;        }        public void setSt_company(String st_company) {            St_company = st_company;        }        public String getSt_onlyId() {            return St_onlyId;        }        public void setSt_onlyId(String st_onlyId) {            St_onlyId = st_onlyId;        }        public String getSt_payHow() {            return St_payHow;        }        public void setSt_payHow(String st_payHow) {            St_payHow = st_payHow;        }        public String getSt_tp() {            return St_tp;        }        public void setSt_tp(String st_tp) {            St_tp = st_tp;        }        public String getSt_spec() {            return St_spec;        }        public void setSt_spec(String st_spec) {            St_spec = st_spec;        }        public String getSt_splitunit() {            return St_splitunit;        }        public void setSt_splitunit(String st_splitunit) {            St_splitunit = st_splitunit;        }        public String getSt_pd() {            return St_pd;        }        public void setSt_pd(String st_pd) {            St_pd = st_pd;        }        public String getSt_exp() {            return St_exp;        }        public void setSt_exp(String st_exp) {            St_exp = st_exp;        }        public String getSt_orderNumber() {            return St_orderNumber;        }        public void setSt_orderNumber(String st_orderNumber) {            St_orderNumber = st_orderNumber;        }        public String getSt_sendTime() {            return St_sendTime;        }        public void setSt_sendTime(String st_sendTime) {            St_sendTime = st_sendTime;        }        public String getSt_status() {            return St_status;        }        public void setSt_status(String st_status) {            St_status = st_status;        }    }    @Override    protected void onRestart() {        init();        super.onRestart();    }}