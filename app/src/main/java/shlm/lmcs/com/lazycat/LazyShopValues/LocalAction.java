package shlm.lmcs.com.lazycat.LazyShopValues;/** * 字节对 ACTION */public class LocalAction {    /**     * 关于短信处理的ACTION     */    public static class ACTION_SMS {        public static final String ACTION_PHONE = "phone_number";    }    /**     * 关于本地存储的一些ACTION     */    public static class ACTION_LOCALUSERPAGE {        public static final String ACTION_USER = "phone_user";        public static final String ACTION_TOKEN = "phone_token";        public static final String ACTION_BUSINESS = "isbusiness";    }    /**     * 关于登录检查的ACTION     */    public static class ACTION_LOGIN {        public static final String ACTION_ISBUSINESS = "business";/*是否为商家*/        public static final String ACTION_PHONE = "phone_number";        public static final String ACTION_CODE = "phone_code";        public static final String ACTION_STATIC = "static";/*状态*/        public static final String ACTION_TOKEN = "token";/*token*/    }    /**     * 用户的表格的一些ACTION     */    public static class ACTION_USER {        public static final String ACTION_BUSINESS = "isbusiness";/*是否为商家*/    }    /**     * 网络处理的ACTION     */    public static class ACTION_HTTP {        public static final String ACTION_HTTP = "";        /**         * 要获取商品数据的方式         * 1         * 2         * 3         * 4         * 5         */        public static final String ACTION_SHOP_FOR_BARCODE = "1";        public static final String ACTION_SHOP_FOR_NAME = "2";        public static final String ACTION_SHOP_FOR_ONLYID = "4";        public static final String ACTION_SHOP_FOR_SEARCH = "3";        public static final String ACTION_SHOP_FOR_TYPE = "5";        public static final String ACTION_SHOP_GETSHOP_ACTION = "action";        public static final String ACTION_SHOP_SHOPMESSAGE = "shopmessage";    }    /**     * 产品分类的ACTION     */    public static class CLASSIFY_ACTION {        public static final String ACTION_INTO = "classify_title";        /*大分类  标题对应*/        public static final String ACTION_SHOP_SNACKS = "snacks";/*休闲食品*/        public static final String ACTION_SHOP_MILK = "milk";/*水乳饮品*/        public static final String ACTION_SHOP_AMENITY = "amenity";/*方便食品*/        public static final String ACTION_SHOP_DRINKS = "drinks";/*酒水宴席*/        public static final String ACTION_SHOP_SEASON = "season";/*粮油调味*/        public static final String ACTION_SHOP_STATIONERY = "stationery";/*文具办公*/        public static final String ACTION_SHOP_ELECTRIC = "electric";/*家用电器*/        public static final String ACTION_SHOP_NUMERICAL = "numerical";/*数码配件*/        public static final String ACTION_SHOP_CONTRACEPTIVE = "contraceptive";/*计生用品*/        public static final String ACTION_SHOP_HEALTH = "health";/*冲调建生*/        public static final String ACTION_SHOP_PAPER = "paper";/*纸品卫生*/        public static final String ACTION_SHOP_BEAUTY = "beauty";/*个体美妆*/        public static final String ACTION_SHOP_DAILY = "daily";/*日化清洁*/        public static final String ACTION_SHOP_TOWEL = "towel";/*毛巾护理*/        public static final String ACTION_SHOP_WOMAN = "woman";/*妇女卫生*/        public static final String ACTION_SHOP_DRAY = "dray";/*干货精选*/    }}