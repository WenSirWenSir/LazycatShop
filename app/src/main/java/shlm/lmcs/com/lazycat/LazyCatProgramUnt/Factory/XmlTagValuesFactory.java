package shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory;


import android.graphics.Color;

import java.util.ArrayList;

/**
 * XML对应的数据解析
 */
public class XmlTagValuesFactory {
    static XmlServiceInitFillet xmlServiceInitFillet = new XmlServiceInitFillet();/*圆角矩形和*/
    static XmlServiceInitBtn xmlServiceInitBtn = new XmlServiceInitBtn();/*首页的按钮参数信息*/

    /**
     * 获取拱形和圆角矩形的参数
     *
     * @return
     */
    public static XmlServiceInitFillet getXmlServiceInitPageInstance() {
        return xmlServiceInitFillet;
    }

    public static XmlServiceInitBtn getXmlServiceInitBtnInstance() {
        return xmlServiceInitBtn;
    }


    /**
     * 存在地区的服务器的数据圆角矩形和ArcView的ACTION
     */
    public static class ActionServiceInitFillet {
        public static String ACTION_XML_START = "init_fillet";
        public static String ACTION_STATIC = "";
    }

    /**
     * 存在地区的服务的首页导航按钮的参数信息ACTION
     */
    public static class ActionServiceNavBtn {
        public static String ACTION_XML_START = "init_btns";
        public static String ACTION_XML_STATUS = "init_btns_status";/*View的状态  0 为显示  1为不显示*/
        public static String ACTION_FIRST_BTN = "nav_first_btn";
        public static String ACTION_FIRST_BTN_TITLE = "first_btn_title";
        public static String ACTION_FIRST_BTN_STATUS = "first_btn_status";
        public static String ACTION_FIRST_BTN_IMG = "first_btn_img";
        public static String ACTION_FIRST_BTN_URL = "first_btn_url";

        /**
         * 第二个按钮的ACTION
         */
        public static String ACTION_SECOND_BTN = "nav_second_btn";
        public static String ACTION_SECOND_BTN_TITLE = "second_btn_title";
        public static String ACTION_SECOND_BTN_STATUS = "second_btn_status";
        public static String ACTION_SECOND_BTN_IMG = "second_btn_img";
        public static String ACTION_SECOND_BTN_URL = "second_btn_url";
        /**
         * 第三个按钮的ACTION
         */
        public static String ACTION_THREE_BTN = "nav_three_btn";
        public static String ACTION_THREE_BTN_TITLE = "three_btn_title";
        public static String ACTION_THREE_BTN_STATUS = "three_btn_status";
        public static String ACTION_THREE_BTN_IMG = "three_btn_img";
        public static String ACTION_THREE_BTN_URL = "three_btn_url";
        /**
         * 第四个按钮的ACTION
         */
        public static String ACTION_FOUR_BTN = "nav_four_btn";
        public static String ACTION_FOUR_BTN_TITLE = "four_btn_title";
        public static String ACTION_FOUR_BTN_STATUS = "four_btn_status";
        public static String ACTION_FOUR_BTN_IMG = "four_btn_img";
        public static String ACTION_FOUR_BTN_URL = "four_btn_url";
    }


    /**
     * 存在地区的服务的首页商家促销的参数信息ACTION
     */
    public static class ActionServiceBusinessPromotion {
        public static String ACTION_XML_START = "init_business_promotion";
        /**
         * 第一个商家促销的界面
         */
        public static String ACTION_FIRST_BUSINESSPROMOTION_TITLE =
                "first_business_promotion_title";
        public static String ACTION_FIRST_BUSINESSPROMOTION_IMG = "first_business_promotion_img";
        public static String ACTION_FIRST_BUSINESSPROMOTION_URL = "first_business_promotion_url";
        public static String ACTION_FIRST_BUSINESSPROMOTION_STATUS =
                "first_business_promotion_status";

        /**
         * 第二个商家促销的界面
         */

        public static String ACTION_SECOND_BUSINESSPROMOTION_TITLE =
                "second_business_promotion_title";
        public static String ACTION_SECOND_BUSINESSPROMOTION_IMG = "second_business_promotion_img";
        public static String ACTION_SECOND_BUSINESSPROMOTION_URL = "second_business_promotion_url";
        public static String ACTION_SECOND_BUSINESSPROMOTION_STATUS =
                "second_business_prmotion_status";

