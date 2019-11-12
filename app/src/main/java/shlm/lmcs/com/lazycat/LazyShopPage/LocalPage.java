package shlm.lmcs.com.lazycat.LazyShopPage;/** * 存储商品的参数信息  或者模块值的信息 */public class LocalPage {    private static BigCenterHeadpageInstance bigCenterHeadpageInstance = new            BigCenterHeadpageInstance();    private static SecondSmallNavAPage secondSmallNavPage = new SecondSmallNavAPage();    private static ThreeNavPageInstance threeNavPageInstance = new ThreeNavPageInstance();    /**     * 获取第三个导航的界面的处理器     * @return     */    public static ThreeNavPageInstance getThreeNavPageInstance() {        return threeNavPageInstance;    }    /**     * 获取一个界面第中间导航的处理器     *     * @return     */    public static BigCenterHeadpageInstance getBigCenterHeadpageInstance() {        return bigCenterHeadpageInstance;    }    /**     * 获取一个界面第二个导航的处理器     *     * @return     */    public static SecondSmallNavAPage getSecondSmallNavAPageInstance() {        return secondSmallNavPage;    }    public static class UserPage {        private String UserNiackname;/*用户的别称*/        private String UserBalance;/*用户余额*/        private String UserStatus;/*用户的状态*/        private String UserVipStatus;/*用户是否为VIP*/    }    /**     * 定义一个类  用来存储首页中SecondSmallNav中的数据信息     */    public static class SecondSmallNavAPage {        /**         * 第一个横向的图片的参数值         */        private String SecondSmallAimgUrl;/*小图片的图片地址*/        private String SecondSmallAClickUrl;/*点击的图片地址*/        private String SecondSmallAtitle;/*小图片第一个标题*/        private String SecondSmallAcontext;/*第一个小图片的介绍*/        private String SecondSmallAtitleColor;/*第一个小图片的标题的颜色*/        private String SecondSmallAcontextColor;/*第一个小图片的介绍的颜色*/        /**         * 第二个横向的图片的参数值         */        private String SecondSmallBimgUrl;        private String SecondSmallBClickUrl;        private String SecondSmallBtitle;        private String SecondSmallBtitleColor;        private String SecondSmallBcontext;        private String SecondSmallBcontextColor;        /**         * 第一个横向的NAV的XmlAction         */        public static String BEGIN_XML = "SecondSmallNavA";        public static String XML_TAG_NAVA_IMG = "navA_img";        public static String XML_TAG_NAVA_URL = "navA_url";        public static String XML_TAG_NAVA_TITLE = "navA_title";        public static String XML_TAG_NAVA_CONTEXT = "navA_context";        public static String XML_TAG_NAVA_TITLECOLOR = "navA_titleColor";        public static String XML_TAG_NAVA_CONTEXTCOLOR = "navA_contextColor";        /**         * 第二个横向的NAV的XmlAction         */        public static String XML_TAG_NAVB_IMG = "navB_img";        public static String XML_TAG_NAVB_URL = "navB_url";        public static String XML_TAG_NAVB_TITLE = "navB_title";        public static String XML_TAG_NAVB_CONTEXT = "navB_context";        public static String XML_TAG_NAVB_TITLECOLOR = "navB_titleColor";        public static String XML_TAG_NAVB_CONTEXTCOLOR = "navB_contextColor";        /**         * 第三个竖向的ImageView控件的数值         */        private String SecondSmallCimgUrl;/*竖向图片的地址*/        private String SecondSmallCClickUrl;/*点击图片的地址*/        private String SecondSmallCtitle;/*标题*/        private String SecondSmallCcontext;/*内容*/        private String SecondSmallCtitleColor;/*标题的颜色*/        private String SecondSmallCcontextColor;/*内容的颜色*/        /**         * 第四个竖向的ImageView控件的数值         */        private String SecondSmallDimgUrl;/*竖向的图片地址*/        private String SecondSmallDClickUrl;/*点击图片的地址*/        private String SecondSmallDtitle;/*标题*/        private String SecondSmallDcontext;/*内容*/        private String SecondSmallDtitleColor;/*标题的颜色*/        private String SecondSmallDcontextColor;/*内容的颜色*/        /**         * Xml的TAG         */        public static String XML_TAG_NAVC_IMG = "navC_img";        public static String XML_TAG_NAVC_URL = "navC_url";        public static String XML_TAG_NAVC_TITLE = "navC_title";        public static String XML_TAG_NAVC_CONTEXT = "navC_context";        public static String XML_TAG_NAVC_TITLECOLOR = "navC_titleColor";        public static String XML_TAG_NAVC_CONTEXTCOLOR = "navC_contextColor";        /**         * 第四个竖向的图片的XML参数         */        public static String XML_TAG_NAVD_IMG = "navD_img";        public static String XML_TAG_NAVD_URL = "navD_url";        public static String XML_TAG_NAVD_TITLE = "navD_title";        public static String XML_TAG_NAVD_CONTEXT = "navD_context";        public static String XML_TAG_NAVD_TITLECOLOR = "navD_titleColor";        public static String XML_TAG_NAVD_CONTEXTCOLOR = "navD_contextColor";        public String getSecondSmallAtitleColor() {            return SecondSmallAtitleColor;        }        public void setSecondSmallAtitleColor(String secondSmallAtitleColor) {            SecondSmallAtitleColor = secondSmallAtitleColor;        }        public String getSecondSmallAcontextColor() {            return SecondSmallAcontextColor;        }        public void setSecondSmallAcontextColor(String secondSmallAcontextColor) {            SecondSmallAcontextColor = secondSmallAcontextColor;        }        public String getSecondSmallAtitle() {            return SecondSmallAtitle;        }        public void setSecondSmallAtitle(String secondSmallAtitle) {            SecondSmallAtitle = secondSmallAtitle;        }        public String getSecondSmallAcontext() {            return SecondSmallAcontext;        }        public void setSecondSmallAcontext(String secondSmallAcontext) {            SecondSmallAcontext = secondSmallAcontext;        }        public String getSecondSmallAClickUrl() {            return this.SecondSmallAClickUrl;        }        public void setSecondSmallAClickUrl(String secondSmallAClickUrl) {            this.SecondSmallAClickUrl = secondSmallAClickUrl;        }        public String getSecondSmallAimgUrl() {            return this.SecondSmallAimgUrl;        }        public void setSecondSmallAimgUrl(String secondSmallAimgUrl) {            this.SecondSmallAimgUrl = secondSmallAimgUrl;        }        public String getSecondSmallCimgUrl() {            return SecondSmallCimgUrl;        }        public void setSecondSmallCimgUrl(String secondSmallCimgUrl) {            SecondSmallCimgUrl = secondSmallCimgUrl;        }        public String getSecondSmallCClickUrl() {            return SecondSmallCClickUrl;        }        public void setSecondSmallCClickUrl(String secondSmallCClickUrl) {            SecondSmallCClickUrl = secondSmallCClickUrl;        }        public String getSecondSmallCtitle() {            return SecondSmallCtitle;        }        public void setSecondSmallCtitle(String secondSmallCtitle) {            SecondSmallCtitle = secondSmallCtitle;        }        public String getSecondSmallCcontext() {            return SecondSmallCcontext;        }        public void setSecondSmallCcontext(String secondSmallCcontext) {            SecondSmallCcontext = secondSmallCcontext;        }        public String getSecondSmallCtitleColor() {            return SecondSmallCtitleColor;        }        public void setSecondSmallCtitleColor(String secondSmallCtitleColor) {            SecondSmallCtitleColor = secondSmallCtitleColor;        }        public String getSecondSmallCcontextColor() {            return SecondSmallCcontextColor;        }        public void setSecondSmallCcontextColor(String secondSmallCcontextColor) {            SecondSmallCcontextColor = secondSmallCcontextColor;        }        public String getSecondSmallDimgUrl() {            return SecondSmallDimgUrl;        }        public void setSecondSmallDimgUrl(String secondSmallDimgUrl) {            SecondSmallDimgUrl = secondSmallDimgUrl;        }        public String getSecondSmallDClickUrl() {            return SecondSmallDClickUrl;        }        public void setSecondSmallDClickUrl(String secondSmallDClickUrl) {            SecondSmallDClickUrl = secondSmallDClickUrl;        }        public String getSecondSmallDtitle() {            return SecondSmallDtitle;        }        public void setSecondSmallDtitle(String secondSmallDtitle) {            SecondSmallDtitle = secondSmallDtitle;        }        public String getSecondSmallDcontext() {            return SecondSmallDcontext;        }        public void setSecondSmallDcontext(String secondSmallDcontext) {            SecondSmallDcontext = secondSmallDcontext;        }        public String getSecondSmallDtitleColor() {            return SecondSmallDtitleColor;        }        public void setSecondSmallDtitleColor(String secondSmallDtitleColor) {            SecondSmallDtitleColor = secondSmallDtitleColor;        }        public String getSecondSmallDcontextColor() {            return SecondSmallDcontextColor;        }        public void setSecondSmallDcontextColor(String secondSmallDcontextColor) {            SecondSmallDcontextColor = secondSmallDcontextColor;        }        public String getSecondSmallBimgUrl() {            return SecondSmallBimgUrl;        }        public void setSecondSmallBimgUrl(String secondSmallBimgUrl) {            SecondSmallBimgUrl = secondSmallBimgUrl;        }        public String getSecondSmallBClickUrl() {            return SecondSmallBClickUrl;        }        public void setSecondSmallBClickUrl(String secondSmallBClickUrl) {            SecondSmallBClickUrl = secondSmallBClickUrl;        }        public String getSecondSmallBtitle() {            return SecondSmallBtitle;        }        public void setSecondSmallBtitle(String secondSmallBtitle) {            SecondSmallBtitle = secondSmallBtitle;        }        public String getSecondSmallBtitleColor() {            return SecondSmallBtitleColor;        }        public void setSecondSmallBtitleColor(String secondSmallBtitleColor) {            SecondSmallBtitleColor = secondSmallBtitleColor;        }        public String getSecondSmallBcontext() {            return SecondSmallBcontext;        }        public void setSecondSmallBcontext(String secondSmallBcontext) {            SecondSmallBcontext = secondSmallBcontext;        }        public String getSecondSmallBcontextColor() {            return SecondSmallBcontextColor;        }        public void setSecondSmallBcontextColor(String secondSmallBcontextColor) {            SecondSmallBcontextColor = secondSmallBcontextColor;        }    }    /**     * 中间的图片的取值类     */    public static class BigCenterHeadpageInstance {        public static String XML_TAG_CENTER_HEAD_START = "BigCenterHead";        public static String XML_TAG_CENTER_HEAD_IMG = "BigCenterHeadimg";        /**         * 存储的数值         */        private String Headimg;        public String getHeadimg() {            return this.Headimg;        }        public void setHeadimg(String headimg) {            this.Headimg = headimg;        }    }    public static class ThreeNavPageInstance {        public static String XML_TAG_THREE_NAVA_START = "ThreeNav";        /**         * 第一个取值参数         */        public static String XML_TAG_THREE_NAVA_IMG = "ThreeNavAimg";        public static String XML_TAG_THREE_NAVA_URL = "ThreeNavAUrl";        public static String XML_TAG_THREE_NAVA_BIGTITLE = "ThreeNavABigtitle";        public static String XML_TAG_THREE_NAVA_BIGTITLE_COLOR = "ThreeNavABigtitleColor";        public static String XML_TAG_THREE_NAVA_SMALLTITLE = "ThreeNavASmalltitle";        public static String XML_TAG_THREE_NAVA_SMALLTITLE_COLOR = "ThreeNavASmalltitleColor";        /**         * 第二个取值参数         */        public static String XML_TAG_THREE_NAVB_IMG = "ThreeNavBimg";        public static String XML_TAG_THREE_NAVB_URL = "ThreeNavBUrl";        public static String XML_TAG_THREE_NAVB_BIGTITLE = "ThreeNavBBigtitle";        public static String XML_TAG_THREE_NAVB_BIGTITLE_COLOR = "ThreeNavBBigtitleColor";        public static String XML_TAG_THREE_NAVB_SMALLTITLE = "ThreeNavBSmalltitle";        public static String XML_TAG_THREE_NAVB_SMALLTITLE_COLOR = "ThreeNavBSmalltitleColor";        /**         * 第三个取值参数         */        public static String XML_TAG_THREE_NAVC_IMG = "ThreeNavCimg";        public static String XML_TAG_THREE_NAVC_URL = "ThreeNavCUrl";        public static String XML_TAG_THREE_NAVC_BIGTITLE = "ThreeNavCBigtitle";        public static String XML_TAG_THREE_NAVC_BIGTITLE_COLOR = "ThreeNavCBigtitleColor";        public static String XML_TAG_THREE_NAVC_SMALLTITLE = "ThreeNavCSmalltitle";        public static String XML_TAG_THREE_NAVC_SMALLTITLE_COLOR = "ThreeNavCSmalltitleColor";        /**         * 保存参数         */        private String NavAimg;        private String NavAurl;        private String NavAbigTitle;        private String NavAbigTitlecolor;        private String NavASmalltitle;        private String NavASmalltitleColor;        private String NavBimg;        private String NavBurl;        private String NavBbigTitle;        private String NavBbigTitlecolor;        private String NavBSmalltitle;        private String NavBSmalltitleColor;        private String NavCimg;        private String NavCurl;        private String NavCbigTitle;        private String NavCbigTitlecolor;        private String NavCSmalltitle;        private String NavCSmalltitleColor;        public String getNavAbigTitle() {            return NavAbigTitle;        }        public void setNavAbigTitle(String navAbigTitle) {            NavAbigTitle = navAbigTitle;        }        public String getNavAbigTitlecolor() {            return NavAbigTitlecolor;        }        public void setNavAbigTitlecolor(String navAbigTitlecolor) {            NavAbigTitlecolor = navAbigTitlecolor;        }        public String getNavASmalltitle() {            return NavASmalltitle;        }        public void setNavASmalltitle(String navASmalltitle) {            NavASmalltitle = navASmalltitle;        }        public String getNavASmalltitleColor() {            return NavASmalltitleColor;        }        public void setNavASmalltitleColor(String navASmalltitleColor) {            NavASmalltitleColor = navASmalltitleColor;        }        public String getNavBimg() {            return NavBimg;        }        public void setNavBimg(String navBimg) {            NavBimg = navBimg;        }        public String getNavBurl() {            return NavBurl;        }        public void setNavBurl(String navBurl) {            NavBurl = navBurl;        }        public String getNavBbigTitle() {            return NavBbigTitle;        }        public void setNavBbigTitle(String navBbigTitle) {            NavBbigTitle = navBbigTitle;        }        public String getNavBbigTitlecolor() {            return NavBbigTitlecolor;        }        public void setNavBbigTitlecolor(String navBbigTitlecolor) {            NavBbigTitlecolor = navBbigTitlecolor;        }        public String getNavBSmalltitle() {            return NavBSmalltitle;        }        public void setNavBSmalltitle(String navBSmalltitle) {            NavBSmalltitle = navBSmalltitle;        }        public String getNavBSmalltitleColor() {            return NavBSmalltitleColor;        }        public void setNavBSmalltitleColor(String navBSmalltitleColor) {            NavBSmalltitleColor = navBSmalltitleColor;        }        public String getNavCimg() {            return NavCimg;        }        public void setNavCimg(String navCimg) {            NavCimg = navCimg;        }        public String getNavCurl() {            return NavCurl;        }        public void setNavCurl(String navCurl) {            NavCurl = navCurl;        }        public String getNavCbigTitle() {            return NavCbigTitle;        }        public void setNavCbigTitle(String navCbigTitle) {            NavCbigTitle = navCbigTitle;        }        public String getNavCbigTitlecolor() {            return NavCbigTitlecolor;        }        public void setNavCbigTitlecolor(String navCbigTitlecolor) {            NavCbigTitlecolor = navCbigTitlecolor;        }        public String getNavCSmalltitle() {            return NavCSmalltitle;        }        public void setNavCSmalltitle(String navCSmalltitle) {            NavCSmalltitle = navCSmalltitle;        }        public String getNavCSmalltitleColor() {            return NavCSmalltitleColor;        }        public void setNavCSmalltitleColor(String navCSmalltitleColor) {            NavCSmalltitleColor = navCSmalltitleColor;        }        public String getNavAimg() {            return NavAimg;        }        public void setNavAimg(String navAimg) {            NavAimg = navAimg;        }        public String getNavAurl() {            return NavAurl;        }        public void setNavAurl(String navAurl) {            NavAurl = navAurl;        }    }}