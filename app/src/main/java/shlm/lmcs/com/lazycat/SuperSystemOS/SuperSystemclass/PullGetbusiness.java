package shlm.lmcs.com.lazycat.SuperSystemOS.SuperSystemclass;import android.content.Context;import android.util.Log;import org.xmlpull.v1.XmlPullParser;import java.lang.reflect.Array;import java.util.ArrayList;import java.util.List;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;/** * 拉取所有的要取货的合作商家信息 */public class PullGetbusiness {    private static ArrayList<Businesspages> ar_list = new ArrayList<Businesspages>();    private static String MSG = "PullGetbusiness.java[+]";    private static Businesspages _Businesspages;    private static String RETURN_CODE = "";/*返回状态码*/    public static void _start(final Context _context, final _onPulldone onPulldone) {        LocalValues.HTTP_ADDRS http_addrs = new LocalValues.HTTP_ADDRS(_context);        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getJavaXmlinstanceBuilder(true);        xmlInstance.overJavaDom();        Net.doPostXml(http_addrs.HTTP_ADDR_SUPER_PULLBUSINESS, new ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                _refreshDialog.dismiss();                Log.i(MSG, "超级系统拉取合作商家XML信息为:" + data.trim());                if (data.equals(LocalValues.NET_ERROR)) {                } else {                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                            ar_list.clear();                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            try {                                /*对接商家的表格开始*/                                if (tag.equals(LocalAction.ACTION_SUPERBUSINESS._BUSINESS)) {                                    _Businesspages = new Businesspages();                                }                                /*获取状态码*/                                if (tag.equals(LocalAction.ACTION_RETURNCODE)) {                                    RETURN_CODE = pullParser.nextText().trim();                                }                                /*获取对接商家的名称*/                                if (tag.equals(LocalAction.ACTION_SUPERBUSINESS._BUSINESSNAME)) {                                    _Businesspages._name = pullParser.nextText().trim();                                }                                /*获取对接商家的联系电话*/                                if (tag.equals(LocalAction.ACTION_SUPERBUSINESS._BUSINESSTEL)) {                                    _Businesspages._tel = pullParser.nextText().trim();                                }                                /*获取对接商家代码*/                                if (tag.equals(LocalAction.ACTION_SUPERBUSINESS._BUSINESSCODE)) {                                    _Businesspages._code = pullParser.nextText().trim();                                }                            } catch (Exception e) {                                Log.e(MSG, "超级系统处理合作商家取货XML数据失败:" + e.getMessage());                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            if (tag.equals(LocalAction.ACTION_SUPERBUSINESS._BUSINESS)) {                                ar_list.add(_Businesspages);                                _Businesspages = null;                            }                        }                        @Override                        public void onEndDocument() {                            if (RETURN_CODE.equals(LocalValues.NET_OK)) {                                onPulldone._onGet(ar_list);                            } else {                                onPulldone._onError();                            }                        }                    });                }            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                return Tools.getShowwait(_context);            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree().trim());    }    /**     * 表格     */    public static class Businesspages {        public String _name;        public String _tel;        public String _payhow;        public String _total;        public String _code;    }    /**     * 监听回调     */    public interface _onPulldone {        void _onGet(ArrayList<Businesspages> _Business);/*获取到表格*/        void _onError();/*系统错误*/    }}