        /**
         * 第三个商家促销的界面
         */

        public static String ACTION_THREE_BUSINESSPROMOTION_TITLE =
                "three_business_promotion_title";
        public static String ACTION_THREE_BUSINESSPROMOTION_IMG = "three_business_promotion_img";
        public static String ACTION_THREE_BUSINESSPROMOTION_URL = "three_business_promotion_url";
        public static String ACTION_THREE_BUSINESSPROMOTION_STATUS =
                "three_business_promotion_status";

        /**
         * 第四个商家促销的界面
         */
        public static String ACTION_FOUR_BUSINESSPROMOTION_TITLE = "four_business_promotion_title";
        public static String ACTION_FOUR_BUSINESSPROMOTION_IMG = "four_business_promotion_img";
        public static String ACTION_FOUR_BUSINESSPROMOTION_URL = "four_business_promotion_url";
        public static String ACTION_FOUR_BUSINESSPROMOTION_STATUS =
                "four_business_promotion_status";
    }


    /**
     * 数据分割----------------------------------------------------------------------
     */


    /**
     * 存在服务器地址的  商家促销的初始化信息表格
     */
    public static class XmlServiceInitBusinessPromotion {
        /**
         * 第一个商家的View参数
         */
        String first_business_promotion_title;
        String first_business_promotion_img;
        String first_business_promotion_url;
        String first_business_promotion_status;
        /**
         * 第二个商家的View参数
         */
        String second_business_promotion_title;
        String second_business_promotion_img;
        String second_business_promotion_url;
        String second_business_promotion_status;
        /**
         * 第三个商家的View参数
         */
        String three_business_promotion_title;
        String three_business_promotion_img;
        String three_business_promotion_url;
        String three_business_promotion_status;

        /**
         * 第四个商家的View参数
         */
        String four_business_promotion_title;
        String four_business_promotion_img;
        String four_business_promotion_url;
        String four_business_promotion_status;

        public String getFirst_business_promotion_title() {
            return first_business_promotion_title;
        }

        public void setFirst_business_promotion_title(String first_business_promotion_title) {
            this.first_business_promotion_title = first_business_promotion_title;
        }

        public String getFirst_business_promotion_img() {
            return first_business_promotion_img;
        }

        public void setFirst_business_promotion_img(String first_business_promotion_img) {
            this.first_business_promotion_img = first_business_promotion_img;
        }

        public String getFirst_business_promotion_url() {
            return first_business_promotion_url;
        }

        public void setFirst_business_promotion_url(String first_business_promotion_url) {
            this.first_business_promotion_url = first_business_promotion_url;
        }

        public String getFirst_business_promotion_status() {
            return first_business_promotion_status;
        }

        public void setFirst_business_promotion_status(String first_business_promotion_status) {
            this.first_business_promotion_status = first_business_promotion_status;
        }

        public String getSecond_business_promotion_title() {
            return second_business_promotion_title;
        }

        public void setSecond_business_promotion_title(String second_business_promotion_title) {
            this.second_business_promotion_title = second_business_promotion_title;
        }

        public String getSecond_business_promotion_img() {
            return second_business_promotion_img;
        }

        public void setSecond_business_promotion_img(String second_business_promotion_img) {
            this.second_business_promotion_img = second_business_promotion_img;
        }

        public String getSecond_business_promotion_url() {
            return second_business_promotion_url;
        }

        public void setSecond_business_promotion_url(String second_business_promotion_url) {
            this.second_business_promotion_url = second_business_promotion_url;
        }

        public String getSecond_business_promotion_status() {
            return second_business_promotion_status;
        }

        public void setSecond_business_promotion_status(String second_business_promotion_status) {
            this.second_business_promotion_status = second_business_promotion_status;
        }

        public String getThree_business_promotion_title() {
            return three_business_promotion_title;
        }

        public void setThree_business_promotion_title(String three_business_promotion_title) {
            this.three_business_promotion_title = three_business_promotion_title;
        }

        public String getThree_business_promotion_img() {
            return three_business_promotion_img;
        }

        public void setThree_business_promotion_img(String three_business_promotion_img) {
            this.three_business_promotion_img = three_business_promotion_img;
        }

        public String getThree_business_promotion_url() {
            return three_business_promotion_url;
        }

