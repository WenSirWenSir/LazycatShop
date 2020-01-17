package shlm.lmcs.com.lazycat.LazyShopAct;import android.os.Bundle;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.ListView;import android.widget.TextView;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.R;/*交易记录的ACTIVITY*/public class BrowseAct extends LazyCatAct {    private ListView listView;    private BrowseAdapter browseAdapter;/*适配器*/    /**/    @Override    protected void onCreate(Bundle savedInstanceState) {        setContentView(R.layout.activity_browse);        setStatusBar("#ffffff");        /*ListView的控件*/        listView = findViewById(R.id.activity_browseListView);        init();        Listener();        super.onCreate(savedInstanceState);    }    /**     * 界面的控件的监听     */    private void Listener() {    }    /**     * 界面的初始化     */    private void init() {        setTransparentBar();        /*初始化适配器 */        browseAdapter = new BrowseAdapter();        listView.setAdapter(browseAdapter);    }    class BrowseAdapter extends BaseAdapter {        @Override        public int getCount() {            return 20;        }        @Override        public Object getItem(int position) {            return position;        }        @Override        public long getItemId(int position) {            return position;        }        @Override        public View getView(int position, View convertView, ViewGroup parent) {            valuePage valuePage;            if (convertView != null) {                valuePage = (BrowseAdapter.valuePage) convertView.getTag();            } else {/*                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout                        .item_searchshoplist, null);*/                valuePage = new valuePage();                convertView.setTag(valuePage);            }            return convertView;        }        class valuePage {            TextView title;        }    }}