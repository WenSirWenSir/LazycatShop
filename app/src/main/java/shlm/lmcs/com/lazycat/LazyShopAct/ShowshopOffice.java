package shlm.lmcs.com.lazycat.LazyShopAct;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.xmlpull.v1.XmlPullParser;

import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
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
    private ScrollViewBiggerPhoto scrollViewBiggerPhoto;
    private Boolean isLoginin;/*标记用户是否已经登录状态*/
    private ImageView photo;
    private TextView btn_select_del;
    private TextView btn_select_add;
    private LinearLayout body_addordel;
    private TextView SHOP_TITLE;
    private TextView SHOP_DLP;/*商品的虚线价格*/
    private TextView SHOP_PD;/*商品的生产日期*/
    private TextView SHOP_EXP;/*商品的保质期*/
    private TextView SHOP_SPEC;/*商品的品牌*/
    private TextView SHOP_GRADE;/*商品的等级*/
    private TextView SHOP_BARCODE;/*商品的条码*/
    private TextView SHOP_RETAIL;/*商品的零售价格*/
    private TextView SHOP_SHOWBRAND;/*商品显示的品牌*/
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
    private final static int MSG_COUNT_DOWN_ADAVERT = 0;
    private final static int MSG_CLEAR_COUNT_DOWN_ADAVERT = 1;
    private DisplayMetrics metrics;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_COUNT_DOWN_ADAVERT:
                    countDownadavert.setVisibility(View.VISIBLE);
                    AlphaAnimation ap = Tools.createOnalpha(2000, true);
                    countDownadavert.setAnimation(ap);
                    /*开启线程 用来关闭广告*/
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = MSG_CLEAR_COUNT_DOWN_ADAVERT;
                            handler.sendMessage(msg);
                        }
                    }, Tools.getRandom(7, 11) * 1000);
                    break;
                case MSG_CLEAR_COUNT_DOWN_ADAVERT:
                    /*清空倒计时广告*/
                    final ValueAnimator anim = ValueAnimator.ofInt(countDownadavert.getHeight(), 0);
                    anim.setDuration(Tools.getRandom(1, 2) * 1000);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Log.e(Config.DEBUG, "ShowshopOffice.java[+]属性动画值: " + animation
                                    .getAnimatedValue() + "广告图片的高度:" + countDownadavert.getHeight
                                    ());
                            ViewGroup.LayoutParams params = countDownadavert.getLayoutParams();
                            params.height = (int) animation.getAnimatedValue();
                            countDownadavert.setLayoutParams(params);

                        }
                    });
                    anim.start();//属性改变动画
                    //AlphaAnimation clearap = Tools.clearOnalpha(Tools.getRandom(3, 6) * 1000,
                    // true);
                    //countDownadavert.startAnimation(clearap);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showshopoffice);
        setTransparentBar();


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
        /*商品的生产日期*/
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
        /*尝试加载*/
        /*图片包裹*/
        /*获取界面传值*/
        shopmesage = getBundlerValue(Config.Windows.GET_WINDOW_VALUE_SHOP_MESSAGE);/*商品信息*/
        getshopAction = getBundlerValue(Config.Windows.GET_WINDOW_VALUE_SHOP_ACTION);/*获取方式*/

        /*count down advert*/
        /**
         * 最重要的一步  首先获取到服务器的信息  进行数据的整理和初始化
         */
        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder();
        xmlInstance.initDom();
        xmlInstance.setXmlTree(LocalAction.ACTION, getshopAction);
        xmlInstance.setXmlTree(LocalAction.ACTION_SEARCHKEY.ACTION_KEYWORD, shopmesage);
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

                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_START)) {
                                shopvalues = XmlTagValuesFactory.getShopvaluesInstance();
                            }

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
                            /*商品保质期*/
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
                            /*商品生产日期*/
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

                        } catch (Exception e) {

                        }

                    }

                    @Override
                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {
                        if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_START)) {
                            /*处理完毕了*/
                            init();
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

                /**
                 * 展示给用户重新确认的订单
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowshopOffice.this);
                View confirmdeliverItem = LayoutInflater.from(ShowshopOffice.this).inflate(R
                        .layout.assembly_confirmdeliver, null);
                builder.setView(confirmdeliverItem);
                TextView deliverShopname = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmdeliverShopname);
                /*设置标题*/
                deliverShopname.setText(shopvalues.getTitle());
                /*设置图片*/
                ImageView deliverShopimg = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmdeliverShopimg);
                /*设置图片*/
                Glide.with(getApplicationContext()).load("http://f.freep" + "" + "" + "" + "" +
                        "" + ".cn/583105/SHOP_DATABASE/" + shopvalues.getImg()).into
                        (deliverShopimg);
                /*设置数量*/
                TextView deliverShophow = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmdeliverShopNumber);
                deliverShophow.setText("订购数量:" + select_number.getText().toString() + shopvalues
                        .getCompany());
                /*设置箱规*/
                TextView deliverShopspec = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmdeliverShopSpec);
                deliverShopspec.setText("规格:" + shopvalues.getCompany() + "装X" + shopvalues
                        .getSpec() + shopvalues.getSplitUnit());
                /*设置保质期和生产日期*/
                TextView deliverShopPd_exp = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmdeliverShopPd_Exp);
                deliverShopPd_exp.setText("生产日期:" + shopvalues.getExp() + "·保质期:" + shopvalues
                        .getPd() + "天");
                /*设置价格*/
                TextView deliverShopTp = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmdeliverShopTp);
                deliverShopTp.setText("商品单价:" + shopvalues.getTp() + "/" + shopvalues.getCompany());
                /**
                 * 计算总和
                 */
                float deliverTotal = Math.round(Float.valueOf(select_number.getText().toString()
                        .trim()) * Float.valueOf(shopvalues.getTp().trim()) * 100) / 100;
                TextView deliverShopTotal = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmedliverTotal);
                deliverShopTotal.setText(Float.toString(deliverTotal));
                /*虚线价格的符号*/
                TextView deliverPriceSymbol = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmedliverPriceSymbol);
                /*虚线的价格*/
                TextView deliverDottedPrices = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmedliverDottedPrice);
                /**
                 * 判断是否为促销或者其他状态
                 */
                switch (shopvalues.get_static()) {
                    case LocalValues.VALUES_SHOPPAGE.NORMAL:
                        Log.i(MSG, "正常商品");
                        TextUnt.with(deliverDottedPrices).setVisibility(false);
                        TextUnt.with(deliverPriceSymbol).setVisibility(false);
                        break;
                    case LocalValues.VALUES_SHOPPAGE.PROMOTION:
                        Log.i(MSG, "促销商品");
                        break;
                    case LocalValues.VALUES_SHOPPAGE.REDUCTION:
                        Log.i(MSG, "折扣商品");
                        break;
                    case LocalValues.VALUES_SHOPPAGE.VOLUME:
                        Log.i(MSG, "用卷商品");
                        break;
                }
                /**
                 * 判断用户是否为VIP
                 */
                /*VIP提供的服务*/
                TextView deliverVipMsg = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmdeliverShopVipMsg);
                /*商品的VIP图标*/
                ImageView deliverShopequity = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmdeliverShopEquity);
                /*名字旁边的VIP图标*/
                TextView deliverUserIco = confirmdeliverItem.findViewById(R.id
                        .assembly_confirmdeliverIcoVip);

                if (true) {
                    /*是Vip*/
                    TextUnt.with(deliverVipMsg).setBackground(Tools.CreateDrawable(1,
                            getResources().getString(R.color.colorVip), getResources().getString
                                    (R.color.colorVip), 5)).setTextColor("#ffffff").setText
                            (getResources().getString(R.string.isVipmsg));
                    /*设置Vip图标*/
                    deliverShopequity.setImageDrawable(Tools.setSvgColor(getApplicationContext(),
                            R.drawable.ico_equity, getResources().getString(R.color.colorVip)));
                    /*设置名字旁边的图标*/
                    TextUnt.with(deliverUserIco).setBackground(Tools.CreateDrawable(1,
                            getResources().getString(R.color.colorVip), getResources().getString
                                    (R.color.colorVip), 5)).setTextColor("#ffffff");

                } else {
                    /*不是Vip*/
                    TextUnt.with(deliverVipMsg).setBackground(Tools.CreateDrawable(1,
                            getResources().getString(R.color.colornoVip), getResources()
                                    .getString(R.color.colornoVip), 5)).setTextColor("#ffffff")
                            .setText(getResources().getString(R.string.noVipmsg));
                    deliverShopequity.setImageDrawable(Tools.setSvgColor(getApplicationContext(),
                            R.drawable.ico_equity, getResources().getString(R.color.colornoVip)));
                    /*设置名字旁边的图标*/
                    TextUnt.with(deliverUserIco).setBackground(Tools.CreateDrawable(1,
                            getResources().getString(R.color.colornoVip), getResources()
                                    .getString(R.color.colornoVip), 5)).setTextColor("#ffffff");
                }
                builder.show();
                builder.setCancelable(false);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {


        /**
         * 查询本地是否登录了账户 如果没有 就不要显示
         */
        /*获取用户工具类*/
        userToolsInstance = LocalProgramTools.getUserToolsInstance();
        userToolsInstance.StartPullerUserpageXml(new LocalProgramTools.UserToolsInstance
                .SetReadUserpageListener() {
            @Override
            public void onRead(String tag, String values) {
                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TOKEN)) {
                    if (!TextUtils.isEmpty(values)) {
                        /*有登录*/
                        isLoginin = true;
                    } else {
                        /*没有登录*/
                        isLoginin = false;
                    }
                }
            }

            @Override
            public void onError() {
                /*处理失败  直接退出*/
                Toast.makeText(getApplicationContext(), "您的账号登录失败,请您联系管理人员", Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        });
        /*设置条码图片*/
        if (shopvalues.getBarcode().equals("") || shopvalues.getBarcode().equals("0")) {
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
        tp.setBackground(Tools.CreateDrawable(2, "#08c299", "#ffffff", 5));
        tp.setPadding(10, 7, 10, 7);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(15, 0, 0, 0);
        tp.setLayoutParams(params);

        otherMessage.addView(tp);

        /*界面初始化*/
        TextUnt.with(SHOP_TITLE).setText(shopvalues.getTitle());
        if (isLoginin) {
            /*设置批发价格*/
            TextUnt.with(SHOP_TP).setText(shopvalues.getTp());
            /*设置虚线价格*/
            TextUnt.with(SHOP_DLP).setText(shopvalues.getDlp()).setMidcourtLine();
            /*设置零售价*/
            TextUnt.with(SHOP_RETAIL).setText("终端建议售价:" + shopvalues.getRetail() + "元/" +
                    shopvalues.getSplitUnit());
            TextUnt.with(tp).setText(shopvalues.getSu() + shopvalues.getCompany() + "起订")
                    .setTextColor("#08c299").setTextSize(8);
        } else {
            TextUnt.with(btnAccount).setText("未登录账户").setBackground(Tools.CreateDrawable(1, "#666666",
                    "#666666",5)).setTextColor("#ffffff");
            TextUnt.with(SHOP_TP).setText("*.*");
            /*设置批发价格*/
            TextUnt.with(SHOP_TP).setText("*.*");
            /*设置虚线价格*/
            SHOP_DLP.setVisibility(View.GONE);
            TextUnt.with(SHOP_DLP).setVisibility(false);
            /*设置零售价*/
            TextUnt.with(SHOP_RETAIL).setText("终端建议售价:-元/" + shopvalues.getSplitUnit());
            TextUnt.with(tp).setText("-" + shopvalues.getCompany() + "起订")
                    .setTextColor("#08c299").setTextSize(8);

        }
        /*设置生产日期*/
        TextUnt.with(SHOP_PD).setText("生产日期:" + shopvalues.getPd());
        /*设置保质期*/
        TextUnt.with(SHOP_EXP).setText("保质期:" + shopvalues.getExp());
        /*设置品牌*/
        TextUnt.with(SHOP_SPEC).setText("箱规:" + shopvalues.getSpec() + shopvalues.getSplitUnit());
        /*设置等级*/
        TextUnt.with(SHOP_GRADE).setText("等级:" + shopvalues.getGrade());
        /*设置条码*/
        TextUnt.with(SHOP_BARCODE).setText("条码:" + shopvalues.getBarcode());

        /*设置要显示的品牌*/
        TextUnt.with(SHOP_SHOWBRAND).setText(shopvalues.getBrand());

        /*设置边框的圆角*/
        /*设置订购边框的圆角 订单未满*/
        // btnAccount.setBackground(Tools.CreateDrawable(1, getResources().getString(R.color
        //         .ThemeColor), getResources().getString(R.color.ThemeColor), 5));
        btnAccount.setBackground(Tools.CreateDrawable(1, "#08c299", "#08c299", 5));
        btnAccount.setTextColor(Color.parseColor("#ffffff"));
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
                Log.e(Config.DEBUG, "ShowshopOffice.java[+]改变的高度值:" + animation.getAnimatedValue());
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
        /*线程开启计算倒计时广告*/
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = MSG_COUNT_DOWN_ADAVERT;
                handler.sendMessage(msg);

            }
        }, Tools.getRandom(10, 20) * 1000);
        scrollViewBiggerPhoto.setImageHead(photo, metrics);

        /**
         *  开始访问网络图片
         */
        Glide.with(getApplicationContext()).load("http://f.freep.cn/583105/SHOP_DATABASE/" +
                shopvalues.getImg()).into(photo);
        /*加入购物车的布局*/
        /*数量减少*/
        btn_select_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(select_number.getText().toString());
                i -= 1;

                if (i <= 0) {
                    Toast.makeText(getApplicationContext(), "配送的数量不能小于0哦", Toast.LENGTH_SHORT)
                            .show();
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
        });
        /*数量增加*/
        btn_select_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });


    }

    /**
     * 展示购物车的产品
     */

    private void showShoplist() {
        /**
         * 开始网络访问 计算优惠和总额
         */
        if (true) {

            /*满足条件就显示*/
            inShowCarPage.setVisibility(View.VISIBLE);
            if (hideShopBody.getVisibility() == View.VISIBLE) {
                isShowCart = false;/*标志购物车没有显示*/
                /*动画隐藏购物车显示*/
                ValueAnimator valueAnimator = ValueAnimator.ofInt((int) (window_height / 1.5), 0);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int height = (int) animation.getAnimatedValue();
                        ViewGroup.LayoutParams params = showShoplistBody.getLayoutParams();/*获取布局*/
                        params.height = height;
                        showShoplistBody.setLayoutParams(params);
                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.start();
                //控件显示就隐藏
                hideShopBody.setVisibility(View.GONE);

            } else {
                hideShopBody.setVisibility(View.VISIBLE);
                isShowCart = true;/*标志购物车展开*/
                //没有显示就显示
                //创建属性动画 改变高度
                ValueAnimator valueAnimator = ValueAnimator.ofInt(0, (int) (window_height / 1.5));
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int height = (int) animation.getAnimatedValue();
                        ViewGroup.LayoutParams params = showShoplistBody.getLayoutParams();/*获取布局*/
                        params.height = height;
                        showShoplistBody.setLayoutParams(params);
                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.start();
            }
        } else {

        }


    }

    @Override
    public void onBackPressed() {
        if (isShowCart) {
            showShoplist();/*去关闭*/
        } else {
            super.onBackPressed();
        }
    }
}
