package shlm.lmcs.com.lazycat.FreightOS;import android.content.Context;import android.util.Log;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;/** * 拉取服务器物流数据信息 */public class Freight {    private static String MSG = "Freight.java[+]";    public final static String STATUS_OK = "0";    public final static String STATUS_SEND = "1";/*已经提交*/    public final static String STATUS_PRINT = "2";/*打印成功*/    public final static String STATUS_DELIVERY = "3";/*正在派送*/    public final static String STATUS_NOT_SHOP = "4";/*没有商品*/    public final static String STATUS_RETURNCODE_OK = "0";/*获取订单正常*/    public final static String STATUS_RETURNCODE_ERROR = "1";/*获取订单失败*/    /**     * 服务器获取数据信息     */    private static String VALUES_RETURNCODE = "";    /**     * 服务器返回的数据信息的TAG     */    private static String ACTION_RETURN_CODE = "return_code";/*获取信息状态*/    private static String ACTION_FREIGHT_BUSSINES = "freight_business";/*物流运送方*/    private static String ACTION_FREIGHT_STATUS = "freight_status";/*物流状态*/    private static String ACTION_FREIGHT_TEL = "freight_tel";/*物流电话*/    private static String ACTION_MSG_START = "msg_log";/*物流信息开始的节点*/    private static String ACTION_MSG_MSG = "msg";/*物流信息*/    private static String ACTION_MSG_TIME = "time";/*时间*/    private static String ACTION_MSG_STATUS = "status";/*单条物流信息的状态*/    private static FreightLog _freightLog = new FreightLog();    private static _Log _log;    public static void _pulloutFreight(final Context _context, String _orderNumber, final onFreight _onFreight) {        LocalValues.HTTP_ADDRS http_addrs = new LocalValues.HTTP_ADDRS(_context);        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);        xmlInstance.setXmlTree(LocalAction.ACTION_ORDER._ORDERNUMBER, _orderNumber);        xmlInstance.overDom();        Net.doPostXml(http_addrs.HTTP_ADDR_FREIGHTOS_FREIGHT, new ProgramInterface() {            @Override            public void onSucess(String data, int code, final WaitDialog.RefreshDialog _refreshDialog) {                _refreshDialog.dismiss();                Log.i(MSG, "物流拉取服务器数据回传:" + data.trim());                if (data.trim().equals(LocalValues.NET_ERROR)) {                    /*错误信息回调*/                } else {                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                            _freightLog.ar_msg.clear();                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            try {                                if (tag.equals(ACTION_RETURN_CODE)) {                                    VALUES_RETURNCODE = pullParser.nextText().trim();                                }                                /*物流公司*/                                if (tag.equals(ACTION_FREIGHT_BUSSINES)) {                                    _freightLog._freightBusiness = pullParser.nextText().trim();                                }                                /*物流官方电话*/                                if (tag.equals(ACTION_FREIGHT_TEL)) {                                    _freightLog._freightTel = pullParser.nextText().trim();                                }                                /*订单号*/                                if (tag.equals(LocalAction.ACTION_ORDER._ORDERNUMBER)) {                                    _freightLog._orderNumber = pullParser.nextText().trim();                                }                                /*物流状态*/                                if (tag.equals(ACTION_FREIGHT_STATUS)) {                                    _freightLog._freightStatus = pullParser.nextText().trim();                                }                                /*商品的图片地址*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_IMG)) {                                    _freightLog._shopImg = pullParser.nextText().trim();                                }                                /*初始化信息的表格*/                                if (tag.equals(ACTION_MSG_START)) {                                    _log = new _Log();                                }                                /*物流的信息*/                                if (tag.equals(ACTION_MSG_MSG)) {                                    _log._msg = pullParser.nextText().trim();                                }                                /*物流的时间*/                                if (tag.equals(ACTION_MSG_TIME)) {                                    _log._time = pullParser.nextText().trim();                                }                                /*物流的状态*/                                if (tag.equals(ACTION_MSG_STATUS)) {                                    _log._status = pullParser.nextText().trim();                                }                            } catch (Exception e) {                                Log.i(MSG, "物流拉取服务器数据解析失败:" + e.getMessage());                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            if (tag.equals(ACTION_MSG_START)) {                                _freightLog.ar_msg.add(_log);                                _log = null;                            }                        }                        @Override                        public void onEndDocument() {                            switch (VALUES_RETURNCODE) {                                /*获取订单成功*/                                case STATUS_RETURNCODE_OK:                                    _onFreight._onPullOk(_freightLog);                                    break;                                /*获取订单失败*/                                case STATUS_RETURNCODE_ERROR:                                    _onFreight._onPullError();                                    break;                            }                        }                    });                }            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                return Tools.getShowwait(_context);            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree().trim());    }    /**     * 监听回调     */    public interface onFreight {        void _onPullOk(FreightLog _freightLog);/*获取订单成功*/        void _onPullError();/*获取信息错误*/    }    /**     * 物流信息     */    public static class FreightLog {        public String _freightBusiness;        public String _freightTel;        public String _orderNumber;        public String _freightStatus;        public String _shopImg;        public ArrayList<_Log> ar_msg = new ArrayList<>();    }    public static class _Log {        public String _msg;/*物流信息*/        public String _time;/*时间*/        public String _status;/*状态*/    }}