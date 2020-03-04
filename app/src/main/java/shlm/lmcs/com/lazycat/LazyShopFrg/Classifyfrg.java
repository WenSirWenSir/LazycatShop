package shlm.lmcs.com.lazycat.LazyShopFrg;import android.annotation.SuppressLint;import android.app.Activity;import android.graphics.Color;import android.os.Bundle;import android.util.Log;import android.view.Gravity;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.view.animation.TranslateAnimation;import android.widget.BaseAdapter;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.ListView;import android.widget.TextView;import com.bumptech.glide.Glide;import java.util.ArrayList;import java.util.List;import cn.pedant.SweetAlert.SweetAlertDialog;import cn.qqtheme.framework.picker.SinglePicker;import shlm.lmcs.com.lazycat.ClassifySystemGET.arrange;import shlm.lmcs.com.lazycat.ClassifySystemGET.getClassshop;import shlm.lmcs.com.lazycat.ClassifySystemGET.initHome;import shlm.lmcs.com.lazycat.ClassifySystemGET.node;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.SensorManagerHelper;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopAct.ScanQRCodeAct;import shlm.lmcs.com.lazycat.LazyShopAct.SearchAct;import shlm.lmcs.com.lazycat.LazyShopAct.ShowshopOffice;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.LazyShopView.SystemTextView;import shlm.lmcs.com.lazycat.R;import static shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config.Windows.GET_WINDOW_VALUE_SHOP_ACTION;public class Classifyfrg extends LazyCatFragment {    private String MSG = "Classifyfrg.java[+]";    private ListView classifyfrg_body;    private ArrayList<String> second_title = new ArrayList<String>();    private LinearLayout list_bodysecondlevel_title;/*二级的标题*/    private View item;    private LocalProgramTools.UserToolsInstance userToolsInstance;    private LocalValues.HTTP_ADDRS http_addrs;    private int showBodywidth;/*listView的展示宽度*/    /*用户是否登录*/    private Boolean is_login;    @SuppressLint({"NewApi", "ResourceType"})    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        /*获取地址工具类*/        http_addrs = LocalValues.getHttpaddrs(getContext());        item = inflater.inflate(R.layout.fragment_classify, null);        /*设置标题仓库网络*/        TextUnt.with(item, R.id.fragment_classifyTitle).setText("仓库网络").setFontFile(getContext(), "canLogo").setTextColor(getResources().getString(R.color.ThemeColor));        /*二级标题*/        list_bodysecondlevel_title = item.findViewById(R.id.fragmen_classify_listview_body_secondlevelTitle);        /*显示内容的ListView*/        classifyfrg_body = item.findViewById(R.id.fragmen_classify_listview_body);/*显示内容*/        /*获取用户类*/        userToolsInstance = LocalProgramTools.getUserToolsInstance();        /*设置状态栏的颜色*/        setStatusBar("#efefef");        /*统一设置监听事件*/        init();        listener(item);        return item;    }    @SuppressLint("NewApi")    private void init() {        /*获取主流分类的数据信息*/        arrange._start(getContext(), new arrange.onGet() {            @Override            public void _onOk(Boolean isLogin, ArrayList<arrange.Pages> _pages) {                /*结束之后第一个就是获取首页界面*/                initHome._init(getContext(), new initHome.onCreateadapter() {                    @Override                    public void onCreateOk(BaseAdapter _baseAdapter) {                        classifyfrg_body.setAdapter(_baseAdapter);/*设置适配器*/                    }                    @Override                    public void onCreateError() {                    }                    @Override                    public void onShop(String _shopId) {                        LazyCatFragmentStartActivityWithBundler(ShowshopOffice.class,                                LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ONLYID, _shopId);                    }                    @Override                    public void onWeb(String _web) {                    }                });                list_bodysecondlevel_title.removeAllViews();                for (int i = 0; i < _pages.size(); i++) {                    SystemTextView title = new SystemTextView(list_bodysecondlevel_title.getContext());                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,                            ViewGroup.LayoutParams.WRAP_CONTENT);                    title.setLayoutParams(params);                    title.setPadding(13, 23, 13, 23);                    title.setGravity(Gravity.CENTER);                    title.setText(_pages.get(i)._title);                    title.setTextSize(15);                    if (i == 0) {                        TextUnt.with(title).setTextColor("#08c299");                    } else {                        TextUnt.with(title).setTextColor("#020433");                    }                    title.setOnClickListener(new View.OnClickListener() {                        @SuppressLint("NewApi")                        @Override                        public void onClick(View v) {                            TextView _tv = (TextView) v;                            for (int i = 0; i < list_bodysecondlevel_title.getChildCount(); i++) {                                TextView tv = (TextView) list_bodysecondlevel_title.getChildAt(i);                                TextUnt.with(tv).setTextColor("#020433");                            }                            TextView tv = (TextView) v;                            TextUnt.with(tv).setTextColor("#08c299");                            /*根据分类标题获取节点信息*/                            if (_tv.getText().toString().equals("首页")) {                                /*展示首页界面*/                                initHome._init(getContext(), new initHome.onCreateadapter() {                                    @Override                                    public void onCreateOk(BaseAdapter _baseAdapter) {                                        classifyfrg_body.setAdapter(_baseAdapter);/*设置适配器*/                                    }                                    @Override                                    public void onCreateError() {                                    }                                    @Override                                    public void onShop(String _shopId) {                                        LazyCatFragmentStartActivityWithBundler(ShowshopOffice.class,                                                LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ONLYID, _shopId);                                    }                                    @Override                                    public void onWeb(String _web) {                                    }                                });                            } else {                                node._getNode(getContext(), _tv.getText().toString().trim(), new node.onNode() {                                    @SuppressLint("ResourceType")                                    @Override                                    public void onOk(Boolean _login, List<node.Nodepages> _nodepages,                                                     final String _label) {                                        /*组合获取数据信息*/                                        final ArrayList<String> ar_node = new ArrayList<>();                                        ar_node.clear();                                        for (int i = 0; i < _nodepages.size(); i++) {                                            ar_node.add(_nodepages.get(i)._nodeTitle);                                        }                                        /**                                         * 一个底部显示的选择器                                         */                                        final SinglePicker singlePicker = new SinglePicker((Activity) getContext(),                                                ar_node);                                        singlePicker.setTextColor(Color.parseColor(getResources().getString(R.color.ThemeColor)));                                        singlePicker.setSubmitText("展示该分类");                                        singlePicker.setSubmitTextColor(Color.parseColor(getResources().getString(R.color.ThemeColor)));                                        singlePicker.setCancelText("取消");                                        singlePicker.setCancelTextColor(Color.parseColor(getResources().getString(R.color.ThemeColor)));                                        singlePicker.show();                                        singlePicker.getSubmitButton().setOnClickListener(new View.OnClickListener() {                                            @Override                                            public void onClick(View v) {                                                singlePicker.dismiss();                                                Log.i(MSG, "获取点击的_node" + ar_node.get(singlePicker.getSelectedIndex()));                                                getClassshop(_label, ar_node.get(singlePicker.getSelectedIndex()));                                                /*获取节点下的所有数据信息*/                                            }                                        });                                        Log.i(MSG, "获取节点信息为的总数为:" + _nodepages.size());                                    }                                    @SuppressLint("ResourceType")                                    @Override                                    public void onNodata() {                                        ShowErrorDialog();                                    }                                    @Override                                    public void onError() {                                    }                                });                            }                        }                    });                    list_bodysecondlevel_title.addView(title);                }                Log.i(MSG, "分类标题数量:" + _pages.size());            }            @Override            public void _onError() {            }        });    }    /**     * 获取系统的节点下的所有数据信息     */    @SuppressLint("NewApi")    private void getClassshop(String _label, String _node) {        Log.i(MSG, "分类信息:" + _label + _node);        getClassshop.get(getContext(), _label, _node, new getClassshop.onClassshop() {            @Override            public void onGet(Boolean _isLogin, ArrayList<getClassshop.shopPages> _pages) {                Log.i(MSG, "分类商品信息总数:" + _pages.size());                if (_pages.size() <= 0) {                    ShowErrorDialog();                } else {                    Pageadapter pageadapter = new Pageadapter(_pages, _isLogin);                    /*清空原始数据*/                    classifyfrg_body.setAdapter(null);                    classifyfrg_body.setAdapter(pageadapter);                }            }            @Override            public void onError() {            }        });    }    /**     * 监听事件     */    private void listener(View _item) {        /**         * 启动二维码扫描         */        _item.findViewById(R.id.fragment_main_btnScan).                setOnClickListener(new View.OnClickListener() {                    @Override                    public void onClick(View v) {                        //加载扫一扫                        LazyCatFragmetStartAct(ScanQRCodeAct.class);                    }                });        /**         * 搜索界面         */        _item.findViewById(R.id.fragment_main_btnSearchIco).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatFragmetStartAct(SearchAct.class);            }        });    }    /**     * 整理参数值     */    private void initValues() {    }    /**     * 展示商品的adapter     */    class Pageadapter extends BaseAdapter {        private ArrayList<getClassshop.shopPages> _arpages;        private int _stopCreate = 6;        private Boolean Islogin;        public Pageadapter(ArrayList<getClassshop.shopPages> ar_pages, Boolean is_login) {            _arpages = new ArrayList<getClassshop.shopPages>();            _arpages.addAll(ar_pages);            this.Islogin = is_login;/*是否登录*/        }        @Override        public int getCount() {            if (this._arpages != null && this._arpages.size() > 0) {                return _arpages.size();            } else {                return 0;            }        }        @Override        public Object getItem(int position) {            return position;        }        @Override        public long getItemId(int position) {            return position;        }        @SuppressLint({"NewApi", "ResourceType"})        @Override        public View getView(final int position, View convertView, ViewGroup parent) {            final valuesId valuesId;            if (convertView != null && _stopCreate < 0) {                Log.i(MSG, "1" + _stopCreate);                valuesId = (Pageadapter.valuesId) convertView.getTag();                if (valuesId._animationStatus) {                    /*打开*/                    convertView.clearAnimation();                    int itemWidth = convertView.getMeasuredWidth();                    TranslateAnimation trAnimation = new TranslateAnimation(showBodywidth - itemWidth, 0, 0, 0);                    trAnimation.setDuration(300);                    trAnimation.setFillEnabled(true);                    trAnimation.setFillAfter(true);                    convertView.setAnimation(trAnimation);                    trAnimation.start();                } else {                    /*关闭*/                }            } else {                _stopCreate--;                Log.i(MSG, "stop_create" + _stopCreate);                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_classifyshoplist, null);                convertView.clearAnimation();                valuesId = new valuesId();                valuesId._Body = convertView.findViewById(R.id.item_classifyshoplistBody);                valuesId._title = convertView.findViewById(R.id.item_classifyshoplistTitle);                valuesId._tp = convertView.findViewById(R.id.item_classifyshoplistTp);                valuesId._exp = convertView.findViewById(R.id.item_classifyshoplistExp);                valuesId._spec = convertView.findViewById(R.id.item_classifyshoplistSpec);                valuesId._tpUnit = convertView.findViewById(R.id.item_classifyshoplisttpUnit);                valuesId._integral = convertView.findViewById(R.id.item_classifyshoplistIntegral);                valuesId._img = convertView.findViewById(R.id.item_classifyshoplistImg);                valuesId._nostock = convertView.findViewById(R.id.item_classifyshoplistNostock);                /**                 *===========                 * 设置边框样式                 */                valuesId._Body.setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff", 10));                convertView.setTag(valuesId);                valuesId._integral.setBackground(Tools.CreateDrawable(1, getResources().getString(R.color.colorVip),                        getResources().getString(R.color.colorVip), 10));                String _animationStatus = Tools.getToken(getContext(), LocalAction.ACTION_SYSTEM_SET._ANIMATIONNAME,                        LocalAction.ACTION_SYSTEM_SET._ANIMATIONKEY);                /**                 * 动画  判断系统设置是否显示                 */                if (_animationStatus.equals("") || _animationStatus.equals(LocalValues.VALUES_SYSTEM_SET._SYSTEM_OPEN)) {                    /*默认打开*/                    valuesId._animationStatus = true;                    int itemWidth = convertView.getMeasuredWidth();                    TranslateAnimation trAnimation = new TranslateAnimation(showBodywidth - itemWidth, 0, 0, 0);                    trAnimation.setDuration(300);                    trAnimation.setFillEnabled(true);                    trAnimation.setFillAfter(true);                    convertView.setAnimation(trAnimation);                    trAnimation.start();                } else {                    valuesId._animationStatus = false;                }            }            /**             *===================             *  整理数据             */            TextUnt.with(valuesId._title).setText(this._arpages.get(position)._title);            /*判断是否登录云仓库*/            if (this.Islogin) {                TextUnt.with(valuesId._tp).setText(this._arpages.get(position)._tp);            } else {                TextUnt.with(valuesId._tp).setText("*.**");            }            TextUnt.with(valuesId._tpUnit).setText(this._arpages.get(position)._tpUnit);            /*积分*/            if (this._arpages.get(position)._integral.equals("0")) {                TextUnt.with(valuesId._integral).setVisibility(false);            } else {                TextUnt.with(valuesId._integral).setVisibility(true);                TextUnt.with(valuesId._integral).setText("积分:" + this._arpages.get(position)._integral);            }            /*是否有库存*/            if (this._arpages.get(position)._nostock) {                //存在库存                valuesId._nostock.setVisibility(View.GONE);            } else {                /*没有库存了*/                valuesId._nostock.setVisibility(View.VISIBLE);            }            /*规格*/            TextUnt.with(valuesId._spec).setText(this._arpages.get(position)._tpUnit + "装:" + this._arpages.get(position)._spec + this._arpages.get(position)._splitUnit);            /*生产日期*/            TextUnt.with(valuesId._exp).setText("生产日期:" + this._arpages.get(position)._exp);            /**             * 监听事件             */            valuesId._Body.setTag(this._arpages.get(position)._onlyId);            valuesId._Body.setOnClickListener(new View.OnClickListener() {                @Override                public void onClick(View v) {                    /*是否有库存*/                    if (_arpages.get(position)._nostock) {                        //存在库存                        String _onlyId = (String) v.getTag();                        LazyCatFragmentStartActivityWithBundler(ShowshopOffice.class,                                LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ONLYID, _onlyId);                    } else {                        /*没有库存了*/                        Tools.showError(getContext(), "错误", "该商品没有资源了,无法下单");                    }                }            });            /**             * 载入图片             */            Log.i(MSG, "图片地址:" + http_addrs.HTTP_ADDR_PROGRAM_IMGSERVICE + this._arpages.get(position)._img);            Glide.with(getContext()).load(http_addrs.HTTP_ADDR_PROGRAM_IMGSERVICE + this._arpages.get(position)._img).into(valuesId._img);            return convertView;        }        class valuesId {            /*控件样式*/            /**             *             */            LinearLayout _Body;            /*标题*/ TextView _title;            TextView _tp;/*价格*/            TextView _exp;/*生产日期*/            TextView _tpUnit;/*价格的单位*/            TextView _spec;/*箱规*/            TextView _integral;/*积分*/            /**             * 图片             */            ImageView _img;/*图片*/            ImageView _nostock;/*没有库存的图片*/            /**             * 系统设置             */            Boolean _animationStatus;/*系统设置*/        }    }    @SuppressLint({"ResourceType", "NewApi"})    private void ShowErrorDialog() {        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);        sweetAlertDialog.setContentTextSize(13);        sweetAlertDialog.setContentText("该分类下暂时没有数据信息哦");        sweetAlertDialog.setTitle("没有资源");        sweetAlertDialog.setConfirmButtonTextColor(Color.WHITE);        sweetAlertDialog.setConfirmButtonBackgroundColor(Color.parseColor(getResources().getString(R.color.ThemeColor)));        sweetAlertDialog.setConfirmButton("关闭", new SweetAlertDialog.OnSweetClickListener() {            @Override            public void onClick(SweetAlertDialog sweetAlertDialog) {                sweetAlertDialog.dismiss();            }        });        sweetAlertDialog.show();    }}