        public void setThree_business_promotion_url(String three_business_promotion_url) {
            this.three_business_promotion_url = three_business_promotion_url;
        }

        public String getThree_business_promotion_status() {
            return three_business_promotion_status;
        }

        public void setThree_business_promotion_status(String three_business_promotion_status) {
            this.three_business_promotion_status = three_business_promotion_status;
        }

        public String getFour_business_promotion_title() {
            return four_business_promotion_title;
        }

        public void setFour_business_promotion_title(String four_business_promotion_title) {
            this.four_business_promotion_title = four_business_promotion_title;
        }

        public String getFour_business_promotion_img() {
            return four_business_promotion_img;
        }

        public void setFour_business_promotion_img(String four_business_promotion_img) {
            this.four_business_promotion_img = four_business_promotion_img;
        }

        public String getFour_business_promotion_url() {
            return four_business_promotion_url;
        }

        public void setFour_business_promotion_url(String four_business_promotion_url) {
            this.four_business_promotion_url = four_business_promotion_url;
        }

        public String getFour_business_promotion_status() {
            return four_business_promotion_status;
        }

        public void setFour_business_promotion_status(String four_business_promotion_status) {
            this.four_business_promotion_status = four_business_promotion_status;
        }


    }

    /**
     * 存在服务器地址的  首页按钮的初始化的信息表格
     */
    public static class XmlServiceInitBtn {
        /**
         * 第一个按钮的参数
         */
        String first_btn_title;
        String first_btn_img;
        String first_btn_status;
        String first_btn_url;

        /**
         * 第二个按钮的参数
         */
        String second_btn_title;
        String second_btn_img;
        String second_btn_status;
        String second_btn_url;
        /**
         * 第三个按钮的参数
         */
        String three_btn_title;
        String three_btn_img;
        String three_btn_status;
        String three_btn_url;

        /**
         * 第四个按钮的参数
         */
        String four_btn_title;
        String four_btn_img;
        String four_btn_status;
        String four_btn_url;

        public String getFirst_btn_title() {
            return first_btn_title;
        }

        public void setFirst_btn_title(String fisrt_btn_title) {
            this.first_btn_title = fisrt_btn_title;
        }

        public String getFirst_btn_img() {
            return first_btn_img;
        }

        public void setFirst_btn_img(String first_btn_img) {
            this.first_btn_img = first_btn_img;
        }

        public String getFirst_btn_status() {
            return first_btn_status;
        }

        public void setFirst_btn_status(String first_btn_status) {
            this.first_btn_status = first_btn_status;
        }

        public String getFirst_btn_url() {
            return first_btn_url;
        }

        public void setFirst_btn_url(String first_btn_url) {
            this.first_btn_url = first_btn_url;
        }

        public String getSecond_btn_title() {
            return second_btn_title;
        }

        public void setSecond_btn_title(String second_btn_title) {
            this.second_btn_title = second_btn_title;
        }

        public String getSecond_btn_img() {
            return second_btn_img;
        }

        public void setSecond_btn_img(String second_btn_img) {
            this.second_btn_img = second_btn_img;
        }

        public String getSecond_btn_status() {
            return second_btn_status;
        }

        public void setSecond_btn_status(String second_btn_status) {
            this.second_btn_status = second_btn_status;
        }

        public String getSecond_btn_url() {
            return second_btn_url;
        }

        public void setSecond_btn_url(String second_btn_url) {
            this.second_btn_url = second_btn_url;
        }

        public String getThree_btn_title() {
            return three_btn_title;
        }

        public void setThree_btn_title(String three_btn_title) {
            this.three_btn_title = three_btn_title;
        }

        public String getThree_btn_img() {
            return three_btn_img;
        }

        public void setThree_btn_img(String three_btn_img) {
            this.three_btn_img = three_btn_img;
        }

        public String getThree_btn_status() {
            return three_btn_status;
        }

        public void setThree_btn_status(String three_btn_status) {
            this.three_btn_status = three_btn_status;
        }

        public String getThree_btn_url() {
            return three_btn_url;
        }

        public void setThree_btn_url(String three_btn_url) {
            this.three_btn_url = three_btn_url;
        }

        public String getFour_btn_title() {
            return four_btn_title;
        }

        public void setFour_btn_title(String four_btn_title) {
            this.four_btn_title = four_btn_title;
        }

        public String getFour_btn_img() {
            return four_btn_img;
        }

