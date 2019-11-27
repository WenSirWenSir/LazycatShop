package shlm.lmcs.com.lazycat.LazyShopAct;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.xmlpull.v1.XmlPullParser;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.JsonEndata;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.ScrollViewBiggerPhoto;
import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;
import shlm.lmcs.com.lazycat.R;

@SuppressLint({"ResourceType", "NewApi", "HandlerLeak"})
public class ShowshopOffice extends LazyCatAct {
    private String MSG = "ShowshopOffice.java[+]";
    private Boolean isShowCart = false;/*记录是否把cart展开*/
    private String shopmesage;/*关于商品的信息*/
    private String getshopAction;/*获取商品的方式 1条码查找 2 名称查找 3 模糊查找 4 唯一识别号查找*/

    /**
     * 记录商品回传的信息
     */
    private String shopData;

    /**
     * 账户和密码
     */
    private String St_phone;/*账户和密码*/
    private String St_token;/*token*/
    private String St_payhow;/*如果为0  就是没有订购 如果订购了  就显示订单数*/
    private ScrollViewBiggerPhoto scrollViewBiggerPhoto;
    private ImageView photo;
    private TextView btn_select_del;
    private TextView btn_select_add;
    private LinearLayout body_addordel;
    private TextView SHOP_TITLE;
    private TextView SHOP_DLP;/*商品的虚线价格*/
    private TextView SHOP_PD;/*商品的保质期*/
    private TextView SHOP_EXP;/*商品的生产日期*/
    private TextView SHOP_SPEC;/*商品的品牌*/
    private TextView SHOP_GRADE;/*商品的等级*/
    private TextView SHOP_BARCODE;/*商品的条码*/
    private TextView SHOP_RETAIL;/*商品的零售价格*/
    private TextView SHOP_SHOWBRAND;/*商品显示的品牌*/
    private TextView SHOP_BUSINESS;/*商品的供货单位*/
    private LocalProgramTools.UserToolsInstance userToolsInstance;/*用户工具类*/
    private AlertDialog gradeAlertDialog;/*等级的Alert*/
    private XmlTagValuesFactory.Shopvalues shopvalues = null;
    private XmlTagValuesFactory.Giftshopvalues giftshopvalues = null;/*赠品信息*/
    private ImageView countDownadavert;
    private ImageView btnGradeAsk;/*问号按钮*/
    private ImageView imgBarcode;/*图片的条码*/
    private LinearLayout select_numberBody;
    private LinearLayout showShoplistBody;
    private LinearLayout valuesBody;/*商品参数的Body*/
    private RelativeLayout inShowCarPage;/*打开购物车之后 计算价格和优惠*/
    private TextView btnAccount;
    private TextView select_number;
    private TextView SHOP_TP;/*显示批发的价格*/
    private LinearLayout hideShopBody;
    private Float Total;/*计算总和*/
    private int window_height;/*屏幕的高度*/
    private LinearLayout otherMessage;/*显示其他一些的基础的信息的body*/
    private final static int MSG_CLEAR_COUNT_DOWN_ADAVERT = 1;
    private DisplayMetrics metrics;
    private Boolean IsVip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showshopoffice);
        setTransparentBar();
        userToolsInstance = LocalProgramTools.getUserToolsInstance();

        /**
         * 设置促销的标题
         */
        findViewById(R.id.activity_showshopoffice_progrm_title).setBackground(Tools
                .CreateDrawable(1, getResources().getString(R.color.ThemeColor), getResources()
                        .getString(R.color.ThemeColor), 5));
        otherMessage = findViewById(R.id.activity_showshopoffice_otherMessagebody);/*其他一些基本信息的BODY*/
        /*滑动增大图片控件*/
        scrollViewBiggerPhoto = findViewById(R.id.activity_showshopoffice_scrollview);
        /*增加或者减少按钮布局*/
        body_addordel = findViewById(R.id.activity_showshopoffice_SeletNumberBody);
        /*显示商品图片*/
        photo = findViewById(R.id.activity_showshopoffice_photo);
        /*显示商品的批发价格*/
        SHOP_TP = findViewById(R.id.activity_showshopoffice_tp);
        /*商品的保质期*/
        SHOP_PD = findViewById(R.id.activity_showshopoffice_pd);
        /*商品的保质期*/
        SHOP_EXP = findViewById(R.id.activity_showshopoffice_exp);
        /*商品的箱规*/
        SHOP_SPEC = findViewById(R.id.activity_showshopoffice_spec);
        /*商品的等级*/
        SHOP_GRADE = findViewById(R.id.activity_showshopoffice_grade);
        /*商品的条码*/
        SHOP_BARCODE = findViewById(R.id.activity_showshopoffice_barcode);
        /*商品的零售价格*/
        SHOP_RETAIL = findViewById(R.id.activity_showshopoffice_retail);
        /*显示虚线的价格*/
        SHOP_DLP = findViewById(R.id.activity_showshopoffice_dlp);
        /*商品的标题*/
        SHOP_TITLE = findViewById(R.id.activity_showshopoffice_shoptitle);
        /*显示商品的品牌*/
        SHOP_SHOWBRAND = findViewById(R.id.activity_showshopoffice_showbrand);
        /*倒计时的广告*/
        countDownadavert = findViewById(R.id.activity_showshopoffice_countDownadavert);
        /*选择数量*/
        select_numberBody = findViewById(R.id.activity_showshopoffice_SeletNumberBody);
        /*选择数量减少*/
        btn_select_del = findViewById(R.id.activity_showshopoffice_SeletNumberBtndel);
        /*选择数量增加*/
        btn_select_add = findViewById(R.id.activity_showshopoffice_SeletNumberBtnadd);
        /*获取选择数量*/
        select_number = findViewById(R.id.activity_showshopoffice_SeletNumber);
        /*订购按钮*/
        btnAccount = findViewById(R.id.activity_showshopoffice_btnAccount);
        /*显示商品列表的body*/
        showShoplistBody = findViewById(R.id.activity_showshopoffice_showshoplistBody);
        /*遮物布局*/
        hideShopBody = findViewById(R.id.activity_showshopoffice_hideShopBody);
        /*购物车图标按钮*/
        /*显示购物车之后 展示购物车的总计价值和优惠*/
        inShowCarPage = findViewById(R.id.activity_showshopoffice_carpage);
        /*显示商品的参数的Body*/
        valuesBody = findViewById(R.id.activity_showshopoffice_valuesBody);
        /*显示问号按钮*/
        btnGradeAsk = findViewById(R.id.activity_showshopoffice_btnGradeAsk);
        /*图片的条码*/
        imgBarcode = findViewById(R.id.activity_showshopoffice_imgBarcode);
        /*商品的供货单位*/
        SHOP_BUSINESS = findViewById(R.id.activity_showshopoffice_businessName);
        /*尝试加载*/
        /*图片包裹*/
        /*获取界面传值*/
        shopmesage = getBundlerValue(Config.Windows.GET_WINDOW_VALUE_SHOP_MESSAGE);/*商品信息*/
        getshopAction = getBundlerValue(Config.Windows.GET_WINDOW_VALUE_SHOP_ACTION);/*获取方式*/

        /*count down advert*/
        init();

    }


    /**
     * 初始化
     */
    private void init() {
        shopvalues = null;
        shopvalues = XmlTagValuesFactory.getShopvaluesInstance();
        /**
         * 最重要的一步  首先获取到服务器的信息  进行数据的整理和初始化
         */
        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);
        xmlInstance.setXmlTree(LocalAction.ACTION, getshopAction);
        xmlInstance.setXmlTree(LocalAction.ACTION_SEARCHKEY.ACTION_KEYWORD, shopmesage);
        xmlInstance.overDom();
        Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_GET_SHOPVALUES,
                new ProgramInterface() {
            @Override
            public void onSucess(String data, int code, final WaitDialog.RefreshDialog
                    _refreshDialog) {
                Log.i(MSG, "商品数据:" + data.trim());
                _refreshDialog.dismiss();
                shopData = data.trim();
                XmlanalysisFactory xml = new XmlanalysisFactory(data.trim());
                xml.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {
                    @Override
                    public void onFaile() {

                    }

                    @Override
                    public void onStartDocument(String tag) {


                    }

                    @Override
                    public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {
                        try {


                            /*商品标题*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TITLE)) {
                                shopvalues.setTitle(pullParser.nextText().trim());

                            }

                            /*商品条码*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_BARCODE)) {
                                shopvalues.setBarcode(pullParser.nextText().trim());
                            }
                            /*商品归属*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_ASCRIPTION)) {
                                shopvalues.setAscription(pullParser.nextText().trim());
                            }
                            /*商品的品牌*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_BRAND)) {
                                shopvalues.setBrand(pullParser.nextText().trim());
                            }
                            /*商品的零售价格*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_RETAIL)) {
                                shopvalues.setRetail(pullParser.nextText().trim());
                            }

                            /*商品单位*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_COMPANY)) {
                                shopvalues.setCompany(pullParser.nextText().trim());
                            }
                            /*商品生产日期*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_EXP)) {
                                shopvalues.setExp(pullParser.nextText().trim());
                            }

                            /*商品的归属*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_GRADE)) {
                                shopvalues.setGrade(pullParser.nextText().trim());
                            }

                            /*商品的产地*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_INFROM)) {
                                shopvalues.setInfrom(pullParser.nextText().trim());
                            }
                            /*商品唯一标识 */
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_ONLYID)) {
                                shopvalues.setOnlyid(pullParser.nextText().trim());
                            }
                            /*商品的保质期*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_PD)) {
                                shopvalues.setPd(pullParser.nextText().trim());
                            }
                            /*商品的终端建议售价*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_PRICE)) {
                                shopvalues.setPrice(pullParser.nextText().trim());
                            }
                            /*商品的规格*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SPEC)) {
                                shopvalues.setSpec(pullParser.nextText().trim());
                            }
                            /*商品的状态*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_STATIC)) {
                                shopvalues.set_static(pullParser.nextText().trim());
                            }
                            /*商品的起订数量*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SU)) {
                                shopvalues.setSu(pullParser.nextText().trim());
                            }
                            /*商品的批发价格*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TP)) {
                                shopvalues.setTp(pullParser.nextText().trim());
                            }

                            /*商品的净重*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_WEIGHT)) {
                                shopvalues.setWeight(pullParser.nextText().trim());
                            }

                            /*商品的虚线的价格*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_DLP)) {
                                shopvalues.setDlp(pullParser.nextText().trim());
                            }
                            /*商品的图片地址*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_IMG)) {
                                shopvalues.setImg(pullParser.nextText().trim());
                            }
                            /*商品的最低的组合单位*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_SPLITUNIT)) {
                                shopvalues.setSplitUnit(pullParser.nextText().trim());
                            }
                            /*商品的对接商家*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_BUSINSS)) {
                                shopvalues.setBusiness(pullParser.nextText().trim());
                            }
                            /*如果商家登录了 就获取这个值 来判断商品购买数量*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_TOPAYHOW)) {
                                St_payhow = pullParser.nextText().trim();
                            }
                            /*商品的品牌信息*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_BRANDADDR)) {
                                shopvalues.setSt_Brandaddr(pullParser.nextText().trim());
                            }
                            /*商品的品牌图片*/
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_BRANDIMG)) {
                                shopvalues.setSt_Brandimg(pullParser.nextText().trim());
                            }
                            /*判断用户是否登录成功*/
                            if (tag.equals(LocalAction.ACTION_LOGIN.ACTION_XML_LOGINSTATUS)) {
                                if (pullParser.nextText().trim().equals("0")) {
                                    shopvalues.setSt_loginin(true);
                                } else {
                                    shopvalues.setSt_loginin(false);
                                }
                            }

                            /*获取商家的物理经度*/
                            if (tag.equals(LocalAction.ACTION_INTERFACE_BUSINESS
                                    .INTERFACE_BUSINESS_LONG)) {
                                shopvalues.setSt_BusinessLong(pullParser.nextText().trim());
                            }
                            /*获取对接商家的物理维度*/
                            if (tag.equals(LocalAction.ACTION_INTERFACE_BUSINESS
                                    .INTERFACE_BUSINESS_LAT)) {
                                shopvalues.setSt_businessLat(pullParser.nextText().trim());
                            }
                            /*判断商家是否为Vip*/
                            if (tag.equals(LocalAction.ACTION_LOGIN.ACTION_XML_VIPSTATUS)) {
                                if (pullParser.nextText().trim().equals(LocalValues.NET_ERROR)) {
                                    shopvalues.setVip(false);
                                } else {
                                    shopvalues.setVip(true);
                                }
                            }
                        } catch (Exception e) {

                        }

                    }

                    @Override
                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {
                        if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_START)) {
                            /*处理完毕了*/
                            initMainpage();
                            listener();
                        }

                    }

                    @Override
                    public void onEndDocument() {
                        /*文档处理完毕 就关闭显示的等待的LOGO*/

                    }
                });
            }

            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                /*初始化一个DIALOG*/
                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog
                        (ShowshopOffice.this);
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
        }, xmlInstance.getXmlTree().trim());

    }

    private void listener() {

        /**
         * 退出的监听事件
         */
        findViewById(R.id.activity_showshopoffice_btnBack).setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 询问等级的按钮问号
         */
        btnGradeAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowshopOffice.this);
                View item = LayoutInflater.from(ShowshopOffice.this).inflate(R.layout
                        .alert_message, null);
                /*提示文字的标题*/
                TextView alertTitle = item.findViewById(R.id.alert_messageTitle);
                TextUnt.with(alertTitle).setText("等级解释");
                /*提示文字信息*/
                TextView alertText = item.findViewById(R.id.alert_messageText);
                /*确定按钮*/
                TextView btnConfirm = item.findViewById(R.id.alert_messageBtnConfirm);
                TextUnt.with(alertText).setHtmlText
                        ("<b>一级:</b>过期可以退款。<br><br><b>二级:</b>过期之前可以退款。<br><br><b>三级:</b>售卖3" +
                                "个月支持退款<br><br>");
                TextUnt.with(btnConfirm).setText("我已知晓");
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (gradeAlertDialog != null) {
                            gradeAlertDialog.dismiss();
                        }
                    }
                });
                builder.setView(item);
                gradeAlertDialog = builder.show();
            }
        });
        /**
         * 请求仓库网络发货
         */

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                if ((Boolean) v.getTag()) {
                    showOrderConfirm();
                } else {
                    /*判断是否为发送订单了*/
                    if (tv.getText().toString().trim().indexOf("仓库发货") != -1 || tv.getText()
                            .toString().trim().indexOf("您已经预定") != -1) {
                        Toast.makeText(getApplicationContext(), "您已成功提交,暂不支持补单哦!", Toast
                                .LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "您没有登录,请您登录后发送订货单", Toast
                                .LENGTH_SHORT).show();
                    }
                }

            }
        });
    }


    /**
     * 展示用户的确定界面
     */
    private void showOrderConfirm() {
        /**
         * 重新获取用户的信息
         *
         * 用户的地址是不能由用户自己修改的 只能用户使用客服服务进行修改 并且需要重新登录
         *
         */
        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);
        xmlInstance.overDom();
        Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_GETUSER_VALUES,
                new ProgramInterface() {
            @Override
            public void onSucess(String data, int code, final WaitDialog.RefreshDialog
                    _refreshDialog) {
                Log.i(MSG, "重新获取用户信息的请求:" + data.trim());
                _refreshDialog.dismiss();
                if (data.trim().equals(LocalValues.VALUES_LOGIN.LOGIN_ERROR)) {
                    Toast.makeText(getApplicationContext(), "登录过期,请重新登录", Toast.LENGTH_SHORT)
                            .show();
                    if (userToolsInstance != null) {
                        userToolsInstance.ClearLocalCach();
                    }

                } else {
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
                            /*判断是否是Vip客户*/
                            try {
                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_VIPSTATUS)) {
                                    if (pullParser.nextText().trim().equals(LocalValues
                                            .VALUES_USERCENTER.IS_VIP)) {
                                        IsVip = true;
                                    } else {
                                        IsVip = false;
                                    }
                                }
                            } catch (Exception e) {

                            }

                        }

                        @Override
                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {

                        }

                        @Override
                        public void onEndDocument() {
                            _refreshDialog.dismiss();
                            /*判断是否是VIP专属的订单  如果是  就只能是VIP才能订购*/
                            if (shopvalues.get_static().equals(LocalValues.VALUES_SHOPPAGE
                                    .ONLY_VIP)) {
                                if (IsVip) {
                                    AlertOrderPage();

                                } else {
                                    /*不是VIP*/

                                    AlertDialog.Builder builder = new AlertDialog.Builder
                                            (ShowshopOffice.this);
                                    View item = LayoutInflater.from(getApplicationContext())
                                            .inflate(R.layout.alert_message, null);
                                    builder.setView(item);
                                    TextUnt.with(item, R.id.alert_messageTitle).setText("提交订货单失败");
                                    TextUnt.with(item, R.id.alert_messageText).setText
                                            (getResources().getString(R.string.onVipcantOrder));
                                    TextUnt.with(item, R.id.alert_messageBtnConfirm).setText
                                            ("我已了解").setTag(builder.show()).setOnClick(new View
                                            .OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog alertDialog = (AlertDialog) v.getTag();
                                            alertDialog.dismiss();

                                        }
                                    });
                                }
                            } else {
                                AlertOrderPage();
                            }


                        }
                    });

                }
            }


            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                /*初始化一个DIALOG*/
                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog
                        (ShowshopOffice.this);
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


    /**
     * 展示用户的订单确认界面
     */

    private void AlertOrderPage() {
        /**
         * 展示给用户重新确认的订单
         */
        final AlertDialog.Builder builder = new AlertDialog.Builder(ShowshopOffice
                .this);
        View confirmdeliverItem = LayoutInflater.from(ShowshopOffice.this).inflate(R.layout
                .assembly_confirmdeliver, null);
        builder.setView(confirmdeliverItem);
        /*设置标题*/

        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliverShopname).setText(shopvalues
                .getTitle());
        /*设置图片*/
        ImageView deliverShopimg = confirmdeliverItem.findViewById(R.id
                .assembly_confirmdeliverShopimg);
        /*设置图片*/
        Glide.with(getApplicationContext()).load("http://f.freep" + "" + "" + "" + "" + "" + "" +
                ".cn/583105/SHOP_DATABASE/" + shopvalues.getImg()).into(deliverShopimg);
        /*设置数量*/
        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliverShopNumber).setText("订购数量:"
                + select_number.getText().toString() + shopvalues.getCompany());
        /*设置箱规*/
        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliverShopSpec).setText("规格:" +
                shopvalues.getCompany() + "装X" + shopvalues.getSpec() + shopvalues.getSplitUnit());
        /*设置保质期和生产日期*/
        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliverShopPd_Exp).setText("生产日期:"
                + shopvalues.getExp() + "·保质期:" + shopvalues.getPd() + "天");
        /*设置价格*/
        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliverShopTp).setText("商品单价:" +
                shopvalues.getTp() + "/" + shopvalues.getCompany());
        /*设置用户的店铺的名称*/
        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliverUsername).setText
                (userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPNAME));
        /*设置用户的手机*/
        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_Shoptel).setText
                (userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPTEL));
        /*设置用户的地址*/
        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_Shopaddr).setText
                (userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPADDR));
        /*设置用户的店铺负责人*/
        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_shopusePeople).setText
                (userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPUSEPEOPLE));
        /**
         * 计算总和
         */
        float deliverTotal = Math.round(Float.valueOf(select_number.getText().toString().trim())
                * Float.valueOf(shopvalues.getTp().trim()) * 100) / 100;

        /*设置总和*/
        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverTotal).setText(Tools
                .calcToRide(select_number.getText().toString().trim(), shopvalues.getTp().trim())
        ).setTextColor(getResources().getString(R.color.colorPrice));

        /**
         * 判断用户是否为VIP
         */

        /*商品的VIP图标*/
        ImageView deliverShopequity = confirmdeliverItem.findViewById(R.id
                .assembly_confirmdeliverShopEquity);
        /*判断状态*/
        switch (shopvalues.get_static()) {
            case LocalValues.VALUES_SHOPPAGE.NORMAL:
                /*正常*/
                /*设置原始价格为不显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(false);
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(false);
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_BtnSendorder)
                        .setText("确定提交").setTextColor("#ffffff").setBackColor(getResources()
                        .getString(R.color.ThemeColor));

                break;
            case LocalValues.VALUES_SHOPPAGE.PROMOTION:
                /*促销*/
                /*设置原始价格为显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(true).setText(shopvalues.getDlp().trim()).setMidcourtLine();
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(true);
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_BtnSendorder)
                        .setText("确定提交").setTextColor("#ffffff").setBackColor(getResources()
                        .getString(R.color.colorPromotion));
                break;
            case LocalValues.VALUES_SHOPPAGE.REDUCTION:
                /*满减*/
                /*设置原始价格为不显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(false);
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(false);
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_BtnSendorder)
                        .setText("确定提交").setTextColor("#ffffff").setBackColor(getResources()
                        .getString(R.color.colorReduction));

                break;
            case LocalValues.VALUES_SHOPPAGE.VOLUME:
                /*用卷*/
                /*设置原始价格为不显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(false);
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(false);
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_BtnSendorder)
                        .setText("确定提交").setTextColor("#ffffff").setBackColor(getResources()
                        .getString(R.color.colorVolumn));
                break;
            case LocalValues.VALUES_SHOPPAGE.ONLY_ONE:
                /*只能购买一件*/
                /*设置原始价格为显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(true).setText(shopvalues.getDlp()).setMidcourtLine();
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(true);
                /*设置价格*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverTotal).setTextColor
                        (getResources().getString(R.color.colorPayonly));
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_BtnSendorder)
                        .setText("确定提交").setTextColor("#ffffff").setBackColor(getResources()
                        .getString(R.color.colorPayonly));

                break;
            case LocalValues.VALUES_SHOPPAGE.ONLY_VIP:
                /*只有Vip可以购买*/
                /*设置VIP的颜色为VIP专属颜色*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverTotal).setTextColor
                        (getResources().getString(R.color.colorVip));
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_TotalSymbol)
                        .setTextColor(getResources().getString(R.color.colorVip));
                /*设置原始价格为显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(true).setText(shopvalues.getDlp().trim()).setMidcourtLine();
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(true);
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_BtnSendorder)
                        .setText("确定提交").setTextColor("#ffffff").setBackColor(getResources()
                        .getString(R.color.colorVip));

                break;
            case LocalValues.VALUES_SHOPPAGE.WHOLEASALE:
                /*商品拼团*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverTotal).setTextColor
                        (getResources().getString(R.color.colorWholeasale));
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_TotalSymbol)
                        .setTextColor(getResources().getString(R.color.colorWholeasale));
                /*设置原始价格为显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(true).setText(shopvalues.getDlp().trim()).setMidcourtLine();
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(true);
                /*将按钮设置为拼团*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_BtnSendorder)
                        .setText("确定拼团").setTextColor("#ffffff").setBackColor(getResources()
                        .getString(R.color.colorWholeasale));
                break;
            case LocalValues.VALUES_SHOPPAGE.RESERVE:
                /*商品预定*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverTotal).setTextColor
                        (getResources().getString(R.color.colorReserve));
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_TotalSymbol)
                        .setTextColor(getResources().getString(R.color.colorReserve));
                /*设置原始价格为显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(true).setText(shopvalues.getDlp().trim()).setMidcourtLine();
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(true);
                /*将按钮设置为拼团*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_BtnSendorder)
                        .setText("确定预定").setTextColor("#ffffff").setBackColor(getResources()
                        .getString(R.color.colorReserve));
                break;
        }

        if (IsVip) {
            /*VIP提供的服务*/
            TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliverShopVipMsg)
                    .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color
                            .colorVip), getResources().getString(R.color.colorVip), 5))
                    .setTextColor("#ffffff").setText(getResources().getString(R.string.isVipmsg));
            /*设置Vip图标*/
            deliverShopequity.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R
                    .drawable.ico_equity, getResources().getString(R.color.colorVip)));
            /*设置名字旁边的图标*/
            TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliverIcoVip).setBackground
                    (Tools.CreateDrawable(1, getResources().getString(R.color.colorVip),
                            getResources().getString(R.color.colorVip), 5)).setTextColor
                    ("#ffffff").setText("Vip");

        } else {
            /*不是Vip*/
            TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliverShopVipMsg)
                    .setBackground(Tools.CreateDrawable(1, getResources().getString(R.color
                            .colornoVip), getResources().getString(R.color.colornoVip), 5))
                    .setTextColor("#ffffff").setText(getResources().getString(R.string.noVipmsg));
            deliverShopequity.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R
                    .drawable.ico_equity, getResources().getString(R.color.colornoVip)));
            /*设置名字旁边的图标*/
            TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliverIcoVip).setBackground
                    (Tools.CreateDrawable(1, getResources().getString(R.color.colornoVip),
                            getResources().getString(R.color.colornoVip), 5)).setTextColor
                    ("#ffffff").setText("Vip");
        }
        /*确定通知仓库发货的按钮*/

        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmdeliver_BtnSendorder).setOnClick
                (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog Ad = (AlertDialog) v.getTag();
                Ad.dismiss();/*关闭确认订单界面*/
                /*开始整理数据*/
                if (IsVip) {
                    toSendOrder();
                } else {
                    final AlertDialog alertDialog;
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowshopOffice.this);
                    View item = LayoutInflater.from(getApplicationContext()).inflate(R.layout
                            .alert_message, null);
                    builder1.setView(item);
                    alertDialog = builder1.show();
                    TextUnt.with(item, R.id.alert_messageText).setText(getResources().getString(R
                            .string.noVipnoSerivce));
                    TextUnt.with(item, R.id.alert_messageBtnConfirm).setTag(alertDialog)
                            .setOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog Ad = (AlertDialog) v.getTag();
                            Ad.dismiss();
                            toSendOrder();
                        }
                    }).setTag(alertDialog).setText("我已知晓,确定提交");

                }
            }
        }).setTag(builder.show());

        builder.setCancelable(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initMainpage() {
        /**
         * 判断是否登录账户
         */
        if (shopvalues.getSt_loginin()) {
            /*判断用户是否为Vip*/
            if (shopvalues.getVip()) {
                TextUnt.with(this, R.id.activity_showshopoffice_deliverVip).setText("Vip超时赔付")
                        .setBackground(Tools.CreateDrawable(getApplicationContext(), 1, R.color
                                .colorVip, R.color.colorVip, 5)).setTextColor("#ffffff");
            } else {
                TextUnt.with(this, R.id.activity_showshopoffice_deliverVip).setText("Vip超时赔付")
                        .setBackground(Tools.CreateDrawable(getApplicationContext(), 1, R.color
                                .colornoVip, R.color.colornoVip, 5)).setTextColor("#ffffff");
            }
            /*显示商品的距离*/
            if (!shopvalues.getSt_BusinessLong().equals("")) {
                int distance = Tools.getDistance(shopvalues.getSt_BusinessLong(), shopvalues
                        .getSt_businessLat(), userToolsInstance.GetUserpageOnAction(LocalAction
                        .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_SHOPLONG), userToolsInstance
                        .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE
                                .ACTION_LOCALUSERPAGE_SHOPLAT));
                if (shopvalues.get_static().equals(LocalValues.VALUES_SHOPPAGE.RESERVE) ||
                        shopvalues.get_static().equals(LocalValues.VALUES_SHOPPAGE.WHOLEASALE)) {
                    //如果商品正在拼团 或者是正在预定 就不显示什么时候到达了
                    TextUnt.with(this, R.id.activity_showshopoffice_toUsertime).setText("15");
                } else {
                    if (distance <= 0) {
                        /*如果小于一公里  则直接约等于1公里范围内*/
                        TextUnt.with(this, R.id.activity_showshopoffice_distance).setText("1");
                        TextUnt.with(this, R.id.activity_showshopoffice_toUsertime).setText("1天");
                    } else {
                        /*计算出来的公里数为>2公里*/
                        TextUnt.with(this, R.id.activity_showshopoffice_toUsertime).setText("2天");
                        TextUnt.with(this, R.id.activity_showshopoffice_distance).setText(String
                                .valueOf(distance));
                    }
                }
            } else {
                /*不显示距离*/
                findViewById(R.id.activity_showshopoffice_DistanceBody).setVisibility(View.GONE);
            }
            /*设置批发价格*/
            TextUnt.with(SHOP_TP).setText(shopvalues.getTp());
            /*设置零售价*/
            int retail = Integer.valueOf(shopvalues.getRetail()) / Integer.valueOf(shopvalues
                    .getSpec());
            TextUnt.with(SHOP_RETAIL).setText("终端建议售价:" + String.valueOf(retail) + "元/" +
                    shopvalues.getSplitUnit());
            /*登录之后 判断是否下单过*/
            Log.i(MSG, "用户购买数量:" + St_payhow);
            if (!St_payhow.equals("0")) {
                /*购买过了*/
                if (shopvalues.get_static().equals(LocalValues.VALUES_SHOPPAGE.RESERVE)) {
                    TextUnt.with(btnAccount).setText("您已经预定" + St_payhow + shopvalues.getCompany
                            ()).setBackground(Tools.CreateDrawable(1, getResources().getString(R
                            .color.colorReserve), getResources().getString(R.color.colorReserve),
                            5)).setTextColor("#ffffff").setTag(false);

                } else {
                    TextUnt.with(btnAccount).setText("您已通知仓库发货" + St_payhow + shopvalues
                            .getCompany()).setBackground(Tools.CreateDrawable(1, "#666666",
                            "#666666", 5)).setTextColor("#ffffff").setTag(false);

                }
            } else {
                TextUnt.with(btnAccount).setText("通知仓库发货").setBackground(Tools.CreateDrawable(1,
                        getResources().getString(R.color.ThemeColor), getResources().getString(R
                                .color.ThemeColor), 5)).setTextColor("#ffffff").setTag(true);

            }
        } else {
            //没有登录 顺便要删除本地存储的文件
            userToolsInstance.ClearLocalCach();/*清空本地数据文件*/
            /*没有登录的情况下 不能设置不能发送订货单*/
            TextUnt.with(btnAccount).setText("未登录,请登录后使用").setBackground(Tools.CreateDrawable(1,
                    "#666666", "#666666", 5)).setTextColor("#ffffff").setTag(false);
            TextUnt.with(SHOP_TP).setText("*.*");
            /*设置批发价格*/
            SHOP_DLP.setVisibility(View.GONE);
            TextUnt.with(SHOP_DLP).setVisibility(false);
            /*设置零售价*/
            TextUnt.with(SHOP_RETAIL).setText("终端建议售价:*.*元/" + shopvalues.getSplitUnit());

        }
        /*设置品牌图片 如果品牌数据为空 就不要显示了*/
        if (shopvalues.getSt_Brandaddr().equals("")) {
            findViewById(R.id.activity_showshopoffice_BrandBody).setVisibility(View.GONE);
            findViewById(R.id.activity_showshopoffice_BrandTitleBody).setVisibility(View.GONE);
        } else {
            TextUnt.with(this, R.id.activity_showshopoffice_Brandaddr).setText(shopvalues
                    .getSt_Brandaddr());
            TextUnt.with(this, R.id.activity_showshopoffice_Brandtitle).setText(shopvalues
                    .getBrand());
            ImageView brandImg = findViewById(R.id.activity_showshopoffice_BrandImg);
            Glide.with(getApplicationContext()).load(shopvalues.getSt_Brandimg())
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).into
                    (brandImg);
        }
        /*设置条码图片*/
        if (shopvalues.getBarcode().equals("") || shopvalues.getBarcode().equals("0") ||
                shopvalues.getBarcode().equals("暂无")) {
            imgBarcode.setVisibility(View.GONE);
        } else {
            Log.i(MSG, "图片条码地址:" + "http://api.k780.com/?app=barcode.get&bc_text=" + shopvalues
                    .getBarcode() + "&appkey=" + getResources().getString(R.string.nowApiKey) +
                    "&sign=" + getResources().getString(R.string.nowApiMd5));
            Glide.with(ShowshopOffice.this).load("http://api.k780.com/?app=barcode.get&bc_text="
                    + shopvalues.getBarcode() + "&appkey=" + getResources().getString(R.string
                    .nowApiKey) + "&sign=" + getResources().getString(R.string.nowApiMd5))
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).into
                    (imgBarcode);
        }
        /*设置商品参数的Body的边框*/
        valuesBody.setBackground(Tools.CreateDrawable(1, "#efefef", "#efefef", 10));
        /*判断网络整理的标题是否为空*/
        Log.i(MSG, "标题为:" + shopvalues.getTitle());
        //添加起订
        TextView tp = new TextView(otherMessage.getContext());
        tp.setPadding(10, 7, 10, 7);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(15, 0, 0, 0);
        tp.setLayoutParams(params);
        otherMessage.addView(tp);

        /*界面初始化*/
        TextUnt.with(SHOP_BUSINESS).setText("云仓库专送");
        TextUnt.with(SHOP_TITLE).setText(shopvalues.getTitle());
        /*判断商品的状态*/
        switch (shopvalues.get_static()) {
            case LocalValues.VALUES_SHOPPAGE.NORMAL:
                /*正常*/
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(false);
                /*设置价格颜色*/
                SHOP_TP.setTextColor(Color.parseColor(getResources().getString(R.color
                        .colorPrice)));
                TextUnt.with(this, R.id.activity_showshopoffice_tpSymbol).setTextColor
                        (getResources().getString(R.color.colorPrice));
                /**
                 * 设置虚线价格
                 */
                TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setTextColor(R.color
                        .color_dlpsymbol).setVisibility(false);
                TextUnt.with(this, R.id.activity_showshopoffice_dlp).setTextColor(getString(R
                        .color.color_dlpsymbol)).setText(shopvalues.getDlp()).setVisibility(false);
                break;
            case LocalValues.VALUES_SHOPPAGE.PROMOTION:
                /*促销*/
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(true)
                        .setText(R.string.shop_promotion).setBackground(Tools.CreateDrawable(1,
                        getResources().getString(R.color.colorPromotion), getResources()
                                .getString(R.color.colorPromotion), 5));
                SHOP_TP.setTextColor(Color.parseColor(getResources().getString(R.color
                        .colorPromotion)));
                TextUnt.with(this, R.id.activity_showshopoffice_tpSymbol).setTextColor
                        (getResources().getString(R.color.colorPromotion));
                /**
                 * 设置虚线价格
                 */
                if (shopvalues.getSt_loginin() && !shopvalues.getDlp().equals("0") && !shopvalues
                        .getDlp().equals("")) {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText(shopvalues
                            .getDlp()).setMidcourtLine();
                } else {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText("*.*")
                            .setMidcourtLine();
                }


                break;
            case LocalValues.VALUES_SHOPPAGE.REDUCTION:
                /*设置状态*/

                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(true)
                        .setText(R.string.shop_reduction).setBackground(Tools.CreateDrawable(1,
                        getResources().getString(R.color.colorReduction), getResources()
                                .getString(R.color.colorReduction), 5));
                SHOP_TP.setTextColor(Color.parseColor(getResources().getString(R.color
                        .colorReduction)));
                TextUnt.with(this, R.id.activity_showshopoffice_tpSymbol).setTextColor
                        (getResources().getString(R.color.colorReduction));

                /**
                 * 设置虚线价格
                 */
                if (shopvalues.getSt_loginin() && !shopvalues.getDlp().equals("0") && !shopvalues
                        .getDlp().equals("")) {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText(shopvalues
                            .getDlp()).setMidcourtLine();
                } else {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText("*.*")
                            .setMidcourtLine();
                }

                break;
            case LocalValues.VALUES_SHOPPAGE.VOLUME:
                /*设置状态*/
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(true)
                        .setText(R.string.shop_volume).setBackground(Tools.CreateDrawable(1,
                        getResources().getString(R.color.colorVolumn), getResources().getString(R
                                .color.colorVolumn), 5));
                SHOP_TP.setTextColor(Color.parseColor(getResources().getString(R.color
                        .colorVolumn)));
                TextUnt.with(this, R.id.activity_showshopoffice_tpSymbol).setTextColor
                        (getResources().getString(R.color.colorVolumn));

                /**
                 * 设置虚线价格
                 */
                if (shopvalues.getSt_loginin() && !shopvalues.getDlp().equals("0") && !shopvalues
                        .getDlp().equals("")) {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText(shopvalues
                            .getDlp()).setMidcourtLine();

                } else {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText("*.*")
                            .setMidcourtLine();

                }
                break;
            case LocalValues.VALUES_SHOPPAGE.ONLY_VIP:
                /*只有VIP可以购买*/
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(true)
                        .setText(R.string.shop_vip).setTextColor("#ffffff").setBackground(Tools
                        .CreateDrawable(1, getResources().getString(R.color.colorVip),
                                getResources().getString(R.color.colorVip), 5));
                SHOP_TP.setTextColor(Color.parseColor(getResources().getString(R.color.colorVip)));
                TextUnt.with(this, R.id.activity_showshopoffice_tpSymbol).setTextColor
                        (getResources().getString(R.color.colorVip));

                shopvalues.setonlyVipPay(true);
                /**
                 * 设置虚线价格
                 */
                if (shopvalues.getSt_loginin() && !shopvalues.getDlp().equals("0") && !shopvalues
                        .getDlp().equals("")) {

                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText(shopvalues
                            .getDlp()).setMidcourtLine();

                } else {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText("*.*")
                            .setMidcourtLine();

                }
                break;

            case LocalValues.VALUES_SHOPPAGE.ONLY_ONE:
                /*只能购买一件*/
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(true)
                        .setText(R.string.shop_only).setTextColor("#ffffff").setBackground(Tools
                        .CreateDrawable(1, getResources().getString(R.color.colorPayonly),
                                getResources().getString(R.color.colorPayonly), 5));
                SHOP_TP.setTextColor(Color.parseColor(getResources().getString(R.color
                        .colorPayonly)));
                TextUnt.with(this, R.id.activity_showshopoffice_tpSymbol).setTextColor
                        (getResources().getString(R.color.colorPayonly));

                /*隐藏加减BTN*/
                TextUnt.with(btn_select_del).setTag(false);
                TextUnt.with(btn_select_add).setTag(false);
                /**
                 * 设置虚线价格
                 */
                if (shopvalues.getSt_loginin() && !shopvalues.getDlp().equals("0") && !shopvalues
                        .getDlp().equals("")) {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText(shopvalues
                            .getDlp().trim()).setMidcourtLine();
                } else {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText("*.*");
                }
                break;
            case LocalValues.VALUES_SHOPPAGE.WHOLEASALE:
                /*设置按钮为开启拼团*/
                TextUnt.with(this, R.id.activity_showshopoffice_btnAccount).setBackground(Tools
                        .CreateDrawable(1, getResources().getString(R.color.colorWholeasale),
                                getResources().getString(R.color.colorWholeasale), 5)).setText
                        ("我要拼团");
                TextUnt.with(this, R.id.activity_showshopoffice_tpSymbol).setTextColor
                        (getResources().getString(R.color.colorWholeasale));
                SHOP_TP.setTextColor(Color.parseColor(getResources().getString(R.color
                        .colorWholeasale)));
                /**
                 * 设置虚线价格
                 */
                if (shopvalues.getSt_loginin() && !shopvalues.getDlp().equals("0") && !shopvalues
                        .getDlp().equals("")) {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText(shopvalues
                            .getDlp());
                    /*设置价格符号的颜色*/
                } else {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText("*.*");
                }
                break;

            case LocalValues.VALUES_SHOPPAGE.RESERVE:
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(true)
                        .setText(R.string.shop_reserve).setTextColor("#ffffff").setBackColor
                        (getResources().getString(R.color.colorReserve));
                SHOP_TP.setTextColor(Color.parseColor(getResources().getString(R.color
                        .colorReserve)));
                TextUnt.with(this, R.id.activity_showshopoffice_tpSymbol).setTextColor(R.color
                        .colorReserve);
                /**
                 * 设置虚线价格
                 */
                if (shopvalues.getSt_loginin() && !shopvalues.getDlp().equals("0") && !shopvalues
                        .getDlp().equals("")) {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText(shopvalues
                            .getDlp()).setMidcourtLine();
                    if (St_payhow.equals("0")) {
                        TextUnt.with(this, R.id.activity_showshopoffice_btnAccount).setBackground
                                (Tools.CreateDrawable(1, getResources().getString(R.color
                                        .colorReserve), getResources().getString(R.color
                                        .colorReserve), 5)).setText("预定商品");
                    }


                } else {
                    TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility
                            (true).setTextColor(R.color.color_dlpsymbol).setVisibility(true);
                    TextUnt.with(this, R.id.activity_showshopoffice_dlp).setVisibility(true)
                            .setTextColor(getString(R.color.color_dlpsymbol)).setText("*.*");
                }
                alertReserve();
                break;
        }


        /**
         * 初始化布局
         */
        /*设置保质日期*/
        TextUnt.with(SHOP_PD).setText("保质期:" + shopvalues.getPd());
        /*设置生产期*/
        TextUnt.with(SHOP_EXP).setText("生产日期:" + shopvalues.getExp());
        /*设置品牌*/
        TextUnt.with(SHOP_SPEC).setText("箱规:" + shopvalues.getSpec() + shopvalues.getSplitUnit());
        /*设置等级*/
        TextUnt.with(SHOP_GRADE).setText("等级:" + shopvalues.getGrade());
        /*设置条码*/
        TextUnt.with(SHOP_BARCODE).setText("条码:" + shopvalues.getBarcode());
        /*设置要显示的品牌*/
        TextUnt.with(SHOP_SHOWBRAND).setText(shopvalues.getBrand());
        /*设置起订按钮*/
        TextUnt.with(tp).setText(shopvalues.getSu() + shopvalues.getCompany() + "起订")
                .setTextColor("#ffffff").setTextSize(8).setBackground(Tools.CreateDrawable(1,
                getResources().getString(R.color.ThemeColor), getResources().getString(R.color
                        .ThemeColor), 5));

        OpeningAnimation();
        /**
         *  开始访问网络图片
         */
        photo.setImageDrawable(null);
        Glide.with(getApplicationContext()).load("http://f.freep.cn/583105/SHOP_DATABASE/" +
                shopvalues.getImg()).into(photo);
        /*数量减少*/
        btn_select_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    if ((Boolean) v.getTag()) {
                        /*可以被点击*/
                        int i = Integer.parseInt(select_number.getText().toString());
                        i -= 1;

                        if (i <= 0) {
                            Toast.makeText(getApplicationContext(), "配送的数量不能小于0哦", Toast
                                    .LENGTH_SHORT).show();
                        } else {
                            try {
                                int su = Integer.parseInt(shopvalues.getSu());
                                float tp = Float.parseFloat(shopvalues.getTp());
                                Total -= su * tp;
                                float total = su * tp;
                            } catch (Exception e) {
                                Log.e(MSG, "计算总和错误:" + e.getMessage());

                            }
                            select_number.setText(String.valueOf(i));
                        }
                    } else {
                        /*不能被点击*/
                        Toast.makeText(getApplicationContext(), "限定的商品不能减少", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    /*可以被点击*/
                    int i = Integer.parseInt(select_number.getText().toString());
                    i -= 1;

                    if (i <= 0) {
                        Toast.makeText(getApplicationContext(), "配送的数量不能小于0哦", Toast
                                .LENGTH_SHORT).show();
                    } else {
                        try {
                            int su = Integer.parseInt(shopvalues.getSu());
                            float tp = Float.parseFloat(shopvalues.getTp());
                            Total -= su * tp;
                            float total = su * tp;
                        } catch (Exception e) {
                            Log.e(MSG, "计算总和错误:" + e.getMessage());

                        }
                        select_number.setText(String.valueOf(i));
                    }

                }


            }
        });
        /*数量增加*/
        btn_select_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    if ((Boolean) v.getTag()) {
                        int i = Integer.parseInt(select_number.getText().toString());
                        i += 1;
                        select_number.setText(String.valueOf(i));
                        try {
                            int su = Integer.parseInt(shopvalues.getSu());
                            float tp = Float.parseFloat(shopvalues.getTp());
                            Total += su * tp;
                        } catch (Exception e) {
                            Log.e(MSG, "计算总和错误:" + e.getMessage());

                        }
                        int total = i * (Integer.valueOf(shopvalues.getSu()));
                    } else {
                        Toast.makeText(getApplicationContext(), "限定的商品不能增加", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    int i = Integer.parseInt(select_number.getText().toString());
                    i += 1;
                    select_number.setText(String.valueOf(i));
                    try {
                        int su = Integer.parseInt(shopvalues.getSu());
                        float tp = Float.parseFloat(shopvalues.getTp());
                        Total += su * tp;
                    } catch (Exception e) {
                        Log.e(MSG, "计算总和错误:" + e.getMessage());

                    }
                    int total = i * (Integer.valueOf(shopvalues.getSu()));
                }


            }
        });
    }


    /**
     * 弹出一个提示框 提示用户该商品是预定商品
     */
    private void alertReserve() {
        giftshopvalues = XmlTagValuesFactory.getGiftshopvalues();
        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(shopData);
        /*处理赠品数据*/
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

                    /*赠品的标题*/
                    if (tag.equals(LocalAction.ACTION_GIFTSHOP.GIFT_TITLE)) {
                        giftshopvalues.setTitle(pullParser.nextText().trim());
                    }
                    /*赠品的规则*/
                    if (tag.equals(LocalAction.ACTION_GIFTSHOP.GIFT_CONDTION)) {
                        giftshopvalues.setCondtion(pullParser.nextText().trim());
                    }
                    /*商品的重量*/
                    if (tag.equals(LocalAction.ACTION_GIFTSHOP.GIFT_WEIGHT)) {
                        giftshopvalues.setWeight(pullParser.nextText().trim());
                    }
                    /*商品的单位*/
                    if (tag.equals(LocalAction.ACTION_GIFTSHOP.GIFT_COMPANY)) {
                        giftshopvalues.setCompany(pullParser.nextText().trim());
                    }
                    /*商品的规格*/
                    if (tag.equals(LocalAction.ACTION_GIFTSHOP.GIFT_SPEC)) {
                        giftshopvalues.setSpec(pullParser.nextText().trim());
                    }
                    /*商品的生产日期*/
                    if (tag.equals(LocalAction.ACTION_GIFTSHOP.GIFT_EXP)) {
                        giftshopvalues.setExp(pullParser.nextText().trim());
                    }
                    /*商品的保质期*/
                    if (tag.equals(LocalAction.ACTION_GIFTSHOP.GIFT_PD)) {
                        giftshopvalues.setPd(pullParser.nextText().trim());
                    }
                    /*商品剩余的数量*/
                    if (tag.equals(LocalAction.ACTION_GIFTSHOP.GIFT_STOCKNUMBER)) {
                        giftshopvalues.setStocknumber(pullParser.nextText().trim());
                    }
                    /*商品的图片地址*/
                    if (tag.equals(LocalAction.ACTION_GIFTSHOP.GIFT_IMG)) {
                        giftshopvalues.setImg(pullParser.nextText().trim());
                    }
                    /*商品的条码*/
                    if (tag.equals(LocalAction.ACTION_GIFTSHOP.GIFT_BARCODE)) {
                        giftshopvalues.setBarcode(pullParser.nextText().trim());
                    }
                } catch (Exception e) {
                    Log.e(MSG, "处理商品的赠品XML失败:" + e.getMessage());
                }

            }

            @Override
            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {

            }

            @Override
            public void onEndDocument() {
                handlerGiftshop();

            }
        });
        /*赠品标题的边框*/
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowshopOffice.this);
        View item = LayoutInflater.from(getApplicationContext()).inflate(R.layout.alert_message,
                null);
        TextUnt.with(item, R.id.assembly_reservebody_msg).setBackground(Tools.CreateDrawable(1,
                getResources().getString(R.color.ThemeColor), getResources().getString(R.color
                        .ThemeColor), 5)).setTextColor("#ffffff");
        TextUnt.with(item, R.id.alert_messageTitle).setText("系统提示");
        TextUnt.with(item, R.id.alert_messageText).setText(getResources().getString(R.string
                .alertReserveMsg));
        builder.setView(item);
        TextUnt.with(item, R.id.alert_messageBtnConfirm).setText("我已知晓").setTag(builder.show())
                .setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = (AlertDialog) v.getTag();
                alertDialog.dismiss();
            }
        });

    }


    /**
     * 初始化 商品赠品的界面
     */
    private void handlerGiftshop() {
        /**
         * 载入赠品信息
         */


        View gitShop = LayoutInflater.from(getApplicationContext()).inflate(R.layout
                .assembly_reservebody, null);
        LinearLayout gitShopbody = findViewById(R.id.activity_showshopoffice_reserveBody);
        gitShopbody.addView(gitShop);
        /*设置重量*/
        TextUnt.with(gitShop, R.id.assembly_reservebody_weight).setText(giftshopvalues.getWeight());
        /*设置规格*/
        TextUnt.with(gitShop, R.id.assembly_reservebody_spec).setText(giftshopvalues.getSpec());
        /*设置保质期*/
        TextUnt.with(gitShop, R.id.assembly_reservebody_pd).setText(giftshopvalues.getPd());
        /*设置单位*/
        TextUnt.with(gitShop, R.id.assembly_reservebody_company).setText(giftshopvalues
                .getCompany());
        /*生产日期*/
        TextUnt.with(gitShop, R.id.assembly_reservebody_exp).setText(giftshopvalues.getExp());
        /*设置库存*/
        TextUnt.with(gitShop, R.id.assembly_reservebody_stocknumber).setText(giftshopvalues
                .getStocknumber());
        /*设置规格*/
        TextUnt.with(gitShop, R.id.assembly_reservebody_msg).setText(giftshopvalues.getCondtion());
        LinearLayout gitShopvalusbody = gitShop.findViewById(R.id.activity_gitshop_valuesBody);
        //赠品的边框
        gitShopvalusbody.setBackground(Tools.CreateDrawable(1, "#efefef", "#efefef", 10));
        //加载赠品图片
        ImageView giftimg = gitShop.findViewById(R.id.assembly_reservebody_img);
        Glide.with(getApplicationContext()).load(LocalValues.HTTP_ADDRS.HTTP_ADDR_IMG_URL +
                "cuxiao/20190809_a.png").diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false).into(giftimg);
    }


    /**
     * 计算开场动画
     */
    private void OpeningAnimation() {
        metrics = new DisplayMetrics();/*获取屏幕矩阵*/
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        /*重新计算图片的高度实现开场动画*/
        final ViewGroup.LayoutParams photoParams = photo.getLayoutParams();
        window_height = metrics.heightPixels;
        photoParams.height = metrics.heightPixels;
        photoParams.width = metrics.widthPixels;
        photo.setLayoutParams(photoParams);
        /*图片边框动画*/
        ValueAnimator anim = ValueAnimator.ofInt(metrics.heightPixels, metrics.heightPixels * 8 /
                16);
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams params = photo.getLayoutParams();
                params.height = (int) animation.getAnimatedValue();
                photo.setLayoutParams(params);
            }
        });
        anim.start();
        /*重新计算高度和宽度*/
        ViewGroup.LayoutParams Bodyparams = countDownadavert.getLayoutParams();
        Bodyparams.width = metrics.widthPixels;
        Bodyparams.height = Tools.getCount_downAdvert(metrics);
        countDownadavert.setLayoutParams(Bodyparams);
        countDownadavert.setVisibility(View.GONE);
        scrollViewBiggerPhoto.setImageHead(photo, metrics);
    }

    @Override
    public void onBackPressed() {
        if (isShowCart) {
        } else {
            super.onBackPressed();
        }
    }


    /**
     * 提交订单数据信息到服务器
     */
    private void toSendOrder() {
        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);
        /*标题*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TITLE, SHOP_TITLE
                .getText().toString().trim());
        /*唯一ID*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ONLYID, shopvalues
                .getOnlyid());
        /*订购数量*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_PAYHOW,
                select_number.getText().toString().trim());
        /*原始的价格 需要定格 不能以平台的价格随时变动*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TP, shopvalues
                .getTp());
        /*订单的状态 需要定格  不能以平台的状态随时变动*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_STATIC, shopvalues
                .get_static());
        /*商品的日期*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_EXP, shopvalues
                .getExp());
        /*商品的保质期*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_PD, shopvalues
                .getPd());
        /*商品的规格*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SPEC, shopvalues
                .getSpec());
        /*商品的拆分单位*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SPLITUNIT,
                shopvalues.getSplitUnit());
        /*商品的条码*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_BARCODE,
                shopvalues.getBarcode());
        /*订单的打包单位 需要定格 不能以平台的单位随时变动*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_COMPANY,
                shopvalues.getCompany());
        /*商品的图片 需要定格 有些时候促销的图片是有依据的*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_IMG, shopvalues
                .getImg());
        /*商品的标题 需要定格  不能以平台的标题随时变动*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TITLE, shopvalues
                .getTitle());
        /*设置下单时候的VIP状态*/
        if (IsVip) {
            xmlInstance.setXmlTree(LocalAction.ACTION_SENDORDER_SYSTEM.ACTION_ORDER_INVIPSTATUS,
                    LocalValues.VALUES_USERCENTER.IS_VIP);
        } else {
            xmlInstance.setXmlTree(LocalAction.ACTION_SENDORDER_SYSTEM.ACTION_ORDER_INVIPSTATUS,
                    LocalValues.VALUES_USERCENTER.IS_NOT_VIP);
        }
        xmlInstance.setXmlTree(LocalAction.ACTION_SENDORDER_SYSTEM.ACTION_ORDER_DISTANCE, "13");

        xmlInstance.overDom();
        Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_SAVEUSERODER, new
                ProgramInterface() {
            @Override
            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {
                Log.i(MSG, "提交订单的返回信息:" + data.trim());
                JsonEndata jsonEndata = new JsonEndata(data.trim());
                if (jsonEndata.getJsonKeyValue(LocalAction.JSON_ACTION.ACTION_JSON_RETURN_MSG)
                        .equals(LocalValues.NET_ERROR) && !TextUtils.isEmpty(data.trim())) {
                    Toast.makeText(getApplicationContext(), "错误,订单提交失败", Toast.LENGTH_SHORT).show();
                    _refreshDialog.dismiss();
                    init();

                } else {
                    /*重新出现一个对话框 提示用户已经提交成功*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowshopOffice.this);
                    View item = LayoutInflater.from(getApplicationContext()).inflate(R.layout
                            .alert_message, null);
                    builder.setView(item);
                    TextUnt.with(item, R.id.alert_messageTitle).setText("发货单提交成功");
                    TextUnt.with(item, R.id.alert_messageBtnConfirm).setText("我已了解").setOnClick
                            (new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog alertDialog = (AlertDialog) v.getTag();
                            alertDialog.dismiss();
                            finish();

                        }
                    }).setTag(builder.show());
                    if (IsVip) {
                        /*设置VIP派送专属界面*/
                        TextUnt.with(item, R.id.alert_messageText).setText(getResources()
                                .getString(R.string.onSendOrderVipmsg));
                    } else {
                        /*不是VIP的设置专属的界面*/
                        TextUnt.with(item, R.id.alert_messageText).setText(getResources()
                                .getString(R.string.onSendOrdernoVipmsg));

                    }
                    _refreshDialog.dismiss();
                }
            }

            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                /*初始化一个DIALOG*/
                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog
                        (ShowshopOffice.this);
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
        }, xmlInstance.getXmlTree().trim());
    }

}
