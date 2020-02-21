package shlm.lmcs.com.lazycat.LazyShopValues;import android.content.Context;import android.util.Log;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;/** * 系统要用的全局的变量的列表 */public class LocalValues {    public static Boolean isLogin = false;/*默认为没有登录*/    private String MSG = "LocalValues.java[+]";    public final static String NET_ERROR = "1";/*统一使用  提交失败*/    public final static String NET_OK = "0";/*统一使用  提交成功*/    public static String Service = "120.79.241.96";    /**     * 获取公有的类     *     * @return     */    public static HTTP_ADDRS getHttpaddrs(Context mContext) {        return new HTTP_ADDRS(mContext);    }    /**     * 系统设置     */    public static class VALUES_SYSTEM_SET {        public static String _SYSTEM_OPEN = "0";        public static String _SYSTEM_CLOSE = "1";    }    /**     * 关于订单状态     */    public static class VALUES_ORDER {        public static final String _STATUS_PUSH_OK = "1";/*提交成功*/        public static final String _STATUS_PICKSHOP_NOT = "0";/*没有商品*/        public static final String _STATUS_ORDER_DONE = "2";/*服务完毕 送货完成*/    }    /**     * 预支付订单号     */    public static class VALUES_TRRADENO {        /**         * 加入VIP使用的值         */        public final static String TRRADENO_PAY_VIP_ATTACH = "加盟云仓库VIP费用";        public final static String TRRADENO_PAY_VIP_BODY = "云仓库Vip支付";        public final static String TRRADENO_PAY_STATUS = "1";/*支付处理方式*/    }    /**     * 用来判断是否用来打开窗口或者打开URL     */    public static class VALUES_TOOPEN_TAG {        public static final String VALUES_TAG = "TAG";/*打开窗口*/        public static final String VALUES_URL = "URL";/*打开URL地址*/    }    /**     * 获取分类的参数     */    public static class VALUES_CLASSIFY {        public static final String VALUES_GETTITLE = "0";/*获取标题*/        public static final String VALUES_GETVALUES = "1";/*获取标题的所有元素*/        public static final String VALUES_GETSHOPS = "2";/*通过元素获取商品*/    }    /**     * 扫描器的参数     */    public static class VALUES_SCAN {        public final static int VALUES_QR_CODE = 11;    }    /**     * 包含的扫描要做的参数     * 分割为|的第二个参数就是要获取的值     * QRCODE     */    public static class VALUES_QRCODE {        public final static String VALUES_QRCODE_ORDER = "ORDER";        public final static String VALUES_QRCODE_WEBDATA = "http://";        public final static String VALUES_QRCODE_ACTIVITY = "windows";    }    /**     * 商品发货的状态 由系统管理人员处理事务     */    public static class VALUES_SENDSYSTEM {        public final static String IN_SEND = "0";/*提交成功*/        public final static String IN_GETSEND = "1";/*接单打印成功*/        public final static String IN_WHOLEASALE = "6";/*商品拼团*/        public final static String IN_RESERVE = "7";/*商品预定*/        public final static String IN_REFUSE = "3";/*拒接商品配送请求*/    }    /**     * 商品配送的状态 由地勤处理事务     */    public static class VALUES_DELIVER {        public final static String IN_TOCART = "4";/*已经装车开始配送*/        public final static String IN_TOUSER = "5";/*已经送达用户店里*/        public final static String IN_NOTSTOCKNUMBER = "2";/*商品没有库存*/    }    /**     * 商品配送完成 由地勤处理事务     */    public static class VALUES_ORDERDONE {        public final static String IN_TOPAYDONE = "8";/*用户已经支付完成*/        public final static String IN_CANCLE = "9";/*商家主动删除订货单*/    }    /**     * 商品需要售后     */    public static class VALUES_AFTERSALE {        public final static String IN_STARTAFTER = "0";/*用户提交售后请求*/        public final static String IN_STARTAFTERDONE = "1";/*用户取消售后*/        public final static String IN_GETSHOP = "2";/*商品取回成功,等待退款*/        public final static String IN_DONE = "3";/*商品完毕*/    }    /**     * 控件动画     */    public static class VALUES_VIEWS {        public final static String SHAKE_ANIMATION_1 = "1";        public final static String SHAKE_ANIMATION_2 = "2";    }    /**     * 关于用户是否喜欢商品的ICO的参考     */    public static class VALUES_SHOPLIKES {        public final static String SHOP_NO_LIKE = "0";        public final static String SHOP_LIKE = "1";    }    /**     * 关于商品的一些状态     */    public static class VALUES_SHOPPAGE {        public final static String NORMAL = "0";/*正常*/        public final static String PROMOTION = "1";/*促销*/        public final static String REDUCTION = "2";/*满减*/        public final static String VOLUME = "3";/*领劵促销*/        public final static String ONLY_VIP = "4";/*代表只有VIP用户可以购买*/        public final static String ONLY_ONE = "5";/*代表只能订购一件*/        public final static String WHOLEASALE = "6";/*商品拼团*/        public final static String RESERVE = "7";/*商品预定中*/        /**         * 关于服务         */    }    /**     * 用户中心的判断值     */    public static class VALUES_USERCENTER {        public final static String IS_VIP = "0";        public final static String IS_NOT_VIP = "1";        public final static String ACCOUNT_IS_OK = "0";        public final static String LOGIN_OK = "0";/*登录成功*/        public final static String LOGIN_ERROR = "2";/*登录失败*/    }    /**     * 处理获取到的商品数据源     */    public static class VALUES_GETSHOPPAGE {        public static String VALUES_NOT_SHOP = "6";        /**         * 提交服务要求的TAG使用什么方式来获取数据信息         */        public static String TAG_TOSHOP_ONLYID = "2";        public static String TAG_TOSHOP_TITLE = "1";    }    /*提现的值*/    public static class VALUES_CASH {        public static String _NOTBANK_CARD = "4";/*没有银行卡信息*/    }    /**     * 积分     */    public static class VALUES_INTEGRAL {        public static final String INTEGRAL_INFOR_SHOP = "购买商品";        public static final String INTEGRAL_INFOR_WORK = "业务奖励";        public static final String INTEGRAL_STATUS_CANT = "0";/*不能被使用*/        public static final String INTEGRAL_STATUS_USER = "1";/*可以使用*/        public static final String INTEGRAL_STATUS_FROZEN = "2";/*冻结*/    }    /**     * 搜索检索库的判断和请求值     */    public static class VALUES_SEARCH {        public final static String VALUES_TO_SEARCH_KEYWORD = "0";//只检索关键字 不检索商品        public final static String VALUES_TO_SEARCH_SHOPKEYWORD = "1";//只检索商品 不检索关键字        public final static String VALUES_TO_SEARCH_ONLYID = "2";/*根据ONLYID来检索商品*/    }    /**     * 地址     */    public static class HTTP_ADDRS {        public HTTP_ADDRS(Context mContext) {        }        public String getAddr() {            Log.e("LocalValues.java[+]", Service);            return Service;        }        /*微信支付同意下单号地址*/        public String HTTP_ADDR_WXPAY_UNIFIEDORDER = "https://api.mch.weixin.qq" + "" + "" + "" + ".com/pay" +                "/unifiedorder";        /*公司图片服务器地址 公开*/        public String HTTP_ADDR_PROGRAM_IMGSERVICE = "http://120.79.63.36/SHOP_DATABASE/";        /*公有的更新APK的地址*/        public String HTTP_ADDR_UPDATE_APK = "http://" + getAddr() + "/webdata/ConfigXml" + "/Version.xml";        /*获取商品数据地址  */        public String HTTP_ADDR_GET_SHOPVALUES = "http://" + getAddr() + "/webdata/TerminalSystemOS/" +                "/PagesgetShoppage.php";        public String HTTP_ADDR_GETSHOPCAR = "http://" + getAddr() + "/webdata/Shopcart/index.php";        public String HTTP_ADDR_SEARCH_KEY = "http://" + getAddr() + "/webdata/ShopOffice" + "/search_key.php";        /*云仓库检索商品的关键字和标题*/        public String HTTP_ADDR_SEARCH_SHOPLIST =                "http://" + getAddr() + "/webdata/ShopOffice" + "/toSearchShoplist" + ".php";        public String HTTP_ADDR_PUSH_MAKEBUSINESS = "http://" + getAddr() + "/webdata/System" + "/toMakeBusiness.php";        /*获取用户的数据地址*/        public String HTTP_ADDR_GETUSER_PAGE = "http://" + getAddr() + "/webdata/Login/index.php";        /*查看用户订单的URL*/        public String HTTP_ADDR_USER_ORDERLIST = "http://" + getAddr() + "/webdata/Htmlpage" + "/orderPagelist.php";        /*查看用户的收藏记录记录*/        public String HTTP_ADDR_USER_BROWSE = "http://" + getAddr() + "/webdata/Htmlpage" + "/browsePagelist.php";        /*获取地区服务器的首页整理数据的地址*/        public String HTTP_ADDR_GET_MAINCONFIGPAGE =                "http://" + getAddr() + "/webdata/ConfigXml" + "/InitMainConfig" + ".php";        /*获取系统的通知信息地址*/        public String HTTP_ADDR_GET_SYSTEMMSG = "http://" + getAddr() + "/webdata/System" + "/toSystemMsg.php";        public String HTTP_ADDR_INITSEARCH = "http://" + getAddr() + "/webdata/ConfigXml" + "/InitSearchConfig.php";        public String HTTP_ADDR_SEND_LOGINSMS = "http://" + getAddr() + "/webdata/System/SendSms" + "/loginSms.php";        public String HTTP_ADDR_INSPECT_LOGIN = "http://" + getAddr() + "/webdata/System/Inspect" + "/In_login.php";        /*获取用户的信息*/        public String HTTP_ADDR_GETUSER_VALUES = "http://" + getAddr() + "/webdata/User" + "/getuservalue.php";        /*提交用户的发货单*/        public String HTTP_ADDR_TOSAVEORDER = "http://" + getAddr() + "/webdata/User/toSaveorder" + ".php";        public String HTTP_ADDR_SAVEUSERODER = "http://" + getAddr() + "/webdata/ShopOffice" + "/saveOrderpage.php";        /*获取用户的发货单*/        public String HTTP_ADDR_ORDER_TOOLS = "http://" + getAddr() + "/webdata/User/OrderTools" + ".php";        /*提交扫描的信息到服务器 检索*/        public String HTTP_ADDR_TOSCANQRCODE = "http://" + getAddr() + "/webdata/System" + "/toScanQRCode.php";        /*关于我们的网址*/        public String HTTP_ADDR_ABOUTTO_AS = "http://" + getAddr() + "/webdata/System/toAboutas" + ".php";        public String HTTP_ADDR_TO_HELP = "http://" + getAddr() + "/webdata/System/toHelp.php";        /*用户的足迹*/        public String HTTP_ADDR_GETUSER_TRACK = "http://" + getAddr() + "/webdata/User/toGettrack" + ".php";        /*云仓库的首页展示的数据*/        public String HTT_ADDR_GETMAINPAGE_SHOWSHOP = "http://" + getAddr() + "/webdata/System" + "/toGetInitmainShop"                + ".php";        /*云仓库 获取订单数据地址*/        public String HTTP_ADDR_GETORDERPAGE = "http://" + getAddr() + "/webdata/System" + "/toGetOrdernumberpage.php";        /*扫描之后获取订单的ID包里的所有商品的信息*/        public String HTTP_ADDR_TOSCANORDERLIST = "http://" + getAddr() + "/webdata/System" + "/toScanOrderlist.php";        /*获取云仓库管理退单的系统服务地址*/        public String HTTP_ADDR_TOREFUSESHOP = "http://" + getAddr() + "/webdata/System" + "/toRefuseshop.php";        /*获取云仓库删除一个已经发送成功的订单*/        public String HTTP_ADDR_TODEL_SENDORDER = "http://" + getAddr() + "/webdata/System" + "/toDelsendOrder.php";        /*云仓库创建一个KEY到微信支付数据库中*/        public String HTTP_ADDR_WXPAY_CREATEOUTTRADENO = "http://" + getAddr() + "/webdata/System" +                "/toCreateOuttradeno.php";        /*云仓库微信支付成功回调地址*/        public String HTTP_ADDR_WXPAY_NOTIFY_URL = "http://" + getAddr() + "/webdata/Wxpay" + "/interface.php";        /*云仓库获取用户购买Vip的充值记录*/        public String HTTP_ADDR_USERPAY_VIPRECORD = "http://" + getAddr() + "/webdata/User" + "/toGetWxpaytoVip.php";        /*云仓库新系统上线领取7天Vip的地址*/        public String HTTP_ADDR_GET_EVENT_VIP = "http://" + getAddr() + "/webdata/User/newSystemin" + "/toGetvip.php";        /*云仓库网络获取随机的商品的地址*/        public String HTTP_ADDR_GET_RECOMMEND_SHOP = "http://" + getAddr() + "/webdata/System" + "/toGetRecommendShop"                + ".php";        /*云仓库网络获取积分的地址*/        public String HTTP_ADDR_ADDINTEGRAL = "http://" + getAddr() + "/webdata/User/toAddIntegral" + ".php";        public String HTTP_ADDR_TOGETINTEGRAL = "http://" + getAddr() + "/webdata/User" + "/toSearchIntegral.php";        /**         * 检测Vip的状态         */        public String HTTP_ADDR_CHECK_VIP = "http://" + getAddr() + "/webdata/User/toVipcheck" + ".php";        /*添加用户的银行卡*/        public String HTTP_ADDR_TOADDBANKS = "http://" + getAddr() + "/webdata/User/toAddBank.php";        /*云仓库获取用户的VIP状态*/        public String HTTP_ADDR_GETUSERVIP_STATUS = "http://" + getAddr() + "/webdata/User" + "/toGetUservipStatus.php";        /*云仓库获取帮助信息的URL*/        public String HTTP_ADDR_GET_SYSTEMHELPMSG = "http://" + getAddr() + "/webdata/System" + "/toGethelpmsg.php";        /*云仓库的服务器的管理地址*/        public String HTTP_ADDR_SYSTEMMONITOR_LOG = "http://" + getAddr() + "/webdata/System" + "/toSavelog.php";        /*云仓库 记录系统的单日访问次数*/        public String HTTP_ADDR_TERMINALSYSTEM_MO_SYSTEMVS =                "http://" + getAddr() + "/webdata" + "/TerminalSystemMO" + "/Record/SystemVs.php";        /*云仓库 记录系统的商品的搜索记录*/        public String HTTP_ADDR_TERMINALSYSTEM_MO_SYSTEM_SEARCH =                "http://" + getAddr() + "/webdata/TerminalSystemMO" + "/Record/System/Search.php";        /*云仓库 记录系统用户点击的商品 */        public String HTTP_ADDR_TERMINALSYSTEM_MO_SHOP_SEE =                "http://" + getAddr() + "/webdata" + "/TerminalSystemMO" + "/Record/Shop/See.php";        /*云仓库 记录用户的发货单*/        public String HTTP_ADDR_TERMINALSYSTEM_OS = "http://" + getAddr() + "/webdata" + "/TerminalSystemOS/OS.php";        /*云仓库 获取主流分类和次主流分类*/        public String HTTP_ADDR_CLASSIFY_SYSTEM_GET = "http://" + getAddr() + "/webdata" + "/ClassifySystemGET/Get.php";        /*云仓库 根据分类标题获取节点信息*/        public String HTTP_ADDR_CLASSIFY_SYSTEM_GET_GETNODE = "http://" + getAddr() + "/webdata" +                "/ClassifySystemGET/Getnode.php";        /*云仓库 首页界面整理地址*/        public String HTTP_ADDR_CLASSIFY_SYSTEM_GET_INITHOME = "http://" + getAddr() + "/webdata" +                "/ClassifySystemGET/initHome.php";        /*云仓库 根据节点和标签获取数据信息*/        public String HTTP_ADDR_CLASSIFY_SYSTEM_GET_GETCLASSSHOP = "http://" + getAddr() + "/webdata" +                "/ClassifySystemGET/getClassshop.php";        /*云仓库 创建预支付信息*/        public String HTTP_ADDR_VIP_SYSTEM_JOIN = "http://" + getAddr() + "/webdata/VipSystemJoin" + "/create_key.php";        /*云仓库 获取发货单数据信息*/        public String HTTP_ADDR_TERMINALSYSTEM_MO_PULLOUTORDERS =                "http://" + getAddr() + "/webdata/TerminalSystemOS" + "/PulloutOrders.php";        /*云仓库 删除发货单数据信息*/        public String HTTP_ADDR_TERMINALSYSTEM_OS_DELETEORDER =                "http://" + getAddr() + "/webdata/TerminalSystemOS" + "/deleteOrder.php";        /*云仓库 获取物流信息*/        public String HTTP_ADDR_FREIGHTOS_FREIGHT = "http://" + getAddr() + "/webdata/FreightOS/Freight.php";        /*云仓库 获取用户界面整理信息*/        public String HTTP_ADDR_TERMINALSYSTEM_OS_PAGESGETUSER =                "http://" + getAddr() + "/webdata/TerminalSystemOS" + "/PagesgetUser.php";        /*云仓库 获取积分主界面整理信息*/        public String HTTP_ADDR_TERMINALSYSTEM_OS_PAGEINTEGRAL =                "http://" + getAddr() + "/webdata/TerminalSystemOS" + "/PagesgetIntegral.php";        /*云仓库 获取积分界面的整理信息*/        public String HTTP_ADDR_TERMINALSYSTEM_OS_PAGEINTEGRALMAIN = "http://" + getAddr() + "/webdata" +                "/TerminalSystemOS" + "/PagesgetIntegralMain.php";        /*云仓库 获取提现的信息*/        public String HTTP_ADDR_TERMINALSYSTEM_OS_TOCASH = "http://" + getAddr() + "/webdata/TerminalSystemOS/Cash.php";        /*云仓库获取银行卡*/        /*超级系统 获取全部商家*/        public String HTTP_ADDR_SUPER_PULLBUSINESS = "http://" + getAddr() + "/system/SuperSystemOS/pullbusiness.php";        public String HTTP_ADDR_SUPER_PULLPICKSHOPS = "http://" + getAddr() + "/system/SuperSystemOS" +                "/PullBusinesspickshops.php";        /*全部取货成功*/        public String HTTP_ADDR_SUPER_PUSHPICKSHOPS = "http://" + getAddr() + "/system/SuperSystemOS" +                "/Pushpickshops.php";        /*获取每个取货的商品的所有订单用户*/        public String HTTP_ADDR_SUPER_PULLSHOPS_USERS = "http://" + getAddr() + "/system/SuperSystemOS" +                "/PullshopUsers.php";        /*标记一个商品没有货物*/        public String HTTP_ADDR_SUPER_PUSHNOTSHOP =                "http://" + getAddr() + "/system/SuperSystemOS" + "/PushnotGoods" + ".php";    }    /**     * 关于登录检查的返回值     */    public static class VALUES_LOGIN {        public final static String LOGIN_OK = "0";        public final static String LOGIN_ERROR = "1";        /*是商家*/        public final static String LOGIN_ISBUSINESS = "0";        /*不是商家*/        public final static String LOGIN_NOTBUSINESS = "1";    }    /**     * 关于商家的一些值     */    public static class VALUES_BUSINESS {        public final static String BUSINESS_NO = "1";/*是注册认证商家*/        public final static String BUSINESS_IS = "0";/*不是注册认证商家*/    }    public static String getServices(Context mContext) {/*        LocalProgramTools.ProgramServiceTools programServiceTools = LocalProgramTools                .getServiceToolsInstatnce();        return programServiceTools.GetService();*/        return Tools.getService(mContext).trim();    }}