        public void setFour_btn_img(String four_btn_img) {
            this.four_btn_img = four_btn_img;
        }

        public String getFour_btn_status() {
            return four_btn_status;
        }

        public void setFour_btn_status(String four_btn_status) {
            this.four_btn_status = four_btn_status;
        }

        public String getFour_btn_url() {
            return four_btn_url;
        }

        public void setFour_btn_url(String four_btn_url) {
            this.four_btn_url = four_btn_url;
        }


    }


    /**
     * 存在地区呢服务器的数据初始化
     */
    public static class XmlServiceInitFillet {
        /**
         * 关键的参数
         */
        int arcViewColor;/*拱形的颜色*/
        String fillet_img;/*圆角矩形的图片地址*/
        String fillet_url;/*圆角矩形的关联地址*/


        public String getFillet_url() {
            return fillet_url;
        }

        public void setFillet_url(String fillet_url) {
            this.fillet_url = fillet_url;
        }

        public String getFillet_img() {
            return fillet_img;
        }

        public void setFillet_img(String fillet_img) {
            this.fillet_img = fillet_img;
        }


        public int getArcViewColor() {
            return arcViewColor;
        }

        public void setArcViewColor(String arcViewColor) {
            this.arcViewColor = Color.parseColor(arcViewColor.trim());
        }

    }

    /*用户界面的xmlTag*/
    public static class XMLKeyUserPageXml {
        /*卡片*/
        public final static String key_card = "card";
        /*卡片标题*/
        public final static String key_card_title = "card_title";
        /*卡片的按钮ITEM*/
        public final static String key_card_item = "item";
        /*卡片的按钮的标题*/
        public final static String key_card_item_title = "title";
        /*卡片的按钮的颜色*/
        public final static String key_card_item_titlecolor = "title_color";
        /*卡片的按钮的图片地址*/
        public final static String key_card_item_imgurl = "img_url";
    }

    public static class XMLKeyMainXml {
        public final static String key_nav_arcview = "arc_background";/*拱形的颜色*/
        public final static String key_only_code = "only_code";/*唯一的代码更新*/
        public final static String key_photo_addr = "photo_addr";/*图片地址*/
        public final static String key_adver_head = "adver_head";/*广告图片*/
        public final static String key_adver_head_first = "first";/*第一个广告图片*/
        public final static String key_adver_head_second = "second";/*第二个产品图片*/
        public final static String key_adver_head_three = "three";/*第三个产品图片*/
        public final static String key_nav_ico = "nav_ico";/*导航的xml参数*/
        public final static String key_nav_body = "nav_xml";/*单个导航*/
        public final static String key_nav_ico_ico = "ico";/*导航的参数*/
        public final static String key_nav_ico_title = "title";/*导航参数*/
        public final static String key_nav_ico_color = "color";/*导航参数*/
        public final static String key_nav_ico_auto_link = "auto_link";/*导航参数链接地址*/
        public final static String key_nav_ico_static = "static";/*导航图标的状态*/
        public final static String key_nav_ico_static_titleColor = "static_titleColor";/*状态的颜色*/
        public final static String key_nav_ico_static_backColor = "static_backColor";/*状态的背景*/
        public final static String key_nav_ico_auto_link_titleColor = "auto_link_titleColor";
        /*打开webview的字体颜色*/
        public final static String key_nav_ico_auto_link_titleBackColor =
                "auto_link_titleBackColor";/*打开webview的标题背景颜色*/
        public final static String key_nav_ico_auto_link_staticColor = "auto_link_staticColor";
        /*打开的webview的状态栏颜色*/
        /*更换状态颜色*/
        public final static String key_main_static_color = "static_color";
        /*更换搜索框的背景颜色*/
        public final static String key_main_searchBody_BackColor = "searchbody_backcolor";

        /*搜索框文字颜色*/
        public final static String key_main_searchBody_inputColor = "searchbody_inputcolor";
        /*搜索框文字背景颜色*/
        public final static String key_main_searchBody_inputBackColor = "searchbody_inputbackcolor";
        /*搜索框线条颜色*/
        public final static String key_main_searchBody_inputLineColor = "searchbody_inputlinecolor";
    }

