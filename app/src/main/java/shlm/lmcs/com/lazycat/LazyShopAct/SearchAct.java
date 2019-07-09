package shlm.lmcs.com.lazycat.LazyShopAct;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.AutoLineLayout;
import shlm.lmcs.com.lazycat.R;

/**
 * 搜索界面
 */
public class SearchAct extends LazyCatAct {
    private AutoLineLayout everyoneSearch;
    private AutoLineLayout searchLog;
    private EditText input;
    private ListView SearchlogListview;

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
        Tools.getFocusable(input);
        init();
    }

    private void init() {

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
                    ArrayList<String> text_list2 = new ArrayList<>();
                    text_list2.add("流浪地球");
                    text_list2.add("逍遥散人");
                    text_list2.add("东宫");
                    text_list2.add("EXO");
                    text_list2.add("老番茄");
                    text_list2.add("假面骑士ZI-O");
                    text_list2.add("信誓旦旦");
                    SearchLogAdapter adapter = new SearchLogAdapter(text_list2);
                    SearchlogListview.setAdapter(adapter);
                    findViewById(R.id.activity_search_listview).setVisibility(View.VISIBLE);
                    findViewById(R.id.activity_search_RefreshScrollView).setVisibility(View.GONE);
                }
                //执行动画
            }
        });


        /**
         * 点击搜索框的监听事件
         */

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

        class viewpage {
            
            TextView title;
            TextView _static;
        }
    }
}
