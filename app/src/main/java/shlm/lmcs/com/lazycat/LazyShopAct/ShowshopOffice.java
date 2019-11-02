package shlm.lmcs.com.lazycat.LazyShopAct;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder();
        xmlInstance.initDom();
        xmlInstance.setXmlTree(LocalAction.ACTION, getshopAction);
        xmlInstance.setXmlTree(LocalAction.ACTION_SEARCHKEY.ACTION_KEYWORD, shopmesage);
        if (userToolsInstance.isLogin()) {
            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, userToolsInstance
                    .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE
                            .ACTION_LOCALUSERPAGE_ACCOUNT));
            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, userToolsInstance
                    .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE
                            .ACTION_LOCALUSERPAGE_TOKEN));
        } else {
            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, "");
            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, "");

        }
        xmlInstance.overDom();
        Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_GET_SHOPVALUES,
                new ProgramInterface() {
            @Override
            public void onSucess(String data, int code, final WaitDialog.RefreshDialog
                    _refreshDialog) {
                Log.i(MSG, "商品数据:" + data.trim());
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
                            if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                    .ACTION_SHOPVALUES_TOPAYHOW)) {
                                St_payhow = pullParser.nextText().trim();
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
                        _refreshDialog.dismiss();

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
                if ((Boolean) v.getTag()) {
                    showOrderConfirm();
                } else {
                    Toast.makeText(getApplicationContext(), "您没有登录,请你登录后发送订货单", Toast
                            .LENGTH_SHORT).show();
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
        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder();
        xmlInstance.initDom();
        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, userToolsInstance
                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_ACCOUNT));/*存入账户*/
        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, userToolsInstance
                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TOKEN)
        );/*存入密码*/
        xmlInstance.overDom();
        Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_GETUSER, new
                ProgramInterface() {
            @Override
            public void onSucess(String data, int code, final WaitDialog.RefreshDialog
                    _refreshDialog) {
                Log.i(MSG, "重新获取用户信息的请求:" + data.trim());
                if (data.trim().equals(LocalValues.VALUES_LOGIN.LOGIN_ERROR)) {
                    Toast.makeText(getApplicationContext(), "登录过期,请重新登录", Toast.LENGTH_SHORT)
                            .show();
                    if (userToolsInstance != null) {
                        userToolsInstance.ClearLocalCach();
                    }
                    _refreshDialog.dismiss();

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
                                    if (pullParser.nextText().trim().equals("0")) {
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
        TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverTotal).setText(Float
                .toString(deliverTotal)).setTextColor(getResources().getString(R.color.colorPrice));

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
                break;
            case LocalValues.VALUES_SHOPPAGE.PROMOTION:
                /*促销*/
                /*设置原始价格为显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(true).setText(shopvalues.getDlp().trim()).setMidcourtLine();
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(true);
                break;
            case LocalValues.VALUES_SHOPPAGE.REDUCTION:
                /*满减*/
                /*设置原始价格为不显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(false);
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(false);
                break;
            case LocalValues.VALUES_SHOPPAGE.VOLUME:
                /*用卷*/
                /*设置原始价格为不显示*/
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPrice)
                        .setVisibility(false);
                TextUnt.with(confirmdeliverItem, R.id.assembly_confirmedliverDottedPriceSymbol)
                        .setVisibility(false);
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
        /*判断商品的状态*/
        /*获取用户工具类*/

        /**
         * 判断是否登录账户
         */
        if (userToolsInstance.isLogin()) {
            /*设置批发价格*/
            TextUnt.with(SHOP_TP).setText(shopvalues.getTp());
            /*设置虚线价格*/
            TextUnt.with(SHOP_DLP).setText(shopvalues.getDlp()).setMidcourtLine();
            /*设置零售价*/
            TextUnt.with(SHOP_RETAIL).setText("终端建议售价:" + shopvalues.getRetail() + "元/" +
                    shopvalues.getSplitUnit());
            /*登录之后 判断是否下单过*/
            if (!St_payhow.equals("0")) {
                /*购买过了*/
                TextUnt.with(btnAccount).setText("您已通知仓库发货" + St_payhow + shopvalues.getCompany()
                ).setBackground(Tools.CreateDrawable(1, "#666666", "#666666", 5)).setTextColor
                        ("#ffffff").setTag(false);


            } else {
                TextUnt.with(btnAccount).setText("通知仓库发货").setBackground(Tools.CreateDrawable(1,
                        getResources().getString(R.color.ThemeColor), getResources().getString(R
                                .color.ThemeColor), 5)).setTextColor("#ffffff").setTag(true);

            }


        } else {
            /*设置不能发送订货单*/
            TextUnt.with(btnAccount).setText("未登录,请登录后使用").setBackground(Tools.CreateDrawable(1,
                    "#666666", "#666666", 5)).setTextColor("#ffffff").setTag(false);
            TextUnt.with(SHOP_TP).setText("*.*");
            /*设置批发价格*/
            TextUnt.with(SHOP_TP).setText("*.*");

            /*设置虚线价格*/
            SHOP_DLP.setVisibility(View.GONE);
            TextUnt.with(SHOP_DLP).setVisibility(false);
            /*设置零售价*/
            TextUnt.with(SHOP_RETAIL).setText("终端建议售价:*.*元/" + shopvalues.getSplitUnit());

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
        TextUnt.with(SHOP_BUSINESS).setText(shopvalues.getBusiness());
        TextUnt.with(SHOP_TITLE).setText(shopvalues.getTitle());
        /*判断商品的状态*/
        switch (shopvalues.get_static()) {
            case LocalValues.VALUES_SHOPPAGE.NORMAL:
                /*正常*/
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(false);
                TextUnt.with(SHOP_DLP).setVisibility(false);
                TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility(false);
                break;
            case LocalValues.VALUES_SHOPPAGE.PROMOTION:
                /*促销*/
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(true)
                        .setText("促销产品");
                TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility(true)
                        .setTextColor(getResources().getString(R.color.colorPrice));
                TextUnt.with(SHOP_DLP).setVisibility(false);
                TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility(false);
                break;
            case LocalValues.VALUES_SHOPPAGE.REDUCTION:
                /*用卷*/
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(true)
                        .setText("用卷商品");
                TextUnt.with(SHOP_DLP).setVisibility(false);
                TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility(false);
                break;
            case LocalValues.VALUES_SHOPPAGE.VOLUME:
                TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility(false);
                TextUnt.with(SHOP_DLP).setVisibility(false);
                break;
            case LocalValues.VALUES_SHOPPAGE.ONLY_VIP:
                /*只有VIP可以购买*/
                /*设置原先的单位的价格符号*/
                TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility(true)
                        .setTextColor(getResources().getString(R.color.colorPrice));
                TextUnt.with(this, R.id.activity_showshopoffice_tpSymbol).setTextColor
                        (getResources().getString(R.color.colorVip));
                TextUnt.with(this, R.id.activity_showshopoffice_tp).setTextColor(getResources()
                        .getString(R.color.colorVip));
                shopvalues.setonlyVipPay(true);
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(true)
                        .setText("VIP用户专享").setBackground(Tools.CreateDrawable(1, getResources()
                        .getString(R.color.colorVip), getResources().getString(R.color.colorVip),
                        5));
                break;

            case LocalValues.VALUES_SHOPPAGE.ONLY_ONE:
                /*只能购买一件*/
                TextUnt.with(this, R.id.activity_showshopoffice_static).setVisibility(true)
                        .setText("限定").setTextColor("#ffffff").setBackColor("#f03d65");
                TextUnt.with(SHOP_DLP).setVisibility(true).setText(shopvalues.getDlp())
                        .setTextColor("#666666");
                TextUnt.with(SHOP_TP).setTextColor(getResources().getString(R.color.ThemeColor));
                TextUnt.with(this, R.id.activity_showshopoffice_tpSymbol).setTextColor
                        (getResources().getString(R.color.ThemeColor));
                TextUnt.with(this, R.id.activity_showshopoffice_dlpSymbol).setVisibility(true)
                        .setTextColor("#666666");
                /*隐藏加减BTN*/
                TextUnt.with(btn_select_del).setTag(false);
                TextUnt.with(btn_select_add).setTag(false);
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
        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder();
        xmlInstance.initDom();
        /*标题*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TITLE, SHOP_TITLE
                .getText().toString().trim());
        /*唯一ID*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ONLYID, shopvalues
                .getOnlyid());
        /*订购数量*/
        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_PAYHOW,
                select_number.getText().toString().trim());
        /*请求提交的用户*/
        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE, userToolsInstance
                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_ACCOUNT));
        /*请求提交的用户的Token*/
        xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN, userToolsInstance
                .GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TOKEN));
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
