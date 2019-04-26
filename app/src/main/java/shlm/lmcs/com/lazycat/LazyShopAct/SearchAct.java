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
 * ��������
 */
public class SearchAct extends LazyCatAct {
    private AutoLineLayout everyoneSearch;
    private AutoLineLayout searchLog;
    private EditText input;
    private ListView SearchlogListview;
    private TextView btn_search;

    @SuppressLint({"NewApi", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        setTransparentBar();
        super.onCreate(savedInstanceState);
        input = findViewById(R.id.assembly_head_input);
        input.setBackground(Tools.CreateDrawable(3, getResources().getString(R.color.ThemeColor),
                "#ffffff", 10));
        btn_search = findViewById(R.id.assembly_head_searchTitle);
        SearchlogListview = findViewById(R.id.activity_search_listview);
        Tools.getFoucus(input);

        init();
    }

    private void init() {

        /**
         * �༭��ļ����¼�
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
                 * ����Ƿ�û������ ���û�� �Ͳ�Ҫ�����б����
                 */
                if (TextUtils.isEmpty(s.toString())) {
                    /*Ϊ��*/
                    findViewById(R.id.activity_search_listview).setVisibility(View.GONE);
                    findViewById(R.id.activity_search_RefreshScrollView).setVisibility(View
                            .VISIBLE);
                } else {
                    /*��Ϊ��*/

                    /*ģ���������������*/
                    ArrayList<String> text_list2 = new ArrayList<>();
                    text_list2.add("���˵���");
                    text_list2.add("��ңɢ��");
                    text_list2.add("����");
                    text_list2.add("EXO");
                    text_list2.add("�Ϸ���");
                    text_list2.add("������ʿZI-O");
                    text_list2.add("���ĵ���");
                    SearchLogAdapter adapter = new SearchLogAdapter(text_list2);
                    SearchlogListview.setAdapter(adapter);
                    findViewById(R.id.activity_search_listview).setVisibility(View.VISIBLE);
                    findViewById(R.id.activity_search_RefreshScrollView).setVisibility(View.GONE);
                }
                //ִ�ж���
                if (TextUtils.isEmpty(s.toString())) {
                    /*����������Ϊ��*/
                    btn_search.clearAnimation();
                    /*�����ض���*/
                    btn_search.startAnimation(Tools.clearOnalpha(1000, false));
                    btn_search.setVisibility(View.GONE);
                    /*listView����ʾ*/
                } else if (btn_search.getVisibility() != View.VISIBLE) {
                    //������ʾ
                    btn_search.clearAnimation();
                    btn_search.setVisibility(View.VISIBLE);
                    btn_search.clearAnimation();
                    btn_search.startAnimation(Tools.createOnalpha(1000, false));
                }
            }
        });


        /**
         * ���������ļ����¼�
         */

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        ArrayList<String> text_list = new ArrayList<>();
        text_list.add("���˵���");
        text_list.add("��ңɢ��");
        text_list.add("����");
        text_list.add("EXO");
        text_list.add("�Ϸ���");
        text_list.add("������ʿZI-O");
        text_list.add("���ĵ���");
        ArrayList<String> text_list2 = new ArrayList<>();
        text_list2.add("���˵���");
        text_list2.add("��ңɢ��");
        text_list2.add("����");
        text_list2.add("EXO");
        text_list2.add("�Ϸ���");
        text_list2.add("������ʿZI-O");
        text_list2.add("���ĵ���");
        ArrayList<LinearLayout> tv_list = Tools.handleToarraylist(getApplicationContext(),
                text_list, 20, 10, 20, 10, "#e9e9e9", "#666666", 13);
        ArrayList<LinearLayout> tv_list2 = Tools.handleToarraylist(getApplicationContext(),
                text_list2, 20, 10, 20, 10, "#e9e9e9", "#666666", 13);

        everyoneSearch = findViewById(R.id.activity_everyone_searchBody);/*��Ҷ�����*/
        searchLog = findViewById(R.id.activity_search_searchLogBody);/*�û���������¼*/
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
            /*�������ֵ�*/
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
