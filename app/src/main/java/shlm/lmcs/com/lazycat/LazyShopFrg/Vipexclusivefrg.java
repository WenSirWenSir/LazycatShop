package shlm.lmcs.com.lazycat.LazyShopFrg;import android.annotation.SuppressLint;import android.graphics.Color;import android.os.Build;import android.os.Bundle;import android.support.annotation.RequiresApi;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.ListView;import android.widget.TextView;import android.widget.Toast;import com.bumptech.glide.Glide;import com.bumptech.glide.load.engine.DiskCacheStrategy;import java.util.ArrayList;import cn.pedant.SweetAlert.SweetAlertDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemFreight;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.LazyShopVip.SystemVip;import shlm.lmcs.com.lazycat.R;import shlm.lmcs.com.lazycat.TerminalSystemOS.PulloutOrders;import shlm.lmcs.com.lazycat.TerminalSystemOS.OS;/** * 发送的订单 * <p> * 原先为VIP的专属界面 现在不改名 直接使用 以后再改 */public class Vipexclusivefrg extends LazyCatFragment {    private View item;//布局    private ListView _listView;    private LocalValues.HTTP_ADDRS http_addrs;    private PulloutAdapter pulloutAdapter;    private String MSG = "Vipexclusivefrg.java[+]";    @SuppressLint("ResourceType")    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        item = inflater.inflate(R.layout.fragment_vipexclusive, null, false);        item.findViewById(R.id.assembly_act_headBackImg).setVisibility(View.GONE);/*隐藏退出的图片*/        TextUnt.with(item, R.id.assembly_act_headTitle).setText("订单信息").setTextColor(getResources().getString(R.color.ThemeColor));        /*列表框*/        _listView = item.findViewById(R.id.fragment_vipexclusiveListview);        init();        return item;    }    /**     * 初始化界面处理     */    @SuppressLint("NewApi")    private void init() {        http_addrs = new LocalValues.HTTP_ADDRS(getContext());        SystemVip systemVip = new SystemVip(getContext());        systemVip.Start(new SystemVip.OnVipcheck() {            @Override            public void onIsvip() {            }            @Override            public void onIsnovip() {            }            @SuppressLint("ResourceType")            @Override            public void onIsnologin() {                /*没有登录*/                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);                sweetAlertDialog.setContentTextSize(13);                sweetAlertDialog.setContentText("您还没有登录云仓库");                sweetAlertDialog.setTitle("没有登录");                sweetAlertDialog.setConfirmButtonTextColor(Color.WHITE);                sweetAlertDialog.setConfirmButtonBackgroundColor(Color.parseColor(getResources().getString(R.color.ThemeColor)));                sweetAlertDialog.setConfirmButton("关闭", new SweetAlertDialog.OnSweetClickListener() {                    @Override                    public void onClick(SweetAlertDialog sweetAlertDialog) {                        sweetAlertDialog.dismiss();                    }                });                sweetAlertDialog.show();            }            @Override            public void onIslogin() {                PulloutOrders.Pull(getContext(), new PulloutOrders.onPull() {                    @Override                    public void onPullok(ArrayList<PulloutOrders.Orderpage> _pages) {                        Log.i(MSG, "onPullok");                        if (_pages == null) {                            pulloutAdapter = new PulloutAdapter(null);                            _listView.setAdapter(pulloutAdapter);                        } else {                            pulloutAdapter = new PulloutAdapter(_pages);                            _listView.setAdapter(pulloutAdapter);                        }                    }                    @Override                    public void onPullError() {                        Log.i(MSG, "onPullError");                        Tools.showError(getContext(), "错误", "获取订单失败");                    }                    @Override                    public void onPullNull() {                        Log.i(MSG, "onPullNull");                        Tools.showError(getContext(), "提示", "您的订单列表是空的");                        if (pulloutAdapter != null) {                            pulloutAdapter = null;                            pulloutAdapter = new PulloutAdapter(null);                            _listView.setAdapter(pulloutAdapter);                        }                    }                });            }        });    }    /**     * 订单Adapter     */    public class PulloutAdapter extends BaseAdapter {        ArrayList<PulloutOrders.Orderpage> ar_pages = new ArrayList<PulloutOrders.Orderpage>();        public PulloutAdapter(ArrayList<PulloutOrders.Orderpage> _pages) {            if (_pages != null) {                this.ar_pages.addAll(_pages);            }        }        @Override        public int getCount() {            if (this.ar_pages != null) {                return this.ar_pages.size();            } else {                return 0;            }        }        @Override        public Object getItem(int position) {            return position;        }        @Override        public long getItemId(int position) {            return position;        }        @SuppressLint("ResourceType")        @RequiresApi(api = Build.VERSION_CODES.M)        @Override        public View getView(int position, View convertView, ViewGroup parent) {            Values values;            if (convertView != null) {                values = (Values) convertView.getTag();            } else {                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sendsystemorderact_list, null,                        false);                values = new Values();                values.listBody = convertView.findViewById(R.id.item_sendsystemorderact_list_body);                values.seeBtn = convertView.findViewById(R.id.item_sendsystemorderact_list_btnToseeOrder);                values.orderNumber = convertView.findViewById(R.id.item_sendsystemorderact_list_orderNumber);                values.cancelBtn = convertView.findViewById(R.id.item_sendsystemorderact_list_btnToCancleOrder);                values.title = convertView.findViewById(R.id.item_sendsystemorderact_list_shoptitle);                values.totalTp = convertView.findViewById(R.id.item_sendsystemorderact_list_tp);                values.payhow = convertView.findViewById(R.id.item_sendSystemorderAct_topayhow);                values.exp = convertView.findViewById(R.id.item_sendsystemorderact_list_exp);                values.orderTime = convertView.findViewById(R.id.item_sendsystemorderact_createTime);                values.spec = convertView.findViewById(R.id.item_sendsystemorderact_list_spec);                values.img = convertView.findViewById(R.id.item_sendsystemorderact_list_img);                convertView.setTag(values);            }            values.listBody.setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff", 20));            /*设置规格*/            TextUnt.with(values.spec).setText("商品规格:" + this.ar_pages.get(position)._company + "装x" + this.ar_pages.get(position)._spec + this.ar_pages.get(position)._splitunt);            /*设置生产日期*/            TextUnt.with(values.exp).setText("商品日期:" + this.ar_pages.get(position)._exp);            /*设置购买的时间*/            TextUnt.with(values.orderTime).setText("购买时间:" + this.ar_pages.get(position)._time);            /*设置购买的数量*/            TextUnt.with(values.payhow).setText("x" + this.ar_pages.get(position)._payhow + this.ar_pages.get(position)._company);            /*设置总的价格*/            TextUnt.with(values.totalTp).setText(Tools.calcToRide(this.ar_pages.get(position)._tp,                    this.ar_pages.get(position)._payhow));            /*设置订单号*/            TextUnt.with(values.orderNumber).setText(this.ar_pages.get(position)._ordernumber);            /*标题*/            TextUnt.with(values.title).setText(this.ar_pages.get(position)._title);            /**             * 判断订单状态             */            switch (this.ar_pages.get(position)._orderstatus) {                /*订单提交成功*/                case LocalValues.VALUES_ORDER._STATUS_PUSH_OK:                    TextUnt.with(values.cancelBtn).setTextColor("#f30d88").setBackground(Tools.CreateDrawable(2,                            "#f30d88", "#ffffff", 50)).setVisibility(true).setText("取消订单");                    break;                /*没有商品*/                case LocalValues.VALUES_ORDER._STATUS_PICKSHOP_NOT:                    TextUnt.with(values.cancelBtn).setTextColor("#ffffff").setBackground(Tools.CreateDrawable(2,                            "#afafaf", "#afafaf", 50)).setVisibility(true).setText("商品无货");                    break;            }            /**             * 取消订单             */            TextUnt.with(values.cancelBtn).setVisibility(true).setOnClick(new View.OnClickListener() {                @Override                public void onClick(View v) {                    TextView _tv = (TextView) v;                    if (_tv.getText().toString().equals("取消订单")) {                        final String orderNumber = (String) v.getTag();                        Tools.showConfirm(getContext(), "确定删除订单？", "如果有积分直接扣除哦,点击屏幕任何地方即可取消",                                new SweetAlertDialog.OnSweetClickListener() {                            @Override                            public void onClick(final SweetAlertDialog sweetAlertDialog) {                                sweetAlertDialog.dismiss();                                OS._deleOrder(getContext(), orderNumber, new OS.onDeleorder() {                                    @Override                                    public void onDeleOk() {                                        Tools.showSuccess(getContext(), "删除成功", "您的订单已经取消,积分也将实时更新");                                        init();                                        pulloutAdapter.notifyDataSetChanged();                                    }                                    @Override                                    public void onDeleError() {                                        Tools.showError(getContext(), "删除失败", "该订单已经被取消,或者您没有登录系统!");                                        init();                                        pulloutAdapter.notifyDataSetChanged();                                    }                                });                            }                        });                    }                }            }).setTag(this.ar_pages.get(position)._ordernumber);            /**             *查看物流             */            TextUnt.with(values.seeBtn).setVisibility(true).setOnClick(new View.OnClickListener() {                @Override                public void onClick(View v) {                    LazyCatFragmentStartActivityWithBundler(SystemFreight.class,                            LocalAction.ACTION_ORDER._ORDERNUMBER, v.getTag().toString().trim());                }            }).setTextColor(getResources().getString(R.color.ThemeColor)).setBackground(Tools.CreateDrawable(2,                    getResources().getString(R.color.ThemeColor), "#ffffff", 50)).setTag(this.ar_pages.get(position)._ordernumber);            /*设置图片地址*/            Glide.with(getContext()).load(http_addrs.HTTP_ADDR_PROGRAM_IMGSERVICE + this.ar_pages.get(position)._img).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).into(values.img);            return convertView;        }        class Values {            LinearLayout listBody;            TextView seeBtn;/*查看订单*/            TextView cancelBtn;/*取消订单*/            TextView orderNumber;/*订单号码*/            TextView title;            TextView totalTp;/*总计的价格*/            TextView payhow;/*购买的数量*/            TextView spec;/*规格*/            TextView exp;/*生产日期*/            TextView orderTime;/*订单时间*/            ImageView img;/*图片*/        }    }}