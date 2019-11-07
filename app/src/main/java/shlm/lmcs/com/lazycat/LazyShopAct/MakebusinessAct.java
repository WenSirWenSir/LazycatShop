package shlm.lmcs.com.lazycat.LazyShopAct;import android.Manifest;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.os.Bundle;import android.util.Log;import android.view.View;import android.widget.EditText;import android.widget.TextView;import android.widget.Toast;import com.tencent.map.geolocation.TencentLocation;import com.tencent.map.geolocation.TencentLocationListener;import com.tencent.map.geolocation.TencentLocationManager;import com.tencent.map.geolocation.TencentLocationRequest;import org.xmlpull.v1.XmlPullParser;import java.util.Timer;import java.util.TimerTask;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;import static shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools.isPermission;public class MakebusinessAct extends LazyCatAct implements TencentLocationListener {    private TextView btn_make;    private TextView Tv_longitude;/*经度*/    private TextView Tv_latitude;/*维度*/    private EditText edit_name;    private EditText edit_addr;    private EditText edit_people;    private EditText edit_tel;    private static final String MSG = "MakebusinessAct.java[+]";    /**     * 腾讯地图参数     */    private TencentLocationListener locationListener;    private TencentLocationManager locationManager;    private TencentLocationRequest locationRequest;    /**     * 商户值     */    private String _businessname;/*商户的店铺名称*/    private String _businessaddr;/*商户的店铺地址*/    private String _businesspeople; /*商户的联系人名称*/    private String _businesstel;/*商户的电话*/    private String _businessLongitude;/*商户的经度*/    private String _businessDimensionality;/*商户的维度*/    private String Action;/*提交状态*/    @SuppressLint("ResourceType")    @Override    protected void onCreate(Bundle savedInstanceState) {        setContentView(R.layout.activity_makebusiness);        setTransparentBar();        /*设置标题*/        TextUnt.with(this,R.id.assembly_act_headTitle).setText("预约办理");        /*判断是否有定位权限 没有定位权限就去申请定位权限*/        if (isPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {            /*获取权限成功*/            Log.i(MSG, "定位地址获取权限成功");        } else {            final AlertDialog alertDialog;            AlertDialog.Builder builder = new AlertDialog.Builder(MakebusinessAct.this);            View item = findViewById(R.layout.alert_message);            TextView btn_confirm = item.findViewById(R.id.alert_messageBtnConfirm);            TextView Tv_title = item.findViewById(R.id.alert_messageTitle);            TextUnt.with(Tv_title).setText("请求授权");            TextView Tv_context = item.findViewById(R.id.alert_messageText);            TextUnt.with(Tv_context).setText("检测到您没有开启定位权限,请您开启定位权限用来获取您的店铺位置.");            TextUnt.with(btn_confirm).setText("马上去开启");            builder.setView(item);            builder.show();        }        /*寻找控件*/        btn_make = findViewById(R.id.activity_makebusiness_btn_make);/*确定预约*/        TextUnt.with(btn_make).setBackground(Tools.CreateDrawable(1, getResources().getString(R                .color.ThemeColor), getResources().getString(R.color.ThemeColor), 10))                .setTextColor("#ffffff");        /*店铺全称*/        edit_name = findViewById(R.id.activity_useraddhome_addr_editName);        /*店铺地址*/        edit_addr = findViewById(R.id.activity_makebusiness_editAddr);        /*店铺的电话*/        edit_tel = findViewById(R.id.activity_makebusiness_editTel);        /*店铺的联系人*/        edit_people = findViewById(R.id.activity_makebusiness_editPeople);        /*经度*/        Tv_longitude = findViewById(R.id.activity_makebusiness_Longitude);        /*维度*/        Tv_latitude = findViewById(R.id.activity_makebusiness_Latitude);        init();        super.onCreate(savedInstanceState);    }    private void init() {        /**         * 获取地址         */        locationRequest = TencentLocationRequest.create();        locationManager = TencentLocationManager.getInstance(getApplicationContext());        locationRequest.setInterval(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);        locationRequest.setInterval(1000);        locationRequest.setAllowCache(false);        locationManager.requestLocationUpdates(locationRequest, this);        Listener();    }    private void Listener() {        /**         * 退出按键         */        findViewById(R.id.assembly_act_headBackImg).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                finish();            }        });        edit_name.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                EditText ev = (EditText) v;                if (ev.getText().toString().equals("店铺全称")) {                    ev.setText("");                }            }        });        edit_addr.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                EditText ev = (EditText) v;                if (ev.getText().toString().trim().equals("街道、门牌、大楼、建筑、具体位置")) {                    ev.setText("");                }            }        });        edit_tel.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                EditText ev = (EditText) v;                if (ev.getText().toString().trim().equals("手机/固话")) {                    ev.setText("");                }            }        });        edit_people.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                EditText ev = (EditText) v;                if (ev.getText().toString().trim().equals("您的姓名")) {                    ev.setText("");                }            }        });        /**         * 确定预约办理         */        btn_make.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                if (edit_name.getText().toString().equals("") || edit_name.getText().toString()                        .equals("店铺全称")) {                    Toast.makeText(getApplicationContext(), "店铺名称不能为空", Toast.LENGTH_SHORT).show();                } else if (edit_addr.getText().toString().equals("") || edit_addr.getText()                        .toString().equals("街道、门牌、大楼、建筑、具体位置")) {                    Toast.makeText(getApplicationContext(), "店铺的具体位置不能为空", Toast.LENGTH_SHORT)                            .show();                } else if (edit_tel.getText().toString().equals("") || edit_tel.getText()                        .toString().equals("手机/固话")) {                    Toast.makeText(getApplicationContext(), "手机/固话必须要填写", Toast.LENGTH_SHORT)                            .show();                } else if (edit_people.getText().toString().equals("") || edit_people.getText()                        .toString().equals("您的姓名")) {                    Toast.makeText(getApplicationContext(), "您的姓名不能为空", Toast.LENGTH_SHORT).show();                } else {                    _businessname = edit_name.getText().toString().trim();                    _businessaddr = edit_addr.getText().toString().trim();                    _businesspeople = edit_people.getText().toString().trim();                    _businesstel = edit_tel.getText().toString().trim();                    /**                     * 开始提交                     */                    XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder();                    xmlInstance.initDom();                    xmlInstance.setXmlTree(LocalAction.ACTION_MAKEBUSINESS.ACTION_BUSINESS_NAME,                            _businessname);                    xmlInstance.setXmlTree(LocalAction.ACTION_MAKEBUSINESS.ACTION_BUSINESS_ADDR,                            _businessaddr);                    xmlInstance.setXmlTree(LocalAction.ACTION_MAKEBUSINESS                            .ACTION_BUSINESS_PEOPLE, _businesspeople);                    xmlInstance.setXmlTree(LocalAction.ACTION_MAKEBUSINESS.ACTION_BUSINESS_TEL,                            _businesstel);                    xmlInstance.setXmlTree(LocalAction.ACTION_MAKEBUSINESS.ACTION_BUSINESS_LONG,                            _businessLongitude);                    xmlInstance.setXmlTree(LocalAction.ACTION_MAKEBUSINESS.ACTION_BUSINESS_LAT,                            _businessDimensionality);                    xmlInstance.overDom();                    Net.doPostXml(getApplicationContext(), LocalValues.HTTP_ADDRS                            .HTTP_ADDR_PUSH_MAKEBUSINESS, new ProgramInterface() {                        @Override                        public void onSucess(String data, int code, WaitDialog.RefreshDialog                                _refreshDialog) {                            Log.i(MSG, data);                            XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data                                    .trim());                            xmlanalysisFactory.Startanalysis(new XmlanalysisFactory                                    .XmlanalysisInterface() {                                @Override                                public void onFaile() {                                }                                @Override                                public void onStartDocument(String tag) {                                }                                @Override                                public void onStartTag(String tag, XmlPullParser pullParser,                                                       Integer id) {                                    /*获取状态*/                                    try {                                        if (tag.equals(LocalAction.ACTION)) {                                            Action = pullParser.nextText().trim();                                        }                                    } catch (Exception e) {                                        Log.i(MSG, "错误信息:" + e.getMessage());                                    }                                }                                @Override                                public void onEndTag(String tag, XmlPullParser pullParser,                                                     Integer id) {                                }                                @Override                                public void onEndDocument() {                                    if (Action.equals("0")) {                                        Toast.makeText(getApplicationContext(), "感谢您的支持," +                                                "稍后业务员将会致电给您.请您留意电话哦", Toast.LENGTH_SHORT).show();                                        Timer timer = new Timer();                                        timer.schedule(new TimerTask() {                                            @Override                                            public void run() {                                                finish();                                            }                                        }, 3000);                                    }                                }                            });                        }                        @Override                        public WaitDialog.RefreshDialog onStartLoad() {                            return null;                        }                        @Override                        public void onFaile(String data, int code) {                        }                    }, xmlInstance.getXmlTree().trim());                }            }        });    }    @Override    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {        if (TencentLocation.ERROR_OK == 0) {            locationManager.removeUpdates(this);            _businessDimensionality = String.valueOf(tencentLocation.getLatitude());/*维度*/            _businessLongitude = String.valueOf(tencentLocation.getLongitude());/*经度*/            TextUnt.with(Tv_latitude).setText("店铺维度:" + _businessDimensionality);/*设置维度*/            TextUnt.with(Tv_longitude).setText("店铺经度:" + _businessLongitude);/*设置经度*/            edit_addr.setText(tencentLocation.getAddress());        } else {            Toast.makeText(getApplicationContext(), "请求定位失败,错误代码为:[" + i + "]", Toast                    .LENGTH_SHORT).show();        }    }    @Override    public void onStatusUpdate(String s, int i, String s1) {    }}