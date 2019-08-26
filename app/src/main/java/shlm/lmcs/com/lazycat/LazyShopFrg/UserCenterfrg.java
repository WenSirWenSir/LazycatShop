package shlm.lmcs.com.lazycat.LazyShopFrg;import android.annotation.SuppressLint;import android.graphics.Color;import android.os.Bundle;import android.util.Log;import android.view.Gravity;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.RelativeLayout;import android.widget.TextView;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.R;public class UserCenterfrg extends LazyCatFragment {    private String MSG = "UserCenterfrg.java[+]";    private LinearLayout CardBody;    private XmlTagValuesFactory.XMLValuesUserpageCard userpageCard = null;    private XmlTagValuesFactory.XMLValuesUserpageBtnItem item = null;/*控件BTN*/    private ArrayList<XmlTagValuesFactory.XMLValuesUserpageCard> cardList = new            ArrayList<XmlTagValuesFactory.XMLValuesUserpageCard>();    @SuppressLint({"NewApi", "ResourceType"})    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle            savedInstanceState) {        View item = inflater.inflate(R.layout.usercenterfrg, null);        /*获取主题的颜色  */        setStatusBar(getResources().getString(R.color.ThemeColor));        /*我的功能body*///        LinearLayout mefuctionBody = item.findViewById(R.id.usercenterfrg_mefuctionBody);        item.findViewById(R.id.usercenterfrg_userValuesBody).setBackground(Tools.CreateDrawable                (1, "#ffffff", "#ffffff", 15));        CardBody = item.findViewById(R.id.usercenterfrg_cardBody);/*添加card的body*/        /**         /*初始化Card*/        /* *//*4个导航*//*        for (int i = 0; i < 4; i++) {            RelativeLayout rl = new RelativeLayout(getContext());*//*创建布局*//*            rl.setBackground(Tools.CreateDrawable(1, "#e9e9e9", "#e9e9e9", 5));            *//*设置权重*//*            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout                    .LayoutParams.WRAP_CONTENT, 1.0f);            params.setMargins(10, 10, 10, 10);            *//*设置图片*//*            RelativeLayout.LayoutParams ivParams = new RelativeLayout.LayoutParams(100, 100);            ImageView iv = new ImageView(getContext());            ivParams.addRule(RelativeLayout.CENTER_HORIZONTAL);            ivParams.setMargins(0, 50, 0, 0);            iv.setLayoutParams(ivParams);            iv.setImageResource(R.drawable.ico_bell);            rl.addView(iv);            *//*加入图片布局*//*            rl.setLayoutParams(params);            *//*设置文字*//*            RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(RelativeLayout                    .LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);            TextView title = new TextView(getContext());            title.setText("这是第" + i);            title.setTextColor(Color.parseColor("#ffffff"));            title.setTextSize(12);            tvParams.setMargins(0, 160, 0, 0);            title.setLayoutParams(tvParams);            title.setGravity(Gravity.CENTER);            rl.addView(title);            mefuctionBody.addView(rl);        }*//*        item.findViewById(R.id.usercenterfrg_mefuction).setBackground(Tools.CreateDrawable(1,                "#e9e9e9", "#ffffff", 5));*/        ;        setTransparentBar();        init(item);        return item;    }    @SuppressLint("NewApi")    private void init(View item) {        /*访问服务器 获取初始化配置文件*/        Net.doGet(getContext(), Config.HTTP_ADDR.SERVICE + "/lazyShop/configXml/userPageConfig" +                ".php", new Net.onVisitInterServiceListener() {            @Override            public WaitDialog.RefreshDialog onStartLoad() {                /*初始化一个DIALOG*/                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog(getActivity());                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                wait_itme_dialogpage.setImg(R.id.item_wait_img);                wait_itme_dialogpage.setView(R.layout.item_wait);                wait_itme_dialogpage.setTitle(R.id.item_wait_title);                refreshDialog.Init(wait_itme_dialogpage);                refreshDialog.showRefreshDialog("加载中...", false);                return refreshDialog;            }            @Override            public void onSucess(String tOrgin,WaitDialog.RefreshDialog _RefreshDialog) {                Log.i(MSG, "返回数据:" + tOrgin);                handlXml(tOrgin);            }            @Override            public void onNotConnect() {            }            @Override            public void onFail(String tOrgin) {            }        });    }    /*处理xml数据信息*/    private void handlXml(String tOrgin) {        XmlanalysisFactory xml = new XmlanalysisFactory(tOrgin);        xml.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {            @Override            public void onFaile() {            }            @Override            public void onStartDocument(String tag) {            }            @Override            public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                try {                    if (tag.equals(XmlTagValuesFactory.XMLKeyUserPageXml.key_card)) {                        /*一个卡片*/                        if (userpageCard != null) {                            /*已经创建了*/                        } else {                            userpageCard = new XmlTagValuesFactory.XMLValuesUserpageCard();                        }                    }                    /*设置card的标题*/                    if (tag.equals(XmlTagValuesFactory.XMLKeyUserPageXml.key_card_title)) {                        userpageCard.setCard_title(pullParser.nextText());                    }                    if (tag.equals(XmlTagValuesFactory.XMLKeyUserPageXml.key_card_item)) {                        if (item == null) {                            item = new XmlTagValuesFactory.XMLValuesUserpageBtnItem();                        }                    }                    /*设置ITEM的标题*/                    if (tag.equals(XmlTagValuesFactory.XMLKeyUserPageXml.key_card_item_title)) {                        if (item != null) {                            item.setTitle(pullParser.nextText().trim());                        }                    }                    /*设置ITEM的字体的颜色*/                    if (tag.equals(XmlTagValuesFactory.XMLKeyUserPageXml                            .key_card_item_titlecolor)) {                        if (item != null) {                            item.setTitle_color(pullParser.nextText().trim());                        }                    }                    /*设置标题的图片的地址*/                    if (tag.equals(XmlTagValuesFactory.XMLKeyUserPageXml.key_card_item_imgurl)) {                        if (item != null) {                            item.setImg_url(pullParser.nextText().trim());                        }                    }                } catch (Exception e) {                }            }            @Override            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                if (tag.equals(XmlTagValuesFactory.XMLKeyUserPageXml.key_card)) {                    if (userpageCard != null) {                        cardList.add(userpageCard);                        userpageCard = null;                    }                }                if (tag.equals(XmlTagValuesFactory.XMLKeyUserPageXml.key_card_item)) {                    if (item != null) {                        userpageCard.addList(item);                        item = null;                    }                }            }            @SuppressLint("NewApi")            @Override            public void onEndDocument() {                for (int i = 0; i < cardList.size(); i++) {                    /*初始化一个卡片*/                    LinearLayout card = (LinearLayout) LayoutInflater.from(getContext()).inflate                            (R.layout.assembly_item_userpagecard, null);                    if (CardBody != null) {                        /*加入布局*/                        card.findViewById(R.id.assembly_item_userpagecardBody).setBackground                                (Tools.CreateDrawable(1, "#ffffff", "#ffffff", 15));                        CardBody.addView(card);                    }                    /*找到要添加的控件地址*/                    LinearLayout body = card.findViewById(R.id.assembly_item_userpagecardItemBody);                    /*在布局中添加控件*/                    TextView title = card.findViewById(R.id.assembly_item_userpagecardTitle);                    title.setText(cardList.get(i).getCard_title().trim());                    /*首先判断是否大于4*/                    if (cardList.get(i).getList().size() > 4) {                    } else {                        for (int y = 0; y <= 4; y++) {                            /*必须要设置4个VIEW*/                            if (cardList.get(i).getList().get(y).getTitle() == null) {                                /*已经没有数据了*/                                Log.i(MSG, "没有数据了");                                RelativeLayout rl = new RelativeLayout(getContext());//*创建布局*//*                                rl.setBackground(Tools.CreateDrawable(1, "#e9e9e9", "#e9e9e9", 5));                                //*设置权重*//*                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams                                        (0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);                                params.setMargins(10, 10, 10, 10);                                rl.setLayoutParams(params);                                body.addView(rl);                            } else {                                RelativeLayout rl = new RelativeLayout(getContext());//*创建布局*//*                                rl.setBackground(Tools.CreateDrawable(1, "#e9e9e9", "#e9e9e9", 5));                                //*设置权重*//*                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams                                        (0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);                                params.setMargins(10, 10, 10, 10);                                //*设置图片*//*                                RelativeLayout.LayoutParams ivParams = new RelativeLayout                                        .LayoutParams(100, 100);                                ImageView iv = new ImageView(getContext());                                ivParams.addRule(RelativeLayout.CENTER_HORIZONTAL);                                ivParams.setMargins(0, 50, 0, 0);                                iv.setLayoutParams(ivParams);                                iv.setImageResource(R.drawable.ico_bell);                                rl.addView(iv);                                //*加入图片布局*//*                                rl.setLayoutParams(params);                                //*设置文字*//*                                RelativeLayout.LayoutParams tvParams = new RelativeLayout                                        .LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,                                        RelativeLayout.LayoutParams.WRAP_CONTENT);                                TextView tvTitle = new TextView(getContext());                                tvTitle.setText("这是第" + i);                                tvTitle.setTextColor(Color.parseColor("#ffffff"));                                tvTitle.setTextSize(12);                                tvParams.setMargins(0, 160, 0, 0);                                tvTitle.setLayoutParams(tvParams);                                tvTitle.setGravity(Gravity.CENTER);                                rl.addView(tvTitle);                                body.addView(rl);                            }                        }                    }                }            }        });    }}