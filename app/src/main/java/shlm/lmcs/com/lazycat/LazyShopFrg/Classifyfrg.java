package shlm.lmcs.com.lazycat.LazyShopFrg;import android.annotation.SuppressLint;import android.graphics.Color;import android.os.Bundle;import android.util.Log;import android.view.Gravity;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.AdapterView;import android.widget.BaseAdapter;import android.widget.LinearLayout;import android.widget.ListView;import android.widget.TextView;import android.widget.Toast;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;public class Classifyfrg extends LazyCatFragment {    private String MSG = "Classifyfrg.java[+]";    private ListView classifyfrg_title, classifyfrg_body;    private ArrayList<String> calssify_title = new ArrayList<String>();    private TextView classify_title;    private ArrayList<String> second_title = new ArrayList<String>();    private LinearLayout list_bodysecondlevel_title;/*二级的标题*/    @SuppressLint("NewApi")    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle            savedInstanceState) {        View item = inflater.inflate(R.layout.fragment_classify, null);        /*显示的类型的标题*/        classify_title = item.findViewById(R.id.fragmen_classify_title);        /*二级标题*/        list_bodysecondlevel_title = item.findViewById(R.id                .fragmen_classify_listview_body_secondlevelTitle);        calssify_title.clear();        calssify_title.add("休闲食品");        calssify_title.add("水乳饮品");        calssify_title.add("方便食品");        calssify_title.add("酒水宴席");        calssify_title.add("粮油调味");        calssify_title.add("文具办公");        calssify_title.add("家用电器");        calssify_title.add("数码通讯");        calssify_title.add("计生用品");        calssify_title.add("冲调保健");        calssify_title.add("纸品家清");        calssify_title.add("个护美妆");        calssify_title.add("日化清洁");        calssify_title.add("毛巾护理");        calssify_title.add("妇女卫生");        calssify_title.add("干货精选");        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_SNACKS);        TextUnt.with(classify_title).setText(calssify_title.get(0).trim());        init(item);        return item;    }    private void handlerXml(String _xml) {        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(_xml);        xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {            @Override            public void onFaile() {            }            @Override            public void onStartDocument(String tag) {                second_title.clear();/*先清空以前的数据*/            }            @Override            public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                try {                    if (tag.equals(XmlTagValuesFactory.CLASSIFYFRG_TITLE_TAG.ACTION_ITEM)) {                        second_title.add(pullParser.nextText().trim());                    }                } catch (Exception e) {                    Log.e(MSG, "解析XML错误+" + e.getMessage());                }            }            @Override            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                if (tag.equals(XmlTagValuesFactory.CLASSIFYFRG_TITLE_TAG.ACTION_BODY)) {                    /*已经解析完成*/                    /*清空Layout的所有的Item*/                    list_bodysecondlevel_title.removeAllViews();                    for (int i = 0; i < second_title.size(); i++) {                        TextView title = new TextView(list_bodysecondlevel_title.getContext());                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup                                .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);                        title.setLayoutParams(params);                        title.setPadding(13, 23, 13, 23);                        title.setGravity(Gravity.CENTER);                        title.setText(second_title.get(i));                        title.setTextSize(15);                        if(i == 0){                            TextUnt.with(title).setTextColor("#08c299");                        }                        else{                            TextUnt.with(title).setTextColor("#a9a9a9");                        }                        title.setOnClickListener(new View.OnClickListener() {                            @Override                            public void onClick(View v) {                                for (int i = 0; i < list_bodysecondlevel_title.getChildCount();                                     i++) {                                    TextView tv = (TextView) list_bodysecondlevel_title                                            .getChildAt(i);                                    TextUnt.with(tv).setTextColor("#a9a9a9");                                }                                TextView tv = (TextView) v;                                TextUnt.with(tv).setTextColor("#08c299");                            }                        });                        list_bodysecondlevel_title.addView(title);                    }                }            }            @Override            public void onEndDocument() {            }        });    }    private void init(View _item) {        /*找到控件*/        classifyfrg_title = _item.findViewById(R.id.fragmen_classify_listview_title);/*显示分类标题的*/        final titleAdapter _titlTitleAdapter = new titleAdapter(calssify_title);        classifyfrg_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {            @SuppressLint("NewApi")            @Override            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {                TitleView_adapter titleView_adapter = (TitleView_adapter) view.getTag();                _titlTitleAdapter.onClickItem(position);                if (titleView_adapter != null) {                    /*数据不为空 就去更新标题*/                    TextUnt.with(classify_title).setText(titleView_adapter.title);                    /*去访问互联网 获取数据*/                    if (titleView_adapter.title.equals("休闲食品")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_SNACKS);                    }                    if (titleView_adapter.title.equals("水乳饮品")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_MILK);                    }                    if (titleView_adapter.title.equals("方便食品")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_AMENITY);                    }                    if (titleView_adapter.title.equals("酒水宴席")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_DRINKS);                    }                    if (titleView_adapter.title.equals("粮油调味")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_SEASON);                    }                    if (titleView_adapter.title.equals("文具办公")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_STATIONERY);                    }                    if (titleView_adapter.title.equals("家用电器")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_ELECTRIC);                    }                    if (titleView_adapter.title.equals("数码通讯")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_NUMERICAL);                    }                    if (titleView_adapter.title.equals("计生用品")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_CONTRACEPTIVE);                    }                    if (titleView_adapter.title.equals("冲调保健")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_HEALTH);                    }                    if (titleView_adapter.title.equals("纸品家清")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_PAPER);                    }                    if (titleView_adapter.title.equals("个护美妆")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_BEAUTY);                    }                    if (titleView_adapter.title.equals("日化清洁")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_DAILY);                    }                    if (titleView_adapter.title.equals("毛巾护理")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_TOWEL);                    }                    if (titleView_adapter.title.equals("妇女卫生")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_WOMAN);                    }                    if (titleView_adapter.title.equals("干货精选")) {                        toGetSecondTitle(LocalAction.CLASSIFY_ACTION.ACTION_SHOP_DRAY);                    }                } else {                    Toast.makeText(getContext(), "titleView_adapter为空", Toast.LENGTH_SHORT).show();                }            }        });        classifyfrg_title.setAdapter(_titlTitleAdapter);        classifyfrg_body = _item.findViewById(R.id.fragmen_classify_listview_body);/*显示内容*/    }    @SuppressLint("NewApi")    class titleAdapter extends BaseAdapter {        private ArrayList<String> title_list = new ArrayList<String>();        private int _position;        public void onClickItem(int position) {            this._position = position;            notifyDataSetChanged();        }        public titleAdapter(ArrayList<String> _title_list) {            this.title_list.clear();            this.title_list = _title_list;        }        @Override        public int getCount() {            return this.title_list.size();        }        @Override        public Object getItem(int position) {            return null;        }        @Override        public long getItemId(int position) {            return 0;        }        @SuppressLint("WrongConstant")        @Override        public View getView(int position, View convertView, ViewGroup parent) {            /**             * 使用自定义的Item来支撑布局             */          /*  LinearLayout body = new LinearLayout(getContext());            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams                    .MATCH_PARENT,50);            body.setLayoutParams(params);            body.setBackgroundColor(Color.parseColor("#f30d88"));*/            LinearLayout body = new LinearLayout(getContext());            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams                    .MATCH_PARENT, 120);            body.setGravity(Gravity.CENTER);            body.setLayoutParams(params);            body.setOrientation(0);//*设置横向布局*//*            TextView title = new TextView(getContext());            title.setPadding(13, 23, 13, 23);            TitleView_adapter titleView_adapter = new TitleView_adapter();            titleView_adapter.title = this.title_list.get(position).trim();            if (_position == position) {                TextUnt.with(title).setTextColor("#08c299").setTextSize(15).setBackColor                        ("#fefefe").setBoldText(true);                View line = new View(getContext());                LinearLayout.LayoutParams line_params = new LinearLayout.LayoutParams(8, 40);                line_params.setMargins(10, 20, 10, 20);                line.setLayoutParams(line_params);                line.setBackgroundColor(Color.parseColor("#08c299"));                body.addView(line);                /*要添加一个线条*/            } else {                TextUnt.with(title).setTextColor("#000000").setTextSize(15).setBackColor                        ("#ffffff").setBoldText(true);            }            ViewGroup.LayoutParams title_params = new ViewGroup.LayoutParams(ViewGroup                    .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);            title.setLayoutParams(title_params);            title.setGravity(Gravity.CENTER);            title.setText(this.title_list.get(position));            body.addView(title);            body.setTag(titleView_adapter);            return body;        }        class Viewpage {            View lines;/*线条*/            TextView title;/*标题*/        }    }    /**     * 获取二级标题     */    @SuppressLint("NewApi")    public void toGetSecondTitle(String _Action) {        Log.i(MSG, "toGetSecondTitle启动");        Net.doGet(getContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_GET_SHOPCLASSIFY, new Net                .onVisitInterServiceListener() {            @Override            public WaitDialog.RefreshDialog onStartLoad() {                WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog(getActivity                        ());                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                wait_itme_dialogpage.setImg(R.id.item_wait_img);                wait_itme_dialogpage.setView(R.layout.item_wait);                wait_itme_dialogpage.setTitle(R.id.item_wait_title);                refreshDialog.Init(wait_itme_dialogpage);                refreshDialog.showRefreshDialog("加载中...", false);                wait_itme_dialogpage.setCanClose(false);                return refreshDialog;            }            @Override            public void onSucess(String tOrgin, WaitDialog.RefreshDialog _RefreshDialog) {                _RefreshDialog.dismiss();                Log.i(MSG, "获取到的数据为:" + tOrgin.trim());                handlerXml(tOrgin.trim());            }            @Override            public void onNotConnect() {            }            @Override            public void onFail(String tOrgin) {            }        }, LocalAction.CLASSIFY_ACTION.ACTION_INTO, _Action);    }    class TitleView_adapter {        String title;    }}