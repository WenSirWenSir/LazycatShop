package shlm.lmcs.com.lazycat.LazyShopValues;/** * 系统要用的全局的变量的列表 */public class LocalValues {    public static String ADDR_SERVICE = "";/*地区服务器地址*/    /**     *     */    public static class VALUES_USERCENTER {        public final static String IS_VIP = "0";        public final static String IS_NOT_VIP = "1";    }    /**     * 地址     */    public static class HTTP_ADDRS {        /*获取商品数据地址  */        public final static String HTTP_ADDR_GET_SHOPVALUES = "http://" + ADDR_SERVICE +                "/webdata/ShopOffice/getshop.php";        public final static String HTTP_ADDR_GET_SHOPCLASSIFY = "http://" + ADDR_SERVICE +                "/webdata/ShopOffice/getclassify.php";    }    /**     * 关于登录检查的返回值     */    public static class VALUES_LOGIN {        public final static String LOGIN_OK = "0";        public final static String LOGIN_ERROR = "1";        /*是商家*/        public final static String LOGIN_ISBUSINESS = "0";        /*不是商家*/        public final static String LOGIN_NOTBUSINESS = "1";    }    /**     * 关于商家的一些值     */    public static class VALUES_BUSINESS {        public final static String BUSINESS_NO = "1";/*是注册认证商家*/        public final static String BUSINESS_IS = "0";/*不是注册认证商家*/    }}