    public static class XMLTagMainNavValues {
        public String Nav_ico;
        public String Nav_title;
        public String Nav_color;
        public String Nav_link_url;
        public String Nav_static_titleColor;
        public String Nav_static_backColor;
        /*状态栏颜色*/
        public String Main_static_color;
        /*搜索框背景颜色*/
        public String Main_searchback_color;
        /*输入框文字颜色*/
        public String Main_input_color;
        /*输入框背景颜色*/
        public String Main_inputback_color;
        /*输入框线条颜色*/
        public String Main_input_line_color;

        public String getAuto_link_titleColor() {
            return Auto_link_titleColor;
        }

        public void setAuto_link_titleColor(String auto_link_titleColor) {
            Auto_link_titleColor = auto_link_titleColor;
        }

        public String getAuto_link_titleBackColor() {
            return Auto_link_titleBackColor;
        }

        public void setAuto_link_titleBackColor(String auto_link_titleBackColor) {
            Auto_link_titleBackColor = auto_link_titleBackColor;
        }

        public String getAuto_link_staticColor() {
            return Auto_link_staticColor;
        }

        public void setAuto_link_staticColor(String auto_link_staticColor) {
            Auto_link_staticColor = auto_link_staticColor;
        }

        public String Auto_link_titleColor;/*webview标题文字颜色*/
        public String Auto_link_titleBackColor;/*webview标题背景颜色*/
        public String Auto_link_staticColor;/*webview状态栏颜色*/

        public String getNav_static_titleColor() {
            return Nav_static_titleColor;
        }

        public void setNav_static_titleColor(String nav_static_titleColor) {
            Nav_static_titleColor = nav_static_titleColor;
        }

        public String getNav_static_backColor() {
            return Nav_static_backColor;
        }

        public void setNav_static_backColor(String nav_static_backColor) {
            Nav_static_backColor = nav_static_backColor;
        }

        public String getNav_static() {
            return Nav_static;
        }

        public void setNav_static(String nav_static) {
            Nav_static = nav_static;
        }

        public String Nav_static;

        public String getNav_ico() {
            return Nav_ico;
        }

        public void setNav_ico(String nav_ico) {
            Nav_ico = nav_ico;
        }

        public String getNav_title() {
            return Nav_title;
        }

        public void setNav_title(String nav_title) {
            Nav_title = nav_title;
        }

        public String getNav_color() {
            return Nav_color;
        }

        public void setNav_color(String nav_color) {
            Nav_color = nav_color;
        }

        public String getNav_link_url() {
            return Nav_link_url;
        }

        public void setNav_link_url(String nav_link_url) {
            Nav_link_url = nav_link_url;
        }
    }

    /**
     * 首页的新品促销
     */
    public static class XMLKeyMainNewshopIn {
        public static String key_newshopin_title = "newshopin_title";
        public static String key_newshopin_title_color = "newshopin_title_color";/*标题的颜色*/
        public static String key_newshopin_photoaddr = "newshopin_photoaddr";/*图片地址*/
        public static String key_newshopin_text = "newshopin_text";
        public static String key_newshopin_text_color = "newshopin_text_color";
        public static String key_title_color = "title_color";/*控件标题的颜色*/
        public static String key_text_color = "text_color";/*控件的内容的颜色*/
        public static String key_status_color = "status_color";/*控件状态的文字颜色*/
        public static String key_status_back_color = "status_back_color";/*控件的状态背景颜色*/
        public static String key_values_title = "title";
        public static String key_values_text = "text";
        public static String key_values_status = "status";
        public static String key_values_img = "img";
        public static String key = "values";

    }


    /*用户界面的卡片*/
    public static class XMLValuesUserpageCard {
        public String card_title;

        public String getCard_title() {
            return card_title;
        }

        public void setCard_title(String card_title) {
            this.card_title = card_title;
        }

        public ArrayList<XMLValuesUserpageBtnItem> getList() {
            return list;
        }

        public void addList(XMLValuesUserpageBtnItem item) {
            if (list != null) {
                list.add(item);
            } else {
            }
        }

        /*存储Item的数量*/
        public ArrayList<XmlTagValuesFactory.XMLValuesUserpageBtnItem> list = new
                ArrayList<XmlTagValuesFactory.XMLValuesUserpageBtnItem>();


    }

    public static class XMLValuesUserpageBtnItem {
        public String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle_color() {
            return title_color;
        }

        public void setTitle_color(String title_color) {
            this.title_color = title_color;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String title_color;
        public String img_url;
    }

