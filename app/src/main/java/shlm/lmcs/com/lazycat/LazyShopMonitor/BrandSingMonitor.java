package shlm.lmcs.com.lazycat.LazyShopMonitor;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.LOAD_IMAGEPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.ThreadFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.R;

/**
 * 主界面品牌管理者
 */
@SuppressLint("LongLogTag")
public class BrandSingMonitor extends Monitor {
    private View item;
    private Context mContext;
    private Boolean is;
    public Boolean _Static;//是否可以加载信息的状态
    public Boolean Stop = false;//外部调用 是否可以进行加载操作
    public Boolean Runing = false;//外部判断内部是否在刷新操作
    private List<LOAD_IMAGEPAGE> load_imagepages = new ArrayList<LOAD_IMAGEPAGE>();
    private List<Runnable> RunnableList = null;
    private ProgramInterface programInterface;
    public int SUCESS = 1023;/*每一个处理的管理者 在处理完成之后 就要给定这个值*/


    /**
     * 判断状态  加载完成 或者 没有加载完成
     *
     * @return
     */
    public Boolean GetStatic() {
        return _Static;
    }

    /**
     * 立即停止加载
     */
    public void Stop() {

    }

    /**
     * 重新开始加载
     */
    public void ReStart(Context context) {

        /*判断线程元素是否还存在*/
        if (RunnableList != null) {
            /*启动管理*/
        } else {
            Start(programInterface, context);//重新启动
        }

    }

    @SuppressLint("NewApi")
    public BrandSingMonitor(View _View, Context _Context) {
        this.item = _View;
        this.mContext = _Context;
        //开始最基础的整理界面
        this.item.findViewById(R.id.assembly_fragment_main_singleAlphaTitle).setBackground(Tools
                .CreateDrawable(1, "#000000", "#000000", 50));
        this.item.findViewById(R.id.assembly_fragment_main_singleShopabody).setBackground(Tools
                .CreateDrawable(1, "#ffffff", "#ffffff", 20));
        this.item.findViewById(R.id.assembly_fragment_main_singleShopbbody).setBackground(Tools
                .CreateDrawable(1, "#ffffff", "#ffffff", 20));
        this.item.findViewById(R.id.assembly_fragment_main_singleShopcbody).setBackground(Tools
                .CreateDrawable(1, "#ffffff", "#ffffff", 20));
    }

    /**
     * 开始整理事务
     */
    public void Start(ProgramInterface _programInterface, final Context context) {
        this.programInterface = _programInterface;
        Net.doGet(mContext, "http://120.79.63.36/lazyShop/config/main_brand_promotion.xml", new
                Net.onVisitInterServiceListener() {
            @Override
            public void onSucess(String tOrgin) {
                //让子函数去处理XML返回的信息
                programInterface.onSucess("调用成功", 0);
                onXml(tOrgin, context);
            }

            @Override
            public void onNotConnect() {
                programInterface.onFaile("", 0);
            }

            @Override
            public void onFail(String tOrgin) {
                programInterface.onFaile("", 0);

            }
        });

    }

