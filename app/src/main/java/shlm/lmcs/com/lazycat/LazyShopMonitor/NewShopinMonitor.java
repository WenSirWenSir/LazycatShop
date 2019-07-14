package shlm.lmcs.com.lazycat.LazyShopMonitor;


import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalNewshopIn;
import shlm.lmcs.com.lazycat.R;

/**
 * 新品上架的数据管理者
 */
public class NewShopinMonitor extends Monitor {
    private boolean StopLoad = false;
    private String MSG = "NewShopinMonitor.java[+]";
    private LocalNewshopIn localNewshopIn = null;
    private Context mContext;
    private XmlTagValuesFactory.XMLValuesMainNewshopIn xmlValuesMainNewshopIn = new
            XmlTagValuesFactory.XMLValuesMainNewshopIn();
    private ArrayList<XmlTagValuesFactory.XMLValuesMainNewshopIn> item_values = new
            ArrayList<XmlTagValuesFactory.XMLValuesMainNewshopIn>();
    private XmlTagValuesFactory.XMLKeyMainNewshopIn newShopin = new XmlTagValuesFactory
            .XMLKeyMainNewshopIn();
    private XmlTagValuesFactory.XMLValuesMainNewshopIn shopinValues = null;//保存一个控件的值
    private List<LocalNewshopIn> shopinList = new ArrayList<LocalNewshopIn>();
    private LinearLayout _newShopinView;
    private String _url = "";/*请求构造地址*/
    private TextView item_title;
    private TextView item_text;

    /**
     * 构造方法
     */
    public NewShopinMonitor(View newShopinView, Context context, String url) {
        this.mContext = context;
        this._newShopinView = (LinearLayout) newShopinView;
        this._url = url;
    }


    /**
     * 启动管理
     */
    public void Start() {
        if (StopLoad) {
            /*不准加载*/
        } else {
            if (!TextUtils.isEmpty(this._url)) {
                /*准许加载*/
                Net.doGet(mContext, this._url, new Net.onVisitInterServiceListener() {
                    @Override
                    public void onSucess(String tOrgin) {
                        onXml(tOrgin);
                    }

                    @Override
                    public void onNotConnect() {

                    }

                    @Override
                    public void onFail(String tOrgin) {

                    }
                });

            }

        }
    }

    private void onXml(String tOrgin) {
        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(tOrgin);
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
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key_newshopin_title)) {
                        /*控件的标题*/
                        xmlValuesMainNewshopIn.setTitle(pullParser.nextText());
                    }
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn
                            .key_newshopin_title_color)) {
                        /*控件标题的颜色*/
                        xmlValuesMainNewshopIn.setTitle_color(pullParser.nextText());
                    }
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn
                            .key_newshopin_photoaddr)) {
                        /*控件显示图片的地址*/
                        xmlValuesMainNewshopIn.setImg_url(pullParser.nextText());
                    }

                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key_newshopin_text)) {
                        /*控件显示的标语*/
                        xmlValuesMainNewshopIn.setText(pullParser.nextText());
                    }

                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn
                            .key_newshopin_text_color)) {
                        /*控件显示的标题颜色*/
                        xmlValuesMainNewshopIn.setText_color(pullParser.nextText());

                    }

                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key)) {
                        /*显示商品的初始类*/
                        if (shopinValues != null) {

                        } else {
                            shopinValues = new XmlTagValuesFactory.XMLValuesMainNewshopIn();

                        }
                    }
                    /**
                     * 存储商品的参数
                     */


                    /*标题*/
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key_values_title)) {
                        shopinValues.setTitle(pullParser.nextText());
                    }
                    /*标题颜色*/
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key_title_color)) {
                        shopinValues.setTitle_color(pullParser.nextText());
                    }
                    /*标语*/
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key_values_text)) {
                        shopinValues.setText(pullParser.nextText());
                    }
                    /*标语的颜色*/
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key_text_color)) {
                        shopinValues.setText_color(pullParser.nextText());
                    }
                    /*状态的标题*/
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key_values_status)) {
                        shopinValues.setStatus(pullParser.nextText());
                    }
                    /*状态的背景*/
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key_status_back_color)) {
                        shopinValues.setStatus_back_color(pullParser.nextText());
                    }
                    /*状态的文字颜色*/
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key_status_color)) {
                        shopinValues.setStatus_color(pullParser.nextText());
                    }
                    /*背景的图片*/
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key_values_img)) {
                        shopinValues.setImg_url(pullParser.nextText());
                    }
                } catch (Exception e) {
                    Log.e(MSG, "NewShopinMonitor.java[+]错误信息:" + e.getMessage());
                }

            }

            @Override
            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {
                if (tag.equals(XmlTagValuesFactory.XMLKeyMainNewshopIn.key)) {
                    item_values.add(shopinValues);
                    shopinValues = null;
                }

            }

            @Override
            public void onEndDocument() {
                Log.i(MSG, "图片地址:" + xmlValuesMainNewshopIn.getImg_url());

                /*开始整理界面*/

                item_title = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_title);/*首页标题*/
                item_text = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_text);/*首页的标语*/
                item_title.setText(xmlValuesMainNewshopIn.getTitle());
                item_title.setTextColor(Color.parseColor(xmlValuesMainNewshopIn.getTitle_color())
                );/*设置颜色*/
                item_text.setText(xmlValuesMainNewshopIn.getText());
                item_text.setTextColor(Color.parseColor(xmlValuesMainNewshopIn.getText_color()));
                /*设置颜色*/


                /**
                 * 商品整理
                 */
                TextView first_title = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_firstTitle);/*第一个商品的标题*/
                first_title.setText(item_values.get(0).getTitle());
                first_title.setTextColor(Color.parseColor(item_values.get(0).getTitle_color()));
                TextView first_text = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_firstText);/*第二个商品的标语*/
                first_text.setText(item_values.get(0).getText());
                first_text.setTextColor(Color.parseColor(item_values.get(0).getText_color()));
                TextView first_status = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_firstStatus);/*商品的状态*/
                first_status.setText(item_values.get(0).getStatus());
                first_status.setTextColor(Color.parseColor(item_values.get(0).getStatus_color()));
                first_status.setBackgroundColor(Color.parseColor(item_values.get(0)
                        .getStatus_back_color()));
            }
        });
    }


    /**
     * 停止管理
     */
    public void Stop() {

    }
}
