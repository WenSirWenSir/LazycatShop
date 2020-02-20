package shlm.lmcs.com.lazycat.TerminalSystemOS;import android.content.Context;import android.util.Log;import org.xmlpull.v1.XmlPullParser;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;public class PagesHandlerShopvalues {    private static Context _context;    private static LocalValues.HTTP_ADDRS http_addrs;    private static XmlBuilder.XmlInstance xmlInstance;    private static String RETURN_CODE = "";    private static String MSG = "PagesHandlerShopvalues.java[+]";    private static ShopPages _ShopPages = new ShopPages();    /**     * 初始化解析结构     *     * @param action  请求的类型  使用关键字或者唯一ID号码     * @param keyword 请求关键字     * @param context 上下文对象     */    public void set(String action, String keyword, Context context) {        _context = context;        xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);        xmlInstance.setXmlTree(LocalAction.ACTION, action);        xmlInstance.setXmlTree(LocalAction.ACTION_GETSHOPPAGE.SEARCH_KEY, keyword);        xmlInstance.overDom();        Log.i(MSG, "提交信息XML为:" + xmlInstance.getXmlTree());        http_addrs = new LocalValues.HTTP_ADDRS(context);    }    public void _start(final onGet _onGet) {        Net.doPostXml(http_addrs.HTTP_ADDR_GET_SHOPVALUES, new ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                _refreshDialog.dismiss();                Log.i(MSG, "获取商品表格的XML信息:" + data.trim());                if (data.equals(LocalValues.NET_ERROR)) {                    /*服务器错误*/                } else {                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            try {                                /*状态*/                                if (tag.equals(LocalAction.ACTION_RETURNCODE)) {                                    RETURN_CODE = pullParser.nextText().trim();                                }                                /**                                 * 获取商品的表格节点信息                                 */                                /*商品标题*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TITLE)) {                                    _ShopPages.title = pullParser.nextText().trim();                                }                                /*商品条码*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_BARCODE)) {                                    _ShopPages.barcode = pullParser.nextText().trim();                                }                                /*商品归属*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ASCRIPTION)) {                                    _ShopPages.ascription = pullParser.nextText().trim();                                }                                /*商品的品牌*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_BRAND)) {                                    _ShopPages.brand = pullParser.nextText().trim();                                }                                /*商品的零售价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_RETAIL)) {                                    _ShopPages.retail = pullParser.nextText().trim();                                }                                /*商品单位*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_COMPANY)) {                                    _ShopPages.company = pullParser.nextText().trim();                                }                                /*商品保质期*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_EXP)) {                                    _ShopPages.exp = pullParser.nextText().trim();                                }                                /*商品的等级*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_GRADE)) {                                    _ShopPages.grade = pullParser.nextText().trim();                                }                                /*商品的产地*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_INFROM)) {                                    _ShopPages.infrom = pullParser.nextText().trim();                                }                                /*商品唯一标识 */                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ONLYID)) {                                    _ShopPages.onlyid = pullParser.nextText().trim();                                }                                /*商品的生产日期期*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_PD)) {                                    _ShopPages.pd = pullParser.nextText().trim();                                }                                /*商品的规格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SPEC)) {                                    _ShopPages.spec = pullParser.nextText().trim();                                }                                /*商品的状态*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_STATIC)) {                                    _ShopPages._static = pullParser.nextText().trim();                                }                                /*商品的起订数量*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SU)) {                                    _ShopPages.su = pullParser.nextText().trim();                                }                                /*商品的批发价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TP)) {                                    _ShopPages.tp = pullParser.nextText().trim();                                }                                /*商品的净重*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_WEIGHT)) {                                    _ShopPages.weight = pullParser.nextText().trim();                                }                                /*商品的虚线的价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_DLP)) {                                    _ShopPages.dlp = pullParser.nextText().trim();                                }                                /*商品的图片地址*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_IMG)) {                                    _ShopPages.img = pullParser.nextText().trim();                                }                                /*商品的最低的组合单位*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SPLITUNIT)) {                                    _ShopPages.splitUnit = pullParser.nextText().trim();                                }                                /*商品的对接商家*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_BUSINSS)) {                                }                                /*如果商家登录了 就获取这个值 来判断商品购买数量*//*                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TOPAYHOW)) {                                    St_payhow = pullParser.nextText().trim();                                }*/                                /*获取商品的库存数量*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_STOCKNUMBER)) {                                    _ShopPages.shopStocknumber = pullParser.nextText().trim();                                }                                /*商品的重量符号*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_WEIGHTSYMBOL)) {                                    _ShopPages.weightSymbol = pullParser.nextText().trim();                                }                                /*积分 这里的积分不是不需要乘以10的*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_INTEGRAL)) {                                    _ShopPages.integral = pullParser.nextText().trim();                                }                            } catch (Exception e) {                                Log.e(MSG, "解析商品表格失败");                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                        }                        @Override                        public void onEndDocument() {                            try {                                if (RETURN_CODE.equals("")) {                                    _onGet._onError();                                } else if (RETURN_CODE.equals(LocalValues.NET_ERROR)) {                                    _onGet._onError();                                } else if (RETURN_CODE.equals(LocalValues.NET_OK)) {                                    _onGet._onGet(_ShopPages);                                } else if (RETURN_CODE.equals(LocalValues.VALUES_USERCENTER.LOGIN_ERROR)) {                                    _onGet._onNologin();                                } else if (RETURN_CODE.equals(LocalValues.VALUES_GETSHOPPAGE.VALUES_NOT_SHOP)) {                                    _onGet._onNoshop();/*没有商品的回调事件*/                                }                            } catch (Exception e) {                                if (_onGet != null) {                                    _onGet._onThiserror(e.getMessage().trim());                                }                            }                        }                    });                }            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                return Tools.getShowwait(_context);            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree().trim());    }    /**     * 接口回调地址     */    public interface onGet {        void _onGet(ShopPages _Shopage);/*成功*/        void _onError();/*失败*/        void _onNologin();/*没有登录*/        void _onThiserror(String _msg);/*这个类的错误*/        void _onNoshop();/*没有这个商品了*/    }    /**     * 商品表格     */    public static class ShopPages {        public String title;/*标题*/        public String barcode;/*条码*/        public String spec;/*规格*/        public String weight;/*净重*/        public String company;/*单位*/        public String grade;/*等级*/        public String ascription;/*归属*/        public String pd;/*生产日期*/        public String exp;/*保质期*/        public String infrom;/*原场地*/        public String onlyid;/*唯一表示符号*/        public String retail;/*零售价格*/        public String business;/*商品的对接供货商家*/        public String shopStocknumber;/*商品的库存*/        public String weightSymbol;        public String _static;/*状态*/        public String su;/*起订*/        public String tp;/*批发价格*/        public String brand;/*品牌*/        public String dlp;/*虚线的价格*/        public String img;/*图片的地址*/        public String splitUnit;/*最小的的组合单位*/        public Boolean isOnlyVipPay;        public Boolean isPaythis;/*判断是否购买*/        public String payHow;/*购买数量*/        public Boolean shoplike = true;/*是否收藏*/        public String integral;    }}