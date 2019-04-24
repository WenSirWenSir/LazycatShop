package shlm.lmcs.com.lazycat.LazyCatProgramUnt;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.XMLUserAddr;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;


/**
 * 既然遇见了 既然相爱了
 * 那就别放弃别去计较,别去在意别人的想法。
 * 加油点
 * 只是为了以后没有人可以用钱把你砸的低头
 */
@SuppressLint("LongLogTag")
public class Tools {

    /**
     * Whether the network is Connected
     *
     * @param context
     * @return
     */
    public static boolean isIntentConnect(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        } else {
            @SuppressLint("MissingPermission") NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) return true;
                }
            }
            return false;

        }
    }


    /**
     * gets the screen height or width
     */
    public static int GetWindowScreen(Context tContext, int tModule) {
        int value = 0;
        try {
            WindowManager wm = (WindowManager) tContext.getSystemService(Context.WINDOW_SERVICE);
            switch (tModule) {
                case Config.Windows.GET_WINDOW_HEIGHT:
                    value = wm.getDefaultDisplay().getHeight();
                    break;
                case Config.Windows.GET_WINDOW_WIDHT:
                    value = wm.getDefaultDisplay().getWidth();
                    break;
            }
            return value;
        } catch (Exception e) {

        }
        return value;
    }

    /**
     * 隐藏一个Fragment
     */

    public static void hideFragment(FragmentTransaction ft, LazyCatFragment ftagment) {
        if (ft != null) {
            ft.hide(ftagment);
        } else {
            Log.e(Config.DEBUG, "Tools.java[+]关闭Fragment失败");
        }
    }


    /**
     * Determines whether the file exists
     *
     * @param tPath file addrs
     * @return
     */
    public boolean FileIn(String tPath) {
        try {
            File f = new File(tPath);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * get the Token data saved by the User
     *
     * @param key Want key
     * @return
     */
    @SuppressLint("LongLogTag")
    public static String gettoKen(Context tContext, String key) {
        SharedPreferences sharedPreferences = tContext.getSharedPreferences("YazhouUser", 0);
        try {
            return sharedPreferences.getString(key, "");//如果不存在  就返回一个空字符
        } catch (Exception e) {
            Log.i(Config.DEBUG, "LeftCompanyProgram Tools.java[+]" + e.getMessage());
            return "";
        }
    }

    /**
     * saved user data
     *
     * @param tContext
     * @param tdata    Characters of name,key
     */
    @SuppressLint({"ApplySharedPref", "LongLogTag"})
    public static void settoKen(Context tContext, String... tdata) {
        SharedPreferences sharedPreferences = tContext.getSharedPreferences("YazhouUser", 0);
        try {
            sharedPreferences.edit().putString(tdata[0], tdata[1]).commit();
            Log.i(Config.DEBUG, "LeftCompanyProgarm Tools.java[+]保存用户数据成功");
        } catch (Exception e) {
            Log.e(Config.DEBUG, "LeftCompnayProgram Tools.java[+]" + e.getMessage());
        }

    }

    /**
     * 发送短信验证码
     * <p>
     * 短信的验证码由服务器进行设置
     *
     * @param tPhone 电话号码
     */
    public static void sendVerificationCodeSMS(final Context tContext, String tPhone, final
    ProgramInterface.SMSInterface listener) {


        Net.doGet(tContext, Config.HTTP_ADDR.SendVerificationCodeAddr(), new Net
                .onVisitInterServiceListener() {
            @Override
            public void onSucess(String tOrgin) {
                JsonEndata jsonEndata = new JsonEndata(tOrgin);
                if (jsonEndata.getJsonKeyValue(Config.HttpMethodUserAction.KEY_STATUS).equals
                        (Config.HttpMethodUserAction.STATUS_SENDOK)) {
                    listener.onSendOk();
                } else {
                    listener.onSendError();
                }
            }

            @Override
            public void onNotConnect() {

            }

            @Override
            public void onFail(String tOrgin) {

            }
        }, Config.HttpMethodUserAction.KEY_ACTION, "" + Config.HttpMethodUserAction
                .SEND_VERIFICATION, Config.HttpMethodUserAction.KEY_USER, Tools.getStringMD5
                (tPhone), Config.HttpMethodUserAction.KEY_PHONE, tPhone);

    }


    /**
     * get the maximum memory of the phone
     * return values tyeps is kb
     */

    public static int getApplicationMemorySize() {
        return (int) Runtime.getRuntime().maxMemory();
    }


    /**
     * md5     encryption
     *
     * @param str want encryption values
     * @return
     */
    @SuppressLint("LongLogTag")
    public static String getStringMD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            StringBuffer strBuf = new StringBuffer();
            byte[] encryption = md5.digest();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        } catch (Exception e) {
            Log.e(Config.DEBUG, "LeftCompanyProgram Tools.java[+]:" + e.getMessage());
            return "";
        }
    }

    /**
     * gets the device unique identifier
     */
    @SuppressLint({"NewApi", "MissingPermission"})
    public static String getsystemDevicdeId(Context tContext) {
        TelephonyManager telephonyManager = (TelephonyManager) tContext.getSystemService(Context
                .TELEPHONY_SERVICE);
        try {
            return telephonyManager.getImei();
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * change the background lines and background color
     */
    public static void setBackgroundValues(View view, int StorkeWith, String StorkeColor, String
            BackgroundColor) {
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();//get view
        // background
        if (StorkeWith != 0) {
            gradientDrawable.setStroke(StorkeWith, Color.parseColor(StorkeColor));//Set
            // background line Color and width
        }

        //update background color
        gradientDrawable.setColor(Color.parseColor(BackgroundColor));
    }


    /**
     * Create a AlertDilg box,
     *
     * @param view            view
     * @param mConext         ApplictionContext
     * @param title           titleStr
     * @param context         contextStr
     * @param cancleStr       cancleStr
     * @param confirmStr      confirmStr
     * @param alertDilgClick  interface Cancle and Confirm
     * @param alertViewIDpage Calss for ConfigPageClass.AlertViewIDpage(Instance) you can call
     *                        getAlertViewIDpageInstance to get
     */

    @SuppressLint("LongLogTag")
    public static void showAlertDilg(View view, Context mConext, String title, String context,
                                     String cancleStr, String confirmStr, final AlertDilgClick
                                                 alertDilgClick, ConfigPageClass.AlertViewIDpage
                                                 alertViewIDpage) {
        if (alertViewIDpage != null) {
            final AlertDialog alertDialog = new AlertDialog.Builder(mConext).create();
            if (alertViewIDpage.getConfirm() != null) {
                alertViewIDpage.getCancle().setText(cancleStr);
                alertViewIDpage.getCancle().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertDilgClick != null) {

                            alertDilgClick.onCancle(alertDialog);
                        }
                    }
                });

            } else {
                Log.e(Config.DEBUG, "Tools.java[+]表里的值为空{0001}");

            }
            if (alertViewIDpage.getTitle() != null) {
                alertViewIDpage.getTitle().setText(title);
            } else {
                Log.e(Config.DEBUG, "Tools.java[+]表里的值为空{0001}");

            }
            if (alertViewIDpage.getContext() != null) {
                alertViewIDpage.getContext().setText(context);
            } else {
                Log.e(Config.DEBUG, "Tools.java[+]表里的值为空{0001}");

            }
            if (alertViewIDpage.getConfirm() != null) {
                alertViewIDpage.getConfirm().setText(confirmStr);
                alertViewIDpage.getConfirm().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertDilgClick != null) {
                            alertDilgClick.onConfirm(alertDialog);
                        }
                    }
                });

            } else {
                Log.e(Config.DEBUG, "Tools.java[+]表里的值为空{0001}");
            }

            if (alertViewIDpage.isCanwindow()) {
                Log.e(Config.DEBUG, "Tools.java[+]表里的值为空{0002}");
            }
            alertDialog.setView(view);
            alertDialog.show();

        } else {
            Log.e(Config.DEBUG, "Tools.java[+]表是空的");

        }

    }

    public interface AlertDilgClick {
        void onConfirm(AlertDialog alertDialog);

        void onCancle(AlertDialog alertDialog);
    }


    /**
     * 检查验证码是否正确
     */
    public static void checkVerficationCode(Context tContext, String phone, String code, final
    ProgramInterface programInterface) {
        Net.doGet(tContext, Config.HTTP_ADDR.CheckVerificationAddr(), new Net
                .onVisitInterServiceListener() {
            @Override
            public void onSucess(String tOrgin) {
                if (programInterface != null) {
                    programInterface.onSucess(tOrgin, 0);

                }
            }

            @Override
            public void onNotConnect() {
                if (programInterface != null) {
                    programInterface.onFaile("", 0);
                }


            }

            @Override
            public void onFail(String tOrgin) {
                if (programInterface != null) {
                    programInterface.onFaile("", 0);
                }

            }
        }, Config.HttpMethodUserAction.KEY_ACTION, Config.HttpMethodUserAction
                .CHECK_VERIFICATION, Config.HttpMethodUserAction.KEY_USER, Tools.getStringMD5
                (phone), Config.HttpMethodUserAction.KEY_CODE, code);

    }


    @SuppressLint("LongLogTag")
    public static ArrayList<XMLUserAddr> UserAddrXMLDomeService(InputStream is) throws Exception {
        ArrayList<XMLUserAddr> list = new ArrayList<XMLUserAddr>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.parse(is);
        Element element = document.getDocumentElement();//获取元素
        NodeList nodes = element.getElementsByTagName("addrs");
        if (nodes.getLength() <= 0) {
            list = null;//没有数据信息

        } else {
            for (int i = 0; i < nodes.getLength(); i++) {
                Element bodyelement = (Element) nodes.item(i);
                XMLUserAddr xmlUserAddr = new XMLUserAddr();
                NodeList childNodes = bodyelement.getChildNodes();
                Log.i(Config.DEBUG, "childnodes总数：" + childNodes.getLength());
                for (int y = 0; y < childNodes.getLength(); y++) {
                    if (childNodes.item(y).getNodeType() == Node.ELEMENT_NODE) {
                        if ("USER_NAME".equals(childNodes.item(y).getNodeName())) {
                            //用户的名称
                            Log.i(Config.DEBUG, "获取到的名称" + childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                            xmlUserAddr.setUSER_NAME(childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                        } else if ("USER_TEL".equals(childNodes.item(y).getNodeName())) {
                            Log.i(Config.DEBUG, "获取到的电话" + childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                            xmlUserAddr.setUSER_TEL(childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                        } else if ("USER_ADDR".equals(childNodes.item(y).getNodeName())) {
                            Log.i(Config.DEBUG, "获取到的地址" + childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                            xmlUserAddr.setUSER_ADDR(childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                        } else if ("PHYSICS_ADDR".equals(childNodes.item(y).getNodeName())) {
                            Log.i(Config.DEBUG, "获取到的物理地址" + childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                            xmlUserAddr.setPHYSICS_ADDR(childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                        } else if ("ADDR_IN".equals(childNodes.item(y).getNodeName())) {
                            Log.i(Config.DEBUG, "获取到的地址所属" + childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                            xmlUserAddr.setADDR_IN(childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                        } else if ("USER_SEX".equals(childNodes.item(y).getNodeName())) {
                            Log.i(Config.DEBUG, "获取到的用户的性别" + childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                            xmlUserAddr.setUSER_SEX(childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                        } else if ("USER_YEAR".equals(childNodes.item(y).getNodeName())) {
                            Log.i(Config.DEBUG, "获取到的用户的年龄" + childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                            xmlUserAddr.setUSER_YEAR(childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                        } else if ("DEFAULT_ADDR".equals(childNodes.item(y).getNodeName())) {
                            Log.i(Config.DEBUG, "获取到是否默认地址" + childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                            xmlUserAddr.setDEFAULT_ADDR(childNodes.item(y).getFirstChild()
                                    .getNodeValue());
                        }
                    }
                }
                list.add(xmlUserAddr);
            }
        }
        return list;
    }


    /**
     * 判断是否获取到权限
     *
     * @param mContext
     * @param permission 清单文件中的权限
     * @return
     */
    public static boolean isPermission(Context mContext, String permission) {
        if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager
                .PERMISSION_DENIED) {
            //获取到权限
            return true;
        } else {
            //没有获取到权限
            return false;
        }
    }


    /**
     * 设置一个背景样式
     *
     * @param width           线条的宽度
     * @param StockColor      线条的颜色
     * @param backgroundColor 背景的颜色
     * @param radius          角度
     * @return GradientDrawable
     */

    public static GradientDrawable CreateDrawable(int width, String StockColor, String
            backgroundColor, int radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor(backgroundColor));//设置背景
        gradientDrawable.setStroke(width, Color.parseColor(StockColor));//设置线条的宽度和颜色
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    /**
     * 拨打电话
     *
     * @param mContext 上下文
     * @param phone    电话号码
     */

    public static void callPhone(Context mContext, String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Log.i(Config.DEBUG, "Tools.java[+]要拨打的电话为:" + phone);
            Intent i = new Intent(Intent.ACTION_CALL);
            Uri uri = Uri.parse("tel:" + phone);
            i.setData(uri);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        } else {
            Toast.makeText(mContext, "实在不好意思,客服人员暂时不方便接电话", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 动态申请权限
     *
     * @param mContext   上下文
     * @param permission 权限名称
     */
    public static void getPermission(Context mContext, String permission) {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{permission}, 1);
    }


    /**
     * 返回客服人员的手机号码
     *
     * @return
     */
    public static String getServicePeoPhone() {
        return "";
    }


    /**
     * 把图片剪切成圆角
     *
     * @return
     */
    public static Bitmap CutPhotoRadius(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config
                .ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0Xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    /**
     * 图片缩放[失真严重]
     *
     * @return
     */
    public static Bitmap zoomImg(Context mContext, Bitmap bt, int _height, int _width) {
        int height = bt.getHeight();/*获取原始的高度*/
        Log.i(Config.DEBUG, "height" + bt.getHeight());
        int width = bt.getWidth();/*获取原始的宽度*/
        Log.i(Config.DEBUG, "widht" + bt.getWidth());

        /*计算缩放比例*/
        float _sWidth = 0, _sHeight = 0;

        if (height > _height && width > _width) {
            _sWidth = width / _width;
            _sHeight = height / _height;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(1f / _sWidth, 1f / _sHeight);
        Bitmap newBm = Bitmap.createBitmap(bt, 0, 0, width, height, matrix, true);
        bt.recycle();
        return newBm;
    }


    /**
     * 保存文件
     *
     * @param key
     * @param bitmap
     */
    public static void saveImage(String key, Bitmap bitmap) {

    }


    /**
     * 获取剩余内存 最大内存 分配内存
     *
     * @param onMemorySize
     * @param mContext
     */
    public static void getMemorySize(Context mContext, ProgramInterface.onMemorySize onMemorySize) {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context
                .ACTIVITY_SERVICE);
        int memory = activityManager.getMemoryClass();
        float totalmemory = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
        float freememory = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));
        onMemorySize.onGet(memory, totalmemory, freememory);
    }

    /**
     * @param view
     * @param time
     * @return
     */
    @SuppressLint("NewApi")
    public static android.animation.Animator createRoundAnimation(View view, int time) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        float startx = 0f;
        float starty = (float) Math.sqrt(cx * cx + cy * cy);
        Animator animation = ViewAnimationUtils.createCircularReveal(view, cx, cy, startx, starty);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(time);
        return animation;
    }

    /**
     * 创建一个可以换行显示的layout
     *
     * @param context
     * @param textlist 字符集合
     */
    public static void createshifterLinearlayout(LinearLayout layout, Context context,
                                                 ArrayList<String> textlist) {
        /*获取父布局的宽度*/
        TextView t = new TextView(context);
        t.setText("234234324");
        TextView a = new TextView(context);
        layout.addView(t);
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        layout.measure(spec, spec);
        Log.e(Config.DEBUG, "测量宽度" + t.getMeasuredWidth() + "父布局宽度：" + layout.getMeasuredWidth());

    }

    /**
     * 将文本变成TextView
     *
     * @param text_list
     */
    @SuppressLint("NewApi")
    public static ArrayList<LinearLayout> handleToarraylist(Context context, ArrayList<String>
            text_list, int left, int top, int right, int bottom, String color, String textColor,int textSize) {
        ArrayList<LinearLayout> textViews = new ArrayList<LinearLayout>();
        for (int i = 0; i < text_list.size(); i++) {
            LinearLayout layout = new LinearLayout(context);
            TextView tv = new TextView(context);
            tv.setText(text_list.get(i));
            tv.setPadding(left, top, right, bottom);
            layout.addView(tv);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
            params.setMargins(20, 10, 10, 10);
            tv.setLayoutParams(params);
            tv.setTextSize(textSize);
            tv.setTextColor(Color.parseColor(textColor));
            tv.setBackground(CreateDrawable(1, color, color, 10));
            textViews.add(layout);
        }
        return textViews;

    }
}
