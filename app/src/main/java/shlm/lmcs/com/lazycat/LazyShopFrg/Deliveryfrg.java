package shlm.lmcs.com.lazycat.LazyShopFrg;import android.annotation.SuppressLint;import android.os.Bundle;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.ImageView;import android.widget.ListView;import android.widget.TextView;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.R;/** * 购物车的操作 */public class Deliveryfrg extends LazyCatFragment {    private ListView shoplistview;    @SuppressLint("NewApi")    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle            savedInstanceState) {        View item = inflater.inflate(R.layout.fragment_delivery, null);        init(item);        return item;    }    private void init(View _item) {        /*显示商品ITEM的ListView*/        shoplistview = _item.findViewById(R.id.fragment_delivery_shoplistview);        /*开始处理适配器*/        ShopitemAdapter shopitemAdapter = new ShopitemAdapter();        shoplistview.setAdapter(shopitemAdapter);    }    @SuppressLint({"NewApi", "ViewHolder"})    class ShopitemAdapter extends BaseAdapter {        @Override        public int getCount() {            return 30;        }        @Override        public Object getItem(int position) {            return null;        }        @Override        public long getItemId(int position) {            return 0;        }        @Override        public View getView(int position, View convertView, ViewGroup parent) {            Viewpage viewpage;            if (convertView != null) {            } else {                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart_shop,                        null);                viewpage = new Viewpage();                /*标题*/                viewpage.promotion_title = convertView.findViewById(R.id                        .item_car_shop_promotiontitle);                /*商品的状态*/                viewpage.shop_static = convertView.findViewById(R.id.item_cart_shopstatic_title);                /*商品图片*/                viewpage.shop_img = convertView.findViewById(R.id.item_cart_shop_shopimg);                /*商品的标题*/                viewpage.title = convertView.findViewById(R.id.item_cart_shop_title);                TextUnt.with(viewpage.shop_static).setBackground(Tools.CreateDrawable(1,                        "#f30d88", "#f30d88", 2)).setTextColor("#ffffff");                TextUnt.with(viewpage.promotion_title).setBackground(Tools.CreateDrawable(1,                        "#f03d66", "#f30d66", 5)).setTextColor("#ffffff");            }            return convertView;        }        class Viewpage {            TextView title;            TextView promotion_title;            ImageView shop_img;            TextView shop_static;        }    }}