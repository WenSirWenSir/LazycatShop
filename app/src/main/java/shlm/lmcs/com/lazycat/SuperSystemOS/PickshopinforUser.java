package shlm.lmcs.com.lazycat.SuperSystemOS;import android.os.Bundle;import android.os.PersistableBundle;import android.support.annotation.Nullable;import android.util.Log;import android.view.LayoutInflater;import android.view.TextureView;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.ImageView;import android.widget.ListView;import android.widget.TextView;import com.bumptech.glide.Glide;import com.bumptech.glide.load.engine.DiskCacheStrategy;import java.util.ArrayList;import cn.pedant.SweetAlert.SweetAlertDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;import shlm.lmcs.com.lazycat.SuperSystemOS.SuperSystemclass.PullshopsUsers;import shlm.lmcs.com.lazycat.SuperSystemOS.SuperSystemclass.Pushgetdone;import shlm.lmcs.com.lazycat.SuperSystemOS.SuperSystemclass.PushnotGoods;/** * 展示一个取货商品的所有要求取货的终端列表 */public class PickshopinforUser extends LazyCatAct {    private String MSG = "PickshopinfoUser.java[+]";    private String _onlyID;    private LocalValues.HTTP_ADDRS http_addrs;    private ListView _listview;    /**     * 取货的表格     */    private PullshopsUsers._Goodpage _goodpage;    @Override    protected void onCreate(@Nullable Bundle savedInstanceState) {        setContentView(R.layout.activity_superpickshopinfouser);        setStatusBar("#efefef");        TextUnt.with(this, R.id.assembly_act_headTitle).setText("确定取货数量");        setBackListener(R.id.assembly_act_headBackImg);        /*获取商品的唯一ID*/        _onlyID = getBundlerValue(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ONLYID);        _listview = findViewById(R.id.activity_superpickshopinforListview);        http_addrs = new LocalValues.HTTP_ADDRS(getApplicationContext());        init();        listener();        super.onCreate(savedInstanceState);    }    private void init() {        PullshopsUsers._pull(PickshopinforUser.this, _onlyID, new PullshopsUsers._onPullusers() {            @Override            public void _onGet(ArrayList<PullshopsUsers._Userpages> _users, PullshopsUsers._Goodpage goodpage) {/*                _goodpage = goodpage;                */                /*设置取货商品的数据*//*                ImageView img = findViewById(R.id.item_superpickshopImg);                TextUnt.with(PickshopinforUser.this, R.id.item_superpickshopTitle).setText(goodpage._title);                TextUnt.with(PickshopinforUser.this, R.id.item_superpickshopPayhow).setText(goodpage._payhow);                TextUnt.with(PickshopinforUser.this, R.id.item_superpickshopSpec).setText(goodpage._company + "装:" +                goodpage._spec + goodpage._splitunit);                TextUnt.with(PickshopinforUser.this, R.id.item_superpickshopTp).setText("单件批价:" + goodpage._tp);                TextUnt.with(PickshopinforUser.this, R.id.item_superpickshopPayhow).setText("待取货:" + goodpage._payhow);                Glide.with(getApplicationContext()).load(http_addrs.HTTP_ADDR_PROGRAM_IMGSERVICE + goodpage._img)                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).into(img);*/                inforUseradapter _InforUseradapter = new inforUseradapter(_users);                _listview.setAdapter(_InforUseradapter);                Log.i(MSG, "累计数据:" + _users.size());            }            @Override            public void _onError() {            }        });    }    /**     * 监听事件     */    private void listener() {    }    class inforUseradapter extends BaseAdapter {        ArrayList<PullshopsUsers._Userpages> ar_list;        public inforUseradapter(ArrayList<PullshopsUsers._Userpages> _list) {            this.ar_list = _list;        }        @Override        public int getCount() {            return this.ar_list.size();        }        @Override        public Object getItem(int position) {            return position;        }        @Override        public long getItemId(int position) {            return position;        }        @Override        public View getView(int position, View convertView, ViewGroup parent) {            Values values;            if (convertView != null) {                values = (Values) convertView.getTag();            } else {                values = new Values();                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_superpickuser, null,                        false);                values.user = convertView.findViewById(R.id.item_superpickuserPeople);                values.user_addr = convertView.findViewById(R.id.item_superpickuserAddr);                values.user_tel = convertView.findViewById(R.id.item_superpickuserTel);                values.user_payhow = convertView.findViewById(R.id.item_superpickuserPayhow);                values.user_name = convertView.findViewById(R.id.item_superpickuserName);                values.btn_getgoods = convertView.findViewById(R.id.item_superpickuserBtngetGoods);                values.btn_notgoods = convertView.findViewById(R.id.item_superpickuserBtnnoGoods);                values.btn_sendok = convertView.findViewById(R.id.item_superpickuserBtnSendOk);                convertView.setTag(values);            }            try {                /*设置商店名称*/                TextUnt.with(values.user_name).setText(this.ar_list.get(position)._name);                /*设置商店电话*/                TextUnt.with(values.user_tel).setText(this.ar_list.get(position)._tel);                /*设置商店地址*/                TextUnt.with(values.user_addr).setText(this.ar_list.get(position)._addr);                /*设置商店购买数量*/                TextUnt.with(values.user_payhow).setText("该商家订购:" + this.ar_list.get(position)._payhow);                /*设置店铺所有人*/                TextUnt.with(values.user).setText(this.ar_list.get(position)._people);            } catch (Exception e) {                e.printStackTrace();            }            /**             * 全部揽件完毕             */            TextUnt.with(values.btn_getgoods).setOnClick(new View.OnClickListener() {                @Override                public void onClick(View v) {                    final PullshopsUsers._Userpages user_list = (PullshopsUsers._Userpages) v.getTag();                    Tools.showConfirm(PickshopinforUser.this, "提示信息", "确定提交全部已经取货完毕?该商家购买该商品总共:" + user_list._payhow,                            new SweetAlertDialog.OnSweetClickListener() {                        @Override                        public void onClick(SweetAlertDialog sweetAlertDialog) {                            sweetAlertDialog.dismiss();                            Pushgetdone._push(PickshopinforUser.this, user_list._tel, _onlyID,                                    new Pushgetdone.onPush() {                                @Override                                public void _onPushok() {                                    Tools.showSuccess(PickshopinforUser.this, "提示内容", "已经将该商品标记为取货完毕");                                }                                @Override                                public void _onPusherror() {                                    Tools.showError(PickshopinforUser.this, "提示内容", "系统服务器出现异常");                                }                            });                        }                    });                }            }).setTag(this.ar_list.get(position));            /**             * 修改揽件数量             */            TextUnt.with(values.btn_sendok).setOnClick(new View.OnClickListener() {                @Override                public void onClick(View v) {                }            });            /**             * 揽件失败 没有库存信息             */            TextUnt.with(values.btn_notgoods).setOnClick(new View.OnClickListener() {                @Override                public void onClick(View v) {                    final PullshopsUsers._Userpages user_list = (PullshopsUsers._Userpages) v.getTag();                    Tools.showConfirm(PickshopinforUser.this, "提示信息", "确定该商品没有库存?该商家购买该商品总共:" + user_list._payhow +                            "/" + _goodpage._company + ",同时系统将修改商品的库存", new SweetAlertDialog.OnSweetClickListener() {                        @Override                        public void onClick(SweetAlertDialog sweetAlertDialog) {                            sweetAlertDialog.dismiss();                            PushnotGoods._push(PickshopinforUser.this, user_list._account, _onlyID,                                    new PushnotGoods.onPushonoGoods() {                                @Override                                public void _onPushok() {                                    Tools.showSuccess(PickshopinforUser.this, "提示信息", "操作成功");                                }                                @Override                                public void _onPusherror() {                                    Tools.showError(PickshopinforUser.this, "提示信息", "服务器操作失败");                                    init();                                }                            });                        }                    });                }            }).setTag(this.ar_list.get(position));            return convertView;        }        class Values {            TextView user_name;            TextView user_tel;            TextView user_addr;            TextView user_payhow;            TextView user;            /**             * 按钮类             */            TextView btn_getgoods;/*全部取货完毕*/            TextView btn_notgoods;/*没有获取*/            TextView btn_sendok;/*已经送达*/        }    }}