    /*首页新品促销的商品表格*/
    public static class XMLValuesMainNewshopIn {
        public String title;
        public String title_color;
        public String text;
        public String text_color;
        public String status;
        public String status_color;
        public String status_back_color;
        public String img_url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle_color() {
            return title_color;
        }

        public void setTitle_color(String title_color) {
            this.title_color = title_color;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText_color() {
            return text_color;
        }

        public void setText_color(String text_color) {
            this.text_color = text_color;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus_color() {
            return status_color;
        }

        public void setStatus_color(String status_color) {
            this.status_color = status_color;
        }

        public String getStatus_back_color() {
            return status_back_color;
        }

        public void setStatus_back_color(String status_back_color) {
            this.status_back_color = status_back_color;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }


    /*首页新品促销的初始化表格*/
    public class XMLInitMainNewshopIn {
        public String getBody_title() {
            return body_title;
        }

        public void setBody_title(String body_title) {
            this.body_title = body_title;
        }

        public String getBody_title_color() {
            return body_title_color;
        }

        public void setBody_title_color(String body_title_color) {
            this.body_title_color = body_title_color;
        }

        public String getBody_text() {
            return body_text;
        }

        public void setBody_text(String body_text) {
            this.body_text = body_text;
        }

        public String getBody_text_color() {
            return body_text_color;
        }

        public void setBody_text_color(String body_text_color) {
            this.body_text_color = body_text_color;
        }

        public String getPhoto_addr() {
            return photo_addr;
        }

        public void setPhoto_addr(String photo_addr) {
            this.photo_addr = photo_addr;
        }

        public String body_title;
        public String body_title_color;
        public String body_text;
        public String body_text_color;
        public String photo_addr;

    }


    /**
     * 首页的经销商管理的界面
     */

    public static class XMLtagMainMerchant {
        public static String key_merchant_title = "merchant_title";/*大界面标题*/
        public static String key_merchant_photoaddr = "merchant_photoaddr";/*图片的地址*/
        public static String key_merchant_values = "values";/*包裹开始标签*/
        public static String key_merchant_values_title = "title";/*控件值的标题*/
        public static String key_merchant_values_text = "text";/*控件的标语*/
        public static String key_merchant_values_status = "status";/*控件的状态*/
        public static String key_merchant_values_background = "background";/*控件的背景图片*/


        private static String merchant_title;

        public static String getMerchant_photoaddr() {
            return merchant_photoaddr;
        }

        public static void setMerchant_photoaddr(String merchant_photoaddr) {
            XMLtagMainMerchant.merchant_photoaddr = merchant_photoaddr;
        }

        private static String merchant_photoaddr;

        public static String getMerchant_title() {
            return merchant_title;
        }

        public static void setMerchant_title(String merchant_title) {
            XMLtagMainMerchant.merchant_title = merchant_title;
        }
    }


    /**
     * 首页的品牌促销的整理
     */
    public static class XMLtagMainBrandPromotion {
        public static String key_back = "background";
        public static String key_title_a = "title_a";
        public static String key_title_b = "title_b";
        public static String key_big_img = "big_img";
        public static String key_shop_b_img = "shop_b_img";
        public static String key_shop_a_img = "shop_a_img";
        public static String key_shop_c_img = "shop_c_img";
        public static String key_shop_a_title = "shop_a_title";
        public static String key_shop_b_title = "shop_b_title";
        public static String key_shop_c_title = "shop_c_title";
        public static String key_shop_a_price = "shop_a_price";
        public static String key_shop_b_price = "shop_b_price";
        public static String key_shop_c_price = "shop_c_price";


        public static String back;

        public static String getBack() {
            return back;
        }

        public static void setBack(String back) {
            XMLtagMainBrandPromotion.back = back;
        }

        public static String title_a;

        public static String getKey_back() {
            return key_back;
        }

        public static void setKey_back(String key_back) {
            XMLtagMainBrandPromotion.key_back = key_back;
        }

        public static String title_b;

        public static String getShop_c_price() {
            return shop_c_price;
        }

        public static void setShop_c_price(String shop_c_price) {
            XMLtagMainBrandPromotion.shop_c_price = shop_c_price;
        }

        public static String getShop_b_price() {

            return shop_b_price;
        }

        public static void setShop_b_price(String shop_b_price) {
            XMLtagMainBrandPromotion.shop_b_price = shop_b_price;
        }

        public static String getShop_a_price() {

            return shop_a_price;
        }

        public static void setShop_a_price(String shop_a_price) {
            XMLtagMainBrandPromotion.shop_a_price = shop_a_price;
        }

        public static String getShop_c_title() {

            return shop_c_title;
        }

        public static void setShop_c_title(String shop_c_title) {
            XMLtagMainBrandPromotion.shop_c_title = shop_c_title;
        }

        public static String getShop_b_title() {
            return shop_b_title;

        }

        public static void setShop_b_title(String shop_b_title) {
            XMLtagMainBrandPromotion.shop_b_title = shop_b_title;
        }

        public static String getShop_a_title() {

            return shop_a_title;
        }

        public static void setShop_a_title(String shop_a_title) {
            XMLtagMainBrandPromotion.shop_a_title = shop_a_title;
        }

        public static String getShop_c_img() {

            return shop_c_img;
        }

        public static void setShop_c_img(String shop_c_img) {
            XMLtagMainBrandPromotion.shop_c_img = shop_c_img;
        }

        public static String getShop_a_img() {
            return shop_a_img;

        }

        public static void setShop_a_img(String shop_a_img) {
            XMLtagMainBrandPromotion.shop_a_img = shop_a_img;
        }

        public static String getShop_b_img() {

            return shop_b_img;
        }

        public static void setShop_b_img(String shop_b_img) {
            XMLtagMainBrandPromotion.shop_b_img = shop_b_img;
        }

        public static String getBig_img() {

            return big_img;
        }

        public static void setBig_img(String big_img) {
            XMLtagMainBrandPromotion.big_img = big_img;
        }

        public static String getTitle_b() {

            return title_b;
        }

        public static void setTitle_b(String title_b) {
            XMLtagMainBrandPromotion.title_b = title_b;
        }

        public static String getTitle_a() {

            return title_a;
        }

        public static void setTitle_a(String title_a) {
            XMLtagMainBrandPromotion.title_a = title_a;
        }

        public static String big_img;
        public static String shop_b_img;
        public static String shop_a_img;
        public static String shop_c_img;
        public static String shop_a_title;
        public static String shop_b_title;
        public static String shop_c_title;
        public static String shop_a_price;
        public static String shop_b_price;
        public static String shop_c_price;
    }

    /**
     * 配置文件的初始化界面的XML数据
     */
    public static class XmlInitPage {
        public final static String key_windowcolor = "WindowColor";
        public final static String key_searchbackground = "SearchBackground";
        public final static String key_searchbodycolor = "SearchBodyColor";
        public final static String key_searchlinecolor = "SearchLineColor";
        public final static String key_howmessagenumber = "HowMessageNumber";
        public final static String key_searchkeyword = "SearchKeyWord";/*首页热搜*/
        public static String WindowColor;
        public static String SearchBackground;
        public static String SearchBodyColor;
        public static String SearchLineColor;
        public static String HowMessageNumber;

        public static String getSearchKeyWord() {
            return SearchKeyWord;
        }

        public static void setSearchKeyWord(String searchKeyWord) {
            SearchKeyWord = searchKeyWord;
        }

        public static String SearchKeyWord;/*首页热搜*/

        public static String getHowMessageNumber() {
            return HowMessageNumber;
        }

        public static void setHowMessageNumber(String howMessageNumber) {
            HowMessageNumber = howMessageNumber;
        }

        public void ClearValues() {
            this.WindowColor = "";
            this.SearchBackground = "";
            this.SearchBodyColor = "";
            this.SearchLineColor = "";
        }

        public static String getWindowColor() {
            return WindowColor;
        }

        public static void setWindowColor(String windowColor) {
            WindowColor = windowColor;
        }

        public static String getSearchBackground() {
            return SearchBackground;
        }

        public static void setSearchBackground(String searchBackground) {
            SearchBackground = searchBackground;
        }

        public static String getSearchBodyColor() {
            return SearchBodyColor;
        }

        public static void setSearchBodyColor(String searchBodyColor) {
            SearchBodyColor = searchBodyColor;
        }

        public static String getSearchLineColor() {
            return SearchLineColor;
        }

        public static void setSearchLineColor(String searchLineColor) {
            SearchLineColor = searchLineColor;
        }
    }
}
