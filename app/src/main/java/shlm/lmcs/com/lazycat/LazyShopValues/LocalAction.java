package shlm.lmcs.com.lazycat.LazyShopValues;/** * 字节对 ACTION */public class LocalAction {    //公用    public static final String ACTION = "action";    public static class ACTION_PAYVIP_ACTION {    }    public static class ACTION_BANKS {        public final static String BANK_NAME = "bankname";/*银行名称*/        public final static String BANK_PEOPLE = "bankpeople";/*姓名*/        public final static String BANK_TEL = "banktel";/*电话*/        public final static String BANK_CARD = "bankcard";/*卡号*/    }    /**     * 记录对接商家的信息的ACTION     */    public static class ACTION_INTERFACE_BUSINESS {        public final static String INTERFACE_BUSINESS_LONG = "interfaceBusinessLong";/*用户的经度*/        public final static String INTERFACE_BUSINESS_LAT = "interfaceBusinessLat";/*用户的维度*/    }    /**     * 处理赠品的信息的ACTION     */    public static class ACTION_GIFTSHOP {        public final static String GIFT_TITLE = "gifttitle";        public final static String GIFT_CONDTION = "giftcondetion";        public final static String GIFT_WEIGHT = "giftweight";        public final static String GIFT_COMPANY = "giftcompany";        public final static String GIFT_SPEC = "giftspec";        public final static String GIFT_EXP = "giftexp";        public final static String GIFT_PD = "giftpd";        public final static String GIFT_STOCKNUMBER = "giftstocknumber";        public final static String GIFT_IMG = "giftimg";        public final static String GIFT_BARCODE = "giftbarcoe";    }    /**     * 存在系统服务器的ID     */    public static class ACTION_SYSTEMID {        public final static String ACTION_ORDER = "system_id";    }    public static class ACTION_QRCODE {        public final static String ACTION_BARCODE = "barcode";    }    /**     * 商品预支付订单参数     */    public static class ACTION_TRRADENO {        public final static String ACTION_TRRADENO_NUMBER = "trradenoNumber";        public final static String ACTION_TRRADENO_ATTACH = "attach";        public final static String ACTION_TRRADENO_BODY = "paybody";        public final static String ACTION_TRRADENO_TOTAL = "totalfee";        public final static String ACTION_TRRADENO_PAYSTATUS = "paystatus";    }    /**     * 微信支付用     */    public static class ACTION_WXPAY {        public final static String ACTION_APPID = "appid";        public final static String ACTION_ATTACH = "attach";        public final static String ACTION_BODY = "body";        public final static String ACTION_MCH_ID = "mch_id";        public final static String ACTION_NONCE_STR = "noncestr";        public final static String ACTION_NOTIFY_URL = "notify_url";        public final static String ACTION_OUT_TRADE_NO = "out_trade_no";        public final static String ACTION_SPBILL_CREATE_IP = "spbill_create_ip";        public final static String ACTION_TOTAL_FEE = "total_fee";        public final static String ACTION_TRADE_TYPE = "trade_type";        public final static String ACTION_SIGN = "sign";        public final static String ACTION_PACKAGE = "package";        public final static String ACTION_PARTNERID = "partnerid";        public final static String ACTION_TIMESTAMP = "timestamp";        public final static String ACTION_PREPAYID = "prepayid";        /**         * 服务器返回数据         */    }    /**     * 首页展示的图片     */    public static class ACTION_PROMOTIONACT {        public static final String ACTION_LOCAL_LONG = "local_long";/*定位到的经度*/        public static final String ACTION_LOCAL_LAT = "local_lat";/*定位到的维度*/        public static final String ACTION_LOCAL_CITYCODE = "local_citycode";/*定位到的城市的代码*/    }    public static class WINDOWS_TO_WINDOWS {        public static final String ACTION_SEARCH_KEY = "search_key";        public static final String ACTION_ORDERNUMBER = "ordernumber";        public static final String ACTION = "action";        public static final String ACTION_TO_PAYMONEY = "money";    }    /*提交数据 都用XML提交数据*/    public static class ACTION_MAINPAGE {        public static String ACTION_MAINFRGPAG_LOADINGIMG = "head_toloading_img";/*下拉加载的图片的TAG*/        public static String ACTION_MAINFRGPAG_LOADINGONCLICK = "head_toloading_onClick";        public static String ACTION_MARQUEE_FOREVER = "marquee_forever";        /*下拉加载的点击的处理URL或者TAG*/    }    /**     * 商户的预约action     */    public static class ACTION_MAKEBUSINESS {        public static String ACTION_BUSINESS_NAME = "business_name";        public static String ACTION_BUSINESS_ADDR = "business_addr";        public static String ACTION_BUSINESS_PEOPLE = "business_people";        public static String ACTION_BUSINESS_TEL = "business_tel";        public static String ACTION_BUSINESS_LONG = "business_long";        public static String ACTION_BUSINESS_LAT = "business_lat";    }    /**     * 商品搜索的ACTION     */    public static class ACTION_SEARCHKEY {        public final static String ACTION_KEYWORD = "key_word";        /*热搜开始的节点*/        public final static String ACTION_HOT_BEGIN = "Hotsearch";        /*热搜标题*/        public final static String ACTION_HOT_TITLE = "hotTitle";        /*热搜的结果*/        public final static String ACTION_KEY_RESULT = "key_result";        /*热搜的标题*/        public final static String ACTION_KEY_RESULT_TITLE = "key_title";        /*热搜的状态*/        public final static String ACTION_KEY_RESULT_STATUS = "key_status";    }    /**     * 商品值的ACTION     */    public static class ACTION_SHOPVALUES {        public static String ACTION_SHOPVALUES_START = "shop_page";        public static String ACTION_SHOPVALUES_TITLE = "shoptitle";        public static String ACTION_SHOPVALUES_BARCODE = "barcode";        public static String ACTION_SHOPVALUES_SPEC = "spec";        public static String ACTION_SHOPVALUES_WEIGHT = "weight";        public static String ACTION_SHOPVALUES_COMPANY = "company";/*价格对应的单位*/        public static String ACTION_SHOPVALUES_GRADE = "grade";        public static String ACTION_SHOPVALUES_ASCRIPTION = "ascription";        public static String ACTION_SHOPVALUES_PD = "pd";        public static String ACTION_SHOPVALUES_EXP = "exp";        public static String ACTION_SHOPVALUES_INFROM = "infrom";        public static String ACTION_SHOPVALUES_ONLYID = "onlyid";        public static String ACTION_SHOPVALUES_PRICE = "price";        public static String ACTION_SHOPVALUES_STATIC = "static";        public static String ACTION_SHOPVALUES_SU = "su";        public static String ACTION_SHOPVALUES_TP = "tp";        public static String ACTION_SHOPVALUES_BRAND = "brand";        public static String ACTION_SHOPVALUES_DLP = "dlp";//虚线的价格        public static String ACTION_SHOPVALUES_IMG = "img";        public static String ACTION_SHOPVALUES_RETAIL = "retail";/*商品的零售价格*/        public static String ACTION_SHOPVALUES_SPLITUNIT = "splitunit";/*最下的组合单位*/        public static String ACTION_SHOPVALUES_BUSINSS = "business";        public static String ACTION_SHOPVALUES_SHOPDOTTENLINE_PRICE = "dottenlineprice";        public static String ACTION_SHOPVALUES_PAYHOW = "payhow";/*购买数量*/        public static String ACTION_SHOPVALUES_LOGINSTATIC = "loginStatus";/*检测用户的状态*/        public static String ACTION_SHOPVALUES_TOPAYHOW = "sendTopayhow";/*判断是否用户发送过订货单了*/        public static String ACTION_SHOPVALUES_SAVESTATUS = "shopStatus";/*商品的历史的状态        用来取得商品订单保存的商品的状态*/        public static String ACTION_SHOPVALUES_STOCKNUMBER = "stocknumber";        /*商品的对接商家地址*/        public static String ACTION_SHOPVALUES_BRANDIMG = "brandimg";        public static String ACTION_SHOPVALUES_BRANDTITLE = "brandtitle";        public static String ACTION_SHOPVALUES_BRANDADDR = "brandaddr";        public static String ACTION_SHOPVALUES_BRANDCONTENT = "brandcontent";        public static String ACTION_SHOPVALUES_WEIGHTSYMBOL = "weightsymbol";        public static String ACTION_SHOPVALUES_VIP_TP = "viptp";/*加盟商的价格*/    }    /**     * 关于发货单的信息     */    public static class ACTION_SENDORDER_SYSTEM {        public static String ACTION_ORDERNUMBER = "ordernumber";/*订单号码*/        public static String ACTION_SEND_TIME = "orderTime";/*创建订单时间*/        public static String ACTION_ORDER_STATUS = "status";/*订单的状态*/        public static String ACTION_ORDER_PAGE_BEGIN = "OrderPage";/*开始节点*/        public static String ACTION_ORDER_DISTANCE = "distance";/*距离*/        public static String ACTION_ORDER_INVIPSTATUS = "inOrderVipstatus";/*下单时候的VIP状态*/        public static String ACTION_ORDER_SURPLUS = "surplusTime";/*剩余时间*/        public static String ACTION_ORDER_TIMESTAMP = "timeStamp";/*时间戳*/        public static String ACTION_ORDER_TOPAY_HOW = "sendTopayhow";/*发送订单的数量*/        public static String ACTION_ORDER_SHOP_STATUS = "shopStatus";/*下单时候的商品的状态*/    }    /**     * 公有判断订单的TAG     */    public static class ACTION_ORDER_STATUS {        public static String ORDER_NUMBER = "order_number";    }    /**     * 有关于积分的键     */    public static class ACTION_INTEGRAL {        public static String INTEGRAL_NUMBER = "integral_number";/*积分数量*/        public static String INTEGRAL_INFOR = "integral_infor";/*积分的来源*/        public static String INTEGRAL_STATUS = "integral_status";/*积分的状态  可以使用 不可以使用*/        /**         * 表示获取的积分         */        public static String INTEGRAL_USE = "use";/*能被使用的积分*/        public static String INTEGRAL_CANT = "cant";/*不能使用的积分*/        public static String INTEGRAL_FROZEN = "frozen";/*冻结的积分*/        public static String INTEGRAL_TOTAL = "total";/*总的积分*/    }    /**     * 获取推荐商品的TAG「     */    public static class ACTION_GET_RECOMMENDSHOP {        public static String GET_NUMBER = "get_number";    }    /**     * 关于商品的     */    public static class ACTION_SHOP {        public static final String ACTION = "";        public static final String ACTION_SHOP_ID = "only_id";        public static final String ACTION_ORDER_NUMBER = "order_number";    }    /**     * 关于系统的帮助信息     */    public static class ACTION_SYSTEMHELP {        public static final String ACTION_START = "item";/*开始解析的节点*/        public static final String ACTION_MSG = "msg";/*问题的标题*/        public static final String ACTION_CONTEXT = "context";/*解释的内容*/    }    /**     * 关于商品的购物车的查询ACTION     */    public static class ACTION_SHOP_CART {        public static final String ACITON = "ACTION";/*提交的Action的标题*/        public static final String ACTION_INSERT_ONES = "0";/*添加一个如果商品存在于购物车 就去修改购物车的定购数量*/        public static final String ACTION_DEL_ONES = "2";/*删除一个*/        public static final String ACTION_QUEYR_ALL = "1";/*查询全部*/    }    /**     * 关于短信处理的ACTION     */    public static class ACTION_SMS {        public static final String ACTION_PHONE = "phone_number";    }    /**     * 关于本地存储的一些ACTION     */    public static class ACTION_LOCALUSERPAGE {        public static final String ACTION_USER = "phone_user";        public static final String ACTION_TOKEN = "phone_token";        public static final String ACTION_BUSINESS = "isbusiness";        /*存储XML文件的ACTION*/        public static final String ACTION_LOCALUSERPAGE_BLANCE = "Blance";        public static final String ACTION_LOCALUSERPAGE_STATUS = "Status";        public static final String ACTION_LOCALUSERPAGE_VIPSTATUS = "Vipstatus";        public static final String ACTION_LOCALUSERPAGE_VIPOVERDATE = "VipoverDate";        public static final String ACTION_LOCALUSERPAGE_TOKEN = "Token";        public static final String ACTION_LOCALUSERPAGE_ACCOUNT = "Account";        public static final String ACTION_LOCALUSERPAGE_SHOPADDR = "Shopaddr";        public static final String ACTION_LOCALUSERPAGE_SHOPNAME = "Shopname";        public static final String ACTION_LOCALUSERPAGE_SHOPUSEPEOPLE = "Shopusepeople";        public static final String ACTION_LOCALUSERPAGE_SHOPTEL = "Shoptel";        public static final String ACTION_LOCALUSERPAGE_SHOPLONG = "Shoplong";        public static final String ACTION_LOCALUSERPAGE_SHOPLAT = "Shoplat";        public static final String ACTION_LOCALUSERPAGE_COUPON = "Coupon";/*优惠卷*/        public static final String ACTION_LOCALUSERPAGE_INTEGER = "integral";/*积分*/        public static final String ACTION_LOCALUSERPAGE_RECHARGERECORD = "Rechargerecord";/*充值记录*/        public static final String ACTION_LOCALUSERPAGE_TRANSACTION = "Transaction"; /*交易记录*/        public static final String ACTION_LOCALUSERPAGE_SENDSYSTEM = "Sendsystem";/*通知发货数量*/        public static final String ACTION_LOCALUSERPAGE_DELIVER = "Deliver";/*派送中的数量*/    }    /**     * 关于登录检查的ACTION     */    public static class ACTION_LOGIN {        public static final String ACTION_ISBUSINESS = "business";/*是否为商家*/        public static final String ACTION_PHONE = "phone_number";        public static final String ACTION_CODE = "phone_code";        public static final String ACTION_STATIC = "static";/*状态*/        public static final String ACTION_TOKEN = "token";/*token*/        /*从服务器中获取的数据的ACTION*/        public static final String ACTION_XML_NIACKNAME = "_niackname";        public static final String ACTION_XML_BALANCE = "_balance";        public static final String ACTION_XML_STATUS = "_status";        public static final String ACTION_XML_VIPSTATUS = "_vipstatus";        public static final String ACTION_XML_TOKEN = "_token";        public static final String ACTION_XML_LOGINSTATUS = "loginStatus";    }    /**     * 用户的表格的一些ACTION     */    public static class ACTION_USER {        public static final String ACTION_BUSINESS = "isbusiness";/*是否为商家*/    }    /**     * 网络处理的ACTION     */    public static class ACTION_HTTP {        public static final String ACTION_HTTP = "";        /**         * 要获取商品数据的方式         * 1         * 2         * 3         * 4         * 5         */        public static final String ACTION_SHOP_FOR_BARCODE = "1";        public static final String ACTION_SHOP_FOR_NAME = "2";        public static final String ACTION_SHOP_FOR_ONLYID = "4";        public static final String ACTION_SHOP_FOR_SEARCH = "3";        public static final String ACTION_SHOP_FOR_TYPE = "5";        public static final String ACTION_SHOP_GETSHOP_ACTION = "action";        public static final String ACTION_SHOP_SHOPMESSAGE = "shopmessage";    }    /**     * 产品分类的ACTION     */    public static class CLASSIFY_ACTION {        public static final String ACTION_INTO = "classify_title";        public static final String ACTION_CLASSIFY_NAME = "classify_name";        public static final String ACTION_CLASSIFY_VALUES = "classify_values";        /*大分类  标题对应*/    }    public static class JSON_ACTION {        public static final String ACTION_JSON_RETURN_MSG = "return_msg";    }}