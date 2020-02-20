package shlm.lmcs.com.lazycat.SuperSystemOS.SuperSystemclass;import android.content.Context;import android.util.Log;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;/** * 拉取一个商品里面的全部用户 */public class PullshopsUsers {    private static String MSG = "PullshopsUsers.java[+]";    private static ArrayList<_Userpages> ar_users;    private static _Userpages userpages;    private static _Goodpage goodpage;    public static void _pull(final Context _context, String _onlyId, final _onPullusers onPullusers) {        LocalValues.HTTP_ADDRS http_addrs = new LocalValues.HTTP_ADDRS(_context);        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ONLYID, _onlyId);        xmlInstance.overDom();        Net.doPostXml(http_addrs.HTTP_ADDR_SUPER_PULLSHOPS_USERS, new ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                _refreshDialog.dismiss();                Log.i(MSG, "拉取一个商品的全部客户XML数据信息:" + data.trim());                if (data.equals(LocalValues.NET_ERROR)) {                    onPullusers._onError();                } else {                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                            ar_users = new ArrayList<>();                            goodpage = new _Goodpage();                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            try {                                /*取货商品的标题*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TITLE)) {                                    goodpage._title = pullParser.nextText().trim();                                }                                /*取货商品的取货数量*/                                if (tag.equals(LocalAction.ACTION_SUPERPICKSHOPS._PICKSHOPSPAYHOW)) {                                    goodpage._payhow = pullParser.nextText().trim();                                }                                /*取货商品的分割单位*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SPLITUNIT)) {                                    goodpage._splitunit = pullParser.nextText().trim();                                }                                /*取货商品的价格对应的单位*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_COMPANY)) {                                    goodpage._company = pullParser.nextText().trim();                                }                                /*取货商品的规格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SPEC)) {                                    goodpage._spec = pullParser.nextText().trim();                                }                                /*单件的价格*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TP)) {                                    goodpage._tp = pullParser.nextText().trim();                                }                                /*商品图片地址*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_IMG)) {                                    goodpage._img = pullParser.nextText().trim();                                }                                /**                                 * 开始获取多个表格(用户)                                 */                                if (tag.equals(LocalAction.ACTION_SUPERPICKINFOR_USERS._INFOR_USERPAGE)) {                                    userpages = new _Userpages();                                }                                /*获取名称*/                                if (tag.equals(LocalAction.ACTION_SUPERBUSINESS._BUSINESSNAME)) {                                    userpages._name = pullParser.nextText().trim();                                }                                /*获取电话*/                                if (tag.equals(LocalAction.ACTION_SUPERBUSINESS._BUSINESSTEL)) {                                    userpages._tel = pullParser.nextText().trim();                                }                                /*获取地址*/                                if (tag.equals(LocalAction.ACTION_SUPERBUSINESS._BUSINESSADDR)) {                                    userpages._addr = pullParser.nextText().trim();                                }                                /*获取所有人*/                                if (tag.equals(LocalAction.ACTION_SUPERBUSINESS._BUSINESSPEOPLE)) {                                    userpages._people = pullParser.nextText().trim();                                }                                /*获取要取货数量*/                                if (tag.equals(LocalAction.ACTION_SUPERBUSINESS._BUSINESSPAYHOW)) {                                    userpages._payhow = pullParser.nextText().trim();                                }                            } catch (Exception e) {                                Log.i(MSG, "解析商品的全部商家XML数据失败:" + e.getMessage());                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            if (tag.equals(LocalAction.ACTION_SUPERPICKINFOR_USERS._INFOR_USERPAGE)) {                                ar_users.add(userpages);                                userpages = null;                            }                        }                        @Override                        public void onEndDocument() {                            onPullusers._onGet(ar_users, goodpage);                        }                    });                }            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                return Tools.getShowwait(_context);            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree().trim());    }    public static class _Userpages {        public String _name;        public String _tel;        public String _addr;        public String _people;        public String _payhow;    }    public static class _Goodpage {        public String _title;/*标题*/        public String _payhow;/*订购数量*/        public String _splitunit;/*拆分单位*/        public String _company;/*价格对应的单位*/        public String _spec;/*规格*/        public String _tp;/*单件的价格*/        public String _img;/*商品图片地址*/    }    /**     * 监听事件     */    public interface _onPullusers {        void _onGet(ArrayList<_Userpages> _users, _Goodpage goodpage);/*获取数据完成*/        void _onError();/*获取数据失败*/    }}