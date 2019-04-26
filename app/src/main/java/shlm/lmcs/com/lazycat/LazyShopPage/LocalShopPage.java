package shlm.lmcs.com.lazycat.LazyShopPage;


/**
 * ��Ʒ��Config
 */
public class LocalShopPage {
    /**
     * ����
     */
    public final static String SHOP_TITLE = "title";
    /**
     * ����
     */
    public final static String SHOP_BARCODE = "barcode";
    private String title;//����
    private String barcode;//����
    private String small_img;//СͼƬ
    /**
     * СͼƬ
     */
    public final static String SHOP_SMALL_IMG = "small_img";
    private String in_img;/*��ͼƬ*/
    /**
     * ��ͼƬ
     */
    public final static String SHOP_IN_IMG = "in_img";
    private String big_img;/*��ͼƬ*/
    /**
     * ��ͼƬ
     */
    public final static String SHOP_BIG_IMG = "big_img";
    private float origin_price;/*ԭ�۸�*/
    /**
     * ԭ�۸�
     */
    public final static String SHOP_ORIGIN_PRICE = "origin_price";
    private float now_price;/*�ּ۸�*/
    /**
     * �ּ۸�
     */
    public final static String SHOP_NOW_PRICE = "now_price";
    private int shop_static;/*��Ʒ��״̬*/
    /**
     * ��Ʒ��״̬
     */
    public final static String SHOP_STATIC = "shop_static";
    private int sale_number;/*��������*/
    /**
     * ��������
     */
    public final static String SHOP_SALE_NUMBER = "sale_number";
    private int like_number;/*ϲ��������*/
    /**
     * ϲ��������
     */
    public final static String SHOP_LIKE_NUMBER = "like_number";
    private String big_showshop_office_promotion_title;/*չʾ���Ĵ�������*/
    /**
     * չʾ���Ĵ�������
     */
    public final static String SHOP_BIG_SHOWSHOP_OFFICE_PROMOTION_TITLE =
            "big_showshop_office_promotion_title";
    private String big_showshop_office_promotion_formula;/*չʾ���Ĵ�����ʽ*/
    /**
     * չʾ���Ĵ�����ʽ
     */
    public final static String SHOP_BIG_SHOWSHOP_OFFICE_PROMOTION_FORMULA =
            "big_showshop_office_promotion_formula";
    private float shop_score;/*��Ʒ������*/
    /**
     * ��Ʒ������
     */
    public final static String SHOP_SCORE = "shop_score";
    private String shop_unit;/*��Ʒ�ĵ�λ*/
    /**
     * ��Ʒ�ĵ�λ
     */
    public final static String SHOP_UNIT = "shop_unit";
    private String shop_spec;/*��Ʒ������*/
    /**
     * ��Ʒ������
     */
    public final static String SHOP_SPEC = "shop_spec";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSmall_img() {
        return small_img;
    }

    public void setSmall_img(String small_img) {
        this.small_img = small_img;
    }

    public String getIn_img() {
        return in_img;
    }

    public void setIn_img(String in_img) {
        this.in_img = in_img;
    }

    public String getBig_img() {
        return big_img;
    }

    public void setBig_img(String big_img) {
        this.big_img = big_img;
    }

    public float getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(float origin_price) {
        this.origin_price = origin_price;
    }

    public float getNow_price() {
        return now_price;
    }

    public void setNow_price(float now_price) {
        this.now_price = now_price;
    }

    public int getShop_static() {
        return shop_static;
    }

    public void setShop_static(int shop_static) {
        this.shop_static = shop_static;
    }

    public int getSale_number() {
        return sale_number;
    }

    public void setSale_number(int sale_number) {
        this.sale_number = sale_number;
    }

    public int getLike_number() {
        return like_number;
    }

    public void setLike_number(int like_number) {
        this.like_number = like_number;
    }

    public String getBig_showshop_office_promotion_title() {
        return big_showshop_office_promotion_title;
    }

    public void setBig_showshop_office_promotion_title(String big_showshop_office_promotion_title) {
        this.big_showshop_office_promotion_title = big_showshop_office_promotion_title;
    }

    public String getBig_showshop_office_promotion_formula() {
        return big_showshop_office_promotion_formula;
    }

    public void setBig_showshop_office_promotion_formula(String big_showshop_office_promotion_formula) {
        this.big_showshop_office_promotion_formula = big_showshop_office_promotion_formula;
    }

    public float getShop_score() {
        return shop_score;
    }

    public void setShop_score(float shop_score) {
        this.shop_score = shop_score;
    }

    public String getShop_unit() {
        return shop_unit;
    }

    public void setShop_unit(String shop_unit) {
        this.shop_unit = shop_unit;
    }

    public String getShop_spec() {
        return shop_spec;
    }

    public void setShop_spec(String shop_spec) {
        this.shop_spec = shop_spec;
    }

    public boolean isIs_special_delivery() {
        return is_special_delivery;
    }

    public void setIs_special_delivery(boolean is_special_delivery) {
        this.is_special_delivery = is_special_delivery;
    }

    public String getShowcabinet_classified_labels() {
        return showcabinet_classified_labels;
    }

    public void setShowcabinet_classified_labels(String showcabinet_classified_labels) {
        this.showcabinet_classified_labels = showcabinet_classified_labels;
    }

    public String getShowcabinet_classified_promotion_title() {
        return showcabinet_classified_promotion_title;
    }

    public void setShowcabinet_classified_promotion_title(String showcabinet_classified_promotion_title) {
        this.showcabinet_classified_promotion_title = showcabinet_classified_promotion_title;
    }

    public int getShop_business_type() {
        return shop_business_type;
    }

    public void setShop_business_type(int shop_business_type) {
        this.shop_business_type = shop_business_type;
    }

    public boolean isIs_shop_imported() {
        return is_shop_imported;
    }

    public void setIs_shop_imported(boolean is_shop_imported) {
        this.is_shop_imported = is_shop_imported;
    }

    private boolean is_special_delivery;/*�Ƿ��Լ�����*/
    private String showcabinet_classified_labels;/*չʾ��/���ķ����ǩ*/
    private String showcabinet_classified_promotion_title;/*չʾ��Ĵ����ı���*/
    private int shop_business_type;/*Ӫҵ������0��Ӫ1������2����3����*/
    private boolean is_shop_imported;/*�Ƿ����*/


}
