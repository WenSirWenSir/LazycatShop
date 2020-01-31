package shlm.lmcs.com.lazycat.LazyShopAct;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.AutoLineLayout;
import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;
import shlm.lmcs.com.lazycat.R;

/**
 * 搜索界面
 */
public class SearchAct extends LazyCatAct {
    private final static String MSG = "LazyCatAct.java[+]";
    private AutoLineLayout everyoneSearch;
    private AutoLineLayout searchLog;
    private EditText input;
    private ListView SearchlogListview;
    private ArrayList<Key_result> SearchkeyList = new ArrayList<Key_result>();
    private LinearLayout SearchViewBody;
    private ImageView btnSearch;
    private TextView btnClear;/*点击清空按钮*/
    private TextView headTitle;/*标题*/
    private String userAccount;/*用户的账户*/
    private String userToken;/*用户的TOKEN*/
    private Key_result key_result;/*关键字节对*/
    /*热搜*/
    private ArrayList<String> hotTitle;

    private LocalProgramTools.UserToolsInstance userToolsInstance;
    /*获取地址工具类*/
    private LocalValues.HTTP_ADDRS http_addrs;


    @SuppressLint({"NewApi", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        setTransparentBar();
        super.onCreate(savedInstanceState);
        input = findViewById(R.id.assembly_head_editText);
        input.setBackground(Tools.CreateDrawable(3, getResources().getString(R.color.ThemeColor),
                "#ffffff", 10));
        SearchlogListview = findViewById(R.id.activity_search_listview);
        /*搜索框的父窗口布局*/
        SearchViewBody = findViewById(R.id.activity_search_searchHead);
        /*搜索框的点击的图片*/
        btnSearch = SearchViewBody.findViewById(R.id.assembly_head_btnSearch);
        /*点击清空的按钮*/
        btnClear = findViewById(R.id.activity_searchBtnclear);
        /**/
        headTitle = findViewById(R.id.assembly_act_headTitle);
        headTitle.setText("检索商品");
        userToolsInstance = LocalProgramTools.getUserToolsInstance();/*获取用户工具类*/
        Tools.getFocusable(input);
        /*获取地址工具类*/
        http_addrs = LocalValues.getHttpaddrs(getApplicationContext());
        init();
    }


    private void init() {
        InitPageXml();/*整理编辑框的界面*/
        InitListener();
        ArrayList<String> text_list2 = new ArrayList<>();
        text_list2.add("流浪地球");
        text_list2.add("逍遥散人");
        text_list2.add("东宫");
        text_list2.add("EXO");
        text_list2.add("老番茄");
        text_list2.add("假面骑士ZI-O");
        text_list2.add("信誓旦旦");
        ArrayList<LinearLayout> tv_list2 = Tools.handleToarraylist(getApplicationContext(),
                text_list2, 20, 10, 20, 10, "#e9e9e9", "#666666", 13, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        everyoneSearch = findViewById(R.id.activity_everyone_searchBody);/*大家都在搜*/
        searchLog = findViewById(R.id.activity_search_searchLogBody);/*用户的搜索记录*/

        for (int i = 0; i < tv_list2.size(); i++) {
            searchLog.addView(tv_list2.get(i));
        }
    }


    /**
     * 创建监听
     */
    private void InitListener() {
        /**
         * listview点击的事件
         */
        SearchlogListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewpage _viewpage = (viewpage) view.getTag();
                LazyCatStartActivityWithBundler(SearchShoplist.class, true, LocalAction
                        .WINDOWS_TO_WINDOWS.ACTION_SEARCH_KEY, _viewpage.title.getText().toString
                        ().trim());
            }
        });
        /**
         * 编辑框的监听事件
         */
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /**
                 * 检查是否没有数据 如果没有 就不要进行列表操作
                 */
                if (TextUtils.isEmpty(s.toString())) {
                    /*为空*/
                    findViewById(R.id.activity_search_listview).setVisibility(View.GONE);
                    findViewById(R.id.activity_search_RefreshScrollView).setVisibility(View
                            .VISIBLE);
                } else {
                    /*不为空*/
                    /*模拟网络访问有数据*/
                    XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);
                    xmlInstance.setXmlTree(LocalAction.ACTION, "0");/*设置XML树结构*/
                    xmlInstance.setXmlTree(LocalAction.ACTION_SEARCHKEY.ACTION_KEYWORD, s
                            .toString().trim());
                    xmlInstance.overDom();
                    Net.doPostXml( http_addrs
                            .HTTP_ADDR_SEARCH_KEY, new ProgramInterface() {
                        @Override
                        public void onSucess(String data, int code, WaitDialog.RefreshDialog
                                _refreshDialog) {
                            Log.i(MSG, "返回的数据为:" + data.toString().trim());
                            XmlanalysisFactory xml = new XmlanalysisFactory(data.trim());
                            xml.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {
                                @Override
                                public void onFaile() {

                                }

                                @Override
                                public void onStartDocument(String tag) {
                                    SearchkeyList.clear();
                                }

                                @Override
                                public void onStartTag(String tag, XmlPullParser pullParser,
                                                       Integer id) {
                                    try {

                                        if (tag.equals(LocalAction.ACTION_SEARCHKEY
                                                .ACTION_KEY_RESULT)) {
                                            key_result = new Key_result();
                                        }
                                        /*商品的关键字搜索回传*/
                                        if (tag.equals(LocalAction.ACTION_SEARCHKEY
                                                .ACTION_KEY_RESULT_TITLE)) {
                                            key_result.setTitle(pullParser.nextText().trim());
                                        }
                                        /*商品的状态*/
                                        if (tag.equals(LocalAction.ACTION_SEARCHKEY
                                                .ACTION_KEY_RESULT_STATUS)) {
                                            key_result.setStatus(pullParser.nextText().trim());
                                        }

                                    } catch (Exception e) {
                                        Log.e(MSG, "错误信息:" + e.getMessage());
                                    }

                                }

                                @Override
                                public void onEndTag(String tag, XmlPullParser pullParser,
                                                     Integer id) {
                                    if(tag.equals(LocalAction.ACTION_SEARCHKEY.ACTION_KEY_RESULT)){
                                        SearchkeyList.add(key_result);
                                    }
                                }

                                @Override
                                public void onEndDocument() {
                                    //解析完成
                                    SearchLogAdapter adapter = new SearchLogAdapter(SearchkeyList);
                                    SearchlogListview.setAdapter(adapter);
                                    findViewById(R.id.activity_search_listview).setVisibility
                                            (View.VISIBLE);
                                    findViewById(R.id.activity_search_RefreshScrollView)
                                            .setVisibility(View.GONE);

                                }
                            });
                        }

                        @Override
                        public WaitDialog.RefreshDialog onStartLoad() {
                            return null;
                        }

                        @Override
                        public void onFaile(String data, int code) {

                        }
                    }, xmlInstance.getXmlTree().trim());

                }
                //执行动画
            }
        });

        findViewById(R.id.assembly_act_headBackImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(input.getText().toString().trim())){
                    LazyCatStartActivityWithBundler(SearchShoplist.class, true,
                            LocalAction.WINDOWS_TO_WINDOWS.ACTION_SEARCH_KEY, input
                                    .getText().toString().trim());
                }
                else{
                    Toast.makeText(getApplicationContext(),"错误!检索的名称不能为空",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLog.removeAllViews();
                TextView noLog = new TextView(searchLog.getContext());
                noLog.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                        .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                noLog.setGravity(Gravity.CENTER);
                searchLog.addView(noLog);
                TextUnt.with(noLog).setText("没有搜索记录").setTextColor("#666666").setTextSize(12);
                /*隐藏 不出现*/
                btnClear.setVisibility(View.GONE);
            }
        });
        /*点击退出的按钮*/
        findViewById(R.id.assembly_act_headBackImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class SearchLogAdapter extends BaseAdapter {
        private ArrayList<Key_result> list;

        public SearchLogAdapter(ArrayList<Key_result> _list) {
            this.list = _list;
        }

        @Override
        public int getCount() {
            return this.list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //assembly_searchact_showsearchitem_title
            viewpage _viewpage = null;
            if (convertView != null) {
                _viewpage = (viewpage) convertView.getTag();

            } else {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout
                        .assembly_searchact_showsearchitem, null);
                _viewpage = new viewpage();
                _viewpage.title = convertView.findViewById(R.id
                        .assembly_searchact_showsearchitem_title);
                _viewpage._static = convertView.findViewById(R.id
                        .assembly_searchact_showsearchitem_static);
                convertView.setTag(_viewpage);
            }
            /*处理文字等*/
            Log.i(MSG,"标题为:" + this.list.get(position).getTitle().trim());
            _viewpage.title.setText(this.list.get(position).getTitle().trim());
            switch (this.list.get(position).getStatus()) {
                case LocalValues.VALUES_SHOPPAGE.NORMAL:
                    TextUnt.with(_viewpage._static).setVisibility(false);
                    break;
                case LocalValues.VALUES_SHOPPAGE.PROMOTION:
                    TextUnt.with(_viewpage._static).setVisibility(false).setText(R.string
                            .shop_promotion).setBackground(Tools.CreateDrawable(getApplication(),
                            1, R.color.colorPromotion, R.color.colorPromotion, 5));
                    break;
                case LocalValues.VALUES_SHOPPAGE.REDUCTION:
                    TextUnt.with(_viewpage._static).setVisibility(false).setText(R.string
                            .shop_reduction).setBackground(Tools.CreateDrawable(getApplication(),
                            1, R.color.colorReduction, R.color.colorReduction, 5));

                    break;
                case LocalValues.VALUES_SHOPPAGE.VOLUME:
                    TextUnt.with(_viewpage._static).setVisibility(false).setText(R.string
                            .shop_volume).setBackground(Tools.CreateDrawable(getApplication(),
                            1, R.color.colorVolumn, R.color.colorVolumn, 5));
                    break;
                case LocalValues.VALUES_SHOPPAGE.ONLY_VIP:
                    TextUnt.with(_viewpage._static).setVisibility(false).setText(R.string
                            .shop_vip).setBackground(Tools.CreateDrawable(getApplication(),
                            1, R.color.colorVip, R.color.colorVip, 5));

                    break;
                case LocalValues.VALUES_SHOPPAGE.ONLY_ONE:
                    TextUnt.with(_viewpage._static).setVisibility(false).setText(R.string
                            .shop_only).setBackground(Tools.CreateDrawable(getApplication(),
                            1, R.color.colorPayonly, R.color.colorPayonly, 5));

                    break;
                case LocalValues.VALUES_SHOPPAGE.WHOLEASALE:
                    TextUnt.with(_viewpage._static).setVisibility(false).setText(R.string
                            .shop_wholeasale).setBackground(Tools.CreateDrawable(getApplication(),
                            1, R.color.colorWholeasale, R.color.colorWholeasale, 5));

                    break;
                case LocalValues.VALUES_SHOPPAGE.RESERVE:
                    TextUnt.with(_viewpage._static).setVisibility(false).setText(R.string
                            .shop_reserve).setBackground(Tools.CreateDrawable(getApplication(),
                            1, R.color.colorReserve, R.color.colorReserve, 5));

                    break;
            }
            return convertView;

        }

    }

    /**
     * 初始化设置边框等界面
     */
    @SuppressLint("NewApi")
    private void InitPageXml() {
        /**
         * 判断用户是否登录状态
         */
        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);
        xmlInstance.overDom();
        Net.doPostXml( http_addrs.HTTP_ADDR_INITSEARCH, new
                ProgramInterface() {
            @Override
            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {
                Log.i(MSG, data.trim());
                XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());
                xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {
                    @Override
                    public void onFaile() {

                    }

                    @Override
                    public void onStartDocument(String tag) {

                    }

                    @Override
                    public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {
                        try {
                            if (tag.equals(LocalAction.ACTION_SEARCHKEY.ACTION_HOT_BEGIN)) {
                                if (hotTitle != null) {
                                    hotTitle.clear();
                                } else {
                                    hotTitle = new ArrayList<String>();
                                }
                            }
                            /*获取解析的热搜标题*/
                            if (tag.equals(LocalAction.ACTION_SEARCHKEY.ACTION_HOT_TITLE)) {
                                if (hotTitle != null) {
                                    hotTitle.add(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "热搜的数组是空的");
                                }
                            }

                        } catch (Exception e) {
                            Log.e(MSG, "解析XML错误,错误原因:" + e.getMessage());

                        }
                    }

                    @Override
                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {

                    }

                    @Override
                    public void onEndDocument() {
                        /**
                         * 开始整理热搜界面
                         */
                        ArrayList<LinearLayout> hotTitlearray = Tools.handleToarraylist
                                (getApplicationContext(), hotTitle, 20, 10, 20, 10, "#e9e9e9",
                                        "#666666", 13, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView tv = (TextView) v;
                                LazyCatStartActivityWithBundler(SearchShoplist.class, true,
                                        LocalAction.WINDOWS_TO_WINDOWS.ACTION_SEARCH_KEY, tv
                                                .getText().toString().trim());
                            }
                        });
                        for (int i = 0; i < hotTitlearray.size(); i++) {
                            everyoneSearch.addView(hotTitlearray.get(i));
                        }
                    }
                });
                _refreshDialog.dismiss();
            }

            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                /*初始化一个DIALOG*/
                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog
                        (SearchAct.this);
                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();
                wait_itme_dialogpage.setImg(R.id.item_wait_img);
                wait_itme_dialogpage.setView(R.layout.item_wait);
                wait_itme_dialogpage.setCanClose(false);
                wait_itme_dialogpage.setTitle(R.id.item_wait_title);
                refreshDialog.Init(wait_itme_dialogpage);
                refreshDialog.showRefreshDialog("加载中...", false);
                return refreshDialog;
            }

            @Override
            public void onFaile(String data, int code) {

            }
        }, xmlInstance.getXmlTree());
    }


    class viewpage {
        TextView title;
        TextView _static;
    }

    class Key_result {
        String title;
        String status;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
