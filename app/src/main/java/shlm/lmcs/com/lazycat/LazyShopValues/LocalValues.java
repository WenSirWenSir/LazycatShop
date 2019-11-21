package shlm.lmcs.com.lazycat.LazyShopValues;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;/** * 系统要用的全局的变量的列表 */public class LocalValues {    public static Boolean isLogin = false;/*默认为没有登录*/    private String MSG = "LocalValues.java[+]";    public final static String NET_ERROR = "1";/*统一使用  提交失败*/    /**     * 用来判断是否用来打开窗口或者打开URL     */    public static class VALUES_TOOPEN_TAG {        public static final String VALUES_TAG = "TAG";/*打开窗口*/        public static final String VALUES_URL = "URL";/*打开URL地址*/    }    /**     * 获取分类的参数     */    public static class VALUES_CLASSIFY {        public static final String VALUES_GETTITLE = "0";/*获取标题*/        public static final String VALUES_GETVALUES = "1";/*获取标题的所有元素*/        public static final String VALUES_GETSHOPS = "2";/*通过元素获取商品*/    }    /**     * 扫描器的参数     */    public static class VALUES_SCAN {        public final static int VALUES_QR_CODE = 11;    }    /**     * 包含的扫描要做的参数     * 分割为|的第二个参数就是要获取的值     * QRCODE     */    public static class VALUES_QRCODE {        public final static String VALUES_QRCODE_ORDER = "ORDER";        public final static String VALUES_QRCODE_WEBDATA = "http://";        public final static String VALUES_QRCODE_ACTIVITY = "windows";    }    /**     * 商品发货的状态 由系统管理人员处理事务     */    public static class VALUES_SENDSYSTEM {        public final static String IN_SEND = "0";/*提交成功*/        public final static String IN_GETSEND = "1";/*接单打印成功*/        public final static String IN_REFUSE = "3";/*拒接商品配送请求*/    }    /**     * 商品配送的状态 由地勤处理事务     */    public static class VALUES_DELIVER {        public final static String IN_TOCART = "2";/*已经装车开始配送*/        public final static String IN_TOUSER = "3";/*已经送达用户店里*/        public final static String IN_NOTSTOCKNUMBER = "2";/*商品没有库存*/    }    /**     * 商品配送完成 由地勤处理事务     */    public static class VALUES_ORDERDONE {        public final static String IN_TOPAYDONE = "4";/*用户已经支付完成*/    }    /**     * 商品需要售后     */    public static class VALUES_AFTERSALE {        public final static String IN_STARTAFTER = "0";/*用户提交售后请求*/        public final static String IN_STARTAFTERDONE = "1";/*用户取消售后*/        public final static String IN_GETSHOP = "2";/*商品取回成功,等待退款*/        public final static String IN_DONE = "3";/*商品完毕*/    }    /**     * 关于用户是否喜欢商品的ICO的参考     */    public static class VALUES_SHOPLIKES {        public final static String SHOP_NO_LIKE = "0";        public final static String SHOP_LIKE = "1";    }    /**     * 关于商品的一些状态     */    public static class VALUES_SHOPPAGE {        public final static String NORMAL = "0";/*正常*/        public final static String PROMOTION = "1";/*促销*/        public final static String REDUCTION = "2";/*满减*/        public final static String VOLUME = "3";/*领劵促销*/        public final static String ONLY_VIP = "4";/*代表只有VIP用户可以购买*/        public final static String ONLY_ONE = "5";/*代表只能订购一件*/        /**         * 关于服务         */    }    /**     * 用户中心的判断值     */    public static class VALUES_USERCENTER {        public final static String IS_VIP = "0";        public final static String IS_NOT_VIP = "1";        public final static String ACCOUNT_IS_OK = "0";        public final static String LOGIN_OK = "0";/*登录成功*/        public final static String LOGIN_ERROR = "1";/*登录失败*/    }    /**     * 搜索检索库的判断和请求值     */    public static class VALUES_SEARCH {        public final static String VALUES_TO_SEARCH_KEYWORD = "0";//只检索关键字 不检索商品        public final static String VALUES_TO_SEARCH_SHOPKEYWORD = "1";//只检索商品 不检索关键字    }    /**     * 地址     */    public static class HTTP_ADDRS {        /*微信支付同意下单号地址*/        public final static String HTTP_ADDR_WXPAY_UNIFIEDORDER = "https://api.mch.weixin.qq" +                ".com/pay/unifiedorder";        /*公有图片服务器地址 公开*/        public final static String HTTP_ADDR_IMG_URL = "http://f.freep.cn/583105/SHOP_DATABASE/";        /*公有的更新APK的地址*/        public final static String HTTP_ADDR_UPDATE_APK = "http://120.79.63.36/Yuncanku.apk";        /*获取商品数据地址  */        public final static String HTTP_ADDR_GET_SHOPVALUES = "http://" + getServices() +                "/webdata/ShopOffice/getshop.php";        public final static String HTTP_ADDR_GETSHOPCAR = "http://" + getServices() +                "/webdata/Shopcart/index.php";        public final static String HTTP_ADDR_SEARCH_KEY = "http://" + getServices() +                "/webdata/ShopOffice/search_key.php";        public final static String HTTP_ADDR_PUSH_MAKEBUSINESS = "http://" + getServices() +                "/webdata/System/toMakeBusiness.php";        /*获取用户的数据地址*/        public final static String HTTP_ADDR_GETUSER_PAGE = "http://" + getServices() +                "/webdata/Login/index.php";        /**         * 测试的图片服务器地址         */        public final static String HTTP_ADDR_PHOTOS_IMG = "http://i.caigoubao" + "" + "" + "" +                "" + ".cc/583105/SHOP_DATABASE/";        /*查看用户订单的URL*/        public final static String HTTP_ADDR_USER_ORDERLIST = "http://" + getServices() +                "/webdata/Htmlpage/orderPagelist.php";        /*查看用户的收藏记录记录*/        public final static String HTTP_ADDR_USER_BROWSE = "http://" + getServices() +                "/webdata/Htmlpage/browsePagelist.php";        /*获取地区服务器的首页整理数据的地址*/        public final static String HTTP_ADDR_GET_MAINCONFIGPAGE = "http://" + getServices() +                "/webdata/ConfigXml/InitMainConfig.php";        /*获取系统的通知信息地址*/        public final static String HTTP_ADDR_GET_SYSTEMMSG = "http://" + getServices() +                "/webdata/System/toSystemMsg.php";        public final static String HTTP_ADDR_INITSEARCH = "http://" + getServices() +                "/webdata/ConfigXml/InitSearchConfig.php";        public final static String HTTP_ADDR_SEND_LOGINSMS = "http://" + getServices() +                "/webdata/System/SendSms/loginSms.php";        public final static String HTTP_ADDR_INSPECT_LOGIN = "http://" + getServices() +                "/webdata/System/Inspect/In_login.php";        /*获取用户中心的信息*/        public final static String HTTP_ADDR_GETUSER_CENTERVALUES = "http://" + getServices() +                "/webdata/User/getuserCentervalues.php";        /*获取用户的信息*/        public final static String HTTP_ADDR_GETUSER_VALUES = "http://" + getServices() +                "/webdata/User/getuservalue.php";        /*提交用户的发货单*/        public final static String HTTP_ADDR_SAVEUSERODER = "http://" + getServices() +                "/webdata/ShopOffice/saveOrderpage.php";        /*获取用户的发货单*/        public final static String HTTP_ADDR_ORDER_TOOLS = "http://" + getServices() +                "/webdata/User/OrderTools.php";        /*提交扫描的信息到服务器 检索*/        public final static String HTTP_ADDR_TOSCANQRCODE = "http://" + getServices() +                "/webdata/System/toScanQRCode.php";        /*关于我们的网址*/        public final static String HTTP_ADDR_ABOUTTO_AS = "http://" + getServices() +                "/webdata/System/toAboutas.php";        public final static String HTTP_ADDR_TO_HELP = "http://" + getServices() +                "/webdata/System/toHelp.php";        /*用户的足迹*/        public final static String HTTP_ADDR_GETUSER_TRACK = "http://" + getServices() +                "/webdata/User/toGettrack.php";        /*云仓库的分类*/        public final static String HTTP_ADDR_GETCLASSIFY = "http://" + getServices() +                "/webdata/System/toGetclassify.php";        /*云仓库的首页展示的数据*/        public final static String HTT_ADDR_GETMAINPAGE_SHOWSHOP = "http://" + getServices() +                "/webdata/System/toGetInitmainShop.php";        /*云仓库 获取订单数据地址*/        public final static String HTTP_ADDR_GETORDERPAGE = "http://" + getServices() +                "/webdata/System/toGetOrdernumberpage.php";        /*扫描之后获取订单的ID包里的所有商品的信息*/        public final static String HTTP_ADDR_TOSCANORDERLIST = "http://" + getServices() +                "/webdata/System/toScanOrderlist.php";        /*获取云仓库管理退单的系统服务地址*/        public final static String HTTP_ADDR_TOREFUSESHOP = "http://" + getServices() +                "/webdata/System/toRefuseshop.php";    }    /**     * 关于登录检查的返回值     */    public static class VALUES_LOGIN {        public final static String LOGIN_OK = "0";        public final static String LOGIN_ERROR = "1";        /*是商家*/        public final static String LOGIN_ISBUSINESS = "0";        /*不是商家*/        public final static String LOGIN_NOTBUSINESS = "1";    }    /**     * 关于商家的一些值     */    public static class VALUES_BUSINESS {        public final static String BUSINESS_NO = "1";/*是注册认证商家*/        public final static String BUSINESS_IS = "0";/*不是注册认证商家*/    }    public static String getServices() {        LocalProgramTools.ProgramServiceTools programServiceTools = LocalProgramTools                .getServiceToolsInstatnce();        return programServiceTools.GetService();    }}