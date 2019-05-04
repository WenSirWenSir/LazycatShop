package shlm.lmcs.com.lazycat.LazyShopMonitor;


import android.content.Context;
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
    private String MSG = "NewShopinMonitor";
    private LocalNewshopIn localNewshopIn = null;
    private Context mContext;
    private String url;
    private XmlTagValuesFactory.XMLtagMainNewshopIn newShopin = new XmlTagValuesFactory
            .XMLtagMainNewshopIn();
    private LocalNewshopIn shopinValues = null;//保存一个控件的值
    private List<LocalNewshopIn> shopinList = new ArrayList<LocalNewshopIn>();
    private LinearLayout _newShopinView;

    /**
     * 构造方法
     */
    public NewShopinMonitor(View newShopinView, Context context) {
        this.mContext = context;
        this._newShopinView = (LinearLayout) newShopinView;
        this.url = url;

    }


    /**
     * 启动管理
     */
    public void Start() {
        if (StopLoad) {
            /*不准加载*/
        } else {
            /*准许加载*/
            Net.doGet(mContext, this.url, new Net.onVisitInterServiceListener() {
                @Override
                public void onSucess(String tOrgin) {
                    Log.i(MSG, "NewShopinMonitor.java[+]网络返回的Xml数据:" + tOrgin);
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

    private void onXml(String tOrgin) {
        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(tOrgin);
        xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {
            @Override
            public void onFaile() {

            }

            @Override
            public void onStartDocument() {

            }

            @Override
            public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {
                try {
                    if (tag.equals(XmlTagValuesFactory.XMLtagMainNewshopIn.key_newshopin_title)) {
                        newShopin.setNewshopin_title(pullParser.nextText());/*新品上架的标题*/
                    }
                    if (tag.equals(XmlTagValuesFactory.XMLtagMainNewshopIn
                            .key_newshopin_photoaddr)) {
                        newShopin.setNewshopin_photoaddr(pullParser.nextText());/*新品上架的图片地址*/

                    }
                    if (tag.equals(XmlTagValuesFactory.XMLtagMainNewshopIn.key_newshopin_text)) {
                        newShopin.setNewshopin_text(pullParser.nextText());/*新品上架的标语*/
                    }

                    if (tag.equals(XmlTagValuesFactory.XMLtagMainNewshopIn.key_values)) {
                        /*检测到开始的标签  就重新构造表格*/
                        shopinValues = new LocalNewshopIn();
                    }

                    if (tag.equals(XmlTagValuesFactory.XMLtagMainNewshopIn.key_values_title)) {
                        shopinValues.setTitle(pullParser.nextText());/*标题*/
                    }

                    if (tag.equals(XmlTagValuesFactory.XMLtagMainNewshopIn.key_values_text)) {
                        shopinValues.setText(pullParser.nextText());/*标语*/
                    }

                    if (tag.equals(XmlTagValuesFactory.XMLtagMainNewshopIn.key_values_img)) {
                        shopinValues.setImg(pullParser.nextText());/*图片*/
                    }
                } catch (Exception e) {
                    Log.e(MSG, "NewShopinMonitor.java[+]错误信息:" + e.getMessage());
                }

            }

            @Override
            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {
                if (tag.equals(XmlTagValuesFactory.XMLtagMainNewshopIn.key_values)) {
                    shopinList.add(shopinValues);
                    shopinValues = null;
                }

            }

            @Override
            public void onEndDocument() {
                /**
                 * 初始化View不加载图片
                 */
                for (int i = 0; i < shopinList.size(); i++) {
                    Log.i(MSG, "NewShopinMonitor.java[" + i + "]" + shopinList.get(i).getTitle());
                }
                /*标题*/
                TextView first_title = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_firstTitle);
                first_title.setText(shopinList.get(0).getTitle());
                TextView second_title = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_secondTitle);
                second_title.setText(shopinList.get(1).getTitle());

                TextView three_title = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_threeTitle);
                three_title.setText(shopinList.get(2).getTitle());
                /*标语*/
                TextView first_text = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_firstText);
                first_text.setText(shopinList.get(0).getText());
                TextView second_text = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_secondText);
                second_text.setText(shopinList.get(1).getText());
                TextView three_text = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_threeText);
                three_text.setText(shopinList.get(2).getText());
                /*状态*/
                TextView first_status = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_firstStatus);
                first_status.setText(shopinList.get(0).getStatus());
                TextView second_status = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_secondStatus);
                second_status.setText(shopinList.get(1).getStatus());

                TextView three_status = _newShopinView.findViewById(R.id
                        .assembly_fragment_main_newshopin_threeStatus);
                three_status.setText(shopinList.get(2).getStatus());

            }
        });
    }


    /**
     * 停止管理
     */
    public void Stop() {

    }
}
