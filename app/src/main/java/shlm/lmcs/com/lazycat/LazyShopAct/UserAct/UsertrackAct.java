package shlm.lmcs.com.lazycat.LazyShopAct.UserAct;import android.annotation.SuppressLint;import android.os.Bundle;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.ImageView;import android.widget.ListView;import android.widget.Toast;import com.bumptech.glide.Glide;import com.bumptech.glide.load.engine.DiskCacheStrategy;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopPage.LocalShoppage;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;/** * 用户的足迹 */public class UsertrackAct extends LazyCatAct {    private ListView _listView;/*展示的容器*/    private LocalProgramTools.UserToolsInstance userToolsInstance;    private String MSG = "UsertrackAct.java[+]";    /*装载商品参数的容器*/    private ArrayList<LocalShoppage> Ar_list;    private LocalShoppage shopvalues = null;    @Override    protected void onCreate(Bundle savedInstanceState) {        setContentView(R.layout.activity_usertrackact);        setStatusBar("#ffffff");        userToolsInstance = LocalProgramTools.getUserToolsInstance();        /*要装载适配器的容器*/        _listView = findViewById(R.id.activity_usertrackact_listview);        /*设置标题*/        TextUnt.with(this, R.id.assembly_act_headTitle).setText("足迹");        init();        super.onCreate(savedInstanceState);    }    /**     * 界面初始化     */    private void init() {        if (userToolsInstance.isLogin()) {            XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);            xmlInstance.overDom();            LocalValues.HTTP_ADDRS http_addrs = LocalValues.getHttpaddrs(getApplicationContext());            Net.doPostXml(getApplicationContext(), http_addrs                    .HTTP_ADDR_GETUSER_TRACK, new ProgramInterface() {                @Override                public void onSucess(String data, int code, WaitDialog.RefreshDialog                        _refreshDialog) {                    Log.i(MSG, "回传的数据:" + data.trim());                    _refreshDialog.dismiss();                    if (data.trim().equals(LocalValues.NET_ERROR)) {                        Toast.makeText(getApplicationContext(), "不好意思,您暂时没有登录云仓库哦", Toast                                .LENGTH_SHORT).show();                    } else {                        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                        xmlanalysisFactory.Startanalysis(new XmlanalysisFactory                                .XmlanalysisInterface() {                            @Override                            public void onFaile() {                            }                            @Override                            public void onStartDocument(String tag) {                                Ar_list = new ArrayList<>();                            }                            @Override                            public void onStartTag(String tag, XmlPullParser pullParser, Integer                                    id) {                                try {                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_START)) {                                        if (shopvalues != null) {                                        } else {                                            shopvalues = new LocalShoppage();                                        }                                    }                                    /*商品标题*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_TITLE)) {                                        shopvalues.setTitle(pullParser.nextText().trim());                                    }                                    /*商品条码*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_BARCODE)) {                                        shopvalues.setBarcode(pullParser.nextText().trim());                                    }                                    /*商品归属*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_ASCRIPTION)) {                                        shopvalues.setAscription(pullParser.nextText().trim());                                    }                                    /*商品的品牌*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_BRAND)) {                                        shopvalues.setBrand(pullParser.nextText().trim());                                    }                                    /*商品的零售价格*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_RETAIL)) {                                        shopvalues.setRetail(pullParser.nextText().trim());                                    }                                    /*商品单位*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_COMPANY)) {                                        shopvalues.setCompany(pullParser.nextText().trim());                                    }                                    /*商品生产日期*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_EXP)) {                                        shopvalues.setExp(pullParser.nextText().trim());                                    }                                    /*商品的归属*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_GRADE)) {                                        shopvalues.setGrade(pullParser.nextText().trim());                                    }                                    /*商品的产地*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_INFROM)) {                                        shopvalues.setInfrom(pullParser.nextText().trim());                                    }                                    /*商品唯一标识 */                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_ONLYID)) {                                        shopvalues.setOnlyid(pullParser.nextText().trim());                                    }                                    /*商品的保质期*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_PD)) {                                        shopvalues.setPd(pullParser.nextText().trim());                                    }                                    /*商品的终端建议售价*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_PRICE)) {                                        shopvalues.setPrice(pullParser.nextText().trim());                                    }                                    /*商品的规格*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_SPEC)) {                                        shopvalues.setSpec(pullParser.nextText().trim());                                    }                                    /*商品的状态*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_STATIC)) {                                        shopvalues.set_static(pullParser.nextText().trim());                                    }                                    /*商品的起订数量*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_SU)) {                                        shopvalues.setSu(pullParser.nextText().trim());                                    }                                    /*商品的批发价格*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_TP)) {                                        shopvalues.setTp(pullParser.nextText().trim());                                    }                                    /*商品的净重*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_WEIGHT)) {                                        shopvalues.setWeight(pullParser.nextText().trim());                                    }                                    /*商品的虚线的价格*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_DLP)) {                                        shopvalues.setDlp(pullParser.nextText().trim());                                    }                                    /*商品的图片地址*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_IMG)) {                                        shopvalues.setImg(pullParser.nextText().trim());                                    }                                    /*商品的最低的组合单位*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_SPLITUNIT)) {                                        shopvalues.setSplitUnit(pullParser.nextText().trim());                                    }                                    /*商品的对接商家*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_BUSINSS)) {                                        shopvalues.setBusiness(pullParser.nextText().trim());                                    }                                } catch (Exception e) {                                }                            }                            @Override                            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_START)) {                                    Ar_list.add(shopvalues);                                    shopvalues = null;                                }                            }                            @Override                            public void onEndDocument() {                                _Adapter _adapter = new _Adapter();                                _listView.setAdapter(_adapter);                            }                        });                    }                }                @Override                public WaitDialog.RefreshDialog onStartLoad() {                    final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog                            (UsertrackAct.this);                    WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                    wait_itme_dialogpage.setImg(R.id.item_wait_img);                    wait_itme_dialogpage.setView(R.layout.item_wait);                    wait_itme_dialogpage.setTitle(R.id.item_wait_title);                    refreshDialog.Init(wait_itme_dialogpage);                    refreshDialog.showRefreshDialog("请稍后...", false);                    return refreshDialog;                }                @Override                public void onFaile(String data, int code) {                }            }, xmlInstance.getXmlTree().trim());        } else {            Toast.makeText(getApplicationContext(), "您没有登录,请您登录后使用", Toast.LENGTH_SHORT).show();        }        Listener();    }    /**     * 监听事件     */    private void Listener() {        findViewById(R.id.assembly_act_headBackImg).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                finish();            }        });    }    class _Adapter extends BaseAdapter {        @Override        public int getCount() {            return Ar_list.size();        }        @Override        public Object getItem(int position) {            return position;        }        @Override        public long getItemId(int position) {            return position;        }        @SuppressLint("ResourceType")        @Override        public View getView(int position, View convertView, ViewGroup parent) {            valuesId valuesId;            if (convertView != null) {                valuesId = (_Adapter.valuesId) convertView.getTag();            } else {                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout                        .item_searchshoplist, null);                valuesId = new valuesId();                valuesId.img = convertView.findViewById(R.id.item_searchshoplist_img);/*设置图片*/                /*设置Tag*/                convertView.setTag(valuesId);            }            /*设置图片地址*/            Glide.with(getApplicationContext()).load("http://f.freep.cn/583105/SHOP_DATABASE/" +                    Ar_list.get(position).getImg()).diskCacheStrategy(DiskCacheStrategy.NONE)                    .skipMemoryCache(false).into(valuesId.img);            /*设置商品的标题*/            TextUnt.with(convertView, R.id.item_searchshoplist_title).setText(Ar_list.get                    (position).getTitle().trim());            /*设置价格对应的单位*/            TextUnt.with(convertView, R.id.item_searchshoplist_company).setText("/" + Ar_list.get                    (position).getCompany());            /*设置商家*/            TextUnt.with(convertView, R.id.item_searchshoplist_businessName).setText("云仓库专送");            switch (Ar_list.get(position).get_static()) {                case LocalValues.VALUES_SHOPPAGE.NORMAL:                    /*商品的正常价格*/                    TextUnt.with(convertView, R.id.item_searchshoplist_price).setText(Ar_list.get                            (position).getTp()).setTextColor(getResources().getString(R.color                            .colorPrice));                    TextUnt.with(convertView, R.id.item_searchshoplist_titleStatic).setVisibility                            (false);/*不显示状态*/                    break;                case LocalValues.VALUES_SHOPPAGE.PROMOTION:                    /*商品的促销价格*/                    TextUnt.with(convertView, R.id.item_searchshoplist_price).setText(Ar_list.get                            (position).getTp()).setTextColor(getResources().getString(R.color                            .colorPromotion));                    TextUnt.with(convertView, R.id.item_searchshoplist_titleStatic).setVisibility                            (true).setText(R.string.shop_reserve).setTextColor("#ffffff")                            .setBackground(Tools.CreateDrawable(1, getResources().getString(R                                    .color.colorPromotion), getResources().getString(R.color                                    .colorPromotion), 5));                    break;                case LocalValues.VALUES_SHOPPAGE.REDUCTION:                    /*商品的满减价格*/                    TextUnt.with(convertView, R.id.item_searchshoplist_price).setText(Ar_list.get                            (position).getTp()).setTextColor(getResources().getString(R.color                            .colorReduction));                    TextUnt.with(convertView, R.id.item_searchshoplist_titleStatic).setVisibility                            (true).setText(R.string.shop_reserve).setTextColor("#ffffff")                            .setBackground(Tools.CreateDrawable(1, getResources().getString(R                                    .color.colorReduction), getResources().getString(R.color                                    .colorReduction), 5));                    break;                case LocalValues.VALUES_SHOPPAGE.VOLUME:                    /*商品的领劵价格*/                    TextUnt.with(convertView, R.id.item_searchshoplist_price).setText(Ar_list.get                            (position).getTp()).setTextColor(getResources().getString(R.color                            .colorVolumn));                    TextUnt.with(convertView, R.id.item_searchshoplist_titleStatic).setVisibility                            (true).setText(R.string.shop_reserve).setTextColor("#ffffff")                            .setBackground(Tools.CreateDrawable(1, getResources().getString(R                                    .color.colorVolumn), getResources().getString(R.color                                    .colorVolumn), 5));                    break;                case LocalValues.VALUES_SHOPPAGE.ONLY_VIP:                    /*商品的VIP价格*/                    TextUnt.with(convertView, R.id.item_searchshoplist_price).setText(Ar_list.get                            (position).getTp()).setTextColor(getResources().getString(R.color                            .colorVip));                    TextUnt.with(convertView, R.id.item_searchshoplist_titleStatic).setVisibility                            (true).setText(R.string.shop_reserve).setTextColor("#ffffff")                            .setBackground(Tools.CreateDrawable(1, getResources().getString(R                                    .color.colorVip), getResources().getString(R.color                                    .colorVip), 5));                    break;                case LocalValues.VALUES_SHOPPAGE.ONLY_ONE:                    /*商品的限定价格*/                    TextUnt.with(convertView, R.id.item_searchshoplist_price).setText(Ar_list.get                            (position).getTp()).setTextColor(getResources().getString(R.color                            .colorPayonly));                    TextUnt.with(convertView, R.id.item_searchshoplist_titleStatic).setVisibility                            (true).setText(R.string.shop_reserve).setTextColor("#ffffff")                            .setBackground(Tools.CreateDrawable(1, getResources().getString(R                                    .color.colorPayonly), getResources().getString(R.color                                    .colorPayonly), 5));                    break;                case LocalValues.VALUES_SHOPPAGE.WHOLEASALE:                    /*商品拼团*/                    TextUnt.with(convertView, R.id.item_searchshoplist_price).setText(Ar_list.get                            (position).getTp()).setTextColor(getResources().getString(R.color                            .colorWholeasale));                    TextUnt.with(convertView, R.id.item_searchshoplist_titleStatic).setVisibility                            (true).setText(R.string.shop_wholeasale);                    break;                case LocalValues.VALUES_SHOPPAGE.RESERVE:                    /*商品预定*/                    TextUnt.with(convertView, R.id.item_searchshoplist_price).setText(Ar_list.get                            (position).getTp()).setTextColor(getResources().getString(R.color                            .colorReserve));                    TextUnt.with(convertView, R.id.item_searchshoplist_titleStatic).setVisibility                            (true).setText(R.string.shop_reserve).setTextColor("#ffffff")                            .setBackground(Tools.CreateDrawable(1, getResources().getString(R                                    .color.colorReserve), getResources().getString(R.color                                    .colorReserve), 5));                    break;            }            return convertView;        }        class valuesId {            ImageView img;        }    }}