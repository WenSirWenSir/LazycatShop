package shlm.lmcs.com.lazycat.LazyShopAct;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.AutoLineLayout;
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
    private ArrayList<String> SearchkeyList = new ArrayList<String>();

    private LinearLayout SearchViewBody;
    private ImageView btnSearch;

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

        Tools.getFocusable(input);
        init();
    }

    private void init() {
        InitPageXml();/*整理编辑框的界面*/
        InitListener();

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
                    XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder();
                    xmlInstance.initDom();/*初始化dom*/
                    xmlInstance.setXmlTree(LocalAction.ACTION, "0");/*设置XML树结构*/
                    xmlInstance.setXmlTree(LocalAction.ACTION_SEARCHKEY.ACTION_KEYWORD, s
                            .toString().trim());
                    xmlInstance.overDom();
                    Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS
                            .HTTP_ADDR_SEARCH_KEY, new ProgramInterface() {
                        @Override
                        public void onSucess(String data, int code, WaitDialog.RefreshDialog
                                _refreshDialog) {
                            Log.i(MSG, data.toString().trim());
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
                                        if (tag.equals("key_word")) {
                                            SearchkeyList.add(pullParser.nextText().trim());
                                        }
                                    } catch (Exception e) {
                                        Log.e(MSG, "错误信息:" + e.getMessage());
                                    }

                                }

                                @Override
                                public void onEndTag(String tag, XmlPullParser pullParser,
                                                     Integer id) {

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


        ArrayList<String> text_list = new ArrayList<>();
        text_list.add("流浪地球");
        text_list.add("逍遥散人");
        text_list.add("东宫");
        text_list.add("EXO");
        text_list.add("老番茄");
        text_list.add("假面骑士ZI-O");
        text_list.add("信誓旦旦");
        ArrayList<String> text_list2 = new ArrayList<>();
        text_list2.add("流浪地球");
        text_list2.add("逍遥散人");
        text_list2.add("东宫");
        text_list2.add("EXO");
        text_list2.add("老番茄");
        text_list2.add("假面骑士ZI-O");
        text_list2.add("信誓旦旦");
        ArrayList<LinearLayout> tv_list = Tools.handleToarraylist(getApplicationContext(),
                text_list, 20, 10, 20, 10, "#e9e9e9", "#666666", 13);
        ArrayList<LinearLayout> tv_list2 = Tools.handleToarraylist(getApplicationContext(),
                text_list2, 20, 10, 20, 10, "#e9e9e9", "#666666", 13);

        everyoneSearch = findViewById(R.id.activity_everyone_searchBody);/*大家都在搜*/
        searchLog = findViewById(R.id.activity_search_searchLogBody);/*用户的搜索记录*/
        for (int i = 0; i < tv_list.size(); i++) {
            everyoneSearch.addView(tv_list.get(i));
        }
        for (int i = 0; i < tv_list2.size(); i++) {
            searchLog.addView(tv_list2.get(i));
        }
    }


    /**
     * 创建监听
     */
    private void InitListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LazyCatActStartActivity(SearchShoplist.class, false);
            }
        });
    }

    class SearchLogAdapter extends BaseAdapter {
        private ArrayList<String> list;

        public SearchLogAdapter(ArrayList<String> _list) {
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
            _viewpage.title.setText(this.list.get(position));
            _viewpage._static.setBackground(Tools.CreateDrawable(1, "#f30d88", "#ffffff", 5));

            return convertView;
        }

    }

    /**
     * 初始化设置边框等界面
     */
    @SuppressLint("NewApi")
    private void InitPageXml() {
        Net.doGet(getApplicationContext(), Config.HTTP_ADDR.getInitMainXmlConfig(), new Net
                .onVisitInterServiceListener() {
            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                return null;
            }

            @Override
            public void onSucess(String tOrgin, final WaitDialog.RefreshDialog _RefreshDialog) {
                Log.i(MSG, "调试输出:" + tOrgin);
                XmlanalysisFactory xmlTools = new XmlanalysisFactory(tOrgin);
                xmlTools.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {
                    @Override
                    public void onFaile() {

                    }

                    @Override
                    public void onStartDocument(String tag) {

                    }

                    @Override
                    public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {
                        try {

                            /**
                             * 获取搜索的父窗口的背景颜色
                             */
                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_searchbackground)) {
                                XmlTagValuesFactory.XmlInitPage.setSearchBackground(pullParser
                                        .nextText().trim());
                            }


                            /**
                             * 获取状态栏颜色
                             */
                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_windowcolor)) {
                                XmlTagValuesFactory.XmlInitPage.setWindowColor(pullParser
                                        .nextText().trim());
                            }

                            /**
                             * 获取搜索栏的线条的颜色
                             */

                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_searchlinecolor)) {
                                XmlTagValuesFactory.XmlInitPage.setSearchLineColor(pullParser
                                        .nextText().trim());
                            }

                            /**
                             * 获取搜索栏的背景颜色
                             */

                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_searchbodycolor)) {
                                XmlTagValuesFactory.XmlInitPage.setSearchBodyColor(pullParser
                                        .nextText().trim());
                            }

                            /**
                             * 获取系统通知的数量
                             */

                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_howmessagenumber)) {
                                XmlTagValuesFactory.XmlInitPage.setHowMessageNumber(pullParser
                                        .nextText().trim());

                            }

                            /**
                             * 获取热搜的标题
                             */
                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_searchkeyword)) {
                                XmlTagValuesFactory.XmlInitPage.setSearchKeyWord(pullParser
                                        .nextText().trim());
                            }
                        } catch (Exception e) {
                            Log.e(MSG, "错误信息:" + e.getMessage());
                        }

                    }

                    @Override
                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {

                    }

                    @Override
                    public void onEndDocument() {
                        setStatusBar(XmlTagValuesFactory.XmlInitPage.getSearchBackground());
                        /*设置搜索框的线条和背景颜色*/
                        EditText editText = SearchViewBody.findViewById(R.id
                                .assembly_head_editText);
                        editText.setBackground(Tools.CreateDrawable(1, XmlTagValuesFactory
                                .XmlInitPage.getSearchLineColor(), XmlTagValuesFactory
                                .XmlInitPage.getSearchBodyColor(), 10));
                        /*设置父窗口背景的颜色*/
                        SearchViewBody.setBackgroundColor(Color.parseColor(XmlTagValuesFactory
                                .XmlInitPage.getSearchBackground()));
                        /*设置热搜*/
                        EditText SearchEditView = SearchViewBody.findViewById(R.id
                                .assembly_head_editText);/*热搜输入框*/
                    }
                });


            }

            @Override
            public void onNotConnect() {

            }

            @Override
            public void onFail(String tOrgin) {

            }
        });
    }


    class viewpage {

        TextView title;
        TextView _static;
    }

}
