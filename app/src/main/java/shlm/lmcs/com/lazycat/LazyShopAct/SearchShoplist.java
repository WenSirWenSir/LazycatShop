package shlm.lmcs.com.lazycat.LazyShopAct;import android.annotation.SuppressLint;import android.graphics.Color;import android.os.Bundle;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.view.animation.TranslateAnimation;import android.widget.AdapterView;import android.widget.BaseAdapter;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.ListView;import android.widget.RelativeLayout;import android.widget.TextView;import android.widget.Toast;import com.bumptech.glide.Glide;import com.bumptech.glide.load.engine.DiskCacheStrategy;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.OnLoadmoreListView;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.LazyShopVip.SystemVip;import shlm.lmcs.com.lazycat.R;import static shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config.Windows.GET_WINDOW_VALUE_SHOP_ACTION;public class SearchShoplist extends LazyCatAct implements OnLoadmoreListView._onScrollListener {    private ListView onLoadmoreListView;    private final String MSG = "SearchShoplist.java[+]";    private String search_key;    private SearchPage searchShoplist = null;/*商品的实例*/    private ImageView headBack_img;/*退出的图标*/    private TextView headTitle;/*标题*/    private ArrayList<SearchPage> Shop_arrays = new ArrayList<SearchPage>();/*所有的商品的实例集合*/    private LocalProgramTools.UserToolsInstance userToolsInstance;    /*地址工具类*/    private LocalValues.HTTP_ADDRS http_addrs;    /*是否登录*/    private Boolean isLogin;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_searchshoplist);        /*获取地址工具类*/        http_addrs = LocalValues.getHttpaddrs(getApplicationContext());        /**         * 找寻控件         */        search_key = getBundlerValue(LocalAction.WINDOWS_TO_WINDOWS.ACTION_SEARCH_KEY);        Log.i(MSG, "传送过来的Search_key为:" + search_key);        /*自定义的ListView*/        onLoadmoreListView = findViewById(R.id.activity_searchshoplist_showlistview);        setStatusBar("#efefef");        /*引入的标题*/        RelativeLayout head_layout = findViewById(R.id.activity_searchShoplistHead);        head_layout.setBackgroundColor(Color.parseColor("#efefef"));        /*退出的图标*/        headBack_img = head_layout.findViewById(R.id.assembly_act_headBackImg);        /*头部的标题*/        headTitle = head_layout.findViewById(R.id.assembly_act_headTitle);        TextUnt.with(headTitle).setText("搜索结果");        SystemVip systemVip = new SystemVip(SearchShoplist.this);        systemVip.Start(new SystemVip.OnVipcheck() {            @Override            public void onIsvip() {                init();            }            @Override            public void onIsnovip() {                init();            }            @Override            public void onIsnologin() {                isLogin = false;/*没有登录 不能显示价格*/                init();            }            @Override            public void onIslogin() {            }        });    }    public void init() {        userToolsInstance = LocalProgramTools.getUserToolsInstance();        if (search_key.equals("")) {            //为空            finish();        } else {            XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(false);            xmlInstance.initDom();            xmlInstance.setXmlTree(LocalAction.ACTION_SEARCHKEY.ACTION_KEYWORD, search_key.trim());            xmlInstance.overDom();            Net.doPostXml(http_addrs.HTTP_ADDR_SEARCH_SHOPLIST, new ProgramInterface() {                @Override                public void onSucess(String data, int code, WaitDialog.RefreshDialog                        _refreshDialog) {                    Log.i(MSG, "搜索产品返回数据信息:" + data.trim());                    /*成功返回数据信息之后  把Dialog销毁掉*/                    _refreshDialog.dismiss();                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                            /*开始解析*/                            Shop_arrays.clear();/*清空*/                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            try {                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_START)) {                                    /*解析到一个开始的头部*/                                    if (searchShoplist == null) {                                        searchShoplist = new SearchPage();                                    } else {                                        Log.i(MSG, "searchShoplist不为空");                                    }                                }                                /*标题*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_TITLE)) {                                    searchShoplist.set_title(pullParser.nextText().trim());                                }                                /*箱规*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_SPEC)) {                                    searchShoplist.set_spec(pullParser.nextText().trim());                                }                                /*价格对应的单位*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_COMPANY)) {                                    searchShoplist.set_company(pullParser.nextText().trim());                                }                                /*商品的拆分单位*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_SPLITUNIT)) {                                    searchShoplist.set_splitunit(pullParser.nextText().trim());                                }                                /*商品的生产日期*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_EXP)) {                                    searchShoplist.set_exp(pullParser.nextText().trim());                                }                                /*商品的批发价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_TP)) {                                    searchShoplist.set_tp(pullParser.nextText().trim());                                }                                /*商品的零售价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_RETAIL)) {                                    searchShoplist.set_retail(pullParser.nextText().trim());                                }                                /*商品的加盟价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_VIP_TP)) {                                    searchShoplist.set_viptp(pullParser.nextText().trim());                                }                                /*商品的虚线价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_DLP)) {                                    searchShoplist.set_dottenlineprice(pullParser.nextText().trim                                            ());                                }                                /*商品的图片地址*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_IMG)) {                                    searchShoplist.set_img(pullParser.nextText().trim());                                }                                /*商品的唯一地址*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_ONLYID)) {                                    searchShoplist.set_onlyid(pullParser.nextText().trim());                                }                                /*设置商品的库存*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES                                        .ACTION_SHOPVALUES_STOCKNUMBER)) {                                    searchShoplist.set_stocknumber(pullParser.nextText().trim());                                }                            } catch (Exception e) {                                Log.e(MSG, "xml解析错误信息:" + e.getMessage());                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_START)) {                                /*解析到尾部了*/                                Shop_arrays.add(searchShoplist);                                searchShoplist = null;                            }                        }                        @Override                        public void onEndDocument() {                            for (int i = 0; i < Shop_arrays.size(); i++) {                                Log.i(MSG, "商品的标题为:" + Shop_arrays.get(i).get_title());                            }                            SearchlistAdapter searchlistAdapter = new SearchlistAdapter                                    (Shop_arrays);                            onLoadmoreListView.setAdapter(searchlistAdapter);                        }                    });                }                @Override                public WaitDialog.RefreshDialog onStartLoad() {                    /*初始化一个DIALOG*/                    final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog                            (SearchShoplist.this);                    WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                    wait_itme_dialogpage.setImg(R.id.item_wait_img);                    wait_itme_dialogpage.setView(R.layout.item_wait);                    wait_itme_dialogpage.setCanClose(false);                    wait_itme_dialogpage.setTitle(R.id.item_wait_title);                    refreshDialog.Init(wait_itme_dialogpage);                    refreshDialog.showRefreshDialog("加载中...", false);                    return refreshDialog;                }                @Override                public void onFaile(String data, int code) {                }            }, xmlInstance.getXmlTree().trim());        }        /*listView设置监听*//*        onLoadmoreListView.setonScrollListener(this);*/        listener();    }    /*处理控件的监听事件*/    private void listener() {        headBack_img.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                SearchShoplist.this.finish();            }        });        onLoadmoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {            @Override            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {                ValueId valueId = (ValueId) view.getTag();                /*判断是否没有库存*/                if (valueId._noStocknumber.getVisibility() == View.VISIBLE) {                    /*没有库存了 不进入*/                    Toast.makeText(getApplicationContext(), "该商品没有库存啦，暂不支持订货哦", Toast                            .LENGTH_SHORT).show();                } else {                    LazyCatStartActivityWithBundler(ShowshopOffice.class, false, Config.Windows                            .GET_WINDOW_VALUE_SHOP_MESSAGE, valueId._title.getText().toString()                            .trim(), GET_WINDOW_VALUE_SHOP_ACTION, LocalValues.VALUES_SEARCH                            .VALUES_TO_SEARCH_SHOPKEYWORD);                }            }        });    }    class SearchlistAdapter extends BaseAdapter {        ArrayList<SearchPage> arrayList = new ArrayList<SearchPage>();        private int _stopCreate = 3;        /*所有的商品的实例集合*/        public SearchlistAdapter(ArrayList<SearchPage> list) {            this.arrayList.addAll(list);        }        @Override        public int getCount() {            return this.arrayList.size();        }        @Override        public Object getItem(int position) {            return null;        }        @Override        public long getItemId(int position) {            return position;        }        @SuppressLint({"ResourceType", "NewApi"})        @Override        public View getView(int position, View convertView, ViewGroup parent) {            ValueId valueId;            if (convertView != null && _stopCreate < 0) {                Log.i(MSG, "stop_create" + _stopCreate);                valueId = (ValueId) convertView.getTag();                convertView.clearAnimation();                int itemWidth = convertView.getMeasuredWidth();                TranslateAnimation trAnimation = new TranslateAnimation(itemWidth, 0, 0, 0);                trAnimation.setDuration(300);                trAnimation.setFillEnabled(true);                trAnimation.setFillAfter(true);                convertView.setAnimation(trAnimation);                trAnimation.start();            } else {                _stopCreate = _stopCreate - 1;                Log.i(MSG, "_stop_create" + _stopCreate);                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout                        .item_searchshoplist_2, null);                valueId = new ValueId();                valueId._title = convertView.findViewById(R.id.item_searchshoplist_title);                valueId._tp = convertView.findViewById(R.id.item_searchshoplist_tp);                valueId._tpspec = convertView.findViewById(R.id.item_searchshoplist_tpspec);                valueId._spec = convertView.findViewById(R.id.item_searchshoplist_spec);                valueId._integral = convertView.findViewById(R.id.item_searchshoplist_integral);                valueId._tpspec = convertView.findViewById(R.id.item_searchshoplist_tpspec);                valueId._dlp = convertView.findViewById(R.id.item_searchshoplist_dlp);                valueId._dlpspec = convertView.findViewById(R.id.item_searchshoplist_dlpspec);                valueId._splittp = convertView.findViewById(R.id.item_searchshoplist_splittp);                valueId._splittpspec = convertView.findViewById(R.id                        .item_searchshoplist_splittpspec);                valueId._pd = convertView.findViewById(R.id.item_searchshoplist_pd);                valueId._dlpBody = convertView.findViewById(R.id.item_searchshoplist_dlpBody);                valueId._img = convertView.findViewById(R.id.item_searchshoplist_img);                valueId._noStocknumber = convertView.findViewById(R.id                        .item_searchshoplist_noStockImg);                valueId._Body = convertView.findViewById(R.id.item_searchshoplist_Shopbody);                int itemWidth = convertView.getMeasuredWidth();                TranslateAnimation trAnimation = new TranslateAnimation(itemWidth, 0, 0, 0);                trAnimation.setDuration(300);                trAnimation.setFillEnabled(true);                trAnimation.setFillAfter(true);                convertView.clearAnimation();                convertView.setAnimation(trAnimation);                convertView.setTag(valueId);                trAnimation.start();            }            /**             * 样式边框             */            valueId._integral.setBackground(Tools.CreateDrawable(1, getResources().getString(R                    .color.colorVip), getResources().getString(R.color.colorVip), 10));            valueId._splittpBody = convertView.findViewById(R.id.item_searchshoplist_splittpBody)            ;/*拆分的价格样式*/            valueId._splittpBody.setBackground(Tools.CreateDrawable(1, getResources().getString(R                    .color.ThemeColor), getResources().getString(R.color.ThemeColor), 10));            valueId._Body.setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff", 10));            /**             * ==================             * 开始设置数据信息             */            /*设置标题*/            TextUnt.with(valueId._title).setText(arrayList.get(position).get_title().trim());            /*设置规格*/            TextUnt.with(valueId._spec).setText("规格:" + arrayList.get(position).get_company() +                    "装x" + arrayList.get(position).get_spec() + arrayList.get(position)                    .get_splitunit());            /*设置生产日期*/            TextUnt.with(valueId._pd).setText("日期:" + arrayList.get(position).get_exp());            /*设置是否没有库存了*/            if (arrayList.get(position).get_stocknumber().equals("") || arrayList.get(position)                    .get_stocknumber().equals("0")) {                /*没有库存了*/                valueId._noStocknumber.setVisibility(View.VISIBLE);            } else {                /*还有库存*/                valueId._noStocknumber.setVisibility(View.GONE);            }            /**             * 判断是否登录  如果登录了 就显示价格 如果没有登录 就不要显示价格             */            if (!isLogin) {                /*设置批发价格为不显示*/                TextUnt.with(valueId._tp).setText("*.**");                /*设置批发价格对应的单位*/                TextUnt.with(valueId._tpspec).setText(arrayList.get(position).get_company());                /**                 * 判断是否存在虚线的价格                 */                if (arrayList.get(position).get_dottenlineprice().equals("") || arrayList.get                        (position).get_dottenlineprice().equals("0")) {                    valueId._dlpBody.setVisibility(View.GONE);                } else {                    valueId._dlpBody.setVisibility(View.VISIBLE);                    /*设置虚线的价格为不显示*/                    TextUnt.with(valueId._dlp).setText("*.**");                    /*设置虚线的价格单位*/                    TextUnt.with(valueId._dlpspec).setText(arrayList.get(position).get_company());                }                /**                 * 计算积分                 */                TextUnt.with(valueId._integral).setText("积分:*.**");                /*设置单价*/                TextUnt.with(valueId._splittp).setText("*.**");/*设置计算的价格*/                TextUnt.with(valueId._splittpspec).setText(arrayList.get(position).get_splitunit());            } else {                /*设置批发价格*/                TextUnt.with(valueId._tp).setText(arrayList.get(position).get_tp());                /*设置批发价格对应的单位*/                TextUnt.with(valueId._tpspec).setText(arrayList.get(position).get_company());                /**                 * 判断是否存在虚线的价格                 */                if (arrayList.get(position).get_dottenlineprice().equals("") || arrayList.get                        (position).get_dottenlineprice().equals("0")) {                    valueId._dlpBody.setVisibility(View.GONE);                } else {                    valueId._dlpBody.setVisibility(View.VISIBLE);                    /*设置虚线的价格*/                    TextUnt.with(valueId._dlp).setText(arrayList.get(position)                            .get_dottenlineprice()).setMidcourtLine();                    /*设置虚线的价格单位*/                    TextUnt.with(valueId._dlpspec).setText(arrayList.get(position).get_company());                }                /**                 * 计算积分                 */                if (arrayList.get(position).get_viptp().equals("") || arrayList.get(position)                        .get_viptp().equals("0")) {                    /*没有设置加盟价格*/                    valueId._integral.setText("该商品无积分");                } else {                    Float integral = (Float.parseFloat(arrayList.get(position).get_tp()) * 10 -                            Float.parseFloat(arrayList.get(position).get_viptp()) * 10);                    TextUnt.with(valueId._integral).setText("积分:" + String.valueOf(integral));                }                /*计算拆分的单位的价格*/                try {                    Float splitPrice = Float.parseFloat(arrayList.get(position).get_tp()) / Float                            .parseFloat(arrayList.get(position).get_spec());//计算拆分的价格                    TextUnt.with(valueId._splittp).setText(String.format("%.2f", splitPrice));                    /*设置计算的价格*/                    TextUnt.with(valueId._splittpspec).setText(arrayList.get(position)                            .get_splitunit());                } catch (Exception e) {                    /*如果计算失败  就刷入正件的价格*/                    TextUnt.with(valueId._splittp).setText(arrayList.get(position).get_retail());                    TextUnt.with(valueId._splittpspec).setText(arrayList.get(position)                            .get_company());                }            }            /*开始加载网络图片*/            Glide.with(getApplicationContext()).load(http_addrs.HTTP_ADDR_IMG_URL + arrayList.get                    (position).get_img()).diskCacheStrategy(DiskCacheStrategy.SOURCE)                    .skipMemoryCache(false).into(valueId._img);            return convertView;        }    }    /**     * 尝试加载更多     */    @Override    public void onLoadmore() {    }    /**     * 加载中之后的滑动X和Y轴的坐标和计算的总和     *     * @param x     * @param y     * @param total     */    @Override    public void onScroll(int x, int y, int total) {    }    /**     * ITEM的PAGE值     */    class ValueId {        TextView _title;/*标题控件*/        TextView _tp;/*批发价格控件*/        TextView _tpspec;/*批发价格单位*/        TextView _pd;/*生产日期*/        TextView _spec;/*箱规*/        TextView _dlp;/*虚线价格*/        TextView _dlpspec;/*虚线价格单位*/        TextView _splittp;/*拆分价格*/        TextView _integral;/*积分*/        TextView _splittpspec;/*拆分价格单位*/        /**         * 样式的控件         */        LinearLayout _splittpBody;/*拆分的价格样式*/        LinearLayout _dlpBody;/*虚线价格的样式*/        LinearLayout _Body;/*控件的body*/        /**         * 图片控件         */        ImageView _img;/*图片的控件*/        ImageView _noStocknumber;/*没有库存的图片*/    }    /**     * 单个商品的参数     */    class SearchPage {        String _title;/*标题*/        String _spec;/*箱规*/        String _company;/*价格对应的单位*/        String _splitunit;/*拆分单位*/        String _exp;/*生产日期*/        String _tp;/*批发价格*/        String _viptp;/*VIP的加盟价格*/        String _dottenlineprice;/*虚线的价格*/        String _img;/*图片的地址*/        String _onlyid;/*商品的唯一ID*/        String _stocknumber;/*商品的库存*/        String _retail;/*零售建议价格*/        public String get_stocknumber() {            return _stocknumber;        }        public void set_stocknumber(String _stocknumber) {            this._stocknumber = _stocknumber;        }        public String get_retail() {            return _retail;        }        public void set_retail(String _retail) {            this._retail = _retail;        }        public String get_title() {            return _title;        }        public void set_title(String _title) {            this._title = _title;        }        public String get_spec() {            return _spec;        }        public void set_spec(String _spec) {            this._spec = _spec;        }        public String get_company() {            return _company;        }        public void set_company(String _company) {            this._company = _company;        }        public String get_splitunit() {            return _splitunit;        }        public void set_splitunit(String _splitunit) {            this._splitunit = _splitunit;        }        public String get_exp() {            return _exp;        }        public void set_exp(String _exp) {            this._exp = _exp;        }        public String get_tp() {            return _tp;        }        public void set_tp(String _tp) {            this._tp = _tp;        }        public String get_viptp() {            return _viptp;        }        public void set_viptp(String _viptp) {            this._viptp = _viptp;        }        public String get_dottenlineprice() {            return _dottenlineprice;        }        public void set_dottenlineprice(String _dottenlineprice) {            this._dottenlineprice = _dottenlineprice;        }        public String get_img() {            return _img;        }        public void set_img(String _img) {            this._img = _img;        }        public String get_onlyid() {            return _onlyid;        }        public void set_onlyid(String _onlyid) {            this._onlyid = _onlyid;        }    }}