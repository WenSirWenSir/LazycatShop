package shlm.lmcs.com.lazycat.SuperSystemOS;import android.content.Context;import android.util.Log;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import static shlm.lmcs.com.lazycat.TerminalSystemMO.Record.Useroperaction.System.Search.MSG;/** * 用来拉取用户的所有的订单记录 */public class PulloutOrders {	private static String VALUES_STATUS = "";	private final static String VALUES_PULLOK = "2";	private final static String VALUES_PULLERROR = "3";	private final static String VALUES_PULLNODATA = "4";	private static String ACTION_ORDERS = "orders";/*订单开始的节点*/	private static Orderpage orderpage;/*构造表格*/	private static ArrayList<Orderpage> ar_orderpages;/*集合表格*/	/**	 * 拉取数据	 *	 * @param _context	 * @param _onPull  监听对象	 */	public static void Pull(final Context _context, final onPull _onPull) {		LocalValues.HTTP_ADDRS http_addrs = new LocalValues.HTTP_ADDRS(_context);		XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);		xmlInstance.overDom();		Net.doPostXml(http_addrs.HTTP_ADDR_TERMINALSYSTEM_MO_PULLOUTORDERS,			new ProgramInterface() {			@Override			public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {				_refreshDialog.dismiss();				Log.i(MSG, "拉取数据获取到的信息为:" + data.trim());				if (data.trim().equals(LocalValues.NET_ERROR)) {					_onPull.onPullError();				} else {					XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());					xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {						@Override						public void onFaile() {						}						@Override						public void onStartDocument(String tag) {							ar_orderpages = new ArrayList<>();						}						@Override						public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {							try {								//获取状态信息								if (tag.equals(LocalAction.ACTION_RETURNCODE)) {									VALUES_STATUS = pullParser.nextText().trim();								}								/*page开始*/								if (tag.equals(ACTION_ORDERS)) {									/*开始节点*/									orderpage = new Orderpage();								}								/*订单的创建时间*/								if (tag.equals(LocalAction.ACTION_ORDER._TIME)) {									orderpage._time = pullParser.nextText().trim();								}								/*订单号*/								if (tag.equals(LocalAction.ACTION_ORDER._ORDERNUMBER)) {									orderpage._ordernumber = pullParser.nextText().trim();								}								/*积分*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_INTEGRAL)) {									orderpage._integral = pullParser.nextText().trim();								}								/*标题*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TITLE)) {									orderpage._title = pullParser.nextText().trim();								}								/*批发对应的单位*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_COMPANY)) {									orderpage._company = pullParser.nextText().trim();								}								/*批发价格*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TP)) {									orderpage._tp = pullParser.nextText().trim();								}								/*生产日期*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_EXP)) {									orderpage._exp = pullParser.nextText().trim();								}								/*唯一ID号*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ONLYID)) {									orderpage._onlyid = pullParser.nextText().trim();								}								/*条码*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_BARCODE)) {									orderpage._barcode = pullParser.nextText().trim();								}								/*规格*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SPEC)) {									orderpage._spec = pullParser.nextText().trim();								}								/*图片地址*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_IMG)) {									orderpage._img = pullParser.nextText().trim();								}								/*等级*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_GRADE)) {									orderpage._grade = pullParser.nextText().trim();								}								/*分割单位*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_SPLITUNIT)) {									orderpage._splitunt = pullParser.nextText().trim();								}								/*加盟商的价格*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_VIP_TP)) {									orderpage._viptp = pullParser.nextText().trim();								}								/*购买的数量*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_PAYHOW)) {									orderpage._payhow = pullParser.nextText().trim();								}								/*商品的原先的状态*/								if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_STATIC)) {									orderpage._shopstatus = pullParser.nextText().trim();								}								/*商品的订单状态*/								if (tag.equals(LocalAction.ACTION_ORDER._ORDERSTATUS)) {									orderpage._orderstatus = pullParser.nextText().trim();								}							} catch (Exception e) {								Log.e(MSG, "拉取数据解析错误:" + e.getMessage());							}						}						@Override						public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {							if (tag.equals(ACTION_ORDERS)) {								ar_orderpages.add(orderpage);								orderpage = null;							}						}						@Override						public void onEndDocument() {							switch (VALUES_STATUS) {								case VALUES_PULLERROR:									/*拉取数据失败*/									break;								case VALUES_PULLNODATA:									_onPull.onPullNull();									/*没有订单信息*/									break;								case VALUES_PULLOK:									_onPull.onPullok(ar_orderpages);									/*拉取成功*/									break;							}						}					});				}			}			@Override			public WaitDialog.RefreshDialog onStartLoad() {				return Tools.getShowwait(_context);			}			@Override			public void onFaile(String data, int code) {			}		}, xmlInstance.getXmlTree().trim());	}	public static class Orderpage {		public String _ordernumber;		public String _time;		public String _integral;		public String _title;		public String _company;		public String _tp;		public String _exp;		public String _onlyid;		public String _barcode;		public String _spec;		public String _img;		public String _grade;		public String _splitunt;		public String _viptp;		public String _payhow;		public String _shopstatus;		public String _orderstatus;	}	/**	 * 监听回调	 */	public interface onPull {		void onPullok(ArrayList<Orderpage> _pages);/*获取订单成功*/		void onPullError();/*获取订单失败*/		void onPullNull();/*没有订单信息*/	}}