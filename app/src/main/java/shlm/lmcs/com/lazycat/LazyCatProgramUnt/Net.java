package shlm.lmcs.com.lazycat.LazyCatProgramUnt;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.LOAD_IMAGEPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.ImageCache;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;

/**
 * 网络访问模块  有关于网络访问的监听和提交事件都在这里
 * <p>
 * 该模块属于左边远景软件开发工作室公司服务器内模块 任何开发个体的android project都可以使用
 * 该套模块
 */
@SuppressLint("LongLogTag")
public class Net {
    private String MSG = "Net.java[+]";
    private String tUrl = "";
    private StringBuffer mKvsBuffer = new StringBuffer();
    private Net.onVisitInterServiceListener mOnVisitInterServiceListener;
    private ImageCache imageCache;
    private int VisitInterMethod;

    /**
     * 用GET方式获取数据信息
     *
     * @param tUrl                         地址
     * @param mOnVisitInterServiceListener 监听回调
     * @param kvs                          参数对,没有就直接用NULL
     */
    public static void doGet(final Context context, String tUrl, final Net
            .onVisitInterServiceListener mOnVisitInterServiceListener, String... kvs) {
        if (!Tools.isIntentConnect(context)) {
            //网络无连接 就不做什么操作了
            if (mOnVisitInterServiceListener != null) {
                mOnVisitInterServiceListener.onNotConnect();
            }
            return;
        } else {
            final WaitDialog.RefreshDialog refreshDialog = mOnVisitInterServiceListener
                    .onStartLoad();

            final StringBuffer kvsBuffer = new StringBuffer();
            if (kvs != null && kvs.length > 1) {
                try {
                    for (int i = 0; i < kvs.length; i += 2) {
                        kvsBuffer.append(kvs[i] + "=" + kvs[i + 1] + "&");
                    }
                } catch (Exception e) {
                    Log.e(Config.DEBUG, "SystemVisitInerService.java[+]:" + e.getMessage());
                }
            }
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... urls) {
                    try {
                        HttpURLConnection con = (HttpURLConnection) new URL(urls[0].trim()
                                .toString() + "?" + kvsBuffer.toString()).openConnection();
                        Log.i(Config.DEBUG, "访问网络地址:" + urls[0].trim().toString() + "?" +
                                kvsBuffer.toString());
                        con.setRequestMethod("GET");
                        con.setConnectTimeout(5000);
                        con.setReadTimeout(5000);
                        con.connect();
                        if (con != null) {
                            InputStream is = con.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new
                                    InputStreamReader(is));
                            StringBuffer sb = new StringBuffer();
                            String ReadLine = "";
                            while ((ReadLine = bufferedReader.readLine()) != null) {
                                sb.append(ReadLine);
                            }
                            is.close();
                            con.disconnect();
                            bufferedReader.close();
                            return sb.toString();
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        Log.e(Config.DEBUG, "SystemVisitInerService.java[+]:" + e.getMessage());

                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    if (s != null) {
                        if (mOnVisitInterServiceListener != null) {
                            mOnVisitInterServiceListener.onSucess(s, refreshDialog);
                        }
                    } else {
                        if (mOnVisitInterServiceListener != null) {
                            mOnVisitInterServiceListener.onFail("");
                        }
                    }
                    super.onPostExecute(s);
                }
            }.execute(tUrl);
        }
    }

    public interface onVisitInterServiceListener {
        WaitDialog.RefreshDialog onStartLoad();/*开始数据访问 并且添加一个等候的DIALOG*/

        void onSucess(String tOrgin, final WaitDialog.RefreshDialog _rfreshdialog);//成功的监听

        void onNotConnect();//网络断开连接

        void onFail(String tOrgin);//失败的监听
    }


    /**
     * 获取XML文件的网络访问
     */
    @SuppressLint("StaticFieldLeak")
    public static void doGetXml(Context mContext, String url, final ProgramInterface
            .XMLDomServiceInterface xmlDomServiceInterface, String... kvs) {

        /**
         * 判断是否没有网络访问
         */
        if (!Tools.isIntentConnect(mContext)) {
            if (xmlDomServiceInterface != null) {
                xmlDomServiceInterface.onNotService();
            }
            return;
        } else {
            final StringBuffer kvsBuffer = new StringBuffer();
            if (kvs != null && kvs.length > 1) {
                try {
                    for (int i = 0; i < kvs.length; i += 2) {
                        kvsBuffer.append(kvs[i] + "=" + kvs[i + 1] + "&");
                    }
                } catch (Exception e) {
                    Log.e(Config.DEBUG, "Net.java[+]:" + e.getMessage());
                }
            }
            /**
             * 返回XMLinputStream
             */
            new AsyncTask<String, Void, InputStream>() {
                boolean isJson = false;//判断是否该用JSON解析
                String origin = "";//json解析的数据

                @Override
                protected InputStream doInBackground(String... urls) {
                    InputStream in = null;
                    try {
                        URL url = new URL(urls[0].trim().toString() + "?" + kvsBuffer.toString());
                        Log.i(Config.DEBUG, "网络访问的地址" + urls[0].trim().toString() + "?" +
                                kvsBuffer.toString());
                        if (url != null) {
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setConnectTimeout(2000);
                            con.setDoInput(true);
                            con.setRequestMethod("GET");
                            if (200 == con.getResponseCode()) {
                                //成功
                                Map<String, List<String>> headFiels = con.getHeaderFields();
                                int size = headFiels.size();
                                for (int i = 0; i < size; i++) {
                                    if (con.getHeaderFieldKey(i).equals("Content-Type")) {
                                        if (con.getHeaderField(i).indexOf("text/html") != -1) {
                                            Log.e(Config.DEBUG, "应该要用json解析");
                                            isJson = true;
                                            BufferedReader bufferedReader = new BufferedReader
                                                    (new InputStreamReader(con.getInputStream()));
                                            String len;
                                            StringBuffer stringBuffer = new StringBuffer();
                                            while ((len = bufferedReader.readLine()) != null) {
                                                stringBuffer.append(len);
                                            }
                                            origin = stringBuffer.toString();
                                            Log.e(Config.DEBUG, "Json数据返回:" + origin);
                                        } else {
                                            Log.e(Config.DEBUG, "应该要用XML解析");
                                            in = con.getInputStream();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e(Config.DEBUG, "getXMLInterGet[+]" + e.getMessage());
                    }
                    return in;
                }

                @Override
                protected void onPostExecute(InputStream inputStream) {
                    if (inputStream != null) {
                        //成功
                        if (xmlDomServiceInterface != null) {
                            xmlDomServiceInterface.onSucess(inputStream);
                        }
                    } else {
                        //为空  说明这个可能需要JSON解析
                        if (xmlDomServiceInterface != null && isJson) {
                            if (!TextUtils.isEmpty(origin)) {
                                xmlDomServiceInterface.onJson(origin);
                            } else {
                                Log.e(Config.DEBUG, "Net.java[+]该使用json解析 可是获取文本数据失败");
                            }

                        }
                    }
                    super.onPostExecute(inputStream);
                }
            }.execute(url);
        }
    }


    /**
     * 用POST方式提交XML数据信息
     *
     * @param mContext         上下文
     * @param url              地址
     * @param programInterface 接口信息
     * @param xmldata          xml数据
     */
    public static void doPostXml(final Context mContext, String url, final ProgramInterface
            programInterface, final String xmldata) {
        final WaitDialog.RefreshDialog _refreshDialog;
        _refreshDialog = programInterface.onStartLoad();
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... _url) {
                if(_refreshDialog != null){
                    _refreshDialog.show();
                }
                String _data = null;
                Log.i("Net.java[doPost]","要提交的XML信息为：" + xmldata);
                //构建xml数据信息
                /*StringBuilder xml = new StringBuilder();
                xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
                xml.append("<body>");
                Log.i("doPostXml.java[+]", "xml字节对的个数为:" + xml.length());
                *//*处理字符对  用来上传比较长的一些不适合Get包提交的数据信息*//*
                for (int i = 0; i < xmldata.length; i += 2) {
                    xml.append("<" + xmldata[i] + ">" + xmldata[i + 1] + "</" + xmldata[i] + ">");
                }
                xml.append("</body>");
                Log.i("doPostXml.java[+]", "xml提交数据为:" + xml.toString());*/
                try {
                    byte[] xmlbyte = xmldata.getBytes("UTF-8");
                    URL url = new URL(_url[0]);//地址
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(15000);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", String.valueOf(xmlbyte.length));
                    conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
                    OutputStream outStream = conn.getOutputStream();
                    outStream.write(xmlbyte);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                                (is));
                        StringBuffer sb = new StringBuffer();
                        String ReadLine = "";
                        while ((ReadLine = bufferedReader.readLine()) != null) {
                            sb.append(ReadLine);
                        }
                        is.close();
                        conn.disconnect();
                        bufferedReader.close();
                        _data = sb.toString();
                    } else {
                        InputStream eis = conn.getErrorStream();
                        InputStreamReader isr = new InputStreamReader(eis, "UTF-8");
                        BufferedReader br = new BufferedReader(isr);
                        Log.i(Config.DEBUG, "Net.java[+]xml提交失败");
                        String line;
                        while ((line = br.readLine()) != null) {
                            Log.e(Config.DEBUG, "Net.java[+]ErrorStream:" + line);
                        }
                    }
                } catch (Exception e) {
                    Log.e(Config.DEBUG, "Net.java[+]" + e.getMessage() + "1");
                    e.printStackTrace();
                }
                return _data;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    Log.e(Config.DEBUG, "Net.java[+]xml提交返回的数据为null");
                    if (programInterface != null) {
                        programInterface.onFaile("", 0);
                    } else {
                        Log.e(Config.DEBUG, "Net.java[+]xml提交数据回调为空");
                    }
                } else {
                    //开始监听调用
                    if (programInterface != null) {
                        programInterface.onSucess(s.toString(), 0, _refreshDialog);
                    } else {
                        Log.e(Config.DEBUG, "Net.java[+]xml提交数据回调为空");
                    }
                }
                super.onPostExecute(s);
            }
        }.execute(url);


    }

    public static void doGetimg(final LOAD_IMAGEPAGE imgfile, final ProgramInterface.doGetImg
            _doGetimg) {
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... imgurl) {
                Bitmap bitmap = null;
                try {
                    URL url = new URL(imgurl[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(4000);
                    con.setRequestMethod("GET");
                    con.connect();
                    if (con.getResponseCode() == 200) {
                        bitmap = BitmapFactory.decodeStream(con.getInputStream());
                        Log.i(Config.DEBUG, "访问成功");
                    } else {
                        Log.e(Config.DEBUG, "访问失败 访问返回状态码:" + con.getResponseCode());
                    }
                } catch (Exception e) {
                    Log.e(Config.DEBUG, "Net.java[+]" + e.getMessage());
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    _doGetimg.onSucess(bitmap);
                } else {
                    _doGetimg.onFain();
                }
            }
        }.execute(Config.HTTP_ADDR.PHOTO_SERVICE_ADDR.trim() + imgfile.getImg_url());


    }


    public static Bitmap doGetBitmap() {
        Bitmap bitmap = null;

        return bitmap;
    }


}
