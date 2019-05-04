package shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory;


/**
 * XML对应的数据解析
 */
public class XmlTagValuesFactory {


    /**
     * 首页的新品促销
     */
    public static class XMLtagMainNewshopIn {
        public static String key_newshopin_title = "newshopin_title";
        public static String key_newshopin_photoaddr = "newshopin_photoaddr";
        public static String key_newshopin_text = "newshopin_text";
        public static String key_values = "values";
        public static String key_values_title = "title";
        public static String key_values_text = "text";
        public static String key_values_status = "status";
        public static String key_values_img = "img";

        public static String getNewshopin_title() {
            return newshopin_title;
        }

        public static void setNewshopin_title(String newshopin_title) {
            XMLtagMainNewshopIn.newshopin_title = newshopin_title;
        }

        public static String getNewshopin_text() {
            return newshopin_text;
        }

        public static void setNewshopin_text(String newshopin_text) {
            XMLtagMainNewshopIn.newshopin_text = newshopin_text;
        }

        public static String getNewshopin_photoaddr() {
            return newshopin_photoaddr;
        }

        public static void setNewshopin_photoaddr(String newshopin_photoaddr) {
            XMLtagMainNewshopIn.newshopin_photoaddr = newshopin_photoaddr;
        }

        public static String newshopin_title;/*首页新品促销标题*/
        public static String newshopin_text;/*首页新品促销标语*/
        public static String newshopin_photoaddr;/*首页新品促销图片地址*/
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
}