    void onXml(String origin, final Context context) {
        //获取XML解析
        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(origin);
        xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {
            @Override
            public void onFaile() {
            }

            @Override
            public void onStartDocument() {
                //开始解析文档

            }

            @Override
            public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {
                //开始解析数据
                try {
                    if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion.key_back)) {
                        /*处理背景*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setBack(pullParser.nextText());
                        LOAD_IMAGEPAGE load_imagepage = new LOAD_IMAGEPAGE();
                        load_imagepage.setSaveName("key_back.png");
                        load_imagepage.setImg_url(XmlTagValuesFactory.XMLtagMainBrandPromotion
                                .getBack());
                        if (load_imagepage.getImg_url().indexOf("png") != -1) {
                            /*存在*/
                            load_imagepage.setPostPixt("png");
                        } else {
                            load_imagepage.setPostPixt("jpg");
                        }
                        load_imagepage.setImg((ImageView) item.findViewById(R.id
                                .assembly_fragment_main_singleBack));
                        load_imagepages.add(load_imagepage);
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_big_img)) {
                        /*处理大图片*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setBig_img(pullParser
                                .nextText());
                        /**
                         * 重新构造一个配置文件
                         */
                        LOAD_IMAGEPAGE load_imagepage = new LOAD_IMAGEPAGE();
                        load_imagepage.setSaveName("key_big_img.png");
                        load_imagepage.setImg_url(XmlTagValuesFactory.XMLtagMainBrandPromotion
                                .getBig_img());
                        ImageView img = item.findViewById(R.id.assembly_fragment_main_singleBigImg);
                        load_imagepage.setImg(img);
                        load_imagepages.add(load_imagepage);//添加一个元素
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_shop_a_img)) {
                        /*处理第一个商品图片*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setShop_a_img(pullParser
                                .nextText());
                        /**
                         * 重新构造一个配置文件
                         */
                        LOAD_IMAGEPAGE load_imagepage = new LOAD_IMAGEPAGE();
                        /*设置保存到本地的名称*/
                        load_imagepage.setSaveName(XmlTagValuesFactory.XMLtagMainBrandPromotion
                                .getShop_a_img());
                        /*设置要访问互联网的名称*/
                        load_imagepage.setImg_url(XmlTagValuesFactory.XMLtagMainBrandPromotion
                                .getShop_a_img());
                        /*设置本地存储目录*/
                        load_imagepage.setDefaultFile(mContext.getFilesDir());
                        /*设置缓存的Tag*/
                        load_imagepage.setLruchTag(Tools.getRandString());
                        ImageView img = item.findViewById(R.id
                                .assembly_fragment_main_singleShopaImage);
                        load_imagepage.setImg(img);
                        load_imagepages.add(load_imagepage);//添加一个元素

                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_shop_b_img)) {
                        /*处理第二个商品图片*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setShop_b_img(pullParser
                                .nextText());
                        /**
                         * 重新构造一个配置文件
                         */
                        LOAD_IMAGEPAGE load_imagepage = new LOAD_IMAGEPAGE();
                        /*设置保存到手机上的名字*/
                        load_imagepage.setSaveName(XmlTagValuesFactory.XMLtagMainBrandPromotion
                                .getShop_b_img());
                        /*设置要访问的文件名称*/
                        load_imagepage.setImg_url(XmlTagValuesFactory.XMLtagMainBrandPromotion
                                .getShop_b_img());
                        ImageView img = item.findViewById(R.id
                                .assembly_fragment_main_singleShopbImage);
                        /*设置一个TAG用来缓存图片*/
                        load_imagepage.setLruchTag(Tools.getRandString());
                        load_imagepage.setImg(img);
                        load_imagepages.add(load_imagepage);//添加一个元素
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_shop_c_img)) {
                        /*处理第三个商品图片*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setShop_c_img(pullParser
                                .nextText());
                        /**
                         * 重新构造一个配置文件
                         */
                        LOAD_IMAGEPAGE load_imagepage = new LOAD_IMAGEPAGE();
                        /*设置本地存储位置*/
                        load_imagepage.setDefaultFile(mContext.getFilesDir());
                        /*设置保存的图片名称*/
                        load_imagepage.setSaveName(XmlTagValuesFactory.XMLtagMainBrandPromotion
                                .getShop_c_img());
                        /*设置图片的地址*/
                        load_imagepage.setImg_url(XmlTagValuesFactory.XMLtagMainBrandPromotion
                                .getShop_c_img());
                        /*设置对应的图片的View*/
                        ImageView img = item.findViewById(R.id
                                .assembly_fragment_main_singleShopcImage);
                        /*设置缓存的Tag表示*/
                        load_imagepage.setLruchTag(Tools.getRandString());
                        load_imagepage.setImg(img);
                        /*数组添加一个数据*/
                        load_imagepages.add(load_imagepage);//添加一个元素
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_title_a)) {
                        /*文字信息 不做处理 直接交个XmlTagValuesFactory.XMLtagMainBrandPromotion处理*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setTitle_a(pullParser
                                .nextText());
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_title_b)) {
                        /*文字信息 不做处理 直接交个XmlTagValuesFactory.XMLtagMainBrandPromotion处理*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setTitle_b(pullParser
                                .nextText());
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_shop_a_price)) {
                        /*文字信息 不做处理 直接交个XmlTagValuesFactory.XMLtagMainBrandPromotion处理*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setShop_a_price(pullParser
                                .nextText());
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_shop_b_price)) {
                        /*文字信息 不做处理 直接交个XmlTagValuesFactory.XMLtagMainBrandPromotion处理*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setShop_b_price(pullParser
                                .nextText());
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_shop_c_price)) {
                        /*文字信息 不做处理 直接交个XmlTagValuesFactory.XMLtagMainBrandPromotion处理*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setShop_c_price(pullParser
                                .nextText());
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_shop_a_title)) {
                        /*文字信息 不做处理 直接交个XmlTagValuesFactory.XMLtagMainBrandPromotion处理*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setShop_a_title(pullParser
                                .nextText());
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_shop_b_title)) {
                        /*文字信息 不做处理 直接交个XmlTagValuesFactory.XMLtagMainBrandPromotion处理*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setShop_b_title(pullParser
                                .nextText());
                    } else if (tag.equals(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .key_shop_c_title)) {
                        /*文字信息 不做处理 直接交个XmlTagValuesFactory.XMLtagMainBrandPromotion处理*/
                        XmlTagValuesFactory.XMLtagMainBrandPromotion.setShop_c_title(pullParser
                                .nextText());
                    }
                } catch (Exception e) {
                    Log.e(Config.DEBUG, "BrandSingMonitor.java[+]" + e.getMessage());
                }

            }

            @Override
            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {

            }

            @Override
            public void onEndDocument() {
                //开始处理
                Log.e(Config.DEBUG, "处理到的元素的个数为:" + load_imagepages.size());
                if (Stop == true) {
                    //不准刷新
                } else {
                    Runing = true;//代表已经在处理了
                    /**
                     * 处理文字信息
                     */
                    TextView title_a = item.findViewById(R.id.assembly_fragment_main_singleTitlea);
                    TextView title_b = item.findViewById(R.id.assembly_fragment_main_singleTitleb);
                    TextView shop_a_title = item.findViewById(R.id
                            .assembly_fragment_main_singleShopaTitle);
                    TextView shop_b_title = item.findViewById(R.id
                            .assembly_fragment_main_singleShopbTitle);
                    TextView shop_c_title = item.findViewById(R.id
                            .assembly_fragment_main_singleShopcTitle);
                    title_b.setText(XmlTagValuesFactory.XMLtagMainBrandPromotion.getTitle_b());
                    title_a.setText(XmlTagValuesFactory.XMLtagMainBrandPromotion.getTitle_a());
                    /**
                     * 设置商品的标题
                     */
                    shop_a_title.setText(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .getShop_a_title());
                    shop_b_title.setText(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .getShop_b_title());
                    shop_c_title.setText(XmlTagValuesFactory.XMLtagMainBrandPromotion
                            .getShop_c_title());

                    ThreadFactory threadFactory = new ThreadFactory();
                    RunnableList = new ArrayList<>();
                    for (int i = 0; i < load_imagepages.size(); i++) {
                        final int y = i;
                    }
                    // threadFactory.doThread(RunnableList);
                }

            }
        });

    }

    /**
     * 模拟处理xml数据信息
     */

    class Page {
    }

}
